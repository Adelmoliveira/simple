package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.CidadesRepository;
import com.mycompany.myapp.service.CidadesService;
import com.mycompany.myapp.service.dto.CidadesDTO;
import com.mycompany.myapp.service.mapper.CidadesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Cidades}.
 */
@Service
@Transactional
public class CidadesServiceImpl implements CidadesService {

    private final Logger log = LoggerFactory.getLogger(CidadesServiceImpl.class);

    private final CidadesRepository cidadesRepository;

    private final CidadesMapper cidadesMapper;

    public CidadesServiceImpl(CidadesRepository cidadesRepository, CidadesMapper cidadesMapper) {
        this.cidadesRepository = cidadesRepository;
        this.cidadesMapper = cidadesMapper;
    }

    @Override
    public Mono<CidadesDTO> save(CidadesDTO cidadesDTO) {
        log.debug("Request to save Cidades : {}", cidadesDTO);
        return cidadesRepository.save(cidadesMapper.toEntity(cidadesDTO)).map(cidadesMapper::toDto);
    }

    @Override
    public Mono<CidadesDTO> update(CidadesDTO cidadesDTO) {
        log.debug("Request to update Cidades : {}", cidadesDTO);
        return cidadesRepository.save(cidadesMapper.toEntity(cidadesDTO)).map(cidadesMapper::toDto);
    }

    @Override
    public Mono<CidadesDTO> partialUpdate(CidadesDTO cidadesDTO) {
        log.debug("Request to partially update Cidades : {}", cidadesDTO);

        return cidadesRepository
            .findById(cidadesDTO.getId())
            .map(existingCidades -> {
                cidadesMapper.partialUpdate(existingCidades, cidadesDTO);

                return existingCidades;
            })
            .flatMap(cidadesRepository::save)
            .map(cidadesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<CidadesDTO> findAll() {
        log.debug("Request to get all Cidades");
        return cidadesRepository.findAll().map(cidadesMapper::toDto);
    }

    /**
     *  Get all the cidades where Missao is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<CidadesDTO> findAllWhereMissaoIsNull() {
        log.debug("Request to get all cidades where Missao is null");
        return cidadesRepository.findAllWhereMissaoIsNull().map(cidadesMapper::toDto);
    }

    public Mono<Long> countAll() {
        return cidadesRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<CidadesDTO> findOne(Long id) {
        log.debug("Request to get Cidades : {}", id);
        return cidadesRepository.findById(id).map(cidadesMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Cidades : {}", id);
        return cidadesRepository.deleteById(id);
    }
}
