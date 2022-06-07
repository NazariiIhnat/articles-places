package components.items.handle;

import components.items.TableModel;
import lombok.Getter;

import javax.swing.*;

@Getter
public class ItemHandleTable extends JTable {

    private TableModel customModel;
    private JScrollPane scrollPane;

    public ItemHandleTable() {
        this.customModel = new ItemHandleTableModel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
    }
}
