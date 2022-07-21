package components.items.create;

import components.items.ItemTable;
import lombok.Getter;

@Getter
public class ItemCreateTable extends ItemTable {

    private Object [][] values;

    public ItemCreateTable (){
        super(new ItemCreateTableModel());
    }

    public Object[][] getValues() {
        values = new Object[getRowCount()][3];
        for(int i = 0; i < getRowCount(); i++)
            values[i] = new Object[]{getValueAt(i, 0), getValueAt(i, 1), getValueAt(i, 2)};
        return values;
    }
}
