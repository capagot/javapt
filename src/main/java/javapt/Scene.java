package javapt;

import java.util.ArrayList;

final public class Scene {
    public Scene() {
        primitives = new ArrayList<Sphere>();
    }

    final public void addSphere(final Sphere sphere) {
        primitives.add(sphere);
    }

    final public boolean intersect(final Ray ray, IntersectionRecord intRec) {
        intRec.t = Double.POSITIVE_INFINITY;
        final IntersectionRecord tmpIntRec = new IntersectionRecord();
        boolean intResult = false;

        for(int i = 0; i < primitives.size(); ++i) {
            if (primitives.get(i).intersect(ray, tmpIntRec)) {
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

    public ArrayList<Sphere> primitives;
}
