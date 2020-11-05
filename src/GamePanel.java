import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class GamePanel extends JPanel implements ActionListener {
    Player player;
    Player player2;
    Timer gameTimer;

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
    BufferedImageLoader loader;
    int height = 500;
    //Import Images for the different solids
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheetStone = null;
    int[] topLayer;
    int gameMode;


    /**
     *Constructor of the GamePanel. Starts the music, places the player, starts the gameloop / timer.
     */
    public GamePanel(int gameMode) {
        this.gameMode = gameMode;
        music();
        //einfügen des Spieler-Objekts
        if (gameMode == 1){
            player = new Player(400, 300, this);
        } else{
            player = new Player(500, 300, this);
            player2 = new Player(300, 300, this);
        }


        reset1();

        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (gameMode == 1){
                    player.set();
                }
                else if (gameMode == 2){
                    player.set();
                    player2.set();
                }
                //zeichnet walls wenn sie  kurz davor sind ins sichtfeld zu kommen
                if (walls.get(walls.size()-1).x < (windowWidth)) {
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
            default:
                filepath = "Music/song1.wav"; //Einfach irgendein Path es wird so oder so ein error geschmissen
        }

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
    public void respawn(){
        if (gameMode == 1){
            player.x = 400;
            player.y = 150;
            player.xspeed = 0;
            player.yspeed = 0;
        } else if (gameMode == 2){
            player.x = 200;
            player.y = 150;
            player.xspeed = 0;
            player.yspeed = 0;
        }

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
     * @param g name for the Graphics to paint the tiles
     */
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;
        if (gameMode == 1){
            player.draw(gtd);
        } else if(gameMode == 2){
            player.draw(gtd);
            player2.draw(gtd);
        }



        for (Wall wall : walls) {
            wall.draw(gtd);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }

    /**
     * The method for creating the terrain, by subtracting or adding height.
     * @param height startheight of the terrain
     */
    public void terrainGen(int height) {
        if (gameMode == 1){
            for (int x = 0; x < 40; x++) {
                int minHeight = (height) - 50;
                int maxHeight = (height) + 100;
                height = ((int) (Math.random() * (maxHeight - minHeight) + minHeight) / 50) * 50;
                int minStoneSpawnDistance = height + 50;
                int maxStoneSpawnDistance = height + 300;
                int totalSpawnDistance = ((int) (Math.random() * (maxStoneSpawnDistance - minStoneSpawnDistance) + minStoneSpawnDistance) / 50) * 50;
                for (int y = 1100; y > height; y -= 50) {
                    if (y > totalSpawnDistance) {
                        walls.add(new Wall((offset + x * 50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x * 50), y, s, s, dirt));

                    }
                }

                topLayer = new int[41];
                topLayer[x] = height;

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
            }
        }
        else if (gameMode == 2){
            //TODO: Arena bauen
        }


    }

    /**
     * Method for opening the Menu
     */
    public void openMenu(){
        MenuFrame menuFrame = new MenuFrame();
        menuFrame.setSize(300,300);
        menuFrame.setLocationRelativeTo(null);

        menuFrame.setResizable(true);

        menuFrame.setTitle("Menu");
        menuFrame.setVisible(true);
        menuFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /**
     * Checks the KeyPressed inputs and perform the specific actions.
     * @param e The KeyEvent received from KeyChecker
     */
    public void keyPressed(KeyEvent e) {
        if (gameMode == 1){
            //movement player 1
            if (e.getKeyChar() == 'a') player.keyLeft = true;
            if (e.getKeyChar() == 'd') player.keyRight = true;
            if (e.getKeyChar() == 'w') player.keyUp = true;

            //respawn
            if (e.getKeyChar() == 'r') reset1();
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) openMenu();
        }
        else if (gameMode == 2){
            //movement player 1

            if (e.getKeyChar() == 'a') player.keyLeft = true;
            if (e.getKeyChar() == 'd') player.keyRight = true;
            if (e.getKeyChar() == 'w') player.keyUp = true;

            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) openMenu();

            if (e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = true;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = true;
            if (e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = true;
        }


    }

    /**
     * Checks the KeyReleased inputs and perform the specific actions.
     * @param e The KeyEvent received from KeyChecker
     */
    public void keyReleased(KeyEvent e) {
        if (gameMode == 1){
            //stop movement player1
            if (e.getKeyChar() == 'a') player.keyLeft = false;
            if (e.getKeyChar() == 'd') player.keyRight = false;
            if (e.getKeyChar() == 'w') player.keyUp = false;
        }
        else if (gameMode == 2){
            //stop movement player1
            if (e.getKeyChar() == 'a') player.keyLeft = false;
            if (e.getKeyChar() == 'd') player.keyRight = false;
            if (e.getKeyChar() == 'w') player.keyUp = false;
            //stop movement Player2
            if (e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = false;
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = false;
            if (e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = false;
        }

    }
}
