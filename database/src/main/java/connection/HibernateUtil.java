package connection;

import entities.Item;
import entities.Location;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Location.class)
                .configure();
        return configuration.buildSessionFactory();
    }

    public static void start() {
        buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }
}
