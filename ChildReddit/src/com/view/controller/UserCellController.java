package com.view.controller;

import com.jfoenix.controls.JFXListCell;
import com.model.SubReddit;
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

public class UserCellController extends JFXListCell<User> {

    private User user;
    private SubReddit subReddit;

    public UserCellController(User user, SubReddit subReddit) {
        this.user = user;
        this.subReddit = subReddit;
    }


    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Circle imagePlace;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label numberPost;

    @FXML
    private Label upVoteNumber;

    @FXML
    private Label downVoteNumber;

    private FXMLLoader fxmlLoader;

    @FXML
    public void initialize() {

    }

    public void updateItem(User user, boolean empty){
        super.updateItem(user, empty);

        if(empty || user == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("../UserCell.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Image img = new Image(user.getPathImage());
            if(!img.isError()) {
                imagePlace.setFill(new ImagePattern(img));
            }else{
                user.setPathImage("com/assert/reddit.png");
                Image img2 = new Image(user.getPathImage());
                imagePlace.setFill(new ImagePattern(img2));
            }
            usernameLabel.setText(user.getUserName());

            numberPost.setText(String.valueOf(user.numberPostInSubReddit(subReddit)));
            upVoteNumber.setText(user.averageUpVotePost(subReddit)+"%");
            downVoteNumber.setText(user.averageDownVotePost(subReddit)+"%");

            usernameLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> profilePage(user));

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }

    private void profilePage(User owner){
        usernameLabel.getScene().getWindow().hide();
        OpenWindow.openWindow("../ProfilePage.fxml", new ProfilePageController(owner, this.user),
                "ChildReddit - Profile Page " + owner.getUserName());
    }

}
