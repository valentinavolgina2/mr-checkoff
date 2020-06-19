package edu.lwtech.csd299.todo;

import java.util.*;

import org.apache.log4j.Logger;

public class ToDoListMemoryDAO implements DAO<ToDoList> {

    private static final Logger logger = Logger.getLogger(ToDoListMemoryDAO.class.getName());

    private int nextID;
    private List<ToDoList> memoryDB;

    public ToDoListMemoryDAO() {
        this.nextID = 1;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init() {
        return true;
    }

    public int insert(ToDoList todoList) {
        logger.debug("Inserting " + todoList + "...");

        if (todoList.getId() != -1) {
            logger.error("Attempting to add previously added to-do list: " + todoList);
            return -1;
        }

        todoList = new ToDoList(generateNextItemID(), todoList);
        memoryDB.add(todoList);

        logger.debug("To-do list was successfully inserted!");
        return todoList.getId();
    }

    public void delete(int id) {
        logger.debug("Trying to delete to-do list with ID: " + id);

        ToDoList todoListFound = null;
        for (ToDoList todoList : memoryDB) {
            if (todoList.getId() == id) {
                todoListFound = todoList;
                break;
            }
        }

        if (todoListFound != null) {
            memoryDB.remove(todoListFound);
            logger.debug("To-do list was successfully deleted!");
        } else {
            logger.debug("To-do list with ID: " + id + " wasn't found!");
        }

    }

    public boolean update(ToDoList todoList) {

        int id = todoList.getId();
        logger.debug("Trying to update to-do list with ID: " + id);

        ToDoList todoListFound = null;
        for (ToDoList oldToDoList : memoryDB) {
            if (oldToDoList.getId() == id) {
                todoListFound = oldToDoList;
                break;
            }
        }

        if (todoListFound != null) {
            memoryDB.remove(todoListFound);
            memoryDB.add(new ToDoList(id, todoList));
            logger.debug("To-do list was successfully updated!");
            return true;
        } else {
            logger.debug("To-do list wasn't found!");
            return false;
        }

    }

    public ToDoList getByID(int id) {
        logger.debug("Trying to get to-do list with ID: " + id);

        ToDoList todoListFound = null;
        for (ToDoList todoList : memoryDB) {
            if (todoList.getId() == id) {
                todoListFound = todoList;
                break;
            }
        }

        if (todoListFound != null) {
            logger.debug("To-do list with ID: " + id + " was found!");
        } else {
            logger.debug("To-do list with ID: " + id + " was not found!");
        }
        return todoListFound;
    }

    public ToDoList getByIndex(int index) {
        // Note: indexes are zero-based
        logger.debug("Trying to get To-Do List with index: " + index);

        if (index < 0 || index > memoryDB.size()) {
            logger.debug("To-Do List with index: " + index + " was not found!");
            return null;
        } else {
            logger.debug("To-Do List with index: " + index + " was found!");
            return memoryDB.get(index);
        }

    }

    public List<ToDoList> getAll() {
        logger.debug("Getting all To-Do Lists");
        return new ArrayList<>(memoryDB);
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting To-Do List IDs...");

        List<Integer> todoListIDs = new ArrayList<>();
        for (ToDoList todoList : memoryDB) {
            todoListIDs.add(todoList.getId());
        }
        return todoListIDs;
    }

    public int size() {
        logger.debug("Getting the size of To-Do List database...");
        return memoryDB.size();
    }

    public ToDoList search(String fieldName, String keyword) {
        logger.debug("Trying to get to-do List with " + fieldName + " equals " + keyword);

        ToDoList todoListFound = null;
        for (ToDoList todoList : memoryDB) {
            if (fieldName.equals("description")
                    && todoList.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                todoListFound = todoList;
                break;
            }
        }

        if (todoListFound != null) {
            logger.debug("To-do list with " + fieldName + " equals " + keyword + " was found!");
        } else {
            logger.debug("To-do list with " + fieldName + " equals " + keyword + " was not found!");
        }
        return todoListFound;

    }

    public List<ToDoList> getByOwnerID(int ownerID) {
        logger.debug("Trying to get to-do List with owner ID: " + ownerID);

        List<ToDoList> ownerToDoLists = new ArrayList<>();
        for (ToDoList todoList : memoryDB) {
            if (todoList.getOwnerID() == ownerID) {
                ownerToDoLists.add(todoList);
            }
        }

        return ownerToDoLists;
    }

    public void disconnect() {
        this.memoryDB = null;
    }

    // =================================================================

    public synchronized int generateNextItemID() {
        return nextID++;
    }

}