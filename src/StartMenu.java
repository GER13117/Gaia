import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start-Menu
 */
public class StartMenu extends JFrame implements Runnable {
    Container pane = getContentPane();
    JButton story = new JButton("Hunter");

    /**
     * Constructor of Start-Menu
     */
    public StartMenu() {
        run();
    }

    /**
     * Opens the MainFrame on which the GamePanel is placed
     */
    public void openGame() {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * Method adding Buttons
     */
    public void buttons() {
        pane.add(story);
        story.addActionListener(e -> {
            openGame();
            dispose();
        });

    }

    /**
     * run method
     */
    @Override
    public void run() {
        buttons();
        pane.setLayout(new GridLayout(3, 3, 10, 10));
    }
}
