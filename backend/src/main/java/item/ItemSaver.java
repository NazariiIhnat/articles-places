package item;

import components.MainFrame;
import components.items.ItemReadUpdateDeleteTableModel;
import components.items.create.ItemCreateTableModel;
import components.items.create.ItemCreateDialog;
import components.location.ShowMessage;
import components.location.TreeNodeWithID;
import dao.CodeDAO;
import dao.ItemsDAO;
import dao.LocationDAO;
import entities.Code;
import entities.Item;
import entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.event.ActionListener;

@Component
public class ItemSaver {

    private MainFrame mainFrame;
    private ItemsDAO itemsDAO;
    private LocationDAO locationDAO;
    private CodeDAO codeDAO;
    private ItemCreateDialog dialog;
    private Location selectedLocation;
    private ItemReadUpdateDeleteTableModel model;

    @Autowired
    public ItemSaver(MainFrame mainFrame, ItemsDAO itemsDAO, LocationDAO locationDAO, CodeDAO codeDAO) {
        this.mainFrame = mainFrame;
        this.itemsDAO = itemsDAO;
        this.locationDAO = locationDAO;
        this.codeDAO = codeDAO;
        this.model = (ItemReadUpdateDeleteTableModel) mainFrame.getTable().getModel();
        dialog = new ItemCreateDialog(mainFrame);
        mainFrame.getTreeItemList().getPopupMenu().getAddItemMenuItem().addActionListener(showItemHandleDialog());
        dialog.getOkButton().addActionListener(saveItems());
    }

    private ActionListener showItemHandleDialog() {
        return x -> dialog.setVisible(true);
    }

    private ActionListener saveItems() {
        return x -> {
            selectedLocation = getSelectedLocation();
            dialog.getTable().clearSelection();
            dialog.getTable().editCellAt(0, 0);
            if(isCorrectValues()){
                for(Object[] obj : dialog.getTable().getValues()) {
                    String strCode = (String) obj[1];
                    Code code = codeDAO.get(strCode);
                    int quantity = (int) obj[2];
                    if (code != null) {
                        if (getSelectedLocation().hasItem(strCode)) {
                            Item item = selectedLocation
                                    .getItems()
                                    .stream()
                                    .filter(i -> i.getCode().equals(strCode))
                                    .findFirst()
                                    .get();
                            item.setQuantity(item.getQuantity() + quantity);
                            itemsDAO.update(item);
                            model.addItem(item);
                        } else saveItem(code, quantity);
                    } else {
                        code = new Code(strCode);
                        codeDAO.save(code);
                        saveItem(code, quantity);
                    }
                }
                dialog.getTable().setModel(new ItemCreateTableModel());
                dialog.dispose();
                mainFrame.setEnabled(true);
                mainFrame.toFront();
            }
        };
    }

    private void saveItem(Code code, int quantity) {
        Item item = new Item();
        item.setQuantity(quantity);
        item.setCode(code);
        item.setLocation(selectedLocation);
        model.addItem(item);
        itemsDAO.save(item);
        selectedLocation.addItem(item);
        locationDAO.update(selectedLocation);
        selectedLocation = getSelectedLocation();
    }

    private Location getSelectedLocation() {
        TreeNodeWithID node = mainFrame.getTreeItemList().getCustomTreeModel().getSelectedNode();
        return locationDAO.get(node.getId());
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
                int i = (int)obj[2];
            } catch (NumberFormatException e){
                ShowMessage.error("Podana niepoprawna ilość w wierszu № " + obj[0]);
                flag = false;
                break;
            }
        }
        return flag;
    }
}
