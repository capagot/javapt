package javapt;

final public class Application {
    public Application(final String[] str) {
        CmdLineParser cmd = new CmdLineParser(str);

        final int imageWidth = cmd.getValue("-w", 400, Integer.class);
        final int imageHeight = cmd.getValue("-h", 300, Integer.class);
        final int spp = cmd.getValue("-s", 4, Integer.class);
        final String inFileName = cmd.getValue("-f", "file_no_informed.txt", String.class);
        final int numThreads = cmd.getValue("-t", 1, Integer.class);

        System.out.println("Image size .........: " + imageWidth + " x " + imageHeight + " pixels");
        System.out.println("Samples per pixel ..: " + spp);
        System.out.println("Number of threads ..: " + numThreads);
        System.out.println("Input file name ....: " + inFileName);

        camera = new Camera(new Vec3(0.0, 0.0, 9.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), 35.0, imageWidth, imageHeight);
        scene = new Scene();
        imageBuffer = new ImageBuffer(imageWidth, imageHeight, "image.ppm");
        integrator = new Integrator(camera, scene, spp, imageBuffer, numThreads);
    }

    final public void run() {
        integrator.render();
        imageBuffer.saveBufferToFile();

        System.out.printf("%nRendering finished!%n");        
    }

    final private Camera camera;
    final private Scene scene;
    final private ImageBuffer imageBuffer;
    final private Integrator integrator;
}
