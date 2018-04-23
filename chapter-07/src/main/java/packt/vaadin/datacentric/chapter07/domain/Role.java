package packt.vaadin.datacentric.chapter07.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Alejandro Duarte
 */
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Boolean module1Authorized;

    private Boolean accessModule2;

    @Override
    public String toString() {
        return name;
    }

}
