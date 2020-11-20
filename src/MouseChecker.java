import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * checks if the Mouse is pressed
 */
public class MouseChecker implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * @param e MouseEvent for detecting Mouse-Press
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int offset = MainFrame.panel.cameraX;

        if (e.getButton() == MouseEvent.BUTTON1) {
            float x = (e.getX() - offset) / 50f;
            int xPos = ((int) x) * 50;
            float y = e.getY() / 50f;
            int yPos = ((int) y) * 50;
            MainFrame.panel.walls.add(new Wall(xPos, yPos, MainFrame.panel.s, MainFrame.panel.s, MainFrame.panel.stone));
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
