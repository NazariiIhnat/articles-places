package location;


import components.MainFrame;
import components.UserReadableInfoSetDialog;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class UserReadableInfoUpdater extends LocationHandler implements ActionListener {

    private UserReadableInfoSetDialog userReadableInfoSetDialog;

    @Autowired
    public UserReadableInfoUpdater(MainFrame mainFrame, ImageHandler imageHandler, LocationDAO locationDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, locationDAO);
        this.userReadableInfoSetDialog = new UserReadableInfoSetDialog(mainFrame);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getUpdateLocationMenuItem()
                .addActionListener(x -> {
                    this.userReadableInfoSetDialog.setVisible(true);
                    userReadableInfoSetDialog.getTextField().setText(super.getSelectedLocation().getUserReadableInfo());
                });
        userReadableInfoSetDialog.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Location location = getSelectedLocation();
        String text = userReadableInfoSetDialog.getTextField().getText();
        location.setUserReadableInfo(text);
        super.update(location);
        userReadableInfoSetDialog.dispose();
    }
}
