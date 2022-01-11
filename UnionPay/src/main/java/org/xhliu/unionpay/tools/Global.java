package org.xhliu.unionpay.tools;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class Global {
    static SqlSessionFactory sqlSessionFactory;

    public final static DBTools DB_TOOLS = new DBTools();

    static {
        try (
                InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        ) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DB_RO {
        public static SqlSession openSession() {
            return sqlSessionFactory.openSession(false);
        }
    }

    public static class DB {
        public static SqlSession openSession() {
            return sqlSessionFactory.openSession(false);
        }
    }
}
