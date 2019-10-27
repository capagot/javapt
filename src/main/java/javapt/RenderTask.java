package javapt;

import java.text.DecimalFormat;

public final class RenderTask implements Runnable {
    public RenderTask(final Camera camera, final Scene scene, final int samples, final ImageBuffer imageBuffer, final int line) {
        this.camera = camera;
        this.scene = scene;
        this.samples = samples;
        this.imageBuffer = imageBuffer;
        this.line = line;
        this.r = new java.util.Random(this.line);
        this.df = new DecimalFormat("#0.0");
    }

    @Override
    public final void run() {
        for (int x = 0; x < camera.getImageWidth(); ++x) {
            for (int s = 0; s < samples; ++s) {
                final Vec2 pixelSample = new Vec2(x + r.nextDouble(), line + r.nextDouble());
                final Ray ray = camera.getRay(pixelSample);
                imageBuffer.setPixel(x, line, Vec3.add(imageBuffer.getPixel(x, line), traceRay(ray, 0)));
            }

            imageBuffer.setPixel(x, line, Vec3.div(imageBuffer.getPixel(x, line), samples));
        }

        synchronized (System.out) {
            System.out.print("Progress: " + df.format(((double) (line + 1) / camera.getImageHeight()) * 100.0) + "%\r");
        }
    }

    public final Vec3 traceRay(final Ray ray, final int depth) {
        IntersectionRecord intRec = new IntersectionRecord();

        if (!scene.intersect(ray, intRec) || (depth > 10))
            return new Vec3();

        final Vec3 emission = scene.getPrimitives().get(intRec.id).getMaterial().getEmission();
        final ONB onb = new ONB(Vec3.normalize(intRec.normal));
        final Vec3 localWo = Mat3x3.mul(onb.getWorldToLocalMatrix(), Vec3.mul(ray.getDirection(), -1.0));
        Vec3 localWi = new Vec3();
        final Vec3 partREValue = scene.getPrimitives().get(intRec.id).getMaterial().getBSDF().evalPartRE(localWo, localWi, r);
        final Vec3 worldWi = Mat3x3.mul(onb.getLocalToWorldMatrix(), localWi);
        final Ray newRay = new Ray(Vec3.add(intRec.position, Vec3.mul(worldWi, 1e-5)), worldWi);

        return Vec3.add(emission, Vec3.mul(partREValue, traceRay(newRay, depth + 1)));  
    }

    private final Camera camera;
    private final Scene scene;
    private final int samples;
    private final ImageBuffer imageBuffer;
    private final int line;
    private final java.util.Random r;
    private final DecimalFormat df;
}
