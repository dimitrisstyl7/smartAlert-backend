package com.unipi.smartalert.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@UtilityClass
public class ImageUtil {

    private static final String DEFAULT_IMAGES_DIR = "images";

    /**
     * Saves raw bytes to disk under the default images folder.
     * Returns the saved file absolute path as String.
     */
    public static String saveImageToDisk(byte[] bytes, String filename) {
        try {
            Path dirPath = Paths.get(DEFAULT_IMAGES_DIR);
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(Objects.requireNonNull(filename));
            Files.write(filePath, bytes);
            return filePath.toAbsolutePath().toString(); // store absolute path for safety
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image to disk", e);
        }
    }

    /**
     * Read image bytes from disk by absolute or relative path.
     */
    public static byte[] readImageFromDisk(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image from disk: " + path, e);
        }
    }

    private static BufferedImage convertByteArrayToJPEG(byte[] byteArray) {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        BufferedImage image;

        try {
            image = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (image == null) {
            throw new RuntimeException("Image is null");
        }
        return image;
    }

    private static byte[] convertJPEGToByteArray(BufferedImage image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpeg", bos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

}
