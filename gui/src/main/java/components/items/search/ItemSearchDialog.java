package components.items.search;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

@Getter
public class ItemSearchDialog extends JDialog {

    private ItemSearchList searchList;
    private JTextField textField;

    public ItemSearchDialog(Frame owner) {
        super(owner);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 409, 258);
        initTextField();
        initSearchList();
        addWindowListener(ownerEnableAndDisable());
    }

    private void initTextField() {
        textField = new JTextField();
        textField.setBounds(10, 7, 373, 20);
        getContentPane().add(textField);
        textField.setColumns(10);
    }

    private void initSearchList() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 38, 373, 170);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        searchList = new ItemSearchList();
        scrollPane.setViewportView(searchList);
    }

    private WindowListener ownerEnableAndDisable() {
        return new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                getParent().setEnabled(false);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                getParent().setEnabled(true);
                JFrame frame = (JFrame) getParent();
                frame.toFront();
            }
        };
    }
}
