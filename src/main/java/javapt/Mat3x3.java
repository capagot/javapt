package javapt;

public class Mat3x3 {
    public Mat3x3() {
        m = new Vec3[3];
        m[0] = new Vec3();
        m[1] = new Vec3();
        m[2] = new Vec3();
    }

    public static final Vec3 mul(final Mat3x3 m, final Vec3 a) {
        return new Vec3(Vec3.dot(m.m[0], a), Vec3.dot(m.m[1], a), Vec3.dot(m.m[2], a));
    }

    public static final Mat3x3 transpose(final Mat3x3 r) {
        Mat3x3 l = new Mat3x3();

        l.m[0].x = r.m[0].x;
        l.m[0].y = r.m[1].x;
        l.m[0].z = r.m[2].x;

        l.m[1].x = r.m[0].y;
        l.m[1].y = r.m[1].y;
        l.m[1].z = r.m[2].y;

        l.m[2].x = r.m[0].z;
        l.m[2].y = r.m[1].z;
        l.m[2].z = r.m[2].z;

        return l;
    }

    public final Vec3[] m;
}
