package persistence.sql.ddl;

import persistence.dialect.Dialect;
import sources.ColumnMetaData;
import sources.MetaData;

import java.util.List;

public class QueryBuilder {

    Dialect dialect;
    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public StringBuilder create(MetaData metaData, StringBuilder sb) {
        return sb.append("create table ")
                .append(metaData.getEntity())
                .append(" (")
                .append(metaData.getId())
                .append(" ")
                .append(columnTypeName(metaData.getColumns()))
                .append(" )")
        ;

    }

    private String columnTypeName(List<ColumnMetaData> columns) {
        StringBuilder columnQuery = new StringBuilder();
        for (ColumnMetaData column : columns) {
            columnQuery.append(columnQueryBuilder(column));
        }
        return columnQuery.toString();
    }

    private String columnQueryBuilder(ColumnMetaData columnMetaData) {
        if(columnMetaData.isNullable()) {
            return ", "+ columnMetaData.getName() + " "+ dialect.transferType(columnMetaData.getType()) + " ";
        }
        return ", "+columnMetaData.getName() + " " + dialect.transferType(columnMetaData.getType()) + " not null";
    }
}
