package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Missao;
import com.mycompany.myapp.domain.enumeration.AcaoEnum;
import com.mycompany.myapp.domain.enumeration.OrcamentoEnum;
import com.mycompany.myapp.domain.enumeration.Prioridade;
import com.mycompany.myapp.domain.enumeration.StatusEnum;
import com.mycompany.myapp.repository.EntityManager;
import com.mycompany.myapp.repository.MissaoRepository;
import com.mycompany.myapp.service.dto.MissaoDTO;
import com.mycompany.myapp.service.mapper.MissaoMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link MissaoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_ENTITY_TIMEOUT)
@WithMockUser
class MissaoResourceIT {

    private static final String DEFAULT_NOME_MISSAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MISSAO = "BBBBBBBBBB";

    private static final Prioridade DEFAULT_PRIORIDADE = Prioridade.ALTA;
    private static final Prioridade UPDATED_PRIORIDADE = Prioridade.MEDIA;

    private static final Long DEFAULT_QUANTIDADE_DIARIA = 1L;
    private static final Long UPDATED_QUANTIDADE_DIARIA = 2L;

    private static final Boolean DEFAULT_MEIA_DIARIA = false;
    private static final Boolean UPDATED_MEIA_DIARIA = true;

    private static final Integer DEFAULT_QUANTIDADE_EQUIPE = 1;
    private static final Integer UPDATED_QUANTIDADE_EQUIPE = 2;

    private static final LocalDate DEFAULT_DATA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_TERMINO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_TERMINO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DESLOCAMENTO = false;
    private static final Boolean UPDATED_DESLOCAMENTO = true;

    private static final Boolean DEFAULT_PASSAGEM_AEREA = false;
    private static final Boolean UPDATED_PASSAGEM_AEREA = true;

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.APROVADA;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.CANCELADA;

    private static final Boolean DEFAULT_OSVERIFICADA = false;
    private static final Boolean UPDATED_OSVERIFICADA = true;

    private static final AcaoEnum DEFAULT_ACAO = AcaoEnum.APROVADA;
    private static final AcaoEnum UPDATED_ACAO = AcaoEnum.CANCELADA;

    private static final OrcamentoEnum DEFAULT_VALOR_TOTAL_MISSAO = OrcamentoEnum.DISPONIBILIZADO;
    private static final OrcamentoEnum UPDATED_VALOR_TOTAL_MISSAO = OrcamentoEnum.REALIZADO;

    private static final Double DEFAULT_VALOR_DIARIAS_REALIZADAS = 1D;
    private static final Double UPDATED_VALOR_DIARIAS_REALIZADAS = 2D;

    private static final Double DEFAULT_SALDO_DISPONIVEL = 1D;
    private static final Double UPDATED_SALDO_DISPONIVEL = 2D;

    private static final String ENTITY_API_URL = "/api/missaos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private MissaoMapper missaoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private WebTestClient webTestClient;

