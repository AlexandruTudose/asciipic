package com.asciipic.filter.services;

import com.asciipic.filter.dtos.ImagePostDTO;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public ImagePostDTO resize(ImagePostDTO imagePostDTO, Long pHeight, Long pWidth) throws Exception {
        int height = Math.toIntExact(pHeight);
        int width = Math.toIntExact(pWidth);

        BufferedImage image;
        image = transformByteIntoBufferedImage(imagePostDTO.getByteImage());

        if (height == 0 && width == 0) {
            throw new Exception("Width or height is missing!");
        } else if (height == 0 && width != 0) {
            height = computeTheSize(width, image.getWidth(), image.getHeight());
        } else if (width == 0 && height != 0) {
            width = computeTheSize(height, image.getHeight(), image.getWidth());
        }

        image = scale(image, width, height, (double) width / image.getWidth(), (double) height / image.getHeight());
        ImagePostDTO newImage = imagePostDTO;

        List<String> tags = imagePostDTO.getTags();

        if (!tags.contains("resized")) {
            tags.add("resized");
        }

        byte[] imageByte = transformBufferedImageIntoByte(image);
        newImage.setWidth(width);
        newImage.setHeight(height);
        newImage.setTags(tags);
        newImage.setByteImage(imageByte);
        return newImage;
    }

    @Override
    public ImagePostDTO crop(ImagePostDTO imagePostDTO, Long pTop, Long pBottom, Long pLeft, Long pRight) throws IOException, SQLException {

        int top = Math.toIntExact(pTop);
        int bottom = Math.toIntExact(pBottom);
        int left = Math.toIntExact(pLeft);
        int right = Math.toIntExact(pRight);

        BufferedImage bufferedImage = redrawTheImage(imagePostDTO.getByteImage(), top, bottom, left, right);

        List<String> tags = imagePostDTO.getTags();

        if (!tags.contains("cropped")) {
            tags.add("cropped");
        }

        ImagePostDTO newImage = imagePostDTO;
        newImage.setWidth(bufferedImage.getWidth());
        newImage.setHeight(bufferedImage.getHeight());
        newImage.setTags(tags);
        newImage.setByteImage(transformBufferedImageIntoByte(bufferedImage));

        return newImage;
    }

    private BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight, double fWidth, double fHeight) {
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

    private BufferedImage redrawTheImage(byte[] image, int top, int bottom, int left, int right) throws IOException, SQLException {
        BufferedImage bufferedImage = transformByteIntoBufferedImage(image);
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();
        BufferedImage newBufferedImage = bufferedImage.getSubimage(left,top,width-left-right,height - top - bottom );

        return newBufferedImage;
    }

    private BufferedImage transformByteIntoBufferedImage(byte[] imageByte) throws IOException {
        BufferedImage image;
        ByteArrayInputStream bais = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bais);

        return image;
    }

    private byte[] transformBufferedImageIntoByte(BufferedImage image) throws IOException, SQLException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageByte = baos.toByteArray();

        return imageByte;
    }

    private int computeTheSize(int a, int imageHeight, int imageWidth) {
        return ((a * imageWidth) / imageHeight);
    }
}
