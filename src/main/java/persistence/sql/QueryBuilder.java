package persistence.sql;

import persistence.dialect.Dialect;

public abstract class QueryBuilder {

    protected Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

}
