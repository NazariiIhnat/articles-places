package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTable;
import components.items.ItemReadUpdateDeleteTableModel;
import components.items.SpinnerDialog;
import components.location.ShowMessage;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.*;

@Component
public class ItemQuantityReducer implements ActionListener {

    private MainFrame frame;
    private ItemReadUpdateDeleteTable table;
    private ItemReadUpdateDeleteTableModel model;
    private TreeItemList tree;
    private LocationDAO locationDAO;
    private ItemsDAO itemsDAO;
    private SpinnerDialog spinnerDialog;

    @Autowired
    public ItemQuantityReducer(MainFrame frame, LocationDAO locationDAO, ItemsDAO itemsDAO) {
        this.frame = frame;
        this.table = frame.getTable();
        this.model = (ItemReadUpdateDeleteTableModel) table.getModel();
        tree = frame.getTreeItemList();
        this.locationDAO = locationDAO;
        this.itemsDAO = itemsDAO;
        spinnerDialog = new SpinnerDialog(frame);
        table.getPopupMenu().getReduceItemQuantityMenuItem().addActionListener(this);
        spinnerDialog.getOkButton().addActionListener(reduceQuantity());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int quantity = (int) table.getValueAt(table.getSelectedRow(), 2);
        spinnerDialog.getSpinnerNumberModel().setMaximum(quantity);
        spinnerDialog.getSpinnerNumberModel().setValue(spinnerDialog.getSpinnerNumberModel().getMinimum());
        spinnerDialog.setVisible(true);
        spinnerDialog.getOwner().setEnabled(false);
    }

    private ActionListener reduceQuantity(){
        return x -> {
            int quantity = (int) table.getValueAt(table.getSelectedRow(), 2);
            int reduceValue = (int) spinnerDialog.getSpinnerNumberModel().getValue();
            updateQuantityInDatabase(reduceValue);
            if(quantity == reduceValue){
                model.deleteRow(table.getSelectedRow());
                spinnerDialog.setVisible(false);
                frame.setEnabled(true);
                frame.toFront();
            } else if (quantity < reduceValue) {
                ShowMessage.error("Podana ilość ("
                                + reduceValue +
                                ") jest większa od ilości towaru na stnie ("
                                + quantity
                                + ")");
            } else {
                model.reduceQuantityOfRow(reduceValue, table.getSelectedRow());
                spinnerDialog.setVisible(false);
                frame.setEnabled(true);
                frame.toFront();
            }
        };
    }

    private void updateQuantityInDatabase(int reduceValue){
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        Location location = locationDAO.get(node.getId());
        String code = (String) table.getValueAt(table.getSelectedRow(), 1);
        Item item = location
                .getItems()
                .stream()
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .get();
        if (item.getQuantity() == reduceValue) {
            location.removeItem(item);
            item.setLocation(null);
            itemsDAO.delete(item);
            locationDAO.update(location);
        } else {
            item.setQuantity(item.getQuantity() - reduceValue);
            itemsDAO.update(item);
        }
    }
}
