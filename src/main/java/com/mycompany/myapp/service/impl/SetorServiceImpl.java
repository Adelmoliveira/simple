package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.SetorRepository;
import com.mycompany.myapp.service.SetorService;
import com.mycompany.myapp.service.dto.SetorDTO;
import com.mycompany.myapp.service.mapper.SetorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Setor}.
 */
@Service
@Transactional
public class SetorServiceImpl implements SetorService {

    private final Logger log = LoggerFactory.getLogger(SetorServiceImpl.class);

    private final SetorRepository setorRepository;

    private final SetorMapper setorMapper;

    public SetorServiceImpl(SetorRepository setorRepository, SetorMapper setorMapper) {
        this.setorRepository = setorRepository;
        this.setorMapper = setorMapper;
    }

    @Override
    public Mono<SetorDTO> save(SetorDTO setorDTO) {
        log.debug("Request to save Setor : {}", setorDTO);
        return setorRepository.save(setorMapper.toEntity(setorDTO)).map(setorMapper::toDto);
    }

    @Override
    public Mono<SetorDTO> update(SetorDTO setorDTO) {
        log.debug("Request to update Setor : {}", setorDTO);
        return setorRepository.save(setorMapper.toEntity(setorDTO)).map(setorMapper::toDto);
    }

    @Override
    public Mono<SetorDTO> partialUpdate(SetorDTO setorDTO) {
        log.debug("Request to partially update Setor : {}", setorDTO);

        return setorRepository
            .findById(setorDTO.getId())
            .map(existingSetor -> {
                setorMapper.partialUpdate(existingSetor, setorDTO);

                return existingSetor;
            })
            .flatMap(setorRepository::save)
            .map(setorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<SetorDTO> findAll() {
        log.debug("Request to get all Setors");
        return setorRepository.findAll().map(setorMapper::toDto);
    }

    public Mono<Long> countAll() {
        return setorRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<SetorDTO> findOne(Long id) {
        log.debug("Request to get Setor : {}", id);
        return setorRepository.findById(id).map(setorMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Setor : {}", id);
        return setorRepository.deleteById(id);
    }
}
