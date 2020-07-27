package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.model.Comment;
import com.model.Post;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class WriteCommentController {
    private User user;
    private Post post;
    private Comment comment;
    public WriteCommentController(User user, Post post){
        this.user = user;
        this.post = post;
        comment = null;
    }
    public  WriteCommentController(User user, Comment comment){
        this.user = user;
        this.comment = comment;
        post= null;
    }

    @FXML
    private TextArea commentText;

    @FXML
    private Label error;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton sendButton;

    @FXML
    public void initialize() {
        error.setText("");
        if(comment == null) {
            sendButton.setOnAction(event -> send());
        }else{
            sendButton.setOnAction(event -> sendToComment());
        }

        cancelButton.setOnAction(event -> cancelButton.getScene().getWindow().hide());

    }

    private void send(){
        error.setText("");
        String text = commentText.getText().replaceFirst("\\s++$", "");
        if(text.length()==0){
            error.setText("Comment can not be empty");
        }else {
            user.addCommentToPost(text, post);
            sendButton.getScene().getWindow().hide();
        }
    }

    private void sendToComment() {
        error.setText("");
        String text = commentText.getText().replaceFirst("\\s++$", "");
        if(text.length()==0){
            error.setText("Comment can not be empty");
        }else {
            user.replyComment(text, comment);
            sendButton.getScene().getWindow().hide();
        }

    }
}
