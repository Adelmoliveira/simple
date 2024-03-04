package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ServidorRepository;
import com.mycompany.myapp.service.ServidorService;
import com.mycompany.myapp.service.dto.ServidorDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Servidor}.
 */
@RestController
@RequestMapping("/api/servidors")
public class ServidorResource {

    private final Logger log = LoggerFactory.getLogger(ServidorResource.class);

    private static final String ENTITY_NAME = "servidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServidorService servidorService;

    private final ServidorRepository servidorRepository;

    public ServidorResource(ServidorService servidorService, ServidorRepository servidorRepository) {
        this.servidorService = servidorService;
        this.servidorRepository = servidorRepository;
    }

    /**
     * {@code POST  /servidors} : Create a new servidor.
     *
     * @param servidorDTO the servidorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servidorDTO, or with status {@code 400 (Bad Request)} if the servidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<ServidorDTO>> createServidor(@Valid @RequestBody ServidorDTO servidorDTO) throws URISyntaxException {
        log.debug("REST request to save Servidor : {}", servidorDTO);
        if (servidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new servidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return servidorService
            .save(servidorDTO)
            .map(result -> {
                try {
                    return ResponseEntity
                        .created(new URI("/api/servidors/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /servidors/:id} : Updates an existing servidor.
     *
     * @param id the id of the servidorDTO to save.
     * @param servidorDTO the servidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidorDTO,
     * or with status {@code 400 (Bad Request)} if the servidorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<ServidorDTO>> updateServidor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServidorDTO servidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Servidor : {}, {}", id, servidorDTO);
        if (servidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return servidorRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return servidorService
                    .update(servidorDTO)
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
     * {@code PATCH  /servidors/:id} : Partial updates given fields of an existing servidor, field will ignore if it is null
     *
     * @param id the id of the servidorDTO to save.
     * @param servidorDTO the servidorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servidorDTO,
     * or with status {@code 400 (Bad Request)} if the servidorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servidorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servidorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<ServidorDTO>> partialUpdateServidor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServidorDTO servidorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Servidor partially : {}, {}", id, servidorDTO);
        if (servidorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servidorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return servidorRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<ServidorDTO> result = servidorService.partialUpdate(servidorDTO);

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
     * {@code GET  /servidors} : get all the servidors.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servidors in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<ServidorDTO>>> getAllServidors(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("setor-is-null".equals(filter)) {
            log.debug("REST request to get all Servidors where setor is null");
            return servidorService.findAllWhereSetorIsNull().collectList().map(ResponseEntity::ok);
        }
        log.debug("REST request to get a page of Servidors");
        return servidorService
            .countAll()
            .zipWith(servidorService.findAll(pageable).collectList())
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
     * {@code GET  /servidors/:id} : get the "id" servidor.
     *
     * @param id the id of the servidorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servidorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<ServidorDTO>> getServidor(@PathVariable("id") Long id) {
        log.debug("REST request to get Servidor : {}", id);
        Mono<ServidorDTO> servidorDTO = servidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servidorDTO);
    }

    /**
     * {@code DELETE  /servidors/:id} : delete the "id" servidor.
     *
     * @param id the id of the servidorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteServidor(@PathVariable("id") Long id) {
        log.debug("REST request to delete Servidor : {}", id);
        return servidorService
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
