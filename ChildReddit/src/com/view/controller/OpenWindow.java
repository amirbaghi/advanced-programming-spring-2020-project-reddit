package com.view.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenWindow {

    private static Stage loader(String path, Object controller, String title){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(OpenWindow.class.getResource(path));
        loader.setController(controller);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.resizableProperty().setValue(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

    public static void openWindowWait(String path, Object controller, String title){
        Stage stage = loader(path, controller, title);
        stage.showAndWait();
    }

    public static void openWindow(String path, Object controller, String title){
        Stage stage = loader(path, controller, title);
        stage.show();
    }
}
