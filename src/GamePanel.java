import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class GamePanel extends JPanel implements ActionListener{
    Player player;
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
    int windowWidth = 2500;//die renderdistanz ist damit gemeint
    int bottomRow = windowHeight - 100;

    //Import Images for the different solids
    private BufferedImage spriteSheet = null;
    private BufferedImage spriteSheetStone = null;
    BufferedImage stone;
    BufferedImage dirt;
    BufferedImage gras;
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
        loader = new BufferedImageLoader();
        try{
            spriteSheet = loader.loadImage("textures/gras_dirt_sprite.png");
        }catch (IOException e){
            e.printStackTrace();
        }
        ss = new SpriteSheet(spriteSheet);
        BufferedImage grasLeft = ss.grabImage(1,1, s, s);
        dirt = ss.grabImage(2,2 ,s,s);
        gras = ss.grabImage(2,1,s,s);
        BufferedImage dirtLeftDown = ss.grabImage(1,3,s,s);
        BufferedImage grasRight = ss.grabImage(3,1,s,s);
        BufferedImage dirtRight = ss.grabImage(3,2,s,s);
        BufferedImage grasRightDown = ss.grabImage(3,3,s,s);

        try {
            spriteSheetStone = loader.loadImage("textures/stone_sprite.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stoneSheet = new SpriteSheet(spriteSheetStone);
        stone = stoneSheet.grabImage(2,1,s,s);
        BufferedImage  stoneRight = stoneSheet.grabImage(3,1,s,s);
        BufferedImage stoneLeft = stoneSheet.grabImage(1,1,s,s);
        int height = 500;
        terrainGen(height);


    }


    public void paint(Graphics g){
        super.paint(g);

        Graphics2D gtd = (Graphics2D) g;
        player.draw(gtd);

        for(Wall wall: walls){
            wall.draw(gtd);
        }
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
        public void terrainGen(int height){
            for (int x = 0; x < 50; x++){
                int minHeight = (height) - 50;
                int maxHeight = (height) + 100;
                height = ((int) (Math.random() * (maxHeight-minHeight) + minHeight)/50) * 50;
                int minStoneSpawnDistance = height + 250;
                int maxStoneSpawnDistance = height + 300;
                int totalSpawnDistance = ((int) (Math.random() * (maxStoneSpawnDistance-minStoneSpawnDistance) + minStoneSpawnDistance)/50) * 50;
                for (int y = 1100; y > height; y-=50){
                    if (y>totalSpawnDistance){
                        walls.add(new Wall((offset + x*50), y, s, s, stone));
                    } else {
                        walls.add(new Wall((offset + x*50), y, s, s, dirt));

                    }
                }
                walls.add(new Wall((offset + x*50), height, s, s, gras));
            
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
}
