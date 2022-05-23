package components;

import lombok.Getter;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@Component
@Getter
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private TreeItemList treeItemList;
    private ArticlesTable articlesTable;
    private JButton generateBtn;

    public MainFrame() {
        this.treeItemList = new TreeItemList();
        this.articlesTable = new ArticlesTable();
        initMainFrame();
        initTreeItemList();
        initArticlesTable();
        initGenerateBtn();
    }

    private void initMainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 409, 339);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }

    private void initTreeItemList() {
        treeItemList.getScrollPane().setBounds(10, 11, 161, 239);
        contentPane.add(treeItemList.getScrollPane());
    }

    private void initArticlesTable() {
        articlesTable.getScrollPane().setBounds(181, 11, 199, 239);
        contentPane.add(articlesTable.getScrollPane());
    }

    private void initGenerateBtn() {
        generateBtn = new JButton("Generuj");
        generateBtn.setBounds(10, 261, 89, 23);
        contentPane.add(generateBtn);
    }
}
