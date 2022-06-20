package dao;

import entities.Item;

import java.util.List;

public interface ItemsDAO {
    void save(Item item);
    Item get(long id);
    List<Item> getByLocationID(long id);
    void delete(Item item);
}
