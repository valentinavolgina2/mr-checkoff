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
    }

    @Test
    public void completeTest() {
        assertFalse(dbItem.isCompleted());
        dbItem.complete();
        assertTrue(dbItem.isCompleted());
    }

    @Test
    public void toStringTest() {
        dbItem.complete();
        assertTrue(newItem.toString().startsWith("[-1:"));
        assertTrue(newItem.toString().contains("Item #1"));
        assertTrue(newItem.toString().contains("No"));
        assertTrue(newItem.toString().contains("list id: 1"));
        assertTrue(dbItem.toString().contains("Yes"));
    }

    @Test
    public void cloneTest() {
        Item cloneItem = dbItem.clone();
        dbItem.complete();
        assertEquals(10, dbItem.getID());
        assertEquals(10, cloneItem.getID());
        assertEquals("Item #1", dbItem.getName());
        assertEquals("Item #1", cloneItem.getName());
        assertEquals(1, dbItem.getListID());
        assertEquals(1, cloneItem.getListID());
        assertTrue(dbItem.isCompleted());
        assertFalse(cloneItem.isCompleted());
    }

}