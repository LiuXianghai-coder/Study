package org.xhliu.kafkaexample;

import java.nio.charset.StandardCharsets;

public class KafkaApplication {
    public static void main(String[] args) {
        byte[] bytes = {
                123, 34, 105, 100, 34, 58, 48,
                44, 34, 98, 111, 100, 121, 34, 58, 34, 66, 76, 79, 67, 75, 73,
                78, 71, 95, 77, 83, 71, 95, 83, 80, 82, 73, 78, 71, 66, 79, 79,
                84, 95, 48, 34, 125
        };
        int n = bytes.length;
        char[] data = new char[(int) Math.ceil(n * 1.0 / 2)];
        for (int i = 0, j = 0; i < n; i += 2, j++) {
            char ch = 0x0;
            ch |= (bytes[i] << 8);
            if (i + 1 < n) ch |= bytes[i + 1];
            data[j] = ch;
        }

        String str = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(String.valueOf(data).toCharArray());
        System.out.println(str);
    }
}
