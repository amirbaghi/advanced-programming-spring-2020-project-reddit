package com.view;

import com.exception.UserExistException;
import com.model.Gender;
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
    public void start(Stage primaryStage) throws IOException {
        OpenWindow.openWindow("../HomePage.fxml", new HomePageController(null), "Reddit - HomePage");
    }


    public static void main(String[] args) throws UserExistException {
        //User user1 = User.signUp("Mohammad","123456",Gender.Male);

        try {
            FileInputStream fisUser = new FileInputStream("UserData.txt");
            ObjectInputStream oisUser = new ObjectInputStream(fisUser);
            User.setUsers((ArrayList<User>)oisUser.readObject());
            oisUser.close();
            //User.getUsers().get(0).createSubReddit("Hello");
            //User.getUsers().get(0).setAge(21);
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