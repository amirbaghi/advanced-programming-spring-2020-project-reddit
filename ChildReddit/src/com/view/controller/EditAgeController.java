package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.User;
import com.model.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class EditAgeController {
    private User user;

    public EditAgeController(User user){
        this.user = user;
    }

    @FXML
    private TextField ageField;


    @FXML
    private Label ageError;

    @FXML
    private JFXButton notSaveButton;

    @FXML
    private JFXButton saveButton;

    @FXML
    public void initialize(){
        ageError.setText("");
        ageField.setText(user.getAge());

        saveButton.setOnAction(event -> {editAge();});

        notSaveButton.setOnAction(event -> {notSaveButton.getScene().getWindow().hide();});
    }

    private void editAge(){
        int valid = 1;
        String strAge = ageField.getText().trim();
        valid = Validation.ageValidation(strAge);
        if(valid==0){
            ageError.setText("Invalid Age");
        }else if(valid == 2){
            ageError.setText("You not allow to use this application");
        }
        else{
            user.setAge(strAge);
            saveButton.getScene().getWindow().hide();
        }
    }
}
