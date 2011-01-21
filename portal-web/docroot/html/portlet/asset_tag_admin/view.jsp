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

<%@ include file="/html/portlet/asset_tag_admin/init.jsp" %>

<aui:form name="fm">

<div class="tags-admin-container">
	<div class="tags-admin-toolbar">
		<span class="tags-admin-search-bar">
			<aui:input cssClass="tags-admin-search" label="" name="tagsAdminSearchInput" />
		</span>

		<aui:button-row cssClass="tags-admin-actions">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_TAG) %>">
				<aui:button cssClass="add-tag-button" name="addTagButton" value="add-tag" />
			</c:if>

			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getParentGroupId()) %>"
					var="permissionsURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<aui:button name="tagsPermissionsButton" data-url="<%= permissionsURL %>" value="permissions" />
			</c:if>
		</aui:button-row>
	</div>

	<div class="tags-admin-content-wrapper">
		<aui:layout cssClass="tags-admin-content">
			<aui:column columnWidth="35" cssClass="tags-admin-list-container">
				<div class="results-header"><liferay-ui:message key="tags" /></div>

				<div class="tags-admin-list lfr-component"></div>
			</aui:column>

			<aui:column columnWidth="65" cssClass="tags-admin-edit-tag">
				<div class="results-header">
					<liferay-ui:message key="tag-details" />
				</div>

				<div class="tag-view-container">
				</div>
			</aui:column>
		</aui:layout>
	</div>
</div>

</aui:form>

<aui:script use="liferay-tags-admin">
	new Liferay.Portlet.AssetTagsAdmin(
		{
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>