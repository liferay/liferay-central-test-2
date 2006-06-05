<%@ page import="com.sample.dao.util.ConnectionPool" %>

<%@ page import="java.util.Enumeration" %>
<%@ page import="java.util.Properties" %>

<span class="portlet-msg-error">
An unexpected error occurred.
</span>

<br><br>

Please check that these database settings are correct:

<br><br>

<%
Properties props = ConnectionPool.getProperties();

Enumeration enu = props.propertyNames();

while (enu.hasMoreElements()) {
	String key = (String)enu.nextElement();

	String value = props.getProperty(key);
%>

	<b><%= key %></b>=<%= value%><br>

<%
}
%>

<br>

You can change the database settings by modifying /WEB-INF/classes/connection-pool.properties.

<br><br>

The SQL script to build the database is found in /WEB-INF/sql/sample_dao.sql.

<br><br>

This portlet requires access to a <a href="http://www.mysql.com">MySQL</a> server.