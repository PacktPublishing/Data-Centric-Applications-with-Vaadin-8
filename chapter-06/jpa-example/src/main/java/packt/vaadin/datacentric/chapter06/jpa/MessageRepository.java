package packt.vaadin.datacentric.chapter06.jpa;

import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class MessageRepository {

    public static List<Message> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select m from Message m").getResultList()
        );
    }

    public static void save(Message message) {
        JPAService.runInTransaction(em -> {
            em.persist(message);
            return null;
        });
    }

}
