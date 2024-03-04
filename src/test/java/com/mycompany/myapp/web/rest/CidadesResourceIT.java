package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Cidades;
import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import com.mycompany.myapp.domain.enumeration.LocalidadeEnum;
import com.mycompany.myapp.repository.CidadesRepository;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.service.dto.CidadesDTO;
import com.mycompany.myapp.service.mapper.CidadesMapper;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integration tests for the {@link CidadesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class CidadesResourceIT {

    private static final DiariaLocalidadeEnum DEFAULT_CIDADE = DiariaLocalidadeEnum.BRASILIA;
    private static final DiariaLocalidadeEnum UPDATED_CIDADE = DiariaLocalidadeEnum.MANAUS;

    private static final LocalidadeEnum DEFAULT_VALOR_LOCALIDADE = LocalidadeEnum.MAJORADA;
    private static final LocalidadeEnum UPDATED_VALOR_LOCALIDADE = LocalidadeEnum.COMUM;

    private static final String ENTITY_API_URL = "/api/cidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CidadesRepository cidadesRepository;

    @Autowired
    private CidadesMapper cidadesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Cidades cidades;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidades createEntity(EntityManager em) {
        Cidades cidades = new Cidades().cidade(DEFAULT_CIDADE).valorLocalidade(DEFAULT_VALOR_LOCALIDADE);
        return cidades;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidades createUpdatedEntity(EntityManager em) {
        Cidades cidades = new Cidades().cidade(UPDATED_CIDADE).valorLocalidade(UPDATED_VALOR_LOCALIDADE);
        return cidades;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Cidades.class).block();
        } catch (Exception e) {
            // It can fail, if other entities are still referring this - it will be removed later.
        }
    }

    @AfterEach
    public void cleanup() {
        deleteEntities(em);
    }

    @BeforeEach
    public void initTest() {
        deleteEntities(em);
        cidades = createEntity(em);
    }

    @Test
    void createCidades() throws Exception {
        int databaseSizeBeforeCreate = cidadesRepository.findAll().collectList().block().size();
        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeCreate + 1);
        Cidades testCidades = cidadesList.get(cidadesList.size() - 1);
        assertThat(testCidades.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCidades.getValorLocalidade()).isEqualTo(DEFAULT_VALOR_LOCALIDADE);
    }

    @Test
    void createCidadesWithExistingId() throws Exception {
        // Create the Cidades with an existing ID
        cidades.setId(1L);
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        int databaseSizeBeforeCreate = cidadesRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllCidadesAsStream() {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        List<Cidades> cidadesList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(CidadesDTO.class)
            .getResponseBody()
            .map(cidadesMapper::toEntity)
            .filter(cidades::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(cidadesList).isNotNull();
        assertThat(cidadesList).hasSize(1);
        Cidades testCidades = cidadesList.get(0);
        assertThat(testCidades.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCidades.getValorLocalidade()).isEqualTo(DEFAULT_VALOR_LOCALIDADE);
    }

    @Test
    void getAllCidades() {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        // Get all the cidadesList
        webTestClient
            .get()
            .uri(ENTITY_API_URL + "?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id")
            .value(hasItem(cidades.getId().intValue()))
            .jsonPath("$.[*].cidade")
            .value(hasItem(DEFAULT_CIDADE.toString()))
            .jsonPath("$.[*].valorLocalidade")
            .value(hasItem(DEFAULT_VALOR_LOCALIDADE.toString()));
    }

    @Test
    void getCidades() {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        // Get the cidades
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, cidades.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(cidades.getId().intValue()))
            .jsonPath("$.cidade")
            .value(is(DEFAULT_CIDADE.toString()))
            .jsonPath("$.valorLocalidade")
            .value(is(DEFAULT_VALOR_LOCALIDADE.toString()));
    }

    @Test
    void getNonExistingCidades() {
        // Get the cidades
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingCidades() throws Exception {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();

        // Update the cidades
        Cidades updatedCidades = cidadesRepository.findById(cidades.getId()).block();
        updatedCidades.cidade(UPDATED_CIDADE).valorLocalidade(UPDATED_VALOR_LOCALIDADE);
        CidadesDTO cidadesDTO = cidadesMapper.toDto(updatedCidades);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, cidadesDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
        Cidades testCidades = cidadesList.get(cidadesList.size() - 1);
        assertThat(testCidades.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCidades.getValorLocalidade()).isEqualTo(UPDATED_VALOR_LOCALIDADE);
    }

    @Test
    void putNonExistingCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, cidadesDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateCidadesWithPatch() throws Exception {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();

        // Update the cidades using partial update
        Cidades partialUpdatedCidades = new Cidades();
        partialUpdatedCidades.setId(cidades.getId());

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCidades.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCidades))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
        Cidades testCidades = cidadesList.get(cidadesList.size() - 1);
        assertThat(testCidades.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testCidades.getValorLocalidade()).isEqualTo(DEFAULT_VALOR_LOCALIDADE);
    }

    @Test
    void fullUpdateCidadesWithPatch() throws Exception {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();

        // Update the cidades using partial update
        Cidades partialUpdatedCidades = new Cidades();
        partialUpdatedCidades.setId(cidades.getId());

        partialUpdatedCidades.cidade(UPDATED_CIDADE).valorLocalidade(UPDATED_VALOR_LOCALIDADE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedCidades.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedCidades))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
        Cidades testCidades = cidadesList.get(cidadesList.size() - 1);
        assertThat(testCidades.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testCidades.getValorLocalidade()).isEqualTo(UPDATED_VALOR_LOCALIDADE);
    }

    @Test
    void patchNonExistingCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, cidadesDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamCidades() throws Exception {
        int databaseSizeBeforeUpdate = cidadesRepository.findAll().collectList().block().size();
        cidades.setId(longCount.incrementAndGet());

        // Create the Cidades
        CidadesDTO cidadesDTO = cidadesMapper.toDto(cidades);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(cidadesDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Cidades in the database
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteCidades() {
        // Initialize the database
        cidadesRepository.save(cidades).block();

        int databaseSizeBeforeDelete = cidadesRepository.findAll().collectList().block().size();

        // Delete the cidades
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, cidades.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Cidades> cidadesList = cidadesRepository.findAll().collectList().block();
        assertThat(cidadesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
