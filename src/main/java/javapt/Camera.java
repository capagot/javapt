package javapt;

class Camera {
    public Camera(Vec3 position, Vec3 look_at, Vec3 up, double v_fov, int x_res, int y_res) {
        this.position = position;
        this.look_at = look_at;
        this.up = up;
        this.direction = Vec3.normalize(Vec3.sub(this.look_at, this.position));
        this.v_fov = v_fov;
        this.x_res = x_res;
        this.y_res = y_res;        
        this.onb = new ONB(Vec3.normalize(Vec3.cross(this.up, Vec3.mul(this.direction, -1.0))), Vec3.mul(this.direction, -1.0));

        double a = x_res * 0.5;
        double b = y_res * 0.5;
        double view_plane_distance = b / Math.tan(v_fov * 0.5 * 3.141592 / 180.0);
        local_upper_left_corner = new Vec3(-a, b, -view_plane_distance);
            
    }

    public Ray getRay(Vec2 sample) {
        Vec3 local_ray_direction = Vec3.normalize(Vec3.add(local_upper_left_corner, new Vec3(sample.x, -sample.y, 0.0)));
        Vec3 world_ray_direction = Mat3x3.mul(onb.getLocalToWorldMatrix(), local_ray_direction);

        assert ((local_ray_direction.x >= -1.0) && (local_ray_direction.x <= 1.0)) : "local_ray_direction.x == " + local_ray_direction.x;
        assert ((local_ray_direction.y >= -1.0) && (local_ray_direction.y <= 1.0)) : "local_ray_direction.y == " + local_ray_direction.y;
        assert ((local_ray_direction.z >= -1.0) && (local_ray_direction.z <= 1.0)) : "local_ray_direction.z == " + local_ray_direction.z;
        assert ((Vec3.norm(local_ray_direction) >= (1.0 - 1e-3)) && (Vec3.norm(local_ray_direction) <= (1.0 + 1e-3))) : "length == " + Vec3.norm(local_ray_direction);
        assert ((world_ray_direction.x >= -1.0) && (world_ray_direction.x <= 1.0)) : "world_ray_direction.x == " + world_ray_direction.x;
        assert ((world_ray_direction.y >= -1.0) && (world_ray_direction.y <= 1.0)) : "world_ray_direction.y == " + world_ray_direction.y;
        assert ((world_ray_direction.z >= -1.0) && (world_ray_direction.z <= 1.0)) : "world_ray_direction.z == " + world_ray_direction.z;
        assert ((Vec3.norm(world_ray_direction) >= (1.0 - 1e-3)) && (Vec3.norm(world_ray_direction) <= (1.0 + 1e-3))) : "length == " + Vec3.norm(world_ray_direction);

        return new Ray(position, world_ray_direction);
    }

    public Vec3 position;
    public Vec3 look_at;
    public Vec3 up;
    public Vec3 direction;    
    public int x_res;
    public int y_res;
    public double v_fov;

    private Vec3 local_upper_left_corner;
    private ONB onb;
}
