package components.items;

import entities.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class FromItemToRowModel extends ItemsDefaultTableModel implements TableModel {

    private List<Article> articles;

    public FromItemToRowModel(JTable table){
        super(table, new boolean[]{false, false, false});
        this.articles = new ArrayList<>();
    }

    @Override
    public void addArticle(Article article) {
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
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
        int rowCount = getTable().getRowCount();
        for(int i = 0; i < rowCount; i++){
            if(getTable().getValueAt(i, 1).equals(article.getCode()))
                flag = true;
            break;
        }
        return flag;
    }

    private int getRowNumber(Article article) {
        int rowNumber = -1;
        for(int i = 0; i < getTable().getRowCount(); i++){
            if(getTable().getValueAt(i, 1).equals(article.getCode()))
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
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        this.articles.clear();
        model.setRowCount(0);
        addAll(articles);
        model.fireTableDataChanged();
    }

    @Override
    public void deleteSelectedRow() {
        DefaultTableModel model = (DefaultTableModel) getTable().getModel();
        int row = getTable().getSelectedRow();
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
