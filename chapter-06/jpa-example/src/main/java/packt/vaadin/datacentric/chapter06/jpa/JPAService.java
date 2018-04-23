package packt.vaadin.datacentric.chapter06.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.function.Function;

/**
 * @author Alejandro Duarte
 */
public class JPAService {

    private static EntityManagerFactory factory;

    public static void init() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("jpa-example-pu");
        }
    }

    public static void close() {
        factory.close();
    }

    public static EntityManagerFactory getFactory() {
        return factory;
    }

    public static <T> T runInTransaction(Function<EntityManager, T> function) {
        EntityManager entityManager = null;

        try {
            entityManager = JPAService.getFactory().createEntityManager();
            entityManager.getTransaction().begin();

            T result = function.apply(entityManager);

            entityManager.getTransaction().commit();
            return result;

        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
