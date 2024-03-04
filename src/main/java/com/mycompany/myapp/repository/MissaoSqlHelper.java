package com.mycompany.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class MissaoSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("nome_missao", table, columnPrefix + "_nome_missao"));
        columns.add(Column.aliased("prioridade", table, columnPrefix + "_prioridade"));
        columns.add(Column.aliased("quantidade_diaria", table, columnPrefix + "_quantidade_diaria"));
        columns.add(Column.aliased("meia_diaria", table, columnPrefix + "_meia_diaria"));
        columns.add(Column.aliased("quantidade_equipe", table, columnPrefix + "_quantidade_equipe"));
        columns.add(Column.aliased("data_inicio", table, columnPrefix + "_data_inicio"));
        columns.add(Column.aliased("data_termino", table, columnPrefix + "_data_termino"));
        columns.add(Column.aliased("deslocamento", table, columnPrefix + "_deslocamento"));
        columns.add(Column.aliased("passagem_aerea", table, columnPrefix + "_passagem_aerea"));
        columns.add(Column.aliased("status", table, columnPrefix + "_status"));
        columns.add(Column.aliased("osverificada", table, columnPrefix + "_osverificada"));
        columns.add(Column.aliased("acao", table, columnPrefix + "_acao"));
        columns.add(Column.aliased("valor_total_missao", table, columnPrefix + "_valor_total_missao"));
        columns.add(Column.aliased("valor_diarias_realizadas", table, columnPrefix + "_valor_diarias_realizadas"));
        columns.add(Column.aliased("saldo_disponivel", table, columnPrefix + "_saldo_disponivel"));

        columns.add(Column.aliased("municipio_id", table, columnPrefix + "_municipio_id"));
        return columns;
    }
}
