package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.SubReddit;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LeaveWarningController {
    private User user;
    private SubReddit subReddit;
    private ListView<SubReddit> subRedditListView;

    public LeaveWarningController(User user, SubReddit subReddit, ListView<SubReddit> subRedditListView) {
        this.user = user;
        this.subReddit = subReddit;
        this.subRedditListView = subRedditListView;
    }

    @FXML
    private JFXButton leaveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    public void initialize(){

        leaveButton.setOnAction(event -> {leave();});

        cancelButton.setOnAction(event -> {cancelButton.getScene().getWindow().hide();});

    }

    private void leave(){
        user.leaveSubReddit(subReddit);
        subRedditListView.getItems().remove(subReddit);
        leaveButton.getScene().getWindow().hide();
    }
}
