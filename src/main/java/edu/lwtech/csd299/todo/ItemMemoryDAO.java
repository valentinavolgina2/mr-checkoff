package edu.lwtech.csd299.todo;

import java.util.*;

import org.apache.log4j.Logger;

public class ItemMemoryDAO implements DAO<Item> {
    private static final Logger logger = Logger.getLogger(ItemMemoryDAO.class.getName());

    private int nextID;
    private List<Item> memoryDB;

    public ItemMemoryDAO() {
        this.nextID = 1000;
        this.memoryDB = new ArrayList<>();
    }

    public boolean init() {
        return true;
    }

    public int insert(Item item) {
        logger.debug("Inserting " + item + "...");

        if (item.getID() != -1) {
            logger.error("Attempting to add previously added item: " + item);
            return -1;
        }

        item = new Item(generateNextItemID(), item);
        memoryDB.add(item);

        logger.debug("Item was successfully inserted!");
        return item.getID();
    }

    public void delete(int id) {
        logger.debug("Trying to delete item with ID: " + id);

        Item itemFound = null;
        for (Item item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }

        if (itemFound != null) {
            memoryDB.remove(itemFound);
            logger.debug("Item was successfully deleted!");
        } else {
            logger.debug("Item with ID: " + id + " wasn't found!");
        }

    }

    public boolean update(Item item) {

        int id = item.getID();
        logger.debug("Trying to update item with ID: " + id);

        Item itemFound = null;
        for (Item oldItem : memoryDB) {
            if (oldItem.getID() == id) {
                itemFound = oldItem;
                break;
            }
        }

        if (itemFound != null) {
            memoryDB.remove(itemFound);
            memoryDB.add(new Item(id, item));
            logger.debug("Item was successfully updated!");
            return true;
        } else {
            logger.debug("Item wasn't found!");
            return false;
        }

    }

    public Item getByID(int id) {
        logger.debug("Trying to get item with ID: " + id);

        Item itemFound = null;
        for (Item item : memoryDB) {
            if (item.getID() == id) {
                itemFound = item;
                break;
            }
        }

        if (itemFound != null) {
            logger.debug("Item with ID: " + id + " was found!");
        } else {
            logger.debug("Item with ID: " + id + " was not found!");
        }
        return itemFound;
    }

    public Item getByIndex(int index) {
        // Note: indexes are zero-based
        logger.debug("Trying to get item with index: " + index);

        if (index < 0 || index > memoryDB.size()) {
            logger.debug("Item with index: " + index + " was not found!");
            return null;
        } else {
            logger.debug("Item with index: " + index + " was found!");
            return memoryDB.get(index);
        }

    }

    public List<Item> getAll() {
        logger.debug("Getting all items");
        return new ArrayList<>(memoryDB);
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting item IDs...");

        List<Integer> itemIDs = new ArrayList<>();
        for (Item item : memoryDB) {
            itemIDs.add(item.getID());
        }
        return itemIDs;
    }

    public int size() {
        logger.debug("Getting the size of Item database...");
        return memoryDB.size();
    }

    public Item search(String fieldName, String keyword) {
        logger.debug("Trying to get item with " + fieldName + " equals " + keyword);

        Item itemFound = null;
        for (Item item : memoryDB) {
            if (fieldName.equals("name") && item.getName().toLowerCase().contains(keyword.toLowerCase())) {
                itemFound = item;
                break;
            }
        }

        if (itemFound != null) {
            logger.debug("Item with " + fieldName + " equals " + keyword + " was found!");
        } else {
            logger.debug("Item with " + fieldName + " equals " + keyword + " was not found!");
        }
        return itemFound;

    }

    public List<Item> getByOwnerID(int listID) {
        logger.debug("Trying to get items with owner ID: " + listID);

        List<Item> listItems = new ArrayList<>();
        for (Item item : memoryDB) {
            if (item.getListID() == listID) {
                listItems.add(item);
            }
        }

        return listItems;
    }

    public void disconnect() {
        this.memoryDB = null;
    }

    // =================================================================

    public synchronized int generateNextItemID() {
        return nextID++;
    }

}