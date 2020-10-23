import javax.swing.ImageIcon;
import java.awt.*;


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
        gtd.setColor(Color.BLACK);
        gtd.drawRect(x, y, width, height);
        gtd.setColor(Color.WHITE);
        gtd.fillRect(x+1,y+1,width-2,height-2);
    }



}
