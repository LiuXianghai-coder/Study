package org.xhliu.unionpay.http;

import fi.iki.elonen.NanoHTTPD;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static fi.iki.elonen.NanoHTTPD.Response.Status.OK;
import static fi.iki.elonen.NanoHTTPD.Response.Status.REDIRECT;

public class Http extends NanoHTTPD {
    private final static Logger log = LoggerFactory.getLogger(Http.class);

    private final Map<String, ? extends DQiangpayController> controllers;

    private final boolean allowCrossAccess;

    public Http(int port, final String root, boolean allowCrossAccess) {
        super(port);
        this.allowCrossAccess = allowCrossAccess;

        Reflections reflections = new Reflections(root);
        Set<Class<? extends DQiangpayController>> classSet =
                reflections.getSubTypesOf(DQiangpayController.class);

        controllers = Collections.unmodifiableMap(
                classSet.stream()
                        .collect(
                                Collectors.toMap(
                                        clazz -> clazz.getName()
                                                .replaceAll(root, "")
                                                .replaceAll("\\.", "/"),

                                        (clazz) -> {
                                            try {
                                                return clazz.getConstructor().newInstance();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                return null;
                                            }
                                        }
                                )
                        )
        );
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String controllerUri = uri.substring(0, uri.lastIndexOf("/"));
        String method = uri.substring(uri.lastIndexOf("/") + 1);

        DQiangpayController controller = controllers.get(controllerUri);

        if (controller == null) {
            Optional<? extends Map.Entry<String, ? extends DQiangpayController>> optional =
                    controllers.entrySet()
                            .stream()
                            .filter(entry -> {
                                String registerUrl = entry.getKey();
                                return uri.startsWith(registerUrl);
                            })
                            .max(Comparator.comparing(entry -> entry.getKey().split("/").length));
            if (optional.isEmpty()) {
                log.info("unknown uri mapping={}", uri);
                return newFixedLengthResponse("");
            }

            controller = optional.get().getValue();

            String registerUrl = optional.get().getKey();
            int beginIdx = registerUrl.length();
            int endIdx = uri.indexOf("/", beginIdx + 1);
            method = uri.substring(beginIdx + 1, endIdx > 0 ? endIdx : uri.length());
        }

        log.debug("controller: {}, method: {}", controller, method);
        Response response;

        try {
            java.lang.reflect.Method m = controller.getClass()
                    .getDeclaredMethod(method, IHTTPSession.class);
            String result = (String) m.invoke(controller, session);

            if (result.trim().startsWith("<!DOCTYPE")) {
                response = newFixedLengthResponse(
                        OK,
                        "text/html;charset=utf-8",
                        result
                );
            } else if (result.trim().startsWith("{") && result.trim().endsWith("}")) {
                response = newFixedLengthResponse(OK, "application/json;charset=utf-8", result);
            } else if (result.trim().startsWith("http")) {
                response = newFixedLengthResponse(REDIRECT, "text/plain;charset=utf-8", "");
                response.addHeader("Location", result);
            } else {
                response = newFixedLengthResponse(OK, "text/plain;charset=utf-8", result);
            }

            if (allowCrossAccess) {
                response.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8082");
                response.addHeader("Access-Control-Allow-Method", "GET, POST, PUT, DELETE, PATCH");
                response.addHeader("Access-Control-Allow-Headers", "X-Requested-With, content-type, Authorization");
                response.addHeader("Access-Control-Allow-Credentials", "true");
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return newFixedLengthResponse(
                    OK,
                    "application/json; charset=utf-8",
                    String.format("{\"__error__\":\"%s\"}", e + " " + e.getMessage())
            );
        }
    }
}
