package dao;

import connection.HibernateUtil;
import entities.Item;
import entities.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class ItemsDAOImpl implements ItemsDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    @Transactional
    public void save(Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(item);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Item item) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(item);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getByCode(String code) {
        String hql = "from Item where CODE_VALUE = :code";
        List<Item> itemProxies;
        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(hql);
            query.setParameter("code", code);
            itemProxies = query.list();
            return itemProxies;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Item> getByLocationID(long id) {
        String hql = "from Item where location_id = :id";
        List<Item> itemProxies;
        try(Session session = sessionFactory.openSession()){
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            itemProxies = query.list();
            return itemProxies;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Item item) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            if (item != null) session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }
}
