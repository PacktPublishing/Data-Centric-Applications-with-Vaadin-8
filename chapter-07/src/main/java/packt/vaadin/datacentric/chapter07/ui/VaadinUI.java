package packt.vaadin.datacentric.chapter07.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

/**
 * @author Alejandro Duarte
 */
public class VaadinUI extends UI {

    private TabSheet tabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest request) {
        tabSheet.setSizeFull();
        setContent(tabSheet);

        addTab(new EditableGridCrud());
        addTab(new CustomCrud());
        addTab(new CrudAddOn());
    }

    private void addTab(Component content) {
        tabSheet.addTab(content, content.getClass().getSimpleName());
    }

}
