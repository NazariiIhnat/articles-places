package components.location;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Enumeration;

public class CustomNavigableTreeModel extends DefaultTreeModel implements NavigableTreeModel {

    private TreeItemList tree;

    public CustomNavigableTreeModel(TreeNode root, TreeItemList tree) {
        super(root);
        this.tree = tree;
    }

    @Override
    public void selectNodeByID(long id) throws NullPointerException{
        TreeNodeWithID node = findNode(id);
        selectNode(node);

    }

    private TreeNodeWithID findNode(long id) {
        Enumeration enumeration = tree.getRootNode().postorderEnumeration();
        while (enumeration.hasMoreElements()) {
            TreeNodeWithID node = (TreeNodeWithID) enumeration.nextElement();
            if(node.getId() == id)
                return node;
        }
        return null;
    }

    private void selectNode(TreeNodeWithID node) {
        TreePath path = new TreePath(node.getPath());
        tree.getSelectionModel().setSelectionPath(path);
        tree.scrollPathToVisible(path);
    }
}
