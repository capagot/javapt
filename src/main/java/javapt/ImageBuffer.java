package javapt;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

final public class ImageBuffer {
    public ImageBuffer(final int imageWidth, final int imageHeight, final String fileName) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.fileName = fileName;
        this.imageBuffer = new Vec3[this.imageHeight][this.imageWidth];

        for(int y = 0; y < this.imageHeight; ++y)
            for(int x = 0; x < this.imageWidth; ++x)
                imageBuffer[y][x] = new Vec3(0.0, 0.0, 0.0);
    }

    final public void setPixel(final int x, final int y, final Vec3 color) {
        imageBuffer[y][x] = color;
    }

    final public Vec3 getPixel(final int x, final int y) {
        return imageBuffer[y][x];
    }

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
