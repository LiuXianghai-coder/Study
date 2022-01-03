package org.xhliu.unionpay.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhliu.unionpay.http.DQiangpayController;

import static fi.iki.elonen.NanoHTTPD.IHTTPSession;

public class DemoController implements DQiangpayController {
    private final static Logger log = LoggerFactory.getLogger(DemoController.class);

    public String demo(IHTTPSession session) {
        log.info(session.getUri());
        return "Hello Demo";
    }

    public String redirection(IHTTPSession session) {
        log.info(session.getUri());
        return "https://www.google.com";
    }
}
