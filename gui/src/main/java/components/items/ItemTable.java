package components.items;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ItemTable extends JTable {

    public ItemTable(DefaultTableModel model){
        super.setModel(model);
        setColumnsSize();
    }

    public void setModel(DefaultTableModel model) {
        super.setModel(model);
        setColumnsSize();
    }

    private void setColumnsSize(){
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(0).setPreferredWidth(30);
        getColumnModel().getColumn(0).setMinWidth(30);
        getColumnModel().getColumn(0).setMaxWidth(30);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(1).setPreferredWidth(136);
        getColumnModel().getColumn(1).setMinWidth(136);
        getColumnModel().getColumn(1).setMaxWidth(136);
        getColumnModel().getColumn(2).setResizable(false);
        getColumnModel().getColumn(2).setPreferredWidth(51);
        getColumnModel().getColumn(2).setMinWidth(51);
        getColumnModel().getColumn(2).setMaxWidth(51);
    }
}
