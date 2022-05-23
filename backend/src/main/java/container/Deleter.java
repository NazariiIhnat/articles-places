package container;

import components.MainFrame;
import components.TreeItemList;
import dao.ContainerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class Deleter extends ContainersHandler implements ActionListener {

    @Autowired
    public Deleter(MainFrame mainFrame, ImageHandler imageHandler, ContainerDAO containerDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, containerDAO);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getDeleteContainerMenuItem()
                .addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        delete(getSelectedContainer().getId());
    }
}
