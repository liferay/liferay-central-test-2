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
	<liferay-ui:message key="welcome" /> <a href="<%= user.getDisplayURL(themeDisplay) %>"><%= user.getFullName() %></a> <a class="sign-out" href="<%= themeDisplay.getURLSignOut() %>"><liferay-ui:message key="sign-out" /></a>
</h1>

<div class="portal-add-content">

	<%
	String ppid = layoutTypePortlet.getStateMaxPortletId();

	for (String category : PortletCategoryKeys.ALL) {
		String panelCategory = "panel-manage-" + category;

		String cssClass = "lfr-component panel-page-category";

		if (category.equals(PortalUtil.getControlPanelCategory(ppid))) {
			cssClass += " selected";
		}

		cssClass += " " + GetterUtil.getString(SessionClicks.get(request, panelCategory, null), "open");

		List<Portlet> portlets = PortalUtil.getControlPanelPortlets(category);

		portlets = filterPortlets(permissionChecker, scopeGroupId, category, portlets);

		if (portlets.size() > 0) {
	%>

			<div class="<%= cssClass %>" id="<%= panelCategory %>">
				<ul>
					<li>
						<h2>
							<span><liferay-ui:message key='<%= "category." + category %>' /></span>
						</h2>

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
					</li>
				</ul>
			</div>

	<%
		}
	}
	%>

</div>

<%!
public static final List<Portlet> filterPortlets(PermissionChecker permissionChecker, Long groupId, String category, List<Portlet> portlets)
	throws Exception {

	if (permissionChecker.isCompanyAdmin()) {
		return portlets;
	}

	if (category.equals(PortletCategoryKeys.CONTENT) && (permissionChecker.isCommunityAdmin(groupId.longValue())) {
		return portlets;
	}

	List<Portlet> filteredPortlets = new ArrayList<Portlet>();

	for (Portlet portlet : portlets) {
		if (portlet.hasAddPortletPermission(permissionChecker.getUserId()) ||
			showPortlet(permissionChecker, portlet)) {

			filteredPortlets.add(portlet);
		}
	}

	return filteredPortlets;
}

public static boolean showPortlet(PermissionChecker permissionChecker, Portlet portlet)
	throws Exception {

	ControlPanelEntry controlPanelEntry = portlet.getControlPanelEntryInstance();

	if ((controlPanelEntry != null) && controlPanelEntry.isVisible(permissionChecker, portlet)) {
		return true;
	}

	return false;
}
%>