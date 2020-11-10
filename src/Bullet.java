import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet {
    GamePanelPvP panel;
    PlayerPvP player;
    int x;
    int y;
    double xSpeed;
    double ySpeed;
    int width;
    int height;
    Rectangle hitBox;

    boolean ePressed;

    public Bullet(int x, int y){

        this.x = x;
        this.y = y;
        //this.player = player;
        xSpeed = player.xspeed * 3;
        ySpeed = player.yspeed;
        width = 10;
        height = 10;
        hitBox = new Rectangle(x,y,width, height);
        set();

    }
    public void set(){
        if (ePressed){
            xSpeed = player.xspeed * 3;
            ySpeed = player.yspeed;
        }

        //horizontale Kolllision
        hitBox.x += xSpeed;
        for (Bullet bullet : panel.bullets) {
            if (hitBox.intersects(bullet.hitBox)) {
                hitBox.x -= xSpeed;
                while (!bullet.hitBox.intersects(hitBox)) {
                    hitBox.x += Math.signum(xSpeed);//TODO: Googlen wie signum funktioniert
                }
                hitBox.x -= Math.signum(xSpeed);
                xSpeed = 0;
                x = hitBox.x;
            }

        }
        //vertikale Kollision
        hitBox.y += ySpeed;
        for (Bullet bullet : panel.bullets) {
            if (hitBox.intersects(bullet.hitBox)) {//Horizontal
                hitBox.y -= ySpeed;
                while (!bullet.hitBox.intersects(hitBox)) {
                    hitBox.y += Math.signum(ySpeed);
                }
                hitBox.y -= Math.signum(ySpeed);
                ySpeed = 0;
                y = hitBox.y;
            }

        }

        x+=xSpeed;
        y+=ySpeed;

    }
    public void draw(Graphics2D gtd) {

        gtd.setColor(Color.ORANGE);
        gtd.fillRect(x, y, width, height);
    }
}
