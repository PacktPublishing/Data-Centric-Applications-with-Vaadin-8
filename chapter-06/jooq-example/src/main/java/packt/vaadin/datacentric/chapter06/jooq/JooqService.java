package packt.vaadin.datacentric.chapter06.jooq;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author Alejandro Duarte
 */
public class JooqService {

    private static String url;
    private static String password;
    private static String username;
    private static BoneCP pool;

    public static void init() throws SQLException, IOException {
        loadProperties();
        createPool();
    }

    public static void shutdown() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    public static <T> T runWithDslContext(Function<DSLContext, T> function) throws SQLException {
        try (Connection connection = pool.getConnection(); DSLContext dslContext = DSL.using(connection)) {
            T t = function.apply(dslContext);
            return t;
        }
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = JooqService.class.getClassLoader().getResourceAsStream("datasource.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            url = properties.getProperty("datasource.url");
            username = properties.getProperty("datasource.username");
            password = properties.getProperty("datasource.password");
        }
    }

    private static void createPool() throws SQLException {
        BoneCPConfig config = new BoneCPConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        pool = new BoneCP(config);
    }

}
