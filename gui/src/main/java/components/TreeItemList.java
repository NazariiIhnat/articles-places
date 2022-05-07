package components;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

@Getter
@Component
public class TreeItemList extends JTree {

    private DefaultMutableTreeNode rootNode;
    private JScrollPane scrollPane;

    public TreeItemList() {
        rootNode = new DefaultMutableTreeNode("root");
        super.setModel(new DefaultTreeModel(rootNode));
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
        setRootVisible(false);
    }
}
