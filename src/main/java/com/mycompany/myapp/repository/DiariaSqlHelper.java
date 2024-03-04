package com.mycompany.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class DiariaSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("cidade", table, columnPrefix + "_cidade"));
        columns.add(Column.aliased("oficial_sup", table, columnPrefix + "_oficial_sup"));
        columns.add(Column.aliased("oficial", table, columnPrefix + "_oficial"));
        columns.add(Column.aliased("graduado", table, columnPrefix + "_graduado"));
        columns.add(Column.aliased("praca", table, columnPrefix + "_praca"));
        columns.add(Column.aliased("civil", table, columnPrefix + "_civil"));
        columns.add(Column.aliased("localidade", table, columnPrefix + "_localidade"));

        columns.add(Column.aliased("missao_id", table, columnPrefix + "_missao_id"));
        return columns;
    }
}
