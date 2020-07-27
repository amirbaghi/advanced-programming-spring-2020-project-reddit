package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.SubReddit;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateSubRedditController {
    private User user;

    public CreateSubRedditController(User user) {
        this.user = user;
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextArea aboutField;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton createButton;

    @FXML
    private Label invalidLabel;

    @FXML
    public void initialize() {

        createButton.setOnAction(event -> {createSubReddit();});

        cancelButton.setOnAction(event -> {cancelButton.getScene().getWindow().hide();});
    }

    private void createSubReddit(){
        String name = nameField.getText().replaceFirst("\\s++$", "");

        if(name.length()<3 || name.length()>20){
            invalidLabel.setText("Length of Name subreddit must be between 3 and 20");
        }
        else{
            SubReddit subReddit = user.createSubReddit(name);
            subReddit.setDescription(aboutField.getText());
            createButton.getScene().getWindow().hide();
        }
    }

}
