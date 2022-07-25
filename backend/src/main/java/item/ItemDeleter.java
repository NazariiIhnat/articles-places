package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTableModel;
import components.items.ItemTableModel;
import components.items.TableModel;
import components.location.ShowMessage;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Code;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;

@Component
public class ItemDeleter {

    private TreeItemList tree;
    private JTable table;
    private TableModel model;
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;
    private JComboBox<String> comboBox;
    private JCheckBox checkBox;
    private JTextField codeTextField;
    private JTextField quantityTextField;
    private Location selectedLocation;

    @Autowired
    private ItemDeleter(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO){
        this.tree = mainFrame.getTreeItemList();
        this.table = mainFrame.getTable();
        this.model = (TableModel) table.getModel();
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        this.comboBox = mainFrame.getComboBox();
        this.checkBox = mainFrame.getCheckBox();
        this.codeTextField = mainFrame.getCodeTextField();
        this.quantityTextField = mainFrame.getQuantityTextField();
        codeTextField.addKeyListener(codeTextFieldDeleteItemAction());
        quantityTextField.addKeyListener(quantityTextFieldDeleteItemAction());
    }

    private KeyAdapter codeTextFieldDeleteItemAction() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(comboBox.getModel().getSelectedItem().equals("Wyjmij")) {
                        if(getSelectedLocation() == null)
                            ShowMessage.error("Błąd! Nie wybrano żadnej lokacji.");
                        else {
                            selectedLocation = getSelectedLocation();
                            if (checkBox.isSelected()) {
                                if(isCorrectInput()){
                                    if(isExistingItemInLocation()){
                                        doDelete();
                                        codeTextField.setText(null);
                                    }
                                }
                            } else {
                                quantityTextField.grabFocus();
                            }
                        }
                    }
                }
            }
        };
    }

    private KeyAdapter quantityTextFieldDeleteItemAction() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(comboBox.getModel().getSelectedItem().equals("Wyjmij")) {
                        if (getSelectedLocation() == null)
                            ShowMessage.error("Błąd! Nie wybrano żadnej lokacji.");
                        else {
                            selectedLocation = getSelectedLocation();
                            if(isCorrectInput()){
                                if(isExistingItemInLocation()){
                                    if(inputQuantityIsLowerOrEqualsThanActual()){
                                        doDelete();
                                        codeTextField.setText(null);
                                        quantityTextField.setText(null);
                                        codeTextField.grabFocus();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private boolean isCorrectInput() {
        if (codeTextField.getText().trim().isEmpty()) {
            ShowMessage.error("Błąd! Podany kod jest niepoprawny.");
            return false;
        }
        else if (quantityTextField.getText().trim().isEmpty()) {
            ShowMessage.error("Błąd! Podana ilość jest niepoprawna.");
            return false;
        } else return true;
    }

    private boolean isExistingItemInLocation() {
        Item item = getItem();
        if(item == null){
            ShowMessage.error("Błąd! Nie znaleziono tego produktu.");
            return false;
        } else return true;
    }

    private boolean inputQuantityIsLowerOrEqualsThanActual(){
        if(getItem().getQuantity() < Integer.parseInt(quantityTextField.getText())){
            ShowMessage.error("Błąd! Podana ilość jest większa od stanu.");
            return false;
        } else return true;
    }

    private void doDelete() {
        String code = codeTextField.getText();
        String stringQuantity = quantityTextField.getText();
        int quantity = Integer.parseInt(stringQuantity);
        if(!code.isEmpty() && quantity != 0){
            System.out.println(selectedLocation);
            if(selectedLocation.hasItem(code))
                reduceQuantity(code, quantity);
        }
    }

    private Location getSelectedLocation() {
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        return node == null ? null : locationDAO.get(node.getId());
    }

    private void reduceQuantity(String code, int quantity){
        Item item = getItem();
        TableModel model = (TableModel) table.getModel();
        if (item.getQuantity() > quantity) {
            model.reduceQuantity(code, quantity);
            item.setQuantity(item.getQuantity() - quantity);
            itemsDAO.update(item);
        } else {
            deleteItem(code);
        }
    }

    private Item getItem() {
        Optional<Item> optional =  selectedLocation
                .getItems()
                .stream()
                .filter(i -> i.getCode().equals(codeTextField.getText()))
                .findFirst();
        return optional.orElse(null);
    }

    private void deleteItem(String code) {
        Item item = getItem();
        model.deleteItem(code);
        getSelectedLocation().removeItem(item);
        item.setLocation(null);
        locationDAO.update(selectedLocation);
        itemsDAO.delete(item);
    }
}
