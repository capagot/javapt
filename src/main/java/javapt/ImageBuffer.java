package javapt;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This class contains the image buffer that will store the rendered image.
 * It has methods for setting and retrieving pixel colors as well as saving
 * image buffer contents to a file in PPM format. 
 */
final public class ImageBuffer {
    /** 
     * Creates the ImageBuffer object accordint to the informed parameters.
     * @param imageWidth Image buffer width in pixels.
     * @param imageHeight Image buffer height in pixels.
     * @param fileName Name of the file used to stores the image buffer contents.
     */    
    public ImageBuffer(final int imageWidth, final int imageHeight, final String fileName) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.fileName = fileName;
        this.imageBuffer = new Vec3[this.imageHeight][this.imageWidth];

        for(int y = 0; y < this.imageHeight; ++y)
            for(int x = 0; x < this.imageWidth; ++x)
                imageBuffer[y][x] = new Vec3(0.0, 0.0, 0.0);
    }

    /** 
     * Set the color of the image buffer pixel located at coordinates x and y.
     * @param x Column of the pixel to be set.
     * @param y Row of the pixel to be set.
     * @param color Color, in RGB format, to be stores at the pixel.
     */    
    final public void setPixel(final int x, final int y, final Vec3 color) {
        imageBuffer[y][x] = color;
    }

    /** 
     * Retrieves the color of the image buffer pixel located at coordinates x and y.
     * @param x Column of the pixel to be read.
     * @param y Row of the pixel to be read.
     */    
    final public Vec3 getPixel(final int x, final int y) {
        return imageBuffer[y][x];
    }

    /** 
     * Saves the contents of the image buffer ina PPM file.
     */    
    final public void saveBufferToFile() {
        try (FileWriter fwriter = new FileWriter(fileName);
            BufferedWriter bwriter = new BufferedWriter(fwriter)) {

            bwriter.write("P3\n" + imageWidth + " " + imageHeight + "\n" + 255 + "\n");

            for(int y = 0; y < imageHeight; ++y)
                for(int x = 0; x < imageWidth; ++x) {
                    int r = (int) (Math.pow(clamp(imageBuffer[y][x].x), 0.45454545) * 255.0 + 0.5);
                    int g = (int) (Math.pow(clamp(imageBuffer[y][x].y), 0.45454545) * 255.0 + 0.5);
                    int b = (int) (Math.pow(clamp(imageBuffer[y][x].z), 0.45454545) * 255.0 + 0.5);

                    bwriter.write(r + " " + g + " " + b + " ");
                }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    final private double clamp(final double a) {
        if (a > 1.0)
            return 1.0;

        return a;
    }

    final private int imageWidth;
    final private int imageHeight;
    final private String fileName;
    final public Vec3[][] imageBuffer;
}
