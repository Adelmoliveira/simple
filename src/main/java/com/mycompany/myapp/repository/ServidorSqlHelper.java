package com.mycompany.myapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class ServidorSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("foto", table, columnPrefix + "_foto"));
        columns.add(Column.aliased("foto_content_type", table, columnPrefix + "_foto_content_type"));
        columns.add(Column.aliased("nome", table, columnPrefix + "_nome"));
        columns.add(Column.aliased("sobre_nome", table, columnPrefix + "_sobre_nome"));
        columns.add(Column.aliased("email", table, columnPrefix + "_email"));
        columns.add(Column.aliased("celular", table, columnPrefix + "_celular"));
        columns.add(Column.aliased("posto", table, columnPrefix + "_posto"));

        return columns;
    }
}
