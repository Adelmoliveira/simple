package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Setor;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.repository.SetorRepository;
import com.mycompany.myapp.service.dto.SetorDTO;
import com.mycompany.myapp.service.mapper.SetorMapper;
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
 * Integration tests for the {@link SetorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class SetorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/setors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private SetorMapper setorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Setor setor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setor createEntity(EntityManager em) {
        Setor setor = new Setor().nome(DEFAULT_NOME).sigla(DEFAULT_SIGLA);
        return setor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Setor createUpdatedEntity(EntityManager em) {
        Setor setor = new Setor().nome(UPDATED_NOME).sigla(UPDATED_SIGLA);
        return setor;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Setor.class).block();
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
        setor = createEntity(em);
    }

    @Test
    void createSetor() throws Exception {
        int databaseSizeBeforeCreate = setorRepository.findAll().collectList().block().size();
        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeCreate + 1);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSetor.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    void createSetorWithExistingId() throws Exception {
        // Create the Setor with an existing ID
        setor.setId(1L);
        SetorDTO setorDTO = setorMapper.toDto(setor);

        int databaseSizeBeforeCreate = setorRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = setorRepository.findAll().collectList().block().size();
        // set the field null
        setor.setNome(null);

        // Create the Setor, which fails.
        SetorDTO setorDTO = setorMapper.toDto(setor);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkSiglaIsRequired() throws Exception {
        int databaseSizeBeforeTest = setorRepository.findAll().collectList().block().size();
        // set the field null
        setor.setSigla(null);

        // Create the Setor, which fails.
        SetorDTO setorDTO = setorMapper.toDto(setor);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSetorsAsStream() {
        // Initialize the database
        setorRepository.save(setor).block();

        List<Setor> setorList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(SetorDTO.class)
            .getResponseBody()
            .map(setorMapper::toEntity)
            .filter(setor::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(setorList).isNotNull();
        assertThat(setorList).hasSize(1);
        Setor testSetor = setorList.get(0);
        assertThat(testSetor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSetor.getSigla()).isEqualTo(DEFAULT_SIGLA);
    }

    @Test
    void getAllSetors() {
        // Initialize the database
        setorRepository.save(setor).block();

        // Get all the setorList
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
            .value(hasItem(setor.getId().intValue()))
            .jsonPath("$.[*].nome")
            .value(hasItem(DEFAULT_NOME))
            .jsonPath("$.[*].sigla")
            .value(hasItem(DEFAULT_SIGLA));
    }

    @Test
    void getSetor() {
        // Initialize the database
        setorRepository.save(setor).block();

        // Get the setor
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, setor.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(setor.getId().intValue()))
            .jsonPath("$.nome")
            .value(is(DEFAULT_NOME))
            .jsonPath("$.sigla")
            .value(is(DEFAULT_SIGLA));
    }

    @Test
    void getNonExistingSetor() {
        // Get the setor
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingSetor() throws Exception {
        // Initialize the database
        setorRepository.save(setor).block();

        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();

        // Update the setor
        Setor updatedSetor = setorRepository.findById(setor.getId()).block();
        updatedSetor.nome(UPDATED_NOME).sigla(UPDATED_SIGLA);
        SetorDTO setorDTO = setorMapper.toDto(updatedSetor);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, setorDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSetor.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    void putNonExistingSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, setorDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSetorWithPatch() throws Exception {
        // Initialize the database
        setorRepository.save(setor).block();

        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();

        // Update the setor using partial update
        Setor partialUpdatedSetor = new Setor();
        partialUpdatedSetor.setId(setor.getId());

        partialUpdatedSetor.nome(UPDATED_NOME).sigla(UPDATED_SIGLA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSetor.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSetor))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSetor.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    void fullUpdateSetorWithPatch() throws Exception {
        // Initialize the database
        setorRepository.save(setor).block();

        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();

        // Update the setor using partial update
        Setor partialUpdatedSetor = new Setor();
        partialUpdatedSetor.setId(setor.getId());

        partialUpdatedSetor.nome(UPDATED_NOME).sigla(UPDATED_SIGLA);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedSetor.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedSetor))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
        Setor testSetor = setorList.get(setorList.size() - 1);
        assertThat(testSetor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSetor.getSigla()).isEqualTo(UPDATED_SIGLA);
    }

    @Test
    void patchNonExistingSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, setorDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSetor() throws Exception {
        int databaseSizeBeforeUpdate = setorRepository.findAll().collectList().block().size();
        setor.setId(longCount.incrementAndGet());

        // Create the Setor
        SetorDTO setorDTO = setorMapper.toDto(setor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(setorDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Setor in the database
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSetor() {
        // Initialize the database
        setorRepository.save(setor).block();

        int databaseSizeBeforeDelete = setorRepository.findAll().collectList().block().size();

        // Delete the setor
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, setor.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Setor> setorList = setorRepository.findAll().collectList().block();
        assertThat(setorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
