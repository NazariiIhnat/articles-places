package item;

import components.MainFrame;
import components.items.ItemsTable;
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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDeleter implements ActionListener {

    private TreeItemList tree;
    private ItemsTable table;
    private JMenuItem deleteItemMenuItem;
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;

    @Autowired
    private ItemDeleter(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO){
        tree = mainFrame.getTreeItemList();
        table = mainFrame.getItemsTable();
        deleteItemMenuItem = table.getPopupMenu().getDeleteItemMenuItem();
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        deleteItemMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        Location location = locationDAO.get(node.getId());
        String code = (String) table.getValueAt(table.getSelectedRow(), 1);
        List<Item> itemsToDelete = location
                .getItems()
                .stream()
                .filter(x -> x.getCode().equals(code))
                .collect(Collectors.toList());
        itemsToDelete.forEach(x -> {
            location.removeItem(x);
            x.setLocation(null);
        });
        locationDAO.update(location);
        itemsToDelete.forEach(itemsDAO::delete);
        table.getCustomModel().deleteSelectedRow();
    }
}
