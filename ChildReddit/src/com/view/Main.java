package com.view;

import com.model.SubReddit;
import com.model.User;
import com.view.controller.HomePageController;
import com.view.controller.OpenWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null), "ChildReddit - HomePage");
    }


    public static void main(String[] args){

        try {
            FileInputStream fisUser = new FileInputStream("UserData.txt");
            ObjectInputStream oisUser = new ObjectInputStream(fisUser);
            User.setUsers((ArrayList<User>)oisUser.readObject());
            oisUser.close();
            for(User user: User.getUsers()){
                for(SubReddit subReddit: user.getSubReddits()){
                    if(!SubReddit.getSubRedditCreated().contains(subReddit)){
                        SubReddit.addSubReddit(subReddit);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        launch(args);
        WriteData.writeUsers();
        System.out.println("End");
    }
}
