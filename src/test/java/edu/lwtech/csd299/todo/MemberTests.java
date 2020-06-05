package edu.lwtech.csd299.todo;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MemberTests {

    Member member;
    Member copyMember;
    Member newMember;

    @Before
    public void setUp() {
        newMember = new Member("user1", "password1", "John", "Smith", "johnsmith@gmail.com");
        member = new Member(10, "user1", "password1", "John", "Smith", "johnsmith@gmail.com");
        copyMember = new Member(11, member);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDTest() {
        Member badMember = new Member(-2, member);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullUserNameTest() {
        Member badMember = new Member(1, null, "password", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyUserNameTest() {
        Member badMember = new Member(1, "", "password", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPasswordTest() {
        Member badMember = new Member(1, "user", null, "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPasswordTest() {
        Member badMember = new Member(1, "user", "", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFirstNameTest() {
        Member badMember = new Member(1, "user", "password", null, "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyFirstNameTest() {
        Member badMember = new Member(1, "user", "password", "", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullEmailTest() {
        Member badMember = new Member(1, "user", "password", "John", "Smith", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyEmailTest() {
        Member badMember = new Member(1, "user", "password", "John", "Smith", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmailTest() {
        Member badMember = new Member(1, "user", "password", "John", "Smith", "email");
    }

    @Test
    public void getIdTest() {
        assertEquals(-1, newMember.getID());
        assertEquals(10, member.getID());
        assertEquals(11, copyMember.getID());
    }

    @Test
    public void getUsernameTest() {
        assertEquals("user1", member.getUsername());
        assertEquals("user1", copyMember.getUsername());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("password1", member.getPassword());
        assertEquals("password1", copyMember.getPassword());
    }

    @Test
    public void getFirstNameTest() {
        assertEquals("John", member.getFirstName());
        assertEquals("John", copyMember.getFirstName());
    }

    @Test
    public void getLastNameTest() {
        assertEquals("Smith", member.getLastName());
        assertEquals("Smith", copyMember.getLastName());
    }

    @Test
    public void getEmailTest() {
        assertEquals("johnsmith@gmail.com", member.getEmail());
        assertEquals("johnsmith@gmail.com", copyMember.getEmail());
    }

    @Test
    public void toStringTest() {
        assertTrue(member.toString().startsWith("[10:"));
        assertTrue(member.toString().contains("user1"));
        assertTrue(member.toString().contains("John"));
        assertTrue(member.toString().contains("Smith"));
        assertTrue(member.toString().contains("johnsmith@gmail.com"));
    }

}