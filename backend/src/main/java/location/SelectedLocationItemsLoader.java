package location;

import components.MainFrame;
import components.items.ItemsTable;
import components.location.TreeItemList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectedLocationItemsLoader {
    private ItemsTable table;
    private TreeItemList tree;

    @Autowired
    public SelectedLocationItemsLoader(MainFrame mainFrame){
        this.table = mainFrame.getItemsTable();
        this.tree = mainFrame.getTreeItemList();
    }
}
