package components.items;

import lombok.Getter;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class ItemsTable extends JTable {

    private JScrollPane scrollPane;
    private TableModel customModel;
    private PopupMenu popupMenu;

    public ItemsTable() {
        this.customModel = new FromItemToRowModel(this);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(this);
        popupMenu = new PopupMenu();
        setComponentPopupMenu(popupMenu);
        addMouseListener(rowSelectRMB());
    }

    private MouseAdapter rowSelectRMB() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int r = rowAtPoint(e.getPoint());
                if(r >= 0 && r < getRowCount())
                    setRowSelectionInterval(r, r);
                else
                    clearSelection();
                int rowindex = getSelectedRow();
                if(rowindex < 0)
                    return;
                if (e.isPopupTrigger())
                    e.getComponent();
            }
        };
    }

    @Getter
    public class PopupMenu extends JPopupMenu{
        private JMenuItem deleteItemMenuItem;
        private JMenuItem reduceItemQuantityMenuItem;

        PopupMenu() {
            deleteItemMenuItem = new JMenuItem("Usuń");
            reduceItemQuantityMenuItem = new JMenuItem("Wyjmij");
            add(deleteItemMenuItem);
            add(reduceItemQuantityMenuItem);
        }
    }
}
