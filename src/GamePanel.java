import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final int TILE_SIZE = 64;
    private static final int DECORATION_COUNT = 30;

    private record Decoration(int x, int y, BufferedImage image) {}

    private final List<Decoration> decorations = new ArrayList<>();
    private final Random random = new Random();
    private final Input input = new Input();
    private final Assets assets = new Assets();

    private Player player; // panel boyutu belli olunca oluşturulacak

    public GamePanel() {
        setFocusable(true);
        addKeyListener(input);

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    private void update() {
        if (getWidth() == 0) return;

        if (getWidth() < TILE_SIZE || getHeight() < TILE_SIZE) return;

        if (player == null) {
            int startX = (getWidth() - Player.SIZE) / 2;
            int startY = (getHeight() - Player.SIZE) / 2;
            player = new Player(startX, startY, assets.playerImage);
            generateDecorations();
        }

        player.update(input, getWidth(), getHeight());
    }

    private void generateDecorations() {
        int cols = getWidth() / TILE_SIZE;
        int rows = getHeight() / TILE_SIZE;

        for (int i = 0; i < DECORATION_COUNT; i++) {
            int x = random.nextInt(cols) * TILE_SIZE;
            int y = random.nextInt(rows) * TILE_SIZE;
            BufferedImage image = random.nextBoolean() ? assets.mushroomImage : assets.flowerImage;
            decorations.add(new Decoration(x, y, image));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0; x < getWidth(); x += TILE_SIZE) {
            for (int y = 0; y < getHeight(); y += TILE_SIZE) {
                g.drawImage(assets.tile, x, y, TILE_SIZE, TILE_SIZE, this);
            }
        }

        for (Decoration d : decorations) {
            g.drawImage(d.image(), d.x(), d.y(), TILE_SIZE, TILE_SIZE, this);
        }

        if (player != null) {
            player.draw(g, this);
        }
    }
}