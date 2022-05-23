package components;

import lombok.Getter;
import javax.swing.*;

@Getter
public class UserReadableInfoSetFrame extends JDialog{

    private JTextField textField;
    private JButton okBtn;

    public UserReadableInfoSetFrame(JFrame owner) {
        super(owner, true);
        setBounds(100, 100, 237, 80);
        getContentPane().setLayout(null);
        initTextField();
        initOkButton();
    }

    private void initTextField() {
        textField = new JTextField();
        textField.setBounds(10, 11, 138, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
    }

    private void initOkButton() {
        okBtn = new JButton("OK");
        okBtn.setBounds(158, 10, 53, 23);
        getContentPane().add(okBtn);
    }
}
