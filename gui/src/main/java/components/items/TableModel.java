package components.items;

import entities.Item;

import java.util.List;

public interface TableModel {
    void addItem(Item item);
    void addAll(List<Item> itemProxies);
    void set(List<Item> itemProxies);
    void deleteRow(int row);
    void reduceQuantityOfRow(int quantity, int row);
}
