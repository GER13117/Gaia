import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Menu which opens ingame
 */
public class MenuFrame extends JFrame implements Runnable {
    //Member
    JMenuBar menuBar = new JMenuBar();
    Container pane = getContentPane();
    JButton leaveGame = new JButton("Leave Game");
    JButton backToGame = new JButton("Continue");

    public MenuFrame() {
        run();
    }

    @Override
    public void run() {
        pane.setLayout(new FlowLayout());
        MenuBar();
        Buttons();
    }

    /**
     * Menubar: Usage unknown
     */
    public void MenuBar() {
        JMenu test = new JMenu("test");
        setJMenuBar(menuBar);
        menuBar.add(test);
    }

    /**
     * buttons for leaving or continuing the game
     */
    public void Buttons() {
        pane.add(leaveGame);
        leaveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Stattdessen ins Startmenü zurückkehren
                System.exit(0);
            }
        });

        pane.add(backToGame);
        backToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
