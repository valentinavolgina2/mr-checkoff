package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MemberMemoryDAOTests {

    private DAO<Member> memoryDAO;

    private Member fred;
    private Member tom;
    private Member mary;

    @Before
    public void setUp() {
        fred = new Member("FredLwtech", "111", "Fred", "", "fred@lwtech.edu");
        tom = new Member("TomLwtech", "222", "Tom", "", "tom@lwtech.edu");
        mary = new Member("MaryLwtech", "333", "Mary", "", "mary@lwtech.edu");

        memoryDAO = new MemberMemoryDAO();

        memoryDAO.insert(fred);
        memoryDAO.insert(tom);
    }

    @Test
    public void insertTest() {
        assertEquals(2, memoryDAO.size());
        int memberID = memoryDAO.insert(mary);
        assertEquals(3, memoryDAO.size());
        assertTrue(memberID > 0);
    }

    @Test
    public void deleteTest() {
        assertEquals(2, memoryDAO.size());
        int memberID = memoryDAO.insert(mary);
        assertEquals(3, memoryDAO.size());
        memoryDAO.delete(memberID);
        assertEquals(2, memoryDAO.size());
    }

    @Test
    public void getByIDTest() {
        Member member = memoryDAO.getByID(1000);
        assertEquals(1000, member.getID());
        member = memoryDAO.getByID(1001);
        assertEquals(1001, member.getID());
    }

    @Test
    public void updateTest() {

        assertEquals(2, memoryDAO.size());

        Member member = memoryDAO.getByID(1000);
        assertEquals("", member.getLastName());

        Member fredUpdated = new Member(1000, new Member("FredLwtech", "111", "Fred", "Johnson", "fred@lwtech.edu"));
        assertTrue(memoryDAO.update(fredUpdated));

        member = memoryDAO.getByID(1000);
        assertEquals("Johnson", member.getLastName());

        assertEquals(2, memoryDAO.size());
        assertFalse(memoryDAO.update(mary));
    }

    @Test
    public void getByIndexTest() {
        Member member = memoryDAO.getByIndex(0);
        assertEquals(1000, member.getID());
        member = memoryDAO.getByIndex(1);
        assertEquals(1001, member.getID());
    }

    @Test
    public void getAllTest() {
        List<Member> allMembers = new ArrayList<>();
        allMembers = memoryDAO.getAll();
        assertEquals(2, allMembers.size());
    }

    @Test
    public void getAllIDsTest() {
        List<Integer> allIDs = new ArrayList<>();
        allIDs = memoryDAO.getAllIDs();
        assertEquals(2, allIDs.size());
    }

    @Test
    public void searchTest() {
        Member member = memoryDAO.search("username", "FredLwtech");
        assertEquals(1000, member.getID());
        member = memoryDAO.search("email", "tom@lwtech.edu");
        assertEquals(1001, member.getID());
        member = memoryDAO.search("username", "some user");
        assertNull(member);
    }

}