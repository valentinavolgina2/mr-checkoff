package edu.lwtech.csd299.todo;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemTests {

    Item newItem;
    Item copyItem;
    Item invalidItem;

    @Before
    public void setUp() {
        newItem = new Item("Item #1", 1);
        copyItem = new Item(10, newItem);
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
        assertEquals(10, copyItem.getID());
    }

    @Test
    public void getNameTest() {
        assertEquals("Item #1", newItem.getName());
        assertEquals("Item #1", copyItem.getName());
    }

    @Test
    public void isCompletedTest() {
        assertFalse(newItem.isCompleted());
    }

    @Test
    public void completeTest() {
        assertFalse(copyItem.isCompleted());
        copyItem.complete();
        assertTrue(copyItem.isCompleted());
    }

    @Test
    public void toStringTest() {
        copyItem.complete();
        assertTrue(newItem.toString().startsWith("[-1:"));
        assertTrue(newItem.toString().contains("Item #1"));
        assertTrue(newItem.toString().contains("No"));
        assertTrue(newItem.toString().contains("list id: 1"));
        assertTrue(copyItem.toString().contains("Yes"));
    }

}