package org.xhliu.unionpay.tools;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class BaseUtils {
    public final static ObjectMapper mapper = new ObjectMapper();

    public static Map<Object, Object> asMap(Object ...params) {
        int len = params.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("key 和 value 必须成对出现");
        }

        Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < len; i += 2) {
            map.put(params[i], params[i + 1]);
        }

        return map;
    }
}
