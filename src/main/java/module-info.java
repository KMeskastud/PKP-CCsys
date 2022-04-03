module com.example.ccsys {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires spring.web;
    requires spring.context;

    requires mysql.connector.java;
    requires spring.core;
    requires com.fasterxml.jackson.databind;

    opens com.example.ccsys to javafx.fxml;
    exports com.example.ccsys;
    exports com.example.ccsys.controllers;
    opens com.example.ccsys.controllers to javafx.fxml;
}