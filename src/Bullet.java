import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;


/**
 * Class for creating the solid Body Tiles
 */
public class Bullet {
    int x;
    int y;
    int width;
    int height;
    //sichert Postion zu den anderen Blöcken
    int startX;
    int startY;
    Rectangle hitBox;
    private Image wall;
    PlayerPvP playerPvP;
    boolean movePositive;

    /**
     * Constructor of the Block / Wall
     *
     * @param x         xPostion of a specific tile
     * @param y         yPostion of a specific tile
     * @param width     width of a specific tile
     * @param height    height of a specific tile
     */
    public Bullet(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        startX = x;
        startY = y;//StartX wird am Anfang einmal festgelegt
        this.width = width;
        this.height = height;

        hitBox = new Rectangle(x, y, width, height);
    }


    /**
     * Draws 50*50px Image at the Postion of the Tile
     *
     * @param gtd Graphics2D for actually painting the Image
     */
    public void draw(Graphics2D gtd) {
        for (int i = 0; i<30; i++){
            gtd.setColor(Color.ORANGE);
            gtd.drawRect(x, y, width, height);
            gtd.setColor(Color.WHITE);
            gtd.fillRect(x+1,y+1,width-2,height-2);
            //gtd.drawImage(wall, x, y, null);
            x++;
        }
    }

    /**
     * method for moving the wall equivalent to the speed of the Player
     *
     * @param cameraX XPostion of the Camera
     * @param cameraY YPostion of the Camera (unused)
     * @return new x Postion of the Wall
     */
    public int set(int cameraX, int cameraY) {
        /*y = startY + cameraY;
        hitBox.y = y;*/

        return x;
    }

}
