package packt.vaadin.datacentric.chapter02.example.module;

import com.vaadin.ui.Notification;
import packt.vaadin.datacentric.chapter02.api.AppModule;
import packt.vaadin.datacentric.chapter02.api.ApplicationLayout;

/**
 * @author Alejandro Duarte
 */
public class ExampleModule implements AppModule {

    @Override
    public void register(ApplicationLayout layout) {
        ApplicationLayout.MenuOption menuOption = new ApplicationLayout.MenuOption("Example module");
        layout.addMenuOption(menuOption, this::optionClicked);
    }

    private void optionClicked(ApplicationLayout.MenuOption menuOption) {
        Notification.show("It works!", Notification.Type.TRAY_NOTIFICATION);
    }

}
