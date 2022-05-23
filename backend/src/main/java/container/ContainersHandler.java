package container;

import components.TreeItemList;
import components.TreeNodeWithID;
import dao.ContainerDAO;
import entities.Container;
import utils.ImageHandler;

public class ContainersHandler {

    private TreeItemList tree;
    private ImageHandler imageHandler;
    private ContainerDAO containerDAO;

    public ContainersHandler(TreeItemList tree, ImageHandler imageHandler, ContainerDAO containerDAO) {
        this.tree = tree;
        this.imageHandler = imageHandler;
        this.containerDAO = containerDAO;
    }

    public void save(Container container) {
        container.setParentContainer(getSelectedContainer());
        containerDAO.save(container);
        imageHandler.save(container);
        TreeNodeWithID node = new TreeNodeWithID(container.getId(), container.getUserReadableInfo());
        tree.getCustomModel().addNode(node);
    }

    public Container getSelectedContainer() {
        long id = tree.getCustomModel().getSelectedNode().getId();
        return containerDAO.get(id);
    }

    public void update(Container newValue) {
        containerDAO.update(newValue);
        imageHandler.update(newValue);
        tree.getCustomModel().updateNode(newValue.getUserReadableInfo());
    }

    public void delete(long id) {
        imageHandler.delete(containerDAO.get(id));
        containerDAO.delete(id);
        tree.getCustomModel().deleteSelectedNode();
    }
}
