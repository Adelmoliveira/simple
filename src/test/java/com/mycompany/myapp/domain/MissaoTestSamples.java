package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MissaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Missao getMissaoSample1() {
        return new Missao().id(1L).nomeMissao("nomeMissao1").quantidadeDiaria(1L).quantidadeEquipe(1);
    }

    public static Missao getMissaoSample2() {
        return new Missao().id(2L).nomeMissao("nomeMissao2").quantidadeDiaria(2L).quantidadeEquipe(2);
    }

    public static Missao getMissaoRandomSampleGenerator() {
        return new Missao()
            .id(longCount.incrementAndGet())
            .nomeMissao(UUID.randomUUID().toString())
            .quantidadeDiaria(longCount.incrementAndGet())
            .quantidadeEquipe(intCount.incrementAndGet());
    }
}
