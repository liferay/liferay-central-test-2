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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() && (layout != null) && (layout.getType().equals(LayoutConstants.TYPE_PORTLET) || layout.getType().equals(LayoutConstants.TYPE_PANEL)) %>">

		<%
		PortletURL refererURL = renderResponse.createActionURL();

		refererURL.setParameter("updateLayout", "true");
		%>

		<div id="portal_add_content">
			<div class="portal-add-content">
				<form action="<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=<%= plid %>" method="post" name="<portlet:namespace />fm">
				<input name="doAsUserId" type="hidden" value="<%= HtmlUtil.escapeAttribute(themeDisplay.getDoAsUserId()) %>" />
				<input name="<%= Constants.CMD %>" type="hidden" value="template" />
				<input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
				<input name="refresh" type="hidden" value="true" />

				<c:if test="<%= layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">
					<div class="portal-add-content-search">
						<span id="portal_add_content_title"><liferay-ui:message key="search-applications-searches-as-you-type" /></span>

						<input class="lfr-auto-focus" id="layout_configuration_content" type="text" onKeyPress="if (event.keyCode == 13) { return false; }" />
					</div>
				</c:if>

				<%
				Set panelSelectedPortlets = SetUtil.fromArray(StringUtil.split(layout.getTypeSettingsProperties().getProperty("panelSelectedPortlets")));

				PortletCategory portletCategory = (PortletCategory)WebAppPool.get(String.valueOf(company.getCompanyId()), WebKeys.PORTLET_CATEGORY);

				portletCategory = _getRelevantPortletCategory(portletCategory, panelSelectedPortlets, layoutTypePortlet, layout, user);

				List categories = ListUtil.fromCollection(portletCategory.getCategories());

				categories = ListUtil.sort(categories, new PortletCategoryComparator(locale));

				Iterator itr = categories.iterator();

				while (itr.hasNext()) {
					PortletCategory curPortletCategory = (PortletCategory)itr.next();

					if (curPortletCategory.getName().equals("category.hidden")) {
						continue;
					}

					request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
				%>

					<liferay-util:include page="/html/portlet/layout_configuration/view_category.jsp" />

				<%
				}
				%>

				<c:if test="<%= layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">
					<div class="portlet-msg-info">
						<liferay-ui:message key="to-add-a-portlet-to-the-page-just-drag-it" />
					</div>
				</c:if>

				<c:if test="<%= permissionChecker.isOmniadmin() %>">

					<%
					Group controlPanelGroup = GroupLocalServiceUtil.getGroup(company.getCompanyId(), GroupConstants.CONTROL_PANEL);

					long controlPanelPlid = LayoutLocalServiceUtil.getDefaultPlid(controlPanelGroup.getGroupId(), true);

					PortletURLImpl pluginsURL = new PortletURLImpl(request, PortletKeys.PLUGIN_INSTALLER, controlPanelPlid, PortletRequest.RENDER_PHASE);

					pluginsURL.setPortletMode(PortletMode.VIEW);
					pluginsURL.setRefererPlid(plid);
					%>

					<p class="lfr-install-more">
						<a href="<%= pluginsURL.toString() %>"><liferay-ui:message key="install-more-applications" /></a>
					</p>
				</c:if>

				</form>
			</div>
		</div>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() && (layout != null) && (layout.getType().equals(LayoutConstants.TYPE_CONTROL_PANEL)) %>">
		<liferay-util:include page="/html/portlet/layout_configuration/view_control_panel_menu.jsp" />
	</c:when>
</c:choose>

<c:if test="<%= !themeDisplay.isSignedIn() %>">
	<liferay-ui:message key="please-sign-in-to-continue" />
</c:if>

<%!
private static PortletCategory _getRelevantPortletCategory(PortletCategory portletCategory, Set panelSelectedPortlets, LayoutTypePortlet layoutTypePortlet, Layout layout, User user) throws Exception {
	PortletCategory relevantPortletCategory = new PortletCategory(portletCategory.getName(), portletCategory.getPortletIds());

	for (PortletCategory curPortletCategory : portletCategory.getCategories()) {
		Set<String> portletIds = new HashSet<String>();

		for (String portletId : curPortletCategory.getPortletIds()) {
			Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive() || portlet.isUndeployedPortlet()) {
				}
				else if (layout.getType().equals(LayoutConstants.TYPE_PANEL) && panelSelectedPortlets.contains(portlet.getRootPortletId())) {
					portletIds.add(portlet.getPortletId());
				}
				else if (layout.getType().equals(LayoutConstants.TYPE_PANEL) && !panelSelectedPortlets.contains(portlet.getRootPortletId())) {
				}
				else if (!portlet.hasAddPortletPermission(user.getUserId())) {
				}
				else if (!portlet.isInstanceable() && layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
					portletIds.add(portlet.getPortletId());
				}
				else {
					portletIds.add(portlet.getPortletId());
				}
			}
		}

		PortletCategory curRelevantPortletCategory = _getRelevantPortletCategory(curPortletCategory, panelSelectedPortlets, layoutTypePortlet, layout, user);

		curRelevantPortletCategory.setPortletIds(portletIds);

		if (!curRelevantPortletCategory.getCategories().isEmpty() || !portletIds.isEmpty()) {
			relevantPortletCategory.addCategory(curRelevantPortletCategory);
		}
	}

	return relevantPortletCategory;
}
%>