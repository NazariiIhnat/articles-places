package item;

import components.MainFrame;
import components.items.handle.ItemHandleDialog;
import components.location.ShowMessage;
import components.location.TreeNodeWithID;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

@Component
public class ItemSaver {

    private MainFrame mainFrame;
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;
    private ItemHandleDialog dialog;

    @Autowired
    public ItemSaver(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO) {
        this.mainFrame = mainFrame;
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        mainFrame.getTreeItemList().getPopupMenu().getAddItemMenuItem().addActionListener(showItemHandleDialog());;
    }

    private ActionListener showItemHandleDialog() {
        return x -> {
            dialog = new ItemHandleDialog(mainFrame);
            dialog.getOkButton().addActionListener(saveItems());
            dialog.setVisible(true);
        };
    }

    private ActionListener saveItems() {
        return x -> {
            List<Item> items = new ArrayList<>();
            dialog.getTable().clearSelection();
            dialog.getTable().editCellAt(0, 0);
            if(isCorrectValues()){
            for(Object[] obj : dialog.getTable().getValues()){
                String code = (String) obj[1];
                int quantity = Integer.parseInt((String) obj[2]);
                for(int i = 0; i < quantity; i++){
                    Item item = new Item();
                    item.setCode(code);
                    item.setLocation(getSelectedLocation());
                    items.add(item);
                }
            }
            dialog.setVisible(false);
            dialog.getTable().getCustomModel().clearData();
            items.forEach(item -> {
                itemsDAO.save(item);
                mainFrame.getItemsTable().getCustomModel().addItem(item);
            });
            }
        };
    }

    private boolean isCorrectValues() {
        return !areEmptyCells() && areCorrectQuantities();
    }

    private boolean areEmptyCells() {
        boolean flag = false;
        for(Object[] objects : dialog.getTable().getValues()){
            if(objects[1] == null | objects[2] == null){
                flag = true;
                ShowMessage.error("Nie podano danych w wierszu № " + objects[0]);
                break;
            }
        }
        return flag;
    }

    private boolean areCorrectQuantities() {
        boolean flag = true;
        for(Object [] obj : dialog.getTable().getValues()){
            try {
                Integer.parseInt((String)obj[2]);
            } catch (NumberFormatException e){
                ShowMessage.error("Podana niepoprawna ilość w wierszu № " + obj[0]);
                flag = false;
                break;
            }
        }
        return flag;
    }

    private Location getSelectedLocation() {
        TreeNodeWithID node = mainFrame.getTreeItemList().getCustomTreeModel().getSelectedNode();
        return locationDAO.get(node.getId());
    }
}
