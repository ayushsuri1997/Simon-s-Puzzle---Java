package com.example.javafinalproject.utils;

import com.example.javafinalproject.SimonPuzzle;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/** Handler for the AlertBox to display alerts when the player wins, loses or makes an invalid move.
 * @author Ayush
 * @version 1.0
 */

public class AlertBoxHandler {

    /**
     * Shows an alert dialog with 1 button when the user makes an invalid move.
     * @param s A message that you want to display on the alert box.
     */
    public void showAlertDialog(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR, s, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }

    /**
     * Shows an alert dialog with 2 buttons to when the user wins or loses. The user has the option to play the game again or quit the game.
     * @param s A message that you want to display on the alert box.
     * @param label Label to get the current window and restarting the application.
     * @throws IOException An error when the FXML document is not found.
     */
    public void finishGameAlert(String s, Label label) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, s, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            Stage stage = (Stage) label.getScene().getWindow();
            stage.close();
            FXMLLoader fxmlLoader = new FXMLLoader(SimonPuzzle.class.getResource("Board.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Java Game");
            stage.setScene(scene);
            stage.show();
        } else if (alert.getResult() == ButtonType.NO) {
            Platform.exit();
        }
    }
}
