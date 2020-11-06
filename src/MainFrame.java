import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static GamePanel panel;
    public static GamePanelPvP panelPvP;
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
            panel.setVisible(true);
            this.add(panel);

            addKeyListener(new KeyChecker(panel));
            panel.addMouseListener(new MouseChecker());

        } else if (gameMode == 2) {
            panelPvP = new GamePanelPvP();
            panelPvP.setLocation(0, 0);
            panelPvP.setSize(this.getSize());
            Color skyPlaceholder = new Color(78, 147, 202);
            panelPvP.setBackground(skyPlaceholder);
            panelPvP.setVisible(true);
            this.add(panelPvP);

            addKeyListener(new KeyCheckerPvP(panelPvP));
            panelPvP.addMouseListener(new MouseChecker());
        }


    }


}
