import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * MainMethod
 */
public class Gaia {
    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu();
        startMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        startMenu.setLocationRelativeTo(null);

        startMenu.setResizable(true);

        startMenu.setTitle("Gaia");
        startMenu.setVisible(true);
        startMenu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}


//TODO: Animationen und 2. Charakter
//TODO: randomiser, um zwischen verschiedenen Steinen zu wählen
//TODO: EndScreen öffnet sich nur einmal und noch designen
//TODO: JavaDoc Kommentare erweitern
//TODO: TerrainGen fixen!!!!!