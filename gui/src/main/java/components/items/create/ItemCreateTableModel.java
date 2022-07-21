package components.items.create;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

public class ItemCreateTableModel extends DefaultTableModel {

    private Map<String, Integer> codeMap;
    private boolean[] columnsEditable = {false, false, false};
    private static final String[] columnsName = {"\u2116", "Artyku\u0142", "Ilo\u015B\u0107"};

    public ItemCreateTableModel() {
        super(new Object[][]{}, columnsName);
        codeMap = new HashMap<>();
    }

    public void addItem(String code, int quantity, int row) {
        addRow(new Object[]{getRowCount()+1, code, quantity});
        codeMap.put(code, row);
    }

    public void increaseQuantity(String code, int quantity){
        int rowNumber = codeMap.get(code);
        int newQuantity = (int)getValueAt(rowNumber, 2) + quantity;
        setValueAt(newQuantity, rowNumber, 2);
    }

    public boolean hasCode(String code) {
        return codeMap.containsKey(code);
    }

    public void clear() {
        setRowCount(0);
    }

    public boolean isCellEditable(int row, int column){
        return columnsEditable[column];
    }
}
