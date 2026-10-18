import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseMotionListener, MouseListener {
    public boolean up, down, left, right;
    public int mouseX, mouseY;
    public boolean shooting;
    public boolean damageKey;


    @Override
    public void keyPressed(KeyEvent e) {
        setKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        setKey(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override 
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override 
    public void mousePressed(MouseEvent e) {
        shooting = true;
    }

    @Override 
    public void mouseReleased(MouseEvent e) {
        shooting = false;
    }

    @Override 
    public void mouseClicked(MouseEvent e) {}

    @Override 
    public void mouseEntered(MouseEvent e) {}

    @Override 
    public void mouseExited(MouseEvent e) {}

    // Basma ve bırakma aynı mantık, sadece değer farklı
    private void setKey(int key, boolean pressed) {
        switch (key) {
            case KeyEvent.VK_W -> up = pressed;
            case KeyEvent.VK_S -> down = pressed;
            case KeyEvent.VK_A -> left = pressed;
            case KeyEvent.VK_D -> right = pressed;
            case KeyEvent.VK_H -> damageKey = pressed;
        }
    }
}