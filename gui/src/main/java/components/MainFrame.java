package components;

import components.article.ArticlesTable;
import components.location.TreeItemList;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@Component
@Getter
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private Menu menu;
    private TreeItemList treeItemList;
    private JTextField searchLocationTextFiled;
    private JTextField searchItemTextField;
    private ArticlesTable articlesTable;

    public MainFrame() {
        this.treeItemList = new TreeItemList();
        this.articlesTable = new ArticlesTable();
        initMainFrame();
        initMenu();
        initSearchLocationTextField();
        initSearchItemTextField();
        initTreeItemList();
        initArticlesTable();
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 409, 349);
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
        searchItemTextField.setBounds(181, 31, 204, 20);
        contentPane.add(searchItemTextField);
    }

    private void initTreeItemList() {
        treeItemList.getScrollPane().setBounds(10, 61, 161, 239);
        contentPane.add(treeItemList.getScrollPane());
    }

    private void initArticlesTable() {
        articlesTable.getScrollPane().setBounds(181, 61, 204, 239);
        contentPane.add(articlesTable.getScrollPane());
    }

    @Getter
    public class Menu extends JMenuBar{
        @Getter(AccessLevel.PACKAGE)
        private JMenu itemsMenu;
        private JMenuItem addItemToLocationMenuItem;
        private JMenuItem removeItemFromLocationMenuItem;
        private JMenuItem findItemMenuITem;
        @Getter(AccessLevel.PACKAGE)
        private JMenu optionsMenu;
        private JMenuItem openFolderWithBarcodesMenuItem;

        Menu() {
            this.itemsMenu = new JMenu("Towar");
            add(itemsMenu);
            this.addItemToLocationMenuItem = new JMenuItem("Dodaj do lokacji");
            itemsMenu.add(addItemToLocationMenuItem);
            this.removeItemFromLocationMenuItem = new JMenuItem("Wyjmij z lokacji");
            itemsMenu.add(removeItemFromLocationMenuItem);
            this.findItemMenuITem = new JMenuItem("Znajdż");
            itemsMenu.add(findItemMenuITem);
            this.optionsMenu = new JMenu("Opcje");
            add(optionsMenu);
            this.openFolderWithBarcodesMenuItem = new JMenuItem("Otwórz folder z kodami lokacji");
            optionsMenu.add(openFolderWithBarcodesMenuItem);
        }
    }
}
