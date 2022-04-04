package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.Folder;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class CreateFileControl {

    @FXML
    public TextField fileName;

    private User loggedInUser;
    private int selectedFolderId;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedFolderId(int folderId) throws SQLException {
        this.selectedFolderId = folderId;
    }

    public void createFile(ActionEvent actionEvent) throws SQLException, IOException   {
        LoginControl.alertMessage(applyFile(selectedFolderId, this.fileName.getText()));
        this.goBack();
    }

    public String applyFile(int folderID, String name) throws SQLException {
        boolean doesExist = false;
        for(File file : DbQuerys.getFiles(folderID)) {
            if (file.getName().equals(name)) {
                doesExist = true;
                return("File already exists");
            }
        }
        if(isValidInput(name) == false) {
            return ("Please fill name field");
        }
        if(doesExist == false)
        {
            try {
                DbQuerys.createFile(new File(name, folderID));
                return("File created");
            } catch (Exception e) {
                System.out.println(e);
                return("Error creating file" + e);
            }
        }
        return ("Failed");
    }

    private boolean isValidInput(String input) {
        if (input.length() == 0)
            return false;
        return true;
    }

    public void goBack() throws IOException, SQLException {
        if(loggedInUser.getPosition().equals("Super")) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window-admin.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainAdminWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.fileName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.fileName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
