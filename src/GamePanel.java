import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final int SCREEN_SHAKE_DURATION = 10;
    private static final int SCREEN_SHAKE_STRENGTH = 6;

    private final Input input = new Input();
    private final Assets assets = new Assets();
    private final List<Bullet> bullets = new ArrayList<>();
    private final Random random = new Random();

    // Panelin boyutu belli olunca oluşturulacaklar
    private World world;
    private Player player;
    private HealthBar healthBar;

    private int screenShakeTimer;
    private boolean damageKeyWasDown;

    public GamePanel() {
        setFocusable(true);
        addKeyListener(input);
        addMouseMotionListener(input);
        addMouseListener(input);

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {
        if (getWidth() < World.TILE_SIZE || getHeight() < World.TILE_SIZE) return;

        if (world == null) {
            world = new World(getWidth(), getHeight(), assets);

            int startX = (getWidth() - Player.SIZE) / 2;
            int startY = (getHeight() - Player.SIZE) / 2;
            player = new Player(startX, startY, assets.playerImage, new Gun(assets.gunImage));
            healthBar = new HealthBar(Player.MAX_HEALTH);
        }

        player.update(input, getWidth(), getHeight());

        Bullet bullet = player.tryShoot(input);
        if (bullet != null) {
            bullets.add(bullet);
        }
        for (Bullet b : bullets) {
            b.update();
        }

        // Test: H'ye her basışta 10 hasar (basılı tutunca tekrar etmez)
        if (input.damageKey && !damageKeyWasDown) {
            damagePlayer(10);
        }
        damageKeyWasDown = input.damageKey;

        healthBar.update(player.getHealth());
        if (screenShakeTimer > 0) screenShakeTimer--;
    }

    // Oyuncuya hasar vermenin tek yolu: hasar + ekran sarsıntısı birlikte
    private void damagePlayer(int amount) {
        if (player.isDead()) return;
        player.takeDamage(amount);
        screenShakeTimer = SCREEN_SHAKE_DURATION;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (world == null) return;

        // Dünya katmanı: sarsıntı bunu etkiler
        Graphics2D g2 = (Graphics2D) g.create();
        if (screenShakeTimer > 0) {
            int s = SCREEN_SHAKE_STRENGTH * screenShakeTimer / SCREEN_SHAKE_DURATION + 1;
            g2.translate(random.nextInt(2 * s + 1) - s, random.nextInt(2 * s + 1) - s);
        }
        world.draw(g2, this);
        player.draw(g2, this);
        for (Bullet b : bullets) {
            b.draw(g2);
        }
        g2.dispose();

        // Arayüz katmanı: ekranla birlikte sallanmaz, kendi efekti var
        healthBar.draw(g, player.getHealth(), Player.MAX_HEALTH);
    }
}