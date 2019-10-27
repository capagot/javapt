package javapt;

public final class SmoothConductor extends BSDF {
    public SmoothConductor(final Vec3 reflectanceAtNormal) {
        super(BSDFType.SMOOTH_CONDUCTOR);
        this.reflectanceAtNormal = reflectanceAtNormal;
    }

    @Override
    public final BSDFType getType() {
        return bsdfType;
    }

    @Override
    public final Vec3 evalPartRE(final Vec3 localWo, final Vec3 localWi, final java.util.Random r) {
        localWi.x = -localWo.x;
        localWi.y = localWo.y;
        localWi.z = -localWo.z;

        return reflectanceAtNormal;
    }

    private final Vec3 reflectanceAtNormal;
}
