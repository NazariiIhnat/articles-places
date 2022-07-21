package components;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DialogShowCloseAction extends WindowAdapter {

    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        JDialog dialog = (JDialog) e.getSource();
        dialog.getOwner().setEnabled(false);
    }

    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        JDialog dialog = (JDialog) e.getSource();
        dialog.getOwner().setEnabled(true);
        dialog.getOwner().toFront();
    }
}
