package components.items;

import entities.Item;

import java.util.List;

public interface TableModel {
    void addItem(Item item);
    void addAll(List<Item> items);
    void set(List<Item> items);
    void deleteSelectedRow();
}
