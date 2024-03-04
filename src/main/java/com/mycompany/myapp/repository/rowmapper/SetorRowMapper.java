package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Setor;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Setor}, with proper type conversions.
 */
@Service
public class SetorRowMapper implements BiFunction<Row, String, Setor> {

    private final ColumnConverter converter;

    public SetorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Setor} stored in the database.
     */
    @Override
    public Setor apply(Row row, String prefix) {
        Setor entity = new Setor();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNome(converter.fromRow(row, prefix + "_nome", String.class));
        entity.setSigla(converter.fromRow(row, prefix + "_sigla", String.class));
        entity.setServidorId(converter.fromRow(row, prefix + "_servidor_id", Long.class));
        return entity;
    }
}
