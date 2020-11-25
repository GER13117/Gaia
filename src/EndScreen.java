import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

//TODO: Add Image Headline that show "Runner won" or "Hunter won"

/**
 * EndScreen Displaying the Winner. And adding the possibility to restart the game
 */
public class EndScreen extends JFrame implements Runnable {
    /**
     *
     *///TODO: Was ist der Nutze eines Containers
    Container pane = getContentPane();
    /**
     * String where the name of the Winner is saved in. It is used to Display specific images, to show who won.
     */
    String winner;
    /**
     * BufferedImage of the Background
     */
    private BufferedImage bg = null;
    /**
     * BufferedImage of the Button.
     */
    private BufferedImage button = null;
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
            bg = ImageIO.read(getClass().getResource("Backgrounds/EndScreen.png"));
            button = ImageIO.read(getClass().getResource("Backgrounds/StartButtonEmpty.png"));
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
        g.drawImage(bg, 0, 0, null);
        if (winner == "runner"){
            g.drawImage(MainFrame.panel.runner.frame1Forward, 1200, 730, null);
        } else if (winner == "hunter"){
            g.drawImage(MainFrame.panel.hunter.frame1Forward, 1200, 730, null);
        }
    }


}