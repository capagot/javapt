package javapt;

final public class RenderTask implements Runnable {
    public RenderTask(final Camera camera,  final Scene scene, final int samples, final ImageBuffer imageBuffer, final int line) {
        this.camera = camera;
        this.scene = scene;
        this.samples = samples;
        this.imageBuffer = imageBuffer;
        this.line = line;
        this.r = new java.util.Random(this.line);
    }

    @Override
    final public void run() {
        for(int x = 0; x < camera.imageWidth; ++x) {
            for(int s = 0; s < samples; ++s) {
                final Vec2 pixelSample = new Vec2(x + r.nextDouble(), this.line + r.nextDouble());
                Ray ray = camera.getRay(pixelSample);
                imageBuffer.setPixel(x, this.line, Vec3.add(imageBuffer.getPixel(x, this.line), traceRay(ray, 0)));
            }

            imageBuffer.setPixel(x, this.line, Vec3.div(imageBuffer.getPixel(x, this.line), samples));
        }
    }

    final public Vec3 traceRay(final Ray ray, final int depth) {
        IntersectionRecord intRec = new IntersectionRecord();

        if (!scene.intersect(ray, intRec) || (depth > 5))
            return new Vec3();

        ONB onb = new ONB(Vec3.normalize(intRec.normal));

        if (scene.primitives[intRec.id].reflType == ReflectionType.DIFFUSE) {
            // Cosine importance sampling
            double r1 = r.nextDouble();
            double r2 = r.nextDouble();
            double phi = 2.0 * Math.PI * r2;
            double cosTheta = Math.sqrt(r1);
            double sqrtSinTheta = Math.sqrt(1.0 - r1);
            Vec3 localWi = new Vec3(Math.cos(phi) * sqrtSinTheta, cosTheta, Math.sin(phi) * sqrtSinTheta);
            Vec3 worldWi = Mat3x3.mul(onb.getLocalToWorldMatrix(), localWi);
            Ray newRay = new Ray(Vec3.add(intRec.position, Vec3.mul(worldWi, 1e-5)), worldWi);

            Vec3 brdf = scene.primitives[intRec.id].color;
            Vec3 emission = scene.primitives[intRec.id].emission;

            return Vec3.add(emission, Vec3.mul(brdf, traceRay(newRay, depth + 1)));

        } else if (scene.primitives[intRec.id].reflType == ReflectionType.SPECULAR) {

        } else if (scene.primitives[intRec.id].reflType == ReflectionType.DIELECTRIC) {

        }

        return new Vec3();
    }

    final Camera camera;
    final Scene scene;
    final int samples;
    final ImageBuffer imageBuffer;
    final int line;
    private java.util.Random r;
}
