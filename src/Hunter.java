import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Player Class
 */
public class Hunter {
    /**
     * x-position of the hunter
     */
    int x;
    /**
     * y-postion of the hunter
     */
    int y;
    /**
     * instance of the panel, mainly used to paint the hunter
     */
    GamePanel panel;
    /**
     * instance of loader to load the images
     */
    BufferedImageLoader loader;
    /**
     * width of the player and hunter-hitbox
     */
    int width;
    /**
     * height of the player and hunter-hitbox
     */
    int height;
    /**
     * speed in x-Direction of the hunter
     */
    double xSpeed;
    /**
     * speed in y-Direction of the hunter
     */
    double ySpeed;
    /**
     * counter of the frames. Start-value is 1 it goes up to 30, where it gets "reset" by modulo
     */
    int frame = 1;
    /**
     * instance of {@link SpriteSheet}for splitting the BufferedImage into subimages, for forward-movement
     */
    SpriteSheet characterForward;
    /**
     * position of the Hunter when moving forward at frame / position 1
     * Is not private because it's used as the only Image in another class
     */
    BufferedImage frame1Forward;
    /**
     * instance of {@link SpriteSheet}for splitting the BufferedImage into subimages, for backward-movement
     */
    SpriteSheet characterBackward;
    /**
     * the hitbox of the hunter
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
     * BufferedImage where all positions of the character are saved
     */
    private BufferedImage char1 = null;
    /**
     * position of the Hunter when moving forward at frame / position 2
     */
    private BufferedImage frame2Forward;
    /**
     * position of the Hunter when moving forward at frame / position 3
     */
    private BufferedImage frame3Forward;
    /**
     * position of the Hunter when moving forward at frame / position 4
     */
    private BufferedImage frame4Forward;
    /**
     * position of the Hunter when moving forward at frame / position 5
     */
    private BufferedImage frame5Forward;
    /**
     * position of the Hunter when moving backward at frame / position 1
     */
    private BufferedImage frame1Backward;
    /**
     * position of the Hunter when moving backward at frame / position 2
     */
    private BufferedImage frame2Backward;

    /**
     * position of the Hunter when moving backward at frame / position 3
     */
    private BufferedImage frame3Backward;
    /**
     * position of the Hunter when moving backward at frame / position 4
     */
    private BufferedImage frame4Backward;
    /**
     * position of the Hunter when moving backward at frame / position 5
     */
    private BufferedImage frame5Backward;

    /**
     * @param x     xPosition of the hunter
     * @param y     yPosition of the hunter
     * @param panel the panel where the hunter is drawn on
     */
    public Hunter(int x, int y, GamePanel panel) {

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
     * Also sets the "Camera Movement" the same as the Hunter-Movement (Player ist always in the center)
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
        if (xSpeed > 8) {
            xSpeed = 8;
        }
        if (xSpeed < -8) {
            xSpeed = -8;
        }

        //Gravitation und un Kollision
        if (keyUp) {
            //check if touching ground
            hitBox.y++;
            for (int xy = 0; xy < panel.walls.size(); xy++) {
                if (panel.walls.get(xy).hitBox.intersects(hitBox)) {
                    Music music = new Music();
                    for (int i = 0; i < 1; i++) {
                        music.playMusic("res/Music/jumpSound.wav"); //TODO: provide that the jumping sound plays only once
                    }
                    ySpeed = -11;//hier deaktiviert es fliegen bewegt den Spieler mit einer Geschwindigkeit von 11 nach oben
                }
            }
            hitBox.y--;
        }
        ySpeed += 0.5;
        //horizontale Kolllision
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
        //vertikale Kollision
        hitBox.y += ySpeed;
        try {
            for (int i = 0; i < panel.walls.size(); i++) {
                if (hitBox.intersects(panel.walls.get(i).hitBox)) {//Horizontal
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
        panel.cameraX -= xSpeed; //binds the camera-Velocity to the Hunter-Velocity
        y += ySpeed;


        //Death Code
        if (y > 1180) panel.reset1();

        //moves hitbox with the Player
        hitBox.x = x;
        hitBox.y = y;
    }

    /**
     * method for drawing the character
     * it loops through number between 1 and 30 on which are specific frames are painted
     *
     * @param g draws the pictures
     */
    public void draw(Graphics g) {
        if (frame <= 6 && keyRight) {
            g.drawImage(frame1Forward, x, y, null);
        } else if (frame <= 12 && keyRight) {
            g.drawImage(frame2Forward, x, y, null);
        } else if (frame <= 18 && keyRight) {
            g.drawImage(frame3Forward, x, y, null);
        } else if (frame <= 24 && keyRight) {
            g.drawImage(frame4Forward, x, y, null);
        } else if (frame <= 30 && keyRight) {
            g.drawImage(frame5Forward, x, y, null);
        }

        else if (frame <= 6 && keyLeft) {
            g.drawImage(frame1Backward, x, y, null);
        } else if (frame <= 12 && keyLeft) {
            g.drawImage(frame2Backward, x, y, null);
        } else if (frame <= 18 && keyLeft) {
            g.drawImage(frame3Backward, x, y, null);
        } else if (frame <= 24 && keyLeft) {
            g.drawImage(frame4Backward, x, y, null);
        } else if (frame <= 30 && keyLeft) {
            g.drawImage(frame5Backward, x, y, null);
        }

        else g.drawImage(frame1Forward, x, y, null); //default if nothing is pressed
        frame %= 30;
        frame++;
    }

    /**
     * Method for loading the Images from the SpriteSheet using the {@link BufferedImageLoader} and {@link SpriteSheet} class
     */
    public void loadImages() {
        loader = new BufferedImageLoader();
        try {
            char1 = loader.loadImage("Characters/hunterForward.png");
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
            char1 = loader.loadImage("Characters/hunterBackward.png");
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