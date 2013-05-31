<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/control_panel_home/init.jsp" %>

<aui:container cssClass="control-panel-home-menu">
	<aui:row>

		<%
		for (String category : PortletCategoryKeys.ALL) {
			String title = LanguageUtil.get(pageContext, "category." + category);

			List<Portlet> categoryPortlets = PortalUtil.getControlPanelPortlets(category, themeDisplay);

			if (categoryPortlets.isEmpty()) {
				continue;
			}
		%>

			<aui:col width="<%= 25 %>">
				<h3 class="control-panel-home-category-header" id='<%= "control-panel-home-category-header" + category %>'><%= title %></h3>

					<ul class="unstyled">

						<%
						for (Portlet categoryPortlet : categoryPortlets) {
							if (!categoryPortlet.isActive() || categoryPortlet.isInstanceable()) {
								continue;
							}

							String categoryPortletId = categoryPortlet.getPortletId();

							String urlCategoryPortlet = HttpUtil.setParameter(themeDisplay.getURLControlPanel(), "p_p_id", categoryPortletId);

							String portletDescription = PortalUtil.getPortletDescription(categoryPortlet, application, locale);
						%>

							<li>
								<a href="<%= urlCategoryPortlet %>">
									<c:choose>
										<c:when test="<%= Validator.isNull(categoryPortlet.getIcon()) %>">
											<liferay-ui:icon src='<%= themeDisplay.getPathContext() + "/html/icons/default.png" %>' />
										</c:when>
										<c:otherwise>
											<liferay-portlet:icon-portlet portlet="<%= categoryPortlet %>" />
										</c:otherwise>
									</c:choose>

									<%= PortalUtil.getPortletTitle(categoryPortlet, application, locale) %>
								</a>

								<c:if test='<%= Validator.isNotNull(portletDescription) && !portletDescription.startsWith("javax.portlet.description") %>'>
									<liferay-ui:icon-help message="<%= portletDescription %>" />
								</c:if>
							</li>

						<%
						}
						%>

					</ul>
			 </aui:col>

		<%
		}
		%>

	</aui:row>
	<aui:row>
		<liferay-util:include page="/html/portlet/control_panel_home/view_actions.jsp" />
	</aui:row>
</aui:container>