package javapt;

import java.io.*;

class ImageBuffer {
    public ImageBuffer(final int width, final int height, final String filename) {
        this.width = width;
        this.height = height;
        this.filename = filename;
        image_buffer = new Vec3[this.height][this.width];

        for(int y = 0; y < height; ++y)
            for(int x = 0; x < width; ++x)
                image_buffer[y][x] = new Vec3(0.0, 0.0, 0.0);
    }

    public void setPixel(final int x, final int y, final Vec3 a) {
        image_buffer[y][x] = a;
    }

    public Vec3 getPixel(final int x, final int y) {
        return image_buffer[y][x];
    }

    public void saveBufferToFile() {
        try (FileWriter fwriter = new FileWriter(filename);
            BufferedWriter bwriter = new BufferedWriter(fwriter)) {

            bwriter.write("P3\n" + width + " " + height + "\n" + 255 + "\n");

            for(int y = 0; y < height; ++y)
                for(int x = 0; x < width; ++x) {
                    int r = (int) (Math.pow(clamp(image_buffer[y][x].x), 0.45454545) * 255.0 + 0.5);
                    int g = (int) (Math.pow(clamp(image_buffer[y][x].y), 0.45454545) * 255.0 + 0.5);
                    int b = (int) (Math.pow(clamp(image_buffer[y][x].z), 0.45454545) * 255.0 + 0.5);

                    bwriter.write(r + " " + g + " " + b + " ");
                }                
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }                
    }

    private double clamp(double a) {
    	if (a > 1.0)
    		return 1.0;
    	
    	return a;
    }
    
    final private int width;
    final private int height;
    final private String filename;
    final public Vec3[][] image_buffer;
}
