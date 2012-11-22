The DefaultSqlStatementStrategyFactory used by Mule ESB does not support CREATE statements, so I wrote this simple ExtendedSqlStatementStrategyFactory to extend that default functionality. 

This is for Mule version 3.3, and won't work for previous versions. I might share a version for 3.2. later, it's just a few lines of changes.

Use it like this:

    <jdbc:connector name="jdbcConnector" dataSource-ref="dataSource" doc:name="Database (JDBC)">
        <jdbc:sqlStatementStrategyFactory class="org.mule.transport.jdbc.sqlstrategy.ExtendedSqlStatementStrategyFactory"/>
