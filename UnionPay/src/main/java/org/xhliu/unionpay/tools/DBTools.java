package org.xhliu.unionpay.tools;

import org.apache.ibatis.session.SqlSession;

import java.util.function.Function;

public class DBTools {
    private final ThreadLocal<SqlSession> THREAD_LOCAL = new ThreadLocal<>();

    public <T> T runSql(Function<SqlSession, T> function) {
        return runSql(function, false);
    }

    private <T> T runSql(Function<SqlSession, T> function, boolean readOnly) {
        SqlSession sqlSession = THREAD_LOCAL.get();
        if (null == sqlSession) {
            sqlSession = readOnly ? Global.DB_RO.openSession() : Global.DB.openSession();
            THREAD_LOCAL.set(sqlSession);
            try {
                T t = function.apply(sqlSession);
                sqlSession.commit();
                return t;
            } catch (Exception e) {
                sqlSession.rollback(true);
                throw new RuntimeException(e);
            } finally {
                THREAD_LOCAL.remove();
                try {
                    sqlSession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                return function.apply(sqlSession);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
