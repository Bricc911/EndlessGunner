import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class keyInput implements KeyListener{
    public boolean pressedD = false;
    public boolean pressedW = false;
    public boolean pressedA = false;
    public boolean pressedS = false;
    public boolean pressedQ = false;
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            pressedA = true;
        }

        if (key == KeyEvent.VK_D) {
            pressedD = true;
        }

        if (key == KeyEvent.VK_W) {
            pressedW = true;
        }

        if (key == KeyEvent.VK_S) {
            pressedS = true;
        }
         if (key == KeyEvent.VK_Q) {
            pressedQ = true;
            System.out.println("Q pressed");
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A) {
            pressedA = false;
        }

        if (key == KeyEvent.VK_D) {
            pressedD = false;
        }

        if (key == KeyEvent.VK_W) {
            pressedW = false;
        }

        if (key == KeyEvent.VK_S) {
            pressedS = false;
        }
         if (key == KeyEvent.VK_Q) {
            pressedQ = false;
        }

    }
}
