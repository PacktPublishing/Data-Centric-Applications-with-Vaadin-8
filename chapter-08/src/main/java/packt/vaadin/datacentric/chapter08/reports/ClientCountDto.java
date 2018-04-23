package packt.vaadin.datacentric.chapter08.reports;

import lombok.Data;
import packt.vaadin.datacentric.chapter08.domain.City;

/**
 * @author Alejandro Duarte
 */
@Data
public class ClientCountDto {

    private String client;

    private City city;

    private String phoneNumber;

    private int calls;

}
