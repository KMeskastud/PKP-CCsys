package com.example.ccsys.tests;

import com.example.ccsys.controllers.CreateCourseControl;
import com.example.ccsys.controllers.CreateFileControl;
import com.example.ccsys.controllers.CreateFolderControl;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class CreateFileControlTest {

    @Test
    public void ApplyFile_NormalConditions_Success() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);
        CreateFolderControl folder = new CreateFolderControl();
        folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");

        CreateFileControl file = new CreateFileControl();
        String message = file.applyFile(DbQuerys.getFolderId(DbQuerys.getCourseId(1)), "xxx");

        assertEquals("File created", message);
    }

    @Test
    public void ApplyFile_FileExist_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);
        CreateFolderControl folder = new CreateFolderControl();
        folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");

        CreateFileControl file = new CreateFileControl();
        file.applyFile(DbQuerys.getFolderId(DbQuerys.getCourseId(1)), "xxx");
        String message = file.applyFile(DbQuerys.getFolderId(DbQuerys.getCourseId(1)), "xxx");

        assertEquals("File already exists", message);
    }

    @Test
    public void ApplyFile_NoName_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);
        CreateFolderControl folder = new CreateFolderControl();
        folder.applyFolder(DbQuerys.getCourseId(1), 0, "xxx");

        CreateFileControl file = new CreateFileControl();
        String message = file.applyFile(DbQuerys.getFolderId(DbQuerys.getCourseId(1)), "");

        assertEquals("Please fill name field", message);
    }
}