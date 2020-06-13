package edu.lwtech.csd299.todo;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ToDoListTests {

    ToDoList newToDoList;
    ToDoList dbToDoList;
    ToDoList invalidToDoList;

    @Before
    public void setUp() {
        newToDoList = new ToDoList("To-do list #1", 1);
        dbToDoList = new ToDoList(10, newToDoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDTest() {
        invalidToDoList = new ToDoList(-2, newToDoList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOwnerIDTest() {
        invalidToDoList = new ToDoList("To-do list", -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDescriptionTest() {
        invalidToDoList = new ToDoList(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyDescriptionTest() {
        invalidToDoList = new ToDoList("", 2);
    }

    @Test
    public void getIdTest() {
        assertEquals(-1, newToDoList.getId());
        assertEquals(10, dbToDoList.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals("To-do list #1", newToDoList.getDescription());
    }

    @Test
    public void getOwnerIDTest() {
        assertEquals(1, newToDoList.getOwnerID());
    }

    @Test
    public void toStringTest() {
        assertTrue(newToDoList.toString().startsWith("[-1:"));
        assertTrue(dbToDoList.toString().startsWith("[10:"));
        assertTrue(newToDoList.toString().contains("To-do list #1"));
        assertTrue(newToDoList.toString().contains("owner id: 1"));
    }

    @Test
    public void equalsTest() {
        assertFalse(dbToDoList.equals(new ToDoList("List #10", 1)));
        assertFalse(dbToDoList.equals(10));
        assertTrue(dbToDoList.equals(dbToDoList));
        assertTrue(dbToDoList
                .equals(new ToDoList(dbToDoList.getId(), dbToDoList.getDescription(), dbToDoList.getOwnerID())));
    }

}