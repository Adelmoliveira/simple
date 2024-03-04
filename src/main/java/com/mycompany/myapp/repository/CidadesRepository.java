package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cidades;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Cidades entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CidadesRepository extends ReactiveCrudRepository<Cidades, Long>, CidadesRepositoryInternal {
    @Query("SELECT * FROM cidades entity WHERE entity.id not in (select missao_id from missao)")
    Flux<Cidades> findAllWhereMissaoIsNull();

    @Override
    <S extends Cidades> Mono<S> save(S entity);

    @Override
    Flux<Cidades> findAll();

    @Override
    Mono<Cidades> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface CidadesRepositoryInternal {
    <S extends Cidades> Mono<S> save(S entity);

    Flux<Cidades> findAllBy(Pageable pageable);

    Flux<Cidades> findAll();

    Mono<Cidades> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Cidades> findAllBy(Pageable pageable, Criteria criteria);
}
