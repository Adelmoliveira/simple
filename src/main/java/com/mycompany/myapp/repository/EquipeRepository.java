package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Equipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Equipe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipeRepository extends ReactiveCrudRepository<Equipe, Long>, EquipeRepositoryInternal {
    Flux<Equipe> findAllBy(Pageable pageable);

    @Override
    <S extends Equipe> Mono<S> save(S entity);

    @Override
    Flux<Equipe> findAll();

    @Override
    Mono<Equipe> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface EquipeRepositoryInternal {
    <S extends Equipe> Mono<S> save(S entity);

    Flux<Equipe> findAllBy(Pageable pageable);

    Flux<Equipe> findAll();

    Mono<Equipe> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Equipe> findAllBy(Pageable pageable, Criteria criteria);
}
