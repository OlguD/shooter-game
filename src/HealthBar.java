import java.awt.*;
import java.util.Random;

public class HealthBar {
    private static final int X = 16;
    private static final int Y = 16;
    private static final int WIDTH = 240;
    private static final int HEIGHT = 22;

    private static final int GHOST_DELAY = 25;       // hayalet bar erimeden önce kaç kare beklesin
    private static final double GHOST_SPEED = 0.8;   // her karede ne kadar erisin
    private static final int SHAKE_DURATION = 12;
    private static final int SHAKE_STRENGTH = 4;

    private final Random random = new Random();
    private double ghostHealth;
    private int lastHealth;
    private int ghostDelayTimer;
    private int shakeTimer;

    public HealthBar(int startHealth) {
        this.ghostHealth = startHealth;
        this.lastHealth = startHealth;
    }

    // Her karede çağrılır: hasarı fark eder ve efekt sayaçlarını ilerletir
    public void update(int health) {
        if (health < lastHealth) {
            ghostDelayTimer = GHOST_DELAY;
            shakeTimer = SHAKE_DURATION;
        } else if (health > lastHealth) {
            ghostHealth = Math.max(ghostHealth, health);
        }
        lastHealth = health;

        if (ghostDelayTimer > 0) {
            ghostDelayTimer--;
        } else if (ghostHealth > health) {
            ghostHealth = Math.max(health, ghostHealth - GHOST_SPEED);
        }

        if (shakeTimer > 0) shakeTimer--;
    }

    public void draw(Graphics g, int health, int maxHealth) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Sarsıntı: süre azaldıkça şiddeti de azalır
        int x = X, y = Y;
        if (shakeTimer > 0) {
            int s = SHAKE_STRENGTH * shakeTimer / SHAKE_DURATION + 1;
            x += random.nextInt(2 * s + 1) - s;
            y += random.nextInt(2 * s + 1) - s;
        }

        // Gölge
        g2.setColor(new Color(0, 0, 0, 120));
        g2.fillRect(x + 3, y + 3, WIDTH, HEIGHT);

        // Boş kısım
        g2.setColor(new Color(40, 20, 30));
        g2.fillRect(x, y, WIDTH, HEIGHT);

        // Hayalet bar (kaybedilen can)
        int ghostWidth = (int) (WIDTH * ghostHealth / maxHealth);
        g2.setColor(new Color(255, 240, 200));
        g2.fillRect(x, y, ghostWidth, HEIGHT);

        // Asıl can
        int fillWidth = WIDTH * health / maxHealth;
        g2.setColor(colorFor(health, maxHealth));
        g2.fillRect(x, y, fillWidth, HEIGHT);

        // Üstte ince parlama şeridi
        g2.setColor(new Color(255, 255, 255, 70));
        g2.fillRect(x, y, fillWidth, HEIGHT / 4);

        // Çerçeve
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(x, y, WIDTH, HEIGHT);

        // Yazı (gölgeli)
        String text = health + " / " + maxHealth;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 9, y + 16);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x + 8, y + 15);

        g2.dispose();
    }

    private Color colorFor(int health, int maxHealth) {
        double ratio = (double) health / maxHealth;
        if (ratio > 0.6) return new Color(76, 187, 23);   // yeşil
        if (ratio > 0.3) return new Color(240, 190, 30);  // sarı
        // Kritik: yanıp söner
        boolean on = (System.currentTimeMillis() / 200) % 2 == 0;
        return on ? new Color(220, 40, 40) : new Color(140, 20, 20);
    }
}