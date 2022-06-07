package location;

import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.LocationDAO;
import entities.Location;
import utils.ImageHandler;

public class LocationHandler {

    private TreeItemList tree;
    private ImageHandler imageHandler;
    private LocationDAO locationDAO;

    public LocationHandler(TreeItemList tree, ImageHandler imageHandler, LocationDAO locationDAO) {
        this.tree = tree;
        this.imageHandler = imageHandler;
        this.locationDAO = locationDAO;
    }

    public void save(Location location) {
        location.setParentLocation(getSelectedLocation());
        locationDAO.save(location);
        imageHandler.save(location);
        TreeNodeWithID node = new TreeNodeWithID(location.getId(), location.getUserReadableInfo());
        tree.getCustomTreeModel().addNode(node);
    }

    public Location getSelectedLocation() {
        long id = tree.getCustomTreeModel().getSelectedNode().getId();
        return locationDAO.get(id);
    }

    public void update(Location newValue) {
        locationDAO.update(newValue);
        imageHandler.update(newValue);
        tree.getCustomTreeModel().updateNode(newValue.getUserReadableInfo());
    }

    public void delete(long id) {
        imageHandler.delete(locationDAO.get(id));
        locationDAO.delete(id);
        tree.getCustomTreeModel().deleteSelectedNode();
    }
}
