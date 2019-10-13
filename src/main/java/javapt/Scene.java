package javapt;

final public class Scene {
    public Scene() {
        primitives = new Sphere[6];
        
        // Scene: radius, position, emission, color, material
        primitives[0] = new Sphere(10.0, new Vec3(0.0, 11.97, 0.0), new Vec3(10.0, 10.0, 10.0), new Vec3(), ReflectionType.DIFFUSE); // Light
        primitives[1] = new Sphere(10000.0, new Vec3(0.0, -10002.0, 0.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Bottom
        primitives[2] = new Sphere(10000.0, new Vec3(-10002.5, 0.0, 0.0), new Vec3(), new Vec3(0.75, 0.25, 0.25), ReflectionType.DIFFUSE);  // Left
        primitives[3] = new Sphere(10000.0, new Vec3(10002.5, 0.0, 0.0), new Vec3(), new Vec3(0.25, 0.25, 0.75), ReflectionType.DIFFUSE);  // Right
        primitives[4] = new Sphere(10000.0, new Vec3(0.0, 10002.0, 0.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Top
        primitives[5] = new Sphere(10000.0, new Vec3(0.0, 0.0, -10002.0), new Vec3(), new Vec3(0.75, 0.75, 0.75), ReflectionType.DIFFUSE);  // Back                
    }

    final public boolean intersect(final Ray ray, IntersectionRecord intRec) {
        intRec.t = Double.POSITIVE_INFINITY;
        final IntersectionRecord tmpIntRec = new IntersectionRecord();
        boolean intResult = false;

        for(int i = 0; i < primitives.length; ++i) {
            if (primitives[i].intersect(ray, tmpIntRec)) {
            	if ((tmpIntRec.t < intRec.t) && (tmpIntRec.t > 0.0)) {
            		intRec.t = tmpIntRec.t;            		
            		intRec.position = tmpIntRec.position;            		
            		intRec.normal = tmpIntRec.normal;            		
            		intRec.id = i;
            		intResult = true;
            	}
            }
        }

        return intResult;
    }

    public Sphere primitives[];
}
