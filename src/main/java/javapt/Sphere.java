package javapt;

final class Sphere {
    public Sphere(final double radius, final Vec3 center, final Material material) {
        this.radius = radius;
        this.center = center;
        this.material = material;
    }

    public final boolean intersect(final Ray ray, final IntersectionRecord intRec) {
        final double epsilon = 1e-4;
        final Vec3 op = Vec3.sub(center, ray.getOrigin());
        final double b = Vec3.dot(op, ray.getDirection());
        final double det1 = (radius * radius) - (Vec3.dot(op, op) - (b * b));

        if (det1 < 0.0)
            return false;

        final double det2 = Math.sqrt(det1);
        final double t1 = b - det2;
        final double t2 = b + det2;

        if (t1 > epsilon)
            intRec.t = t1;
        else if (t2 > epsilon)
                intRec.t = t2;
        else
            return false;

        intRec.position = Vec3.add(ray.getOrigin(), Vec3.mul(ray.getDirection(), intRec.t));
        intRec.normal = Vec3.normalize(Vec3.sub(intRec.position, center));

        return true;
    }

    public final Material getMaterial() {
        return material;
    }

    private final double radius;
    private final Vec3 center;
    private final Material material;
}
