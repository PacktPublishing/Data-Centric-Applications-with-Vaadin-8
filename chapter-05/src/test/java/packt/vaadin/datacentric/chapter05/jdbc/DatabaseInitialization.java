package packt.vaadin.datacentric.chapter05.jdbc;

/**
 * @author Alejandro Duarte
 */
public class DatabaseInitialization {

    public static void main(String... args) {
        try {
            System.out.println("Initializing database...");

            DatabaseService.init();
            DatabaseService.createSchema();
            DatabaseService.insertDemoData(10);

            System.out.println("Initialization succeeded.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseService.shutdown();
        }
    }

}
