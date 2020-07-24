package com.view.controller;

import com.exception.NotExistSubRedditException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class NewPostController {

    private User user;
    private SubReddit subReddit;

    public NewPostController(User user, SubReddit subReddit) {
        this.user = user;
        this.subReddit = subReddit;
    }

    @FXML
    private TextArea titleBox;

    @FXML
    private Label titleError;

    @FXML
    private TextArea textBox;

    @FXML
    private ImageView attachIcon;

    @FXML
    private JFXComboBox<AttachType> typeBox;

    @FXML
    private Label chooseTypeLabel;

    @FXML
    private JFXButton attachButton;

    @FXML
    private Label address;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton postButton;

    private String pathAttach;
    private AttachType attachType;

    @FXML
    public void initialize() {
        ObservableList<AttachType> attachTypes = FXCollections.observableArrayList(AttachType.values());
        typeBox.setItems(attachTypes);
        attachButton.setOnAction(event -> {attach();});
        attachIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {attach();});

        postButton.setOnAction(event -> {post();});

        cancelButton.setOnAction(event -> {cancel();});

    }

    private void attach(){

        attachType = typeBox.getSelectionModel().getSelectedItem();
        if (attachType == null){
            chooseTypeLabel.setText("You must first select Type");
        }
        else if(attachButton.getText().equals("Delete")){
            attachButton.setText("attach");
            address.setText("");
            pathAttach = null;
            attachType = null;
        }
        else {
            chooseTypeLabel.setText("");
            FileChooser fileChooser = new FileChooser();
            if (attachType.equals(AttachType.IMAGE)) {
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image File", "*.png", "*.jpg"));
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    pathAttach = "file:" + selectedFile.getAbsolutePath();
                    attachType = AttachType.IMAGE ;
                    address.setText(selectedFile.getAbsolutePath());
                    attachButton.setText("Delete");
                }
            } else if (attachType.equals(AttachType.MUSIC)) {
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Music File", "*.mp3"));
                File selectedFile = fileChooser.showOpenDialog(null);
                if (selectedFile != null) {
                    pathAttach = "file:" + selectedFile.getAbsolutePath();
                    attachType = AttachType.MUSIC;
                    address.setText(selectedFile.getAbsolutePath());
                    attachButton.setText("Delete");
                }
            }else{
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image File","*.mp4", "*.MKV"));
                File selectedFile = fileChooser.showOpenDialog(null);
                if(selectedFile != null) {
                    pathAttach = "file:"+selectedFile.getAbsolutePath();
                    attachType = AttachType.VIDEO;
                    address.setText(selectedFile.getAbsolutePath());
                    attachButton.setText("Delete");
                }
            }
        }
    }

    private void cancel(){
        cancelButton.getScene().getWindow().hide();
    }

    private void post(){
        String title = titleBox.getText().replaceFirst("\\s++$", "");
        if(title.length() == 0 ){
            titleError.setText("Title must be write");
        }
        else{
            titleError.setText("");
            String text = textBox.getText().replaceFirst("\\s++$", "");
            try {
                Post post = user.addPost(subReddit, title, text);
                if(pathAttach!=null && attachType!=null) {
                    post.setAttach(new Attach(pathAttach, attachType));
                }

            } catch (NotExistSubRedditException e) {
                e.printStackTrace();
            }
            postButton.getScene().getWindow().hide();
        }
    }


}
