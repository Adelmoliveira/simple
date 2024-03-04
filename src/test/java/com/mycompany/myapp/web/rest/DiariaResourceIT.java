package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Diaria;
import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import com.mycompany.myapp.repository.DiariaRepository;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.service.dto.DiariaDTO;
import com.mycompany.myapp.service.mapper.DiariaMapper;
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
 * Integration tests for the {@link DiariaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class DiariaResourceIT {

    private static final String DEFAULT_CIDADE = "AAAAAAAAAA";
    private static final String UPDATED_CIDADE = "BBBBBBBBBB";

    private static final Double DEFAULT_OFICIAL_SUP = 1D;
    private static final Double UPDATED_OFICIAL_SUP = 2D;

    private static final Double DEFAULT_OFICIAL = 1D;
    private static final Double UPDATED_OFICIAL = 2D;

    private static final Double DEFAULT_GRADUADO = 1D;
    private static final Double UPDATED_GRADUADO = 2D;

    private static final Double DEFAULT_PRACA = 1D;
    private static final Double UPDATED_PRACA = 2D;

    private static final Double DEFAULT_CIVIL = 1D;
    private static final Double UPDATED_CIVIL = 2D;

    private static final DiariaLocalidadeEnum DEFAULT_LOCALIDADE = DiariaLocalidadeEnum.BRASILIA;
    private static final DiariaLocalidadeEnum UPDATED_LOCALIDADE = DiariaLocalidadeEnum.MANAUS;

    private static final String ENTITY_API_URL = "/api/diarias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DiariaRepository diariaRepository;

    @Autowired
    private DiariaMapper diariaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Diaria diaria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diaria createEntity(EntityManager em) {
        Diaria diaria = new Diaria()
            .cidade(DEFAULT_CIDADE)
            .oficialSup(DEFAULT_OFICIAL_SUP)
            .oficial(DEFAULT_OFICIAL)
            .graduado(DEFAULT_GRADUADO)
            .praca(DEFAULT_PRACA)
            .civil(DEFAULT_CIVIL)
            .localidade(DEFAULT_LOCALIDADE);
        return diaria;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diaria createUpdatedEntity(EntityManager em) {
        Diaria diaria = new Diaria()
            .cidade(UPDATED_CIDADE)
            .oficialSup(UPDATED_OFICIAL_SUP)
            .oficial(UPDATED_OFICIAL)
            .graduado(UPDATED_GRADUADO)
            .praca(UPDATED_PRACA)
            .civil(UPDATED_CIVIL)
            .localidade(UPDATED_LOCALIDADE);
        return diaria;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Diaria.class).block();
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
        diaria = createEntity(em);
    }

    @Test
    void createDiaria() throws Exception {
        int databaseSizeBeforeCreate = diariaRepository.findAll().collectList().block().size();
        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeCreate + 1);
        Diaria testDiaria = diariaList.get(diariaList.size() - 1);
        assertThat(testDiaria.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testDiaria.getOficialSup()).isEqualTo(DEFAULT_OFICIAL_SUP);
        assertThat(testDiaria.getOficial()).isEqualTo(DEFAULT_OFICIAL);
        assertThat(testDiaria.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testDiaria.getPraca()).isEqualTo(DEFAULT_PRACA);
        assertThat(testDiaria.getCivil()).isEqualTo(DEFAULT_CIVIL);
        assertThat(testDiaria.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
    }

    @Test
    void createDiariaWithExistingId() throws Exception {
        // Create the Diaria with an existing ID
        diaria.setId(1L);
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        int databaseSizeBeforeCreate = diariaRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diariaRepository.findAll().collectList().block().size();
        // set the field null
        diaria.setCidade(null);

        // Create the Diaria, which fails.
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLocalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diariaRepository.findAll().collectList().block().size();
        // set the field null
        diaria.setLocalidade(null);

        // Create the Diaria, which fails.
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllDiariasAsStream() {
        // Initialize the database
        diariaRepository.save(diaria).block();

        List<Diaria> diariaList = webTestClient
            .get()
            .uri(ENTITY_API_URL)
            .accept(MediaType.APPLICATION_NDJSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.APPLICATION_NDJSON)
            .returnResult(DiariaDTO.class)
            .getResponseBody()
            .map(diariaMapper::toEntity)
            .filter(diaria::equals)
            .collectList()
            .block(Duration.ofSeconds(5));

        assertThat(diariaList).isNotNull();
        assertThat(diariaList).hasSize(1);
        Diaria testDiaria = diariaList.get(0);
        assertThat(testDiaria.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testDiaria.getOficialSup()).isEqualTo(DEFAULT_OFICIAL_SUP);
        assertThat(testDiaria.getOficial()).isEqualTo(DEFAULT_OFICIAL);
        assertThat(testDiaria.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testDiaria.getPraca()).isEqualTo(DEFAULT_PRACA);
        assertThat(testDiaria.getCivil()).isEqualTo(DEFAULT_CIVIL);
        assertThat(testDiaria.getLocalidade()).isEqualTo(DEFAULT_LOCALIDADE);
    }

    @Test
    void getAllDiarias() {
        // Initialize the database
        diariaRepository.save(diaria).block();

        // Get all the diariaList
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
            .value(hasItem(diaria.getId().intValue()))
            .jsonPath("$.[*].cidade")
            .value(hasItem(DEFAULT_CIDADE))
            .jsonPath("$.[*].oficialSup")
            .value(hasItem(DEFAULT_OFICIAL_SUP.doubleValue()))
            .jsonPath("$.[*].oficial")
            .value(hasItem(DEFAULT_OFICIAL.doubleValue()))
            .jsonPath("$.[*].graduado")
            .value(hasItem(DEFAULT_GRADUADO.doubleValue()))
            .jsonPath("$.[*].praca")
            .value(hasItem(DEFAULT_PRACA.doubleValue()))
            .jsonPath("$.[*].civil")
            .value(hasItem(DEFAULT_CIVIL.doubleValue()))
            .jsonPath("$.[*].localidade")
            .value(hasItem(DEFAULT_LOCALIDADE.toString()));
    }

    @Test
    void getDiaria() {
        // Initialize the database
        diariaRepository.save(diaria).block();

        // Get the diaria
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, diaria.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(diaria.getId().intValue()))
            .jsonPath("$.cidade")
            .value(is(DEFAULT_CIDADE))
            .jsonPath("$.oficialSup")
            .value(is(DEFAULT_OFICIAL_SUP.doubleValue()))
            .jsonPath("$.oficial")
            .value(is(DEFAULT_OFICIAL.doubleValue()))
            .jsonPath("$.graduado")
            .value(is(DEFAULT_GRADUADO.doubleValue()))
            .jsonPath("$.praca")
            .value(is(DEFAULT_PRACA.doubleValue()))
            .jsonPath("$.civil")
            .value(is(DEFAULT_CIVIL.doubleValue()))
            .jsonPath("$.localidade")
            .value(is(DEFAULT_LOCALIDADE.toString()));
    }

    @Test
    void getNonExistingDiaria() {
        // Get the diaria
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingDiaria() throws Exception {
        // Initialize the database
        diariaRepository.save(diaria).block();

        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();

        // Update the diaria
        Diaria updatedDiaria = diariaRepository.findById(diaria.getId()).block();
        updatedDiaria
            .cidade(UPDATED_CIDADE)
            .oficialSup(UPDATED_OFICIAL_SUP)
            .oficial(UPDATED_OFICIAL)
            .graduado(UPDATED_GRADUADO)
            .praca(UPDATED_PRACA)
            .civil(UPDATED_CIVIL)
            .localidade(UPDATED_LOCALIDADE);
        DiariaDTO diariaDTO = diariaMapper.toDto(updatedDiaria);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, diariaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
        Diaria testDiaria = diariaList.get(diariaList.size() - 1);
        assertThat(testDiaria.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testDiaria.getOficialSup()).isEqualTo(UPDATED_OFICIAL_SUP);
        assertThat(testDiaria.getOficial()).isEqualTo(UPDATED_OFICIAL);
        assertThat(testDiaria.getGraduado()).isEqualTo(UPDATED_GRADUADO);
        assertThat(testDiaria.getPraca()).isEqualTo(UPDATED_PRACA);
        assertThat(testDiaria.getCivil()).isEqualTo(UPDATED_CIVIL);
        assertThat(testDiaria.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
    }

    @Test
    void putNonExistingDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, diariaDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDiariaWithPatch() throws Exception {
        // Initialize the database
        diariaRepository.save(diaria).block();

        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();

        // Update the diaria using partial update
        Diaria partialUpdatedDiaria = new Diaria();
        partialUpdatedDiaria.setId(diaria.getId());

        partialUpdatedDiaria.oficial(UPDATED_OFICIAL).localidade(UPDATED_LOCALIDADE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDiaria.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaria))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
        Diaria testDiaria = diariaList.get(diariaList.size() - 1);
        assertThat(testDiaria.getCidade()).isEqualTo(DEFAULT_CIDADE);
        assertThat(testDiaria.getOficialSup()).isEqualTo(DEFAULT_OFICIAL_SUP);
        assertThat(testDiaria.getOficial()).isEqualTo(UPDATED_OFICIAL);
        assertThat(testDiaria.getGraduado()).isEqualTo(DEFAULT_GRADUADO);
        assertThat(testDiaria.getPraca()).isEqualTo(DEFAULT_PRACA);
        assertThat(testDiaria.getCivil()).isEqualTo(DEFAULT_CIVIL);
        assertThat(testDiaria.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
    }

    @Test
    void fullUpdateDiariaWithPatch() throws Exception {
        // Initialize the database
        diariaRepository.save(diaria).block();

        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();

        // Update the diaria using partial update
        Diaria partialUpdatedDiaria = new Diaria();
        partialUpdatedDiaria.setId(diaria.getId());

        partialUpdatedDiaria
            .cidade(UPDATED_CIDADE)
            .oficialSup(UPDATED_OFICIAL_SUP)
            .oficial(UPDATED_OFICIAL)
            .graduado(UPDATED_GRADUADO)
            .praca(UPDATED_PRACA)
            .civil(UPDATED_CIVIL)
            .localidade(UPDATED_LOCALIDADE);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedDiaria.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedDiaria))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
        Diaria testDiaria = diariaList.get(diariaList.size() - 1);
        assertThat(testDiaria.getCidade()).isEqualTo(UPDATED_CIDADE);
        assertThat(testDiaria.getOficialSup()).isEqualTo(UPDATED_OFICIAL_SUP);
        assertThat(testDiaria.getOficial()).isEqualTo(UPDATED_OFICIAL);
        assertThat(testDiaria.getGraduado()).isEqualTo(UPDATED_GRADUADO);
        assertThat(testDiaria.getPraca()).isEqualTo(UPDATED_PRACA);
        assertThat(testDiaria.getCivil()).isEqualTo(UPDATED_CIVIL);
        assertThat(testDiaria.getLocalidade()).isEqualTo(UPDATED_LOCALIDADE);
    }

    @Test
    void patchNonExistingDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, diariaDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDiaria() throws Exception {
        int databaseSizeBeforeUpdate = diariaRepository.findAll().collectList().block().size();
        diaria.setId(longCount.incrementAndGet());

        // Create the Diaria
        DiariaDTO diariaDTO = diariaMapper.toDto(diaria);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(diariaDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Diaria in the database
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDiaria() {
        // Initialize the database
        diariaRepository.save(diaria).block();

        int databaseSizeBeforeDelete = diariaRepository.findAll().collectList().block().size();

        // Delete the diaria
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, diaria.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Diaria> diariaList = diariaRepository.findAll().collectList().block();
        assertThat(diariaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
