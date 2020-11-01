import javax.swing.JFrame;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


/**
 * MainMethod
 */
public class Gaia {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}


//TODO: Texturen und Animationen
//TODO: SpriteSheet Stein
//TODO: weitere Blöcke / Materialien hinzufügen
//TODO: Terrain: Fix chunk loading??
//TODO: Variable für temperaturen
//TODO: Regeln zum auswählen der texturen, (fix von topLayer[x])
//TODO: 2. Spieler hinzufügen? oder zweiten PVP Modus
//TODO: Menü (Möglichkeit die Lautstärke einzustellen)
//TODO: Schlagen, bauen
//TODO: Gegner
//TODO: Waterblock
//TODO: Snake Minigame
//TODO: JavaDoc Kommentare erweitern