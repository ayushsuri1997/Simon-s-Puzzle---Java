package com.example.javafinalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Represents the start of the game, This is the entry point of the game.
 * @author Ayush
 * @version 1.0
 * */

public class SimonPuzzle extends Application {

    /** Starts the window which loads the UI and displays it on the screen.
     *
     * @param stage represents the window which loads the FXML Document
     * @throws IOException if the file FXML is not found
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimonPuzzle.class.getResource("Board.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Java Game");
        stage.setScene(scene);
        stage.show();
    }

    /** Main method which launches the stage.
     */
    public static void main(String[] args) {
        launch();
    }
}