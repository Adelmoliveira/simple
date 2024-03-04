package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DiariaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Diaria getDiariaSample1() {
        return new Diaria().id(1L).cidade("cidade1");
    }

    public static Diaria getDiariaSample2() {
        return new Diaria().id(2L).cidade("cidade2");
    }

    public static Diaria getDiariaRandomSampleGenerator() {
        return new Diaria().id(longCount.incrementAndGet()).cidade(UUID.randomUUID().toString());
    }
}
