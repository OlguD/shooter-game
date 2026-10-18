import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private static final int SIZE = 10;
    private static final int SPEED = 12;

    public Bullet(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.vx = SPEED * Math.cos(angle);
        this.vy = SPEED * Math.sin(angle);
    }

    public void update() {
        x += vx;
        y += vy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
    }
}
