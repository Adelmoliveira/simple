package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Diaria;
import com.mycompany.myapp.domain.enumeration.DiariaLocalidadeEnum;
import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Diaria}, with proper type conversions.
 */
@Service
public class DiariaRowMapper implements BiFunction<Row, String, Diaria> {

    private final ColumnConverter converter;

    public DiariaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Diaria} stored in the database.
     */
    @Override
    public Diaria apply(Row row, String prefix) {
        Diaria entity = new Diaria();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setCidade(converter.fromRow(row, prefix + "_cidade", String.class));
        entity.setOficialSup(converter.fromRow(row, prefix + "_oficial_sup", Double.class));
        entity.setOficial(converter.fromRow(row, prefix + "_oficial", Double.class));
        entity.setGraduado(converter.fromRow(row, prefix + "_graduado", Double.class));
        entity.setPraca(converter.fromRow(row, prefix + "_praca", Double.class));
        entity.setCivil(converter.fromRow(row, prefix + "_civil", Double.class));
        entity.setLocalidade(converter.fromRow(row, prefix + "_localidade", DiariaLocalidadeEnum.class));
        entity.setMissaoId(converter.fromRow(row, prefix + "_missao_id", Long.class));
        return entity;
    }
}
