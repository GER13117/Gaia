

import java.awt.Graphics;
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

    int frame = 1;

    private BufferedImage char1 = null;
    SpriteSheet characterForward;
    BufferedImage frame1Forward;
    private BufferedImage frame2Forward;
    private BufferedImage frame3Forward;
    private BufferedImage frame4Forward;
    private BufferedImage frame5Forward;

    SpriteSheet characterBackward;
    private BufferedImage frame1Backward;
    private BufferedImage frame2Backward;
    private BufferedImage frame3Backward;
    private BufferedImage frame4Backward;
    private BufferedImage frame5Backward;
    Rectangle hitBox;

    //Keys
    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;

    /**
     * @param x     xPosition of a specific Player
     * @param y     yPosition of a specific Player
     * @param panel the panel where the Player ist drawn on
     */
    public Runner(int x, int y, GamePanel panel) {

        this.panel = panel;
        this.x = x;
        this.y = y;

        width = 46;
        height = 100;
        hitBox = new Rectangle(x, y, width, height);
        loader = new BufferedImageLoader();
        loadImages();
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
        x += xspeed - MainFrame.panel.hunter.xSpeed;
        y += yspeed;


        //Death Code
        if (y > 1500) panel.reset1();

        //bewegt die Hitbox mit dem Spieler
        hitBox.x = x;
        hitBox.y = y;
    }

    //Platzhalter für animierten Charakter
    public void draw(Graphics g) {
        if (frame <= 6&&keyRight){
            g.drawImage(frame1Forward,x,y,null);
        } else if (frame <= 12 && keyRight) {
            g.drawImage(frame2Forward, x, y, null);

        } else if (frame <= 18 && keyRight){
            g.drawImage(frame3Forward,x,y,null);

        } else if (frame <= 24 && keyRight){
            g.drawImage(frame4Forward,x,y,null);

        } else if (frame <= 30 && keyRight){
            g.drawImage(frame5Forward,x,y,null);
        }
        else if (frame <= 6&&keyLeft){
            g.drawImage(frame1Backward,x,y,null);
        } else if (frame <= 12 && keyLeft) {
            g.drawImage(frame2Backward, x, y, null);

        } else if (frame <= 18 && keyLeft){
            g.drawImage(frame3Backward,x,y,null);

        } else if (frame <= 24 && keyLeft){
            g.drawImage(frame4Backward,x,y,null);

        } else if (frame <= 30 && keyLeft){
            g.drawImage(frame5Backward,x,y,null);
        }

        else g.drawImage(frame1Forward,x,y,null); //default if nothing is pressed
        frame%=30;
        frame++;
    }

    /**
     * Method for loading the Images from the SpriteSheet using the BufferedImageLoader and SpriteSheet class
     */
    public void loadImages() {
        loader = new BufferedImageLoader();
        try {
            char1 = loader.loadImage("Characters/GreenRunnerForward.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        characterForward = new SpriteSheet(char1);
        frame1Forward = characterForward.grabImage(1, 1, 50, 100);
        frame2Forward = characterForward.grabImage(2, 1, 50, 100);
        frame3Forward = characterForward.grabImage(3, 1, 50, 100);
        frame4Forward = characterForward.grabImage(4, 1, 50, 100);
        frame5Forward = characterForward.grabImage(5, 1, 50, 100);

        try {
            char1 = loader.loadImage("Characters/GreenRunnerBackward.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        characterBackward = new SpriteSheet(char1);
        frame1Backward = characterBackward.grabImage(5, 1, 50, 100);
        frame2Backward = characterBackward.grabImage(4, 1, 50, 100);
        frame3Backward = characterBackward.grabImage(3, 1, 50, 100);
        frame4Backward = characterBackward.grabImage(2, 1, 50, 100);
        frame5Backward = characterBackward.grabImage(1, 1, 50, 100);


    }
}