package org.mule.transport.jdbc.sqlstrategy;

import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.transport.jdbc.JdbcConnector;
import org.mule.transport.jdbc.sqlstrategy.SqlStatementStrategy;
import org.mule.util.ArrayUtils;
 
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
 
import org.apache.log4j.Logger;
/**
 * author: Anton Kupias
 * adapted from https://svn.codehaus.org/mule/tags/mule-3.3.0/transports/jdbc/src/main/java/org/mule/transport/jdbc/sqlstrategy/SimpleUpdateSqlStatementStrategy.java
 * Implements strategy for handling create statements
 */
public  class SimpleCreateSqlStatementStrategy implements SqlStatementStrategy
{
    protected transient Logger logger = Logger.getLogger(getClass());
     
	@Override
	public MuleMessage executeStatement(JdbcConnector connector,
			ImmutableEndpoint endpoint, MuleEvent event, long timeout, Connection connection) throws Exception {
		
        //Unparsed SQL statement (with #[foo] format parameters)
        String statement = connector.getStatement(endpoint);
 
        //Storage for parameters
        List paramNames = new ArrayList();
         
        //Parsed SQL statement (with ? placeholders instead of #[foo] params)
        String sql = connector.parseStatement(statement, paramNames);
         
        //Get parameter values from message
        MuleMessage message = event.getMessage();
        Object[] paramValues = connector.getParams(endpoint, paramNames, new DefaultMuleMessage(
            event.getMessage().getPayload(), message, event.getMuleContext()), endpoint.getEndpointURI().getAddress());
 
        if (logger.isDebugEnabled())
        {
            logger.debug("SQL CREATE: " + sql + ", params = " + ArrayUtils.toString(paramValues));
        }
        
        //this is where we format the create statement                
        StringTokenizer st = new StringTokenizer(sql,"?");
        StringBuffer sb = new StringBuffer("");
        for (Object param : paramValues) {
        	sb.append(st.nextToken());
        	sb.append((String)param);            	
        }
    	sb.append(st.nextToken());       	
        
    	//"update" will work just fine, QueryRunner does not explicitly support "create" statements
        connector.getQueryRunnerFor(endpoint).update(connection, sb.toString());
        
        logger.debug("MuleEvent dispatched succesfully");
         
        return event.getMessage();
    }
     
}
