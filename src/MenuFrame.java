import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements Runnable {
    //Member
    JMenuBar menuBar = new JMenuBar();
    Container pane = getContentPane();
    JButton leaveGame = new JButton("Leave Game");
    JButton backToGame = new JButton("Continue");

    public MenuFrame() {
        run();
    }

    @Override
    public void run() {
        pane.setLayout(new FlowLayout());
        MenuBar();
        Buttons();
    }

    public void MenuBar() {
        JMenu test = new JMenu("test");
        setJMenuBar(menuBar);
        menuBar.add(test);
    }

    public void Buttons() {
        pane.add(leaveGame);
        leaveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Stattdessen ins Startmenü zurückkehren
                System.exit(0);
            }
        });

        pane.add(backToGame);
        backToGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
    }
}
