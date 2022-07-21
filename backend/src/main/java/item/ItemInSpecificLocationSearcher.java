package item;

import components.MainFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@Component
public class ItemInSpecificLocationSearcher extends KeyAdapter {

    private JTextField textArea;
    private JTable table;

    @Autowired
    public ItemInSpecificLocationSearcher(MainFrame frame) {
        textArea = frame.getSearchItemTextField();
        table = frame.getTable();
        textArea.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getExtendedKeyCode() == KeyEvent.VK_ENTER){
            String code = textArea.getText();
            for(int i = 0; i < table.getRowCount(); i++) {
                if(table.getValueAt(i, 1).equals(code)){
                    table.changeSelection(i, 2, false, false);
                    break;
                }
            }
            textArea.setText(null);
        }
    }
}
