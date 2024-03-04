package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Servidor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Servidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServidorRepository extends ReactiveCrudRepository<Servidor, Long>, ServidorRepositoryInternal {
    Flux<Servidor> findAllBy(Pageable pageable);

    @Override
    Mono<Servidor> findOneWithEagerRelationships(Long id);

    @Override
    Flux<Servidor> findAllWithEagerRelationships();

    @Override
    Flux<Servidor> findAllWithEagerRelationships(Pageable page);

    @Query(
        "SELECT entity.* FROM servidor entity JOIN rel_servidor__equipes joinTable ON entity.id = joinTable.equipes_id WHERE joinTable.equipes_id = :id"
    )
    Flux<Servidor> findByEquipes(Long id);

    @Query("SELECT * FROM servidor entity WHERE entity.id not in (select setor_id from setor)")
    Flux<Servidor> findAllWhereSetorIsNull();

    @Override
    <S extends Servidor> Mono<S> save(S entity);

    @Override
    Flux<Servidor> findAll();

    @Override
    Mono<Servidor> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ServidorRepositoryInternal {
    <S extends Servidor> Mono<S> save(S entity);

    Flux<Servidor> findAllBy(Pageable pageable);

    Flux<Servidor> findAll();

    Mono<Servidor> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Servidor> findAllBy(Pageable pageable, Criteria criteria);

    Mono<Servidor> findOneWithEagerRelationships(Long id);

    Flux<Servidor> findAllWithEagerRelationships();

    Flux<Servidor> findAllWithEagerRelationships(Pageable page);

    Mono<Void> deleteById(Long id);
}
