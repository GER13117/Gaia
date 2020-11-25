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

    public KeyChecker(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        panel.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        panel.keyReleased(e);
    }
}
