import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


/**
 * Class for creating the solid Body Tiles
 */
public class Wall {
    int x;
    int y;
    int width;
    int height;
    //sichert Postion zu den anderen Blöcken
    int startX;
    int startY;
    Rectangle hitBox;
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
     * @param g Graphics2D for actually painting the Image
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
