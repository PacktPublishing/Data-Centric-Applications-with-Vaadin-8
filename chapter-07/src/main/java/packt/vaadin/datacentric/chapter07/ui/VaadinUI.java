package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.router.Route;
import org.vaadin.tabs.PagedTabs;

/**
 * @author Alejandro Duarte
 */
@Route("")
public class VaadinUI extends Composite<PagedTabs> {

    public VaadinUI() {
        getContent().setSizeFull();

        // Editable Grids are not available in Vaadin 10.0.1
        addTab(new CustomCrud());
        addTab(new CrudAddOn());
    }

    private void addTab(Component content) {
        getContent().add(content, content.getClass().getSimpleName());
    }

}
