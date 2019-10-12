package javapt;

public class RenderTask implements Runnable {
	public RenderTask(final Camera camera,  final Scene scene, final int samples, final ImageBuffer image_buffer, final int line) {
		this.camera = camera;
		this.scene = scene;
		this.samples = samples;
		this.image_buffer = image_buffer;		
		this.line = line;	
	    this.r = new java.util.Random(this.line);
	}
	
	@Override
	public void run() {	  

    	for(int x = 0; x < camera.x_res; ++x) {
        	for(int s = 0; s < samples; ++s) {
            	final Vec2 pixel_sample = new Vec2(x + r.nextDouble(), this.line + r.nextDouble());                	
                Ray ray = camera.getRay(pixel_sample);
                image_buffer.setPixel(x, this.line, Vec3.add(image_buffer.getPixel(x, this.line), traceRay(ray, 0)));
            }

            image_buffer.setPixel(x, this.line, Vec3.div(image_buffer.getPixel(x, this.line), samples));                		
        }            	
	}

    final Vec3 traceRay(final Ray ray, final int depth) {
        IntersectionRecord int_rec = new IntersectionRecord();
    	
        if (!scene.intersect(ray, int_rec) || (depth > 5))
            return new Vec3();
        
        ONB onb = new ONB(Vec3.normalize(int_rec.normal));

        if (scene.primitives[int_rec.id].refl_type == ReflectionType.DIFFUSE) {
        	double r1 = r.nextDouble();
        	double r2 = r.nextDouble();        	        	
            double phi = 2.0 * Math.PI * r2;
            double sqrt_sin_theta = Math.sqrt(1.0 - r1 * r1);
            
        	Vec3 local_wi = new Vec3(Math.cos(phi) * sqrt_sin_theta, r1, Math.sin(phi) * sqrt_sin_theta);
        	Vec3 world_wi = Mat3x3.mul(onb.getLocalToWorldMatrix(), local_wi);
        	Ray new_ray = new Ray(Vec3.add(int_rec.position, Vec3.mul(world_wi, 1e-5)), world_wi);
        	
        	Vec3 brdf = Vec3.div(scene.primitives[int_rec.id].color, Math.PI);
        	double pdf = (2.0 * Math.PI);
        	Vec3 emission = scene.primitives[int_rec.id].emission;
        	
        	return Vec3.add(emission, Vec3.mul(Vec3.mul(Vec3.mul(brdf, traceRay(new_ray, depth + 1)), r1), pdf));
        } else if (scene.primitives[int_rec.id].refl_type == ReflectionType.SPECULAR) {

        			
//            double r1 = r.nextDouble();
//            double r2 = r.nextDouble();
//            double phi = 2.0 * Math.PI * r2;
//            double cos_theta = Math.sqrt(r1);
//            double sqrt_sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);
//            Vec3 local_wi = new Vec3(Math.cos(phi) * sqrt_sin_theta, cos_theta, Math.sin(phi) * sqrt_sin_theta);            
//            double sample_probability = cos_theta / Math.PI;                        
//            Vec3 world_wi = Mat3x3.mul(onb.getLocalToWorldMatrix(), local_wi);               
//            Ray new_ray = new Ray(Vec3.add(int_rec.position, Vec3.mul(world_wi, 1e-3)), world_wi);
//            
//            return Vec3.add(scene.primitives[int_rec.id].emission, Vec3.mul(Vec3.mul(Vec3.div(scene.primitives[int_rec.id].color, Math.PI), traceRay(new_ray, depth + 1)), Math.PI));
        }
        
        return new Vec3();                   
    }

	final Camera camera;
	final Scene scene;
	final int samples;
	final ImageBuffer image_buffer;
	final int line;
	java.util.Random r;
}
