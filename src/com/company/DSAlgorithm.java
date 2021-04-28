package com.company;

import java.util.Random;

/**
 * A java implementation of the Diamond Square Algorithm
 *
 * @link https://en.wikipedia.org/wiki/Diamond-square_algorithm
 */
public class DSAlgorithm {

    static Random rand = new Random();

    /**
     * Calculating a height map for a plane object
     *
     * @param plane a plane with side length 2n+1
     */
    public static void calculateHeightmap(Plane plane) {
        assignRandomValues(plane);
        diamondSquareAlgorithm(plane, plane.getMaxCord());
    }

    /**
     * The method takes a plane and assigns the corners with random values.
     *
     * Note: Corners which are already defined will not be overwritten.
     *
     * @param plane a plane tile
     */
    private static void assignRandomValues(Plane plane) {
        int max = plane.getMaxCord();
        if(plane.getValue(0,0) == -1) {
            plane.setValue(0, 0, rand.nextInt(Main.MAX_ALT));
        }
        if(plane.getValue(0,max) == -1) {
            plane.setValue(0, max, rand.nextInt(Main.MAX_ALT));
        }
        if(plane.getValue(max,0) == -1) {
            plane.setValue(max, 0, rand.nextInt(Main.MAX_ALT));
        }
        if(plane.getValue(max,max) == -1) {
            plane.setValue(max, max, rand.nextInt(Main.MAX_ALT));
        }
    }

    /**
     * Method applies the diamond square algorithm to a plane tile recursively
     *
     * Note: Corners must have been seeded previously
     *
     * @param plane a plane with side length 2n+1
     * @param square_length The length of the subsection of the plane
     *                      (Plane length == square length) on first call
     */
    private static void diamondSquareAlgorithm(Plane plane, int square_length){
        int side_length = plane.getMaxCord();
        // Diamond Step
        for(int x = square_length/2; x < side_length; x+=(square_length - 1)){
            for(int y = square_length/2; y < side_length; y+=(square_length - 1)){
                if(plane.getValue(x, y) == -1) {
                    plane.setValue(x, y, jitter(square_length,
                            (plane.getValue(x - square_length/2, y - square_length/2)
                                    + plane.getValue(x + square_length/2, y - square_length/2)
                                    + plane.getValue(x - square_length/2, y + square_length/2)
                                    + plane.getValue(x + square_length/2, y + square_length/2)) / 4));
                }
            }
        }
        // Square Step
        for(int x = 0; x <= side_length; x+=(square_length / 2)){
            for(int y = 0; y <= side_length; y+=(square_length / 2)){
                if(plane.getValue(x, y) == -1) {
                    plane.setValue(x, y, jitter(square_length, getPlusAverage(plane, x, y, square_length / 2)));
                }
            }
        }
        // Recursion
        if(square_length > 3){
            diamondSquareAlgorithm(plane, square_length/2 + 1 );
        }
    }


    /**
     * Method used to apply deviation to the output of a diamond square step.
     *
     * The maximal change possible depends on the distance between the points
     *      (size of sub selection in diamondSquareAlgorithm)
     *
     * @param square_length The length of the square for this Diamond Square Step
     * @param average The average value of surrounding points from the Diamond Square Algorithm
     * @return The average value with jitter applied
     */
    private static int jitter(int square_length, int average) {
        int out = (int)(average + (rand.nextInt(2 * square_length) - square_length) * Main.JITTER);
        if(out > Main.MAX_ALT){
            out = Main.MAX_ALT;
        }
        else if(out < Main.MIN_ALT){
            out = Main.MIN_ALT;
        }
        return out;
    }

    /**
     * Prevents square steps from attempting to compute averages with out of bounds coordinates.
     *
     * @param plane plane object on which diamond square algorithm is being applied
     * @param x the x coordinate of the point to be averaged
     * @param y the y coordinate of the point to be averaged
     * @param distance The horizontal and linear distance of points to calculate the average from
     * @return The average of the surrounding points distance away from x,y in the square step
     */
    private static int getPlusAverage(Plane plane, int x, int y, int distance){
        int max = plane.getMaxCord();
        if(x != 0 && y != 0 && x != max && y != max){
            return (plane.getValue(x - distance, y) + plane.getValue(x, y - distance) +
                    plane.getValue(x + distance, y) + plane.getValue(x, y + distance)) / 4;
        }
        else if(x == 0){
            return (plane.getValue(x, y - distance) +
                    plane.getValue(x + distance, y) + plane.getValue(x, y + distance)) / 3;
        }
        else if(y == 0){
            return (plane.getValue(x - distance, y) +
                    plane.getValue(x + distance, y) + plane.getValue(x, y + distance)) / 3;
        }
        else if(x == max){
            return (plane.getValue(x - distance, y) + plane.getValue(x, y - distance) +
                    plane.getValue(x, y + distance)) / 3;
        }
        else {
            return (plane.getValue(x - distance, y) + plane.getValue(x, y - distance) +
                    plane.getValue(x + distance, y)) / 3;
        }
    }

}

