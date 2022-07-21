package location;

import components.UserReadableInfoSetDialog;
import components.MainFrame;
import components.location.ShowMessage;
import components.location.TreeItemList;
import components.location.TreeNodeWithID;
import dao.LocationDAO;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utils.ImageHandler;

import javax.swing.tree.TreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component("locationSaver")
public class Saver extends LocationHandler implements ActionListener {

    private UserReadableInfoSetDialog userReadableInfoSetDialog;
    private TreeItemList tree;

    @Autowired
    public Saver(MainFrame mainFrame, ImageHandler imageHandler, LocationDAO locationDAO) {
        super(mainFrame.getTreeItemList(), imageHandler, locationDAO);
        this.userReadableInfoSetDialog = new UserReadableInfoSetDialog(mainFrame);
        this.tree = mainFrame.getTreeItemList();
        mainFrame
                .getTreeItemList()
                .getPopupMenu()
                .getAddLocationMenuItem()
                .addActionListener(x -> this.userReadableInfoSetDialog.setVisible(true));
        this.userReadableInfoSetDialog.getOkBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = userReadableInfoSetDialog.getTextField().getText();
        if(text.isEmpty()) ShowMessage.error("Błąd! Nieprawidłowa nazwa lokacji");
        else save(text);
    }

    private void save(String text) {
        Location location = new Location();
        location.setUserReadableInfo(text);
        super.save(location);
        userReadableInfoSetDialog.setVisible(false);
    }
}
