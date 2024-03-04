package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Missao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Missao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MissaoRepository extends ReactiveCrudRepository<Missao, Long>, MissaoRepositoryInternal {
    Flux<Missao> findAllBy(Pageable pageable);

    @Query("SELECT * FROM missao entity WHERE entity.municipio_id = :id")
    Flux<Missao> findByMunicipio(Long id);

    @Query("SELECT * FROM missao entity WHERE entity.municipio_id IS NULL")
    Flux<Missao> findAllWhereMunicipioIsNull();

    @Override
    <S extends Missao> Mono<S> save(S entity);

    @Override
    Flux<Missao> findAll();

    @Override
    Mono<Missao> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface MissaoRepositoryInternal {
    <S extends Missao> Mono<S> save(S entity);

    Flux<Missao> findAllBy(Pageable pageable);

    Flux<Missao> findAll();

    Mono<Missao> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Missao> findAllBy(Pageable pageable, Criteria criteria);
}
