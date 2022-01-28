package com.example.javafinalproject.view;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;

/** Contains the functionality of taking the username by showing a window
 * @author Ayush
 * @version 1.0
 * */

public class WelcomeScreen {
    /** This method represents thw way the username is taken as an input by launching a new Window.
     * @return Returns the name of the user which is of String type.
     */
    public static String display() {
        Stage nameWindow = new Stage();
        nameWindow.initModality(Modality.APPLICATION_MODAL);
        nameWindow.setTitle("Welcome to JAVA Game");
        Label labelTitle = new Label("Welcome to Java Game");
        labelTitle.setFont(Font.font(40));
        labelTitle.setAlignment(Pos.CENTER);
        Label label1= new Label("Enter the Name:");
        TextField textField = new TextField();
        Button okBtn = new Button("Okay");
        //Action on Okay Button
        okBtn.setOnAction(e ->
                {
                    if (textField.getText().trim().length() == 0) {
                        alertBoxName("Name can't be empty");
                    } else {
                        nameWindow.close();
                    }
                }
        );
        //Enter Action on Text Field
        textField.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ENTER) {
                if (textField.getText().trim().length() == 0) {
                    alertBoxName("Name can't be empty");
                } else {
                    nameWindow.close();
                }
            }
        });
        HBox layout= new HBox(10);
        VBox vBox = new VBox(100);
        layout.getChildren().addAll(label1, textField, okBtn);
        layout.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(labelTitle, layout);
        vBox.setAlignment(Pos.CENTER);
        Scene scene1= new Scene(vBox, 500, 500);
        nameWindow.setScene(scene1);
        nameWindow.showAndWait();
        return textField.getText();
    }

    /** Displays an ALert Box if the name field is empty
     * @param s The message that has to be displayed on the Alert Box
     */
    private static void alertBoxName(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR, s, ButtonType.OK);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            alert.close();
        }
    }
}
