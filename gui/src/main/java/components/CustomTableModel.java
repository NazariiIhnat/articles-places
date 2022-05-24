package components;

import entities.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomTableModel implements TableModel{

    private JTable table;
    private List<Article> articles;

    public CustomTableModel(JTable table){
        this.table = table;
        this.articles = new ArrayList<>();
        initTableModel();
    }

    private void initTableModel() {
        this.table.setModel(new DefaultTableModel(
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
    }


    @Override
    public void addArticle(Article article) {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        if(existInTable(article)) {
            int row = getRowNumber(article);
            int column = 2;
            int numberOfArticle = (int) model.getValueAt(row, column);
            model.setValueAt(numberOfArticle + 1, row, column);
            model.fireTableCellUpdated(row, column);
        } else {
            model.addRow(new Object[]{
                    model.getRowCount()+1,
                    article.getCode(),
                    1
            });
            int row = getRowNumber(article);
            model.fireTableRowsInserted(row, row);
        }
        this.articles.add(article);
    }

    private boolean existInTable(Article article) {
        boolean flag = false;
        int rowCount = table.getRowCount();
        for(int i = 0; i < rowCount; i++){
            if(table.getValueAt(i, 1).equals(article.getCode()))
                flag = true;
            break;
        }
        return flag;
    }

    private int getRowNumber(Article article) {
        int rowNumber = -1;
        for(int i = 0; i < table.getRowCount(); i++){
            if(table.getValueAt(i, 1).equals(article.getCode()))
                rowNumber = i;
            break;
        }
        return rowNumber;
    }

    @Override
    public void addAll(List<Article> articles) {
        articles.forEach(this::addArticle);
    }

    @Override
    public void set(List<Article> articles) {
        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        this.articles.clear();
        model.setRowCount(0);
        addAll(articles);
        model.fireTableDataChanged();
    }

    @Override
    public void deleteSelectedRow() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        int column =  2;
        int numberOfArticle = (int)model.getValueAt(row, column);
        if(numberOfArticle != 1) {
            model.setValueAt(numberOfArticle - 1, row, column);
            model.fireTableCellUpdated(row, column);
        } else {
           model.removeRow(row);
           this.articles.remove(row);
           model.fireTableRowsDeleted(row, row);
        }
    }
}
