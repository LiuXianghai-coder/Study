package com.example.uruleexample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

@SpringBootTest
class URuleExampleApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        System.out.println(Math.ceil(0.5));
    }

    public static void main(String[] args) {
        Set<int[]> set = new HashSet<>();
        set.add(new int[]{1, 2});

        System.out.println(set.contains(new int[]{1, 2}));
    }
}
