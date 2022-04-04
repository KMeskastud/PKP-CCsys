package com.example.ccsys.tests;

import com.example.ccsys.controllers.SignUpControl;
import com.example.ccsys.utils.DbQuerys;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class SignUpControlTest {

    @Test
    public void signUp_NormalConditions_Success() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("xxx", "x", "x", "x", "x", "Lecturer");

        assertEquals("User created", message);
    }

    @Test
    public void signUp_UserExist_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        signup.signUp("xxx", "x", "x", "x", "x", "Lecturer");
        String message = signup.signUp("xxx", "x", "x", "x", "x", "Lecturer");

        assertEquals("User already exists", message);
    }

    @Test
    public void signUp_EmptyLogin_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("", "x", "x", "x", "x", "Lecturer");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyPsw_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "", "x", "x", "x", "Lecturer");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyName_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "", "x", "x", "Lecturer");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptySur_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "x", "", "x", "Lecturer");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyEmail_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "x", "x", "", "Lecturer");

        assertEquals("Failed", message);
    }

    @Test
    public void signUp_EmptyType_Fail() throws SQLException, IOException {
        DbQuerys.deleteLogin("xxx");
        SignUpControl signup = new SignUpControl();
        String message = signup.signUp("x", "x", "x", "x", "x", "");

        assertEquals("Failed", message);
    }
}