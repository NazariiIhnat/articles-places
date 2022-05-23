package container;

import components.UserReadableInfoSetFrame;
import components.MainFrame;
import dao.ContainerDAO;
import entities.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class Saver extends ContainersHandler implements ActionListener {

    private UserReadableInfoSetFrame userReadableInfoSetFrame;

    @Autowired
    public Saver(MainFrame mainFrame, ImageHandler imageHandler, ContainerDAO containerDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, containerDAO);
        this.userReadableInfoSetFrame = new UserReadableInfoSetFrame(mainFrame);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getAddContainerMenuItem()
                .addActionListener(x -> this.userReadableInfoSetFrame.setVisible(true));
        this.userReadableInfoSetFrame.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = userReadableInfoSetFrame.getTextField().getText();
        Container container = new Container();
        container.setUserReadableInfo(text);
        super.save(container);
        userReadableInfoSetFrame.setVisible(false);
    }
}
