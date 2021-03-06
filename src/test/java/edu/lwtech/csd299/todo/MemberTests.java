package edu.lwtech.csd299.todo;

//import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

public class MemberTests {

    Member member;
    Member copyMember;
    Member newMember;
    Member invalidMember;

    @Before
    public void setUp() {
        newMember = new Member("user1", "password1", "John", "Smith", "johnsmith@gmail.com");
        member = new Member(10, "user1", "password1", "John", "Smith", "johnsmith@gmail.com");
        copyMember = new Member(11, member);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDTest() {
        invalidMember = new Member(-2, member);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullUserNameTest() {
        invalidMember = new Member(1, null, "password", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyUserNameTest() {
        invalidMember = new Member(1, "", "password", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullPasswordTest() {
        invalidMember = new Member(1, "user", null, "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyPasswordTest() {
        invalidMember = new Member(1, "user", "", "John", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullFirstNameTest() {
        invalidMember = new Member(1, "user", "password", null, "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyFirstNameTest() {
        invalidMember = new Member(1, "user", "password", "", "Smith", "email@gmail.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullEmailTest() {
        invalidMember = new Member(1, "user", "password", "John", "Smith", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyEmailTest() {
        invalidMember = new Member(1, "user", "password", "John", "Smith", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void notEmailTest() {
        invalidMember = new Member(1, "user", "password", "John", "Smith", "email");
    }

    @Test
    public void getIdTest() {
        assertEquals(-1, newMember.getId());
        assertEquals(10, member.getId());
        assertEquals(11, copyMember.getId());
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