package com.example.imageprocessing;

import com.example.imageprocessing.controller.MainController;
import com.example.imageprocessing.model.ImageProcessor;
import com.example.imageprocessing.util.FileUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class HelloApplication extends Application {
    private static ObservableList<String> effectList = FXCollections.observableArrayList("grayscale", "inversion", "blur", "Эффект шаха");
    private Image originalImage;
    private Image resultImage;
    private ImageView original = new ImageView();
    private ImageView result = new ImageView();

    @Override
    public void start(Stage stage) throws IOException {

        Button openImage = new Button("Open Image");
        Button process = new Button("Process");
        Button save = new Button("Save");
        ComboBox<String> effectBox = new ComboBox(effectList);
        Label originalPhoto = new Label("Original");
        Label resultPhoto = new Label("Result");

        openImage.setPrefSize(100 , 30);
        process.setPrefSize(80, 30);
        save.setPrefSize(70 , 30);
        effectBox.setPrefSize(120 ,30);

        openImage.setOnAction(e -> {
            File selectedFile = MainController.inputImage();
            if (selectedFile != null){
                originalImage = FileUtils.convertToFXImage(FileUtils.loadImageFromFile(selectedFile));
                original.setImage(originalImage);
                result.setImage(originalImage);
                double originalWidth = originalImage.getWidth();
                double scale = 0.3;
                original.setFitWidth(originalWidth * scale);
                original.setPreserveRatio(true);
                result.setFitWidth(originalWidth * scale);
                result.setPreserveRatio(true);
            }

        });

        process.setOnAction(e ->{
            String selected = effectBox.getValue();
            resultImage = ImageProcessor.process(originalImage, selected);
            result.setImage(resultImage);
        });

        save.setOnAction(e -> {
            FileUtils.saveImageToFile(FileUtils.convertToBufferedImage(resultImage));
        });

        VBox vBoxOriginal = new VBox(20, originalPhoto, original);
        vBoxOriginal.setAlignment(Pos.CENTER);
        VBox vBoxResult = new VBox(20, resultPhoto, result);
        vBoxResult.setAlignment(Pos.CENTER);
        HBox Photos = new HBox(70, vBoxOriginal, vBoxResult);
        Photos.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(10 ,openImage, effectBox, process, save);
        hBox.setAlignment(Pos.TOP_CENTER);

        VBox root = new VBox(150, hBox, Photos);

        Scene scene = new Scene(root, 768, 768);
        stage.setTitle("Image Processing");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}