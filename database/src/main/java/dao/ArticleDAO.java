package dao;

import entities.Article;

import java.util.List;

public interface ArticleDAO {
    void save(Article article);
    Article get(long id);
    List<Article> getAll();
    void update(Article article);
    void delete(long id);
}
