I use this repo for stuff I use with Mule ESB.
----------------------------------------------

The DefaultSqlStatementStrategyFactory used by Mule ESB does not support CREATE statements, so I wrote this simple ExtendedSqlStatementStrategyFactory to extend that default functionality. 

Another feature I needed, is a way dynamically define the table name and fields for INSERTs, as the default implementation escapes all parameters. The SimpleInsertSqlStatementStrategy implements this.

This is for Mule version 3.3, and won't work for previous versions. I might share a version for 3.2. later, it's just a few lines of changes. If you really need it ASAP, just open these files in Mule Studio 3.2 and fix the stuff that gives errors... :)

Use it like this:

    <jdbc:connector name="jdbcConnector" dataSource-ref="dataSource" doc:name="Database (JDBC)">
        <jdbc:sqlStatementStrategyFactory class="org.mule.transport.jdbc.sqlstrategy.ExtendedSqlStatementStrategyFactory"/>

(and put the files in classpath) 
