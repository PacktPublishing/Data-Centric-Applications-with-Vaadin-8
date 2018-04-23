package packt.vaadin.datacentric.chapter05.jdbc;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        try {
            VerticalLayout layout = new VerticalLayout();
            setContent(layout);
            DatabaseService.findAllMessages().forEach(
                    m -> layout.addComponent(new Label(m))
            );

        } catch (SQLException e) {
            Notification.show("Database error. See server log.", Notification.Type.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
