package com.mycompany.myapp.repository.rowmapper;

import com.mycompany.myapp.domain.Missao;
import com.mycompany.myapp.domain.enumeration.AcaoEnum;
import com.mycompany.myapp.domain.enumeration.OrcamentoEnum;
import com.mycompany.myapp.domain.enumeration.Prioridade;
import com.mycompany.myapp.domain.enumeration.StatusEnum;
import io.r2dbc.spi.Row;
import java.time.LocalDate;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Missao}, with proper type conversions.
 */
@Service
public class MissaoRowMapper implements BiFunction<Row, String, Missao> {

    private final ColumnConverter converter;

    public MissaoRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Missao} stored in the database.
     */
    @Override
    public Missao apply(Row row, String prefix) {
        Missao entity = new Missao();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setNomeMissao(converter.fromRow(row, prefix + "_nome_missao", String.class));
        entity.setPrioridade(converter.fromRow(row, prefix + "_prioridade", Prioridade.class));
        entity.setQuantidadeDiaria(converter.fromRow(row, prefix + "_quantidade_diaria", Long.class));
        entity.setMeiaDiaria(converter.fromRow(row, prefix + "_meia_diaria", Boolean.class));
        entity.setQuantidadeEquipe(converter.fromRow(row, prefix + "_quantidade_equipe", Integer.class));
        entity.setDataInicio(converter.fromRow(row, prefix + "_data_inicio", LocalDate.class));
        entity.setDataTermino(converter.fromRow(row, prefix + "_data_termino", LocalDate.class));
        entity.setDeslocamento(converter.fromRow(row, prefix + "_deslocamento", Boolean.class));
        entity.setPassagemAerea(converter.fromRow(row, prefix + "_passagem_aerea", Boolean.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", StatusEnum.class));
        entity.setOsverificada(converter.fromRow(row, prefix + "_osverificada", Boolean.class));
        entity.setAcao(converter.fromRow(row, prefix + "_acao", AcaoEnum.class));
        entity.setValorTotalMissao(converter.fromRow(row, prefix + "_valor_total_missao", OrcamentoEnum.class));
        entity.setValorDiariasRealizadas(converter.fromRow(row, prefix + "_valor_diarias_realizadas", Double.class));
        entity.setSaldoDisponivel(converter.fromRow(row, prefix + "_saldo_disponivel", Double.class));
        entity.setMunicipioId(converter.fromRow(row, prefix + "_municipio_id", Long.class));
        return entity;
    }
}
