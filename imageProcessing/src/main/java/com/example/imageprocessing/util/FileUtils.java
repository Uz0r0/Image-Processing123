package com.example.imageprocessing.util;

import javafx.scene.image.Image;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static BufferedImage loadImageFromFile(File file){
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    public static Image convertToFXImage(BufferedImage bufferedImage){
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static BufferedImage convertToBufferedImage(Image fxImage){
        return SwingFXUtils.fromFXImage(fxImage, null);
    }

    public static void saveImageToFile(BufferedImage image){
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Save Image");
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image",  "*.png"));

        File file = saveFileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException ex) {
                System.out.println("Error saving image: " + ex.getMessage());
            }
        }
    }
}
