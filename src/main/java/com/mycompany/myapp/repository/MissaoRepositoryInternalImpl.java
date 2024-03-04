package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Missao;
import com.mycompany.myapp.repository.rowmapper.CidadesRowMapper;
import com.mycompany.myapp.repository.rowmapper.MissaoRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Missao entity.
 */
@SuppressWarnings("unused")
class MissaoRepositoryInternalImpl extends SimpleR2dbcRepository<Missao, Long> implements MissaoRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final CidadesRowMapper cidadesMapper;
    private final MissaoRowMapper missaoMapper;

    private static final Table entityTable = Table.aliased("missao", EntityManager.ENTITY_ALIAS);
    private static final Table municipioTable = Table.aliased("cidades", "municipio");

    public MissaoRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CidadesRowMapper cidadesMapper,
        MissaoRowMapper missaoMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Missao.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.cidadesMapper = cidadesMapper;
        this.missaoMapper = missaoMapper;
    }

    @Override
    public Flux<Missao> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Missao> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = MissaoSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(CidadesSqlHelper.getColumns(municipioTable, "municipio"));
        SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(municipioTable)
            .on(Column.create("municipio_id", entityTable))
            .equals(Column.create("id", municipioTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Missao.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Missao> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Missao> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Missao process(Row row, RowMetadata metadata) {
        Missao entity = missaoMapper.apply(row, "e");
        entity.setMunicipio(cidadesMapper.apply(row, "municipio"));
        return entity;
    }

    @Override
    public <S extends Missao> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
