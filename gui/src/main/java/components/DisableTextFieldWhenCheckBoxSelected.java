package components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisableTextFieldWhenCheckBoxSelected implements ActionListener {

    private JTextField textField;

    public DisableTextFieldWhenCheckBoxSelected(JTextField textField) {
        this.textField = textField;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getSource();
        if(checkBox.isSelected()) {
            textField.setEnabled(false);
            textField.setText("1");
        } else {
            textField.setEnabled(true);
            textField.setText(null);
        }
    }
}
