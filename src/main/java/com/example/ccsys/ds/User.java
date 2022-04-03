package com.example.ccsys.ds;

public class User {

    private int id;
    private String login;
    private String password;
    private String position;
    private String Name;
    private String Surname;
    private String Email;

    public User(String login, String password, String position, String name, String surname, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.position = position;
        this.Name = name;
        this.Surname = surname;
        this.Email = email;
    }

    public User(int id, String name, String surname, String email) {
        this.id = id;
        this.Name = name;
        this.Surname = surname;
        this.Email = email;
    }

    public User(int id) {
        this.id = id;
    }

    public User( String login, int id, String name, String surname, String email) {
        this.id = id;
        this.Name = name;
        this.Surname = surname;
        this.Email = email;
        this.login = login;
    }

    public User(int id, String name, String surname, String email, String position) {
        this.id = id;
        this.Name = name;
        this.Surname = surname;
        this.Email = email;
        this.position = position;
    }

    public User() {
    }

    public User(int id, String name, String surname) {
        this.id = id;
        Name = name;
        Surname = surname;
    }

    public User(String name, String surname) {
        Name = name;
        Surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setRole(String position) {
        this.position = position;
    }


}
