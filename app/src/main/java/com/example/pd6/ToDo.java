package com.example.pd6;

public class ToDo {

    public ToDo(int listId, String title, String description) {
        this.listId = listId;
        this.title = title;
        this.description = description;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int listId;
    private String title,description;
}
