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

<h1 class="user-greeting">
	<liferay-ui:message key="control-panel" />
</h1>

<div class="portal-add-content">

<liferay-ui:panel-container id="panel-manage-container" extended="<%= Boolean.TRUE %>" persistState="true">
	
	<%
	String ppid = layoutTypePortlet.getStateMaxPortletId();

	for (String category : PortletCategoryKeys.ALL) {
		String panelCategory = "panel-manage-" + category;

		String cssClass = "lfr-component panel-page-category";

		List<Portlet> portlets = PortalUtil.getControlPanelPortlets(themeDisplay.getCompanyId(), category);

		portlets = filterPortlets(permissionChecker, themeDisplay.getScopeGroup(), category, portlets);

		if (portlets.size() > 0) {
			String title = null;

			if (category.equals(PortletCategoryKeys.MY)) {
				title = StringUtil.shorten(user.getFullName(), 25);
			}
			else {
				title = LanguageUtil.get(pageContext, "category." + category);
			}
	%>

			<liferay-ui:panel id="<%= panelCategory %>" title="<%= title %>" collapsible="true" persistState="true" extended="true" cssClass="<%= cssClass %>">
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

<%!
public static final List<Portlet> filterPortlets(PermissionChecker permissionChecker, Group group, String category, List<Portlet> portlets) throws Exception {
	List<Portlet> filteredPortlets = new ArrayList<Portlet>();

	boolean contentCategory = category.equals(PortletCategoryKeys.CONTENT);

	if (contentCategory && group.isLayout()) {
		for (Portlet portlet : portlets) {
			if (portlet.isScopeable()) {
				filteredPortlets.add(portlet);
			}
		}
	}
	else {
		filteredPortlets.addAll(portlets);
	}

	if (permissionChecker.isCompanyAdmin()) {
		return filteredPortlets;
	}

	if (category.equals(PortletCategoryKeys.CONTENT) && permissionChecker.isCommunityAdmin(group.getGroupId())) {
		return filteredPortlets;
	}

	for (Portlet portlet : filteredPortlets) {
		if ((contentCategory && portlet.hasAddPortletPermission(permissionChecker.getUserId())) || isShowPortlet(permissionChecker, portlet)) {
			filteredPortlets.add(portlet);
		}
	}

	return filteredPortlets;
}

public static boolean isShowPortlet(PermissionChecker permissionChecker, Portlet portlet) throws Exception {
	ControlPanelEntry controlPanelEntry = portlet.getControlPanelEntryInstance();

	if ((controlPanelEntry != null) && controlPanelEntry.isVisible(permissionChecker, portlet)) {
		return true;
	}

	return false;
}
%>