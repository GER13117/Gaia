import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static GamePanel panel;
    public MainFrame(int gameMode) {
        initUI(gameMode);
    }

    private void initUI(int gameMode){

        panel = new GamePanel(gameMode);
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
