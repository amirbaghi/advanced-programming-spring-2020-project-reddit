package com.view.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MediaPlayerController {
    private String path;
    public MediaPlayerController(String path){this.path = path;}

    @FXML
    private MediaView mediaView;

    @FXML
    private ImageView pauseIcon;

    @FXML
    private ImageView playIcon;

    @FXML
    private ImageView stopIcon;

    @FXML
    public void initialize() {

        Media media = new Media(path);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);

        playIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> mediaPlayer.play());
        pauseIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> mediaPlayer.pause());
        stopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> mediaPlayer.stop());
    }
}
