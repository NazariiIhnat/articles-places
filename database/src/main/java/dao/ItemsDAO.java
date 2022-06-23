package dao;

import entities.Item;

import java.util.List;

public interface ItemsDAO {
    void save(Item item);
    List<Item> getByCode(String code);
    List<Item> getByLocationID(long id);
    void delete(Item item);
}
