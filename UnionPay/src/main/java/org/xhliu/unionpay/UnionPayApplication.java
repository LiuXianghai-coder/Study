package org.xhliu.unionpay;

import fi.iki.elonen.NanoHTTPD;
import org.xhliu.unionpay.http.DBHttp;
import org.xhliu.unionpay.http.Http;

import java.io.IOException;

public class UnionPayApplication {
    public static void main(String[] args) throws IOException {
//        NanoHTTPD http = new Http(8000, "org.xhliu.unionpay.api", true);
        NanoHTTPD http = new DBHttp(8000);
        http.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }
}
