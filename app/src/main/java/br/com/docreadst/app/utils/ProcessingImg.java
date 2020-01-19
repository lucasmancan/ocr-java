package br.com.docreadst.app.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessingImg {

    public static File processImg(File fileImg) {
        File output = new File("image_grayscale.png");

        if(output.exists()) {
            if(output.delete()) {
                System.out.println("imagem em grayscale anterior deletada com sucesso.");
            }
        }

        try {
            BufferedImage image = ImageIO.read(fileImg);

            BufferedImage result = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB);

            Graphics2D graphic = result.createGraphics();

            graphic.setComposite(AlphaComposite.Src);
            graphic.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphic.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
            graphic.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

            graphic.drawImage(image, 0, 0, Color.WHITE, null);
            graphic.dispose();

            for (int i = 0; i < result.getHeight(); i++) {
                for (int j = 0; j < result.getWidth(); j++) {
                    Color c = new Color(result.getRGB(j, i));
                    int red = (int) (c.getRed() * 0.299);
                    int green = (int) (c.getGreen() * 0.587);
                    int blue = (int) (c.getBlue() * 0.114);
                    Color newColor = new Color(
                            red + green + blue,
                            red + green + blue,
                            red + green + blue);
                    result.setRGB(j, i, newColor.getRGB());
                }
            }

            final int w = image.getWidth();
            final int h = image.getHeight();

            BufferedImage scaledImage = new BufferedImage((w * 2),(h * 2), BufferedImage.TYPE_INT_RGB);

            final AffineTransform at = AffineTransform.getScaleInstance(2.0, 2.0);
            final AffineTransformOp ato = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);

            result = ato.filter(result, scaledImage);

            ImageIO.write(result, "png", output);

        }  catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
