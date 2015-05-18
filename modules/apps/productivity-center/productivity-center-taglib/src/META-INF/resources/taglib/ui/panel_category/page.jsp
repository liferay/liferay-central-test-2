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

<%@ include file="/taglib/ui/panel_category/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ProductivityCenterWebKeys.PANEL_APP_REGISTRY);
PanelCategory panelCategory = (PanelCategory)request.getAttribute("productivity-center-ui:panel-category:panelCategory");

PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategory);

String panelPageCategoryId = "panel-manage-" + panelCategory.getKey();
%>

<liferay-ui:panel
	collapsible="<%= true %>"
	cssClass="list-unstyled panel-page-category"
	extended="<%= true %>"
	iconCssClass="<%= panelCategory.getIconCssClass() %>"
	id="<%= panelPageCategoryId %>"
	persistState="<%= true %>"
	state='<%= panelCategoryHelper.containsPortlet(themeDisplay.getPpid()) ? "open" : "closed" %>'
	title="<%= panelCategory.getLabel(themeDisplay.getLocale()) %>"
>

	<ul aria-labelledby="<%= panelPageCategoryId %>" class="category-portlets list-unstyled" role="menu">

		<%
		for (PanelApp panelApp : panelAppRegistry.getPanelApps(panelCategory)) {
		%>

			<c:if test="<%= panelApp.hasAccessPermission(permissionChecker, themeDisplay.getScopeGroup()) %>">
				<productivity-center-ui:panel-app
					panelApp="<%= panelApp %>"
					panelCategory="<%= panelCategory %>"
				/>
			</c:if>

		<%
		}
		%>

	</ul>
</liferay-ui:panel>