import javax.imageio.ImageIO;
import javax.swing.*;
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
    Container pane = getContentPane();
    String winner;
    private BufferedImage bg = null;
    private BufferedImage button = null;
    JButton restartButton;

    /**
     * Constructor of Endscreen
     */
    public EndScreen(String winner) {
        buttons();
        loadImages();
        this.winner = winner;
        run();
    }

    public void loadImages(){
        try {
            bg = ImageIO.read(getClass().getResource("Backgrounds/StartMenu.png"));
            button = ImageIO.read(getClass().getResource("Backgrounds/StartButtonEmpty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buttons(){
        restartButton = new JButton((Icon) button);
        restartButton.setBounds(804,568, 312,80);
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

    /**
     * @param g Graphics for painting images
     */
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(bg,0,0,null);

    }

    @Override
    public void run() {
        this.add(restartButton);
        pane.setLayout(null);

    }
}