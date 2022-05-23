package components;

import lombok.AccessLevel;
import lombok.Getter;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter(AccessLevel.PUBLIC)
public class TreeItemList extends JTree {

    private TreeNodeWithID rootNode;
    private JScrollPane scrollPane;
    private PopupMenu popupMenu;
    private Model customModel;

    public TreeItemList() {
        rootNode = new TreeNodeWithID(0, "nakrywamy");
        customModel = new CustomTreeModel(rootNode, this);
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
                    if(customModel.getSelectedNode().isRoot()) {
                        popupMenu.getUpdateContainerMenuItem().setEnabled(false);
                        popupMenu.getDeleteContainerMenuItem().setEnabled(false);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                popupMenu.getUpdateContainerMenuItem().setEnabled(true);
                popupMenu.getDeleteContainerMenuItem().setEnabled(true);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                popupMenu.getUpdateContainerMenuItem().setEnabled(true);
                popupMenu.getDeleteContainerMenuItem().setEnabled(true);
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
        private JMenuItem addContainerMenuItem;
        private JMenuItem addArticleMenuItem;
        private JMenuItem updateContainerMenuItem;
        private JMenuItem deleteContainerMenuItem;

        PopupMenu() {
            addMenu = new JMenu("Dodaj");
            addContainerMenuItem = new JMenuItem("Lokacje");
            addArticleMenuItem = new JMenuItem("Artykuł");
            updateContainerMenuItem = new JMenuItem("Zaktualizuj");
            deleteContainerMenuItem = new JMenuItem("Usuń");
            addMenu.add(addContainerMenuItem);
            addMenu.add(addArticleMenuItem);
            add(addMenu);
            add(updateContainerMenuItem);
            add(deleteContainerMenuItem);
        }
    }
}
