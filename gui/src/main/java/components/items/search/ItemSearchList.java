package components.items.search;

import lombok.Getter;

import javax.swing.*;

@Getter
public class ItemSearchList extends JList<String> {

    private CustomListModel customListModel;

    public  ItemSearchList() {
        customListModel = new CustomListModel();
        setModel(customListModel);
    }
}
