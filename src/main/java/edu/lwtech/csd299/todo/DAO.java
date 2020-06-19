package edu.lwtech.csd299.todo;

import java.util.*;

public interface DAO<T> {
    public boolean init(String jdbc, String user, String password, String driver);

    public int insert(T pojo);

    public boolean update(T pojo);

    public void delete(int id);

    public T getByID(int id);

    public T getByIndex(int index);

    public List<T> getAll();

    public List<Integer> getAllIDs();

    public int size();

    public T search(String fieldName, String keyword);

    public List<T> getByOwnerID(int ownerID);

    public void disconnect();
}
