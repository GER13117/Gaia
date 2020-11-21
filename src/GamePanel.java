import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener {
    Player hunter;
    Runner runner;
    Timer gameTimer;
    ImprovedNoise improvedNoise;
    EndScreen endScreen;

    //Biomes biomes;
    ArrayList<Wall> walls = new ArrayList<>();
    //Variablen zum Definieren der Kamerposition
    /**
     * x-Position of the camera
     */
    int cameraX;
    /**
     * y-Position of the camera
     */
    int cameraY;
    /**
     * offset of the blocks, needed for placing the correct
     */
    int offset;
    /**
     * standard size of the tiles
     */
    int s = 50;

    // gerundete Fenstergröße für vereinfachte verwendung
    /**
     * The Distance in pixel, used to spawn new Blocks(Renderdistance)
     */
    int windowWidth = 2000;
    int screenHeight = 1000;
    boolean isRunning = true;
    BufferedImage stone;
    BufferedImage dirt;
    BufferedImage gras;
    BufferedImage grasLeft;
    SpriteSheet ss;
    SpriteSheet stoneSheet;
    SpriteSheet sandSheet;
    BufferedImageLoader loader;
    int height = 500;
    //Import Images for the different solids
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheetStone = null;
    private BufferedImage spriteSheetSand = null;
    private BufferedImage sand;
    private BufferedImage sandTop;
    private BufferedImage sandTopLeft;

    String winnerString;

    /**
     * Constructor of the GamePanel. Starts the music, places the hunter, starts the gameloop / timer.
     */
    public GamePanel() {
        music();
        hunter = new Player(400, 300, this);
        runner = new Runner(400, 300, this);
        makeWalls();
        reset1();
        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                hunter.set();
                runner.set();
                //zeichnet walls wenn sie  kurz davor sind ins sichtfeld zu kommen
                if (walls.get(walls.size() - 1).x < (windowWidth)) {
                    offset += windowWidth;
                    terrainGen();
                }
                for (int i = 0; i < walls.size(); i++) {
                    walls.get(i).set(cameraX);
                }

                //removes Walls outside of the Window
                for (int i = walls.size() - 1; i >= 0; i--) {
                    if (walls.get(i).x < -windowWidth ){
                        walls.remove(i);
                    }
                }
                checkWinner();
                if (!isRunning) gameTimer.cancel();
                repaint();
            }
        }, 0, 17);


    }

    /**
     * Checks if one of the both Players has won
     */
    public void checkWinner() {
        if (runner.x - hunter.x > 1800 || hunter.x - runner.x > 1800) {
            if (runner.x > hunter.x) {
                isRunning = false;
                winnerString = "runner wins";
                openEndScreen();
            } else if (hunter.x > runner.x) {
                winnerString = "hunter wins";
                isRunning = false;
                openEndScreen();
            }
        }
    }

    /**
     * Method for opening the Endscreen
     */
    public void openEndScreen() {
        endScreen = new EndScreen(winnerString);
        endScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        endScreen.setLocationRelativeTo(null);

        endScreen.setResizable(true);

        endScreen.setTitle("Gaia");
        endScreen.setVisible(true);
        endScreen.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Plays random chosen music.
     */
    public static void music() {
        Random rand = new Random();
        int index = rand.nextInt(3);
        String filepath;
        switch (index) {
            case 0:
                filepath = "res/Music/far-from-the-world.wav";
                break;
            case 1:
                filepath = "res/Music/impavid.wav";
                break;
            case 2:
                filepath = "res/Music/mountains-past.wav";
                break;
            //Einfach irgendein Path es wird so oder so ein error geschmissen
            default:
                filepath = "Music/song1.wav";
                break;
        }

        MusicStuff musicObject = new MusicStuff();
        musicObject.playMusic(filepath);
    }

    /**
     * Method for resetting the world.
     */
    public void reset1() {
        respawn();
        cameraX = 150;
        cameraY = 500;
        walls.clear();
        offset = -150;
        terrainGen();
    }

    /**
     * Method for respawnung the Players
     */
    public void respawn() {
        hunter.x = 400;
        hunter.y = 150;
        hunter.xspeed = 0;
        hunter.yspeed = 0;
        runner.x = 300;
        runner.y = 0;
        runner.yspeed = 0;
        runner.xspeed = 0;

    }

    /**
     * Method to make the walls / blocks.
     * It defines the different Tiles from the sprites. The Generation is happening some where else
     */
    public void makeWalls() {
        loader = new BufferedImageLoader();
        try {
            spriteSheet = loader.loadImage("textures/gras_dirt_sprite.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            spriteSheetSand = loader.loadImage("textures/sand_sheet.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sandSheet = new SpriteSheet(spriteSheetSand);
        sandTopLeft = sandSheet.grabImage(1, 1, s, s);
        sand = sandSheet.grabImage(2, 2, s, s);
        sandTop = sandSheet.grabImage(2, 1, s, s);
        BufferedImage sandLeftDown = sandSheet.grabImage(1, 3, s, s);
        BufferedImage sandTopRight = sandSheet.grabImage(3, 1, s, s);
        BufferedImage sandRight = sandSheet.grabImage(3, 2, s, s);
        BufferedImage sandRightDown = sandSheet.grabImage(3, 3, s, s);


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
        for (int i = 0; i < 40; i++) walls.add(new Wall(i, 1050, s, s, stone));

        terrainGen();
    }

    /**
     * Method for painting the tiles on a given location
     *
     * @param g name for the Graphics to paint the tiles
     */
    public void paint(Graphics g) {
        super.paint(g);
        hunter.draw(g);
        runner.draw(g);

        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).draw(g);
        }
    }

    /**
     * unused Method
     *
     * @param ae Unused ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
    }


    /**
     * The method for creating the terrain, by subtracting or adding height.
     */
    public void terrainGen() {
        for (int x = 0; x < 40; x++) {
            double temperature = improvedNoise.noise(x / 10.5);
            int minHeight = (height) - 50;
            int maxHeight = (height) + 100;
            height = ((int) (Math.random() * (maxHeight - minHeight) + minHeight) / 50) * 50;
            int minStoneSpawnDistance = height + 50;
            int maxStoneSpawnDistance = height + 300;
            int totalSpawnDistance = ((int) (Math.random() * (maxStoneSpawnDistance - minStoneSpawnDistance) + minStoneSpawnDistance) / 50) * 50;

            if (temperature < 0) {

                for (int y = 1100; y > height; y -= 50) {
                    if (y > totalSpawnDistance) {
                        walls.add(new Wall((offset + x * 50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x * 50), y, s, s, dirt));

                    }
                }

                if (x == 0) walls.add(new Wall((offset), height, s, s, grasLeft));
                else {
                    //Platzhalter
                    walls.add(new Wall((offset + x * 50), height, s, s, gras));
                }

            } else {
                for (int y = 1100; y > height; y -= 50) {
                    if (x == 0) walls.add(new Wall((offset), height, s, s, sandTopLeft));
                    else {
                        //Platzhalter
                        walls.add(new Wall((offset + x * 50), height, s, s, sandTop));
                    }
                    if (y > totalSpawnDistance) {
                        walls.add(new Wall((offset + x * 50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x * 50), y, s, s, sand));

                    }
                }
            }
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

    /**
     * Checks the KeyPressed inputs and perform the specific actions.
     *
     * @param e The KeyEvent received from KeyChecker
     */
    public void keyPressed(KeyEvent e) {
        //movement hunter
        if (e.getKeyCode() == KeyEvent.VK_A) runner.keyLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_D) runner.keyRight = true;
        if (e.getKeyCode() == KeyEvent.VK_W) runner.keyUp = true;

        //movement runner
        if (e.getKeyCode() == KeyEvent.VK_LEFT) hunter.keyLeft = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) hunter.keyRight = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) hunter.keyUp = true;

        //respawn
        if (e.getKeyChar() == 'r' || e.getKeyChar() == 'R') reset1();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) openMenu();
    }

    /**
     * Checks the KeyReleased inputs and perform the specific actions.
     *
     * @param e The KeyEvent received from KeyChecker
     */
    public void keyReleased(KeyEvent e) {
        //stop movement player1
        if (e.getKeyCode() == KeyEvent.VK_A) runner.keyLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_D) runner.keyRight = false;
        if (e.getKeyCode() == KeyEvent.VK_W) runner.keyUp = false;

        if (e.getKeyCode() == KeyEvent.VK_LEFT) hunter.keyLeft = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) hunter.keyRight = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) hunter.keyUp = false;
    }


}
