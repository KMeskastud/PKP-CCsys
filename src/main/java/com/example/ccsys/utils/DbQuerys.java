package com.example.ccsys.utils;

import com.example.ccsys.controllers.LoginControl;
import com.example.ccsys.ds.Course;
import com.example.ccsys.ds.File;
import com.example.ccsys.ds.Folder;
import com.example.ccsys.ds.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

public class DbQuerys {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public static int getLatestCreationId(String tableName) throws SQLException {

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM " + tableName + " WHERE id=(SELECT max(id) FROM " + tableName + ")";
        ResultSet rs = statement.executeQuery(query);
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //Users//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static User validateLogin(String login, String password) throws SQLException{

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE login = '" + login + "' AND password = '" + password + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = "null";
        String userSurname = "null";
        String userPosition = "null";
        String email = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userPosition = rs.getString("person_position");
            email = rs.getString("person_email");
        }
        DbUtils.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, email, userPosition);
        return user;
    }

    public static void createUser(User user) throws SQLException{
            connection = DbUtils.connectToDb();
            String insertString = "INSERT INTO user(`login`, `password`, `person_name`, `person_surname`, `person_position`, `person_email`) VALUES (?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getSurname());
            preparedStatement.setString(5, user.getPosition());
            preparedStatement.setString(6, user.getEmail());
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void editUser(String loginName, String password, String name, String surname, String email, int id) throws SQLException{
            connection = DbUtils.connectToDb();
            String insertString = "UPDATE user SET login = '" + loginName + "', password = '" + password + "', person_name = '" + name + "', person_surname = '" + surname + "', person_email = '" + email + "' where id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void resetUser(int id, String password) throws SQLException {
            connection = DbUtils.connectToDb();
            String insertString = "UPDATE user SET password = '" + password + "' where id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void deleteUser(int id) throws SQLException{
        connection = DbUtils.connectToDb();
        String query1 = "DELETE FROM user WHERE id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static ArrayList<User> getUsers() throws SQLException {//////not done
        ArrayList<User> users = new ArrayList<>();
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query1 = "SELECT * FROM user";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            users.add(new User(rs1.getString("login"), rs1.getInt(1), rs1.getString("person_name"), rs1.getString("person_surname"), rs1.getString("person_email")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return users;
    }

    public static User getUser(int userId) throws SQLException{//webui
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE id = '" + userId + "'";
        ResultSet rs = statement.executeQuery(query);
        String userName = "null";
        String userSurname = "null";
        String userPosition = "null";
        String email = "null";
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
            userName = rs.getString("person_name");
            userSurname = rs.getString("person_surname");
            userPosition = rs.getString("person_position");
            email = rs.getString("person_email");
        }
        DbUtils.disconnectFromDb(connection, statement);

        User user = new User(id, userName, userSurname, email, userPosition);
        return user;
    }

    public static ArrayList<User> getAccessedUsers(int courseId) throws SQLException{
        ArrayList<User> users = new ArrayList<>();
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query1 = "SELECT user_id FROM course_access WHERE  course_id = '" + courseId + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            users.add(new User(rs1.getInt("user_id")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return users;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //Courses//
////////////////////////////////////////////////////////////////////////////////////////////////////



    public static ArrayList<Course> getCourses(int userId) throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Integer> coursesId = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT course_id user FROM course_access WHERE user_id = '" + userId + "'";
        ResultSet rs = statement.executeQuery(query1);
        while (rs.next()) {
            coursesId.add(rs.getInt(1));
        }
        for (int i = 0; i < coursesId.size(); i++)
        {
            String query = "SELECT * FROM course WHERE id = '" + coursesId.get(i) + "'";
            ResultSet rs1 = statement.executeQuery(query);
            while (rs1.next()) {
                courses.add(new Course(rs1.getInt(1), rs1.getString("course_name"), rs1.getString("description")));
            }

        }
        DbUtils.disconnectFromDb(connection, statement);
        return courses;
    }

    public static ArrayList<Course> getAllCourses() throws SQLException {
        ArrayList<Course> courses = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

            String query = "SELECT * FROM course";
            ResultSet rs1 = statement.executeQuery(query);
            while (rs1.next()) {
                courses.add(new Course(rs1.getInt(1), rs1.getString("course_name"), rs1.getString("description")));
            }

        DbUtils.disconnectFromDb(connection, statement);
        return courses;
    }

    public static void editCourse(Course course, int id) throws SQLException {

            connection = DbUtils.connectToDb();
            String insertString = "UPDATE course SET course_name = '" + course.getName() + "', description = '" + course.getDescription() + "' where id = '" + id + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);

    }

    public static void createCourse(Course course, User user) throws SQLException {
            connection = DbUtils.connectToDb();
            String insertString = "INSERT INTO Course(`course_name`, `description`) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.execute();

            int id = DbQuerys.getLatestCreationId("course");
            course.setId(id);

            String insertStringForMapping = "INSERT INTO course_access(`user_id`,`course_id`) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(insertStringForMapping);
            preparedStatement.setString(1, Integer.toString(user.getId()));
            preparedStatement.setString(2, Integer.toString(id));
            preparedStatement.execute();

            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void deleteCourse(int courseId) throws SQLException {
        ArrayList<Integer> folderIds = new ArrayList<>();

        //delete files of course
        connection = DbUtils.connectToDb();
        statement = connection.createStatement();
        String query = "SELECT id FROM folder WHERE course_id = '" + courseId + "'";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            folderIds.add(Integer.valueOf(rs.getInt("id")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        connection = DbUtils.connectToDb();
        for(Integer folderId : folderIds) {
            String query1 = "DELETE FROM file WHERE folder_id = '" + folderId + "'";
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.execute();
        }

        //delete folders of course
        String query2 = "DELETE FROM folder WHERE course_id = '" + courseId + "'";
        preparedStatement = connection.prepareStatement(query2);
        preparedStatement.execute();

        //delete course accessibilities
        String query3 = "DELETE FROM course_access WHERE course_id = '" + courseId + "'";
        preparedStatement = connection.prepareStatement(query3);
        preparedStatement.execute();

        //delete course
        String query4 = "DELETE FROM course WHERE id = '" + courseId + "'";
        preparedStatement = connection.prepareStatement(query4);
        preparedStatement.execute();

        DbUtils.disconnectFromDb(connection, preparedStatement);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //FOLDERS//
////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void deleteFolder(int id) throws SQLException{

        //delete files of folder
        connection = DbUtils.connectToDb();
        String query1 = "DELETE FROM file WHERE folder_id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();

        //delete folder
        String query3 = "DELETE FROM folder WHERE id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query3);
        preparedStatement.execute();

        DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void createFolder(Folder folder) throws SQLException
    {
            connection = DbUtils.connectToDb();
            String insertString = "INSERT INTO folder(`folder_name`, `course_id`, `parent_id`) VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, folder.getName());
            preparedStatement.setInt(2, folder.getCourseId());
            preparedStatement.setInt(3, folder.getParentId());
            preparedStatement.execute();

            int id = DbQuerys.getLatestCreationId("folder");
            folder.setId(id);

            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void editFolder(Folder folder) throws SQLException{
            connection = DbUtils.connectToDb();
            String insertString = "UPDATE folder SET folder_name = '" + folder.getName() + "' WHERE id = '" + folder.getId() + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static ArrayList<Folder> getFolders(int courseId) throws SQLException {
        ArrayList<Folder> folders = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT * FROM folder WHERE course_id = '" + courseId + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            folders.add(new Folder(rs1.getInt(1), rs1.getInt("parent_id"), rs1.getInt("course_id"), rs1.getString("folder_name")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return folders;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////
    //Files//
////////////////////////////////////////////////////////////////////////////////////////////////////



    public static ArrayList<File> getFiles(int folderId) throws SQLException {
        ArrayList<File> files = new ArrayList<>();

        connection = DbUtils.connectToDb();
        statement = connection.createStatement();

        String query1 = "SELECT * FROM file WHERE folder_id = '" + folderId + "'";
        ResultSet rs1 = statement.executeQuery(query1);
        while (rs1.next()) {
            files.add(new File(rs1.getInt(1), rs1.getString("file_name"), rs1.getInt("folder_id")));
        }
        DbUtils.disconnectFromDb(connection, statement);
        return files;
    }

    public static void createFile(File file) throws SQLException{

            connection = DbUtils.connectToDb();
            String insertString = "INSERT INTO file(`file_name`, `folder_id`) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.setString(1, file.getName());
            preparedStatement.setInt(2, file.getFolderId());
            preparedStatement.execute();

            int id = DbQuerys.getLatestCreationId("file");
            file.setId(id);

            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void editFile(File file) throws SQLException {
            connection = DbUtils.connectToDb();
            String insertString = "UPDATE file SET file_name = '" + file.getName() + "' where id = '" + file.getId() + "'";
            preparedStatement = connection.prepareStatement(insertString);
            preparedStatement.execute();
            DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void deleteFile(int id) throws SQLException{
        connection = DbUtils.connectToDb();
        String query1 = "DELETE FROM file WHERE id = '" + id + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void addUserAccess(int courseId, int userId) throws SQLException {
        connection = DbUtils.connectToDb();
        String insertString = "INSERT INTO course_access(`user_id`, `course_id`) Values (?, ?)";
        preparedStatement = connection.prepareStatement(insertString);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, courseId);
        preparedStatement.execute();
        DbUtils.disconnectFromDb(connection, preparedStatement);
    }

    public static void removeUserAccess(int courseId, int userId) throws SQLException {
        connection = DbUtils.connectToDb();
        String query1 = "DELETE FROM course_access WHERE course_id = '" + courseId + "' AND user_id = '" + userId + "'";
        preparedStatement = connection.prepareStatement(query1);
        preparedStatement.execute();
        DbUtils.disconnectFromDb(connection, preparedStatement);
    }
}

