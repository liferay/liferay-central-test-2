<%@ page import="com.liferay.portlet.asset.service.AssetCategoryServiceUtil" %>
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

<%@ include file="/html/portlet/asset_category_admin/init.jsp" %>

<%
long categoryId = ParamUtil.getLong(request, "categoryId");
AssetCategory category = AssetCategoryServiceUtil.getCategory(categoryId);
List<AssetCategoryProperty> properties = AssetCategoryPropertyServiceUtil.getCategoryProperties(category.getCategoryId());
%>

<div class="view-category">
	<liferay-ui:header
		title="<%= category.getTitle(locale) %>"
	/>

	<c:if test="<%= category != null && permissionChecker.hasPermission(scopeGroupId, AssetCategory.class.getName(), category.getCategoryId(), ActionKeys.UPDATE)%>">
		<aui:button value="edit" id="category-edit-button" />
	</c:if>

	<c:if test="<%= category != null && permissionChecker.hasPermission(scopeGroupId, AssetCategory.class.getName(), category.getCategoryId(), ActionKeys.DELETE)%>">
		<aui:button value="delete" id="category-delete-button" />
	</c:if>

	<c:if test="<%= category != null && permissionChecker.hasPermission(scopeGroupId, AssetCategory.class.getName(), category.getCategoryId(), ActionKeys.PERMISSIONS) %>" >
		<liferay-security:permissionsURL
			modelResource="<%= AssetCategory.class.getName() %>"
			modelResourceDescription="<%= category.getTitle(locale) %>"
			resourcePrimKey="<%= String.valueOf(category.getCategoryId()) %>"
			var="permissionsURL"
			windowState="pop_up"
		/>

		<aui:button value="permissions" id="category-change-permissions" data-url="<%= permissionsURL %>" />
	</c:if>

	<div class="category-field">
		<label><liferay-ui:message key="description" />:</label>
		<%= category.getDescription(locale) %>
	</div>

	<c:if test="<%= properties.size() > 0 %>">
		<div class="category-field">
			<label><liferay-ui:message key="properties" />:</label>

			<%
			for (AssetCategoryProperty property : properties) {
			%>

				<span class="property-key"><%= property.getKey() %></span> : <span class="property-value"><%= property.getValue() %></span> <br />

			<%
			}
			%>
		</div>
	</c:if>
</div>
