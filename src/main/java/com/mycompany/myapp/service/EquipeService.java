package com.mycompany.myapp.service;

import com.mycompany.myapp.repository.EquipeRepository;
import com.mycompany.myapp.service.dto.EquipeDTO;
import com.mycompany.myapp.service.mapper.EquipeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Equipe}.
 */
@Service
@Transactional
public class EquipeService {

    private final Logger log = LoggerFactory.getLogger(EquipeService.class);

    private final EquipeRepository equipeRepository;

    private final EquipeMapper equipeMapper;

    public EquipeService(EquipeRepository equipeRepository, EquipeMapper equipeMapper) {
        this.equipeRepository = equipeRepository;
        this.equipeMapper = equipeMapper;
    }

    /**
     * Save a equipe.
     *
     * @param equipeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<EquipeDTO> save(EquipeDTO equipeDTO) {
        log.debug("Request to save Equipe : {}", equipeDTO);
        return equipeRepository.save(equipeMapper.toEntity(equipeDTO)).map(equipeMapper::toDto);
    }

    /**
     * Update a equipe.
     *
     * @param equipeDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<EquipeDTO> update(EquipeDTO equipeDTO) {
        log.debug("Request to update Equipe : {}", equipeDTO);
        return equipeRepository.save(equipeMapper.toEntity(equipeDTO)).map(equipeMapper::toDto);
    }

    /**
     * Partially update a equipe.
     *
     * @param equipeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<EquipeDTO> partialUpdate(EquipeDTO equipeDTO) {
        log.debug("Request to partially update Equipe : {}", equipeDTO);

        return equipeRepository
            .findById(equipeDTO.getId())
            .map(existingEquipe -> {
                equipeMapper.partialUpdate(existingEquipe, equipeDTO);

                return existingEquipe;
            })
            .flatMap(equipeRepository::save)
            .map(equipeMapper::toDto);
    }

    /**
     * Get all the equipes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<EquipeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Equipes");
        return equipeRepository.findAllBy(pageable).map(equipeMapper::toDto);
    }

    /**
     * Returns the number of equipes available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return equipeRepository.count();
    }

    /**
     * Get one equipe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<EquipeDTO> findOne(Long id) {
        log.debug("Request to get Equipe : {}", id);
        return equipeRepository.findById(id).map(equipeMapper::toDto);
    }

    /**
     * Delete the equipe by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Equipe : {}", id);
        return equipeRepository.deleteById(id);
    }
}
