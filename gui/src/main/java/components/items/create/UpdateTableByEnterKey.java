package components.items.create;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class UpdateTableByEnterKey extends KeyAdapter {

    private JTextField codeTextField;
    private JTextField quantityTextField;
    private JTable table;

    public UpdateTableByEnterKey(JTextField codeTextField, JTextField quantityTextField, JTable table) {
        this.codeTextField = codeTextField;
        this.quantityTextField = quantityTextField;
        this.table = table;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            ItemCreateTableModel model = (ItemCreateTableModel) table.getModel();
            String code = codeTextField.getText();
            String quantity = quantityTextField.getText();
            if(!code.isEmpty() && !quantity.isEmpty()){
                if(model.hasCode(code)) {
                    model.increaseQuantity(codeTextField.getText(), Integer.parseInt(quantity));
                    clearQuantityAndCodeTextFields();
                }
                else {
                    model.addItem(code, Integer.parseInt(quantity), table.getRowCount());
                    clearQuantityAndCodeTextFields();
                }
                codeTextField.grabFocus();
            }
            System.out.println(model.getRowCount());
        }
    }

    private void clearQuantityAndCodeTextFields() {
        codeTextField.setText(null);
        quantityTextField.setText(null);
    }
}
