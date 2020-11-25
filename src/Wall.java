import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


/**
 * Class for creating the solid Body Tiles
 */
public class Wall {
    /**
     * x-Position of the wall
     */
    int x;
    /**
     * y-Position of the wall
     */
    int y;
    /**
     * width of the wall
     */
    int width;
    /**
     * height of the wall
     */
    int height;
    /**
     * to save the distance relative to the other blocks to ensure, that all blocks are moving in the same speed
     */
    int startX;
    /**
     * to save the distance relative to the other blocks to ensure, that all blocks are moving in the same speed
     */
    int startY;
    /**
     * rectangle to create a hitbox
     */
    Rectangle hitBox;
    /**
     * BufferedImage of a single block
     */
    BufferedImage imageWall;

    /**
     * Constructor of the Block / Wall
     *
     * @param x         xPostion of a specific tile
     * @param y         yPostion of a specific tile
     * @param width     width of a specific tile
     * @param height    height of a specific tile
     * @param imageWall image / texture of a specific tile
     */
    public Wall(int x, int y, int width, int height, BufferedImage imageWall) {

        this.x = x;
        this.y = y;
        startX = x;
        startY = y;//StartX wird am Anfang einmal festgelegt
        this.width = width;
        this.height = height;
        this.imageWall = imageWall;

        hitBox = new Rectangle(x, y, width, height);
    }


    /**
     * Draws 50*50px Image at the Postion of the Tile
     *
     * @param g Graphics for actually painting the Image
     */
    public void draw(Graphics g) {
        g.drawImage(imageWall, x, y, null);
    }

    /**
     * method for moving the wall equivalent to the speed of the Player
     *
     * @param cameraX XPostion of the Camera
     * @return new x Postion of the Wall
     */
    public int set(int cameraX) {
        x = startX + cameraX;
        hitBox.x = x;
        /*y = startY + cameraY;
        hitBox.y = y;*/

        return x;
    }

}
