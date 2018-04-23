package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Alejandro Duarte
 */
@Title("Call Browser")
public class VaadinUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.addComponentsAndExpand(new CallsBrowser());
        //mainLayout.addComponent(new LazyLoadingVerticalLayout(10));
        setContent(mainLayout);
    }

}
