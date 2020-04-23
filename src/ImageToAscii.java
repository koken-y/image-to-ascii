import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageToAscii {
  private BufferedImage bimg;
  private final String ASCII = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$";
  private final String SIMPLE_ASCII = " .:-=+*#%@";

  public ImageToAscii() {

  }

  public void loadImage(String imagePath) {
    try {
      bimg = ImageIO.read(new File(imagePath));
      bimg = Thumbnails.of(bimg).size(bimg.getWidth() / 4, bimg.getHeight() / 4).asBufferedImage();
      System.out.println("Succesfully loaded image!");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void printImageDimensions() {
    int width = bimg.getWidth();
    int height = bimg.getHeight();
    System.out.println("Image Size: " + width + " x " + height);
  }

  public static void main(String[] args) {
    ImageToAscii test = new ImageToAscii();
    test.loadImage("images/test.jpg");
    test.printImageDimensions();

  }
}