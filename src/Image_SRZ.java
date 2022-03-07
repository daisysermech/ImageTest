
//package blur;

import java.awt.image.BufferedImage;
import java.io.Serializable;

class Image_SRZ implements Serializable {
    private static final long serialVersionUID = 5844813814579262025L;
    private int width, height;
    private int[] pixels;
    public Image_SRZ(BufferedImage image)
    {
        width = image.getWidth();
        height = image.getHeight();
        pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
    }
    
    public BufferedImage getImage()
    {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }
}
