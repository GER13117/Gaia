import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

//TODO: Add Image Headline that show "Runner won" or "Hunter won"

/**
 * EndScreen Displaying the Winner. And adding the possibility to restart the game
 */
public class EndScreen extends JFrame implements Runnable {
    Container pane = getContentPane();
    String winner;
    private BufferedImage bg = null;
    private BufferedImage button = null;
    JButton restartButton;
    ImageIcon iconButton;

    /**
     * Constructor of Endscreen
     */
    public EndScreen(String winner) {
        loadImages();
        buttons();
        this.winner = winner;
        run();
    }

    public void loadImages() {
        try {
            bg = ImageIO.read(getClass().getResource("Backgrounds/EndScreen.png"));
            button = ImageIO.read(getClass().getResource("Backgrounds/StartButtonEmpty.png"));
            iconButton = new ImageIcon(button);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    private void reopenGame() {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    @Override
    public void run() {
        pane.setLayout(null);
        pane.add(restartButton);
    }

    /**
     * @param g Graphics for painting images
     */
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(bg, 0, 0, null);
    }


}