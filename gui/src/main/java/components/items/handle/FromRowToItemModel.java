package components.items.handle;

import components.items.ItemsDefaultTableModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class FromRowToItemModel extends ItemsDefaultTableModel {

    public FromRowToItemModel(JTable table) {
        super(table, new boolean[]{false, true, true});
        createChangeSelectionBindings();
        createDeleteSelectedRowBindings();
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        addRow(1);
    }

    private void addRow(int rowNumber) {
        addRow(new Object[]{rowNumber, null, null});
    }

    private void createChangeSelectionBindings() {
        getTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        getTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "Down");
        getTable().getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                getTable().editCellAt(0, 0);
                    if(isEmptyCodeCell() | isEmptyQuantityCell()){
                        changeSelectionBetweenCodeAndQuantityColumnInSameRow();
                    } else {
                        if(!isSelectedLastRow()) {
                            if(isEmptyLastRowNumberOrQuantityColumns()){
                               getTable().changeSelection(getTable().getRowCount()-1, 1, false, false);
                            } else {
                                addRow(getRowCount());
                                getTable().changeSelection(getRowCount()-1, 1, false, false);
                                getTable().editCellAt(getTable().getSelectedRow(), 1);
                            }
                        } else {
                            addRow(getRowCount()+1);
                            getTable().changeSelection(getRowCount() - 1, 1, false, false);
                            getTable().editCellAt(getTable().getSelectedRow(), 1);
                        }
                    }
            }
        });
    }

    private boolean isEmptyCodeCell() {
        Object val = getValueAt(getTable().getSelectedRow(), 1);
        return val == null || val.toString().trim().equals("");
    }

    private boolean isEmptyQuantityCell() {
        Object val = getValueAt(getTable().getSelectedRow(), 2);
        return val == null || val.toString().trim().equals("");
    }

    private void changeSelectionBetweenCodeAndQuantityColumnInSameRow() {
        if (getTable().getSelectedColumn() == 1)
            selectQuantityColumn();
        else
            selectCodeColumn();
    }

    private void selectCodeColumn() {
        getTable().changeSelection(getTable().getSelectedRow(), 1, false, false);
        getTable().editCellAt(getTable().getSelectedRow(), 1);
    }

    private void selectQuantityColumn() {
        getTable().changeSelection(getTable().getSelectedRow(), 2, false, false);
        getTable().editCellAt(getTable().getSelectedRow(), 2);
    }

    private boolean isSelectedLastRow() {
        return getRowCount()-1 == getTable().getSelectedRow();
    }

    private boolean isEmptyLastRowNumberOrQuantityColumns() {
        String codeVal = (String) getValueAt(getTable().getRowCount()-1, 1);
        String quantityVal = (String) getValueAt(getTable().getRowCount()-1, 2);
        return codeVal == null || codeVal.trim().equals("") || quantityVal == null || quantityVal.trim().equals("");
    }

    public void clearData() {
        getTable().setModel(new FromRowToItemModel(getTable()));
    }

    private void createDeleteSelectedRowBindings() {
        getTable().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        getTable().getActionMap().put("Delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = getTable().getSelectedRow();
                removeRow(row);
                for(int i = row; i < getRowCount(); i++)
                    setValueAt(i+1, i, 0);
            }
        });
    }
}
