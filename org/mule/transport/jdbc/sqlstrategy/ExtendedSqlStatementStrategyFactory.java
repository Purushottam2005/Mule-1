package org.mule.transport.jdbc.sqlstrategy;

import org.mule.transport.jdbc.sqlstrategy.DefaultSqlStatementStrategyFactory;

/**
 * author: Anton Kupias
 * adapted from https://svn.codehaus.org/mule/tags/mule-3.3.0/transports/jdbc/src/main/java/org/mule/transport/jdbc/sqlstrategy/DefaultSqlStatementStrategyFactory.java
 * extends DefaultSqlStatementStrategyFactory.java with support for "create table" statements
 */

public class ExtendedSqlStatementStrategyFactory extends DefaultSqlStatementStrategyFactory
{
    protected SimpleCreateSqlStatementStrategy simpleCreateSQLStrategy;
    protected SimpleInsertSqlStatementStrategy simpleInsertSQLStrategy;
    
    public ExtendedSqlStatementStrategyFactory()
    {
    	super();
    	simpleCreateSQLStrategy = new SimpleCreateSqlStatementStrategy();
        simpleInsertSQLStrategy = new SimpleInsertSqlStatementStrategy();
    }

    public SqlStatementStrategy create(String sql, Object payload)
        throws Exception
    {
        String sqlLowerCase = sql.toLowerCase();

        if(sqlLowerCase.startsWith("create"))
        {
                return simpleCreateSQLStrategy;
        }
        else if(sqlLowerCase.startsWith("insert"))
        {
                return simpleInsertSQLStrategy;
        }
        else
        {
        	return super.create(sql, payload);
        }
    }

}
