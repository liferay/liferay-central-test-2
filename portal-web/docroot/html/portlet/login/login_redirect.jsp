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

<%@ include file="/html/portlet/login/init.jsp" %>

<%
Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());

String emailAddress = ParamUtil.getString(request, "emailAddress");
%>

<aui:script use="aui-base">
	if (window.opener) {
		var randomNamespace = window.opener.parent.randomNamespace;

		var afterLogin = window.opener.parent[randomNamespace + 'afterLogin'];

		if (typeof(afterLogin) == 'function') {
			afterLogin('<%= HtmlUtil.escape(emailAddress) %>');
		}

		window.opener.location.reload();

		window.close();
	}
	else {
		var randomNamespace = window.parent.randomNamespace;

		var afterLogin = window.parent[randomNamespace + 'afterLogin'];

		afterLogin('<%= HtmlUtil.escape(emailAddress) %>');

		window.parent.Liferay.fire(
			'close',
			{
				frame: window,
				portletAjaxable: '<%= selPortlet.isAjaxable() %>',
				refresh: '<%= portletDisplay.getId() %>'
			}
		);
	}
</aui:script>