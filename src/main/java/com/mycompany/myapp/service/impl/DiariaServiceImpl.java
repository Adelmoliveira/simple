package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.DiariaRepository;
import com.mycompany.myapp.service.DiariaService;
import com.mycompany.myapp.service.dto.DiariaDTO;
import com.mycompany.myapp.service.mapper.DiariaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Diaria}.
 */
@Service
@Transactional
public class DiariaServiceImpl implements DiariaService {

    private final Logger log = LoggerFactory.getLogger(DiariaServiceImpl.class);

    private final DiariaRepository diariaRepository;

    private final DiariaMapper diariaMapper;

    public DiariaServiceImpl(DiariaRepository diariaRepository, DiariaMapper diariaMapper) {
        this.diariaRepository = diariaRepository;
        this.diariaMapper = diariaMapper;
    }

    @Override
    public Mono<DiariaDTO> save(DiariaDTO diariaDTO) {
        log.debug("Request to save Diaria : {}", diariaDTO);
        return diariaRepository.save(diariaMapper.toEntity(diariaDTO)).map(diariaMapper::toDto);
    }

    @Override
    public Mono<DiariaDTO> update(DiariaDTO diariaDTO) {
        log.debug("Request to update Diaria : {}", diariaDTO);
        return diariaRepository.save(diariaMapper.toEntity(diariaDTO)).map(diariaMapper::toDto);
    }

    @Override
    public Mono<DiariaDTO> partialUpdate(DiariaDTO diariaDTO) {
        log.debug("Request to partially update Diaria : {}", diariaDTO);

        return diariaRepository
            .findById(diariaDTO.getId())
            .map(existingDiaria -> {
                diariaMapper.partialUpdate(existingDiaria, diariaDTO);

                return existingDiaria;
            })
            .flatMap(diariaRepository::save)
            .map(diariaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<DiariaDTO> findAll() {
        log.debug("Request to get all Diarias");
        return diariaRepository.findAll().map(diariaMapper::toDto);
    }

    public Mono<Long> countAll() {
        return diariaRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<DiariaDTO> findOne(Long id) {
        log.debug("Request to get Diaria : {}", id);
        return diariaRepository.findById(id).map(diariaMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Diaria : {}", id);
        return diariaRepository.deleteById(id);
    }
}