    private Missao missao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Missao createEntity(EntityManager em) {
        Missao missao = new Missao()
            .nomeMissao(DEFAULT_NOME_MISSAO)
            .prioridade(DEFAULT_PRIORIDADE)
            .quantidadeDiaria(DEFAULT_QUANTIDADE_DIARIA)
            .meiaDiaria(DEFAULT_MEIA_DIARIA)
            .quantidadeEquipe(DEFAULT_QUANTIDADE_EQUIPE)
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataTermino(DEFAULT_DATA_TERMINO)
            .deslocamento(DEFAULT_DESLOCAMENTO)
            .passagemAerea(DEFAULT_PASSAGEM_AEREA)
            .status(DEFAULT_STATUS)
            .osverificada(DEFAULT_OSVERIFICADA)
            .acao(DEFAULT_ACAO)
            .valorTotalMissao(DEFAULT_VALOR_TOTAL_MISSAO)
            .valorDiariasRealizadas(DEFAULT_VALOR_DIARIAS_REALIZADAS)
            .saldoDisponivel(DEFAULT_SALDO_DISPONIVEL);
        return missao;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Missao createUpdatedEntity(EntityManager em) {
        Missao missao = new Missao()
            .nomeMissao(UPDATED_NOME_MISSAO)
            .prioridade(UPDATED_PRIORIDADE)
            .quantidadeDiaria(UPDATED_QUANTIDADE_DIARIA)
            .meiaDiaria(UPDATED_MEIA_DIARIA)
            .quantidadeEquipe(UPDATED_QUANTIDADE_EQUIPE)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataTermino(UPDATED_DATA_TERMINO)
            .deslocamento(UPDATED_DESLOCAMENTO)
            .passagemAerea(UPDATED_PASSAGEM_AEREA)
            .status(UPDATED_STATUS)
            .osverificada(UPDATED_OSVERIFICADA)
            .acao(UPDATED_ACAO)
            .valorTotalMissao(UPDATED_VALOR_TOTAL_MISSAO)
            .valorDiariasRealizadas(UPDATED_VALOR_DIARIAS_REALIZADAS)
            .saldoDisponivel(UPDATED_SALDO_DISPONIVEL);
        return missao;
    }

    public static void deleteEntities(EntityManager em) {
        try {
            em.deleteAll(Missao.class).block();
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
        missao = createEntity(em);
    }

    @Test
    void createMissao() throws Exception {
        int databaseSizeBeforeCreate = missaoRepository.findAll().collectList().block().size();
        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isCreated();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeCreate + 1);
        Missao testMissao = missaoList.get(missaoList.size() - 1);
        assertThat(testMissao.getNomeMissao()).isEqualTo(DEFAULT_NOME_MISSAO);
        assertThat(testMissao.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testMissao.getQuantidadeDiaria()).isEqualTo(DEFAULT_QUANTIDADE_DIARIA);
        assertThat(testMissao.getMeiaDiaria()).isEqualTo(DEFAULT_MEIA_DIARIA);
        assertThat(testMissao.getQuantidadeEquipe()).isEqualTo(DEFAULT_QUANTIDADE_EQUIPE);
        assertThat(testMissao.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testMissao.getDataTermino()).isEqualTo(DEFAULT_DATA_TERMINO);
        assertThat(testMissao.getDeslocamento()).isEqualTo(DEFAULT_DESLOCAMENTO);
        assertThat(testMissao.getPassagemAerea()).isEqualTo(DEFAULT_PASSAGEM_AEREA);
        assertThat(testMissao.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMissao.getOsverificada()).isEqualTo(DEFAULT_OSVERIFICADA);
        assertThat(testMissao.getAcao()).isEqualTo(DEFAULT_ACAO);
        assertThat(testMissao.getValorTotalMissao()).isEqualTo(DEFAULT_VALOR_TOTAL_MISSAO);
        assertThat(testMissao.getValorDiariasRealizadas()).isEqualTo(DEFAULT_VALOR_DIARIAS_REALIZADAS);
        assertThat(testMissao.getSaldoDisponivel()).isEqualTo(DEFAULT_SALDO_DISPONIVEL);
    }

    @Test
    void createMissaoWithExistingId() throws Exception {
        // Create the Missao with an existing ID
        missao.setId(1L);
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        int databaseSizeBeforeCreate = missaoRepository.findAll().collectList().block().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkOsverificadaIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaoRepository.findAll().collectList().block().size();
        // set the field null
        missao.setOsverificada(null);

        // Create the Missao, which fails.
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAcaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = missaoRepository.findAll().collectList().block().size();
        // set the field null
        missao.setAcao(null);

        // Create the Missao, which fails.
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        webTestClient
            .post()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllMissaos() {
        // Initialize the database
        missaoRepository.save(missao).block();

        // Get all the missaoList
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
            .value(hasItem(missao.getId().intValue()))
            .jsonPath("$.[*].nomeMissao")
            .value(hasItem(DEFAULT_NOME_MISSAO))
            .jsonPath("$.[*].prioridade")
            .value(hasItem(DEFAULT_PRIORIDADE.toString()))
            .jsonPath("$.[*].quantidadeDiaria")
            .value(hasItem(DEFAULT_QUANTIDADE_DIARIA.intValue()))
            .jsonPath("$.[*].meiaDiaria")
            .value(hasItem(DEFAULT_MEIA_DIARIA.booleanValue()))
            .jsonPath("$.[*].quantidadeEquipe")
            .value(hasItem(DEFAULT_QUANTIDADE_EQUIPE))
            .jsonPath("$.[*].dataInicio")
            .value(hasItem(DEFAULT_DATA_INICIO.toString()))
            .jsonPath("$.[*].dataTermino")
            .value(hasItem(DEFAULT_DATA_TERMINO.toString()))
            .jsonPath("$.[*].deslocamento")
            .value(hasItem(DEFAULT_DESLOCAMENTO.booleanValue()))
            .jsonPath("$.[*].passagemAerea")
            .value(hasItem(DEFAULT_PASSAGEM_AEREA.booleanValue()))
            .jsonPath("$.[*].status")
            .value(hasItem(DEFAULT_STATUS.toString()))
            .jsonPath("$.[*].osverificada")
            .value(hasItem(DEFAULT_OSVERIFICADA.booleanValue()))
            .jsonPath("$.[*].acao")
            .value(hasItem(DEFAULT_ACAO.toString()))
            .jsonPath("$.[*].valorTotalMissao")
            .value(hasItem(DEFAULT_VALOR_TOTAL_MISSAO.toString()))
            .jsonPath("$.[*].valorDiariasRealizadas")
            .value(hasItem(DEFAULT_VALOR_DIARIAS_REALIZADAS.doubleValue()))
            .jsonPath("$.[*].saldoDisponivel")
            .value(hasItem(DEFAULT_SALDO_DISPONIVEL.doubleValue()));
    }

    @Test
    void getMissao() {
        // Initialize the database
        missaoRepository.save(missao).block();

        // Get the missao
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, missao.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id")
            .value(is(missao.getId().intValue()))
            .jsonPath("$.nomeMissao")
            .value(is(DEFAULT_NOME_MISSAO))
            .jsonPath("$.prioridade")
            .value(is(DEFAULT_PRIORIDADE.toString()))
            .jsonPath("$.quantidadeDiaria")
            .value(is(DEFAULT_QUANTIDADE_DIARIA.intValue()))
            .jsonPath("$.meiaDiaria")
            .value(is(DEFAULT_MEIA_DIARIA.booleanValue()))
            .jsonPath("$.quantidadeEquipe")
            .value(is(DEFAULT_QUANTIDADE_EQUIPE))
            .jsonPath("$.dataInicio")
            .value(is(DEFAULT_DATA_INICIO.toString()))
            .jsonPath("$.dataTermino")
            .value(is(DEFAULT_DATA_TERMINO.toString()))
            .jsonPath("$.deslocamento")
            .value(is(DEFAULT_DESLOCAMENTO.booleanValue()))
            .jsonPath("$.passagemAerea")
            .value(is(DEFAULT_PASSAGEM_AEREA.booleanValue()))
            .jsonPath("$.status")
            .value(is(DEFAULT_STATUS.toString()))
            .jsonPath("$.osverificada")
            .value(is(DEFAULT_OSVERIFICADA.booleanValue()))
            .jsonPath("$.acao")
            .value(is(DEFAULT_ACAO.toString()))
            .jsonPath("$.valorTotalMissao")
            .value(is(DEFAULT_VALOR_TOTAL_MISSAO.toString()))
            .jsonPath("$.valorDiariasRealizadas")
            .value(is(DEFAULT_VALOR_DIARIAS_REALIZADAS.doubleValue()))
            .jsonPath("$.saldoDisponivel")
            .value(is(DEFAULT_SALDO_DISPONIVEL.doubleValue()));
    }

    @Test
    void getNonExistingMissao() {
        // Get the missao
        webTestClient
            .get()
            .uri(ENTITY_API_URL_ID, Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_PROBLEM_JSON)
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void putExistingMissao() throws Exception {
        // Initialize the database
        missaoRepository.save(missao).block();

        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();

        // Update the missao
        Missao updatedMissao = missaoRepository.findById(missao.getId()).block();
        updatedMissao
            .nomeMissao(UPDATED_NOME_MISSAO)
            .prioridade(UPDATED_PRIORIDADE)
            .quantidadeDiaria(UPDATED_QUANTIDADE_DIARIA)
            .meiaDiaria(UPDATED_MEIA_DIARIA)
            .quantidadeEquipe(UPDATED_QUANTIDADE_EQUIPE)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataTermino(UPDATED_DATA_TERMINO)
            .deslocamento(UPDATED_DESLOCAMENTO)
            .passagemAerea(UPDATED_PASSAGEM_AEREA)
            .status(UPDATED_STATUS)
            .osverificada(UPDATED_OSVERIFICADA)
            .acao(UPDATED_ACAO)
            .valorTotalMissao(UPDATED_VALOR_TOTAL_MISSAO)
            .valorDiariasRealizadas(UPDATED_VALOR_DIARIAS_REALIZADAS)
            .saldoDisponivel(UPDATED_SALDO_DISPONIVEL);
        MissaoDTO missaoDTO = missaoMapper.toDto(updatedMissao);

        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, missaoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
        Missao testMissao = missaoList.get(missaoList.size() - 1);
        assertThat(testMissao.getNomeMissao()).isEqualTo(UPDATED_NOME_MISSAO);
        assertThat(testMissao.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testMissao.getQuantidadeDiaria()).isEqualTo(UPDATED_QUANTIDADE_DIARIA);
        assertThat(testMissao.getMeiaDiaria()).isEqualTo(UPDATED_MEIA_DIARIA);
        assertThat(testMissao.getQuantidadeEquipe()).isEqualTo(UPDATED_QUANTIDADE_EQUIPE);
        assertThat(testMissao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testMissao.getDataTermino()).isEqualTo(UPDATED_DATA_TERMINO);
        assertThat(testMissao.getDeslocamento()).isEqualTo(UPDATED_DESLOCAMENTO);
        assertThat(testMissao.getPassagemAerea()).isEqualTo(UPDATED_PASSAGEM_AEREA);
        assertThat(testMissao.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMissao.getOsverificada()).isEqualTo(UPDATED_OSVERIFICADA);
        assertThat(testMissao.getAcao()).isEqualTo(UPDATED_ACAO);
        assertThat(testMissao.getValorTotalMissao()).isEqualTo(UPDATED_VALOR_TOTAL_MISSAO);
        assertThat(testMissao.getValorDiariasRealizadas()).isEqualTo(UPDATED_VALOR_DIARIAS_REALIZADAS);
        assertThat(testMissao.getSaldoDisponivel()).isEqualTo(UPDATED_SALDO_DISPONIVEL);
    }

    @Test
    void putNonExistingMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, missaoDTO.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .put()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMissaoWithPatch() throws Exception {
        // Initialize the database
        missaoRepository.save(missao).block();

        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();

        // Update the missao using partial update
        Missao partialUpdatedMissao = new Missao();
        partialUpdatedMissao.setId(missao.getId());

        partialUpdatedMissao
            .quantidadeDiaria(UPDATED_QUANTIDADE_DIARIA)
            .quantidadeEquipe(UPDATED_QUANTIDADE_EQUIPE)
            .dataTermino(UPDATED_DATA_TERMINO)
            .deslocamento(UPDATED_DESLOCAMENTO)
            .osverificada(UPDATED_OSVERIFICADA)
            .saldoDisponivel(UPDATED_SALDO_DISPONIVEL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMissao.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMissao))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
        Missao testMissao = missaoList.get(missaoList.size() - 1);
        assertThat(testMissao.getNomeMissao()).isEqualTo(DEFAULT_NOME_MISSAO);
        assertThat(testMissao.getPrioridade()).isEqualTo(DEFAULT_PRIORIDADE);
        assertThat(testMissao.getQuantidadeDiaria()).isEqualTo(UPDATED_QUANTIDADE_DIARIA);
        assertThat(testMissao.getMeiaDiaria()).isEqualTo(DEFAULT_MEIA_DIARIA);
        assertThat(testMissao.getQuantidadeEquipe()).isEqualTo(UPDATED_QUANTIDADE_EQUIPE);
        assertThat(testMissao.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testMissao.getDataTermino()).isEqualTo(UPDATED_DATA_TERMINO);
        assertThat(testMissao.getDeslocamento()).isEqualTo(UPDATED_DESLOCAMENTO);
        assertThat(testMissao.getPassagemAerea()).isEqualTo(DEFAULT_PASSAGEM_AEREA);
        assertThat(testMissao.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMissao.getOsverificada()).isEqualTo(UPDATED_OSVERIFICADA);
        assertThat(testMissao.getAcao()).isEqualTo(DEFAULT_ACAO);
        assertThat(testMissao.getValorTotalMissao()).isEqualTo(DEFAULT_VALOR_TOTAL_MISSAO);
        assertThat(testMissao.getValorDiariasRealizadas()).isEqualTo(DEFAULT_VALOR_DIARIAS_REALIZADAS);
        assertThat(testMissao.getSaldoDisponivel()).isEqualTo(UPDATED_SALDO_DISPONIVEL);
    }

    @Test
    void fullUpdateMissaoWithPatch() throws Exception {
        // Initialize the database
        missaoRepository.save(missao).block();

        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();

        // Update the missao using partial update
        Missao partialUpdatedMissao = new Missao();
        partialUpdatedMissao.setId(missao.getId());

        partialUpdatedMissao
            .nomeMissao(UPDATED_NOME_MISSAO)
            .prioridade(UPDATED_PRIORIDADE)
            .quantidadeDiaria(UPDATED_QUANTIDADE_DIARIA)
            .meiaDiaria(UPDATED_MEIA_DIARIA)
            .quantidadeEquipe(UPDATED_QUANTIDADE_EQUIPE)
            .dataInicio(UPDATED_DATA_INICIO)
            .dataTermino(UPDATED_DATA_TERMINO)
            .deslocamento(UPDATED_DESLOCAMENTO)
            .passagemAerea(UPDATED_PASSAGEM_AEREA)
            .status(UPDATED_STATUS)
            .osverificada(UPDATED_OSVERIFICADA)
            .acao(UPDATED_ACAO)
            .valorTotalMissao(UPDATED_VALOR_TOTAL_MISSAO)
            .valorDiariasRealizadas(UPDATED_VALOR_DIARIAS_REALIZADAS)
            .saldoDisponivel(UPDATED_SALDO_DISPONIVEL);

        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, partialUpdatedMissao.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(partialUpdatedMissao))
            .exchange()
            .expectStatus()
            .isOk();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
        Missao testMissao = missaoList.get(missaoList.size() - 1);
        assertThat(testMissao.getNomeMissao()).isEqualTo(UPDATED_NOME_MISSAO);
        assertThat(testMissao.getPrioridade()).isEqualTo(UPDATED_PRIORIDADE);
        assertThat(testMissao.getQuantidadeDiaria()).isEqualTo(UPDATED_QUANTIDADE_DIARIA);
        assertThat(testMissao.getMeiaDiaria()).isEqualTo(UPDATED_MEIA_DIARIA);
        assertThat(testMissao.getQuantidadeEquipe()).isEqualTo(UPDATED_QUANTIDADE_EQUIPE);
        assertThat(testMissao.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testMissao.getDataTermino()).isEqualTo(UPDATED_DATA_TERMINO);
        assertThat(testMissao.getDeslocamento()).isEqualTo(UPDATED_DESLOCAMENTO);
        assertThat(testMissao.getPassagemAerea()).isEqualTo(UPDATED_PASSAGEM_AEREA);
        assertThat(testMissao.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMissao.getOsverificada()).isEqualTo(UPDATED_OSVERIFICADA);
        assertThat(testMissao.getAcao()).isEqualTo(UPDATED_ACAO);
        assertThat(testMissao.getValorTotalMissao()).isEqualTo(UPDATED_VALOR_TOTAL_MISSAO);
        assertThat(testMissao.getValorDiariasRealizadas()).isEqualTo(UPDATED_VALOR_DIARIAS_REALIZADAS);
        assertThat(testMissao.getSaldoDisponivel()).isEqualTo(UPDATED_SALDO_DISPONIVEL);
    }

    @Test
    void patchNonExistingMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, missaoDTO.getId())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL_ID, longCount.incrementAndGet())
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isBadRequest();

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMissao() throws Exception {
        int databaseSizeBeforeUpdate = missaoRepository.findAll().collectList().block().size();
        missao.setId(longCount.incrementAndGet());

        // Create the Missao
        MissaoDTO missaoDTO = missaoMapper.toDto(missao);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        webTestClient
            .patch()
            .uri(ENTITY_API_URL)
            .contentType(MediaType.valueOf("application/merge-patch+json"))
            .bodyValue(TestUtil.convertObjectToJsonBytes(missaoDTO))
            .exchange()
            .expectStatus()
            .isEqualTo(405);

        // Validate the Missao in the database
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMissao() {
        // Initialize the database
        missaoRepository.save(missao).block();

        int databaseSizeBeforeDelete = missaoRepository.findAll().collectList().block().size();

        // Delete the missao
        webTestClient
            .delete()
            .uri(ENTITY_API_URL_ID, missao.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus()
            .isNoContent();

        // Validate the database contains one less item
        List<Missao> missaoList = missaoRepository.findAll().collectList().block();
        assertThat(missaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
