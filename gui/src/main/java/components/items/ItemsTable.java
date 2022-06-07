package components.items;

import lombok.Getter;
import javax.swing.*;

@Getter
public class ItemsTable extends JTable {

    private JScrollPane scrollPane;
    private TableModel customModel;

    public ItemsTable() {
        this.customModel = new CustomTableModel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
    }
}
