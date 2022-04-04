package com.example.ccsys.tests;

import com.example.ccsys.controllers.CreateCourseControl;
import com.example.ccsys.controllers.CreateFolderControl;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CreateFolderControlTest {

    @Test
    public void ApplyFolder_NormalConditions_Success() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);

        CreateFolderControl folder = new CreateFolderControl();
        String message = folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");

        assertEquals("Folder created", message);
    }

    @Test
    public void ApplyFolder_FolderExist_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);

        CreateFolderControl folder = new CreateFolderControl();
        folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");
        String message = folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");

        assertEquals("Folder already exists", message);
    }

    @Test
    public void ApplyFolder_NoName_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);

        CreateFolderControl folder = new CreateFolderControl();
        String message = folder.applyFolder(DbQuerys.getCourseId(1), 0, "");

        assertEquals("Please fill name field", message);
    }
}