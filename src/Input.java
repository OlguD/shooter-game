import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    public boolean up, down, left, right;

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

    // Basma ve bırakma aynı mantık, sadece değer farklı
    private void setKey(int key, boolean pressed) {
        switch (key) {
            case KeyEvent.VK_W -> up = pressed;
            case KeyEvent.VK_S -> down = pressed;
            case KeyEvent.VK_A -> left = pressed;
            case KeyEvent.VK_D -> right = pressed;
        }
    }
}

// @Override
//     public void keyPressed(KeyEvent e) {
//         int key = e.getKeyCode();
//         if (key == KeyEvent.VK_A) {
//             left = true;
//         } else if (key == KeyEvent.VK_D) {
//             right = true;
//         } else if (key == KeyEvent.VK_W) {
//             up = true;
//         } else if (key == KeyEvent.VK_S) {
//             down = true;
//         }

//     }
//     @Override 
//     public void keyReleased(KeyEvent e) {
//         int key = e.getKeyCode();
//         if (key == KeyEvent.VK_A) {
//             left = false;
//         } else if (key == KeyEvent.VK_D) {
//             right = false;
//         } else if (key == KeyEvent.VK_W) {
//             up = false;
//         } else if (key == KeyEvent.VK_S) {
//             down = false;
//         }
//     }

//     @Override 
//     public void keyTyped(KeyEvent e) {
//         // No action needed on key typed for now
//     } 
