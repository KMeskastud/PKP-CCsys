package com.example.ccsys.controllers;

import com.example.ccsys.Start;
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

public class EditFileControl {

    @FXML
    public TextField fileName;

    private User loggedInUser;
    private int selectedFileId;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void setSelectedFileId(int fileId) throws SQLException {
        this.selectedFileId = fileId;
    }

    public void editFile(ActionEvent actionEvent) throws SQLException, IOException   {
        try {
            DbQuerys.editFile(new File(this.selectedFileId, this.fileName.getText()));
            LoginControl.alertMessage("File updated");
        } catch (Exception e) {
            LoginControl.alertMessage("Error updating file" + e);
        }
        this.goBack();
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
