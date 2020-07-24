package com.view.controller;

import com.model.SubReddit;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class ShowImageController {
    private String path;

    public ShowImageController(String path){ this.path = path;}

    @FXML
    private Rectangle imagePlace;

    public void initialize(){

        Image img = new Image(path);
        imagePlace.setFill(new ImagePattern(img));

    }

}