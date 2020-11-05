import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame implements Runnable{
    Container pane = getContentPane();
    JButton pvp = new JButton("PVP");
    JButton story = new JButton("Jump 'n' run");

    public StartMenu(){
        run();
    }

    public void openGame(){
        MainFrame frame = new MainFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setResizable(true);

        frame.setTitle("Gaia");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void buttons(){
        story.setPreferredSize(new Dimension(200,200));
        pane.add(story);
        story.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
                dispose();
            }
        });

    }

    @Override
    public void run() {
        buttons();
        pane.setLayout(new GridLayout(3,3,10,10));
    }
}
