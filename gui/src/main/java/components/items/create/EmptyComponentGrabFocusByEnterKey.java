package components.items.create;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmptyComponentGrabFocusByEnterKey extends KeyAdapter {

    private JComponent component;

    public EmptyComponentGrabFocusByEnterKey(JComponent component) {
        this.component = component;
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTextField textField = (JTextField) component;
            if(textField.getText().isEmpty())
                component.grabFocus();
        }
    }
}
