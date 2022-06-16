package location;

import components.UserReadableInfoSetFrame;
import components.MainFrame;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component("locationSaver")
public class Saver extends LocationHandler implements ActionListener {

    private UserReadableInfoSetFrame userReadableInfoSetFrame;

    @Autowired
    public Saver(MainFrame mainFrame, ImageHandler imageHandler, LocationDAO locationDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, locationDAO);
        this.userReadableInfoSetFrame = new UserReadableInfoSetFrame(mainFrame);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getAddLocationMenuItem()
                .addActionListener(x -> this.userReadableInfoSetFrame.setVisible(true));
        this.userReadableInfoSetFrame.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = userReadableInfoSetFrame.getTextField().getText();
        Location location = new Location();
        location.setUserReadableInfo(text);
        super.save(location);
        userReadableInfoSetFrame.setVisible(false);
    }
}
