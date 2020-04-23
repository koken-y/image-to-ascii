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

  // ----------Methods to get luminance from RGB----------
  private double rgbToLuminance(Color pixel) {
    double luminance;
    double vR = (double) pixel.getRed() / 255;
    double vG = (double) pixel.getGreen() / 255;
    double vB = (double) pixel.getBlue() / 255;

    luminance = 0.2126 * srgbToLin(vR) + 0.7152 * srgbToLin(vG) + 0.0722 * srgbToLin(vB);
    return luminance;
  }

  private double srgbToLin(double colorChannel) {
    if (colorChannel <= 0.04045) {
      return colorChannel / 12.92;
    } else {
      return Math.pow(((colorChannel + 0.055) / 1.055), 2.4);
    }
  }

  private double luminanceToLightness(double luminance) {
    double lightness;
    if (luminance <= (double) 216 / 24389) { // The CIE standard states 0.008856 but 216/24389 is the intent for
                                             // 0.008856451679036
      lightness = luminance * ((double) 24389 / 27); // The CIE standard states 903.3, but 24389/27 is the intent,
                                                     // making 903.296296296296296
    } else {
      lightness = (Math.pow(luminance, ((double) 1 / 3)) * 116 - 16);
    }
    return lightness / 100;
  }
  // ----------Methods to get luminance from RGB----------
  
  public static void main(String[] args) {
    ImageToAscii test = new ImageToAscii();
    test.loadImage("images/test.jpg");
    test.printImageDimensions();

  }
}