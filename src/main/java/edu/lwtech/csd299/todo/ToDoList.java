package edu.lwtech.csd299.todo;

//import java.util.*;

public class ToDoList {

    private int id; // Database ID (or -1 if it isn't in the database yet)
    private String description;
    private int ownerID;

    public ToDoList(String description, int ownerID) {
        this(-1, description, ownerID);
    }

    public ToDoList(int id, ToDoList todoList) {
        this(id, todoList.description, todoList.ownerID);
    }

    public ToDoList(int id, String description, int ownerID) {

        if (id < -1)
            throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (ownerID < -1)
            throw new IllegalArgumentException("Invalid argument. owner id < -1.");
        if (description == null || description.isEmpty())
            throw new IllegalArgumentException("ToDoList: description cannot be empty or null");

        this.id = id;
        this.description = description;
        this.ownerID = ownerID;
    }

    public int getID() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getOwnerID() {
        return ownerID;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + description + ", owner id: " + ownerID + "]";
    }

}