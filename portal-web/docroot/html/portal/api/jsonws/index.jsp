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

<%@ include file="/html/portal/api/jsonws/init.jsp" %>

<style type="text/css">
	<%@ include file="/html/portal/api/jsonws/css.jspf" %>
</style>

<div id="header">
	<div id="header-content">
		<h1><a href="jsonws">JSONWS API</a></h1>
	</div>
</div>

<div id="content">

	<%
	String signature = ParamUtil.getString(request, "signature");

	if (Validator.isNull(signature)) {
	%>

		<%@ include file="/html/portal/api/jsonws/actions.jspf" %>

	<%
	}
	else {
	%>

		<%@ include file="/html/portal/api/jsonws/action.jspf" %>

	<%
	}
	%>

</div>

<div id="footer">
	<div id="footer-content">

		<%
		Calendar calendar = CalendarFactoryUtil.getCalendar();
		%>

		Copyright (c) 2000-<%= calendar.get(Calendar.YEAR) %> Liferay, Inc. All rights reserved.
	</div>
</div>