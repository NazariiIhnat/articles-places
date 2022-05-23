package components;

public interface Model {

    void addNode(TreeNodeWithID node);
    TreeNodeWithID getSelectedNode();
    void updateNode(String text);
    void deleteSelectedNode();
}
