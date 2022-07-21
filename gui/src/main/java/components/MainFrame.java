package components;

import com.formdev.flatlaf.FlatLightLaf;
import components.items.ItemReadUpdateDeleteTable;
import components.items.ItemReadUpdateDeleteTableModel;
import components.location.TreeItemList;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@Component
@Getter
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Menu menu;
    private TreeItemList treeItemList;
    private JTextField searchLocationTextFiled;
    private JTextField searchItemTextField;
    private ItemReadUpdateDeleteTable table;

    public MainFrame() {
        super("Article places");
        setResizable(false);
        this.treeItemList = new TreeItemList();
        this.table = new ItemReadUpdateDeleteTable();
        initMainFrame();
        initMenu();
        initSearchLocationTextField();
        initSearchItemTextField();
        initTreeItemList();
        initItemsTable();
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 424, 349);
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
        searchLocationTextFiled.setBounds(10, 31, 161, 20);
        contentPane.add(searchLocationTextFiled);
    }

    private void initSearchItemTextField() {
        searchItemTextField = new JTextField();
        searchItemTextField.setBounds(181, 31, 219, 20);
        contentPane.add(searchItemTextField);
    }

    private void initTreeItemList() {
        treeItemList.getScrollPane().setBounds(10, 61, 161, 239);
        contentPane.add(treeItemList.getScrollPane());
    }

    private void initItemsTable() {
        table.getScrollPane().setBounds(181, 61, 219, 239);
        contentPane.add(table.getScrollPane());
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
