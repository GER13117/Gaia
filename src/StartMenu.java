import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Graphics;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.TimerTask;

/**
 * Start-Menu
 */
public class StartMenu extends JFrame implements Runnable {
    Container pane = getContentPane();
    private BufferedImage bg = null;
    private BufferedImage button = null;
    JButton startButton;

    /**
     * Constructor of Start-Menu
     */
    public StartMenu() {
        buttons();
        loadImages();
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

    public void buttons(){
        startButton = new JButton((Icon) button);
        startButton.setBounds(804,568, 312,80);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
                dispose();
            }
        });
    }
    /**
     * run method, adds buttons and sets layout to null
     */
    @Override
    public void run() {
        this.add(startButton);
        pane.setLayout(null);
    }
    public void loadImages(){
        try {
            bg = ImageIO.read(getClass().getResource("Backgrounds/StartMenu.png"));
            button = ImageIO.read(getClass().getResource("Backgrounds/StartButton.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param g Graphics for painting images
     */
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(bg,0,0,null);

    }
}