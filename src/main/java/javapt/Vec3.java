package javapt;

public final class Vec3 {
    public Vec3() {}

    public Vec3(final double a) {
        x = a;
        y = a;
        z = a;
    }

    public Vec3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final Vec3 add(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static final Vec3 sub(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static final Vec3 sub(final double a, final Vec3 b) {
        return new Vec3(a - b.x, a - b.y, a - b.z);
    }

    public static final Vec3 mul(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public static final Vec3 mul(final Vec3 a, final double b) {
        return new Vec3(a.x * b, a.y * b, a.z * b);
    }

    public static final Vec3 mul(final double a, final Vec3 b) {
        return new Vec3(a * b.x, a * b.y, a * b.z);
    }

    public static final Vec3 div(final Vec3 a, final double b) {
        return new Vec3(a.x / b, a.y / b, a.z / b);
    }

    public static final double dot(final Vec3 a, final Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static final double norm(final Vec3 a) {
        return Math.sqrt(Vec3.dot(a, a));
    }

    public static final Vec3 normalize(final Vec3 a) {
        double factor = (1.0 / Vec3.norm(a));
        return new Vec3(a.x * factor, a.y * factor, a.z * factor);
    }

    public static final Vec3 cross(final Vec3 a, final Vec3 b) {
        return new Vec3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    public static final double max(final Vec3 a) {
        return Math.max(Math.max(a.x, a.y), a.z);
    }

    public double x;
    public double y;
    public double z;
}
