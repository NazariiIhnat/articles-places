package components.location;

import lombok.AccessLevel;
import lombok.Getter;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter(AccessLevel.PUBLIC)
public class TreeItemList extends JTree {

    private TreeNodeWithID rootNode;
    private JScrollPane scrollPane;
    private PopupMenu popupMenu;
    private TreeModel customTreeModel;
    private NavigableTreeModel navigableTreeModel;

    public TreeItemList() {
        rootNode = new TreeNodeWithID(0, "nakrywamy");
        customTreeModel = new CustomTreeModel(rootNode, this);
        navigableTreeModel = new CustomNavigableTreeModel(rootNode, this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
        addMouseListener(selectItemByRMB());
        initPopupMenu();
    }

    private void initPopupMenu() {
        popupMenu = new PopupMenu();
        popupMenu.addPopupMenuListener(disableUpdateAndDeleteMenuItemIfSelectedRootNode());
        setComponentPopupMenu(popupMenu);
    }

    private PopupMenuListener disableUpdateAndDeleteMenuItemIfSelectedRootNode() {
        return new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                if(getSelectionModel().getSelectionPath() != null) {
                    if(customTreeModel.getSelectedNode().isRoot()) {
                        popupMenu.getUpdateLocationMenuItem().setEnabled(false);
                        popupMenu.getDeleteLocationMenuItem().setEnabled(false);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                popupMenu.getUpdateLocationMenuItem().setEnabled(true);
                popupMenu.getDeleteLocationMenuItem().setEnabled(true);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                popupMenu.getUpdateLocationMenuItem().setEnabled(true);
                popupMenu.getDeleteLocationMenuItem().setEnabled(true);
            }
        };
    }

    private MouseAdapter selectItemByRMB() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    int selRow = getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = getPathForLocation(e.getX(), e.getY());
                    setSelectionPath(selPath);
                    if (selRow>-1){
                        setSelectionRow(selRow);
                    }
                }
            }
        };
    }

    @Getter
    public class PopupMenu extends JPopupMenu {
        private JMenu addMenu;
        private JMenuItem addLocationMenuItem;
        private JMenuItem addItemMenuItem;
        private JMenuItem updateLocationMenuItem;
        private JMenuItem deleteLocationMenuItem;

        PopupMenu() {
            addMenu = new JMenu("Dodaj");
            addLocationMenuItem = new JMenuItem("Lokacje");
            addItemMenuItem = new JMenuItem("Towar");
            updateLocationMenuItem = new JMenuItem("Zaktualizuj");
            deleteLocationMenuItem = new JMenuItem("Usuń");
            addMenu.add(addLocationMenuItem);
            addMenu.add(addItemMenuItem);
            add(addMenu);
            add(updateLocationMenuItem);
            add(deleteLocationMenuItem);
        }
    }
}
