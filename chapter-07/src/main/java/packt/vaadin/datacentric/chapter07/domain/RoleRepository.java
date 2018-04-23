package packt.vaadin.datacentric.chapter07.domain;

import packt.vaadin.datacentric.chapter07.config.JPAService;

import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class RoleRepository {

    public static List<Role> findAll() {
        return JPAService.runInTransaction(em ->
                em.createQuery("select r from Role r").getResultList()
        );
    }

    public static Role save(Role role) {
        return JPAService.runInTransaction(em -> em.merge(role));
    }

}
