package packt.vaadin.datacentric.chapter02.webapp;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import packt.vaadin.datacentric.chapter02.api.AppModule;
import packt.vaadin.datacentric.chapter02.api.ApplicationLayout;
import packt.vaadin.datacentric.chapter02.api.TabBasedApplicationLayout;

import java.util.ServiceLoader;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        TabBasedApplicationLayout layout = new TabBasedApplicationLayout("Caption");
        setContent(layout);
        loadModules(layout);
    }

    private void loadModules(ApplicationLayout applicationLayout) {
        ServiceLoader<AppModule> moduleLoader = ServiceLoader.load(AppModule.class);
        moduleLoader.forEach(module -> module.register(applicationLayout));
    }

}
