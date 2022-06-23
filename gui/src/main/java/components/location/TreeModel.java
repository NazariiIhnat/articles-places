package components.location;

public interface TreeModel {

    void addNode(TreeNodeWithID node);
    TreeNodeWithID getSelectedNode();
    TreeNodeWithID getNodeById(long id);
    void updateNode(String text);
    void deleteSelectedNode();
}
