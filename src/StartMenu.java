import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame implements Runnable {
    Container pane = getContentPane();
    JButton story = new JButton("Hunter");

    public StartMenu() {
        run();
    }

    public void openGame() {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void buttons() {
        pane.add(story);
        story.addActionListener(e -> {
            openGame();
            dispose();
        });

    }

    @Override
    public void run() {
        buttons();
        pane.setLayout(new GridLayout(3, 3, 10, 10));
    }
}
