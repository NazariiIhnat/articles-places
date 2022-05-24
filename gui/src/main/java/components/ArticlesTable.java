package components;

import lombok.Getter;
import javax.swing.*;

@Getter
public class ArticlesTable extends JTable {

    private JScrollPane scrollPane;
    private TableModel customModel;

    public ArticlesTable() {
        this.customModel = new CustomTableModel(this);
        scrollPane = new JScrollPane();
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(0).setPreferredWidth(30);
        getColumnModel().getColumn(0).setMinWidth(30);
        getColumnModel().getColumn(0).setMaxWidth(30);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(1).setPreferredWidth(136);
        getColumnModel().getColumn(1).setMinWidth(136);
        getColumnModel().getColumn(1).setMaxWidth(136);
        getColumnModel().getColumn(2).setResizable(false);
        getColumnModel().getColumn(2).setPreferredWidth(36);
        getColumnModel().getColumn(2).setMinWidth(36);
        getColumnModel().getColumn(2).setMaxWidth(36);
        scrollPane.setViewportView(this);
    }
}
