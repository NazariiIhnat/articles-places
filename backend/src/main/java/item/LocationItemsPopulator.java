package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTable;
import components.items.ItemReadUpdateDeleteTableModel;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.util.List;

@Component
public class LocationItemsPopulator implements TreeSelectionListener{

    private TreeItemList tree;
    private ItemReadUpdateDeleteTable table;
    private ItemReadUpdateDeleteTableModel model;
    private ItemsDAO itemsDAO;

    @Autowired
    public LocationItemsPopulator(MainFrame frame, ItemsDAO itemsDAO) {
        tree = frame.getTreeItemList();
        table = frame.getTable();
        model = (ItemReadUpdateDeleteTableModel) table.getModel();
        this.itemsDAO = itemsDAO;
        tree.addTreeSelectionListener(this);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreeNodeWithID node = tree.getCustomTreeModel().getSelectedNode();
        if(node != null) {
            long id = node.getId();
            List<Item> items = itemsDAO.getByLocationID(id);
            if (items != null)
                model.set(items);
        }
    }
}
