package item;

import components.MainFrame;
import components.items.ItemsTable;
import components.items.search.ItemSearchDialog;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ItemSearcher implements ActionListener {

    private MainFrame frame;
    private JMenuItem findItemMenuItem;
    private TreeItemList tree;
    private ItemsDAO itemsDAO;
    private ItemSearchDialog searchDialog;
    private String searchingCode;
    private Map<TreeNodeWithID, Integer>  nodesOfSearchingItemsAndQuantity;

    @Autowired
    public ItemSearcher(MainFrame frame, ItemsDAO itemsDAO){
        this.frame = frame;
        findItemMenuItem = frame.getMenu().getFindItemMenuITem();
        tree = frame.getTreeItemList();
        this.itemsDAO = itemsDAO;
        searchDialog = new ItemSearchDialog(frame);
        findItemMenuItem.addActionListener(this);
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
        initNodesOfSearchingItemsAndQuantity();
        StringBuilder builder = new StringBuilder();
        nodesOfSearchingItemsAndQuantity.forEach((node, quantity) -> {
            TreeNode[] path = node.getPath();
            for(TreeNode pathNode : path){
                builder.append(pathNode.toString())
                        .append(" > ");
            }
            builder.append("ilość ")
                    .append(quantity)
                    .append(" szt.");
            searchDialog.getSearchList().getCustomListModel().addElement(builder.toString());
            builder.setLength(0);
        });
    }

    private void initNodesOfSearchingItemsAndQuantity(){
        searchingCode = searchDialog.getTextField().getText();
        List<Item> items = itemsDAO.getByCode(searchingCode);
        List<TreeNodeWithID> nodes = new ArrayList<>();
        items.forEach(x -> nodes.add(tree.getCustomTreeModel().getNodeById(x.getLocation().getId())));
        nodesOfSearchingItemsAndQuantity = new HashMap<>();
        nodes.forEach(x -> nodesOfSearchingItemsAndQuantity.merge(x, 1, Integer::sum));
    }

    private MouseListener selectSearchingItemInMainFrame() {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int selectedItem = searchDialog.getSearchList().getSelectedIndex();
                    TreeNodeWithID selectedNode = (TreeNodeWithID) nodesOfSearchingItemsAndQuantity
                            .keySet()
                            .toArray()[selectedItem];
                    tree.getNavigableTreeModel().selectNodeByID(selectedNode.getId());
                    int rowCount = frame.getItemsTable().getModel().getRowCount();
                    for(int i = 0; i < rowCount; i++) {
                        if(frame.getItemsTable().getValueAt(i, 1).equals(searchingCode)) {
                            frame.getItemsTable().changeSelection(i, 1, false, false);
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
