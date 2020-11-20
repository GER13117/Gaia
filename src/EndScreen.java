import javax.swing.JFrame;
import java.awt.Container;


/**
 * EndScreen Displaying the Winner. And adding the possibility to restart the game
 */
public class EndScreen extends JFrame implements Runnable{
    Container pane = getContentPane();

    /**
     * Constructor of Endscreen
     */
    public EndScreen() {
        run();
    }

    @Override
    public void run() {

    }
}
