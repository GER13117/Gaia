import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel implements ActionListener {
    Player player;
    Timer gameTimer;
    Player2 player2;
    ArrayList<Wall> walls = new ArrayList<>();
    //Variablen zum Definieren der Kamerposition
    int cameraX;
    int offset;

    //Fenstergröße für vereinfachte verwendung
    int windowHeight = 1080;
    int windowWidth = 1920;


    public GamePanel(){
        //einfügen des Spiel-Objekts
        player = new Player(400,300,this);
        player2 = new Player2(500, 300, this);

        reset1();

        //Timer um Spiel laufen zu lassen.
        gameTimer = new Timer();
        gameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //zeichnet walls wenn sie  kurz davor sind ins sichtfeld zu kommen
                if(walls.get(walls.size() - 1).x < (windowWidth + 100) ){
                    offset += windowWidth;
                    makeWalls(offset);
                    System.out.println(walls.size());

                }

                player.set();
                player2.set();
                for( Wall wall: walls){
                    wall.set(cameraX);
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
        player.x = 200;
        player.y = 150;
        cameraX = (150 + player2.x) / 2;
        player.xspeed = 0;
        player.yspeed = 0;
        walls.clear();
        offset = -150;
        makeWalls(offset);
    }

    public void reset2(){
        player2.x = 300;
        player2.y = 150;
        cameraX = (player.x + 150) / 2;
        player2.xspeed = 0;
        player2.yspeed = 0;
        walls.clear();
        offset = -150;
        makeWalls(offset);
    }

    public void makeWalls(int offset) {
        int s = 50;
        int bottomRow = windowHeight - 100;
        Random rand = new Random();
        int index = rand.nextInt(1);

        switch (index){
            case 0:
                for(int i=0; i<40; i++) {
                    walls.add(new Wall((offset + i*50), bottomRow, s, s));
                    walls.add(new Wall((offset+ 3*50), (bottomRow-150), s, s));
                }
            break;
            case 1:
                //for-Schleife mit dem Aufbau
            break;
            default:
                for(int i=0; i<40; i++) {
                    walls.add(new Wall((offset + i*50), bottomRow, s, s));
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
        player2.draw(gtd);

        for(Wall wall: walls){
            wall.draw(gtd);
        }
    }




    public void keyPressed(KeyEvent e) {
        //movement player 1
        if(e.getKeyChar() == 'a') player.keyLeft = true;
        if(e.getKeyChar() == 'd') player.keyRight = true;
        if(e.getKeyChar() == 'w') player.keyUp = true;
        if(e.getKeyChar() == 's') player.keyLeft = true;
        //respawn
        if(e.getKeyChar() == 'r') reset1();
        //movement player 2
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player2.keyDown = true;
        if(e.getKeyCode() == KeyEvent.VK_NUMPAD7) reset2();

    }

    public void keyReleased(KeyEvent e) {
        //stop movement player1
        if(e.getKeyChar() == 'a') player.keyLeft = false;
        if(e.getKeyChar() == 'd') player.keyRight = false;
        if(e.getKeyChar() == 'w') player.keyUp = false;
        if(e.getKeyChar() == 's') player.keyLeft = false;
        //stop movement player2
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = false;
        if(e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = false;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player2.keyDown = false;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
    }
}
