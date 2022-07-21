package item;

import components.MainFrame;
import components.items.search.ItemSearchDialog;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.tree.TreeNode;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemSearcher implements ActionListener {

    private MainFrame frame;
    private TreeItemList tree;
    private ItemsDAO itemsDAO;
    private ItemSearchDialog searchDialog;
    private String searchingCode;
    private List<TreeNodeWithID> nodesOfSearchingItem;

    @Autowired
    public ItemSearcher(MainFrame frame, ItemsDAO itemsDAO){
        this.frame = frame;
        tree = frame.getTreeItemList();
        this.itemsDAO = itemsDAO;
        searchDialog = new ItemSearchDialog(frame);
        frame.getMenu().getFindItemMenuITem().addActionListener(this);
        searchDialog.getTextField().addKeyListener(searchWhenEnterKeyPressed());
        searchDialog.getSearchList().addMouseListener(selectSearchingItemInMainFrame());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        searchDialog.setVisible(true);
    }

    private KeyListener searchWhenEnterKeyPressed() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchDialog.getSearchList().getCustomListModel().removeAllElements();
                    search();
                }
            }
        };
    }

    private void search() {
        searchingCode = searchDialog.getTextField().getText();
        List<Item> items = itemsDAO.getByCode(searchingCode);
        nodesOfSearchingItem = new ArrayList<>();
        items.forEach(x -> nodesOfSearchingItem.add(tree.getCustomTreeModel().getNodeById(x.getLocation().getId())));
        StringBuilder builder = new StringBuilder();
        items.forEach(item -> {
            TreeNode[] path = getNodePathForItem(item);
            for(TreeNode pathNode : path){
                builder.append(pathNode.toString())
                        .append(" > ");
            }
            builder.append("ilość ")
                    .append(item.getQuantity())
                    .append(" szt.");
            searchDialog.getSearchList().getCustomListModel().addElement(builder.toString());
            builder.setLength(0);
        });
    }

    private TreeNode[] getNodePathForItem(Item item) {
        long id = item.getLocation().getId();
        TreeNodeWithID node = tree.getCustomTreeModel().getNodeById(id);
        return node.getPath();
    }

    private MouseListener selectSearchingItemInMainFrame() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int selectedItem = searchDialog.getSearchList().getSelectedIndex();
                    TreeNodeWithID selectedNode = nodesOfSearchingItem.get(selectedItem);
                    tree.getNavigableTreeModel().selectNodeByID(selectedNode.getId());
                    int rowCount = frame.getTable().getModel().getRowCount();
                    for(int i = 0; i < rowCount; i++) {
                        if(frame.getTable().getValueAt(i, 1).equals(searchingCode)) {
                            frame.getTable().changeSelection(i, 1, false, false);
                            break;
                        }
                    }
                    searchDialog.dispose();
                    frame.toFront();
                }
            }
        };
    }
}
