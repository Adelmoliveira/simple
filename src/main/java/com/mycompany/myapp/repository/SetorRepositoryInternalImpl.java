package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Setor;
import com.mycompany.myapp.repository.rowmapper.ServidorRowMapper;
import com.mycompany.myapp.repository.rowmapper.SetorRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Comparison;
import org.springframework.data.relational.core.sql.Condition;
import org.springframework.data.relational.core.sql.Conditions;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Select;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoinCondition;
import org.springframework.data.relational.core.sql.Table;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Setor entity.
 */
@SuppressWarnings("unused")
class SetorRepositoryInternalImpl extends SimpleR2dbcRepository<Setor, Long> implements SetorRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ServidorRowMapper servidorMapper;
    private final SetorRowMapper setorMapper;

    private static final Table entityTable = Table.aliased("setor", EntityManager.ENTITY_ALIAS);
    private static final Table servidorTable = Table.aliased("servidor", "servidor");

    public SetorRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ServidorRowMapper servidorMapper,
        SetorRowMapper setorMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Setor.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.servidorMapper = servidorMapper;
        this.setorMapper = setorMapper;
    }

    @Override
    public Flux<Setor> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Setor> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = SetorSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(ServidorSqlHelper.getColumns(servidorTable, "servidor"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(servidorTable)
            .on(Column.create("servidor_id", entityTable))
            .equals(Column.create("id", servidorTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Setor.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Setor> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Setor> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Setor process(Row row, RowMetadata metadata) {
        Setor entity = setorMapper.apply(row, "e");
        entity.setServidor(servidorMapper.apply(row, "servidor"));
        return entity;
    }

    @Override
    public <S extends Setor> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
