<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
Collection<Portlet> siteAdministrationPortlets = PortalUtil.getControlPanelPortlets(themeDisplay.getCompanyId(), PortletCategoryKeys.SITE_ADMINISTRATION);

List<Group> manageableSiteGroups = GroupServiceUtil.getManageableSiteGroups(siteAdministrationPortlets, PropsValues.CONTROL_PANEL_NAVIGATION_MAX_SITES);

Group userGroup = user.getGroup();

if (PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, userGroup.getGroupId(), siteAdministrationPortlets)) {
	manageableSiteGroups.add(0, userGroup);
}

if (PortalUtil.isCompanyControlPanelVisible(themeDisplay)) {
	Group companyGroup = company.getGroup();

	if (!manageableSiteGroups.contains(companyGroup)) {
		manageableSiteGroups.add(0, companyGroup);
	}
}

Group siteGroup = themeDisplay.getSiteGroup();

manageableSiteGroups.remove(siteGroup);

String switchGroupURL = HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "switchGroup", "1");

switchGroupURL = PortalUtil.resetPortletParameters(switchGroupURL, themeDisplay.getPpid());
%>

<c:choose>
	<c:when test="<%= manageableSiteGroups.isEmpty() %>">
		<span class="control-panel-site-selector">
			<%= HtmlUtil.escape(siteGroup.getDescriptiveName()) %>
		</span>
	</c:when>
	<c:otherwise>
		<liferay-ui:icon-menu cssClass="control-panel-site-selector" direction="down" extended="<%= false %>" icon="../aui/caret-bottom-right" id="controlPanelSiteSelector" localizeMessage="<%= false %>" message="<%= HtmlUtil.escape(siteGroup.getDescriptiveName()) %>" showArrow="<%= true %>" showWhenSingleIcon="<%= true %>" useIconCaret="<%= true %>">

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("navigation", Boolean.TRUE.toString());

			for (Group manageableSiteGroup : manageableSiteGroups) {
			%>

				<liferay-ui:icon
					data="<%= data %>"
					iconCssClass="<%= manageableSiteGroup.getIconCssClass() %>"
					localizeMessage="<%= false %>"
					message="<%= HtmlUtil.escape(manageableSiteGroup.getDescriptiveName(locale)) %>"
					url='<%= HttpUtil.setParameter(switchGroupURL, "doAsGroupId", manageableSiteGroup.getGroupId()) %>'
				/>

			<%
			}
			%>

		</liferay-ui:icon-menu>
	</c:otherwise>
</c:choose>