package org.xhliu.unionpay.http;

import fi.iki.elonen.NanoHTTPD;

public class BeetlHttp extends NanoHTTPD {
    public BeetlHttp(int port) {
        super(port);
    }

    public BeetlHttp(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        return super.serve(session);
    }
}
