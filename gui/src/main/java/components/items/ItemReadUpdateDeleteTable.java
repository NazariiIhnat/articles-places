package components.items;

import lombok.Getter;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Getter
public class ItemReadUpdateDeleteTable extends ItemTable {

    private JScrollPane scrollPane;
    private PopupMenu popupMenu;

    public ItemReadUpdateDeleteTable() {
        super(new ItemReadUpdateDeleteTableModel());
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
