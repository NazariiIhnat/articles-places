package components;

import components.items.ItemReadUpdateDeleteTableModel;
import components.items.ItemTable;
import components.location.TreeItemList;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import java.awt.*;

@Component
@Getter
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Menu menu;
    private TreeItemList treeItemList;
    private JTextField searchLocationTextFiled;
    private JComboBox<String> comboBox;
    private JCheckBox checkBox;
    private JTextField codeTextField;
    private JTextField quantityTextField;
    private ItemTable table;

    public MainFrame() {
        super("Article places");
        setResizable(false);
        this.treeItemList = new TreeItemList();
        this.table = new ItemTable(new ItemReadUpdateDeleteTableModel());
        initMainFrame();
        initMenu();
        initSearchLocationTextField();
        initTreeItemList();
        initCombobox();
        initCheckBox();
        initCodeTextField();
        initQuantityTextField();
        initItemsTable();
        checkBox.addActionListener(new DisableTextFieldWhenCheckBoxSelected(quantityTextField));
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 494, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void initMenu() {
        this.menu = new Menu();
        menu.setBounds(0, 0, getWidth(), 20);
        add(menu);
    }

    private void initSearchLocationTextField() {
        searchLocationTextFiled = new JTextField();
        searchLocationTextFiled.setFont(new Font("Tahoma", Font.PLAIN, 14));
        searchLocationTextFiled.setBounds(10, 31, 237, 26);
        contentPane.add(searchLocationTextFiled);
    }

    private void initTreeItemList() {
        treeItemList.getScrollPane().setBounds(10, 65, 237, 385);
        treeItemList.setFont(new Font("Tahoma", Font.PLAIN, 14));
        contentPane.add(treeItemList.getScrollPane());
    }

    private void initCombobox() {
        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Dodaj", "Wyjmij"}));
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBox.setBounds(257, 31, 105, 26);
        contentPane.add(comboBox);
    }

    private void initCheckBox() {
        checkBox = new JCheckBox("Pojedyncze");
        checkBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        checkBox.setBounds(372, 31, 97, 26);
        contentPane.add(checkBox);
    }

    private void initCodeTextField(){
        codeTextField = new JTextField();
        codeTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        codeTextField.setBounds(257, 66, 161, 26);
        contentPane.add(codeTextField);
    }

    private void initQuantityTextField() {
        quantityTextField = new JTextField();
        quantityTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        quantityTextField.setBounds(428, 66, 41, 26);
        AbstractDocument document = (AbstractDocument) quantityTextField.getDocument();
        document.setDocumentFilter(new NumberDocumentFilter());
        contentPane.add(quantityTextField);
    }

    private void initItemsTable() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(257, 100, 212, 350);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        contentPane.add(scrollPane);
    }

    @Getter
    public class Menu extends JMenuBar{
        private JMenuItem findItemMenuITem;
        @Getter(AccessLevel.PACKAGE)
        private JMenu optionsMenu;
        private JMenuItem openFolderWithBarcodesMenuItem;

        Menu() {
            this.optionsMenu = new JMenu("Opcje");
            add(optionsMenu);
            this.findItemMenuITem = new JMenuItem("Znajdż towar");
            optionsMenu.add(findItemMenuITem);
            this.openFolderWithBarcodesMenuItem = new JMenuItem("Otwórz folder z kodami lokacji");
            optionsMenu.add(openFolderWithBarcodesMenuItem);
        }
    }
}
