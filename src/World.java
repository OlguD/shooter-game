import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static final int TILE_SIZE = 64;
    private static final int DECORATION_COUNT = 30;

    private record Decoration(int x, int y, BufferedImage image) {}

    private final int width;
    private final int height;
    private final Assets assets;
    private final List<Decoration> decorations = new ArrayList<>();
    private final Random random = new Random();

    public World(int width, int height, Assets assets) {
        this.width = width;
        this.height = height;
        this.assets = assets;
        generateDecorations();
    }

    private void generateDecorations() {
        int cols = width / TILE_SIZE;
        int rows = height / TILE_SIZE;

        for (int i = 0; i < DECORATION_COUNT; i++) {
            int x = random.nextInt(cols) * TILE_SIZE;
            int y = random.nextInt(rows) * TILE_SIZE;
            BufferedImage image = random.nextBoolean() ? assets.mushroomImage : assets.flowerImage;
            decorations.add(new Decoration(x, y, image));
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        for (int x = 0; x < width; x += TILE_SIZE) {
            for (int y = 0; y < height; y += TILE_SIZE) {
                g.drawImage(assets.tile, x, y, TILE_SIZE, TILE_SIZE, observer);
            }
        }

        for (Decoration d : decorations) {
            g.drawImage(d.image(), d.x(), d.y(), TILE_SIZE, TILE_SIZE, observer);
        }
    }
}