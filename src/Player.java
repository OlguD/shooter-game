import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Player {
    public static final int SIZE = 64;
    private static final int SPEED = 5;
    public static final int MAX_HEALTH = 100;
    private int health = MAX_HEALTH;
    private static final int FLASH_DURATION = 8;
    private final BufferedImage flashImage;
    private int flashTimer;

    private int x;
    private int y;
    private final BufferedImage image;
    private final Gun gun;

    public Player(int x, int y, BufferedImage image, Gun gun) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.gun = gun;
        this.flashImage = createFlashImage(image);
    }

    public void update(Input input, int width, int height) {
        if (flashTimer > 0) flashTimer--;
        if (width < SIZE || height < SIZE) return;

        if (input.up) y -= SPEED;
        if (input.down) y += SPEED;
        if (input.left) x -= SPEED;
        if (input.right) x += SPEED;

        x = Math.max(0, Math.min(x, width - SIZE));
        y = Math.max(0, Math.min(y, height - SIZE));

        gun.update(x + SIZE / 2, y + SIZE / 2, input.mouseX, input.mouseY);

    }

    public Bullet tryShoot(Input input) {
        if (!input.shooting) return null;

        double handX = x + SIZE / 2.0;
        double handY = y + SIZE / 2.0;
        return gun.shoot(handX, handY);
    }

    public void takeDamage(int damage) {
        if (damage <= 0 || isDead()) return;
        health = Math.max(0, health - damage);
        flashTimer = FLASH_DURATION;
    }

    public void heal(int amount) {
        health = Math.min(MAX_HEALTH, health + amount);
    }

    public boolean isDead() {
        return health == 0;
    }

    public int getHealth() {
        return health;
    }

    private static BufferedImage createFlashImage(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = out.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);   // sadece dolu piksellerin üstüne boya
        g2.setColor(new Color(255, 60, 60));
        g2.fillRect(0, 0, src.getWidth(), src.getHeight());
        g2.dispose();
        return out;
    }

    public void draw(Graphics g, ImageObserver observer) {
        BufferedImage current = flashTimer > 0 ? flashImage : image;
        g.drawImage(current, x, y, SIZE, SIZE, observer);
        gun.draw(g, observer, x + SIZE / 2 - SIZE / 2, y + SIZE / 2 - SIZE / 2);
    }
}