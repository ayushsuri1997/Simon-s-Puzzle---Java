package com.example.javafinalproject.utils;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/** Handler for the ImageView to display the selected image in a window.
 * @author Ayush
 * @version 1.0
 */
public class ImageViewHandler {
    /**
     * Opens the image in a new window.
     * @param fileName Name of the image file in format string which is to be opened.
     * @throws FileNotFoundException Checks if the image that was selected to open is available or not.
     */
    public void openImageWindow(String fileName) throws FileNotFoundException {
        Stage imageWindow = new Stage();
        InputStream stream = new FileInputStream("prevState/"+fileName);
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        Group root = new Group(imageView);
        Scene scene = new Scene(root);
        imageWindow.setTitle("Image Window");
        imageWindow.setScene(scene);
        imageWindow.show();
    }
}
