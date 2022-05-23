package dao;

import connection.HibernateUtil;
import entities.Container;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ContainerDAOImpl implements ContainerDAO{

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    public void save(Container container) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.save(container);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }

    @Override
    public Container get(long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Container.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Container> getAll() {
        try(Session session = sessionFactory.openSession()){
            Query<Container> query = session.createQuery("from entities.Container", Container.class);
            return query.list();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void update(Container container) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()){
            transaction = session.beginTransaction();
            session.update(container);
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
            Container container = get(id);
            if(container != null)
                session.delete(container);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }
}
