

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Runner Class
 */
public class Runner {
    /**
     * x-position of the runner
     */
    int x;
    /**
     * y-postion of the runner
     */
    int y;
    /**
     * instance of the panel, mainly used to paint the runner
     */
    GamePanel panel;
    /**
     * instance of loader to load the images
     */
    BufferedImageLoader loader;
    /**
     * width of the runner and runner-hitbox
     */
    int width;
    /**
     * height of the runner and runner-hitbox
     */
    int height;
    /**
     * speed in x-direction of the runner
     */
    double xSpeed;
    /**
     * speed in x-direction of the runner
     */
    double ySpeed;

    /**
     * counter of the frames. Start-value is 1 it goes up to 30, where it gets "reset" by modulo
     */
    int frame = 1;

    /**
     * BufferedImage to load the Sprite-Sheets on.
     */
    private BufferedImage char1 = null;
    /**
     *  instance of {@link SpriteSheet}for splitting the BufferedImage into subimages, for forward-movement
     */
    SpriteSheet characterForward;
    /**
     * position of the runner when moving forward at frame / position 1
     * It is not private because it's used as the only Image in another class
     */
    BufferedImage frame1Forward;
    /**
     * position of the runner when moving forward at frame / position 2
     */
    private BufferedImage frame2Forward;
    /**
     * position of the runner when moving forward at frame / position 3
     */
    private BufferedImage frame3Forward;
    /**
     * position of the runner when moving forward at frame / position 4
     */
    private BufferedImage frame4Forward;
    /**
     * position of the runner when moving forward at frame / position 5
     */
    private BufferedImage frame5Forward;

    /**
     * instance of {@link SpriteSheet}for splitting the BufferedImage into subimages, for backward-movement
     */
    SpriteSheet characterBackward;
    /**
     * position of the runner when moving "backward" at frame / position 1
     */
    private BufferedImage frame1Backward;
    /**
     * position of the runner when moving "backward" at frame / position 2
     */
    private BufferedImage frame2Backward;
    /**
     * position of the runner when moving "backward" at frame / position 3
     */
    private BufferedImage frame3Backward;
    /**
     * position of the runner when moving "backward" at frame / position 4
     */
    private BufferedImage frame4Backward;
    /**
     * position of the runner when moving "backward" at frame / position 5
     */
    private BufferedImage frame5Backward;
    /**
     * the hitbox of the runner
     */
    Rectangle hitBox;

    /**
     * boolean which turns to true when the left-arrow-key is pressed, to walk to the left
     */
    boolean keyLeft;
    /**
     * boolean which turns to true when the right-arrow-key is pressed, to walk to the right
     */
    boolean keyRight;
    /**
     * boolean which turns to true when up-arrow-key is pressed, to jump
     */
    boolean keyUp;

    /**
     * @param x     xPosition of the runner
     * @param y     yPosition of the runner
     * @param panel the panel where the runner ist drawn on
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
     * Sets the maximum Velocity, gravitation and collision-detection.
     * The Camera isn't fixed on this character.
     */
    public void set() {

        //limitations and requirements of the movement
        if (keyLeft && keyRight || !keyLeft && !keyRight) {
            xSpeed *= 0.8;
        } else if (keyLeft && !keyRight) {
            xSpeed--;
        } else if (keyRight && !keyLeft) {
            xSpeed++;
        }
        if (xSpeed > 0 && xSpeed < 0.75) {
            xSpeed = 0;
        }
        if (xSpeed < 0 && xSpeed > -0.75) {
            xSpeed = 0;
        }
        if (xSpeed > 10) {
            xSpeed = 10;
        }
        if (xSpeed < -10) {
            xSpeed = -10;
        }

        if (keyUp) {
            //check if touching ground
            hitBox.y++;
            for (int i = 0; i < panel.walls.size(); i++) {
                if (panel.walls.get(i).hitBox.intersects(hitBox)) {
                    Music music = new Music();
                    ySpeed = -15;//hier deaktiviert es fliegen bewegt den Spieler mit einer Geschwindigkeit von 11 nach oben
                    music.playMusic("res/Music/jumpSound.wav");
                }
            }
            hitBox.y--;
        }
        ySpeed += 0.5;
        //horizontal collision
        hitBox.x += xSpeed;
        try {
            for (int i = 0; i < panel.walls.size(); i++) {
                if (hitBox.intersects(panel.walls.get(i).hitBox)) {
                    hitBox.x -= xSpeed;
                    while (!panel.walls.get(i).hitBox.intersects(hitBox)) {
                        hitBox.x += Math.signum(xSpeed);
                    }
                    hitBox.x -= Math.signum(xSpeed);
                    panel.cameraX += x - hitBox.x;
                    xSpeed = 0;
                    hitBox.x = x;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //vertical collision
        hitBox.y += ySpeed;
        try {
            for (int i = 0; i < panel.walls.size(); i++) {
                if (hitBox.intersects(panel.walls.get(i).hitBox)) {
                    hitBox.y -= ySpeed;
                    while (!panel.walls.get(i).hitBox.intersects(hitBox)) {
                        hitBox.y += Math.signum(ySpeed);
                    }
                    hitBox.y -= Math.signum(ySpeed);
                    ySpeed = 0;
                    y = hitBox.y;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        x += xSpeed - MainFrame.panel.hunter.xSpeed; //Subtracts the xSpeed of the Hunter to limit the speed
        y += ySpeed;


        //Death Code
        if (y > 1500) panel.reset1();

        //bewegt die Hitbox mit dem Spieler
        hitBox.x = x;
        hitBox.y = y;
    }


    /**
     * method for drawing the character it loops through number between 1 and 30 on which are specific frames are painter
     * @param g draws the pictures
     */
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
     * Method for loading the Images from the SpriteSheet using the {@link BufferedImageLoader} and {@link SpriteSheet} class.
     */
    public void loadImages() {
        loader = new BufferedImageLoader();
        try {
            char1 = loader.loadImage("characters/GreenRunnerForward.png");
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
            char1 = loader.loadImage("characters/GreenRunnerBackward.png");
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