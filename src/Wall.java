import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;


public class Wall {
    int x;
    int y;
    int width;
    int height;
    private Image wall;

    Rectangle hitBox;

    public Wall(int x, int y, int width, int height){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        ImageIcon iconWall = new ImageIcon("wall.png");
        wall = iconWall.getImage();

        hitBox = new Rectangle(x, y, width, height);
    }


    public void draw(Graphics2D gtd) {
        gtd.drawImage(wall, x, y, null);
    }

}
