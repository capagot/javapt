package javapt;

import java.text.DecimalFormat;

final public class RenderTask implements Runnable {
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
    final public void run() {
        for (int x = 0; x < camera.imageWidth; ++x) {
            for (int s = 0; s < samples; ++s) {
                final Vec2 pixelSample = new Vec2(x + r.nextDouble(), this.line + r.nextDouble());
                Ray ray = camera.getRay(pixelSample);
                imageBuffer.setPixel(x, this.line, Vec3.add(imageBuffer.getPixel(x, this.line), traceRay(ray, 0)));
            }

            imageBuffer.setPixel(x, this.line, Vec3.div(imageBuffer.getPixel(x, this.line), samples));
        }

        synchronized (System.out) {
            System.out.print("Progress: " + df.format(((double) (this.line + 1) / camera.imageHeight) * 100.0) + "%\r");
        }
    }

    final public Vec3 traceRay(final Ray ray, final int depth) {
        IntersectionRecord intRec = new IntersectionRecord();

        if (!scene.intersect(ray, intRec) || (depth > 10))
            return new Vec3();

        final ONB onb = new ONB(Vec3.normalize(intRec.normal));

        if (scene.primitives.get(intRec.id).bsdf == BSDF.LAMBERTIAN) {
            final double r1 = r.nextDouble();
            final double r2 = r.nextDouble();
            final double phi = 2.0 * Math.PI * r2;
            final double cosTheta = Math.sqrt(r1);
            final double sqrtSinTheta = Math.sqrt(1.0 - r1);
            final Vec3 localWi = new Vec3(Math.cos(phi) * sqrtSinTheta, cosTheta, Math.sin(phi) * sqrtSinTheta);
            final Vec3 worldWi = Mat3x3.mul(onb.getLocalToWorldMatrix(), localWi);
            final Ray newRay = new Ray(Vec3.add(intRec.position, Vec3.mul(worldWi, 1e-5)), worldWi);

            final Vec3 brdf = scene.primitives.get(intRec.id).color;
            final Vec3 emission = scene.primitives.get(intRec.id).emission;

            return Vec3.add(emission, Vec3.mul(brdf, traceRay(newRay, depth + 1)));

        } else if (scene.primitives.get(intRec.id).bsdf == BSDF.SMOOTH_CONDUCTOR) {
            final Vec3 localWo = Mat3x3.mul(onb.getWorldToLocalMatrix(), Vec3.mul(ray.direction, -1.0));
            final Vec3 localWi = new Vec3(-localWo.x, localWo.y, -localWo.z);
            final Vec3 worldWi = Mat3x3.mul(onb.getLocalToWorldMatrix(), localWi);
            final Ray newRay = new Ray(Vec3.add(intRec.position, Vec3.mul(worldWi, 1e-5)), worldWi);

            final Vec3 emission = scene.primitives.get(intRec.id).emission;

            return Vec3.add(emission, traceRay(newRay, depth + 1));

        } else if (scene.primitives.get(intRec.id).bsdf == BSDF.SMOOTH_DIELECTRIC) {
            double iorI = 1.0;
            double iorT = 1.49;
            double signal = 1.0;
            final Vec3 localWo = Mat3x3.mul(onb.getWorldToLocalMatrix(), Vec3.mul(ray.direction, -1.0));

            if (localWo.y < 0.0) {
                final double tmp;
                tmp = iorI;
                iorI = iorT;
                iorT = tmp;
                signal = -1.0;
            }

            final double a = ((iorI * iorI) / (iorT * iorT)) * (1.0 - localWo.y * localWo.y);
            Vec3 localWi;

            if (a > 1.0) {  // Total internal reflection (TIR)
                localWi = new Vec3(-localWo.x, localWo.y, -localWo.z);
            } else {
                Vec3 fresnel = fresnelSchlick(localWo.y, iorI, iorT, signal);
                double maxReflectance = Vec3.max(fresnel);

                if (r.nextDouble() < maxReflectance)
                    localWi = new Vec3(-localWo.x, localWo.y, -localWo.z);
                else {
                    final Vec3 n = new Vec3(0.0, 1.0, 0.0);
                    double b = 1.0 - ((iorI * iorI) / (iorT * iorT)) * (1.0 - localWo.y * localWo.y);
                    localWi = Vec3.normalize(Vec3.sub(Vec3.mul((iorI / iorT), Vec3.sub(Vec3.mul(n, localWo.y), localWo)), Vec3.mul(Vec3.mul(signal, n), Math.sqrt(b))));
                }
            }

            final Vec3 worldWi = Mat3x3.mul(onb.getLocalToWorldMatrix(), localWi);
            final Ray newRay = new Ray(Vec3.add(intRec.position, Vec3.mul(worldWi, 1e-5)), worldWi);
            final Vec3 emission = scene.primitives.get(intRec.id).emission;

            return Vec3.add(emission, traceRay(newRay, depth + 1));
        }

        return new Vec3();
    }

    final private Vec3 fresnelSchlick(double cosThetaWo, final double iorI, final double iorT, final double signal) {
        if (signal == -1.0) {
            double etaRatio = iorI / iorT;
            double a = 1 - etaRatio * etaRatio * (1.0 - cosThetaWo * cosThetaWo);
            cosThetaWo = Math.sqrt(a);
        }

        double r0 = (iorI - iorT) / (iorI + iorT);
        r0 = r0 * r0;
        double cosThetaWo_5 = 1.0f - cosThetaWo;
        cosThetaWo_5 = cosThetaWo_5 * cosThetaWo_5 * cosThetaWo_5 * cosThetaWo_5 * cosThetaWo_5;

        return new Vec3(r0 + (1.0 - r0) * cosThetaWo_5);
    }

    final private Camera camera;
    final private Scene scene;
    final private int samples;
    final private ImageBuffer imageBuffer;
    final private int line;
    private java.util.Random r;
    private DecimalFormat df;
}
