package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemSqlDAOTests {
    private DAO<Item> sqlDAO;

    private Item item;

    @Before
    public void setUp() {

        // // there 4 lists added in MySQL Workbench
        // item = new Item("some item", 1);

        // sqlDAO = new ItemSqlDAO();
        // sqlDAO.init();

    }

    @Test
    public void insertDeleteTest() {
        // int size = 4; // sqlDAO.size();
        // int itemID = sqlDAO.insert(item);
        // assertEquals(size + 1, sqlDAO.size());
        // sqlDAO.delete(itemID);
        // assertEquals(size, sqlDAO.size());
    }

    @Test
    public void getByIDTest() {
        // int itemID = sqlDAO.insert(item);
        // Item itemFound = sqlDAO.getByID(itemID);
        // assertEquals(itemID, itemFound.getID());
        // sqlDAO.delete(itemID);

        // itemFound = sqlDAO.getByID(1000);
        // assertNull(itemFound);
    }

    @Test
    public void updateTest() {

        // int itemID = sqlDAO.insert(item);

        // Item itemUpdated = new Item(itemID, item.getName(), true, item.getListID());

        // assertFalse(sqlDAO.getByID(itemID).isCompleted());
        // assertTrue(sqlDAO.update(itemUpdated));
        // assertTrue(sqlDAO.getByID(itemID).isCompleted());

        // sqlDAO.delete(itemID);
    }

    @Test
    public void getByIndexTest() {
        // Item itemFound = sqlDAO.getByIndex(0);
        // assertEquals(1, itemFound.getID());
        // itemFound = sqlDAO.getByIndex(1);
        // assertEquals(2, itemFound.getID());
    }

    @Test
    public void getAllTest() {
        // List<Item> allItems = new ArrayList<>();
        // allItems = sqlDAO.getAll();
        // assertEquals(4, allItems.size());
    }

    @Test
    public void getAllIDsTest() {
        // List<Integer> allIDs = new ArrayList<>();
        // allIDs = sqlDAO.getAllIDs();
        // assertEquals(4, allIDs.size());
    }

    @Test
    public void searchTest() {
        // Item itemFound = sqlDAO.search("name", "CSD");
        // assertNull(itemFound);

        // int itemID = sqlDAO.insert(item);
        // itemFound = sqlDAO.search("name", "some");

        // assertEquals(itemID, itemFound.getID());
        // sqlDAO.delete(itemID);
    }

}