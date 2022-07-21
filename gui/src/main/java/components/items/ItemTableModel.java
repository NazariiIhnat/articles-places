package components.items;

import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

public class ItemTableModel extends DefaultTableModel {

    private Map<String, Integer> codeMap;
    private boolean[] columnsEditable;
    private static final String[] columnsName = {"\u2116", "Artyku\u0142", "Ilo\u015B\u0107"};

    public ItemTableModel() {
        this(false, false);
    }

    public ItemTableModel(boolean isCodeColumnEditable, boolean isQuantityColumnEditable){
        super(new Object[][]{}, columnsName);
        this.codeMap = new HashMap<>();
        this.columnsEditable = new boolean[]{false, isCodeColumnEditable, isQuantityColumnEditable};
    }

    public boolean isCellEditable(int row, int column){
        return columnsEditable[column];
    }
}
