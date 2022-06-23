package components.location;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.HashMap;
import java.util.Map;

public class CustomTreeModel extends DefaultTreeModel implements TreeModel {

    private JTree tree;
    private Map<Long, TreeNodeWithID> nodeMap;

    public CustomTreeModel(TreeNodeWithID root, JTree tree) {
        super(root);
        nodeMap = new HashMap<>();
        this.tree = tree;
        this.tree.setModel(this);
    }

    public void addNode(TreeNodeWithID node) {
        nodeMap.put(node.getId(), node);
        if(getSelectedNode() != null)
            insertNodeInto(node, getSelectedNode(), getSelectedNode().getChildCount());
    }

    @Override
    public TreeNodeWithID getSelectedNode() {
        return (TreeNodeWithID) tree.getLastSelectedPathComponent();
    }

    @Override
    public TreeNodeWithID getNodeById(long id) {
        return nodeMap.get(id);
    }

    @Override
    public void updateNode(String text) {
        TreeNodeWithID node = getSelectedNode();
        node.setUserObject(text);
        nodeMap.put(node.getId(), node);
        nodeChanged(node);
    }

    @Override
    public void deleteSelectedNode() {
        TreeNodeWithID node = getSelectedNode();
        nodeMap.remove(node.getId());
        removeNodeFromParent(node);
    }
}
