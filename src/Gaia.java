import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * MainMethod
 */
public class Gaia {
    public static SocketConnection socketConnection;

    public static void main(String[] args) {
        StartMenu startMenu = new StartMenu();
        startMenu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        startMenu.setLocationRelativeTo(null);

        startMenu.setResizable(true);

        startMenu.setTitle("Gaia");
        startMenu.setVisible(true);
        startMenu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        socketConnection = new SocketConnection("localhost", 1337);
    }
}


//TODO: Texturen und Animationen
//TODO: weitere Blöcke / Materialien hinzufügen
//TODO: Regeln zum auswählen der texturen, (fix von topLayer[x])
//TODO: Menü (Möglichkeit die IP des Servers einzustellen)
//TODO: Gegner von Server Steuern lassen
//TODO: TerrainGen auf Server
//TODO: X und Y Position zum Server senden und empfangen
//TODO: JavaDoc Kommentare erweitern