package javapt;

final class Sphere {
    public Sphere(final double radius, final Vec3 center, final Vec3 emission, final Vec3 color, final ReflectionType reflType) {
        this.radius = radius;
        this.center = center;
        this.emission = emission;
        this.color = color;
        this.reflType = reflType;
    }

    final public boolean intersect(final Ray ray, IntersectionRecord intRec) {
        double epsilon = 1e-4;
        Vec3 op = Vec3.sub(center, ray.origin);
        double b = Vec3.dot(op, ray.direction);
        double det = (radius * radius) - (Vec3.dot(op, op) - (b * b));

        if (det < 0.0)
            return false;

        det = Math.sqrt(det);
        double t1 = b - det;
        double t2 = b + det;

        if (t1 > epsilon)
            intRec.t = t1;
        else if (t2 > epsilon)
                intRec.t = t2;
        else
            return false;

        intRec.position = Vec3.add(ray.origin, Vec3.mul(ray.direction, intRec.t));
        intRec.normal = Vec3.normalize(Vec3.sub(intRec.position, center));

        return true;
    }

    final public double radius;
    final public Vec3 center;
    final public Vec3 emission;
    final public Vec3 color;
    final public ReflectionType reflType;
}
