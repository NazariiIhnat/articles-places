package dao;

import connection.HibernateUtil;
import entities.Article;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ArticleDAOImpl implements ArticleDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Article article) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(article);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public Article get(long id) {
        return null;
    }

    @Override
    public List<Article> getAll() {
        return null;
    }

    @Override
    public void update(Article article) {

    }

    @Override
    public void delete(long id) {

    }
}
