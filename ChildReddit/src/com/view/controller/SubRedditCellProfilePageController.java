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

public class SubRedditCellProfilePageController extends JFXListCell<SubReddit> {
    private User owner;
    private User user;

    public SubRedditCellProfilePageController(User owner, User user) {
        this.owner = owner;
        this.user = user;
    }

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label subRedditName;

    @FXML
    private Circle imageSubReddit;

    @FXML
    private JFXButton joinButton;

    @FXML
    private JFXButton leaveButton;

    @FXML
    private Label memberNumber;

    @FXML
    private Label postNumber;

    @FXML
    private Label joinError;

    private FXMLLoader fxmlLoader;

    @FXML
    public void initialize() {

    }

    public void updateItem(SubReddit subReddit, boolean empty){
        super.updateItem(subReddit, empty);

        if(empty || subReddit == null){
            setText(null);
            setGraphic(null);
        }else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("../SubRedditCellProfilePage.fxml"));
                fxmlLoader.setController(this);
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(!owner.equals(user)){
                leaveButton.setVisible(false);
            }else{
                joinButton.setVisible(false);
            }

            if(subReddit.getUsers().contains(user)){
                joinButton.setVisible(false);
            }

            Image img = new Image(subReddit.getImagePath());
            if(!img.isError()) {
                imageSubReddit.setFill(new ImagePattern(img));
            }else{
                subReddit.setImagePath("com/assert/reddit-icon.png");
                Image img2 = new Image(subReddit.getImagePath());
                imageSubReddit.setFill(new ImagePattern(img2));
            }
            subRedditName.setText(subReddit.getName());
            memberNumber.setText(subReddit.members());
            postNumber.setText(subReddit.numberOfPost());

            leaveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {leave(subReddit);});

            subRedditName.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {subRedditPage(subReddit);});

            joinButton.setOnAction(event -> join(subReddit));

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }

    private void leave(SubReddit subReddit){
        leaveButton.getScene().getWindow();
        OpenWindow.openWindowWait("../LeaveWarning.fxml",new LeaveWarningController(owner, subReddit, getListView())
                ,"Warning");
    }

    private void subRedditPage(SubReddit subReddit){
        subRedditName.getScene().getWindow().hide();
        OpenWindow.openWindow("../SubRedditPage.fxml", new SubRedditPageController(user, subReddit)
                ,"Reddit - SubReddit " + subReddit.getName());
    }

    private void join(SubReddit subReddit){
        joinError.setText("");
        if(user == null){
            joinError.setText("please first login or signup");
        }else{
            try {
                user.memberSubReddit(subReddit);
                memberNumber.setText(subReddit.members());
                joinButton.setVisible(false);
            } catch (BeingMember beingMember) {
                beingMember.printStackTrace();
            } catch (NotExistSubRedditException e) {
                e.printStackTrace();
            }
        }
    }
}
