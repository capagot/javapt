package javapt;

/**
 * BSDF stands for Bidirectional Scattering Distribution Function, the function that describes
 * how a given material reflects light, ultimately describing its visual appearance.
 * The BSDF asbtract class defines the common interface that the different materials must expose
 * in order to be used by the renderer.
 */
abstract public class BSDF {
    /** Initializes a BSDF, setting up its type.
     * @param bsdfType describes the type of the BSDF. The currently accepted types are:
     * LAMBERTIAN, SMOOTH_CONDUCTOR and SMOOTH_DIELECTRIC.
     */
    public BSDF(final BSDFType bsdfType) {
        this.bsdfType = bsdfType;
    }

    /**
     * Returns the type of the BSDF.
     * @return the type of the BSDF.
     */
    public BSDFType getType() {
        return bsdfType;
    }

    /**
     * Evaluates the part of the rendering equation specificaly related to the BSDF.
     * @param localWo a Vec3 describing the outgoing light direction.
     * @param localWi Aa Vec3 describing the incoming light direction.
     * @param r a pseudo random number generator (PRNG) object. It is expected the
     * PRNG to generate uniformly distributed real numbers in the range [0, 1).
     * @return returns the constant of proportionality between the irradiance and outgoing radiance
     * for the gven pair of light directions.
     */
    abstract public Vec3 evalPartRE(final Vec3 localWo, Vec3 localWi, final java.util.Random r);

    protected final BSDFType bsdfType;
}
