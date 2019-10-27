package javapt;

public final class Ray {
    public Ray(final Vec3 origin, final Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public final Vec3 getOrigin() {
        return origin;
    }

    public final Vec3 getDirection() {
        return direction;
    }

    private final Vec3 origin;
    private final Vec3 direction;
}
