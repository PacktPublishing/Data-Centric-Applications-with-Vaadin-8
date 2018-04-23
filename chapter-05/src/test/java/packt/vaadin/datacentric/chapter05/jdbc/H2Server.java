package packt.vaadin.datacentric.chapter05.jdbc;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2Server extends Server {

    public static void main(String... args) throws SQLException {
        new H2Server().runTool(args);
    }

}
