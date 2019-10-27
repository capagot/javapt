package javapt;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public final class Integrator {
    public Integrator(final Camera camera, final Scene scene, final int samples, final ImageBuffer imageBuffer, final int numThreads) {
        this.camera = camera;
        this.scene = scene;
        this.samples = samples;
        this.imageBuffer = imageBuffer;
        this.numThreads = numThreads;
    }

    public final void render() {
        final ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

        for(int y = 0; y < camera.getImageHeight(); ++y)
            executorService.submit(new RenderTask(camera, scene, samples, imageBuffer, y));

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(72, TimeUnit.HOURS))
                System.err.println("Threads didn't finish in 72 hours!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final Camera camera;
    private final Scene scene;
    private final int samples;
    private final ImageBuffer imageBuffer;
    private final int numThreads;
}
