package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTable;
import components.items.ItemReadUpdateDeleteTableModel;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;

@Component
public class ItemDeleter implements ActionListener {

    private TreeItemList tree;
    private ItemReadUpdateDeleteTable table;
    private ItemReadUpdateDeleteTableModel model;
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;

    @Autowired
    private ItemDeleter(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO){
        tree = mainFrame.getTreeItemList();
        table = mainFrame.getTable();
        JMenuItem deleteItemMenuItem = table.getPopupMenu().getDeleteItemMenuItem();
        model = (ItemReadUpdateDeleteTableModel) table.getModel();
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        deleteItemMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        Location location = locationDAO.get(node.getId());
        String code = (String) table.getValueAt(table.getSelectedRow(), 1);
        Item itemToDelete = location
                .getItems()
                .stream()
                .filter(x -> x.getCode().equals(code))
                .findFirst()
                .get();
        location.removeItem(itemToDelete);
        itemToDelete.setLocation(null);
        itemToDelete.setCode(null);
        locationDAO.update(location);
        itemsDAO.delete(itemToDelete);
        model.deleteRow(table.getSelectedRow());
    }
}
