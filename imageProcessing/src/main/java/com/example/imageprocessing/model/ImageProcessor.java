package com.example.imageprocessing.model;

import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class ImageProcessor {

    public static WritableImage process(Image image, String effect) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        RGB[][] pixels = new RGB[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = reader.getArgb(x, y);

                int red = (argb >> 16) & 0xff;
                int green = (argb >> 8) & 0xff;
                int blue = argb & 0xff;

                RGB rgb = null;

                if (effect == "grayscale") {
                    rgb = grayscale(red, green, blue);
                } else if (effect == "inversion") {
                    rgb = inversion(red, green, blue);
                } else if (effect == "blur"){
                    return blur(image);
                } else if (effect == "Эффект шаха"){
                    rgb = shahEffect(red, green, blue);
                }

                pixels[y][x] = rgb;

            }
        }

        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                RGB p = pixels[y][x];
                Color color = Color.rgb(p.getRed(), p.getGreen(), p.getBlue());
                writer.setColor(x, y, color);
            }
        }

        return result;
    }

    public static RGB grayscale(int red, int green, int blue){
        int avg = (red + green + blue) / 3;
        return new RGB(avg, avg, avg);
    }

    public static RGB inversion(int red, int green, int blue){
        int invertedRed = 255 - red;
        int invertedGreen = 255 - green;
        int invertedBlue = 255 - blue;
        return new RGB(invertedRed, invertedGreen, invertedBlue);
    }

    public static WritableImage blur(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        PixelReader reader = image.getPixelReader();
        WritableImage result = new WritableImage(width, height);
        PixelWriter writer = result.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int redSum = 0, greenSum = 0, blueSum = 0;

                for (int ky = -4; ky <= 4; ky++) {
                    for (int kx = -4; kx <= 4; kx++) {
                        int px = x + kx;
                        int py = y + ky;

                        if (px >= 0 && px < width && py >= 0 && py < height) {
                            Color color = reader.getColor(px, py);

                            redSum += (int)(color.getRed() * 255);
                            greenSum += (int)(color.getGreen() * 255);
                            blueSum += (int)(color.getBlue() * 255);
                        }
                    }
                }

                int r = redSum / 81;
                int g = greenSum / 81;
                int b = blueSum / 81;

                writer.setColor(x, y, Color.rgb(r, g, b));
            }
        }

        return result;
    }

    public static RGB shahEffect(int red, int green, int blue){
        int shahRed = red - red;
        int shahGreen = green - green;
        int shahBlue = blue - blue;
        return new RGB(shahRed, shahGreen, shahBlue);
    }
}