package javapt;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

final public class Integrator {
    public Integrator(final Camera camera, final Scene scene, final int samples, final ImageBuffer imageBuffer) {
        this.camera = camera;
        this.scene = scene;
        this.samples = samples;
        this.imageBuffer = imageBuffer;
    }

    final public void render() {
        System.out.printf("spp: %d%n", samples);

        final ExecutorService executorService = Executors.newFixedThreadPool(4);

        for(int y = 0; y < camera.imageHeight; ++y)
            executorService.submit(new RenderTask(camera, scene, samples, imageBuffer, y));

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(72, TimeUnit.HOURS))
                System.err.println("Threads didn't finish in 72 hours!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    final private Camera camera;
    final private Scene scene;
    final private int samples;
    final private ImageBuffer imageBuffer;
}
