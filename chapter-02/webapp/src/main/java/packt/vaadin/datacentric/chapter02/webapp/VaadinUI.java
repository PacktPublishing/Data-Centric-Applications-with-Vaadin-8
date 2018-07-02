package packt.vaadin.datacentric.chapter02.webapp;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import packt.vaadin.datacentric.chapter02.api.AppModule;
import packt.vaadin.datacentric.chapter02.api.ApplicationLayout;
import packt.vaadin.datacentric.chapter02.api.TabBasedApplicationLayout;

import java.util.ServiceLoader;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    public VaadinUI() {
        TabBasedApplicationLayout layout = new TabBasedApplicationLayout("Caption");
        getContent().add(layout);
        getContent().setSizeFull();
        loadModules(layout);
    }

    private void loadModules(ApplicationLayout applicationLayout) {
        ServiceLoader<AppModule> moduleLoader = ServiceLoader.load(AppModule.class);
        moduleLoader.forEach(module -> module.register(applicationLayout));
    }

}
