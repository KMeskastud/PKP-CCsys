package com.example.ccsys.tests;

import com.example.ccsys.controllers.CreateCourseControl;
import com.example.ccsys.ds.User;
import com.example.ccsys.utils.DbQuerys;

import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;

//junit4

public class CreateCourseControlTest {

    @Test
    public void ApplyCourse_NormalConditions_Success() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        String message = course.applyCourse("xxx", "xxx", user);

        assertEquals("Course created", message);
    }

    @Test
    public void ApplyCourse_NoName_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        String message = course.applyCourse("", "xxx", user);

        assertEquals("Please fill name field", message);
    }

    @Test
    public void ApplyCourse_NoDescr_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        String message = course.applyCourse("xxx", "", user);

        assertEquals("Please fill description field", message);
    }

    @Test
    public void ApplyCourse_CourseExist_Fail() throws SQLException, IOException {
        DbQuerys.deleteCourses(1);
        User user = new User(1, "unit", "unit", "unit", "Lecturer");
        CreateCourseControl course = new CreateCourseControl();
        course.applyCourse("xxx", "xxx", user);
        String message = course.applyCourse("xxx", "xxx", user);

        assertEquals("Course already exists", message);
    }
}