package com.company;



import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    final static int SCALE = 11;
    final static int MAX_ALT = 1000;
    final static int MIN_ALT = 0;
    final static int WIDTH = 4;
    final static int HEIGHT = 4;

    final static double JITTER = 0.9;
    final static int WATER_HEIGHT = 600;
    final static int MOUNTAIN_HEIGHT = 950;
    final static int MOUNTAIN_HEIGHT_PEAK = 985;



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
