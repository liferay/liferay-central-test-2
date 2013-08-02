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

<%@ include file="/html/portlet/sites_admin/init.jsp" %>

<%
long groupId = GetterUtil.getLong((String)request.getAttribute("merge_alert.jsp-groupId"));
LayoutSet layoutSet = (LayoutSet)request.getAttribute("merge_alert.jsp-layoutSet");
String redirect = (String)request.getAttribute("merge_alert.jsp-redirect");

List<Layout> mergeFailFriendlyURLLayouts = SitesUtil.getMergeFailFriendlyURLLayouts(layoutSet);
%>

<c:if test="<%= !mergeFailFriendlyURLLayouts.isEmpty() %>">
	<span class="portlet-msg-alert">
		<liferay-ui:message key="some-pages-from-the-site-template-cannot-be-propagated-because-their-friendly-urls-conflict-with-the-following-pages" />

		<liferay-ui:message key="modify-the-friendly-url-of-the-pages-to-allow-their-propagation-from-the-site-template" />

		<ul>
			<liferay-portlet:renderURL portletName="<%= PortletKeys.GROUP_PAGES %>" varImpl="editLayoutsURL">
				<portlet:param name="struts_action" value="/group_pages/edit_layouts" />
				<portlet:param name="tabs1" value='<%= layoutSet.isPrivateLayout() ? "private-pages" : "public-pages" %>' />
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="closeRedirect" value="<%= redirect %>" />
				<portlet:param name="backURL" value="<%= redirect %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-portlet:renderURL>

			<%
			for (Layout mergeFailFriendlyURLLayout : mergeFailFriendlyURLLayouts) {
				editLayoutsURL.setParameter("selPlid", String.valueOf(mergeFailFriendlyURLLayout.getPlid()));
			%>

				<li>
					<aui:a href="<%= editLayoutsURL.toString() %>">
						<%= mergeFailFriendlyURLLayout.getName(locale) %>
					</aui:a>
				</li>

			<%
			}
			%>

		</ul>
	</span>
</c:if>