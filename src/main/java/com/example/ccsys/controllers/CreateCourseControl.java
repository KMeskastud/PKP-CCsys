package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.Course;
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

public class CreateCourseControl {

    @FXML
    public TextField courseName;
    @FXML
    public TextField courseDescription;

    private User loggedInUser;

    public void setLoggedInUser(User user) throws SQLException {
        this.loggedInUser = user;
    }

    public void createCourse(ActionEvent actionEvent) throws IOException, SQLException {
        boolean doesExist = false;
        for(Course course : DbQuerys.getAllCourses()) {
            if (course.getName().equals(this.courseName.getText())) {
                LoginControl.alertMessage("Course already exists");
                doesExist = true;
                break;
            }
        }
        if(doesExist == false)
        {
            try {
                DbQuerys.createCourse(new Course(this.courseName.getText(), this.courseDescription.getText()), this.loggedInUser);
                LoginControl.alertMessage("Course created");
            } catch (Exception e) {
                System.out.println(e);
                LoginControl.alertMessage("Error creating Course" + e);
            }
        }
        this.goBack();
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
            Stage stage = (Stage) this.courseName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            MainWindowControl mainCoursesWindow = fxmlLoader.getController();
            mainCoursesWindow.setLoggedInUser(this.loggedInUser);
            Stage stage = (Stage) this.courseName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
