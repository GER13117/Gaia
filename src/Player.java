import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Player Class
 */
public class Player {
	public static BufferedImage[] frames = new BufferedImage[5];
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
			for (int i = 0; i < panel.walls.size(); i++) {
				Wall wall = panel.walls.get(i);
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
		getImages();
		gtd.drawImage(frames[0], x, y, null);
	}

	public void getImages() {
		loader = new BufferedImageLoader();
		try {
			char1 = loader.loadImage("Characters/char_male_1_walking.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		character1 = new SpriteSheet(char1);
		frames[0] = character1.grabImage(1, 1, 50, 100);
		frames[1] = character1.grabImage(2, 1, 50, 100);
		frames[2] = character1.grabImage(3, 1, 50, 100);
		frames[3] = character1.grabImage(4, 1, 50, 100);
		frames[4] = character1.grabImage(5, 1, 50, 100);
	}
}
