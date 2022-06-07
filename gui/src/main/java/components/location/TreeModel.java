package components.location;

public interface TreeModel {

    void addNode(TreeNodeWithID node);
    TreeNodeWithID getSelectedNode();
    void updateNode(String text);
    void deleteSelectedNode();
}
