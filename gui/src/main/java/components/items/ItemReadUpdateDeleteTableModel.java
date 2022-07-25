package components.items;

import entities.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemReadUpdateDeleteTableModel extends ItemTableModel implements TableModel {

    private List<String> codeList;

    public ItemReadUpdateDeleteTableModel(){
        super(false, false);
        codeList = new ArrayList<>();
    }

    @Override
    public void addItem(String code, int quantity) {
        codeList.add(code);
        addRow(new Object[]{getRowCount()+1, code, quantity});
        fireTableRowsInserted(codeList.indexOf(code), codeList.indexOf(code));
    }

    @Override
    public void increaseQuantity(String code, int value) {
        int row = codeList.indexOf(code);
        int oldQuantity = (int) getValueAt(row, 2);
        int newQuantity = oldQuantity + value;
        setValueAt(newQuantity, row, 2);
        fireTableCellUpdated(row, 2);
    }

    @Override
    public void deleteItem(String code) {
        int row = codeList.indexOf(code);
        codeList.remove(code);
        removeRow(row);
        updatePositionNumber(row);
    }

    private void updatePositionNumber(int fromRow){
        for (int i = fromRow; i < getRowCount(); i++)
            setValueAt(i+1, i, 0);
        fireTableRowsUpdated(fromRow, getColumnCount()-1);
    }

    @Override
    public void reduceQuantity(String code, int value) {
        int row = codeList.indexOf(code);
        int oldQuantity = (int) getValueAt(row, 2);
        int newQuantity = oldQuantity - value;
        setValueAt(newQuantity, row, 2);
        fireTableCellUpdated(row, 2);
    }

    @Override
    public void set(List<Item> items) {
        codeList.clear();
        setRowCount(0);
        items.forEach(x -> {
            String code = x.getCode();
            int quantity = x.getQuantity();
            codeList.add(code);
            addRow(new Object[]{getRowCount()+1, code, quantity});
        });
        fireTableRowsInserted(0, getColumnCount());
    }


    //    @Override
//    public void addItem(Item item) {
//        if(existInTable(item.getCode())) {
//            int row = getRowNumber(item.getCode());
//            int column = 2;
//            setValueAt(item.getQuantity(), row, column);
//            fireTableCellUpdated(row, column);
//        } else {
//            addRow(new Object[]{
//                    getRowCount()+1,
//                    item.getCode(),
//                    item.getQuantity()
//            });
//            int row = getRowNumber(item.getCode());
//            fireTableRowsInserted(row, row);
//        }
//    }
//
//    private int getRowNumber(String code) {
//        int rowNumber = -1;
//        for(int i = 0; i < getRowCount(); i++){
//            if(getValueAt(i, 1).equals(code)) {
//                rowNumber = i;
//                break;
//            }
//        }
//        return rowNumber;
//    }
//
//    private boolean existInTable(String code) {
//        boolean flag = false;
//        for(int i = 0; i < getRowCount(); i++){
//            if(getValueAt(i, 1).equals(code)) {
//                flag = true;
//                break;
//            }
//        }
//        return flag;
//    }
//
//    @Override
//    public void addAll(List<Item> codeList) {
//        codeList.forEach(this::addItem);
//    }
//
//    @Override
//    public void set(List<Item> codeList) {
//        setRowCount(0);
//        addAll(codeList);
//        fireTableDataChanged();
//    }
//
//    @Override
//    public void deleteRow(int row) {
//        removeRow(row);
//        fireTableRowsDeleted(row, row);
//        for(int i = 0; i < getRowCount(); i++) {
//            setValueAt(i + 1, i, 0);
//            fireTableCellUpdated(i, 0);
//        }
//    }
//
//    @Override
//    public void reduceQuantityOfRow(int quantity, int row) {
//        int oldQuantity = (int) getValueAt(row, 2);
//        int newQuantity = oldQuantity - quantity;
//        setValueAt(newQuantity, row, 2);
//        fireTableCellUpdated(row, 2);
//    }
}
