package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DiariaDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Diaria}.
 */
public interface DiariaService {
    /**
     * Save a diaria.
     *
     * @param diariaDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<DiariaDTO> save(DiariaDTO diariaDTO);

    /**
     * Updates a diaria.
     *
     * @param diariaDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<DiariaDTO> update(DiariaDTO diariaDTO);

    /**
     * Partially updates a diaria.
     *
     * @param diariaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<DiariaDTO> partialUpdate(DiariaDTO diariaDTO);

    /**
     * Get all the diarias.
     *
     * @return the list of entities.
     */
    Flux<DiariaDTO> findAll();

    /**
     * Returns the number of diarias available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" diaria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<DiariaDTO> findOne(Long id);

    /**
     * Delete the "id" diaria.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
