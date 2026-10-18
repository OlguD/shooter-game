import java.awt.Color;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shooter Game");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // frame.setUndecorated(true); Full screen ayarı.
        frame.setVisible(true);

        frame.getContentPane().setBackground(Color.black);
    }
}