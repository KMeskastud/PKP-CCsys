package com.example.ccsys.controllers;

import com.example.ccsys.Start;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import com.example.ccsys.utils.DbUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginControl {

    @FXML
    public TextField loginName;
    @FXML
    public PasswordField password;

    private Connection connection;
    private Statement statement;

    public static void alertMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(s);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void ValidateLogin(ActionEvent actionEvent) throws SQLException, IOException{
        User user = DbQuerys.validateLogin(loginName.getText(), password.getText());//return position

        if (user.getPosition().equals("Student") && user != null)
        {
            alertMessage("Logged in as Student");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-user-window.fxml"));
            Parent root = fxmlLoader.load();
            MainUserWindowControl mainUserWindowControl = fxmlLoader.getController();
            mainUserWindowControl.setLoggedInUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else if (user.getPosition().equals("Lecturer") && user != null)
        {
            alertMessage("Logged in as Lecturer");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window.fxml"));
            Parent root = fxmlLoader.load();
            MainWindowControl mainWindowControl = fxmlLoader.getController();
            mainWindowControl.setLoggedInUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        else if (user != null && user.getPosition().equals("Super"))
        {
            alertMessage("Logged in as SuperAdmin");
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-window-admin.fxml"));
            Parent root = fxmlLoader.load();
            MainAdminWindowControl mainWindowControl = fxmlLoader.getController();
            mainWindowControl.setLoggedInUser(user);
            Scene scene = new Scene(root);
            Stage stage = (Stage) this.loginName.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        else
            alertMessage("Bad credentials");
    }

    public void startSignUp(ActionEvent actionEvent) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader((Start.class.getResource("sign-up-form.fxml")));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) loginName.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
