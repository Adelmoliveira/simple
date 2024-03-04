package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CidadesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Cidades getCidadesSample1() {
        return new Cidades().id(1L);
    }

    public static Cidades getCidadesSample2() {
        return new Cidades().id(2L);
    }

    public static Cidades getCidadesRandomSampleGenerator() {
        return new Cidades().id(longCount.incrementAndGet());
    }
}
