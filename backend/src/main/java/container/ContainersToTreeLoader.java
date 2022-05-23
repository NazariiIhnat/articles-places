package container;

import components.MainFrame;
import components.TreeItemList;
import components.TreeNodeWithID;
import dao.ContainerDAO;
import entities.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContainersToTreeLoader {

    private List<Container> list;

    @Autowired
    public ContainersToTreeLoader(MainFrame mainFrame, ContainerDAO containerDAO) {
        TreeItemList tree = mainFrame.getTreeItemList();
        list = containerDAO.getAll();
        populateTree(getRoots(), tree.getRootNode());
    }

    private void populateTree(List<Container> list, TreeNodeWithID rootNode) {
        if(list != null) {
            list.forEach(container -> {
                TreeNodeWithID node = new TreeNodeWithID(container.getId(), container.getUserReadableInfo());
                rootNode.add(node);
                if(container.hasChildContainer())
                    populateTree(container.getChildContainers(), node);
            });
        }
    }

    private List<Container> getRoots() {
        return list
                .stream()
                .filter(x -> x.getParentContainer() == null)
                .collect(Collectors.toList());
    }
}
