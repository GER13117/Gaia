import java.util.Timer;
import java.util.TimerTask;

public class Animation extends Thread {
    public Animation() {
        Timer frameTimer = new Timer();
        frameTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }, 0, 200);
    }
}
