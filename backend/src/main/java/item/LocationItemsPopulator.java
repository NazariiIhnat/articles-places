package item;

import components.MainFrame;
import components.items.ItemsTable;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LocationItemsPopulator implements TreeSelectionListener{

    private TreeItemList tree;
    private ItemsTable table;
    private ItemsDAO itemsDAO;

    @Autowired
    public LocationItemsPopulator(MainFrame frame, ItemsDAO itemsDAO) {
        tree = frame.getTreeItemList();
        table = frame.getItemsTable();
        this.itemsDAO = itemsDAO;
        tree.addTreeSelectionListener(this);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        long id = node.getId();
        List<Item> items = itemsDAO.getByLocationID(id);
        if(items != null)
            table.getCustomModel().set(items);
    }
}
