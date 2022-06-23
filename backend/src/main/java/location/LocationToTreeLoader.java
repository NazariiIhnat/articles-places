package location;

import components.MainFrame;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationToTreeLoader {

    private List<Location> list;
    private TreeItemList tree;

    @Autowired
    public LocationToTreeLoader(MainFrame mainFrame, LocationDAO locationDAO) {
        tree = mainFrame.getTreeItemList();
        list = locationDAO.getAll();
        populateTree(getRoots(), tree.getRootNode());
    }

    private void populateTree(List<Location> list, TreeNodeWithID rootNode) {
        if(list != null) {
            list.forEach(container -> {
                TreeNodeWithID node = new TreeNodeWithID(container.getId(), container.getUserReadableInfo());
                rootNode.add(node);
                tree.getCustomTreeModel().addNode(node);
                if(container.hasChildLocation())
                    populateTree(container.getChildLocations(), node);
            });
        }
    }

    private List<Location> getRoots() {
        return list
                .stream()
                .filter(x -> x.getParentLocation() == null)
                .collect(Collectors.toList());
    }
}
