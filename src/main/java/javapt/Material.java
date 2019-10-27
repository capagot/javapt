package javapt;

public final class Material {
    public Material(final Vec3 emission, final BSDF bsdf) {
        this.emission = emission;
        this.bsdf = bsdf;
    }

    public final Vec3 getEmission() {
        return emission;
    }

    public final BSDF getBSDF() {
        return bsdf;
    }

    private final Vec3 emission;
    private final BSDF bsdf;
}
