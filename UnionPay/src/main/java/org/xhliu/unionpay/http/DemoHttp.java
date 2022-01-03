package org.xhliu.unionpay.http;

import fi.iki.elonen.NanoHTTPD;

import java.util.List;
import java.util.Map;

public class DemoHttp extends NanoHTTPD {
    public DemoHttp(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String msg = "<html><body><h1>Hello Server</h1>";
        Map<String, List<String>> map = session.getParameters();
        if (map.get("username") == null) {
            msg += "<form action='?' method='get'>\n<p>Your Name: <input type='text' name='username' />";
        } else {
            msg += "<p>Hello, " + map.get("username").toString() + "!</p>";
        }

        msg += "</body></html>";
        return newFixedLengthResponse(msg);
    }

    public DemoHttp(String hostname, int port) {
        super(hostname, port);
    }
}
