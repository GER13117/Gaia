import javax.swing.JPanel;
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
    int windowWidth = 2500;//die renderdistanz ist damit gemeint
    int bottomRow = windowHeight - 100;

    //Import Images for the different solids
    private BufferedImage spriteSheet = null;
    private BufferedImage stonePlaceholder = null;
    SpriteSheet ss;
    SpriteSheet stoneSheet;
    BufferedImageLoader loader;




    public GamePanel(){
        music();
        //einfügen des Spieler-Objekts
        player = new Player(400,300,this);

        reset1();

        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                player.set();
                //zeichnet walls wenn sie  kurz davor sind ins sichtfeld zu kommen
                if(walls.get(walls.size() - 1).x < (windowWidth) ){
                    offset += windowWidth;
                    makeWalls(offset);
                    //System.out.println(walls.size()); //prints the amount of generated walls

                }
                for( Wall wall: walls){
                    wall.set(cameraX, cameraY);
                }
                //entfernt walls außerhalb des Bildschirms
                for(int i = 0; i<walls.size(); i++){
                    if(walls.get(i).x < -windowWidth){
                        walls.remove(i);
                    }
                }

                repaint();

            }
        }, 0, 17);


    }

    public void reset1(){
        player.x = 300;
        player.y = 150;
        cameraX = 150;
        cameraY = 500;
        player.xspeed = 0;
        player.yspeed = 0;
        walls.clear();
        offset = -150;
        makeWalls(offset);
    }


    public void makeWalls(int offset) {

        //improvedNoise = new ImprovedNoise();
        loader = new BufferedImageLoader();
        try{
            spriteSheet = loader.loadImage("textures/gras_dirt_sprite.png");
        }catch (IOException e){
            e.printStackTrace();
        }
        ss = new SpriteSheet(spriteSheet);
        BufferedImage grasLeft = ss.grabImage(1,1, s, s);
        BufferedImage dirt = ss.grabImage(2,2 ,s,s);
        BufferedImage gras = ss.grabImage(2,1,s,s);
        BufferedImage dirtLeftDown = ss.grabImage(1,3,s,s);
        BufferedImage grasRight = ss.grabImage(3,1,s,s);
        BufferedImage dirtRight = ss.grabImage(3,2,s,s);

        try {
            stonePlaceholder = loader.loadImage("textures/stone.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stoneSheet = new SpriteSheet(stonePlaceholder);
        BufferedImage stone = stoneSheet.grabImage(1,1,s,s);


        Random rand = new Random();
        int index = rand.nextInt(2);
        System.out.println(index);


        switch (index){
            case 0:

                canyon(walls, offset, bottomRow, s, stone, grasLeft, dirt, gras, dirtLeftDown, grasRight, dirtRight);
                break;
            case 1:
                smallHills1(walls, offset, bottomRow, s, stone, grasLeft, dirt, gras, dirtLeftDown, grasRight, dirtRight);

                //BottomLayer Texture: Stone

                break;

            /*case 0: //Noise Terrain-Gen
                for (double i = 0; i < 40; i+= 0.02){
                    double terrainHeight = improvedNoise.noise(i) * 100;
                    int rTerrainHeight = ((int) terrainHeight)*25;
                    walls.add(new Wall((int)(offset + (i*50*50)), bottomRow- rTerrainHeight,s,s, grasLeft));
                    System.out.println(rTerrainHeight);
                }
                break;*/
            default:
                for(int i=0; i<40; i++) {
                    walls.add(new Wall((offset + i*50), bottomRow, s, s, grasLeft));
                }

        }
        /*
        //Altes Test Terrain
        for(int i = 50; i < windowWidth-50; i += 50){
            walls.add(new Wall(i, windowHeight-80, 50, 50));

        }

        //Left endwall
        walls.add(new Wall(50, bottomRow-50, 50, 50));

        //middle walls from left to right
        walls.add(new Wall(450, bottomRow-50, 50, 50));

        walls.add(new Wall(600, bottomRow-100, 50, 50));
        walls.add(new Wall(600, bottomRow-50, 50, 50));

        walls.add(new Wall(650, bottomRow-50, s, s));
        walls.add(new Wall(650, bottomRow-100, s, s));
        walls.add(new Wall(650, bottomRow-150, s, s));

        walls.add(new Wall(700, bottomRow-100, 50, 50));
        walls.add(new Wall(700, bottomRow-50, 50, 50));

        walls.add(new Wall(750, bottomRow-50, 50, 50));


        //right endwall
        walls.add(new Wall(1850, bottomRow-100, s, s ));
        walls.add(new Wall(1850, bottomRow-50, s, s ));
        */
    }


    public void paint(Graphics g){
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);

        for(Wall wall: walls){
            wall.draw(gtd);
        }
    }




    public void keyPressed(KeyEvent e) {
        //movement player 1
        if(e.getKeyChar() == 'a') player.keyLeft = true;
        if(e.getKeyChar() == 'd') player.keyRight = true;
        if(e.getKeyChar() == 'w') player.keyUp = true;

        //respawn
        if(e.getKeyChar() == 'r') reset1();

    }

    public void keyReleased(KeyEvent e) {
        //stop movement player1
        if(e.getKeyChar() == 'a') player.keyLeft = false;
        if(e.getKeyChar() == 'd') player.keyRight = false;
        if(e.getKeyChar() == 'w') player.keyUp = false;

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
    public static void music(){
        Random rand = new Random();
        int index = rand.nextInt(3);
        String filepath;
        switch (index){
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
    private static void canyon(ArrayList<Wall> walls, int offset, int bottomRow, int s, BufferedImage stone, BufferedImage grasLeft, BufferedImage dirt, BufferedImage gras, BufferedImage dirtLeftDown, BufferedImage grasRight, BufferedImage dirtRight){
        for(int i=0; i<50; i++) {
            walls.add(new Wall((offset + i*50), bottomRow, s, s, stone));
        }

        walls.add(new Wall((offset+ 0*50), (bottomRow-50), s, s, grasLeft));
        walls.add(new Wall((offset+ 1*50), (bottomRow-50), s, s, dirt));
        walls.add(new Wall((offset+ 1*50), (bottomRow-100), s, s, grasLeft));
        walls.add(new Wall((offset+ 2*50), (bottomRow-100), s, s, dirt));
        walls.add(new Wall((offset+ 2*50), (bottomRow-150), s, s, dirtLeftDown));
        walls.add(new Wall((offset+ 2*50), (bottomRow-200), s, s, grasLeft));
        walls.add(new Wall((offset+ 3*50), (bottomRow-200), s, s, dirt));
        walls.add(new Wall((offset+ 3*50), (bottomRow-250), s, s, grasLeft));
        for(int i=4; i<= 5; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, gras));
        walls.add(new Wall((offset+ 6*50), (bottomRow-250), s, s, grasRight));
        walls.add(new Wall((offset+ 6*50), (bottomRow-200), s, s, dirt));
        walls.add(new Wall((offset+ 7*50), (bottomRow-200), s, s, grasRight));
        walls.add(new Wall((offset+ 7*50), (bottomRow-150), s, s, dirt));
        walls.add(new Wall((offset+ 8*50), (bottomRow-150), s, s, gras));
        walls.add(new Wall((offset+ 9*50), (bottomRow-150), s, s, dirt));
        walls.add(new Wall((offset+ 9*50), (bottomRow-200), s, s, dirtLeftDown));
        for(int i=10; i<= 11; i++) walls.add(new Wall((offset+ i*50), bottomRow-200, s, s, dirt));
        walls.add(new Wall((offset+ 9*50), bottomRow-250, s, s, grasLeft));
        for(int i=10; i<= 11; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, dirt));
        walls.add(new Wall((offset+ 10*50), bottomRow-300, s, s, grasLeft));
        for(int i=11; i<= 14; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, gras));
        for(int i=15; i<= 18; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, dirt));
        walls.add(new Wall((offset+ 15*50), bottomRow-350, s, s, grasLeft));
        for(int i=16; i<= 17; i++) walls.add(new Wall((offset+ i*50), bottomRow-350, s, s, gras));
        walls.add(new Wall((offset+ 18*50), bottomRow-350, s, s, grasRight));
        walls.add(new Wall((offset+ 19*50), bottomRow-300, s, s, dirtRight));
        walls.add(new Wall((offset+ 19*50), bottomRow-250, s, s, stone));
        walls.add(new Wall((offset+ 19*50), bottomRow-200, s, s, stone));
        walls.add(new Wall((offset+ 19*50), bottomRow-150, s, s, stone));
        walls.add(new Wall((offset+ 19*50), bottomRow-100, s, s, stone));
        walls.add(new Wall((offset+ 19*50), bottomRow-50, s, s, stone));
        walls.add(new Wall((offset+ 21*50), bottomRow-50, s, s, stone));
        walls.add(new Wall((offset+ 21*50), bottomRow-100, s, s, stone));
        for(int i=21; i<= 22; i++) walls.add(new Wall((offset+ i*50), bottomRow-150, s, s, stone));
        walls.add(new Wall((offset+ 22*50), bottomRow-200, s, s, stone));
        walls.add(new Wall((offset+ 22*50), bottomRow-250, s, s, grasLeft));
        walls.add(new Wall((offset+ 23*50), bottomRow-250, s, s, dirt));
        walls.add(new Wall((offset+ 23*50), bottomRow-300, s, s, grasLeft));
        walls.add(new Wall((offset+ 24*50), bottomRow-300, s, s, gras));
        walls.add(new Wall((offset+ 25*50), bottomRow-350, s, s, grasLeft));
        for(int i=26; i<= 27; i++) walls.add(new Wall((offset+ i*50), bottomRow-350, s, s, gras));
        walls.add(new Wall((offset+ 28*50), bottomRow-350, s, s, grasRight));
        for(int i=25; i<= 28; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, dirt));
        for(int i=29; i<= 32; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, gras));
        walls.add(new Wall((offset+ 33*50), bottomRow-300, s, s, grasRight));
        for(int i=34; i<= 39; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, gras));
        walls.add(new Wall((offset+ 40*50), bottomRow-250, s, s, grasRight));
        walls.add(new Wall((offset+ 41*50), bottomRow-200, s, s, gras));
        walls.add(new Wall((offset+ 42*50), bottomRow-200, s, s, grasRight));
        for(int i=43; i<= 45; i++) walls.add(new Wall((offset+ i*50), bottomRow-150, s, s, gras));
        walls.add(new Wall((offset+ 46*50), bottomRow-150, s, s, grasRight));
        walls.add(new Wall((offset+ 47*50), bottomRow-50, s, s, grasRight));
    }

    private void smallHills1(ArrayList<Wall> walls, int offset, int bottomRow, int s, BufferedImage stone, BufferedImage grasLeft, BufferedImage dirt, BufferedImage gras, BufferedImage dirtLeftDown, BufferedImage grasRight, BufferedImage dirtRight) {
        for(int i=0; i<50; i++) {
            walls.add(new Wall((offset + i*50), bottomRow, s, s, stone));
        }
        //Grass or dirt
        for(int i=2; i<= 4; i++) walls.add(new Wall((offset+ i*50), bottomRow-50, s, s, grasLeft));
        walls.add(new Wall((offset+ 4*50), (bottomRow-100), s, s, grasLeft));
        for(int i=4; i<= 6; i++) walls.add(new Wall((offset+ i*50), bottomRow-150, s, s, grasLeft));
        for(int i=6; i<= 9; i++) walls.add(new Wall((offset+ i*50), bottomRow-100, s, s, grasLeft));
        walls.add(new Wall((offset+ 9*50), bottomRow-150, s, s, grasLeft));
        for(int i=9; i<= 11; i++) walls.add(new Wall((offset+ i*50), bottomRow-200, s, s, grasLeft));
        for(int i=11; i<= 13; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, grasLeft));
        for(int i=14; i<= 15; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, grasLeft));
        for(int i=15; i<= 19; i++) walls.add(new Wall((offset+ i*50), bottomRow-350, s, s, grasLeft));
        for(int i=15; i<= 16; i++) walls.add(new Wall((offset+ i*50), bottomRow-400, s, s, grasLeft));
        for(int i=19; i<= 23; i++) walls.add(new Wall((offset+ i*50), bottomRow-500, s, s, grasLeft));
        for(int i=21; i<= 22; i++) walls.add(new Wall((offset+ i*50), bottomRow-550, s, s, grasLeft));
        for(int i=22; i<= 23; i++) walls.add(new Wall((offset+ i*50), bottomRow-500, s, s, grasLeft));
        for(int i=23; i<= 26; i++) walls.add(new Wall((offset+ i*50), bottomRow-450, s, s, grasLeft));
        walls.add(new Wall((offset+ 27*50), bottomRow-400, s, s, grasLeft));
        for(int i=27; i<= 28; i++) walls.add(new Wall((offset+ i*50), bottomRow-350, s, s, grasLeft));
        for(int i=28; i<= 29; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, grasLeft));
        for(int i=29; i<= 30; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, grasLeft));
        for(int i=30; i<= 36; i++) walls.add(new Wall((offset+ i*50), bottomRow-200, s, s, grasLeft));
        for(int i=33; i<= 34; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, grasLeft));
        walls.add(new Wall((offset+ 36*50), bottomRow-150, s, s, grasLeft));
        for(int i=36; i<= 39; i++) walls.add(new Wall((offset+ i*50), bottomRow-100, s, s, grasLeft));
        walls.add(new Wall((offset+ 39*50), bottomRow-50, s, s, grasLeft));
        walls.add(new Wall((offset+ 14*50), bottomRow-250, s, s, grasLeft));
        walls.add(new Wall((offset+ 26*50), bottomRow-400, s, s, grasLeft));

        //Stone
        for(int i=4; i<= 8; i++) walls.add(new Wall((offset+ i*50), bottomRow-50, s, s, stone));
        for(int i=15; i<= 38; i++) walls.add(new Wall((offset+ i*50), bottomRow-50, s, s, stone));
        walls.add(new Wall((offset+ 5*50), bottomRow-100, s, s, stone));
        for(int i=16; i<= 19; i++) walls.add(new Wall((offset+ i*50), bottomRow-100, s, s, stone));
        for(int i=24; i<= 35; i++) walls.add(new Wall((offset+ i*50), bottomRow-100, s, s, stone));
        for(int i=17; i<= 18; i++) walls.add(new Wall((offset+ i*50), bottomRow-150, s, s, stone));
        for(int i=26; i<= 35; i++) walls.add(new Wall((offset+ i*50), bottomRow-150, s, s, stone));
        walls.add(new Wall((offset+ 21*50), bottomRow-200, s, s, stone));
        for(int i=27; i<= 29; i++) walls.add(new Wall((offset+ i*50), bottomRow-200, s, s, stone));
        for(int i=21; i<= 24; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, stone));
        for(int i=27; i<= 28; i++) walls.add(new Wall((offset+ i*50), bottomRow-250, s, s, stone));
        for(int i=16; i<= 21; i++) walls.add(new Wall((offset+ i*50), bottomRow-300, s, s, stone));
        walls.add(new Wall((offset+ 20*50), bottomRow-350, s, s, stone));

    }


}
