package dao;

import connection.HibernateUtil;
import entities.Code;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CodeDAOImpl implements CodeDAO{

    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    @Override
    @Transactional
    public void save(Code code) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(code);
        transaction.commit();
        session.close();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Code get(String codeValue) {
        String hql = "from Code where code = :code";
        List<Code> codes;
        try(Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("code", codeValue);
            codes = query.list();
            if (codes.size() > 0)
                return codes.get(0);
            else return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(String codeValue) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Code code = get(codeValue);
            if(code != null)
                session.delete(code);
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null)
                transaction.rollback();
        }
    }
}
