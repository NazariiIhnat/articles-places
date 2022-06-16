package dao;

import connection.HibernateUtil;
import entities.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemsDAOImpl implements ItemsDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Item item) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getByLocationID(long id) {
        String hql = "from Item where location_id = :id";
        List<Item> items;
        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            items = query.list();
            return items;
        } catch (Exception e) {
            return null;
        }
    }
}
