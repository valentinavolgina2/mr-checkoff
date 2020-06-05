package edu.lwtech.csd299.todo;

public class Member {

    private int id; // Database ID (or -1 if it isn't in the database yet)
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;

    public Member(String username, String password, String firstName, String lastName, String email) {
        this(-1, username, password, firstName, lastName, email);
    }

    public Member(int id, Member member) {
        this(id, member.username, member.password, member.firstName, member.lastName, member.email);
    }

    public Member(int id, String username, String password, String firstName, String lastName, String email) {

        if (id < -1)
            throw new IllegalArgumentException("Invalid argument. id < -1.");
        if (username == null || username.isEmpty())
            throw new IllegalArgumentException("Member: username cannot be empty or null");
        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Member: password cannot be empty or null");
        if (firstName == null || firstName.isEmpty())
            throw new IllegalArgumentException("Member: firstName cannot be empty or null");
        if (email == null || email.isEmpty() || !email.contains("@"))
            throw new IllegalArgumentException("Member: email cannot miss '@', be empty, or null");

        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + username + "( " + firstName + " " + lastName + ")" + ", ******, " + email + "]";
    }

}