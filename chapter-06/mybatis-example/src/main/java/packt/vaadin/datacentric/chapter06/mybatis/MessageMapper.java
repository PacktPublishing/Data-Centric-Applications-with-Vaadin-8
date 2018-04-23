package packt.vaadin.datacentric.chapter06.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Alejandro Duarte
 */
public interface MessageMapper {

    @Select("SELECT id, content FROM messages")
    List<Message> findAll();

    @Insert("INSERT INTO messages(content) VALUES (#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Message message);

}
