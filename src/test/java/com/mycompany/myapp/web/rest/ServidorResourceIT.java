package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Servidor;
import com.mycompany.myapp.domain.enumeration.PostoEnum;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.repository.ServidorRepository;
import com.mycompany.myapp.service.ServidorService;
import com.mycompany.myapp.service.dto.ServidorDTO;
import com.mycompany.myapp.service.mapper.ServidorMapper;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

/**
 * Integration tests for the {@link ServidorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class ServidorResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final PostoEnum DEFAULT_POSTO = PostoEnum.OficialSuper;
    private static final PostoEnum UPDATED_POSTO = PostoEnum.Oficial;

    private static final String ENTITY_API_URL = "/api/servidors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServidorRepository servidorRepository;

    @Mock
    private ServidorRepository servidorRepositoryMock;

    @Autowired
    private ServidorMapper servidorMapper;

    @Mock
    private ServidorService servidorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Servidor servidor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createEntity(EntityManager em) {
        Servidor servidor = new Servidor()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .nome(DEFAULT_NOME)
            .sobreNome(DEFAULT_SOBRE_NOME)
            .email(DEFAULT_EMAIL)
            .celular(DEFAULT_CELULAR)
            .posto(DEFAULT_POSTO);
        return servidor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servidor createUpdatedEntity(EntityManager em) {
        Servidor servidor = new Servidor()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .posto(UPDATED_POSTO);
        return servidor;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll("rel_servidor__equipes").block();
            em.deleteAll(Servidor.class).block();
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
        servidor = createEntity(em);
    }

    @Test
    void createServidor() throws Exception {
        int databaseSizeBeforeCreate = servidorRepository.findAll().collectList().block().size();
        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate + 1);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testServidor.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testServidor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testServidor.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testServidor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testServidor.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testServidor.getPosto()).isEqualTo(DEFAULT_POSTO);
    }

    @Test
    void createServidorWithExistingId() throws Exception {
        // Create the Servidor with an existing ID
        servidor.setId(1L);
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        int databaseSizeBeforeCreate = servidorRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().collectList().block().size();
        // set the field null
        servidor.setNome(null);

        // Create the Servidor, which fails.
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = servidorRepository.findAll().collectList().block().size();
        // set the field null
        servidor.setPosto(null);

        // Create the Servidor, which fails.
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllServidors() {
        // Initialize the database
        servidorRepository.save(servidor).block();

        // Get all the servidorList
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
            .value(hasItem(servidor.getId().intValue()))
            .jsonPath("$.[*].fotoContentType")
            .value(hasItem(DEFAULT_FOTO_CONTENT_TYPE))
            .jsonPath("$.[*].foto")
            .value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_FOTO)))
            .jsonPath("$.[*].nome")
            .value(hasItem(DEFAULT_NOME))
            .jsonPath("$.[*].sobreNome")
            .value(hasItem(DEFAULT_SOBRE_NOME))
            .jsonPath("$.[*].email")
            .value(hasItem(DEFAULT_EMAIL))
            .jsonPath("$.[*].celular")
            .value(hasItem(DEFAULT_CELULAR))
            .jsonPath("$.[*].posto")
            .value(hasItem(DEFAULT_POSTO.toString()));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServidorsWithEagerRelationshipsIsEnabled() {
        when(servidorServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=true").exchange().expectStatus().isOk();

        verify(servidorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServidorsWithEagerRelationshipsIsNotEnabled() {
        when(servidorServiceMock.findAllWithEagerRelationships(any())).thenReturn(Flux.empty());

        webTestClient.get().uri(ENTITY_API_URL + "?eagerload=false").exchange().expectStatus().isOk();
        verify(servidorRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    void getServidor() {
        // Initialize the database
        servidorRepository.save(servidor).block();

        // Get the servidor
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, servidor.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(servidor.getId().intValue()))
            .jsonPath("$.fotoContentType")
            .value(is(DEFAULT_FOTO_CONTENT_TYPE))
            .jsonPath("$.foto")
            .value(is(Base64.getEncoder().encodeToString(DEFAULT_FOTO)))
            .jsonPath("$.nome")
            .value(is(DEFAULT_NOME))
            .jsonPath("$.sobreNome")
            .value(is(DEFAULT_SOBRE_NOME))
            .jsonPath("$.email")
            .value(is(DEFAULT_EMAIL))
            .jsonPath("$.celular")
            .value(is(DEFAULT_CELULAR))
            .jsonPath("$.posto")
            .value(is(DEFAULT_POSTO.toString()));
    }

    @Test
    void getNonExistingServidor() {
        // Get the servidor
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingServidor() throws Exception {
        // Initialize the database
        servidorRepository.save(servidor).block();

        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();

        // Update the servidor
        Servidor updatedServidor = servidorRepository.findById(servidor.getId()).block();
        updatedServidor
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .posto(UPDATED_POSTO);
        ServidorDTO servidorDTO = servidorMapper.toDto(updatedServidor);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, servidorDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testServidor.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testServidor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidor.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testServidor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testServidor.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testServidor.getPosto()).isEqualTo(UPDATED_POSTO);
    }

    @Test
    void putNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, servidorDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateServidorWithPatch() throws Exception {
        // Initialize the database
        servidorRepository.save(servidor).block();

        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();

        // Update the servidor using partial update
        Servidor partialUpdatedServidor = new Servidor();
        partialUpdatedServidor.setId(servidor.getId());

        partialUpdatedServidor.nome(UPDATED_NOME).sobreNome(UPDATED_SOBRE_NOME);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedServidor.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedServidor))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testServidor.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testServidor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidor.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testServidor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testServidor.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testServidor.getPosto()).isEqualTo(DEFAULT_POSTO);
    }

    @Test
    void fullUpdateServidorWithPatch() throws Exception {
        // Initialize the database
        servidorRepository.save(servidor).block();

        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();

        // Update the servidor using partial update
        Servidor partialUpdatedServidor = new Servidor();
        partialUpdatedServidor.setId(servidor.getId());

        partialUpdatedServidor
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .nome(UPDATED_NOME)
            .sobreNome(UPDATED_SOBRE_NOME)
            .email(UPDATED_EMAIL)
            .celular(UPDATED_CELULAR)
            .posto(UPDATED_POSTO);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedServidor.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedServidor))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
        Servidor testServidor = servidorList.get(servidorList.size() - 1);
        assertThat(testServidor.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testServidor.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testServidor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testServidor.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testServidor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testServidor.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testServidor.getPosto()).isEqualTo(UPDATED_POSTO);
    }

    @Test
    void patchNonExistingServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, servidorDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamServidor() throws Exception {
        int databaseSizeBeforeUpdate = servidorRepository.findAll().collectList().block().size();
        servidor.setId(longCount.incrementAndGet());

        // Create the Servidor
        ServidorDTO servidorDTO = servidorMapper.toDto(servidor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(servidorDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Servidor in the database
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteServidor() {
        // Initialize the database
        servidorRepository.save(servidor).block();

        int databaseSizeBeforeDelete = servidorRepository.findAll().collectList().block().size();

        // Delete the servidor
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, servidor.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Servidor> servidorList = servidorRepository.findAll().collectList().block();
        assertThat(servidorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
