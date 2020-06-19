package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ToDoListMemoryDAOTests {

    private DAO<ToDoList> memoryDAO;

    private ToDoList list1;
    private ToDoList list2;
    private ToDoList list3;

    @Before
    public void setUp() {
        list1 = new ToDoList("List #1", 1);
        list2 = new ToDoList("List #2", 1);
        list3 = new ToDoList("List #3", 2);

        memoryDAO = new ToDoListMemoryDAO();

        memoryDAO.insert(list1);
        memoryDAO.insert(list2);
    }

    @Test
    public void insertTest() {
        assertEquals(2, memoryDAO.size());
        int todoListID = memoryDAO.insert(list3);
        assertEquals(3, memoryDAO.size());
        assertTrue(todoListID > 0);
    }

    @Test
    public void deleteTest() {
        assertEquals(2, memoryDAO.size());
        int todoListID = memoryDAO.insert(list3);
        assertEquals(3, memoryDAO.size());
        memoryDAO.delete(todoListID);
        assertEquals(2, memoryDAO.size());
    }

    @Test
    public void getByIDTest() {
        ToDoList todoList = memoryDAO.getByID(1);
        assertEquals(1, todoList.getId());
        todoList = memoryDAO.getByID(2);
        assertEquals(2, todoList.getId());
        assertNull(memoryDAO.getByID(3));
    }

    @Test
    public void updateTest() {

        assertEquals(2, memoryDAO.size());

        ToDoList todoList = memoryDAO.getByID(1);
        assertEquals("List #1", todoList.getDescription());

        ToDoList todoListUpdated = new ToDoList(1, new ToDoList("List #1 updated", todoList.getOwnerID()));
        assertTrue(memoryDAO.update(todoListUpdated));

        todoList = memoryDAO.getByID(1);
        assertEquals("List #1 updated", todoList.getDescription());

        assertEquals(2, memoryDAO.size());
        assertFalse(memoryDAO.update(list3));
    }

    @Test
    public void getByIndexTest() {
        ToDoList todoList = memoryDAO.getByIndex(0);
        assertEquals(1, todoList.getId());
        todoList = memoryDAO.getByIndex(1);
        assertEquals(2, todoList.getId());
    }

    @Test
    public void getAllTest() {
        List<ToDoList> allToDoLists = new ArrayList<>();
        allToDoLists = memoryDAO.getAll();
        assertEquals(2, allToDoLists.size());
    }

    @Test
    public void getAllIDsTest() {
        List<Integer> allIDs = new ArrayList<>();
        allIDs = memoryDAO.getAllIDs();
        assertEquals(2, allIDs.size());
    }

    @Test
    public void searchTest() {
        ToDoList todoList = memoryDAO.search("description", "list #1");
        assertEquals(1, todoList.getId());
        todoList = memoryDAO.search("description", "List #2");
        assertEquals(2, todoList.getId());
        todoList = memoryDAO.search("description", "some list");
        assertNull(todoList);
    }

    @Test
    public void getByOwnerIDTest() {
        List<ToDoList> ownerToDoLists = memoryDAO.getByOwnerID(1);
        assertEquals(1, ownerToDoLists.get(0).getId());
        assertEquals(2, ownerToDoLists.get(1).getId());
        assertEquals(2, ownerToDoLists.size());
        ownerToDoLists = memoryDAO.getByOwnerID(2);
        assertEquals(0, ownerToDoLists.size());
    }

}