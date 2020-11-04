import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.Container;

public class MenuFrame extends JFrame implements Runnable{
    //Member
    JMenuBar menuBar = new JMenuBar();
    Container pane = getContentPane();
    public MenuFrame(){
        run();
    }

    @Override
    public void run() {
        //pane.setLayout();
        MenuBar();

    }
    public void MenuBar(){
        JMenu test = new JMenu("test");
        setJMenuBar(menuBar);
        System.out.println("MENÜÜÜ");
        menuBar.add(test);
    }
}
