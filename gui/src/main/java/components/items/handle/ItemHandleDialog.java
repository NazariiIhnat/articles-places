package components.items.handle;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Getter
public class ItemHandleDialog extends JDialog {

    private JPanel contentPanel = new JPanel();
    private JTable table;

    public ItemHandleDialog(JFrame frame) {
        super(frame);
        setBounds(100, 100, 255, 332);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        initTable();
        initOkButton();
    }

    public static void main(String[] args) {
        ItemHandleDialog dialog = new ItemHandleDialog(null);
        dialog.setVisible(true);
    }

    private void initTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 219, 239);
        contentPanel.add(scrollPane);
        table = new JTable();
        scrollPane.setViewportView(table);
    }

    private void initOkButton() {
        JButton btnNewButton = new JButton("OK");
        btnNewButton.setBounds(10, 261, 219, 23);
        contentPanel.add(btnNewButton);
    }
}
