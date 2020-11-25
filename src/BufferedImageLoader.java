import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Loads provided Images / Files as Buffered Image for further use
 */
public class BufferedImageLoader {

    public BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(getClass().getResource(path));
    }

}
