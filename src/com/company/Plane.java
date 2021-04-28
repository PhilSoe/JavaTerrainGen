package com.company;


import java.awt.image.BufferedImage;

/**
 * Object containing a heightmap and the ability to draw itself
 */
public class Plane {
    private final int[][] heightmap; // int array for height at a given x, y coordinate

    /**
     * Plane Initializer, each value in the heightmap is initially set to -1 to show it
     * is has not yet been calculated.
     *
     * @param side_length The side length of the plane
     */
    public Plane(int side_length){
        this.heightmap = new int[side_length][side_length];
        for(int x = 0; x < side_length; x++){
            for(int y = 0; y < side_length; y++){
                setValue(x,y,-1);
            }
        }
    }

    /**
     * @param x x coordinate to be returned
     * @param y y coordinate to be returned
     * @return The x y value of the heightmap
     */
    public int getValue(int x, int y){
        return this.heightmap[x][y];
    }

    /**
     * @param x x coordinate to be returned
     * @param y y coordinate to be returned
     * @param value The new Height value to insert
     */
    public void setValue(int x, int y, int value){
        this.heightmap[x][y] = value;
    }

    /**
     * @return The length of the planes side length - 1
     */
    public int getMaxCord(){
        return heightmap.length - 1;
    }

    /**
     * Pre seeds the top of the heightmap.
     *
     * @param side_values The array of length (side_length) to replace the top with
     */
    public void setTopSide(int[] side_values){
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            setValue(x, 0, side_values[x]);
        }
    }

    /**
     * @return The top row of values
     */
    public int[] getTopSide(){
        int[] out = new int[heightmap.length];
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            out[x] = this.getValue(x, 0);
        }
        return out;
    }

    /**
     * Pre seeds the bottom of the heightmap.
     *
     * @param side_values The array of length (side_length) to replace the bottom with
     */
    public void setBottomSide(int[] side_values){
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            setValue(x, this.getMaxCord(), side_values[x]);
        }
    }

    /**
     * @return The bottom row of values
     */
    public int[] getBottomSide(){
        int[] out = new int[heightmap.length];
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            out[x] = this.getValue(x, this.getMaxCord());
        }
        return out;
    }

    /**
     * Pre seeds the left side of the heightmap.
     *
     * @param side_values The array of length (side_length) to replace the left side with
     */
    public void setLeftSide(int[] side_values){
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            setValue(0, y, side_values[y]);
        }
    }

    /**
     * @return The left column of values
     */
    public int[] getLeftSide(){
        int[] out = new int[heightmap.length];
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            out[y] = this.getValue(0, y);
        }
        return out;
    }

    /**
     * Pre seeds the right side of the heightmap.
     *
     * @param side_values The array of length (side_length) to replace the right side with
     */
    public void setRightSide(int[] side_values){
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            setValue(this.getMaxCord(), y, side_values[y]);
        }
    }

    /**
     * @return The right column of values
     */
    public int[] getRightSide(){
        int[] out = new int[heightmap.length];
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            out[y] = this.getValue(this.getMaxCord(), y);
        }
        return out;
    }


    /**
     * Sets the top row to zeros
     */
    public void setZerosTop(){
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            setValue(x, 0, 0);
        }
    }

    /**
     * Sets the bottom row to zeros
     */
    public void setZerosBottom(){
        for(int x = 0; x <= this.getMaxCord(); x += 1){
            setValue(x, this.getMaxCord(), 0);
        }
    }

    /**
     * Sets the left column to zeros
     */
    public void setZerosLeft(){
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            setValue(0, y, 0);
        }
    }

    /**
     * Sets the right column to zeros
     */
    public void setZerosRight(){
        for(int y = 0; y <= this.getMaxCord(); y += 1){
            setValue(this.getMaxCord(), y, 0);
        }
    }

    /**
     * @return a Buffered image of the height map for the plane
     */
    public BufferedImage draw(){
        int side_length = getMaxCord();
        BufferedImage img = new BufferedImage(side_length + 1, side_length + 1, BufferedImage.TYPE_INT_RGB);
        // Each pixel is colored by the following rules based on its height
        for(int x = 0; x <= side_length; x++) {
            for (int y = 0; y <= side_length; y++) {
                float height = getValue(x, y);
                if (height < Main.WATER_HEIGHT) {
                    img.setRGB(x, y, gradient(height, 0, Main.WATER_HEIGHT,
                            25,49,107,57,83,149));
                }
                else if (height > Main.MOUNTAIN_HEIGHT_PEAK){
                    img.setRGB(x, y, gradient(height, Main.MOUNTAIN_HEIGHT_PEAK, Main.MAX_ALT,
                            134,133,128,211,206,202));
                }
                else if (height > Main.MOUNTAIN_HEIGHT){
                    img.setRGB(x, y, gradient(height, Main.MOUNTAIN_HEIGHT, Main.MOUNTAIN_HEIGHT_PEAK,
                            55,75,74,134,133,128));
                }
                else {
                    img.setRGB(x, y, gradient(height, Main.WATER_HEIGHT, Main.MOUNTAIN_HEIGHT,
                            80,111,95,55,75,74));
                }
            }
        }
        return img;
    }

    /**
     * @return a Buffered image of the height map for the plane in greyscale
     */
    public BufferedImage drawGreyScale(){
        int side_length = getMaxCord();
        BufferedImage img = new BufferedImage(side_length + 1, side_length + 1, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x <= side_length; x++) {
            for (int y = 0; y <= side_length; y++) {
                float height = getValue(x, y);
                img.setRGB(x, y, gradient(height, 0, Main.MAX_ALT,
                        0,0,0,255,255,255));
            }
        }
        return img;
    }


    /**
     * Calculates the color of a pixel depending on the percentage height between min and max
     * mixing colors rgb 1 and rgb 2.
     *
     * @param height Height of the pixel
     * @param min min height (0%)
     * @param max max height (100%)
     * @param r1 Red value of color for min pixel
     * @param g1 Green value of color for min pixel
     * @param b1 Blue value of color for min pixel
     * @param r2 Red value of color for max pixel
     * @param g2 Green value of color for max pixel
     * @param b2 Blue value of color for max pixel
     * @return Return the mixed color for this pixel in rgb 0 - 16777215
     */
    public int gradient(float height, int min, int max, int r1, int g1, int b1, int r2, int g2, int b2){
        float percentage = (height - min) / (max - min);
        int r = (int)(r1 * (1 - percentage) + r2 * percentage);
        int g = (int)(g1 * (1 - percentage) + g2 * percentage);
        int b = (int)(b1 * (1 - percentage) + b2 * percentage);
        return (65536 * r + 256 * g + b);
    }
}
