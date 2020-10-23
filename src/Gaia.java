import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Gaia {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setSize(700,700);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
