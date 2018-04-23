package packt.vaadin.datacentric.chapter05.jdbc;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Alejandro Duarte
 */
public class DatabaseService {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE messages(id BIGINT auto_increment, content VARCHAR(255))";
    private static final String INSERT_SQL = "INSERT INTO messages(id, content) VALUES(%d, '%s')";
    private static final String SELECT_SQL = "SELECT content FROM messages";
    private static final String CONTENT_COLUMN = "content";

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

    public static void createSchema() throws SQLException {
        try (Connection connection = pool.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_SQL);
        }
    }

    public static void insertDemoData(int messageCount) throws SQLException {
        try (Connection connection = pool.getConnection()) {
            for (int i = 1; i <= messageCount; i++) {
                Statement statement = connection.createStatement();
                statement.executeUpdate(String.format(INSERT_SQL, i, "Demo message " + i));
            }
        }
    }

    public static List<String> findAllMessages() throws SQLException {
        try (Connection connection = pool.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_SQL);

            List<String> messages = new ArrayList<>();
            while (resultSet.next()) {
                messages.add(resultSet.getString(CONTENT_COLUMN));
            }

            return messages;
        }
    }

    private static void loadProperties() throws IOException {
        try (InputStream inputStream = DatabaseService.class.getClassLoader().getResourceAsStream("datasource.properties")) {
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
