package packt.vaadin.datacentric.chapter06.mybatis;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author Alejandro Duarte
 */
public class MessageService {

    public static List<Message> findAll() {
        try (SqlSession session = MyBatisService.getSqlSessionFactory().openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            return mapper.findAll();
        }
    }

    public static void save(Message message) {
        try (SqlSession session = MyBatisService.getSqlSessionFactory().openSession()) {
            MessageMapper mapper = session.getMapper(MessageMapper.class);
            mapper.save(message);
            session.commit();
        }
    }

}
