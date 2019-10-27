package javapt;

/**
 * Main application class. It is responsible for creating
 * all other objects needed by the renderer and for starting the rendering
 * process itself.
 */
public final class Application {
    /**
     * Creates the application.
     * @param str String containing the command line options.
     */
    public Application(final String[] str) {
        CmdLineParser cmd = new CmdLineParser(str);

        final int imageWidth = cmd.getValue("-w", 400, Integer.class);
        final int imageHeight = cmd.getValue("-h", 300, Integer.class);
        final int spp = cmd.getValue("-s", 4, Integer.class);
        final String inFileName = cmd.getValue("-f", "input.json", String.class);
        final int numThreads = cmd.getValue("-t", 1, Integer.class);

        System.out.println("Image size .........: " + imageWidth + " x " + imageHeight + " pixels");
        System.out.println("Samples per pixel ..: " + spp);
        System.out.println("Number of threads ..: " + numThreads);
        System.out.println("Input file name ....: " + inFileName);

        final SceneParser sceneParser = new SceneParser(inFileName);
        final Vec3 position = sceneParser.getCameraPosition();
        final Vec3 lookAt = sceneParser.getCameraLookAt();
        final Vec3 up = sceneParser.getCameraUp();
        final double vFov = sceneParser.getCameraVFov();

        System.out.println("Camera:");
        System.out.println("  Position .........: " + position.x + ", " + position.y + ", " + position.z);
        System.out.println("  Look at ..........: " + lookAt.x + ", " + lookAt.y + ", " + lookAt.z);
        System.out.println("  Up ...............: " + up.x + ", " + up.y + ", " + up.z);
        System.out.println("  Vertical FOV .....: " + vFov);

        camera = new Camera(position, lookAt, up, vFov, imageWidth, imageHeight);
        imageBuffer = new ImageBuffer(imageWidth, imageHeight, "image.ppm");
        scene = new Scene();

        final int numSpheres = sceneParser.getNumSpheres();

        System.out.println("Number of spheres ..: " + numSpheres);

        for (int i = 0; i < numSpheres; ++i) {
            final Vec3 center = sceneParser.getSphereCenter(i);
            final double radius = sceneParser.getSphereRadius(i);
            final Material material = sceneParser.getSphereMaterial(i);

            scene.addSphere(new Sphere(radius, center, material));
        }

        integrator = new Integrator(camera, scene, spp, imageBuffer, numThreads);
    }

    /**
     * Starts the rendering. At the end of the rendering process the resulting
     * image is stored in a file.
     */
    public final void run() {
        integrator.render();
        imageBuffer.saveBufferToFile();
        System.out.printf("%nRendering is finished!%n");        
    }

    private final Camera camera;
    private final ImageBuffer imageBuffer;
    private final Scene scene;
    private final Integrator integrator;
}
