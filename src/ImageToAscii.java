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
      bimg = Thumbnails.of(bimg).size(bimg.getWidth() / 8, bimg.getHeight() / 8).asBufferedImage();
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

  public char[][] getAsciiMatrix() {
    int width = bimg.getWidth();
    int height = bimg.getHeight();
    char[][] asciiMatrix = new char[width][height];
    Color pixel;
    double brightness;
    char ascii;

    for (int w = 0; w < width; w++) {
      for (int h = 0; h < height; h++) {
        pixel = new Color(bimg.getRGB(w, h));
        brightness = rgbToLuminance(pixel);
        ascii = getAsciiFromBrightness(luminanceToLightness(brightness));

        asciiMatrix[w][h] = ascii;
      }
    }
    return asciiMatrix;
  }

  private char getAsciiFromBrightness(double brightness) {
    int asciiIndex = (int) Math.round(brightness * 10);
    return SIMPLE_ASCII.charAt(asciiIndex);
  }

  public void printAsciiImage() {
    char[][] asciiMatrix = getAsciiMatrix();
    StringBuilder line = new StringBuilder();

    for (int h = 0; h < asciiMatrix[0].length; h++) {
      for (int w = 0; w < asciiMatrix.length; w++) {
        char letter = asciiMatrix[w][h];
        for (int i = 0; i < 3; i++) {
          line.append(letter);
        }
      }
      System.out.println(line);
      line.setLength(0);
    }
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
    ImageToAscii app = new ImageToAscii();
    app.loadImage("../images/test.jpg");
    app.printImageDimensions();
    app.printAsciiImage();
  }
}