import javax.swing.JFrame;
import java.awt.Color;

public class MainFrame extends JFrame {
    public MainFrame(){
        GamePanel panel = new GamePanel();
        panel.setLocation(0,0);
        panel.setSize(this.getSize());
        Color skyPlaceholder =new Color (78,147,202);
        panel.setBackground(skyPlaceholder);
        panel.setVisible(true);
        this.add(panel);

        addKeyListener(new KeyChecker(panel));
    }
}
