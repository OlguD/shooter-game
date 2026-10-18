import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Assets {
    public final BufferedImage playerImage;
    public final BufferedImage tile;
    public final BufferedImage mushroomImage;
    public final BufferedImage flowerImage;
    public final BufferedImage gunImage;

    public Assets() {
        try {
            playerImage = ImageIO.read(new File("assets/player.png"));
            tile = ImageIO.read(new File("assets/tile.png"));
            mushroomImage = ImageIO.read(new File("assets/mushroom.png"));
            flowerImage = ImageIO.read(new File("assets/blue_flower.png"));
            gunImage = ImageIO.read(new File("assets/gun.png"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load assets", e);
        }

    }
}
