

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Runner Class
 */
public class Runner {
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
    private BufferedImage frame1buf;
    private BufferedImage frame2buf;
    private BufferedImage frame3buf;
    private BufferedImage frame4buf;
    private BufferedImage frame5buf;
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
    public Runner(int x, int y, GamePanel panel) {

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
        if (xspeed > 10) {
            xspeed = 10;
        }
        if (xspeed < -10) {
            xspeed = -10;
        }

        //Gravitation und un Kollision
        if (keyUp) {
            //check if touching ground
            hitBox.y++;
            for (int i = 0; i < panel.walls.size(); i++) {
                if (panel.walls.get(i).hitBox.intersects(hitBox)) {
                    Music music = new Music();
                    yspeed = -15;//hier deaktiviert es fliegen bewegt den Spieler mit einer Geschwindigkeit von 11 nach oben
                    music.playMusic("res/Music/jumpSound.wav");
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
                        hitBox.x += Math.signum(xspeed);
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
        //x += xspeed - StartMenu.panel.hunter.xspeed;
        x += xspeed - MainFrame.panel.hunter.xspeed;
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
        loadImages();
        gtd.drawImage(frame1buf, x, y, null);
    }

    public void loadImages() {
        loader = new BufferedImageLoader();
        try {
            char1 = loader.loadImage("Characters/hunterForward.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        character1 = new SpriteSheet(char1);
        frame1buf = character1.grabImage(1, 1, 50, 100);
        frame2buf = character1.grabImage(2, 1, 50, 100);
        frame3buf = character1.grabImage(3, 1, 50, 100);
        frame4buf = character1.grabImage(4, 1, 50, 100);
        frame5buf = character1.grabImage(5, 1, 50, 100);
    }
}