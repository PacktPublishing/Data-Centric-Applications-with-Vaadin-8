package packt.vaadin.datacentric.chapter08.reports;

import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Alejandro Duarte
 */
public interface ReportsMapper {

    List<CallDto> findCallsBefore(LocalDateTime time);

    List<CapacityDto> countCallsBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    List<ClientCountDto> countCallsByClientBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}
