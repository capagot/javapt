package javapt;

/**
 * The Application class is the main class of the renderer. It loads data, initializes the main objects, starts the
 * rendering and stores the resulting image to the disk.
 * @author Christian Azambuja Pagot
 */
final public class Application {
    /**
     * The Application construtor is responsible for initializing the main objects of the renderer.
     * @param str the command line options string array.
     */
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


        final SceneParser sceneParser = new SceneParser(inFileName);

        Vec3 camPosition = sceneParser.getCameraPosition();
        System.out.println("cam pos: " + camPosition.x + ", " + camPosition.y + ", " + camPosition.z);
        Vec3 camLookAt = sceneParser.getCameraLookAt();
        System.out.println("cam lookAt: " + camLookAt.x + ", " + camLookAt.y + ", " + camLookAt.z);
        Vec3 camUp = sceneParser.getCameraUp();
        System.out.println("cam up: " + camUp.x + ", " + camUp.y + ", " + camUp.z);
        double camVFov = sceneParser.getCameraVFov();
        System.out.println("cam vfov: " + camVFov);

        camera = new Camera(camPosition, camLookAt, camUp, camVFov, imageWidth, imageHeight);
        imageBuffer = new ImageBuffer(imageWidth, imageHeight, "image.ppm");
        scene = new Scene();

        int numSpheres = sceneParser.getNumSpheres();
        System.out.println("num spheres: " + numSpheres);

        for (int i = 0; i < numSpheres; ++i) {
            System.out.println("-- sphere " + i + " --------");
            Vec3 center = sceneParser.getSphereCenter(i);
            System.out.println("  center: " + center.x + ", " + center.y + ", " + center.z);
            double radius = sceneParser.getSphereRadius(i);
            System.out.println("  radius: " + radius);
            Vec3 reflectance = sceneParser.getSphereReflectance(i);
            System.out.println("  reflectance: " + reflectance.x + ", " + reflectance.y + ", " + reflectance.z);
            Vec3 emission = sceneParser.getSphereEmission(i);
            System.out.println("  emission: " + emission.x + ", " + emission.y + ", " + emission.z);

            BSDF bsdf = sceneParser.getSphereBSDF(i);
            if (bsdf == BSDF.LAMBERTIAN)
                System.out.println("  bsdf: lambertian");
            else if (bsdf == BSDF.SMOOTH_CONDUCTOR)
                System.out.println("  bsdf: smooth conductor");
            else if (bsdf == BSDF.SMOOTH_DIELECTRIC)
                System.out.println("  bsdf: smooth dielectric");

            // Sphere: radius, position, emission, color, material
            scene.addSphere(new Sphere(radius, center, emission, reflectance, bsdf));
        }

        integrator = new Integrator(camera, scene, spp, imageBuffer, numThreads);

//        camera = new Camera(new Vec3(0.0, 0.0, 8.0), new Vec3(0.0, 0.0, 0.0), new Vec3(0.0, 1.0, 0.0), 35.0, imageWidth, imageHeight);
//        scene = new Scene();
//        imageBuffer = new ImageBuffer(imageWidth, imageHeight, "image.ppm");
//        integrator = new Integrator(camera, scene, spp, imageBuffer, numThreads);

    }

    /**
     * The run method starts the rendering and saves the resulting image to the disk once it is finished.
     */
    final public void run() {
        integrator.render();
        imageBuffer.saveBufferToFile();

        System.out.printf("%nRendering finished!%n");        
    }

    private Camera camera;
    private Scene scene;
    private ImageBuffer imageBuffer;
    private Integrator integrator;
}
