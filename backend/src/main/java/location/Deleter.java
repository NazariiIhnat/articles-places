package location;

import components.MainFrame;
import components.location.ShowMessage;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class Deleter extends LocationHandler implements ActionListener {

    @Autowired
    public Deleter(MainFrame mainFrame, ImageHandler imageHandler, LocationDAO locationDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, locationDAO);
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getDeleteLocationMenuItem()
                .addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Location location = getSelectedLocation();
        if(location.hasChildLocations() | location.hasItems())
            ShowMessage
                    .error("Błąd! Locacja \"" + location.getUserReadableInfo() + "\" zawiera inne lokacje lub towar!");
        else delete(getSelectedLocation().getId());
    }
}
