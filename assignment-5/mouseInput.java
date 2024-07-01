import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Math.*;

class mouseInput implements MouseListener {
    public boolean isHeld = false;

    public mouseInput() {
    }

    public void mousePressed(MouseEvent e) {
        isHeld = true;

    }



    public void mouseReleased(MouseEvent e) {
        isHeld = false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}