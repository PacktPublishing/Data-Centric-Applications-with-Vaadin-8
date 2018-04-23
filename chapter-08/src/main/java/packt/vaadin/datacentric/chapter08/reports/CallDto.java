package packt.vaadin.datacentric.chapter08.reports;

import lombok.Data;
import packt.vaadin.datacentric.chapter08.domain.City;
import packt.vaadin.datacentric.chapter08.domain.Status;

import java.time.LocalDateTime;

/**
 * @author Alejandro Duarte
 */
@Data
public class CallDto {

    private String client;

    private String phoneNumber;

    private City city;

    private LocalDateTime startTime;

    private Integer duration;

    private Status status;

}
