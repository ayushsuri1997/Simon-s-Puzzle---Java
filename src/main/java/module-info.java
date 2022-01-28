module com.example.javafinalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires org.junit.jupiter.api;

    opens com.example.javafinalproject to javafx.fxml;
    exports com.example.javafinalproject;
    exports com.example.javafinalproject.controller;
    opens com.example.javafinalproject.controller to javafx.fxml;
    exports com.example.javafinalproject.utils;
    opens com.example.javafinalproject.utils to javafx.fxml;
    exports com.example.javafinalproject.model;
    opens com.example.javafinalproject.model to javafx.fxml;
    exports com.example.javafinalproject.view;
    opens com.example.javafinalproject.view to javafx.fxml;

}