package packt.vaadin.datacentric.chapter09.ui;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alejandro Duarte
 */
public class DataUtils {

    public static <T, F> Map<String, Boolean> getOrderMap(Query<T, F> query) {
        Map<String, Boolean> map = new LinkedHashMap<>();

        for (QuerySortOrder order : query.getSortOrders()) {
            String property = order.getSorted();
            boolean isAscending = SortDirection.ASCENDING.equals(order.getDirection());
            map.put(property, isAscending);
        }

        return map;
    }

}
