package edu.lwtech.csd299.todo;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemTests {

    Item newItem;
    Item dbItem;
    Item invalidItem;

    @Before
    public void setUp() {
        newItem = new Item("Item #1", 1);
        dbItem = new Item(10, newItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDTest() {
        invalidItem = new Item(-2, newItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidListIDTest() {
        invalidItem = new Item("Item #1", -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullNameTest() {
        invalidItem = new Item(null, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyNameTest() {
        invalidItem = new Item("", 1);
    }

    @Test
    public void getIdTest() {
        assertEquals(-1, newItem.getID());
        assertEquals(10, dbItem.getID());
    }

    @Test
    public void getNameTest() {
        assertEquals("Item #1", newItem.getName());
        assertEquals("Item #1", dbItem.getName());
    }

    @Test
    public void getListIDTest() {
        assertEquals(1, newItem.getListID());
        assertEquals(1, dbItem.getListID());
    }

    @Test
    public void isCompletedTest() {
        assertFalse(newItem.isCompleted());
        Item completedItem = new Item(1000, "item # 3", true, 1000);
        assertTrue(completedItem.isCompleted());
    }

    @Test
    public void toStringTest() {
        assertTrue(newItem.toString().startsWith("[-1:"));
        assertTrue(newItem.toString().contains("Item #1"));
        assertTrue(newItem.toString().contains("No"));
        assertTrue(newItem.toString().contains("list id: 1"));
    }

    @Test
    public void equalsTest() {
        assertFalse(dbItem.equals(new Item("Item #10", 1)));
        assertFalse(dbItem.equals(10));
        assertTrue(dbItem.equals(dbItem));
        assertTrue(dbItem.equals(new Item(dbItem.getID(), dbItem.getName(), dbItem.isCompleted(), dbItem.getListID())));
    }

}