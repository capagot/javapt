package javapt;

public class PathTracer {
    public static void main(String[] str) {
        int width = 400;
        int height = 300;
        int samples = 16;
        Camera camera = new Camera(new Vec3(0.0, 0.0, 9.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), 35.0, width, height);
        Scene scene = new Scene();
        ImageBuffer image_buffer = new ImageBuffer(width, height, "image.ppm");
        Integrator integrator = new Integrator(camera, scene, samples, image_buffer);

        integrator.render();        
        image_buffer.saveBufferToFile();
        
        System.out.printf("%nRendering finished!%n");
    }
}
