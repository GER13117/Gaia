import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static GamePanel panel;
    public static int gameMode;

    public MainFrame(int gameMode) {
        this.gameMode = gameMode;
        initUI(gameMode);
    }

    private void initUI(int gameMode) {
        if (gameMode == 1) {
            panel = new GamePanel();
            panel.setLocation(0, 0);
            panel.setSize(this.getSize());
            Color skyPlaceholder = new Color(78, 147, 202);
            panel.setBackground(skyPlaceholder);

            this.add(panel);

            addKeyListener(new KeyChecker(panel));
            panel.addMouseListener(new MouseChecker());
            panel.setVisible(true);

        } else if (gameMode == 2) {
            System.out.println("HA Verkackt den den gibts nicht mehr");
        }


    }


}
