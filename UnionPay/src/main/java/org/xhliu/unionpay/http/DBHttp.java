package org.xhliu.unionpay.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import fi.iki.elonen.NanoHTTPD;
import org.xhliu.unionpay.entity.OmsOrder;
import org.xhliu.unionpay.tools.BaseUtils;
import org.xhliu.unionpay.tools.Global;

public class DBHttp extends NanoHTTPD {

    public DBHttp(int port) {
        super(port);
    }

    public DBHttp(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        OmsOrder order = Global.DB_TOOLS.runSql(
                sqlSession ->
                        sqlSession.selectOne(
                                "org.xhliu.unionpay.dao.OmsOrderDao.selectByPrimaryKey",
                                BaseUtils.asMap("id", 12L)
                        )
        );

        try {
            return newFixedLengthResponse(
                    BaseUtils.mapper
                            .writerWithDefaultPrettyPrinter()
                            .writeValueAsString(order)
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
