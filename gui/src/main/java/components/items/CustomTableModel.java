package components.items;

import entities.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomTableModel extends DefaultTableModel implements TableModel {

    private JTable table;
    private List<Article> articles;
    private boolean[] columnsEditable;
    private static final String[] columnsName = {"\u2116", "Artyku\u0142", "Ilo\u015B\u0107"};

    public CustomTableModel(JTable table){
        this(table, new boolean[]{false, false, false});
    }

    public CustomTableModel(JTable table, boolean[] columnsEditable) {
        super(new Object[][]{}, columnsName);
        this.table = table;
        this.articles = new ArrayList<>();
        this.columnsEditable = columnsEditable;
        table.setModel(this);
        initColumnsSize();
    }

    @Override
    public boolean isCellEditable(int row, int column){
        return columnsEditable[column];
    }

    private void initColumnsSize() {
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(0).setMinWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(30);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(136);
        table.getColumnModel().getColumn(1).setMinWidth(136);
        table.getColumnModel().getColumn(1).setMaxWidth(136);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(2).setPreferredWidth(51);
        table.getColumnModel().getColumn(2).setMinWidth(51);
        table.getColumnModel().getColumn(2).setMaxWidth(51);
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
