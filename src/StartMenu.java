import javax.swing.JButton;
import java.awt.*;

/**
 * Start-Menu
 */
public class StartMenu extends Panel implements Runnable {
    JButton story = new JButton("Hunter");
    public static GamePanel panel;
    boolean panelVisible = false;
    MainFrame mainFrame;

    /**
     * Constructor of Start-Menu
     */
    public StartMenu() {
        add(story);
        run();
    }

    /**
     * Opens the MainFrame on which the GamePanel is placed
     */
    public void openGame() {
        mainFrame = new MainFrame();

        panel = new GamePanel();
        panel.setLocation(0, 0);
        panel.setSize(this.getSize());
        Color skyPlaceholder = new Color(78, 147, 202); //TODO: IMG oder Umbenennen
        panel.setBackground(skyPlaceholder);

        mainFrame.add(panel);
        mainFrame.validate();
        mainFrame.repaint();

        addKeyListener(new KeyChecker(panel));
        panel.addMouseListener(new MouseChecker());
        panel.setVisible(true);


    }


    /**
     * Method adding Buttons
     */
    public void buttons() {
        story.addActionListener(e -> {
            panelVisible = true;
        });

    }

    /**
     * run method
     */
    @Override
    public void run() {
        buttons();
        setLayout(new GridLayout(3, 3, 10, 10));
    }
}
