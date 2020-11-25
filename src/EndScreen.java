import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * EndScreen Displaying the Winner. And adding the possibility to restart the game
 */
public class EndScreen extends JFrame implements Runnable {
    /**
     * Container for structuring the Contents.
     */
    Container pane = getContentPane();
    /**
     * String where the name of the Winner is saved in. It is used to Display specific images, to show who won.
     */
    String winner;
    /**
     * BufferedImage of the Background when Hunter won
     */
    private BufferedImage bgHunter = null;

    /**
     * BufferedImage of the Background when Runner won
     */
    private BufferedImage bgRunner = null;
    /**
     * the JButton you can press to restart the game
     */
    JButton restartButton;
    /**
     * Icon of the Button
     */
    ImageIcon iconButton;

    /**
     * Constructor of Endscreen. It opens the loadImages and buttons class. It also sets the String given by {@link GamePanel} to String Winner, to be used.
     */
    public EndScreen(String winner) {
        loadImages();
        buttons();
        this.winner = winner;
        run();
    }

    /**
     * class for loading the images (Background, button) it also creates an Icon for the JButton.
     */
    public void loadImages() {
        try {
            bgHunter = ImageIO.read(getClass().getResource("Backgrounds/EndScreenHunter.png"));
            bgRunner = ImageIO.read(getClass().getResource("Backgrounds/EndScreenRunner.png"));
            BufferedImage button = ImageIO.read(getClass().getResource("Backgrounds/StartButtonEmpty.png"));
            iconButton = new ImageIcon(button);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * creates the restart button, sets its position and size and sets its actions.
     */
    public void buttons() {
        restartButton = new JButton(iconButton);
        restartButton.setBounds(804, 568, 312, 80);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reopenGame();
                dispose();
            }
        });
    }

    /**
     * class for (re-)opening the game. It gets called by the restart-button.
     */
    private void reopenGame() {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * run-method: sets the layout to null in order to position the JButton by Coordinates. Also adds restartButton to pane
     */
    @Override
    public void run() {
        pane.setLayout(null);
        pane.add(restartButton);
    }

    /**
     * @param g Graphics for painting images. It paints the Background always and then chooses between specific Images for Runner and Hunter, depending on who won.
     */
    public void paint(Graphics g) {
        super.paint(g);
        if (winner == "runner"){
            g.drawImage(bgRunner,0,0,null);
            g.drawImage(MainFrame.panel.runner.frame1Forward, 1200, 730, null);
        } else if (winner == "hunter"){
            g.drawImage(bgHunter,0,0,null);
            g.drawImage(MainFrame.panel.hunter.frame1Forward, 1200, 730, null);
        }
    }


}