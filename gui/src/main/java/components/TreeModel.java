package components;

public interface TreeModel {

    void addNode(TreeNodeWithID node);
    TreeNodeWithID getSelectedNode();
    void updateNode(String text);
    void deleteSelectedNode();
}
