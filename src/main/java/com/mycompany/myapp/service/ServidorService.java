package com.mycompany.myapp.service;

import com.mycompany.myapp.repository.ServidorRepository;
import com.mycompany.myapp.service.dto.ServidorDTO;
import com.mycompany.myapp.service.mapper.ServidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Servidor}.
 */
@Service
@Transactional
public class ServidorService {

    private final Logger log = LoggerFactory.getLogger(ServidorService.class);

    private final ServidorRepository servidorRepository;

    private final ServidorMapper servidorMapper;

    public ServidorService(ServidorRepository servidorRepository, ServidorMapper servidorMapper) {
        this.servidorRepository = servidorRepository;
        this.servidorMapper = servidorMapper;
    }

    /**
     * Save a servidor.
     *
     * @param servidorDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ServidorDTO> save(ServidorDTO servidorDTO) {
        log.debug("Request to save Servidor : {}", servidorDTO);
        return servidorRepository.save(servidorMapper.toEntity(servidorDTO)).map(servidorMapper::toDto);
    }

    /**
     * Update a servidor.
     *
     * @param servidorDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ServidorDTO> update(ServidorDTO servidorDTO) {
        log.debug("Request to update Servidor : {}", servidorDTO);
        return servidorRepository.save(servidorMapper.toEntity(servidorDTO)).map(servidorMapper::toDto);
    }

    /**
     * Partially update a servidor.
     *
     * @param servidorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<ServidorDTO> partialUpdate(ServidorDTO servidorDTO) {
        log.debug("Request to partially update Servidor : {}", servidorDTO);

        return servidorRepository
            .findById(servidorDTO.getId())
            .map(existingServidor -> {
                servidorMapper.partialUpdate(existingServidor, servidorDTO);

                return existingServidor;
            })
            .flatMap(servidorRepository::save)
            .map(servidorMapper::toDto);
    }

    /**
     * Get all the servidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ServidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servidors");
        return servidorRepository.findAllBy(pageable).map(servidorMapper::toDto);
    }

    /**
     * Get all the servidors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Flux<ServidorDTO> findAllWithEagerRelationships(Pageable pageable) {
        return servidorRepository.findAllWithEagerRelationships(pageable).map(servidorMapper::toDto);
    }

    /**
     *  Get all the servidors where Setor is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<ServidorDTO> findAllWhereSetorIsNull() {
        log.debug("Request to get all servidors where Setor is null");
        return servidorRepository.findAllWhereSetorIsNull().map(servidorMapper::toDto);
    }

    /**
     * Returns the number of servidors available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return servidorRepository.count();
    }

    /**
     * Get one servidor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<ServidorDTO> findOne(Long id) {
        log.debug("Request to get Servidor : {}", id);
        return servidorRepository.findOneWithEagerRelationships(id).map(servidorMapper::toDto);
    }

    /**
     * Delete the servidor by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Servidor : {}", id);
        return servidorRepository.deleteById(id);
    }
}
