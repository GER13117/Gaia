import java.awt.image.BufferedImage;

/**
 * Splits BufferedImages at given Points: Used for extracting the different Tiles of a SpriteSheet.
 */
public class SpriteSheet {

    private BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }

    /**
     * @param col Number of colons
     * @param row Number of tows
     * @param width width of the subimages
     * @param height height of the subimage
     * @return returns subimage in specific location as BufferedImage
     */
    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
        return img;
    }
}