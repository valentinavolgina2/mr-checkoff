package edu.lwtech.csd299.todo;

public class Item implements Cloneable {

    private int id; // Database ID (or -1 if it isn't in the database yet)
    private String name;
    private boolean completed;
    private int listID;

    public Item(String name, int listID) {
        this(-1, name, false, listID);
    }

    public Item(int id, Item item) {
        this(id, item.name, item.completed, item.listID);
    }

    public Item(int id, String name, boolean completed, int listID) {
        if (id < -1)
            throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (listID < -1)
            throw new IllegalArgumentException("Invalid argument. List id < -1.");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Item: name cannot be empty or null");

        this.id = id;
        this.name = name;
        this.completed = completed;
        this.listID = listID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public int getListID() {
        return listID;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + name + ", completed: " + (completed ? "Yes" : "No") + ", list id: " + listID + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Item))
            return false;

        Item item = (Item) o;
        return (item.id == this.id) && (item.name == this.name) && (item.completed == this.completed)
                && (item.listID == this.listID);

    }

}