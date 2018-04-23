package packt.vaadin.datacentric.chapter09.domain;

import packt.vaadin.datacentric.chapter09.config.JPAService;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

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

    public static List<Call> find(int offset, int limit, String filter, Map<String, Boolean> order) {
        return JPAService.runInTransaction(em -> {
            String jpql = "select c from Call c where lower(c.client) like :filter or c.phoneNumber like :filter or lower(c.city) like :filter" + buildOrderByClause(order);
            Query query = em.createQuery(jpql);
            query.setParameter("filter", "%" + filter.trim().toLowerCase() + "%");
            query.setFirstResult(offset);
            query.setMaxResults(limit);

            List<Call> resultList = query.getResultList();
            return resultList;
        });
    }

    public static int count(String filter) {
        return JPAService.runInTransaction(em -> {
            Query query = em.createQuery("select count(c.id) from Call c where lower(c.client) like :filter or c.phoneNumber like :filter or lower(c.city) like :filter");
            query.setParameter("filter", "%" + filter.trim().toLowerCase() + "%");

            Long count = (Long) query.getSingleResult();
            return count.intValue();
        });
    }

    private static String buildOrderByClause(Map<String, Boolean> order) {
        StringBuilder orderBy = new StringBuilder();
        order.forEach((property, isAscending) -> orderBy.append(property + (isAscending ? "" : " desc") + ","));

        if (orderBy.length() > 0) {
            orderBy.delete(orderBy.length() - 1, orderBy.length());
            return " order by " + orderBy.toString();
        } else {
            return "";
        }
    }

}
