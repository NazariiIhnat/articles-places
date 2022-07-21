package components.items.create;

import components.DialogShowCloseAction;
import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;

@Getter
@Component
public class ItemCreateDialog extends JDialog {

    private JPanel contentPanel = new JPanel();
    private JTextField codeTextField;
    private JTextField quantityTextField;
    private JButton okButton;
    private ItemCreateTable table;

    public ItemCreateDialog(JFrame frame) {
        super(frame, "Lista nowych towarów");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new DialogShowCloseAction());
        setBounds(100, 100, 255, 332);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        initCodeTextField();
        initQuantityTextField();
        initTable();
        initOkButton();
        initListeners();
    }

    private void initCodeTextField() {
        codeTextField = new JTextField();
        codeTextField.setBounds(10, 11, 169, 20);
        contentPanel.add(codeTextField);
        codeTextField.setColumns(10);
    }

    private void initQuantityTextField() {
        quantityTextField = new JTextField();
        AbstractDocument document = (AbstractDocument) quantityTextField.getDocument();
        document.setDocumentFilter(new NumberDocumentFilter());
        quantityTextField.setBounds(189, 11, 40, 20);
        contentPanel.add(quantityTextField);
        quantityTextField.setColumns(10);
    }

    private void initTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 42, 219, 208);
        table = new ItemCreateTable();
        scrollPane.setViewportView(table);
        contentPanel.add(scrollPane);
    }

    private void initOkButton() {
        okButton = new JButton("OK");
        okButton.setBounds(10, 259, 219, 23);
        contentPanel.add(okButton);
    }

    private void initListeners() {
        codeTextField.addKeyListener(new EmptyComponentGrabFocusByEnterKey(quantityTextField));
        quantityTextField.addKeyListener(new EmptyComponentGrabFocusByEnterKey(codeTextField));
        codeTextField.addKeyListener(new UpdateTableByEnterKey(codeTextField, quantityTextField, table));
        quantityTextField.addKeyListener(new UpdateTableByEnterKey(codeTextField, quantityTextField, table));
    }
}
