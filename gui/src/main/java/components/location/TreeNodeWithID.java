package components.location;

import lombok.Getter;
import lombok.Setter;
import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class TreeNodeWithID extends DefaultMutableTreeNode {

    private long id;

    public TreeNodeWithID(long id, Object userObject) {
        super(userObject);
        this.id = id;
    }
}
