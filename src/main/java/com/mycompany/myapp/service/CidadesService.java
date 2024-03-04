package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CidadesDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Cidades}.
 */
public interface CidadesService {
    /**
     * Save a cidades.
     *
     * @param cidadesDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<CidadesDTO> save(CidadesDTO cidadesDTO);

    /**
     * Updates a cidades.
     *
     * @param cidadesDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<CidadesDTO> update(CidadesDTO cidadesDTO);

    /**
     * Partially updates a cidades.
     *
     * @param cidadesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<CidadesDTO> partialUpdate(CidadesDTO cidadesDTO);

    /**
     * Get all the cidades.
     *
     * @return the list of entities.
     */
    Flux<CidadesDTO> findAll();

    /**
     * Get all the CidadesDTO where Missao is {@code null}.
     *
     * @return the {@link Flux} of entities.
     */
    Flux<CidadesDTO> findAllWhereMissaoIsNull();

    /**
     * Returns the number of cidades available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" cidades.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<CidadesDTO> findOne(Long id);

    /**
     * Delete the "id" cidades.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
