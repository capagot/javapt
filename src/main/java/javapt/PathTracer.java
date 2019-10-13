package javapt;

final public class PathTracer {
    public static void main(String[] str) {
        int imageWidth = 400;
        int imageHeight = 300;
        int samples = 16;
        Camera camera = new Camera(new Vec3(0.0, 0.0, 9.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), 35.0, imageWidth, imageHeight);
        Scene scene = new Scene();
        ImageBuffer imageBuffer = new ImageBuffer(imageWidth, imageHeight, "image.ppm");
        Integrator integrator = new Integrator(camera, scene, samples, imageBuffer);

        integrator.render();        
        imageBuffer.saveBufferToFile();
        
        System.out.printf("%nRendering finished!%n");
    }
}
