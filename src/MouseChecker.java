import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseChecker implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int offset = MainFrame.panel.cameraX;

        if (e.getButton() == MouseEvent.BUTTON1) {
            float x = (e.getX() - offset) / 50f;
            int xPos = ((int) x) * 50;
            float y = e.getY() / 50f;
            int yPos = ((int) y) * 50;
            MainFrame.panel.walls.add(new Wall(xPos, yPos, MainFrame.panel.s, MainFrame.panel.s, MainFrame.panel.stone));
            Gaia.socketConnection.send("Wall:");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
