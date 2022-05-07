package components;

import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Component
@Getter
public class ArticlesTable extends JTable {

    private JScrollPane scrollPane;

    public ArticlesTable() {
        scrollPane = new JScrollPane();
        setModel(new DefaultTableModel(
                new Object[][] {
                },
                new String[] {
                        "\u2116", "Artyku\u0142", "Ilo\u015B\u0107"
                }
        ) {
            boolean[] columnEditables = new boolean[] {
                    false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
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
