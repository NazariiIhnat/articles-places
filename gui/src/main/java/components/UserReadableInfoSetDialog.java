package components;

import lombok.Getter;
import javax.swing.*;

@Getter
public class UserReadableInfoSetDialog extends JDialog{

    private JTextField textField;
    private JButton okBtn;

    public UserReadableInfoSetDialog(JFrame owner) {
        super(owner, "Nowa lokacja", true);
        setBounds(100, 100, 237, 80);
        getContentPane().setLayout(null);
        addWindowFocusListener(new DialogShowCloseAction());
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
