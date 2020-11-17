

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Player Class
 */
public class Player{
    int x;
    int y;
    GamePanel panel;
    BufferedImageLoader loader;
    int width;
    int height;
    //Velocities of the player
    double xspeed;
    double yspeed;

    private BufferedImage char1 = null;
    SpriteSheet character1;
    BufferedImage frame1buf;
    Rectangle hitBox;

    //Keys
    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;

    /**
     * @param x     xPosition of a specific Player
     * @param y     yPosition of a specific Player
     * @param panel the panel where the Player ist drawn on
     */
    public Player(int x, int y, GamePanel panel) {

        this.panel = panel;
        this.x = x;
        this.y = y;

        width = 50;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);
        loader = new BufferedImageLoader();
    }

    /**
     * Sets the maximum Velocity, Gravitation and Collision detection.
     * Also sets the "Camera Movement" the same as the Player Movement (Player ist always in the center)
     */
    public void set() {

        //Bedingungen und einschränkungen vertikal
        if (keyLeft && keyRight || !keyLeft && !keyRight) {
            xspeed *= 0.8;
        } else if (keyLeft && !keyRight) {
            xspeed--;
        } else if (keyRight && !keyLeft) {
            xspeed++;
        }
        if (xspeed > 0 && xspeed < 0.75) {
            xspeed = 0;
        }
        if (xspeed < 0 && xspeed > -0.75) {
            xspeed = 0;
        }
        if (xspeed > 8) {
            xspeed = 8;
        }
        if (xspeed < -8) {
            xspeed = -8;
        }

        //Gravitation und un Kollision
        if (keyUp) {
            //check if touching ground
            hitBox.y++;
            for (Wall wall : panel.walls) {
                if (wall.hitBox.intersects(hitBox)) {
                    yspeed = -11;//hier deaktiviert es fliegen bewegt den Spieler mit einer Geschwindigkeit von 11 nach oben
                }
            }
            hitBox.y--;
        }
        yspeed += 0.5;
        //horizontale Kolllision
        hitBox.x += xspeed;
        try {
            for (Wall wall : panel.walls) {
                if (hitBox.intersects(wall.hitBox)) {
                    hitBox.x -= xspeed;
                    while (!wall.hitBox.intersects(hitBox)) {
                        hitBox.x += Math.signum(xspeed);//TODO: Googlen wie signum funktioniert
                    }
                    hitBox.x -= Math.signum(xspeed);
                    panel.cameraX += x - hitBox.x;
                    xspeed = 0;
                    hitBox.x = x;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //vertikale Kollision
        hitBox.y += yspeed;
        try {
            for (Wall wall : panel.walls) {
                if (hitBox.intersects(wall.hitBox)) {//Horizontal
                    hitBox.y -= yspeed;
                    while (!wall.hitBox.intersects(hitBox)) {
                        hitBox.y += Math.signum(yspeed);
                    }
                    hitBox.y -= Math.signum(yspeed);
                    yspeed = 0;
                    y = hitBox.y;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        panel.cameraX -= xspeed; //bindet Kamerageschwindigkeit an Spielergeschwindigkeit
        y += yspeed;


        //Death Code
        if (y > 1500) panel.reset1();

        //bewegt die Hitbox mit dem Spieler
        hitBox.x = x;
        hitBox.y = y;
    }

    //Platzhalter für animierten Charakter
    public void draw(Graphics gtd) {
        //ImageIcon iconPlayer = new ImageIcon(frame1);
        // test = iconPlayer.getImage();
        //gtd.setColor(Color.BLACK);
        //gtd.fillRect(x, y, width, height);
        new Thread(()->{
            animation(gtd);
        }).start();
        gtd.drawImage(frame1buf,x,y, null);
    }
    public void animation(Graphics g){
        loader = new BufferedImageLoader();
        try {
            char1 = loader.loadImage("Characters/char_male_1_walking.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        character1 = new SpriteSheet(char1);
        frame1buf = character1.grabImage(1,1,50,100);
        g.drawImage(frame1buf,x,y, null);
    }
}
