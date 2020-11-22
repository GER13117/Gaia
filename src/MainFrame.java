import javax.swing.JFrame;
import java.awt.*;

/**
 * Creates MainFrame
 */
public class MainFrame extends JFrame {
    public static StartMenu startMenu;
    public static GamePanel panel;

    public MainFrame() {
        startMenu = new StartMenu();
        gamePanelInit();
        /*if (!startMenu.panelVisible) startMenuInit();
        else gamePanelInit();*/
    }
    public void startMenuInit(){
        startMenu = new StartMenu();
        startMenu.setLocation(0,0);
        startMenu.setSize(this.getSize());
        Color menuColorPlaceholder = new Color(57, 83, 31); //TODO: IMG oder umbenennen
        startMenu.setBackground(menuColorPlaceholder);
        this.add(startMenu);
        startMenu.setVisible(true);
    }

    public void gamePanelInit(){
        remove(startMenu);
        validate();
        repaint();
        panel = new GamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        Color skyPlaceholder = new Color(78, 147, 202); //TODO: IMG oder Umbenennen
        panel.setBackground(skyPlaceholder);
        this.add(panel);
        addKeyListener(new KeyChecker(panel));
        panel.addMouseListener(new MouseChecker());
        panel.setVisible(true);
    }


}
