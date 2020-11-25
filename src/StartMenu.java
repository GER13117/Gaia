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
 * Start-Menu is shown right after start. Instanced in {@link Main}.
 */
public class StartMenu extends JFrame implements Runnable {
    /**
     * container for structuring the Images and Buttons.
     */
    Container pane = getContentPane();
    /**
     * Background Image.
     */
    private BufferedImage bg = null;
    /**
     * JButton to start the game
     */
    JButton startButton;
    /**
     * Icon of the Image. It's an empty png to make the button invisible.
     */
    ImageIcon iconButton;
    /**
     * Instance of {@link MainFrame} for starting the actual game
     */
    private MainFrame frame;

    /**
     * Constructor of Start-Menu. Instantiates the buttons, loadImages and the run Method.
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
        frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Creates the JButton, sets it's bounds and it's action.
     */
    public void buttons(){
        startButton = new JButton(iconButton);
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

    /**
     * Loads the Images for using them as Background or setting them as IconImage for using them with the JButton.
     */
    public void loadImages(){
        try {
            bg = ImageIO.read(getClass().getResource("Backgrounds/StartMenu.png"));
            BufferedImage button = ImageIO.read(getClass().getResource("Backgrounds/StartButtonEmpty.png"));
            iconButton = new ImageIcon(button);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Paints all objects.
     * @param g Graphics for painting images
     */
    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(bg,0,0,null);

    }
}