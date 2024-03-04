package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.SetorDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Setor}.
 */
public interface SetorService {
    /**
     * Save a setor.
     *
     * @param setorDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<SetorDTO> save(SetorDTO setorDTO);

    /**
     * Updates a setor.
     *
     * @param setorDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<SetorDTO> update(SetorDTO setorDTO);

    /**
     * Partially updates a setor.
     *
     * @param setorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<SetorDTO> partialUpdate(SetorDTO setorDTO);

    /**
     * Get all the setors.
     *
     * @return the list of entities.
     */
    Flux<SetorDTO> findAll();

    /**
     * Returns the number of setors available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" setor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<SetorDTO> findOne(Long id);

    /**
     * Delete the "id" setor.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
