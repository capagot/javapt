package javapt;

class ONB {
    public ONB(final Vec3 v) {
        this.v = v;

        if (Math.abs(this.v.x) > Math.abs(this.v.y))
            this.w = Vec3.normalize(new Vec3(this.v.z, 0.0, -this.v.x));
        else
            this.w = Vec3.normalize(new Vec3(0.0, -this.v.z, this.v.y));

        u = Vec3.cross(this.v, this.w);
        
        local_to_world_matrix = new Mat3x3();
        world_to_local_matrix = new Mat3x3();

        setBasisMatrix();        
    }

    public ONB(final Vec3 u, final Vec3 w) {
        this.u = u;
        this.w = w;
        this.v = Vec3.cross(w, u);

        local_to_world_matrix = new Mat3x3();
        world_to_local_matrix = new Mat3x3();

        setBasisMatrix();
    }

    public Mat3x3 getLocalToWorldMatrix() {
        return local_to_world_matrix;
    }

    public Mat3x3 getWorldToLocalMatrix() {
        return world_to_local_matrix;
    }

    private void setBasisMatrix() {
    	world_to_local_matrix.m[0] = u;
    	world_to_local_matrix.m[1] = v;
    	world_to_local_matrix.m[2] = w;
    	local_to_world_matrix = Mat3x3.transpose(world_to_local_matrix);        
    }

    private Vec3 u;
    private Vec3 v;
    private Vec3 w;
    private Mat3x3 local_to_world_matrix;
    private Mat3x3 world_to_local_matrix;
};
