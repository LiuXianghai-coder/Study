package org.xhliu.unionpay;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.nio.charset.StandardCharsets.UTF_8;

public class RSA {
    static void test(String text) {
        int BIT_LENGTH = 2048;

        Random rand = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH / 2, rand);
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH / 2, rand);

        BigInteger n = p.multiply(q);

        // 计算 r
        BigInteger phi = p.subtract(BigInteger.ONE)
                .multiply(q.subtract(BigInteger.ONE));

        BigInteger e = TWO;

        // 找到合适的 e
        while (e.compareTo(phi) < 0) {
            if (e.gcd(phi).intValue() == 1) break;
            e = e.add(ONE);
        }

        BigInteger d = e.modInverse(phi); // 获得 e 的模反元素

        BigInteger msg = new BigInteger(text.getBytes(UTF_8)); // 将消息转换为对应的整数
        BigInteger enc = msg.modPow(e, n);

        System.out.println("raw=" + text);
        System.out.println("enc=" + enc);
        BigInteger dec = enc.modPow(d, n);
        System.out.println("dec=" + new String(dec.toByteArray(), UTF_8));
    }

    public static void main(String[] args) {
        String text = "This is a simple text";

        test(text);
    }
}
