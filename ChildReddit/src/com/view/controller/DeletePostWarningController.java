package com.view.controller;

import com.jfoenix.controls.JFXButton;
import com.model.Post;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class DeletePostWarningController {
    private Post post;
    private ListView<Post> postListView;

    public DeletePostWarningController(Post post, ListView<Post> postListView){
        this.post = post;
        this.postListView = postListView;
    }

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    public void initialize(){

        deleteButton.setOnAction(event -> {delete();});

        cancelButton.setOnAction(event -> {cancelButton.getScene().getWindow().hide();});

    }

    private void delete(){
        post.getUser().deletePost(post.getSubReddit(), post);
        postListView.getItems().remove(post);
        deleteButton.getScene().getWindow().hide();
    }

}
