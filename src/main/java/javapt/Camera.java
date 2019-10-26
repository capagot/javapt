package javapt;

/**
 * Represents the camera. 
 */
final public class Camera {
    /**
    * Creates a camera with the specified position, look at, up vector, vertical field of view.
    * @param position Position of the camera. 
    * @param lookAt Point where the camera is aiming at.
    * @param up Vector the points to the camera's up direction. 
    * @param vFov Vertical fielf of view, in degrees. 
    * @param imageWidth Image width in pixels. 
    * @param imageHeight Image height in pixels. 
    */    
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

    /**
    * Generates a ray, in world space, given the correspoding image pixel coordinates. 
    * @param sample 2D coordinates of the sample.
    */    
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
