package javapt;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class SceneParser {
    SceneParser(final String inFileName) {
        try (FileReader reader = new FileReader(inFileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            this.camera = (JSONObject) jsonObject.get("camera");
            this.scene = (JSONArray) jsonObject.get("scene");
        } catch (Exception e) {
            System.out.println("ERROR: Problem reading JSON file.");
        }
    }

    final public Vec3 getCameraPosition() {
        return getVec3(camera, "position");
    }

    final public Vec3 getCameraLookAt() {
        return getVec3(camera, "look_at");
    }

    final public Vec3 getCameraUp() {
        return getVec3(camera, "up");
    }

    final double getCameraVFov() {
        return (Double) camera.get("vfov");
    }

    final public int getNumSpheres() {
        return scene.size();
    }

    final public Vec3 getSphereCenter(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        return getVec3(jsonObject, "center");
    }

    final public double getSphereRadius(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        return (Double) jsonObject.get("radius");
    }

    final public Vec3 getSphereReflectance(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        final JSONArray jsonArray = (JSONArray) jsonObject.get("reflectance");
        if (jsonArray.size() != 3)
            return new Vec3();
        else
            return getVec3(jsonObject, "reflectance");
    }

    final public Vec3 getSphereEmission(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        final JSONArray jsonArray = (JSONArray) jsonObject.get("emission");
        if (jsonArray.size() != 3)
            return new Vec3();
        else
            return getVec3(jsonObject, "emission");
    }

    final public BSDF getSphereBSDF(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        String bsdf = (String) jsonObject.get("bsdf");

        if (bsdf.compareTo("lambertian") == 0)
            return BSDF.LAMBERTIAN;

        if (bsdf.compareTo("smooth conductor") == 0)
            return BSDF.SMOOTH_CONDUCTOR;

        return BSDF.SMOOTH_DIELECTRIC;
    }

    final private Vec3 getVec3(final JSONObject jsonObject, final String fieldName) {
        final JSONArray jsonArray = (JSONArray) jsonObject.get(fieldName);
        final double x = (Double) jsonArray.get(0);
        final double y = (Double) jsonArray.get(1);
        final double z = (Double) jsonArray.get(2);

        return new Vec3(x, y, z);
    }

    private JSONObject camera;
    private JSONArray scene;
}
