import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Player {
    public static final int SIZE = 64;
    private static final int SPEED = 5;

    private int x;
    private int y;
    private final BufferedImage image;

    public Player(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void update(Input input, int width, int height) {
        if (width < SIZE || height < SIZE) return;

        if (input.up) y -= SPEED;
        if (input.down) y += SPEED;
        if (input.left) x -= SPEED;
        if (input.right) x += SPEED;

        x = Math.max(0, Math.min(x, width - SIZE));
        y = Math.max(0, Math.min(y, height - SIZE));
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, x, y, SIZE, SIZE, observer);
    }
}