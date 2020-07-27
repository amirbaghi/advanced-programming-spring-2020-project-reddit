package com.view.controller;

import com.jfoenix.controls.JFXListCell;
import com.model.Post;
import com.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class UserCellResultSearchController extends JFXListCell<User> {
    private User user;
    public UserCellResultSearchController(User user){ this.user = user; }

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Circle imagePlace;

    @FXML
    private Label username;

    private FXMLLoader fxmlLoader;

    @FXML
    public void initialize(){

    }

    public void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);

        if (empty || user == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (fxmlLoader == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("../UserCellResultSearch.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Image img = new Image(user.getPathImage());
            if (!img.isError()) {
                imagePlace.setFill(new ImagePattern(img));
            } else {
                user.setPathImage("com/assert/reddit.png");
                Image img2 = new Image(user.getPathImage());
                imagePlace.setFill(new ImagePattern(img2));
            }

            username.setText(user.getUserName());

            username.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage(user));
            imagePlace.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage(user));

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }

    private void profilePage(User owner){
        username.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(owner, user),
                "ChildReddit - Profile Page " + owner.getUserName());
    }
}
