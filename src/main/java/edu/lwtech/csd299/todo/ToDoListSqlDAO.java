package edu.lwtech.csd299.todo;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

public class ToDoListSqlDAO implements DAO<ToDoList> {
    private static final Logger logger = Logger.getLogger(ToDoListSqlDAO.class.getName());
    private Connection conn = null;

    public ToDoListSqlDAO() {
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

    public int insert(ToDoList list) {
        logger.debug("Inserting " + list + "...");

        if (list.getID() != -1) {
            logger.error("Attempting to add previously added To-Do list: " + list);
            return -1;
        }

        String query = "INSERT INTO ToDoLIsts";
        query += " (owner_id, description)";
        query += " VALUES (?,?)";

        int listID = SQLUtils.executeSQLInsert(conn, query, "id", "" + list.getOwnerID(), list.getDescription());

        logger.debug("ToDo List successfully inserted with id = " + listID);
        return listID;
    }

    public boolean update(ToDoList list) {
        logger.debug("Updating todo list's data for todo list with id = " + list.getID());

        String query = "UPDATE ToDoLIsts " + "SET owner_id='" + list.getOwnerID() + "', description='"
                + list.getDescription() + "' " + "WHERE id='" + list.getID() + "'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        return (rows != null);
    }

    public void delete(int listID) {
        logger.debug("Trying to delete ToDo List with id: " + listID);

        String query = "DELETE FROM ToDoLIsts WHERE id=" + listID;
        SQLUtils.executeSQL(conn, query);
    }

    public ToDoList getByID(int listID) {
        logger.debug("Trying to get ToDo List with ID: " + listID);

        String query = "SELECT id, owner_id, description";
        query += " FROM ToDoLIsts WHERE id=" + listID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found todo list!");
        } else {
            logger.debug("Did not find todo list.");
            return null;
        }

        SQLRow row = rows.get(0);
        ToDoList list = convertRowToToDoList(row);
        return list;
    }

    public ToDoList getByIndex(int index) {
        logger.debug("Trying to get ToDo List with index: " + index);

        index++; // SQL uses 1-based indexes

        if (index < 1)
            return null;

        String query = "SELECT id, owner_id, description";
        query += " FROM ToDoLIsts ORDER BY id LIMIT " + index;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("Did not find todo list.");
            return null;
        }

        SQLRow row = rows.get(rows.size() - 1);
        ToDoList list = convertRowToToDoList(row);
        return list;
    }

    public List<ToDoList> getAll() {
        logger.debug("Getting all ToDo lists...");

        String query = "SELECT id, owner_id, description";
        query += " FROM ToDoLIsts ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No todo lists found!");
            return null;
        }

        List<ToDoList> lists = new ArrayList<>();
        for (SQLRow row : rows) {
            ToDoList list = convertRowToToDoList(row);
            lists.add(list);
        }
        return lists;
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting ToDo List IDs...");

        String query = "SELECT id FROM ToDoLIsts ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No todo lists found!");
            return null;
        }

        List<Integer> listIDs = new ArrayList<>();
        for (SQLRow row : rows) {
            String value = row.getItem("id");
            int i = Integer.parseInt(value);
            listIDs.add(i);
        }
        return listIDs;
    }

    public int size() {
        logger.debug("Getting the number of rows...");

        String query = "SELECT count(*) FROM ToDoLIsts";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        if (rows == null) {
            logger.error("No todo lists found!");
            return 0;
        }

        String value = rows.get(0).getItem("count(*)");
        return Integer.parseInt(value);
    }

    public ToDoList search(String fieldName, String keyword) {
        logger.debug("Trying to find ToDo List with " + fieldName + " contains " + keyword);

        String query = "SELECT id, owner_id, description";
        query += " FROM ToDoLIsts WHERE " + fieldName + " LIKE '%" + keyword + "%'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found todo list!");
        } else {
            logger.debug("Did not find todo list.");
            return null;
        }

        SQLRow row = rows.get(0);
        ToDoList list = convertRowToToDoList(row);
        return list;
    }

    public List<ToDoList> getByOwnerID(int ownerID) {
        logger.debug("Trying to get ToDo List with member ID: " + ownerID);

        String query = "SELECT id, owner_id, description";
        query += " FROM ToDoLIsts WHERE owner_id = " + ownerID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found todo list!");
        } else {
            logger.debug("Did not find todo list.");
            return null;
        }

        List<ToDoList> lists = new ArrayList<>();
        for (SQLRow row : rows) {
            ToDoList list = convertRowToToDoList(row);
            lists.add(list);
        }
        return lists;
    }

    public void disconnect() {
        SQLUtils.disconnect(conn);
        conn = null;
    }

    // =====================================================================

    private ToDoList convertRowToToDoList(SQLRow row) {
        int listID = Integer.parseInt(row.getItem("id"));
        int owner = Integer.parseInt(row.getItem("owner_id"));
        String description = row.getItem("description");
        return new ToDoList(listID, description, owner);
    }

}