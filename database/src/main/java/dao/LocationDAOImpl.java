package dao;

import connection.HibernateUtil;
import entities.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LocationDAOImpl implements LocationDAO {

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Location location) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(location);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public Location get(long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Location.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Location> getAll() {
        try(Session session = sessionFactory.openSession()){
            Query<Location> query = session.createQuery("from entities.Location", Location.class);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(Location location) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(location);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public void delete(long id) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Location location = get(id);
            if(location != null)
                session.delete(location);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }
}
