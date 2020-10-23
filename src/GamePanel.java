import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel implements ActionListener {
    Player player;
    Timer gameTimer;
    Player2 player2;
    ArrayList<Wall> walls = new ArrayList<>();
    //Variablen zum Definieren der Kamerposition
    int cameraX;

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
                player.set();
                player2.set();
                for( Wall wall: walls){
                    wall.set(cameraX);
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
        int offset = 50;
        makeWalls(offset);
    }

    public void reset2(){
        player2.x = 300;
        player2.y = 150;
        cameraX = (player.x + 150) / 2;
        player2.xspeed = 0;
        player2.yspeed = 0;
        walls.clear();
        int offset = 50;
        makeWalls(offset);
    }

    public void makeWalls(int offset) {
        int squareSize = 50;
        for(int i = 50; i < windowWidth-50; i += 50){
            walls.add(new Wall(i, windowHeight-80, 50, 50));

        }
        int bottomRow = windowHeight - 80;
        //Left endwall
        walls.add(new Wall(50, bottomRow-50, 50, 50));

        //middle walls from left to right
        walls.add(new Wall(450, bottomRow-50, 50, 50));

        walls.add(new Wall(600, bottomRow-100, 50, 50));
        walls.add(new Wall(600, bottomRow-50, 50, 50));

        walls.add(new Wall(650, bottomRow-50, squareSize, squareSize));
        walls.add(new Wall(650, bottomRow-100, squareSize, squareSize));
        walls.add(new Wall(650, bottomRow-150, squareSize, squareSize));

        walls.add(new Wall(700, bottomRow-100, 50, 50));
        walls.add(new Wall(700, bottomRow-50, 50, 50));

        walls.add(new Wall(750, bottomRow-50, 50, 50));


        //right endwall
        walls.add(new Wall(1850, bottomRow-150, squareSize, squareSize ));
        walls.add(new Wall(1850, bottomRow-100, squareSize, squareSize ));
        walls.add(new Wall(1850, bottomRow-50, squareSize, squareSize ));

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
        //movement player 2
        if(e.getKeyCode() == KeyEvent.VK_LEFT) player2.keyLeft = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) player2.keyRight = true;
        if(e.getKeyCode() == KeyEvent.VK_UP) player2.keyUp = true;
        if(e.getKeyCode() == KeyEvent.VK_DOWN) player2.keyDown = true;

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
