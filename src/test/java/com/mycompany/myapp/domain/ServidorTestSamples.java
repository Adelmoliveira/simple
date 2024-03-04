package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ServidorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Servidor getServidorSample1() {
        return new Servidor().id(1L).nome("nome1").sobreNome("sobreNome1").email("email1").celular("celular1");
    }

    public static Servidor getServidorSample2() {
        return new Servidor().id(2L).nome("nome2").sobreNome("sobreNome2").email("email2").celular("celular2");
    }

    public static Servidor getServidorRandomSampleGenerator() {
        return new Servidor()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .sobreNome(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .celular(UUID.randomUUID().toString());
    }
}
