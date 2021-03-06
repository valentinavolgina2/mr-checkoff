package edu.lwtech.csd299.todo;

import java.util.*;

import org.apache.log4j.Logger;

public class MemberMemoryDAO implements DAO<Member> {

    private static final Logger logger = Logger.getLogger(MemberMemoryDAO.class.getName());

    private int nextID;
    private List<Member> memoryDB;

    public MemberMemoryDAO() {
        this.nextID = 1;
        this.memoryDB = new ArrayList<>();
        // addSampleData();
    }

    public boolean init(String jdbc, String user, String password, String driver) {
        addDemoMemberData();
        return true;
    }

    public int insert(Member member) {
        logger.debug("Inserting " + member + "...");

        if (member.getId() != -1) {
            logger.error("Attempting to add previously added member: " + member);
            return -1;
        }

        member = new Member(generateNextItemID(), member);
        memoryDB.add(member);

        logger.debug("Member successfully inserted!");
        return member.getId();
    }

    public void delete(int id) {
        logger.debug("Trying to delete member with ID: " + id);

        Member memberFound = null;
        for (Member member : memoryDB) {
            if (member.getId() == id) {
                memberFound = member;
                break;
            }
        }

        if (memberFound != null) {
            memoryDB.remove(memberFound);
            logger.debug("Member successfully deleted!");
        } else {
            logger.debug("Member with ID: " + id + " wasn't found!");
        }

    }

    public boolean update(Member member) {

        int id = member.getId();
        logger.debug("Trying to update member with ID: " + id);

        Member memberFound = null;
        int index = -1;
        for (Member oldMember : memoryDB) {
            index++;
            if (oldMember.getId() == id) {
                memberFound = oldMember;
                break;
            }
        }

        if (memberFound != null) {
            memoryDB.remove(memberFound);
            memoryDB.add(index, new Member(id, member));
            logger.debug("Member successfully updated!");
            return true;
        } else {
            logger.debug("Member wasn't found!");
            return false;
        }

    }

    public Member getByID(int id) {
        logger.debug("Trying to get member with ID: " + id);

        Member memberFound = null;
        for (Member member : memoryDB) {
            if (member.getId() == id) {
                memberFound = member;
                break;
            }
        }

        if (memberFound != null) {
            logger.debug("Member with ID: " + id + " was found!");
        } else {
            logger.debug("Member with ID: " + id + " was not found!");
        }
        return memberFound;
    }

    public Member getByIndex(int index) {
        // Note: indexes are zero-based
        logger.debug("Trying to get member with index: " + index);

        if (index < 0 || index > memoryDB.size()) {
            logger.debug("Member with index: " + index + " was not found!");
            return null;
        } else {
            logger.debug("Member with index: " + index + " was found!");
            return memoryDB.get(index);
        }

    }

    public List<Member> getAll() {
        logger.debug("Getting all members");
        return new ArrayList<>(memoryDB);
    }

    public List<Integer> getAllIDs() {
        logger.debug("Getting Member IDs...");

        List<Integer> memberIDs = new ArrayList<>();
        for (Member member : memoryDB) {
            memberIDs.add(member.getId());
        }
        return memberIDs;
    }

    public int size() {
        logger.debug("Getting the size of Member datbase...");
        return memoryDB.size();
    }

    public Member search(String fieldName, String keyword) {
        logger.debug("Trying to get member with " + fieldName + " equals " + keyword);

        Member memberFound = null;
        for (Member member : memoryDB) {
            if (fieldName.equals("username") && member.getUsername().equals(keyword)) {
                memberFound = member;
                break;
            }
            if (fieldName.equals("email") && member.getEmail().equals(keyword)) {
                memberFound = member;
                break;
            }
        }

        if (memberFound != null) {
            logger.debug("Member with " + fieldName + " equals " + keyword + " was found!");
        } else {
            logger.debug("Member with " + fieldName + " equals " + keyword + " was not found!");
        }
        return memberFound;

    }

    public List<Member> getByOwnerID(int ownerID) {
        return null;
    }

    public void disconnect() {
        this.memoryDB = null;
    }

    // =================================================================

    public synchronized int generateNextItemID() {
        return nextID++;
    }

    private void addDemoMemberData() {
        logger.debug("Creating demo Members...");

        insert(new Member("FredLwtech", "111", "Fred", "", "fred@lwtech.edu"));
        insert(new Member("MaryLwtech", "333", "Mary", "", "mary@lwtech.edu"));

        logger.info(size() + " members inserted");
    }

}