package javapt;

class Scene {
    public Scene() {
        primitives = new Sphere[6];
        
        // Scene: radius, position, emission, color, material
        primitives[0] = new Sphere(10.0, new Vec3(0.0, 11.97, 0.0), new Vec3(10.0, 10.0, 10.0), new Vec3(), ReflectionType.DIFFUSE); // Light
        //primitives[0] = new Sphere(0.1, new Vec3(0.0, 2.0, 0.0), new Vec3(1.5, 1.5, 1.5), new Vec3(), ReflectionType.DIFFUSE); // Light
        primitives[1] = new Sphere(10000.0, new Vec3(0.0, -10002.0, 0.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Bottom
        primitives[2] = new Sphere(10000.0, new Vec3(-10002.5, 0.0, 0.0), new Vec3(), new Vec3(0.75, 0.25, 0.25), ReflectionType.DIFFUSE);  // Left
        primitives[3] = new Sphere(10000.0, new Vec3(10002.5, 0.0, 0.0), new Vec3(), new Vec3(0.25, 0.25, 0.75), ReflectionType.DIFFUSE);  // Right
        primitives[4] = new Sphere(10000.0, new Vec3(0.0, 10002.0, 0.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Top
        primitives[5] = new Sphere(10000.0, new Vec3(0.0, 0.0, -10002.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Back                
    }

    public boolean intersect(final Ray ray, IntersectionRecord int_rec) {
        int_rec.t = Double.POSITIVE_INFINITY;
        IntersectionRecord tmp_int_rec = new IntersectionRecord();
        boolean int_result = false;

        for(int i = 0; i < primitives.length; ++i) {
            if (primitives[i].intersect(ray, tmp_int_rec)) {
            	if ((tmp_int_rec.t < int_rec.t) && (tmp_int_rec.t > 0.0)) {
            		int_rec.t = tmp_int_rec.t;
            		
            		int_rec.position.x = tmp_int_rec.position.x;
            		int_rec.position.y = tmp_int_rec.position.y;
            		int_rec.position.z = tmp_int_rec.position.z;
            		
            		int_rec.normal.x = tmp_int_rec.normal.x;
            		int_rec.normal.y = tmp_int_rec.normal.y;
            		int_rec.normal.z = tmp_int_rec.normal.z;
            		
            		int_rec.id = i;
            		int_result = true;
            	}
            }
        }

        return int_result;
    }

    public Sphere primitives[];
}
