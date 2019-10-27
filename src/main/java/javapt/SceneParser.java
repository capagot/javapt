package javapt;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public final class SceneParser {
    SceneParser(final String inFileName) {
        try (FileReader reader = new FileReader(inFileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            camera = (JSONObject) jsonObject.get("camera");
            scene = (JSONArray) jsonObject.get("scene");
        } catch (Exception e) {
            System.out.println("ERROR: Problem reading JSON file.");
            e.printStackTrace();
        }
    }

    public final Vec3 getCameraPosition() {
        return getVec3(camera, "position");
    }

    public final Vec3 getCameraLookAt() {
        return getVec3(camera, "look_at");
    }

    public final Vec3 getCameraUp() {
        return getVec3(camera, "up");
    }

    final double getCameraVFov() {
        return (Double) camera.get("vfov");
    }

    public final int getNumSpheres() {
        return scene.size();
    }

    public final Vec3 getSphereCenter(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        return getVec3(jsonObject, "center");
    }

    public final double getSphereRadius(final int i) {
        final JSONObject jsonObject = (JSONObject) scene.get(i);
        return (Double) jsonObject.get("radius");
    }

    public final Material getSphereMaterial(final int i) {
        final JSONObject JSONObject = (JSONObject) scene.get(i);
        final JSONObject JSONMaterial = (JSONObject) JSONObject.get("material");
        final JSONObject JSONBsdf = (JSONObject) JSONMaterial.get("bsdf");
        final String bsdfType = (String) JSONBsdf.get("type");
        final JSONArray JSONEmission = (JSONArray) JSONMaterial.get("emission");
        final Vec3 emission = getVec3(JSONMaterial, "emission");

        if (bsdfType.compareTo("lambertian") == 0) {
            final Vec3 reflectance = getVec3(JSONBsdf, "reflectance");
            return new Material(emission, new Lambertian(reflectance));            
        } else if (bsdfType.compareTo("smooth conductor") == 0) {
            final Vec3 reflectanceAtNormal = getVec3(JSONBsdf, "reflectance_at_normal");
            return new Material(emission, new SmoothConductor(reflectanceAtNormal));            
        }

        final Double ior = (Double) JSONBsdf.get("ior");
        return new Material(emission, new SmoothDielectric(ior));                    
    }

    private final Vec3 getVec3(final JSONObject jsonObject, final String fieldName) {
        final JSONArray jsonArray = (JSONArray) jsonObject.get(fieldName);

        if (jsonArray.size() != 3)
            return new Vec3();

        return new Vec3((Double) jsonArray.get(0), (Double) jsonArray.get(1), (Double) jsonArray.get(2));
    }

    private JSONObject camera;
    private JSONArray scene;
}
