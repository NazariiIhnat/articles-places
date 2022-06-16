package components.items;

import lombok.AccessLevel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class ItemsDefaultTableModel extends DefaultTableModel {

    @Getter(AccessLevel.PROTECTED)
    private JTable table;
    private boolean[] columnsEditable;
    private static final String[] columnsName = {"\u2116", "Artyku\u0142", "Ilo\u015B\u0107"};

    public ItemsDefaultTableModel (JTable table, boolean[] columnsEditable) {
        super(new Object[][]{}, columnsName);
        this.table = table;
        this.columnsEditable = columnsEditable;
        table.setModel(this);
        initColumnsSize();
    }

    private void initColumnsSize() {
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(0).setMinWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(136);
        table.getColumnModel().getColumn(1).setMinWidth(136);
        table.getColumnModel().getColumn(1).setMaxWidth(136);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(51);
        table.getColumnModel().getColumn(2).setMinWidth(51);
        table.getColumnModel().getColumn(2).setMaxWidth(51);
    }

    public boolean isCellEditable(int row, int column){
        return columnsEditable[column];
    }
}
