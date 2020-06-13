package edu.lwtech.csd299.todo;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MemberSqlDAOTests {
    private DAO<Member> memoryDAO;

    private Member fred;
    private Member tom;
    private Member mary;

    @Before
    public void setUp() {

        // // there 3 members added in MySQL Workbench
        // fred = new Member("FredLwtech", "111", "Fred", "", "fred@lwtech.edu");
        // tom = new Member("TomLwtech", "222", "Tom", "", "tom@lwtech.edu");
        // mary = new Member("MaryLwtech", "333", "Mary", "", "mary@lwtech.edu");

        // memoryDAO = new MemberSqlDAO();
        // memoryDAO.init();

    }

    @Test
    public void insertDeleteTest() {
        // int size = memoryDAO.size();
        // int memberID = memoryDAO.insert(fred);
        // assertEquals(size + 1, memoryDAO.size());
        // memoryDAO.delete(memberID);
        // assertEquals(size, memoryDAO.size());
    }

    @Test
    public void getByIDTest() {
        // int memberID = memoryDAO.insert(fred);
        // Member member = memoryDAO.getByID(memberID);
        // assertEquals(memberID, member.getID());
        // memoryDAO.delete(memberID);

        // member = memoryDAO.getByID(1000);
        // assertNull(member);
    }

    @Test
    public void updateTest() {

        // int memberID = memoryDAO.insert(fred);

        // Member fredUpdated = new Member(memberID, fred.getUsername(),
        // fred.getPassword(), fred.getFirstName(),
        // "last name", fred.getEmail());

        // assertEquals("", memoryDAO.getByID(memberID).getLastName());
        // assertTrue(memoryDAO.update(fredUpdated));
        // assertEquals("last name", memoryDAO.getByID(memberID).getLastName());

        // memoryDAO.delete(memberID);
    }

    @Test
    public void getByIndexTest() {
        // Member member = memoryDAO.getByIndex(0);
        // assertEquals(1, member.getID());
        // member = memoryDAO.getByIndex(1);
        // assertEquals(2, member.getID());
    }

    @Test
    public void getAllTest() {
        // List<Member> allMembers = new ArrayList<>();
        // allMembers = memoryDAO.getAll();
        // assertEquals(3, allMembers.size());
    }

    @Test
    public void getAllIDsTest() {
        // List<Integer> allIDs = new ArrayList<>();
        // allIDs = memoryDAO.getAllIDs();
        // assertEquals(3, allIDs.size());
    }

    @Test
    public void searchTest() {
        // Member member = memoryDAO.search("username", "user1");
        // assertEquals(1, member.getID());

        // int memberID = memoryDAO.insert(fred);
        // member = memoryDAO.search("username", "FredLwtech");

        // assertEquals(memberID, member.getID());
        // memoryDAO.delete(memberID);

        // member = memoryDAO.search("username", "Mr");
        // assertNull(member);
    }

}