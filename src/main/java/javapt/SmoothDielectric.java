package javapt;

public final class SmoothDielectric extends BSDF {
    public SmoothDielectric(final double ior) {
        super(BSDFType.SMOOTH_DIELECTRIC);
        this.ior = ior;
    }

    @Override
    public final BSDFType getType() {
        return bsdfType;
    }

    @Override
    public final Vec3 evalPartRE(final Vec3 localWo, final Vec3 localWi, final java.util.Random r) {
        double iorI = 1.0;
        double iorT = ior;
        double signal = 1.0;

        if (localWo.y < 0.0) {
            final double tmp = iorI;
            iorI = iorT;
            iorT = tmp;
            signal = -1.0;
        }

        final double a = ((iorI * iorI) / (iorT * iorT)) * (1.0 - localWo.y * localWo.y);

        if (a > 1.0) {  // Total internal reflection (TIR)
            localWi.x = -localWo.x;
            localWi.y = localWo.y;
            localWi.z = -localWo.z;                
        } else {
            final Vec3 fresnel = fresnelSchlick(localWo.y, iorI, iorT, signal);
            final double maxReflectance = Vec3.max(fresnel);

            if (r.nextDouble() < maxReflectance) {
                localWi.x = -localWo.x;
                localWi.y = localWo.y;
                localWi.z = -localWo.z;                
            } else {
                final Vec3 n = new Vec3(0.0, 1.0, 0.0);
                final double b = 1.0 - ((iorI * iorI) / (iorT * iorT)) * (1.0 - localWo.y * localWo.y);
                Vec3 tmp = Vec3.normalize(Vec3.sub(Vec3.mul((iorI / iorT), Vec3.sub(Vec3.mul(n, localWo.y), localWo)), Vec3.mul(Vec3.mul(signal, n), Math.sqrt(b))));
                localWi.x = tmp.x;
                localWi.y = tmp.y;
                localWi.z = tmp.z;
            }
        }

        return new Vec3(1.0);
    }

    private final Vec3 fresnelSchlick(final double cosThetaWo, final double iorI, final double iorT, final double signal) {
        double NewCosThetaWo = cosThetaWo;
    
        if (signal == -1.0) {
            final double etaRatio = iorI / iorT;
            final double a = 1 - etaRatio * etaRatio * (1.0 - cosThetaWo * cosThetaWo);
            NewCosThetaWo = Math.sqrt(a);
        }

        final double r0 = (iorI - iorT) / (iorI + iorT);
        final double r02 = r0 * r0;
        final double cosThetaWoCompl = 1.0f - NewCosThetaWo;
        final double cosThetaWo5 = cosThetaWoCompl * cosThetaWoCompl * cosThetaWoCompl * cosThetaWoCompl * cosThetaWoCompl;

        return new Vec3(r02 + (1.0 - r02) * cosThetaWo5);
    }

    private final double ior;
}
