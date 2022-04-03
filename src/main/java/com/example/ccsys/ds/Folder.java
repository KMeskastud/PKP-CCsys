package com.example.ccsys.ds;

import java.util.ArrayList;

public class Folder {

    private int id;
    private int parentId;
    private int courseId;
    private String name;

    public Folder() {
    }

    public Folder(int id, int parentId, int courseId, String name) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.courseId = courseId;
    }

    public Folder(int parentId, int courseId, String name) {
        this.name = name;
        this.parentId = parentId;
        this.courseId = courseId;
    }

    public Folder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Folder(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
