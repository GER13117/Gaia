import javax.swing.JFrame;
import java.awt.Color;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static GamePanel panel;

    public MainFrame() {
        initUI();
    }

    /**
     * initializes UI
     */
    private void initUI() {

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

    public void disposeIt(){
        dispose();
    }


}
