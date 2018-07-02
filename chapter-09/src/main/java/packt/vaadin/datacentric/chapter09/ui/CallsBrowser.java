package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import packt.vaadin.datacentric.chapter09.domain.Call;
import packt.vaadin.datacentric.chapter09.domain.CallRepository;

/**
 * @author Alejandro Duarte
 */
public class CallsBrowser extends Composite<VerticalLayout> {

    public CallsBrowser() {
        TextField filter = new TextField();
        filter.setPlaceholder("Client / Phone / City");
        filter.focus();

        Button search = new Button(VaadinIcon.SEARCH.create());
        Button clear = new Button(VaadinIcon.CLOSE_SMALL.create());

        filter.addKeyPressListener(Key.ENTER, e -> search.click());

        HorizontalLayout filterLayout = new HorizontalLayout(filter, search, clear);
        filterLayout.setMargin(false);
        filterLayout.setPadding(false);

        Span countLabel = new Span();
        countLabel.getElement().setAttribute("theme", "font-size-s");

        HorizontalLayout headerLayout = new HorizontalLayout(filterLayout, countLabel);
        headerLayout.setMargin(false);
        headerLayout.setPadding(false);
        headerLayout.setVerticalComponentAlignment(FlexComponent.Alignment.CENTER, countLabel);

        DataProvider<Call, Void> dataProvider = DataProvider.fromFilteringCallbacks(
                query -> CallRepository.find(query.getOffset(), query.getLimit(), filter.getValue(), DataUtils.getOrderMap(query)).stream(),
                query -> {
                    int count = CallRepository.count(filter.getValue());
                    countLabel.setText(count + " calls found");
                    return count;
                }
        );

        Grid<Call> grid = new Grid<>(Call.class);
        grid.setColumns("id", "client", "phoneNumber", "city", "startTime", "duration", "status");
        grid.setColumnReorderingAllowed(true);
        grid.setDataProvider(dataProvider);
        grid.setSizeFull();

        search.addClickListener(e -> dataProvider.refreshAll());

        clear.addClickListener(e -> {
            filter.clear();
            dataProvider.refreshAll();
        });

        getContent().add(headerLayout, grid);
        getContent().expand(grid);
        getContent().setMargin(false);
    }

}
