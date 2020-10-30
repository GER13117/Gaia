import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static GamePanel panel;

    public MainFrame() {
        panel = new GamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        Color skyPlaceholder = new Color(78, 147, 202);
        panel.setBackground(skyPlaceholder);
        panel.setVisible(true);
        this.add(panel);

        addKeyListener(new KeyChecker(panel));
        panel.addMouseListener(new MouseChecker());
    }
}
