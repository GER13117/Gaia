import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * Main-Method. Opens {@link StartMenu}.
 */
public class Main {
    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu();
        startMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        startMenu.setLocationRelativeTo(null);

        startMenu.setResizable(true);

        startMenu.setTitle("Gaia");
        startMenu.setVisible(true);
        startMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}


//TODO: randomiser, um zwischen verschiedenen Steinen zu wählen
//TODO: EndScreen: IMG für den jeweiligen Gewinner
//TODO: JavaDoc Kommentare erweitern
//TODO: TerrainGen fixen!!!!!