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

<%@ include file="/panel/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel:panelCategory");
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
%>

<div class="list-group">

	<%
	for (PanelCategory childPanelCategory : panelCategoryRegistry.getChildPanelCategories(panelCategory)) {
	%>

			<liferay-application-list:panel-category-content panelCategory="<%= childPanelCategory %>" />

	<%
	}
	%>

</div>