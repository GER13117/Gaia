import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Menu which opens ingame
 */
public class MenuFrame extends JFrame implements Runnable {
    //Member
    JMenuBar menuBar = new JMenuBar();
    Container pane = getContentPane();
    JButton leaveGame = new JButton("Leave Game");
    JButton backToGame = new JButton("Continue");
    Desktop desktop;

    public MenuFrame() {
        run();
    }

    /**
     * sets the Layout to FlowLayout and adds the MenuBar and the buttons method.
     */
    @Override
    public void run() {
        pane.setLayout(new FlowLayout());
        menuBar();
        buttons();
    }

    /**
     * Menubar: Usage unknown
     */
    public void menuBar() {
        JMenu rules = new JMenu("Rules");
        setJMenuBar(menuBar);
        menuBar.add(rules);
        JMenuItem rulesItem = new JMenuItem("Readme");
        rules.add(rulesItem);
        rulesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                URL url = null;

                try {
                    url = new URL("https://github.com/GER13117/Gaia/blob/master/README.md");
                } catch (MalformedURLException malformedURLException) {
                    malformedURLException.printStackTrace();
                }
                try {
                    Desktop.getDesktop().browse(url.toURI());
                } catch (IOException | URISyntaxException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    /**
     * buttons for leaving or continuing the game
     */
    public void buttons() {
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
