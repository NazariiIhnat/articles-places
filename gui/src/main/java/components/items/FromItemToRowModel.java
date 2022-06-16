package components.items;

import entities.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class FromItemToRowModel extends ItemsDefaultTableModel implements TableModel {

    private List<Item> items;

    public FromItemToRowModel(JTable table){
        super(table, new boolean[]{false, false, false});
        this.items = new ArrayList<>();
    }

    @Override
    public void addItem(Item item) {
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        if(existInTable(item)) {
            int row = getRowNumber(item);
            int column = 2;
            int numberOfItem = (int) model.getValueAt(row, column);
            model.setValueAt(numberOfItem + 1, row, column);
            model.fireTableCellUpdated(row, column);
        } else {
            model.addRow(new Object[]{
                    model.getRowCount()+1,
                    item.getCode(),
                    1
            });
            int row = getRowNumber(item);
            model.fireTableRowsInserted(row, row);
        }
        this.items.add(item);
    }

    private boolean existInTable(Item item) {
        boolean flag = false;
        int rowCount = getTable().getRowCount();
        for(int i = 0; i < rowCount; i++){
            if(getTable().getValueAt(i, 1).equals(item.getCode())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private int getRowNumber(Item item) {
        int rowNumber = -1;
        for(int i = 0; i < getTable().getRowCount(); i++){
            if(getTable().getValueAt(i, 1).equals(item.getCode())) {
                rowNumber = i;
                break;
            }
        }
        return rowNumber;
    }

    @Override
    public void addAll(List<Item> items) {
        items.forEach(this::addItem);
    }

    @Override
    public void set(List<Item> items) {
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        this.items.clear();
        model.setRowCount(0);
        addAll(items);
        model.fireTableDataChanged();
    }

    @Override
    public void deleteSelectedRow() {
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        int row = getTable().getSelectedRow();
        int column =  2;
        int numberOfItem = (int)model.getValueAt(row, column);
        if(numberOfItem != 1) {
            model.setValueAt(numberOfItem - 1, row, column);
            model.fireTableCellUpdated(row, column);
        } else {
           model.removeRow(row);
           this.items.remove(row);
           model.fireTableRowsDeleted(row, row);
        }
    }
}
