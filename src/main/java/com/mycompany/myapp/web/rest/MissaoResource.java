package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MissaoRepository;
import com.mycompany.myapp.service.MissaoService;
import com.mycompany.myapp.service.dto.MissaoDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Missao}.
 */
@RestController
@RequestMapping("/api/missaos")
public class MissaoResource {

    private final Logger log = LoggerFactory.getLogger(MissaoResource.class);

    private static final String ENTITY_NAME = "missao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MissaoService missaoService;

    private final MissaoRepository missaoRepository;

    public MissaoResource(MissaoService missaoService, MissaoRepository missaoRepository) {
        this.missaoService = missaoService;
        this.missaoRepository = missaoRepository;
    }

    /**
     * {@code POST  /missaos} : Create a new missao.
     *
     * @param missaoDTO the missaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new missaoDTO, or with status {@code 400 (Bad Request)} if the missao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<MissaoDTO>> createMissao(@Valid @RequestBody MissaoDTO missaoDTO) throws URISyntaxException {
        log.debug("REST request to save Missao : {}", missaoDTO);
        if (missaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new missao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return missaoService
            .save(missaoDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/missaos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /missaos/:id} : Updates an existing missao.
     *
     * @param id the id of the missaoDTO to save.
     * @param missaoDTO the missaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missaoDTO,
     * or with status {@code 400 (Bad Request)} if the missaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the missaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<MissaoDTO>> updateMissao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MissaoDTO missaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Missao : {}, {}", id, missaoDTO);
        if (missaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, missaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return missaoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return missaoService
                    .update(missaoDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /missaos/:id} : Partial updates given fields of an existing missao, field will ignore if it is null
     *
     * @param id the id of the missaoDTO to save.
     * @param missaoDTO the missaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missaoDTO,
     * or with status {@code 400 (Bad Request)} if the missaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the missaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the missaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<MissaoDTO>> partialUpdateMissao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MissaoDTO missaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Missao partially : {}, {}", id, missaoDTO);
        if (missaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, missaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return missaoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<MissaoDTO> result = missaoService.partialUpdate(missaoDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity
                            .ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /missaos} : get all the missaos.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of missaos in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<MissaoDTO>>> getAllMissaos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        log.debug("REST request to get a page of Missaos");
        return missaoService
            .countAll()
            .zipWith(missaoService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity
                    .ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /missaos/:id} : get the "id" missao.
     *
     * @param id the id of the missaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the missaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<MissaoDTO>> getMissao(@PathVariable("id") Long id) {
        log.debug("REST request to get Missao : {}", id);
        Mono<MissaoDTO> missaoDTO = missaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(missaoDTO);
    }

    /**
     * {@code DELETE  /missaos/:id} : delete the "id" missao.
     *
     * @param id the id of the missaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMissao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Missao : {}", id);
        return missaoService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity
                        .noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
