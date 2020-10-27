import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;


public class Wall {
    int x;
    int y;
    int width;
    int height;
    //sichert Postion zu den anderen Blöcken
    int startX;
    int startY;

    private Image wall;

    Rectangle hitBox;

    public Wall(int x, int y, int width, int height, String imageWall){

        this.x = x;
        this.y = y;
        startX = x;
        startY = y;//StartX wird am Anfang einmal festgelegt
        this.width = width;
        this.height = height;
        ImageIcon iconWall = new ImageIcon(imageWall);
        wall = iconWall.getImage();

        hitBox = new Rectangle(x, y, width, height);
    }


    public void draw(Graphics2D gtd) {
        gtd.drawImage(wall, x, y, null);
    }

    public int set(int cameraX, int cameraY){
        x = startX + cameraX;
        hitBox.x = x;
        /*y = startY + cameraY;
        hitBox.y = y;*/

        return x;

    }

}
