package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ToDoListSqlDAOTests {
    private DAO<ToDoList> sqlDAO;

    private ToDoList list;

    @Before
    public void setUp() {

        // there 4 lists added in MySQL Workbench
        // list = new ToDoList("todo list", 1);

        // sqlDAO = new ToDoListSqlDAO();
        // sqlDAO.init();

    }

    @Test
    public void insertDeleteTest() {
        // int size = 4; // sqlDAO.size();
        // int listID = sqlDAO.insert(list);
        // assertEquals(size + 1, sqlDAO.size());
        // sqlDAO.delete(listID);
        // assertEquals(size, sqlDAO.size());
    }

    @Test
    public void getByIDTest() {
        // int listID = sqlDAO.insert(list);
        // ToDoList listFound = sqlDAO.getByID(listID);
        // assertEquals(listID, listFound.getID());
        // sqlDAO.delete(listID);

        // listFound = sqlDAO.getByID(1000);
        // assertNull(listFound);
    }

    @Test
    public void updateTest() {

        // int listID = sqlDAO.insert(list);

        // ToDoList listUpdated = new ToDoList(listID, "updated", list.getOwnerID());

        // assertEquals("todo list", sqlDAO.getByID(listID).getDescription());
        // assertTrue(sqlDAO.update(listUpdated));
        // assertEquals("updated", sqlDAO.getByID(listID).getDescription());

        // sqlDAO.delete(listID);
    }

    @Test
    public void getByIndexTest() {
        // ToDoList list = sqlDAO.getByIndex(0);
        // assertEquals(1, list.getID());
        // list = sqlDAO.getByIndex(1);
        // assertEquals(2, list.getID());
    }

    @Test
    public void getAllTest() {
        // List<ToDoList> allLists = new ArrayList<>();
        // allLists = sqlDAO.getAll();
        // assertEquals(4, allLists.size());
    }

    @Test
    public void getAllIDsTest() {
        // List<Integer> allIDs = new ArrayList<>();
        // allIDs = sqlDAO.getAllIDs();
        // assertEquals(4, allIDs.size());
    }

    @Test
    public void searchTest() {
        // ToDoList listFound = sqlDAO.search("description", "#2");
        // assertEquals(2, listFound.getID());

        // int listID = sqlDAO.insert(list);
        // listFound = sqlDAO.search("description", "todo");

        // assertEquals(listID, listFound.getID());
        // sqlDAO.delete(listID);

        // listFound = sqlDAO.search("description", "description");
        // assertNull(listFound);
    }

}