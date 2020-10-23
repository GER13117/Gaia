import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
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



    public GamePanel(){
        //einfügen des Spiel-Objekts
        player = new Player(400,300,this);
        player2 = new Player2(500, 300, this);
        makeWalls();

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

    public void makeWalls() {
        int squareSize = 50;
        for(int i = 50; i < 1870; i += 50){
            walls.add(new Wall(i, 600, 50, 50));

        }
        //Left endwall
        walls.add(new Wall(50, 550, 50, 50));
        walls.add(new Wall(50, 500, 50, 50));
        walls.add(new Wall(50, 450, 50, 50));

        //middle walls frome left to right
        walls.add(new Wall(450, 550, 50, 50));

        walls.add(new Wall(600, 500, 50, 50));
        walls.add(new Wall(600, 550, 50, 50));

        walls.add(new Wall(650, 550, squareSize, squareSize));
        walls.add(new Wall(650, 500, squareSize, squareSize));
        walls.add(new Wall(650, 450, squareSize, squareSize));

        walls.add(new Wall(700, 500, 50, 50));
        walls.add(new Wall(700, 550, 50, 50));

        walls.add(new Wall(750, 550, 50, 50));


        //right endwall
        walls.add(new Wall(1850, 450, squareSize, squareSize ));
        walls.add(new Wall(1850, 500, squareSize, squareSize ));
        walls.add(new Wall(1850, 550, squareSize, squareSize ));

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
