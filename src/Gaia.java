import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * MainMethod
 */
public class Gaia {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setResizable(true);

        mainFrame.setTitle("Gaia");
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}


//TODO: Animationen und 2. Charakter
//TODO: bessere Musik
//TODO: randomiser, um zwischen verschiedenen Steinen zu wählen
//TODO: EndScreen designen
//TODO: JavaDoc Kommentare erweitern
//TODO: TerrainGen fixen!!!!!