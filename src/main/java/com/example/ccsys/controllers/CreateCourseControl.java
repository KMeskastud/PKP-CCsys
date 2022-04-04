package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.Course;
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

public class CreateFolderControl {

    @FXML
    public TextField folderName;

    private User loggedInUser;
    private Course selectedCourse;
    private int selectedFolderId;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedCourse(Course course) throws SQLException {
        this.selectedCourse = course;
    }

    public void setSelectedFolderId(int folderId) throws SQLException {
        this.selectedFolderId = folderId;
    }

    public void createFolder(ActionEvent actionEvent) throws SQLException, IOException {
        LoginControl.alertMessage(applyFolder(this.selectedCourse.getId(), this.selectedFolderId, this.folderName.getText()));
        this.goBack();
    }

    public String applyFolder(int courseID, int folderID, String name) throws SQLException, IOException {
        boolean doesExist = false;
        for(Folder folder : DbQuerys.getFolders(courseID)) {
            if (folder.getName().equals(name)) {
                doesExist = true;
                return("Folder already exists");
            }
        }
        if(isValidInput(name) == false) {
            return ("Please fill name field");
        }
        if(doesExist == false)
        {
            try {
                DbQuerys.createFolder(new Folder(folderID, courseID, name));
                return("Folder created");
            } catch (Exception e) {
                return("Error creating folder" + e);
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
            Stage stage = (Stage) this.folderName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.folderName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
