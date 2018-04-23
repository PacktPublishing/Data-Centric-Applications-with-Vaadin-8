package packt.vaadin.datacentric.chapter08.domain;

import packt.vaadin.datacentric.chapter08.config.JPAService;

import javax.persistence.Query;

/**
 * @author Alejandro Duarte
 */
public class CallRepository {

    public static boolean isEmpty() {
        return JPAService.runInTransaction(em -> {
            Query query = em.createQuery("select c from Call c");
            query.setMaxResults(1);
            return query.getResultList().size() == 0;
        });
    }

    public static Call lastCall() {
        return JPAService.runInTransaction(em -> {
            Query query = em.createQuery("select c from Call c order by c.startTime desc");
            query.setMaxResults(1);
            Call call = (Call) query.getResultList().get(0);
            return call;
        });
    }

    public static Call save(Call call) {
        return JPAService.runInTransaction(em -> em.merge(call));
    }

}
