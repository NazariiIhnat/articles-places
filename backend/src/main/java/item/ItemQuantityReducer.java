package item;

import components.MainFrame;
import components.items.ItemsTable;
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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemQuantityReducer implements ActionListener {

    private MainFrame frame;
    private ItemsTable table;
    private TreeItemList tree;
    private LocationDAO locationDAO;
    private ItemsDAO itemsDAO;
    private SpinnerDialog spinnerDialog;

    @Autowired
    public ItemQuantityReducer(MainFrame frame, LocationDAO locationDAO, ItemsDAO itemsDAO) {
        this.frame = frame;
        table = frame.getItemsTable();
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
                table.getCustomModel().deleteSelectedRow();
            } else {
                table.getCustomModel().reduceQuantityOfSelectedRow(reduceValue);
            }
            spinnerDialog.setVisible(false);
            frame.setEnabled(true);
            frame.toFront();
        };
    }

    private void updateQuantityInDatabase(int reduceValue){
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        Location location = locationDAO.get(node.getId());
        String code = (String) table.getValueAt(table.getSelectedRow(), 1);
        List<Item> itemsToDelete = location
                .getItems()
                .stream()
                .filter(x -> x.getCode().equals(code))
                .collect(Collectors.toList())
                .subList(0, reduceValue);
        itemsToDelete.forEach(x -> {
            location.removeItem(x);
            x.setLocation(null);
        });
        locationDAO.update(location);
        itemsToDelete.forEach(itemsDAO::delete);
    }
}
