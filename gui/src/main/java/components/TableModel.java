package components;

import entities.Article;

import java.util.List;

public interface TableModel {
    void addArticle(Article article);
    void addAll(List<Article> articles);
    void set(List<Article> articles);
    void deleteSelectedRow();
}
