package location;


import components.MainFrame;
import components.UserReadableInfoSetFrame;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class UserReadableInfoUpdater extends LocationHandler implements ActionListener {

    private UserReadableInfoSetFrame userReadableInfoSetFrame;

    @Autowired
    public UserReadableInfoUpdater(MainFrame mainFrame, ImageHandler imageHandler, LocationDAO locationDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, locationDAO);
        this.userReadableInfoSetFrame = new UserReadableInfoSetFrame(mainFrame);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getUpdateLocationMenuItem()
                .addActionListener(x -> {
                    this.userReadableInfoSetFrame.setVisible(true);
                    userReadableInfoSetFrame.getTextField().setText(super.getSelectedLocation().getUserReadableInfo());
                });
        userReadableInfoSetFrame.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Location location = getSelectedLocation();
        String text = userReadableInfoSetFrame.getTextField().getText();
        location.setUserReadableInfo(text);
        super.update(location);
        userReadableInfoSetFrame.setVisible(false);
    }
}
