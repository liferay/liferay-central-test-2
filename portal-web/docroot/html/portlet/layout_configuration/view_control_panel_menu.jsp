<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
					title = StringUtil.shorten(user.getFullName(), 25);
				}
				else {
					title = LanguageUtil.get(pageContext, "category." + category);
				}
		%>

				<liferay-ui:panel collapsible="<%= true %>" cssClass="<%= cssClass %>" extended="<%= true %>" id="<%= panelCategory %>" persistState="<%= true %>" title="<%= title %>">
					<ul class="category-portlets">

						<%
						for (Portlet portlet : portlets) {
							if (!portlet.isInstanceable()) {
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