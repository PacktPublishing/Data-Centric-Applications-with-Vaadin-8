package packt.vaadin.datacentric.chapter07.config;

import packt.vaadin.datacentric.chapter07.domain.Role;
import packt.vaadin.datacentric.chapter07.domain.RoleRepository;
import packt.vaadin.datacentric.chapter07.domain.User;
import packt.vaadin.datacentric.chapter07.domain.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Alejandro Duarte
 */
public class JPAService {

    private static EntityManagerFactory factory;

    public static void init() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("chapter-07-pu");
            createTestData();
        }
    }

    private static void createTestData() {
        for (int i = 1; i <= 4; i++) {
            Role role = new Role();
            role.setName("Role " + i);
            role.setModule1Authorized(true);
            RoleRepository.save(role);
        }

        Set<Role> roles = RoleRepository.findAll().stream()
                .filter(r -> r.getId() <= 2)
                .collect(Collectors.toSet());

        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setFirstName("First" + i);
            user.setLastName("Last" + i);
            user.setEmail("user" + i + "@test.com");
            user.setPassword("password" + i);
            user.setRoles(roles);
            user.setMainRole(roles.stream().findFirst().get());
            UserRepository.save(user);
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
