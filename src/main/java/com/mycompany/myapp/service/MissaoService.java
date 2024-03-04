package com.mycompany.myapp.service;

import com.mycompany.myapp.repository.MissaoRepository;
import com.mycompany.myapp.service.dto.MissaoDTO;
import com.mycompany.myapp.service.mapper.MissaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Missao}.
 */
@Service
@Transactional
public class MissaoService {

    private final Logger log = LoggerFactory.getLogger(MissaoService.class);

    private final MissaoRepository missaoRepository;

    private final MissaoMapper missaoMapper;

    public MissaoService(MissaoRepository missaoRepository, MissaoMapper missaoMapper) {
        this.missaoRepository = missaoRepository;
        this.missaoMapper = missaoMapper;
    }

    /**
     * Save a missao.
     *
     * @param missaoDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MissaoDTO> save(MissaoDTO missaoDTO) {
        log.debug("Request to save Missao : {}", missaoDTO);
        return missaoRepository.save(missaoMapper.toEntity(missaoDTO)).map(missaoMapper::toDto);
    }

    /**
     * Update a missao.
     *
     * @param missaoDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<MissaoDTO> update(MissaoDTO missaoDTO) {
        log.debug("Request to update Missao : {}", missaoDTO);
        return missaoRepository.save(missaoMapper.toEntity(missaoDTO)).map(missaoMapper::toDto);
    }

    /**
     * Partially update a missao.
     *
     * @param missaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Mono<MissaoDTO> partialUpdate(MissaoDTO missaoDTO) {
        log.debug("Request to partially update Missao : {}", missaoDTO);

        return missaoRepository
            .findById(missaoDTO.getId())
            .map(existingMissao -> {
                missaoMapper.partialUpdate(existingMissao, missaoDTO);

                return existingMissao;
            })
            .flatMap(missaoRepository::save)
            .map(missaoMapper::toDto);
    }

    /**
     * Get all the missaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<MissaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Missaos");
        return missaoRepository.findAllBy(pageable).map(missaoMapper::toDto);
    }

    /**
     * Returns the number of missaos available.
     * @return the number of entities in the database.
     *
     */
    public Mono<Long> countAll() {
        return missaoRepository.count();
    }

    /**
     * Get one missao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Mono<MissaoDTO> findOne(Long id) {
        log.debug("Request to get Missao : {}", id);
        return missaoRepository.findById(id).map(missaoMapper::toDto);
    }

    /**
     * Delete the missao by id.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    public Mono<Void> delete(Long id) {
        log.debug("Request to delete Missao : {}", id);
        return missaoRepository.deleteById(id);
    }
}
