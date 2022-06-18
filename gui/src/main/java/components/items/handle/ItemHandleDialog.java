package components.items.handle;

import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Getter
@Component
public class ItemHandleDialog extends JDialog {

    private JPanel contentPanel = new JPanel();
    private JButton okButton;
    private ItemHandleTable table;

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

    private void initTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 219, 239);
        contentPanel.add(scrollPane);
        table = new ItemHandleTable();
        scrollPane.setViewportView(table);
    }

    private void initOkButton() {
        okButton = new JButton("OK");
        okButton.setBounds(10, 261, 219, 23);
        contentPanel.add(okButton);
    }
}
