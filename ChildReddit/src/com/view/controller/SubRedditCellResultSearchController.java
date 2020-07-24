package com.view.controller;

import com.exception.BeingMember;
import com.exception.NotExistSubRedditException;
import com.jfoenix.controls.JFXButton;
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

public class SubRedditCellResultSearchController extends JFXListCell<SubReddit> {
    private User user;
    public SubRedditCellResultSearchController(User user){ this.user = user;}

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label subRedditName;

    @FXML
    private Circle imageSubReddit;

    @FXML
    private JFXButton joinButton;

    @FXML
    private Label memberNumber;

    @FXML
    private Label postNumber;

    @FXML
    private Label joinError;

    @FXML
    private Label dateCreated;

    private FXMLLoader fxmlLoader;

    @FXML
    public void initialize(){

    }

    public void updateItem(SubReddit subReddit, boolean empty) {
        super.updateItem(subReddit, empty);

        if (empty || subReddit == null) {
            setText(null);
            setGraphic(null);
        } else {
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("../SubRedditCellResultSearch.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Image img = new Image(subReddit.getImagePath());
            if (!img.isError()) {
                imageSubReddit.setFill(new ImagePattern(img));
            } else {
                user.setPathImage("com/assert/reddit.png");
                Image img2 = new Image(user.getPathImage());
                imageSubReddit.setFill(new ImagePattern(img2));
            }

            subRedditName.setText(subReddit.getName());
            memberNumber.setText(subReddit.members());
            postNumber.setText(subReddit.numberOfPost());

            dateCreated.setText(subReddit.getDate().toString());

            if(subReddit.getUsers().contains(user)){
                joinButton.setVisible(false);
            }

            joinButton.setOnAction(event -> join(subReddit));

            subRedditName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> subRedditPage(subReddit));
            imageSubReddit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> subRedditPage(subReddit));

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }

    private void subRedditPage(SubReddit subReddit){
        subRedditName.getScene().getWindow().hide();
        OpenWindow.openWindow("../SubRedditPage.fxml", new SubRedditPageController(user, subReddit),
                "Rddit - SubReddit Page " + subReddit.getName());
    }

    private void join(SubReddit subReddit){
        joinError.setText("");
        if(user == null){
            joinError.setText("Please first login or signup");
        }else{
            try {
                user.memberSubReddit(subReddit);
                joinButton.getScene().getWindow().hide();
                OpenWindow.openWindow("../SubRedditPage.fxml",new SignupPageController(),
                        "Reddit - SubReddit Page " + subReddit.getName());
            } catch (BeingMember beingMember) {
                beingMember.printStackTrace();
            } catch (NotExistSubRedditException e) {
                e.printStackTrace();
            }
        }
    }

}
