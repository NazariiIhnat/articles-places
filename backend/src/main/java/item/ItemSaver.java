package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTableModel;
import components.items.ItemTable;
import components.location.ShowMessage;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.CodeDAO;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Code;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class ItemSaver {
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;
    private CodeDAO codeDAO;
    private TreeItemList treeItemList;
    private JComboBox<String> comboBox;
    private JCheckBox checkBox;
    private JTextField codeTextField;
    private JTextField quantityTextField;
    private ItemTable table;
    private Location selectedLocation;

    @Autowired
    public ItemSaver(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO, CodeDAO codeDAO) {
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        this.codeDAO = codeDAO;
        this.treeItemList = mainFrame.getTreeItemList();
        this.comboBox = mainFrame.getComboBox();
        this.checkBox = mainFrame.getCheckBox();
        this.codeTextField = mainFrame.getCodeTextField();
        this.quantityTextField = mainFrame.getQuantityTextField();
        this.table = mainFrame.getTable();
        codeTextField.addKeyListener(codeTextFieldSaveItemAction());
        quantityTextField.addKeyListener(quantityTextFieldSaveItemAction());
    }

    private KeyAdapter codeTextFieldSaveItemAction() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (comboBox.getModel().getSelectedItem().equals("Dodaj")) {
                        if (getSelectedLocation() == null)
                            ShowMessage.error("Błąd! Nie wybrano żadnej lokacji.");
                        else {
                            if (checkBox.isSelected()) {
                                if (codeTextField.getText().trim().isEmpty())
                                    ShowMessage.error("Błąd! Podany kod jest niepoprawny.");
                                else {
                                    doSave();
                                    codeTextField.setText(null);
                                }
                            } else quantityTextField.grabFocus();
                        }
                    }
                }
            }
        };
    }

    private KeyAdapter quantityTextFieldSaveItemAction() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (comboBox.getModel().getSelectedItem().equals("Dodaj")){
                        if (getSelectedLocation() == null)
                            ShowMessage.error("Błąd! Nie wybrano żadnej lokacji.");
                        else {
                            if (codeTextField.getText().trim().isEmpty())
                                ShowMessage.error("Błąd! Podany kod jest niepoprawny.");
                            else if (quantityTextField.getText().trim().isEmpty())
                                ShowMessage.error("Błąd! Podana ilość jest niepoprawna.");
                            else {
                                doSave();
                                codeTextField.setText(null);
                                quantityTextField.setText(null);
                                codeTextField.grabFocus();
                            }
                        }
                    }
                }
            }
        };
    }

    private void doSave() {
        selectedLocation = getSelectedLocation();
        String code = codeTextField.getText();
        String stringQuantity = quantityTextField.getText();
        int quantity;
        try{
            quantity = Integer.parseInt(stringQuantity);
            if(selectedLocation.hasItem(code))
                increaseQuantity(code, quantity);
            else saveItem(code, quantity);
        } catch (NumberFormatException ignore) {
        }
    }

    private Location getSelectedLocation() {
        TreeNodeWithID node = treeItemList.getCustomTreeModel().getSelectedNode();
        return node == null ? null : locationDAO.get(node.getId());
    }

    private void increaseQuantity(String code, int quantity) {
        Item item = selectedLocation
                .getItems()
                .stream()
                .filter(i -> i.getCode().equals(code))
                .findFirst()
                .get();
        ItemReadUpdateDeleteTableModel model = (ItemReadUpdateDeleteTableModel) table.getModel();
        model.increaseQuantity(code, quantity);
        item.setQuantity(item.getQuantity() + quantity);
        itemsDAO.update(item);
    }

    private void saveItem(String code, int quantity) {
        Code codeEntity = codeDAO.get(code);
        if(codeEntity == null) {
            codeEntity = new Code(code);
            codeDAO.save(codeEntity);
        }
        Item item = new Item();
        item.setCode(codeEntity);
        item.setLocation(selectedLocation);
        item.setQuantity(quantity);
        itemsDAO.save(item);
        ItemReadUpdateDeleteTableModel model = (ItemReadUpdateDeleteTableModel) table.getModel();
        model.addItem(code, quantity);
    }
}
