package components.items.search;

import components.location.TreeNodeWithID;

import javax.swing.*;
import javax.swing.tree.TreePath;

public class CustomListModel extends DefaultListModel<String> {

    public void addElement(TreeNodeWithID node) {
        TreePath path = new TreePath(node);
        for(int i = 0; i < path.getPath().length; i++)
            System.out.println(path.getPath()[i].toString());
    }
}
