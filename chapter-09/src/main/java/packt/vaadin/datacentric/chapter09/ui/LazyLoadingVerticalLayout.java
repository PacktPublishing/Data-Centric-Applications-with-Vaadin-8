package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import packt.vaadin.datacentric.chapter09.domain.Call;
import packt.vaadin.datacentric.chapter09.domain.CallRepository;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alejandro Duarte.
 */
public class LazyLoadingVerticalLayout extends Composite<VerticalLayout> {

    private VerticalLayout content = new VerticalLayout();
    private Button button = new Button("Load more...");

    private int offset;
    private int pageSize;

    public LazyLoadingVerticalLayout(int pageSize) {
        this.pageSize = pageSize;

        getContent().add(content, button);
        getContent().setMargin(false);
        getContent().setPadding(false);

        button.addClickListener(e -> loadMore());
        loadMore();
    }

    public void loadMore() {
        List<Call> calls = CallRepository.find(offset, pageSize, "", new HashMap<>());

        if (calls.size() < pageSize) {
            button.setVisible(false);
        }

        calls.stream()
                .map(call -> new Span(call.toString()))
                .forEach(content::add);

        offset += pageSize;
    }

}
