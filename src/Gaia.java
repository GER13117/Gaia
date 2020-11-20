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


//TODO: Texturen und Animationen
//TODO: weitere Blöcke / Materialien hinzufügen
//TODO: Regeln zum auswählen der texturen, (fix von topLayer[x])
//TODO: Menü (Möglichkeit die IP des Servers einzustellen
//TODO: JavaDoc Kommentare erweitern