import javax.swing.*;
import java.awt.*;


/**
 * EndScreen Displaying the Winner. And adding the possibility to restart the game
 */
public class EndScreen extends JPanel implements Runnable {
    String winner;

    /**
     * Constructor of Endscreen
     */
    public EndScreen(String winner) {
        this.winner = winner;
        run();
    }

    public void paint(Graphics g) {
        g.drawString(winner, 500, 500);

    }

    @Override
    public void run() {

    }
}
