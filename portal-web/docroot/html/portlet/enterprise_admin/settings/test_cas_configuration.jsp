<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String casLoginUrl = ParamUtil.getString(request, "casLoginUrl");
String casLogoutUrl = ParamUtil.getString(request, "casLogoutUrl");
String casServerUrl = ParamUtil.getString(request, "casServerUrl");
String casServiceUrl = ParamUtil.getString(request, "casServiceUrl");
%>

<p>
Checking CAS Configuration URLs
</p>

<p>
<liferay-ui:message key="login-url" />:  <%=casLoginUrl %> - <liferay-ui:message key="<%=CasUtil.checkUrlAvailability(casLoginUrl) %>" />
</p>
<p>
<liferay-ui:message key="logout-url" />: <%=casLogoutUrl %> -  <liferay-ui:message key="<%=CasUtil.checkUrlAvailability(casLogoutUrl) %>" />
</p>

<%
if (!casServerUrl.equals(StringPool.BLANK)) {
%>
<p>
<liferay-ui:message key="server-url" />: <%=casServerUrl %> -  <liferay-ui:message key="<%=CasUtil.checkUrlAvailability(casServerUrl) %>" />
</p>
<%
}
%>

<%
if (!casServiceUrl.equals(StringPool.BLANK)) {
%>
<p>
<liferay-ui:message key="service-url" />: <%=casServiceUrl %> -  <liferay-ui:message key="<%=CasUtil.checkUrlAvailability(casServiceUrl) %>" />
</p>
<%
}
%>




