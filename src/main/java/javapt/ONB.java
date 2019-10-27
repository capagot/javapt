package javapt;

public class ONB {
    public ONB(final Vec3 v) {
        this.v = v;

        if (Math.abs(this.v.x) > Math.abs(this.v.y))
            w = Vec3.normalize(new Vec3(this.v.z, 0.0, -this.v.x));
        else
            w = Vec3.normalize(new Vec3(0.0, -this.v.z, this.v.y));

        u = Vec3.cross(this.v, w);

        localToWorldMatrix = new Mat3x3();
        worldToLocalMatrix = new Mat3x3();

        setBasisMatrix();
    }

    public ONB(final Vec3 u, final Vec3 w) {
        this.u = u;
        this.w = w;
        v = Vec3.cross(w, u);

        localToWorldMatrix = new Mat3x3();
        worldToLocalMatrix = new Mat3x3();

        setBasisMatrix();
    }

    public final Mat3x3 getLocalToWorldMatrix() {
        return localToWorldMatrix;
    }

    public final Mat3x3 getWorldToLocalMatrix() {
        return worldToLocalMatrix;
    }

    private final void setBasisMatrix() {
        worldToLocalMatrix.m[0] = u;
        worldToLocalMatrix.m[1] = v;
        worldToLocalMatrix.m[2] = w;
        localToWorldMatrix = Mat3x3.transpose(worldToLocalMatrix);
    }

    private final Vec3 u;
    private final Vec3 v;
    private final Vec3 w;
    private Mat3x3 localToWorldMatrix;
    private Mat3x3 worldToLocalMatrix;
};
