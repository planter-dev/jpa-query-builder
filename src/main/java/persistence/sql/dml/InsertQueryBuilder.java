package persistence.sql.dml;

import persistence.dialect.Dialect;
import persistence.sql.QueryBuilder;

public class InsertQueryBuilder extends QueryBuilder {
    public InsertQueryBuilder(Dialect dialect) {
        super(dialect);
    }
}