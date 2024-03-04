package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SetorRepository;
import com.mycompany.myapp.service.SetorService;
import com.mycompany.myapp.service.dto.SetorDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Setor}.
 */
@RestController
@RequestMapping("/api/setors")
public class SetorResource {

    private final Logger log = LoggerFactory.getLogger(SetorResource.class);

    private static final String ENTITY_NAME = "setor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SetorService setorService;

    private final SetorRepository setorRepository;

    public SetorResource(SetorService setorService, SetorRepository setorRepository) {
        this.setorService = setorService;
        this.setorRepository = setorRepository;
    }

    /**
     * {@code POST  /setors} : Create a new setor.
     *
     * @param setorDTO the setorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new setorDTO, or with status {@code 400 (Bad Request)} if the setor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<SetorDTO>> createSetor(@Valid @RequestBody SetorDTO setorDTO) throws URISyntaxException {
        log.debug("REST request to save Setor : {}", setorDTO);
        if (setorDTO.getId() != null) {
            throw new BadRequestAlertException("A new setor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return setorService
            .save(setorDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/setors/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /setors/:id} : Updates an existing setor.
     *
     * @param id the id of the setorDTO to save.
     * @param setorDTO the setorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setorDTO,
     * or with status {@code 400 (Bad Request)} if the setorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the setorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<SetorDTO>> updateSetor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SetorDTO setorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Setor : {}, {}", id, setorDTO);
        if (setorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return setorRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return setorService
                    .update(setorDTO)
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
     * {@code PATCH  /setors/:id} : Partial updates given fields of an existing setor, field will ignore if it is null
     *
     * @param id the id of the setorDTO to save.
     * @param setorDTO the setorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated setorDTO,
     * or with status {@code 400 (Bad Request)} if the setorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the setorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the setorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<SetorDTO>> partialUpdateSetor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SetorDTO setorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Setor partially : {}, {}", id, setorDTO);
        if (setorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, setorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return setorRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<SetorDTO> result = setorService.partialUpdate(setorDTO);

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
     * {@code GET  /setors} : get all the setors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of setors in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<SetorDTO>> getAllSetors() {
        log.debug("REST request to get all Setors");
        return setorService.findAll().collectList();
    }

    /**
     * {@code GET  /setors} : get all the setors as a stream.
     * @return the {@link Flux} of setors.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<SetorDTO> getAllSetorsAsStream() {
        log.debug("REST request to get all Setors as a stream");
        return setorService.findAll();
    }

    /**
     * {@code GET  /setors/:id} : get the "id" setor.
     *
     * @param id the id of the setorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the setorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<SetorDTO>> getSetor(@PathVariable("id") Long id) {
        log.debug("REST request to get Setor : {}", id);
        Mono<SetorDTO> setorDTO = setorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(setorDTO);
    }

    /**
     * {@code DELETE  /setors/:id} : delete the "id" setor.
     *
     * @param id the id of the setorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteSetor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Setor : {}", id);
        return setorService
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
