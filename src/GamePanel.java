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
import java.util.Timer;
import java.util.TimerTask;


/**
 * Panel where the whole game is displayed except the beginning and the ending
 */
public class GamePanel extends JPanel implements ActionListener {
    /**
     * Instance of the hunter-class
     */
    Hunter hunter;
    /**
     * instance of the runner-class
     */
    Runner runner;
    /**
     * Times the repaint and recalculation of the game.
     */
    Timer gameTimer;
    /**
     * Instance of Perlin-Noise to control parts of the terrain-generation.
     */
    ImprovedNoise improvedNoise;
    /**
     * Instance of the end-screen, which is shown when a player won
     */
    EndScreen endScreen;
    /**
     * ArrayList to store the walls / blocks.
     */
    ArrayList<Wall> walls = new ArrayList<>();
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
    /**
     * The Distance in pixel, used to spawn new Blocks(Renderdistance)
     */
    int windowWidth = 2000;
    /**
     * boolean to check if the game should run. By default it's true and is only set to false if somebody won.
     */
    boolean isRunning = true;
    /**
     * BufferedImage of the stone-texture.
     */
    BufferedImage stone;
    /**
     * BufferedImage of the dirt-texture
     */
    BufferedImage dirt;
    /**
     * BufferedImage of the gras-texture
     */
    BufferedImage gras;
    /**
     * BufferedImage of the gras-texture at a left end of a plateau
     */
    BufferedImage grasLeft;
    /**
     * Instance of SpriteSheet in order to split the SpriteSheet of gras and dirt into pieces.
     */
    SpriteSheet grasSheet;
    /**
     * Instance of SpriteSheet in order to split the SpriteSheet of the stone into pieces
     */
    SpriteSheet stoneSheet;
    /**
     * Instance of SpriteSheet in order to split the SpriteSheet of the sand into pieces
     */
    SpriteSheet sandSheet;
    /**
     * Instance of SpriteSheet in order to split the SpriteSheet of the snow into pieces
     */
    SpriteSheet snowSheet;
    /**
     * Instance of loader, to load BufferedImages (the SpriteSheets)
     */
    BufferedImageLoader loader;
    /**
     * The height of the Terrain. At the beginning it's set to 500. By the TerrainGen the value is changed over time.
     */
    int height = 500;
    /**
     * String where then name of the winner-character is stored. Used in {@link EndScreen}.
     */
    String winnerString;
    /**
     * BufferedImage with the sand-tile
     */
    private BufferedImage sand;
    /**
     * BufferedImage of the sand-tile at the top of the Terrain
     */
    private BufferedImage sandTop;
    //Import Images for the different solids
    /**
     * BufferedImage of the sand-tile at the top-left of the Terrain
     */
    private BufferedImage sandTopLeft;
    private BufferedImage snowLeft;
    private BufferedImage snow;
    /**
     * BufferedImage containing the whole dirt / gras-SpriteSheet.
     */
    private BufferedImage spriteSheet = null;
    /**
     * BufferedImage containing the whole stone-SpriteSheet
     */
    private BufferedImage spriteSheetStone = null;
    /**
     * BufferedImage containing the whole sand-SpriteSheet
     */
    private BufferedImage spriteSheetSand = null;
    /**
     * BufferedImage containing the whole snow-SpriteSheet
     */
    private BufferedImage spriteSheetSnow = null;
    private Boolean summer;

