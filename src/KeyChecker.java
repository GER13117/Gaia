import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyChecker to check which keys are pressed. The methods are found in {@link GamePanel}
 */
public class KeyChecker extends KeyAdapter {
    /**
     * Instance of the GamePanel
     */
    GamePanel panel;

    /**
     * @param panel panel, where the needed methods are
     */
    public KeyChecker(GamePanel panel) {
        this.panel = panel;
    }

    /**
     * @param e checks if a Key was pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }

    /**
     * @param e checks if a pressed-Key got released
     */
    @Override
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
    }
}
