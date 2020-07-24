package com.view;

import com.model.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class WriteData {

    public static void writeUsers(){
        try {
            FileOutputStream fos = new FileOutputStream("UserData.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(User.getUsers());
            oos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
