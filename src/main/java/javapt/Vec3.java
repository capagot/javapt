package javapt;

public class Vec3 {
    public Vec3() {}

    public Vec3(final double a) {
        this.x = a;
        this.y = a;
        this.z = a;
    }

    public Vec3(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vec3 add(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vec3 sub(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vec3 mul(final Vec3 a, final Vec3 b) {
        return new Vec3(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public static Vec3 mul(final Vec3 a, final double b) {
        return new Vec3(a.x * b, a.y * b, a.z * b);
    }

    public static Vec3 div(final Vec3 a, final double b) {
        return new Vec3(a.x / b, a.y / b, a.z / b);
    }

    public static double dot(final Vec3 a, final Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static double norm(final Vec3 a) {
        return Math.sqrt(Vec3.dot(a, a));
    }

    public static Vec3 normalize(final Vec3 a) {
        double factor = (1.0 / Vec3.norm(a));
        return new Vec3(a.x * factor, a.y * factor, a.z * factor);
    }

    public static Vec3 cross(final Vec3 a, final Vec3 b) {
        return new Vec3(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    public double x;
    public double y;
    public double z;
}
