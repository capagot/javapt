package javapt;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

final public class Integrator {
	Integrator(final Camera camera, final Scene scene, final int samples, final ImageBuffer image_buffer) {
		this.camera = camera;
		this.scene = scene;
		this.samples = samples;
		this.image_buffer = image_buffer;		
	}
	
	final void render() {
        System.out.printf("spp: %d%n", samples);

    	ExecutorService executor_service = Executors.newFixedThreadPool(4);

    	for(int y = 0; y < camera.y_res; ++y)
    		executor_service.submit(new RenderTask(camera, scene, samples, image_buffer, y));
    	    	
    	executor_service.shutdown();
    	
        try {
            if (!executor_service.awaitTermination(72, TimeUnit.HOURS))
                System.err.println("Threads didn't finish in 72 hours!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }            
	}

	Camera camera;
	Scene scene;
	int samples;
	ImageBuffer image_buffer;
}
