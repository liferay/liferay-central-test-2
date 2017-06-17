<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>

<%
String id = GetterUtil.getString((String)request.getAttribute("liferay-ui:icon-menu:id"));
boolean scroll = GetterUtil.getBoolean(request.getAttribute("liferay-ui:icon-menu:scroll"));
%>

		</ul>
	<c:if test="<%= scroll %>">
		</div>
	</c:if>
</div>

<aui:script use="liferay-menu">
	Liferay.Menu.handleFocus('#<%= id %>menu');
</aui:script>