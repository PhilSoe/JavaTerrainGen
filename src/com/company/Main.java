package com.company;



import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Phillip Soetebeer
 */
public class Main {
    final static int SCALE = 11; // The side length of an individual plane = 2 ^ SCALE + 1
    final static int MAX_ALT = 1000; // The Highest point the terrain is allowed to reach
    final static int MIN_ALT = 0; // Floor of the altitude. Do not change.
    final static int WIDTH = 4; // Number of planes which compose the PlaneNap horizontally
    final static int HEIGHT = 2; // Number of planes which compose the PlaneMap vertically

    final static double JITTER = 0.9; // Randomness Factor
    final static int WATER_HEIGHT = 600; // Sea Level for Texturing
    final static int MOUNTAIN_HEIGHT = 950; // Mountain Height for Texturing
    final static int MOUNTAIN_HEIGHT_PEAK = 985; // Mountain Peak Height for Texturing



    public static void main(String[] args){
        PlaneMap map = new PlaneMap(WIDTH, HEIGHT, (int)Math.pow(2, SCALE) + 1);
        File output_file = new File("terrain.png");
        File output_file2 = new File("greyscale.png");
        try {
            ImageIO.write(map.draw(), "png", output_file);
            ImageIO.write(map.drawGreyScale(), "png", output_file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
