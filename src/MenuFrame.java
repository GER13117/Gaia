import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MenuFrame extends JFrame implements Runnable{
    //Member
    JMenuBar menuBar = new JMenuBar();
    public MenuFrame(){
        run();
    }

    @Override
    public void run() {
        MenuBar();

    }
    public void MenuBar(){
        setJMenuBar(menuBar);
        System.out.println("MENÜÜÜ");
    }
}
