package components.items.handle;

import components.items.CustomTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ItemHandleTableModel extends CustomTableModel {

    private JTable table;

    public ItemHandleTableModel(JTable table) {
        super(table, new boolean[]{false, true, true});
        this.table = table;
        this.table.setModel(this);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        addRow(1);
        createChangeSelectionBindings();
        table.changeSelection(0, 1, false, false);
        table.editCellAt(0, 1);
    }

    private void addRow(int rowNumber) {
        addRow(new Object[]{rowNumber, null, null});
    }

    private void createChangeSelectionBindings() {
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        table.getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                table.editCellAt(0, 0);
                    if(isEmptyCodeCell() | isEmptyQuantityCell()){
                        changeSelectionBetweenCodeAndQuantityColumnInSameRow();
                    } else {
                        if(!isSelectedLastRow()) {
                            if(isEmptyLastRowNumberOrQuantityColumns()){
                               table.changeSelection(table.getRowCount()-1, 1, false, false);
                            } else {
                                addRow(getRowCount());
                                table.changeSelection(getRowCount()-1, 1, false, false);
                                table.editCellAt(table.getSelectedRow(), 1);
                            }
                        } else {
                            addRow(getRowCount());
                            table.changeSelection(getRowCount() - 1, 1, false, false);
                            table.editCellAt(table.getSelectedRow(), 1);
                        }
                    }
            }
        });
    }

    private boolean isEmptyCodeCell() {
        Object val = getValueAt(table.getSelectedRow(), 1);
        return val == null || val.toString().trim().equals("");
    }

    private boolean isEmptyQuantityCell() {
        Object val = getValueAt(table.getSelectedRow(), 2);
        return val == null || val.toString().trim().equals("");
    }

    private void changeSelectionBetweenCodeAndQuantityColumnInSameRow() {
        if (table.getSelectedColumn() == 1)
            selectQuantityColumn();
        else
            selectCodeColumn();
    }

    private void selectCodeColumn() {
        table.changeSelection(table.getSelectedRow(), 1, false, false);
        table.editCellAt(table.getSelectedRow(), 1);
    }

    private void selectQuantityColumn() {
        table.changeSelection(table.getSelectedRow(), 2, false, false);
        table.editCellAt(table.getSelectedRow(), 2);
    }

    private boolean isSelectedLastRow() {
        return getRowCount()-1 == table.getSelectedRow();
    }

    private boolean isEmptyLastRowNumberOrQuantityColumns() {
        String codeVal = (String) getValueAt(table.getRowCount()-1, 1);
        String quantityVal = (String) getValueAt(table.getRowCount()-1, 2);
        return codeVal == null || codeVal.trim().equals("") || quantityVal == null || quantityVal.trim().equals("");
    }
}
