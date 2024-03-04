package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Servidor;
import com.mycompany.myapp.domain.enumeration.PostoEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Servidor}, with proper type conversions.
 */
@Service
public class ServidorRowMapper implements BiFunction<Row, String, Servidor> {

    private final ColumnConverter converter;

    public ServidorRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Servidor} stored in the database.
     */
    @Override
    public Servidor apply(Row row, String prefix) {
        Servidor entity = new Servidor();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFotoContentType(converter.fromRow(row, prefix + "_foto_content_type", String.class));
        entity.setFoto(converter.fromRow(row, prefix + "_foto", byte[].class));
        entity.setNome(converter.fromRow(row, prefix + "_nome", String.class));
        entity.setSobreNome(converter.fromRow(row, prefix + "_sobre_nome", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setCelular(converter.fromRow(row, prefix + "_celular", String.class));
        entity.setPosto(converter.fromRow(row, prefix + "_posto", PostoEnum.class));
        return entity;
    }
}
