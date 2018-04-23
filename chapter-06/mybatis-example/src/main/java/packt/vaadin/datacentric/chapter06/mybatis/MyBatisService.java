package packt.vaadin.datacentric.chapter06.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * @author Alejandro Duarte
 */
public class MyBatisService {

    private static SqlSessionFactory sqlSessionFactory;

    public static void init() {
        InputStream inputStream = MyBatisService.class.getResourceAsStream("/mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }

}
