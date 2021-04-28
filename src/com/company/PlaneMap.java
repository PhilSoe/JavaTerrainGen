package com.company;


import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The PlaneMap object contains the methods for connecting and
 * drawing the collection of planes into a coherent map
 */
public class PlaneMap {
    private final Plane[][] plane_grid; // The 2d array of Planes to tile together and from a complete map
    private final int width; // The number of tiles horizontally
    private final int height; // The number of tiles vertically
    private final int square_size; // The number

    /**
     * Initialize and fill out a height map composed of multiple plane tiles.
     *
     * For intended Results (Width and Height) >= 2
     *
     * @param width The number of tiles horizontally >= 2
     * @param height The number of tiles vertically >= 2
     * @param square_size The side length of an individual square (must be 2^n+1)
     */
    public PlaneMap(int width, int height, int square_size){
        this.plane_grid = new Plane[width][height];
        this.width = width;
        this.height = height;
        this.square_size = square_size;

        // Initializing planes in the plane map
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                plane_grid[x][y] = new Plane(square_size);
            }
        }

        /*
        Applying the diamond square algorithm to each plane ensuring that each new plane calculated
        will share borders with the surrounding planes which are already generated.
         */
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(x > 0){
                    plane_grid[x][y].setLeftSide(plane_grid[x - 1][y].getRightSide());
                }
                if(y > 0){
                    plane_grid[x][y].setTopSide(plane_grid[x][y - 1].getBottomSide());
                }
                if(x == width - 1){
                    plane_grid[x][y].setRightSide(plane_grid[0][y].getLeftSide());
                }
                if(y == height - 1){
                    plane_grid[x][y].setBottomSide(plane_grid[x][0].getTopSide());
                }

                // This line makes it so that the edges of the map are water.
                if(y == 0){
                    plane_grid[x][y].setZerosTop();
                }
                if(y == height - 1){
                    plane_grid[x][y].setZerosBottom();
                }
                if(x == 0){
                    plane_grid[x][y].setZerosLeft();
                }
                if(x == width - 1){
                    plane_grid[x][y].setZerosRight();
                }

                DSAlgorithm.calculateHeightmap(plane_grid[x][y]);
            }
        }
    }

    /**
     * @return a Buffered image of the height map
     */
    public BufferedImage draw(){
        BufferedImage img = new BufferedImage((square_size) * width, (square_size) * height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                g2d.drawImage(plane_grid[x][y].draw(),x * square_size, y * square_size, null);
            }
        }
        return img;
    }

    /**
     * @return a Buffered image of the height map in greyscale
     */
    public BufferedImage drawGreyScale(){
        BufferedImage img = new BufferedImage((square_size) * width, (square_size) * height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();

        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                g2d.drawImage(plane_grid[x][y].drawGreyScale(),x * square_size, y * square_size, null);
            }
        }
        return img;
    }
}
