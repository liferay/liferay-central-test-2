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

<c:if test="<%= themeDisplay.isSignedIn() && (layout != null) && (layout.getType().equals(LayoutConstants.TYPE_PORTLET) || layout.getType().equals(LayoutConstants.TYPE_PANEL)) %>">

	<%
	PortletURL refererURL = renderResponse.createActionURL();

	refererURL.setParameter("updateLayout", "true");
	%>

	<div id="portal_add_content">
		<div class="portal-add-content">
			<form action="<%= themeDisplay.getPathMain() %>/portal/update_layout?p_l_id=<%= plid %>" class="uni-form" method="post" name="<portlet:namespace />fm">
			<input name="doAsUserId" type="hidden" value="<%= themeDisplay.getDoAsUserId() %>" />
			<input name="<%= Constants.CMD %>" type="hidden" value="template" />
			<input name="<%= WebKeys.REFERER %>" type="hidden" value="<%= refererURL.toString() %>" />
			<input name="refresh" type="hidden" value="true" />

			<c:if test="<%= layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">
				<div class="portal-add-content-search">
					<span id="portal_add_content_title"><liferay-ui:message key="search-content-searches-as-you-type" /></span>

					<input id="layout_configuration_content" type="text" onKeyPress="if (event.keyCode == 13) { return false; }" />
				</div>
			</c:if>

			<%
			Set panelSelectedPortlets = SetUtil.fromArray(StringUtil.split(layout.getTypeSettingsProperties().getProperty("panelSelectedPortlets")));

			PortletCategory portletCategory = (PortletCategory)WebAppPool.get(String.valueOf(company.getCompanyId()), WebKeys.PORTLET_CATEGORY);

			portletCategory = removeEmptyCategories(user, layout, layoutTypePortlet, portletCategory, panelSelectedPortlets);

			List categories = ListUtil.fromCollection(portletCategory.getCategories());

			Collections.sort(categories, new PortletCategoryComparator(company.getCompanyId(), locale));

			Iterator itr = categories.iterator();

			while (itr.hasNext()) {
				request.setAttribute(WebKeys.PORTLET_CATEGORY, itr.next());
			%>

				<liferay-util:include page="/html/portlet/layout_configuration/view_category.jsp" />

			<%
			}
			%>

			<c:if test="<%= layout.getType().equals(LayoutConstants.TYPE_PORTLET) %>">
				<p class="portlet-msg-info">
					<liferay-ui:message key="to-add-a-portlet-to-the-page-just-drag-it" />
				</p>
			</c:if>

			</form>
		</div>
	</div>
</c:if>

<c:if test="<%= !themeDisplay.isSignedIn() %>">
	<liferay-ui:message key="please-sign-in-to-continue" />
</c:if>

<%!
public PortletCategory removeEmptyCategories(User user, Layout layout, LayoutTypePortlet layoutTypePortlet, PortletCategory portletCategory, Set panelSelectedPortlets) throws SystemException {
	PortletCategory cleanCategory = new PortletCategory(portletCategory.getName(), portletCategory.getPortletIds());

	Iterator<PortletCategory> itr = portletCategory.getCategories().iterator();

	while (itr.hasNext()) {
		PortletCategory curCategory = itr.next();

		List portlets = new ArrayList();
		Set<String> portletIds = new HashSet();
		
		Iterator itr2 = curCategory.getPortletIds().iterator();

		while (itr2.hasNext()) {
			String portletId = (String)itr2.next();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(user.getCompanyId(), portletId);

			if (portlet != null) {
				if (portlet.isSystem()) {
				}
				else if (!portlet.isActive()) {
				}
				else if (!portlet.isInstanceable() && layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
					portlets.add(portlet);
				}
				else if (!portlet.hasAddPortletPermission(user.getUserId())) {
				}
				else if (layout.getType().equals(LayoutConstants.TYPE_PANEL) && !panelSelectedPortlets.contains(portlet.getRootPortletId())) {
				}
				else {
					portlets.add(portlet);
					portletIds.add(String.valueOf(portlet.getPortletId()));
				}
			}
		}

		PortletCategory curCleanCategory = removeEmptyCategories(user, layout, layoutTypePortlet, curCategory, panelSelectedPortlets);

		curCleanCategory.setPortletIds(portletIds);

		if ((curCleanCategory.getCategories().size() > 0) || (portlets.size() > 0)) {
			cleanCategory.addCategory(curCleanCategory);
		}
	}

	return cleanCategory;
}
%>