    /**
     * Constructor of the GamePanel. Starts the music, places the hunter, runner and the walls starts the gameloop / timer.
     */
    public GamePanel() {
        summer = false;
        hunter = new Hunter(400, 300, this);
        runner = new Runner(400, 300, this);
        loadWallImages();
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
                for (int i = 0; i < walls.size(); i++) { //has be done like this to prevent concurrentModificationException
                    walls.get(i).set(cameraX);
                }

                //removes Walls outside of the Window
                for (int i = walls.size() - 1; i >= 0; i--) {
                    if (walls.get(i).x < -windowWidth) {
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
     * Checks if one of the both Players has won. If true it sets isRunning false and starts a winning fanfare.
     */
    public void checkWinner() {
        if (runner.x - hunter.x > 1800 || hunter.x - runner.x > 1800) {
            if (runner.x > hunter.x) {
                isRunning = false;
                winnerString = "runner";
            } else if (hunter.x > runner.x) {
                winnerString = "hunter";
                isRunning = false;
            }
            Music music = new Music();
            music.playMusic("res/Music/winningSound.wav");
            openEndScreen();
        }
    }

    /**
     * Method for opening {@link EndScreen}.
     */
    public void openEndScreen() {
        endScreen = new EndScreen(winnerString);
        endScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        endScreen.setLocationRelativeTo(null);

        endScreen.setResizable(true);

        endScreen.setTitle("Gaia");
        endScreen.setVisible(true);
        endScreen.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Method for resetting the world and the camera to starting position.
     */
    public void reset1() {
        respawn();
        cameraX = 150;
        cameraY = 500;
        walls.clear();
        offset = -150;
        height = 500;
        terrainGen();
    }

    /**
     * Method for respawning the Players
     */
    public void respawn() {
        hunter.x = 400;
        hunter.y = height - 150;
        hunter.xSpeed = 0;
        hunter.ySpeed = 0;
        runner.x = 300;
        runner.y = height - 150;
        runner.ySpeed = 0;
        runner.xSpeed = 0;

    }

    /**
     * Method to loads the walls / blocks.
     * It defines the different Tiles from the sprites. The Generation is happening some where else
     */
    public void loadWallImages() {
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
        try {
            spriteSheetSnow = loader.loadImage("textures/SpriteSheetSnow.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sandSheet = new SpriteSheet(spriteSheetSand);
        sandTopLeft = sandSheet.grabImage(1, 1, s, s);
        sand = sandSheet.grabImage(2, 2, s, s);
        sandTop = sandSheet.grabImage(2, 1, s, s);


        grasSheet = new SpriteSheet(spriteSheet);
        grasLeft = grasSheet.grabImage(1, 1, s, s);
        dirt = grasSheet.grabImage(2, 2, s, s);
        gras = grasSheet.grabImage(2, 1, s, s);

        snowSheet = new SpriteSheet(spriteSheetSnow);
        snowLeft = snowSheet.grabImage(1, 1, s, s);
        snow = snowSheet.grabImage(2, 1, s, s);


        try {
            spriteSheetStone = loader.loadImage("textures/stone_sprite.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stoneSheet = new SpriteSheet(spriteSheetStone);
        stone = stoneSheet.grabImage(2, 1, s, s);
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
     * The method for generating the terrain, by subtracting or adding height.
     * It chooses between deserts and meadows by a 1-Dimensional Perlin-Noise. {@link ImprovedNoise}
     */
    public void terrainGen() {
        if (summer) {
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
        } else {
            for (int x = 0; x < 40; x++) {
                int minHeight = (height) - 50;
                int maxHeight = (height) + 100;
                height = ((int) (Math.random() * (maxHeight - minHeight) + minHeight) / 50) * 50;
                int minStoneSpawnDistance = height + 50;
                int maxStoneSpawnDistance = height + 300;
                int totalSpawnDistance = ((int) (Math.random() * (maxStoneSpawnDistance - minStoneSpawnDistance) + minStoneSpawnDistance) / 50) * 50;
                for (int y = 1100; y > height; y -= 50) {
                    if (x == 0) walls.add(new Wall((offset), height, s, s, snowLeft));
                    else {
                        walls.add(new Wall((offset + x * 50), height, s, s, snow));
                    }
                    if (y > totalSpawnDistance) {
                        walls.add(new Wall((offset + x * 50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x * 50), y, s, s, dirt));

                    }
                }
            }

        }

    }

    /**
     * Method for opening the {@link MenuFrame}, the ingame-Menu
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
