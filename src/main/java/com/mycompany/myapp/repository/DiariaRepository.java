package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Diaria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Diaria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiariaRepository extends ReactiveCrudRepository<Diaria, Long>, DiariaRepositoryInternal {
    @Query("SELECT * FROM diaria entity WHERE entity.missao_id = :id")
    Flux<Diaria> findByMissao(Long id);

    @Query("SELECT * FROM diaria entity WHERE entity.missao_id IS NULL")
    Flux<Diaria> findAllWhereMissaoIsNull();

    @Override
    <S extends Diaria> Mono<S> save(S entity);

    @Override
    Flux<Diaria> findAll();

    @Override
    Mono<Diaria> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface DiariaRepositoryInternal {
    <S extends Diaria> Mono<S> save(S entity);

    Flux<Diaria> findAllBy(Pageable pageable);

    Flux<Diaria> findAll();

    Mono<Diaria> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Diaria> findAllBy(Pageable pageable, Criteria criteria);
}
