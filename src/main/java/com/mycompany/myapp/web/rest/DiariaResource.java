package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.DiariaRepository;
import com.mycompany.myapp.service.DiariaService;
import com.mycompany.myapp.service.dto.DiariaDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Diaria}.
 */
@RestController
@RequestMapping("/api/diarias")
public class DiariaResource {

    private final Logger log = LoggerFactory.getLogger(DiariaResource.class);

    private static final String ENTITY_NAME = "diaria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiariaService diariaService;

    private final DiariaRepository diariaRepository;

    public DiariaResource(DiariaService diariaService, DiariaRepository diariaRepository) {
        this.diariaService = diariaService;
        this.diariaRepository = diariaRepository;
    }

    /**
     * {@code POST  /diarias} : Create a new diaria.
     *
     * @param diariaDTO the diariaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new diariaDTO, or with status {@code 400 (Bad Request)} if the diaria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<DiariaDTO>> createDiaria(@Valid @RequestBody DiariaDTO diariaDTO) throws URISyntaxException {
        log.debug("REST request to save Diaria : {}", diariaDTO);
        if (diariaDTO.getId() != null) {
            throw new BadRequestAlertException("A new diaria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return diariaService
            .save(diariaDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/diarias/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /diarias/:id} : Updates an existing diaria.
     *
     * @param id the id of the diariaDTO to save.
     * @param diariaDTO the diariaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diariaDTO,
     * or with status {@code 400 (Bad Request)} if the diariaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the diariaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<DiariaDTO>> updateDiaria(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DiariaDTO diariaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Diaria : {}, {}", id, diariaDTO);
        if (diariaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diariaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return diariaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return diariaService
                    .update(diariaDTO)
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
     * {@code PATCH  /diarias/:id} : Partial updates given fields of an existing diaria, field will ignore if it is null
     *
     * @param id the id of the diariaDTO to save.
     * @param diariaDTO the diariaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated diariaDTO,
     * or with status {@code 400 (Bad Request)} if the diariaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the diariaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the diariaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<DiariaDTO>> partialUpdateDiaria(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DiariaDTO diariaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Diaria partially : {}, {}", id, diariaDTO);
        if (diariaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, diariaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return diariaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<DiariaDTO> result = diariaService.partialUpdate(diariaDTO);

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
     * {@code GET  /diarias} : get all the diarias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of diarias in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<DiariaDTO>> getAllDiarias() {
        log.debug("REST request to get all Diarias");
        return diariaService.findAll().collectList();
    }

    /**
     * {@code GET  /diarias} : get all the diarias as a stream.
     * @return the {@link Flux} of diarias.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<DiariaDTO> getAllDiariasAsStream() {
        log.debug("REST request to get all Diarias as a stream");
        return diariaService.findAll();
    }

    /**
     * {@code GET  /diarias/:id} : get the "id" diaria.
     *
     * @param id the id of the diariaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the diariaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<DiariaDTO>> getDiaria(@PathVariable("id") Long id) {
        log.debug("REST request to get Diaria : {}", id);
        Mono<DiariaDTO> diariaDTO = diariaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diariaDTO);
    }

    /**
     * {@code DELETE  /diarias/:id} : delete the "id" diaria.
     *
     * @param id the id of the diariaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteDiaria(@PathVariable("id") Long id) {
        log.debug("REST request to delete Diaria : {}", id);
        return diariaService
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
