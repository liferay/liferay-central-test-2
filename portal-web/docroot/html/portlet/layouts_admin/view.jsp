<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%@ include file="/html/portlet/layouts_admin/init_attributes.jspf" %>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) || portletName.equals(PortletKeys.GROUP_PAGES) || portletName.equals(PortletKeys.MY_PAGES) %>">
		<c:if test="<%= portletName.equals(PortletKeys.COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_COMMUNITIES) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_ORGANIZATIONS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USER_GROUPS) || portletName.equals(PortletKeys.ENTERPRISE_ADMIN_USERS) %>">
			<liferay-ui:header
				backURL="<%= backURL %>"
				title="<%= liveGroup.getDescriptiveName() %>"
			/>
		</c:if>

		<%
		String tabs1URL = portletURL.toString();

		if (liveGroup.isUser()) {
			PortletURL userTabs1URL = renderResponse.createRenderURL();

			userTabs1URL.setParameter("struts_action", "/my_pages/edit_layouts");
			userTabs1URL.setParameter("tabs1", tabs1);
			userTabs1URL.setParameter("backURL", backURL);
			userTabs1URL.setParameter("groupId", String.valueOf(liveGroupId));

			tabs1URL = userTabs1URL.toString();
		}
		%>

		<liferay-ui:tabs
			names="<%= tabs1Names %>"
			param="tabs1"
			value="<%= tabs1 %>"
			url="<%= tabs1URL %>"
		/>

		<%
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, TextFormatter.format(tabs1, TextFormatter.O)), currentURL);

		if ((selLayout != null) && !group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
		}
		%>
	</c:when>
	<c:otherwise>

		<%
		if ((selLayout != null) && !group.isLayoutPrototype()) {
			PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
		}
		%>

		<div class="layout-breadcrumb">
			<liferay-ui:breadcrumb displayStyle="horizontal" showPortletBreadcrumb="<%= true %>" showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />
		</div>
	</c:otherwise>
</c:choose>

<%
if ((selLayout != null) && !group.isLayoutPrototype()) {
	PortalUtil.addPortletBreadcrumbEntry(request, selLayout.getName(locale), currentURL);
}
%>

<aui:layout cssClass="manage-view">
	<c:if test="<%= !group.isLayoutPrototype() %>">
		<aui:column columnWidth="25" cssClass="manage-sitemap">
			<div class="header-row">
				<div class="header-row-content"> </div>
			</div>

			<liferay-util:include page="/html/portlet/layouts_admin/tree_js.jsp">
				<liferay-util:param name="treeId" value="layoutsTree" />
			</liferay-util:include>
		</aui:column>
	</c:if>

	<aui:column columnWidth="<%= group.isLayoutPrototype() ? 100 : 75 %>" cssClass="manage-layout">
		<div id="<portlet:namespace />layoutsContainer">
			<c:choose>
				<c:when test="<%= selPlid > 0 %>">
					<liferay-util:include page="/html/portlet/layouts_admin/edit_layout.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/layouts_admin/edit_layout_set.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
	</aui:column>
</aui:layout>

<aui:script use="aui-io-plugin">
	var layoutsContainer = A.one('#<portlet:namespace />layoutsContainer');

	layoutsContainer.plug(
		A.Plugin.IO, {
			autoLoad: false
		}
	);

	A.one('#<portlet:namespace />layoutsTreeOutput').delegate(
		'click',
		function(event) {
			event.preventDefault();

			var requestUri = event.currentTarget.get('href');

			layoutsContainer.io.set('uri', requestUri);

			layoutsContainer.io.start();
		},
		'.layout-tree'
	);
</aui:script>