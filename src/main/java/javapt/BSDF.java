package javapt;

abstract public class BSDF {
    public BSDF(final BSDFType bsdfType) {
        this.bsdfType = bsdfType;
    }

    public BSDFType getType() {
        return bsdfType;
    }

    abstract public Vec3 evalPartRE(final Vec3 localWo, Vec3 localWi, final java.util.Random r);

    protected final BSDFType bsdfType;
}
