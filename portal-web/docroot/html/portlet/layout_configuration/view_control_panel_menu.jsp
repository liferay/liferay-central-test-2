<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<div class="user-greeting">
	<%= LanguageUtil.get(pageContext, "welcome") %> <a href="<%= user.getDisplayURL(themeDisplay) %>"><b><%= user.getFirstName() %> <%= user.getLastName() %></b></a>!
</div>

<div class="portal-add-content">

	<%
	String ppid = layoutTypePortlet.getStateMaxPortletId();

	for (String category : _PORTLET_CATEGORIES) {
		String panelCategory = StringPool.BLANK;

		if (category.equals(PortletCategoryKeys.CONTENT)) {
			panelCategory = "panel-manage-content";
		}
		else if (category.equals(PortletCategoryKeys.PORTAL)) {
			panelCategory = "panel-manage-portal";
		}
		else if (category.equals(PortletCategoryKeys.SERVER)) {
			panelCategory = "panel-manage-server";
		}

		boolean isSelectedCategory = category.equals(PortalUtil.getControlPanelCategory(ppid));
	%>

		<div class="lfr-add-content" id="<%= panelCategory %>">
			<h2 class="<%= isSelectedCategory ? "selected" : "" %>">
				<span><liferay-ui:message key="<%= category %>" /></span>
			</h2>

			<div class="lfr-content-category <%= isSelectedCategory ? "selected" : "" %>">

				<%
				List<Portlet> portlets = PortalUtil.getControlPanelPortlets(category);

				for (Portlet portlet : portlets) {
					if (portlet.isInstanceable()) {
						continue;
					}
				%>

					<div>
						<a class='<%= (ppid.equals(portlet.getPortletId())) ? "selected-portlet" : "" %>' href="<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" portletName="<%= portlet.getRootPortletId() %>" />&panel_category=<%= panelCategory %>"><%= PortalUtil.getPortletTitle(portlet, application, locale) %></a>
					</div>

				<%
				}
				%>

			</div>
		</div>
	<%
	}
	%>
</div>

<%!
private static final String[] _PORTLET_CATEGORIES = new String[] {PortletCategoryKeys.CONTENT, PortletCategoryKeys.PORTAL, PortletCategoryKeys.SERVER};
%>