package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemMemoryDAOTests {

    private DAO<Item> memoryDAO;

    private Item item1;
    private Item item2;
    private Item item3;

    @Before
    public void setUp() {
        item1 = new Item("Item #1", 1000);
        item2 = new Item("Item #2", 1000);
        item3 = new Item("Item #1", 1001);

        memoryDAO = new ItemMemoryDAO();

        memoryDAO.insert(item1);
        memoryDAO.insert(item2);
    }

    @Test
    public void insertTest() {
        assertEquals(2, memoryDAO.size());
        int itemID = memoryDAO.insert(item3);
        assertEquals(3, memoryDAO.size());
        assertTrue(itemID > 0);
    }

    @Test
    public void deleteTest() {
        assertEquals(2, memoryDAO.size());
        int itemID = memoryDAO.insert(item3);
        assertEquals(3, memoryDAO.size());
        memoryDAO.delete(itemID);
        assertEquals(2, memoryDAO.size());
    }

    @Test
    public void getByIDTest() {
        Item item = memoryDAO.getByID(1000);
        assertEquals("Item #1", item.getName());
        item = memoryDAO.getByID(1001);
        assertEquals("Item #2", item.getName());
        assertNull(memoryDAO.getByID(1002));
    }

    @Test
    public void updateTest() {

        assertEquals(2, memoryDAO.size());

        Item item = memoryDAO.getByID(1000);
        assertFalse(item.isCompleted());

        Item itemCompleted = new Item(item.getId(), item.getName(), true, item.getListID());
        assertTrue(memoryDAO.update(itemCompleted));

        item = memoryDAO.getByID(1000);
        assertTrue(item.isCompleted());

        assertEquals(2, memoryDAO.size());
        assertFalse(memoryDAO.update(item3));
    }

    @Test
    public void getByIndexTest() {
        Item item = memoryDAO.getByIndex(0);
        assertEquals(1000, item.getId());
        item = memoryDAO.getByIndex(1);
        assertEquals(1001, item.getId());
    }

    @Test
    public void getAllTest() {
        List<Item> allItems = new ArrayList<>();
        allItems = memoryDAO.getAll();
        assertEquals(2, allItems.size());
    }

    @Test
    public void getAllIDsTest() {
        List<Integer> allIDs = new ArrayList<>();
        allIDs = memoryDAO.getAllIDs();
        assertEquals(2, allIDs.size());
    }

    @Test
    public void searchTest() {
        Item item = memoryDAO.search("name", "item #1");
        assertEquals(1000, item.getId());
        item = memoryDAO.search("name", "item #2");
        assertEquals(1001, item.getId());
        item = memoryDAO.search("name", "some item");
        assertNull(item);
    }

    @Test
    public void getByOwnerIDTest() {
        List<Item> listItems = memoryDAO.getByOwnerID(1000);
        assertEquals(1000, listItems.get(0).getId());
        assertEquals(1001, listItems.get(1).getId());
        assertEquals(2, listItems.size());
        listItems = memoryDAO.getByOwnerID(1001);
        assertEquals(0, listItems.size());
    }

}