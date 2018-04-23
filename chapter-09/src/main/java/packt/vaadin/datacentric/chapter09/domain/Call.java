package packt.vaadin.datacentric.chapter09.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author Alejandro Duarte
 */
@Entity
@Table(indexes = {
        @Index(name = "client_index", columnList = "client"),
        @Index(name = "phoneNumber_index", columnList = "phoneNumber"),
        @Index(name = "city_index", columnList = "city")
})
@Data
public class Call {

    @Id
    @GeneratedValue
    private Long id;

    private String client;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private City city;

    private LocalDateTime startTime;

    private Integer duration;

    @Enumerated(EnumType.STRING)
    private Status status;

}
