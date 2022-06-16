package components.items.handle;

import lombok.Getter;
import javax.swing.*;

@Getter
public class ItemHandleTable extends JTable {

    private FromRowToItemModel customModel;
    private JScrollPane scrollPane;
    private Object [][] values;

    public ItemHandleTable() {
        this.customModel = new FromRowToItemModel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
    }

    public Object[][] getValues() {
        values = new Object[getRowCount()][3];
        for(int i = 0; i < getRowCount(); i++)
            values[i] = new Object[]{getValueAt(i, 0), getValueAt(i, 1), getValueAt(i, 2)};
        return values;
    }
}
