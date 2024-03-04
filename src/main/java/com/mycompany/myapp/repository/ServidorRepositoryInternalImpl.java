package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Equipe;
import com.mycompany.myapp.domain.Servidor;
import com.mycompany.myapp.repository.rowmapper.ServidorRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Servidor entity.
 */
@SuppressWarnings("unused")
class ServidorRepositoryInternalImpl extends SimpleR2dbcRepository<Servidor, Long> implements ServidorRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ServidorRowMapper servidorMapper;

    private static final Table entityTable = Table.aliased("servidor", EntityManager.ENTITY_ALIAS);

    private static final EntityManager.LinkTable equipesLink = new EntityManager.LinkTable(
        "rel_servidor__equipes",
        "servidor_id",
        "equipes_id"
    );

    public ServidorRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ServidorRowMapper servidorMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Servidor.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.servidorMapper = servidorMapper;
    }

    @Override
    public Flux<Servidor> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Servidor> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ServidorSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Servidor.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Servidor> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Servidor> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Servidor> findOneWithEagerRelationships(Long id) {
        return findById(id);
    }

    @Override
    public Flux<Servidor> findAllWithEagerRelationships() {
        return findAll();
    }

    @Override
    public Flux<Servidor> findAllWithEagerRelationships(Pageable page) {
        return findAllBy(page);
    }

    private Servidor process(Row row, RowMetadata metadata) {
        Servidor entity = servidorMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Servidor> Mono<S> save(S entity) {
        return super.save(entity).flatMap((S e) -> updateRelations(e));
    }

    protected <S extends Servidor> Mono<S> updateRelations(S entity) {
        Mono<Void> result = entityManager
            .updateLinkTable(equipesLink, entity.getId(), entity.getEquipes().stream().map(Equipe::getId))
            .then();
        return result.thenReturn(entity);
    }

    @Override
    public Mono<Void> deleteById(Long entityId) {
        return deleteRelations(entityId).then(super.deleteById(entityId));
    }

    protected Mono<Void> deleteRelations(Long entityId) {
        return entityManager.deleteFromLinkTable(equipesLink, entityId);
    }
}
