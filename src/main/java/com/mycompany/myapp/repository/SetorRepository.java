package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Setor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Setor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SetorRepository extends ReactiveCrudRepository<Setor, Long>, SetorRepositoryInternal {
    @Query("SELECT * FROM setor entity WHERE entity.servidor_id = :id")
    Flux<Setor> findByServidor(Long id);

    @Query("SELECT * FROM setor entity WHERE entity.servidor_id IS NULL")
    Flux<Setor> findAllWhereServidorIsNull();

    @Override
    <S extends Setor> Mono<S> save(S entity);

    @Override
    Flux<Setor> findAll();

    @Override
    Mono<Setor> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface SetorRepositoryInternal {
    <S extends Setor> Mono<S> save(S entity);

    Flux<Setor> findAllBy(Pageable pageable);

    Flux<Setor> findAll();

    Mono<Setor> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Setor> findAllBy(Pageable pageable, Criteria criteria);
}
