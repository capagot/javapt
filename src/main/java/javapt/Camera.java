package javapt;

final public class Camera {
    public Camera(final Vec3 position, final Vec3 lookAt, final Vec3 up, final double vFov, final int imageWidth, final int imageHeight) {
        this.position = position;
        Vec3 direction = Vec3.normalize(Vec3.sub(lookAt, this.position));
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.onb = new ONB(Vec3.normalize(Vec3.cross(up, Vec3.mul(direction, -1.0))), Vec3.mul(direction, -1.0));

        double a = this.imageWidth * 0.5;
        double b = this.imageHeight * 0.5;
        double viewPlaneDistance = b / Math.tan(vFov * 0.5 * Math.PI / 180.0);
        localUpperLeftCorner = new Vec3(-a, b, -viewPlaneDistance);
    }

    final public Ray getRay(final Vec2 sample) {
        Vec3 localRayDirection = Vec3.normalize(Vec3.add(localUpperLeftCorner, new Vec3(sample.x, -sample.y, 0.0)));
        Vec3 worldRayDirection = Mat3x3.mul(onb.getLocalToWorldMatrix(), localRayDirection);

        return new Ray(position, worldRayDirection);
    }

    final public int imageWidth;
    final public int imageHeight;

    final private Vec3 position;
    final private Vec3 localUpperLeftCorner;
    final private ONB onb;
}
