package javapt;

/**
 * Represents the camera. 
 */
public final class Camera {
    /**
    * Creates a camera with the specified position, look at, up vector and vertical field of view.
    * @param position Position of the camera. 
    * @param lookAt Point where the camera is aiming at.
    * @param up Vector that points to the camera's up direction. 
    * @param vFov Vertical fielf of view, in degrees. 
    * @param imageWidth Image width in pixels. 
    * @param imageHeight Image height in pixels. 
    */    
    public Camera(final Vec3 position, final Vec3 lookAt, final Vec3 up, final double vFov, final int imageWidth, final int imageHeight) {
        this.position = position;
        final Vec3 direction = Vec3.normalize(Vec3.sub(lookAt, this.position));
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        onb = new ONB(Vec3.normalize(Vec3.cross(up, Vec3.mul(direction, -1.0))), Vec3.mul(direction, -1.0));

        final double a = this.imageWidth * 0.5;
        final double b = this.imageHeight * 0.5;
        final double viewPlaneDistance = b / Math.tan(vFov * 0.5 * Math.PI / 180.0);
        localUpperLeftCorner = new Vec3(-a, b, -viewPlaneDistance);
    }

    /**
    * Generates a ray, in world space, given the correspoding image pixel coordinates. 
    * @param sample 2D coordinates of the sample.
    * @return A ray with origin at the camera position and a normalized direction vector aiming at the input sample.
    */    
    public final Ray getRay(final Vec2 sample) {
        Vec3 localRayDirection = Vec3.normalize(Vec3.add(localUpperLeftCorner, new Vec3(sample.x, -sample.y, 0.0)));
        Vec3 worldRayDirection = Mat3x3.mul(onb.getLocalToWorldMatrix(), localRayDirection);

        return new Ray(position, worldRayDirection);
    }

    public final int getImageWidth() {
        return imageWidth;
    }

    public final int getImageHeight() {
        return imageHeight;
    }

    private final int imageWidth;
    private final int imageHeight;
    private final Vec3 position;
    private final Vec3 localUpperLeftCorner;
    private final ONB onb;
}
