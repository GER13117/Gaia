import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates GameFrame where the {@link GamePanel} is created on.
 */
public class MainFrame extends JFrame {
    /**
     * Instance of the GamePanel to create it
     */
    public static GamePanel panel;
    public MainFrame() {
        panel = new GamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        Color skyPlaceholder = new Color(78, 147, 202);
        panel.setBackground(skyPlaceholder);

        this.add(panel);

        addKeyListener(new KeyChecker(panel));
        panel.addMouseListener(new MouseChecker());
        panel.setVisible(true);
    }
}
