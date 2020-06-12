package edu.lwtech.csd299.todo;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

public class MemberSqlDAO implements DAO<Member> {

    private static final Logger logger = Logger.getLogger(MemberSqlDAO.class.getName());
    private Connection conn = null;

    public MemberSqlDAO() {
        this.conn = null; // conn must be created during init()
    }

    public boolean init() {
        logger.info("Connecting to the database...");

        String jdbcDriver = "org.mariadb.jdbc.Driver"; // The MariaDB driver works better than the MySQL driver
        String url = "jdbc:mariadb://localhost:3306/todo?useSSL=false&allowPublicKeyRetrieval=true";

        conn = SQLUtils.connect(url, "todo", "lwtech2000", jdbcDriver); // TODO: Remove DB credentials from the source
                                                                        // code!
        if (conn == null) {
            logger.error("Unable to connect to SQL Database with URL: " + url);
            return false;
        }
        logger.info("...connected!");

        return true;
    }

    public int insert(Member member) {
        logger.debug("Inserting " + member + "...");

        if (member.getID() != -1) {
            logger.error("Attempting to add previously added Member: " + member);
            return -1;
        }

        String query = "INSERT INTO Members";
        query += " (username, password, first_name, last_name, email)";
        query += " VALUES (?,?,?,?,?)";

        int memberID = SQLUtils.executeSQLInsert(conn, query, "id", member.getUsername(), member.getPassword(),
                member.getFirstName(), member.getLastName(), member.getEmail());

        logger.debug("Member successfully inserted with id = " + memberID);
        return memberID;
    }

    public boolean update(Member member) {
        logger.debug("Updating member's data for member with id = " + member.getID());

        String query = "UPDATE Members " + "SET username='" + member.getUsername() + "', password='"
                + member.getPassword() + "', first_name='" + member.getFirstName() + "', last_name='"
                + member.getLastName() + "', email='" + member.getEmail() + "' " + "WHERE id='" + member.getID() + "'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        return (rows != null);
    }

    public void delete(int MemberID) {
        logger.debug("Trying to delete Member with id: " + MemberID);

        String query = "DELETE FROM Members WHERE id=" + MemberID;
        SQLUtils.executeSQL(conn, query);
    }

    public Member getByID(int MemberID) {
        logger.debug("Trying to get Member with ID: " + MemberID);

        String query = "SELECT id, username, password, first_name, last_name, email";
        query += " FROM Members WHERE id=" + MemberID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found member!");
        } else {
            logger.debug("Did not find member.");
            return null;
        }

        SQLRow row = rows.get(0);
        Member member = convertRowToMember(row);
        return member;
    }

    public Member getByIndex(int index) {
        logger.debug("Trying to get Member with index: " + index);

        index++; // SQL uses 1-based indexes

        if (index < 1)
            return null;

        String query = "SELECT id, username, password, first_name, last_name, email";
        query += " FROM Members ORDER BY id LIMIT " + index;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("Did not find member.");
            return null;
        }

        SQLRow row = rows.get(rows.size() - 1);
        Member member = convertRowToMember(row);
        return member;
    }

    public List<Member> getAll() {
        logger.debug("Getting all Members...");

        String query = "SELECT id, username, password, first_name, last_name, email";
        query += " FROM Members ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No members found!");
            return null;
        }

        List<Member> members = new ArrayList<>();
        for (SQLRow row : rows) {
            Member member = convertRowToMember(row);
            members.add(member);
        }
        return members;
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting Member IDs...");

        String query = "SELECT id FROM Members ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No members found!");
            return null;
        }

        List<Integer> memberIDs = new ArrayList<>();
        for (SQLRow row : rows) {
            String value = row.getItem("id");
            int i = Integer.parseInt(value);
            memberIDs.add(i);
        }
        return memberIDs;
    }

    public int size() {
        logger.debug("Getting the number of rows...");

        String query = "SELECT count(*) FROM Members";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        if (rows == null) {
            logger.error("No members found!");
            return 0;
        }

        String value = rows.get(0).getItem();
        return Integer.parseInt(value);
    }

    public Member search(String fieldName, String keyword) {
        logger.debug("Trying to get Member with " + fieldName + " = : " + keyword);

        String query = "SELECT id, username, password, first_name, last_name, email";
        query += " FROM Members WHERE " + fieldName + "=" + keyword;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found member!");
        } else {
            logger.debug("Did not find member.");
            return null;
        }

        SQLRow row = rows.get(0);
        Member member = convertRowToMember(row);
        return member;
    }

    public List<Member> getByOwnerID(int ownerID) {
        return null;
    }

    public void disconnect() {
        SQLUtils.disconnect(conn);
        conn = null;
    }

    // =====================================================================

    private Member convertRowToMember(SQLRow row) {
        int memberID = Integer.parseInt(row.getItem("id"));
        String username = row.getItem("username");
        String password = row.getItem("password");
        String firstName = row.getItem("first_name");
        String lastName = row.getItem("last_name");
        String email = row.getItem("email");
        return new Member(memberID, username, password, firstName, lastName, email);
    }

}