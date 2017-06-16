package com.asciipic.search.services;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Service
public class AsciiServiceImpl implements AsciiService {
    @Override
    public String transformImageToAscii(Blob imageBlob) {
        BufferedImage image = null;
        int blobLength = 0;
        try {

            blobLength = (int) imageBlob.length();
            byte[] blobAsBytes = imageBlob.getBytes(1, blobLength);
            imageBlob.free();
            ByteArrayInputStream bais = new ByteArrayInputStream(blobAsBytes);
            image = ImageIO.read(bais);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int height = image.getHeight();
        int width = image.getWidth();

        if (height > width) {
            height = maxSideLength(height);
            width = computeTheSize(height, image.getHeight(), image.getWidth());
        } else {
            width = maxSideLength(width);
            height = computeTheSize(width, image.getWidth(), image.getHeight());
        }

        image = scale(image, width, height, (double) width / image.getWidth(), (double) height / image.getHeight());
        image = scale(image, image.getWidth(), image.getHeight() / 2, 1, 0.5);
        StringBuilder asciiPic = new StringBuilder(image.getHeight() * (image.getWidth() + 1));

        for (int y = 0; y < image.getHeight(); y++) {
            if (asciiPic.length() != 0) {
                asciiPic.append("\n");
            }
            for (int x = 0; x < image.getWidth(); x++) {
                Color pixelColor = new Color(image.getRGB(x, y));

                double gValue = (double) pixelColor.getRed() * 0.2989 + (double) pixelColor.getBlue() * 0.5870 + (double) pixelColor.getGreen() * 0.1140;
                char s = gValue < 130 ? darkGrayScaleMap(gValue) : lightGrayScaleMap(gValue);
                asciiPic.append(s);
            }
        }

        return asciiPic.toString();

    }

    private static BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight, double fWidth, double fHeight) {
        BufferedImage dbi = null;
        int imageType = imageToScale.getType();
        if (imageToScale != null) {
            dbi = new BufferedImage(dWidth, dHeight, imageType);
            Graphics2D g = dbi.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
            g.drawRenderedImage(imageToScale, at);
        }
        return dbi;
    }

    private char darkGrayScaleMap(double g) {
        char str;
        if (g >= 120.0) {
            str = '=';
        } else if (g >= 100.0) {
            str = 'W';
        } else if (g >= 80.0) {
            str = '@';
        } else if (g >= 70.0) {
            str = '$';
        } else if (g >= 30.0) {
            str = '#';
        } else {
            str = '▒';
        }
        return str;
    }

    private char lightGrayScaleMap(double g) {
        char str;
        // Light
        if (g >= 240.0) {
            str = ' ';
        } else if (g >= 220) {
            str = '~';
        } else if (g >= 200.0) {
            str = '-';
        } else if (g >= 180.0) {
            str = ':';
        } else if (g >= 160.0) {
            str = '+';
        } else {
            str = '%';
        }
        return str;
    }

    private int maxSideLength(int side) {
        if (side >= 1024) {
            return 125;
        } else if (side >= 640) {
            return 100;
        } else {
            return 65;
        }
    }

    private int computeTheSize(int a, int imageHeight, int imageWidth) {
        return ((a * imageWidth) / imageHeight);
    }
}
