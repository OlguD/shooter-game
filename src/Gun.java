import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Gun {
    private final BufferedImage image;
    private final static int SIZE = 32;
    private final static int GRIP_X = 16;
    private final static int GRIP_Y = -15;

    private double angle;

    public Gun(BufferedImage image) {
        this.image = image;
    }

    public void update(int fromX, int fromY, int targetX, int targetY) {
        this.angle = Math.atan2(targetY - fromY, targetX - fromX);
    }

    public Bullet shoot(double fromX, double fromY) {
        return new Bullet(fromX, fromY, angle);
    }

    public void draw(Graphics g, ImageObserver observer, int handX, int handY) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.translate(handX, handY);
        g2.rotate(angle);

        if (Math.cos(angle) < 0) {
            g2.scale(1, -1);
        }

        g2.drawImage(image, -GRIP_X, -GRIP_Y, SIZE, SIZE, observer);
        g2.dispose();
    }
}
