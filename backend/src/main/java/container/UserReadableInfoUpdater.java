package container;


import components.MainFrame;
import components.TreeItemList;
import components.UserReadableInfoSetFrame;
import dao.ContainerDAO;
import entities.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class UserReadableInfoUpdater extends ContainersHandler implements ActionListener {

    private UserReadableInfoSetFrame userReadableInfoSetFrame;

    @Autowired
    public UserReadableInfoUpdater(MainFrame mainFrame, ImageHandler imageHandler, ContainerDAO containerDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, containerDAO);
        this.userReadableInfoSetFrame = new UserReadableInfoSetFrame(mainFrame);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getUpdateContainerMenuItem()
                .addActionListener(x -> {
                    this.userReadableInfoSetFrame.setVisible(true);
                    userReadableInfoSetFrame.getTextField().setText(super.getSelectedContainer().getUserReadableInfo());
                });
        userReadableInfoSetFrame.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Container container = getSelectedContainer();
        String text = userReadableInfoSetFrame.getTextField().getText();
        container.setUserReadableInfo(text);
        super.update(container);
        userReadableInfoSetFrame.setVisible(false);
    }
}
