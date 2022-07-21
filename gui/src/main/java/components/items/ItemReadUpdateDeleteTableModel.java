package components.items;

import entities.Item;
import java.util.List;

public class ItemReadUpdateDeleteTableModel extends ItemTableModel implements TableModel {

    public ItemReadUpdateDeleteTableModel(){
        super(false, false);
    }

    @Override
    public void addItem(Item item) {
        if(existInTable(item.getCode())) {
            int row = getRowNumber(item.getCode());
            int column = 2;
            setValueAt(item.getQuantity(), row, column);
            fireTableCellUpdated(row, column);
        } else {
            addRow(new Object[]{
                    getRowCount()+1,
                    item.getCode(),
                    item.getQuantity()
            });
            int row = getRowNumber(item.getCode());
            fireTableRowsInserted(row, row);
        }
    }

    private int getRowNumber(String code) {
        int rowNumber = -1;
        for(int i = 0; i < getRowCount(); i++){
            if(getValueAt(i, 1).equals(code)) {
                rowNumber = i;
                break;
            }
        }
        return rowNumber;
    }

    private boolean existInTable(String code) {
        boolean flag = false;
        for(int i = 0; i < getRowCount(); i++){
            if(getValueAt(i, 1).equals(code)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void addAll(List<Item> itemList) {
        itemList.forEach(this::addItem);
    }

    @Override
    public void set(List<Item> itemList) {
        setRowCount(0);
        addAll(itemList);
        fireTableDataChanged();
    }

    @Override
    public void deleteRow(int row) {
        removeRow(row);
        fireTableRowsDeleted(row, row);
        for(int i = 0; i < getRowCount(); i++) {
            setValueAt(i + 1, i, 0);
            fireTableCellUpdated(i, 0);
        }
    }

    @Override
    public void reduceQuantityOfRow(int quantity, int row) {
        int oldQuantity = (int) getValueAt(row, 2);
        int newQuantity = oldQuantity - quantity;
        setValueAt(newQuantity, row, 2);
        fireTableCellUpdated(row, 2);
    }
}
