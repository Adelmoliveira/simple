package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CidadesRepository;
import com.mycompany.myapp.service.CidadesService;
import com.mycompany.myapp.service.dto.CidadesDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Cidades}.
 */
@RestController
@RequestMapping("/api/cidades")
public class CidadesResource {

    private final Logger log = LoggerFactory.getLogger(CidadesResource.class);

    private static final String ENTITY_NAME = "cidades";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CidadesService cidadesService;

    private final CidadesRepository cidadesRepository;

    public CidadesResource(CidadesService cidadesService, CidadesRepository cidadesRepository) {
        this.cidadesService = cidadesService;
        this.cidadesRepository = cidadesRepository;
    }

    /**
     * {@code POST  /cidades} : Create a new cidades.
     *
     * @param cidadesDTO the cidadesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cidadesDTO, or with status {@code 400 (Bad Request)} if the cidades has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<CidadesDTO>> createCidades(@RequestBody CidadesDTO cidadesDTO) throws URISyntaxException {
        log.debug("REST request to save Cidades : {}", cidadesDTO);
        if (cidadesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cidades cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return cidadesService
            .save(cidadesDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/cidades/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /cidades/:id} : Updates an existing cidades.
     *
     * @param id the id of the cidadesDTO to save.
     * @param cidadesDTO the cidadesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadesDTO,
     * or with status {@code 400 (Bad Request)} if the cidadesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cidadesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<CidadesDTO>> updateCidades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CidadesDTO cidadesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cidades : {}, {}", id, cidadesDTO);
        if (cidadesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return cidadesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return cidadesService
                    .update(cidadesDTO)
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
     * {@code PATCH  /cidades/:id} : Partial updates given fields of an existing cidades, field will ignore if it is null
     *
     * @param id the id of the cidadesDTO to save.
     * @param cidadesDTO the cidadesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadesDTO,
     * or with status {@code 400 (Bad Request)} if the cidadesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cidadesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cidadesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<CidadesDTO>> partialUpdateCidades(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CidadesDTO cidadesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cidades partially : {}, {}", id, cidadesDTO);
        if (cidadesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return cidadesRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<CidadesDTO> result = cidadesService.partialUpdate(cidadesDTO);

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
     * {@code GET  /cidades} : get all the cidades.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cidades in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CidadesDTO>> getAllCidades(@RequestParam(name = "filter", required = false) String filter) {
        if ("missao-is-null".equals(filter)) {
            log.debug("REST request to get all Cidadess where missao is null");
            return cidadesService.findAllWhereMissaoIsNull().collectList();
        }
        log.debug("REST request to get all Cidades");
        return cidadesService.findAll().collectList();
    }

    /**
     * {@code GET  /cidades} : get all the cidades as a stream.
     * @return the {@link Flux} of cidades.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CidadesDTO> getAllCidadesAsStream() {
        log.debug("REST request to get all Cidades as a stream");
        return cidadesService.findAll();
    }

    /**
     * {@code GET  /cidades/:id} : get the "id" cidades.
     *
     * @param id the id of the cidadesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cidadesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<CidadesDTO>> getCidades(@PathVariable("id") Long id) {
        log.debug("REST request to get Cidades : {}", id);
        Mono<CidadesDTO> cidadesDTO = cidadesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cidadesDTO);
    }

    /**
     * {@code DELETE  /cidades/:id} : delete the "id" cidades.
     *
     * @param id the id of the cidadesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteCidades(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cidades : {}", id);
        return cidadesService
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
