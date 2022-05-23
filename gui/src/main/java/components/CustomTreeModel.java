package components;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

public class CustomTreeModel extends DefaultTreeModel implements Model{

    private JTree tree;

    public CustomTreeModel(TreeNodeWithID root, JTree tree) {
        super(root);
        this.tree = tree;
        this.tree.setModel(this);
    }

    public void addNode(TreeNodeWithID node) {
        insertNodeInto(node, getSelectedNode(), getSelectedNode().getChildCount());
    }

    @Override
    public TreeNodeWithID getSelectedNode() {
        return (TreeNodeWithID) tree.getLastSelectedPathComponent();
    }

    @Override
    public void updateNode(String text) {
        TreeNodeWithID node = getSelectedNode();
        node.setUserObject(text);
        nodeChanged(node);
    }

    @Override
    public void deleteSelectedNode() {
        removeNodeFromParent(getSelectedNode());
    }
}
