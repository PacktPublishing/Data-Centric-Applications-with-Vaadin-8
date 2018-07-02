package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * @author Alejandro Duarte
 */
@PageTitle("Call Browser")
@Route("")
public class VaadinUI extends Composite<VerticalLayout> {

    public VaadinUI() {
        CallsBrowser callsBrowser = new CallsBrowser();
        getContent().add(callsBrowser);
        getContent().expand(callsBrowser);
        getContent().setSizeFull();

        //LazyLoadingVerticalLayout lazyLoadingVerticalLayout = new LazyLoadingVerticalLayout(10);
        //getContent().add(lazyLoadingVerticalLayout);
        //getContent().expand();
    }

}
