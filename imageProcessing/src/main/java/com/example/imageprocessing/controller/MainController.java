package com.example.imageprocessing.controller;

import javafx.stage.FileChooser;

import java.io.File;

public class MainController {

    public static File inputImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(null);
        return file;
    }

}
