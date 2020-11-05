import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.EventQueue;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


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
//TODO: Terrain: Fix chunk loading??
//TODO: Variable für temperaturen
//TODO: Regeln zum auswählen der texturen, (fix von topLayer[x])
//TODO: 2. Spieler hinzufügen? oder zweiten PVP Modus
//TODO: Menü (Möglichkeit die Lautstärke einzustellen)
//TODO: Schlagen, bauen
//TODO: Gegner
//TODO: Waterblock
//TODO: JavaDoc Kommentare erweitern