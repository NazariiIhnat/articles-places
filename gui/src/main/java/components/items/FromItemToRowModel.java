package components.items;

import entities.Item;

import javax.swing.*;
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
        if(existInTable(item)) {
            int row = getRowNumber(item);
            int column = 2;
            int numberOfItem = (int) getValueAt(row, column);
            setValueAt(numberOfItem + 1, row, column);
            fireTableCellUpdated(row, column);
        } else {
            addRow(new Object[]{
                    getRowCount()+1,
                    item.getCode(),
                    1
            });
            int row = getRowNumber(item);
            fireTableRowsInserted(row, row);
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
        this.items.clear();
        setRowCount(0);
        addAll(items);
        fireTableDataChanged();
    }

    @Override
    public void deleteSelectedRow() {
        int row = getTable().getSelectedRow();
        String code = (String) getValueAt(row, 1);
        items.removeIf(item -> item.getCode().equals(code));
        removeRow(row);
        fireTableRowsDeleted(row, row);
    }

    @Override
    public void reduceQuantityOfSelectedRow(int quantity) {
        int row = getTable().getSelectedRow();
        int column = 2;
        int oldQuantity = (int)getValueAt(row, column);
        int newQuantity = oldQuantity - quantity;
        setValueAt(newQuantity, row, 2);
        fireTableCellUpdated(row, 2);
    }
}
