package edu.lwtech.csd299.todo;

import java.sql.*;
import java.util.*;

import org.apache.log4j.Logger;

public class ItemSqlDAO implements DAO<Item> {
    private static final Logger logger = Logger.getLogger(ItemSqlDAO.class.getName());
    private Connection conn = null;

    public ItemSqlDAO() {
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

    public int insert(Item item) {
        logger.debug("Inserting " + item + "...");

        if (item.getID() != -1) {
            logger.error("Attempting to add previously added item: " + item);
            return -1;
        }

        String query = "INSERT INTO Items";
        query += " (list_id, name, completed)";
        query += " VALUES (?,?,?)";

        String completed = item.isCompleted() ? "1" : "0";

        int itemID = SQLUtils.executeSQLInsert(conn, query, "id", "" + item.getListID(), item.getName(), completed);

        logger.debug("Item successfully inserted with id = " + itemID);
        return itemID;
    }

    public boolean update(Item item) {
        logger.debug("Updating item with id = " + item.getID());

        String completed = item.isCompleted() ? "1" : "0";

        String query = "UPDATE Items " + "SET list_id='" + item.getListID() + "', name = '" + item.getName()
                + "', completed = '" + completed + "' " + "WHERE id='" + item.getID() + "'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        return (rows != null);
    }

    public void delete(int itemID) {
        logger.debug("Trying to delete item with id: " + itemID);

        String query = "DELETE FROM Items WHERE id=" + itemID;
        SQLUtils.executeSQL(conn, query);
    }

    public Item getByID(int itemID) {
        logger.debug("Trying to get Item with ID: " + itemID);

        String query = "SELECT id, list_id, name, completed";
        query += " FROM Items WHERE id = " + itemID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found item!");
        } else {
            logger.debug("Did not find item.");
            return null;
        }

        SQLRow row = rows.get(0);
        Item item = convertRowToItem(row);
        return item;
    }

    public Item getByIndex(int index) {
        logger.debug("Trying to get item with index: " + index);

        index++; // SQL uses 1-based indexes

        if (index < 1)
            return null;

        String query = "SELECT id, list_id, name, completed";
        query += " FROM Items ORDER BY id LIMIT " + index;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("Did not find item.");
            return null;
        }

        SQLRow row = rows.get(rows.size() - 1);
        Item item = convertRowToItem(row);
        return item;
    }

    public List<Item> getAll() {
        logger.debug("Getting all items...");

        String query = "SELECT id, list_id, name, completed";
        query += " FROM Items ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No items found!");
            return null;
        }

        List<Item> items = new ArrayList<>();
        for (SQLRow row : rows) {
            Item item = convertRowToItem(row);
            items.add(item);
        }
        return items;
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting Item IDs...");

        String query = "SELECT id FROM Items ORDER BY id";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows == null) {
            logger.debug("No items found!");
            return null;
        }

        List<Integer> itemIDs = new ArrayList<>();
        for (SQLRow row : rows) {
            String value = row.getItem("id");
            int i = Integer.parseInt(value);
            itemIDs.add(i);
        }
        return itemIDs;
    }

    public int size() {
        logger.debug("Getting the number of rows...");

        String query = "SELECT count(*) FROM Items";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);
        if (rows == null) {
            logger.error("No items found!");
            return 0;
        }

        String value = rows.get(0).getItem();
        return Integer.parseInt(value);
    }

    public Item search(String fieldName, String keyword) {
        logger.debug("Trying to find Item with " + fieldName + " contains " + keyword);

        String query = "SELECT id, list_id, name, completed";
        query += " FROM Items WHERE " + fieldName + " LIKE '%" + keyword + "%'";

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found item!");
        } else {
            logger.debug("Did not find item.");
            return null;
        }

        SQLRow row = rows.get(0);
        Item item = convertRowToItem(row);
        return item;
    }

    public List<Item> getByOwnerID(int listID) {
        logger.debug("Trying to get Items with list ID: " + listID);

        String query = "SELECT id, list_id, name, completed";
        query += " FROM Items WHERE list_id = " + listID;

        List<SQLRow> rows = SQLUtils.executeSQL(conn, query);

        if (rows != null) {
            logger.debug("Found items!");
        } else {
            logger.debug("Did not find items.");
            return null;
        }

        List<Item> items = new ArrayList<>();
        for (SQLRow row : rows) {
            Item item = convertRowToItem(row);
            items.add(item);
        }
        return items;
    }

    public void disconnect() {
        SQLUtils.disconnect(conn);
        conn = null;
    }

    // =====================================================================

    private Item convertRowToItem(SQLRow row) {
        int itemID = Integer.parseInt(row.getItem("id"));
        int listID = Integer.parseInt(row.getItem("list_id"));
        String name = row.getItem("name");
        boolean completed = row.getItem("completed").equals("1") ? true : false;
        return new Item(itemID, name, completed, listID);
    }

}