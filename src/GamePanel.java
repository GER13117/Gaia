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
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener {
    Player player;
    Timer gameTimer;
    ImprovedNoise improvedNoise;

    //Biomes biomes;
    ArrayList<Wall> walls = new ArrayList<>();
    //Variablen zum Definieren der Kamerposition
    int cameraX;
    int cameraY;
    int offset;
    int s = 50;

    // gerundete Fenstergröße für vereinfachte verwendung
    int windowHeight = 1100;//1100, da bei 1080 immer 20 px verschiebung war
    int windowWidth = 2000;//die renderdistanz ist damit gemeint
    int bottomRow = windowHeight - 100;
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
    int[] topLayer;
    private BufferedImage sand;
    private BufferedImage sandTop;
    BufferedImage sandTopLeft;

    /**
     * Constructor of the GamePanel. Starts the music, places the player, starts the gameloop / timer.
     */
    public GamePanel() {
        music();
        //einfügen des Spieler-Objekts
        player = new Player(400, 300, this);

        reset1();

        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.set();

                //zeichnet walls wenn sie  kurz davor sind ins sichtfeld zu kommen
                if (walls.get(walls.size() - 1).x < (windowWidth)) {
                    offset += windowWidth;
                    makeWalls();

                }
                for (Wall wall : walls) {
                    wall.set(cameraX, cameraY);
                }
                //entfernt walls außerhalb des Bildschirms
                for (int i = 0; i < walls.size(); i++) {
                    if (walls.get(i).x < -windowWidth) {
                        walls.remove(i);
                    }
                }

                repaint();

            }
        }, 0, 17);


    }

    /**
     * Plays random chosen music.
     */
    public static void music() {
        Random rand = new Random();
        int index = rand.nextInt(3);
        String filepath = switch (index) {
            case 0 -> "res/Music/far-from-the-world.wav";
            case 1 -> "res/Music/impavid.wav";
            case 2 -> "res/Music/mountains-past.wav";
//Einfach irgendein Path es wird so oder so ein error geschmissen
            default -> "Music/song1.wav";
        };

        MusicStuff musicObject;
        musicObject = new MusicStuff();
        musicObject.playMusic(filepath);
    }

    /**
     * Method for resetting Player and the world. Shouldn't be used that often
     */
    public void reset1() {
        respawn();
        cameraX = 150;
        cameraY = 500;
        walls.clear();
        offset = -150;
        makeWalls();
    }

    public void respawn() {
        player.x = 400;
        player.y = 150;
        player.xspeed = 0;
        player.yspeed = 0;

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
                //sandSheet = new SpriteSheet(spriteSheetSand);
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

        terrainGen(height);


    }

    /**
     * Method for painting the tiles on a given location
     *
     * @param g name for the Graphics to paint the tiles
     */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);


        for (Wall wall : walls) {
            wall.draw(gtd);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    /**
     * The method for creating the terrain, by subtracting or adding height.
     *
     * @param height startheight of the terrain
     */
    public void terrainGen(int height) {
        //TODO: die Perlin Noise dazu nutzen, um zwischen Erd und sandblöcken zu entscheiden. Dabei soll sand gesetz werden, wenn temperature über 0 ist und Erde wenn es drunter ist
        for (double i = 0; i < 5; i+= 0.02) {
            double temperature = improvedNoise.noise(i) * 50;
            System.out.println(temperature);
        }
        for (int x = 0; x < 40; x++) {
            double temperature = improvedNoise.noise(x/10.5);
            int minHeight = (height) - 50;
            int maxHeight = (height) + 100;
            height = ((int) (Math.random() * (maxHeight - minHeight) + minHeight) / 50) * 50;
            int minStoneSpawnDistance = height + 50;
            int maxStoneSpawnDistance = height + 300;
            int totalSpawnDistance = ((int) (Math.random() * (maxStoneSpawnDistance - minStoneSpawnDistance) + minStoneSpawnDistance) / 50) * 50;

            if (temperature<0){

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

                    //System.out.println(topLayer[x-1] + " " + height + " " + topLayer[x+1]);//Zum Testen von topLayer[x]
                    //rules for grass
                /*
                    if (topLayer[x-1] == height && topLayer[x+1]== height) walls.add(new Wall((offset + x*50), height, s, s, gras));
                    else if (topLayer[x-1] < height && topLayer[x+1]== height) walls.add(new Wall((offset + x*50), height, s, s, grasLeft));

                    //else if (topLayer[x-1] < height && topLayer[x+1]== height) walls.add(new Wall((offset + x*50), height, s, s, grasLeft));
                    else if (topLayer[x-1] < height && topLayer[x+1] > height) walls.add(new Wall((offset + x*50), height, s, s, grasLeft));*/
                }
            } else {
                for (int y = 1100; y > height; y -= 50) {
                    if (y > totalSpawnDistance) {
                        walls.add(new Wall((offset + x * 50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x * 50), y, s, s, sand));

                    }
                }

                if (x == 0) walls.add(new Wall((offset), height, s, s, sandTopLeft));
                else {
                    //Platzhalter
                    walls.add(new Wall((offset + x * 50), height, s, s, sandTop));
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
        //movement player 1
        if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') player.keyLeft = true;
        if (e.getKeyChar() == 'd'|| e.getKeyChar() == 'D') player.keyRight = true;
        if (e.getKeyChar() == 'w'|| e.getKeyChar() == 'W') player.keyUp = true;

        //respawn
        if (e.getKeyChar() == 'r'|| e.getKeyChar() == 'R') reset1();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) openMenu();


    }

    /**
     * Checks the KeyReleased inputs and perform the specific actions.
     *
     * @param e The KeyEvent received from KeyChecker
     */
    public void keyReleased(KeyEvent e) {
        //stop movement player1
        if (e.getKeyChar() == 'a') player.keyLeft = false;
        if (e.getKeyChar() == 'd') player.keyRight = false;
        if (e.getKeyChar() == 'w') player.keyUp = false;


    }
}
