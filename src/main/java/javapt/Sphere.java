package javapt;

class Sphere {
    public Sphere(final double radius, final Vec3 position, final Vec3 emission, final Vec3 color, final ReflectionType refl_type) {
        this.radius = radius;
        this.position = position;
        this.emission = emission;
        this.color = color;
        this.refl_type = refl_type;
    }

    public boolean intersect(final Ray ray, IntersectionRecord int_rec) {
        double epsilon = 1e-4;
        Vec3 op = Vec3.sub(position, ray.origin);
        double b = Vec3.dot(op, ray.direction);
        double det = (radius * radius) - (Vec3.dot(op, op) - (b * b));

        if (det < 0.0)
            return false;

        det = Math.sqrt(det);
        double t1 = b - det;
        double t2 = b + det;        

        if (t1 > epsilon)
            int_rec.t = t1;
        else if (t2 > epsilon)
                int_rec.t = t2;
        else
            return false;

        int_rec.position = Vec3.add(ray.origin, Vec3.mul(ray.direction, int_rec.t));
        int_rec.normal = Vec3.normalize(Vec3.sub(int_rec.position, position));
        
        return true;
    }

    final public double radius;
    final public Vec3 position;
    final public Vec3 emission;
    final public Vec3 color;
    final public ReflectionType refl_type;
}
