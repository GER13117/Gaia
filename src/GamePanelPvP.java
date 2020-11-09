import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanelPvP extends JPanel implements ActionListener {
    PlayerPvP player;
    PlayerPvP player2;
    Timer gameTimer;
    Bullet bullet;

    //Biomes biomes;
    ArrayList<Wall> walls = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    //Variablen zum Definieren der Kamerposition
    int cameraX;
    int cameraY;
    int offset;
    int s = 50;

    // gerundete Fenstergröße für vereinfachte verwendung
    int windowHeight = 1000;//1100, da bei 1080 immer 20 px verschiebung war
    int windowWidth = 2000;//die renderdistanz ist damit gemeint
    int bottomRow = windowHeight - 200;
    BufferedImage stone;
    BufferedImage dirt;
    BufferedImage gras;
    BufferedImage grasLeft;
    SpriteSheet ss;
    SpriteSheet stoneSheet;
    BufferedImageLoader loader;
    int height = 500;
    //Import Images for the different solids
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheetStone = null;
    int[] topLayer;
    int gameMode;


    public GamePanelPvP() {
        //einfügen des Spieler-Objekts
        player = new PlayerPvP(400, 300, this);
        player2 = new PlayerPvP(200, 300, this);

        reset1();
        reset2();

        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.set();
                player2.set();
                repaint();

            }
        }, 0, 17);


    }

    public void reset1() {
        player.x = 200;
        player.y = 150;
        player.xspeed = 0;
        player.yspeed = 0;
        walls.clear();
        int offset = 50;
        makeWalls(offset);
    }

    public void reset2() {
        player2.x = 200;
        player2.y = 150;
        player2.xspeed = 0;
        player2.yspeed = 0;
        walls.clear();
        int offset = 50;
        makeWalls(offset);
    }


    public void makeWalls(int offset) {
        loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("textures/gras_dirt_sprite.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ss = new SpriteSheet(spriteSheet);
        grasLeft = ss.grabImage(1, 1, s, s);
        dirt = ss.grabImage(2, 2, s, s);
        gras = ss.grabImage(2, 1, s, s);
        BufferedImage dirtLeftDown = ss.grabImage(1, 3, s, s);
        BufferedImage grasRight = ss.grabImage(3, 1, s, s);
        BufferedImage dirtRight = ss.grabImage(3, 2, s, s);
        BufferedImage grasRightDown = ss.grabImage(3, 3, s, s);

        try {
            spriteSheetStone = loader.loadImage("textures/stone_sprite.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stoneSheet = new SpriteSheet(spriteSheetStone);
        stone = stoneSheet.grabImage(2, 1, s, s);
        BufferedImage stoneRight = stoneSheet.grabImage(3, 1, s, s);
        BufferedImage stoneLeft = stoneSheet.grabImage(1, 1, s, s);
        int squareSize = 50;
        for (int i = 50; i < windowWidth - 50; i += 50) {
            walls.add(new Wall(i, windowHeight - 80, 50, 50, stone));

        }
        int bottomRow = windowHeight - 80;
        //Left endwall
        walls.add(new Wall(50, bottomRow - 50, 50, 50, stone));

        //middle walls from left to right
        walls.add(new Wall(450, bottomRow - 50, 50, 50, stone));

        walls.add(new Wall(600, bottomRow - 100, 50, 50, stone));
        walls.add(new Wall(600, bottomRow - 50, 50, 50, stone));

        walls.add(new Wall(650, bottomRow - 50, squareSize, squareSize, stone));
        walls.add(new Wall(650, bottomRow - 100, squareSize, squareSize, stone));
        walls.add(new Wall(650, bottomRow - 150, squareSize, squareSize, stone));

        walls.add(new Wall(700, bottomRow - 100, 50, 50, stone));
        walls.add(new Wall(700, bottomRow - 50, 50, 50, stone));

        walls.add(new Wall(750, bottomRow - 50, 50, 50, stone));


        //right endwall
        walls.add(new Wall(1850, bottomRow - 150, squareSize, squareSize, stoneRight));
        walls.add(new Wall(1850, bottomRow - 100, squareSize, squareSize, stoneRight));
        walls.add(new Wall(1850, bottomRow - 50, squareSize, squareSize, stoneRight));

    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);
        player2.draw(gtd);

        for (Wall wall : walls) {
            wall.draw(gtd);
        }
        for (Bullet bullet: bullets){
            bullet.draw(gtd);
        }
    }

    /**
     * Method for opening the Menu
     */
    public void openMenu() {
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setSize(300, 300);
        menuFrame.setLocationRelativeTo(null);

        menuFrame.setResizable(true);

        menuFrame.setTitle("Menu");
        menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
    public void addBullet(){
        //bullet.ePressed = true;
        bullets.add(new Bullet(player.x, player.y));

    }


    public void keyPressed(KeyEvent e) {
        //movement player 1
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') player.keyLeft = true;
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') player.keyRight = true;
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') player.keyUp = true;
        if (e.getKeyCode() == KeyEvent.VK_E) addBullet();
        //movement player 2
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player2.keyDown = true;

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) openMenu();

    }

    public void keyReleased(KeyEvent e) {
        //stop movement player1
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') player.keyLeft = false;
        if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D') player.keyRight = false;
        if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W') player.keyUp = false;
        //stop movement player2
        if (e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) player2.keyDown = false;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
