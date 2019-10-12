package javapt;

class IntersectionRecord {
	IntersectionRecord() {
        this.t = Double.POSITIVE_INFINITY;
        this.position = new Vec3();
        this.normal = new Vec3();
	}
	
	public IntersectionRecord(IntersectionRecord int_rec) {
		this.t = int_rec.t;
		this.position = int_rec.position;
		this.normal = int_rec.normal;    
	}
	
    public double t;
    public Vec3 position;
    public Vec3 normal;    
    public int id;    
}
