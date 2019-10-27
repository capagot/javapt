package javapt;

public final class Lambertian extends BSDF {
    public Lambertian(final Vec3 reflectance) {
        super(BSDFType.LAMBERTIAN);
        this.reflectance = reflectance;
    }

    @Override
    public final BSDFType getType() {
        return bsdfType;
    }

    @Override
    public final Vec3 evalPartRE(final Vec3 localWo, final Vec3 localWi, final java.util.Random r) {
        final double r1 = r.nextDouble();
        final double r2 = r.nextDouble();
        final double phi = 2.0 * Math.PI * r2;
        final double cosTheta = Math.sqrt(r1);
        final double sqrtSinTheta = Math.sqrt(1.0 - r1);

        localWi.x = Math.cos(phi) * sqrtSinTheta;
        localWi.y = cosTheta;
        localWi.z = Math.sin(phi) * sqrtSinTheta;

        return reflectance;
    }

    private final Vec3 reflectance;
}
