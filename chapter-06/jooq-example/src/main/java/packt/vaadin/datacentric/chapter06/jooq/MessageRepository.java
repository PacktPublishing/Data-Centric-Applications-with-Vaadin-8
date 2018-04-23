package packt.vaadin.datacentric.chapter06.jooq;

import packt.vaadin.chapter06.jooq.public_.tables.records.MessagesRecord;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static packt.vaadin.chapter06.jooq.public_.Tables.MESSAGES;

/**
 * @author Alejandro Duarte
 */
public class MessageRepository {

    public static List<MessagesRecord> findAll() {
        try {
            return JooqService.runWithDslContext(context ->
                    context.select()
                            .from(MESSAGES)
                            .fetchInto(MessagesRecord.class)
            );

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static void save(MessagesRecord message) {
        try {
            JooqService.runWithDslContext(context ->
                    context.insertInto(MESSAGES, MESSAGES.CONTENT)
                            .values(message.getContent())
                            .execute()
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
