package components.items;

import entities.Item;
import java.util.List;

public interface TableModel {
    void addItem(String code, int quantity);
    void increaseQuantity(String code, int value);
    void deleteItem(String code);
    void reduceQuantity(String code, int value);
    void set(List<Item> items);
}
