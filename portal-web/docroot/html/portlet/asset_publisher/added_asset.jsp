<%--
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
Portlet selPortlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), portletDisplay.getId());
%>

<aui:script use="aui-base">
	if (window.parent) {
		<c:choose>
			<c:when test="<%= !selPortlet.isAjaxable() %>">
				window.parent.location.reload();
			</c:when>
			<c:otherwise>
				var curPortletBoundaryId = '#p_p_id_<%= portletDisplay.getId() %>_';

				window.parent.Liferay.Portlet.refresh(curPortletBoundaryId);
			</c:otherwise>
		</c:choose>
	}

	window.parent.Liferay.fire('closeDialog');
</aui:script>