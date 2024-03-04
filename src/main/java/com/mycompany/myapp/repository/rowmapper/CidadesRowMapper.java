package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Cidades;
import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import com.mycompany.myapp.domain.enumeration.LocalidadeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Cidades}, with proper type conversions.
 */
@Service
public class CidadesRowMapper implements BiFunction<Row, String, Cidades> {

    private final ColumnConverter converter;

    public CidadesRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Cidades} stored in the database.
     */
    @Override
    public Cidades apply(Row row, String prefix) {
        Cidades entity = new Cidades();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCidade(converter.fromRow(row, prefix + "_cidade", DiariaLocalidadeEnum.class));
        entity.setValorLocalidade(converter.fromRow(row, prefix + "_valor_localidade", LocalidadeEnum.class));
        return entity;
    }
}
