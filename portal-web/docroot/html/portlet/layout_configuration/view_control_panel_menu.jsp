<%
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
%>

<%@ include file="/html/portlet/layout_configuration/init.jsp" %>

<h1 class="user-greeting">
	<liferay-ui:message key="control-panel" />
</h1>

<div class="portal-add-content">
	<liferay-ui:panel-container extended="<%= true %>" id="addContentPanelContainer" persistState="<%= true %>">

		<%
		String ppid = GetterUtil.getString((String)request.getAttribute("control_panel.jsp-ppid"), layoutTypePortlet.getStateMaxPortletId());

		for (String category : PortletCategoryKeys.ALL) {
			String panelCategory = "panel-manage-" + category;

			String cssClass = "lfr-component panel-page-category";

			List<Portlet> portlets = PortalUtil.getControlPanelPortlets(category, themeDisplay);

			if (!portlets.isEmpty()) {
				String title = null;

				if (category.equals(PortletCategoryKeys.MY)) {
					title = HtmlUtil.escape(StringUtil.shorten(user.getFullName(), 25));
				}
				else {
					title = LanguageUtil.get(pageContext, "category." + category);
				}
		%>

				<liferay-ui:panel collapsible="<%= true %>" cssClass="<%= cssClass %>" extended="<%= true %>" id="<%= panelCategory %>" persistState="<%= true %>" title="<%= title %>">
					<ul class="category-portlets">

						<%
						for (Portlet portlet : portlets) {
							if (portlet.isActive() && !portlet.isInstanceable()) {
						%>

								<li class="<%= ppid.equals(portlet.getPortletId()) ? "selected-portlet" : "" %>">
									<a href="<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= portlet.getRootPortletId() %>" />"><%= PortalUtil.getPortletTitle(portlet, application, locale) %></a>
								</li>

						<%
							}
						}
						%>

					</ul>
				</liferay-ui:panel>

		<%
			}
		}
		%>

	</liferay-ui:panel-container>
</div>