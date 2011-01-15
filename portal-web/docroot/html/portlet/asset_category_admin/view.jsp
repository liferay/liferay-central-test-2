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

<form id="<portlet:namespace />fm">

<div class="vocabulary-container">
	<div class="vocabulary-toolbar">
		<span id="vocabulary-search-bar">
			<input id="vocabulary-search-input" type="text" value="" />

			<select class="vocabulary-select-search" id="vocabulary-select-search">
				<option value="categories" selected><liferay-ui:message key="categories" /></option>
				<option value="vocabularies"><liferay-ui:message key="vocabularies" /></option>
			</select>
		</span>

		<span class="vocabulary-actions">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_VOCABULARY) %>">
				<input class="add-vocabulary-button" id="add-vocabulary-button" name="add-vocabulary-button" type="button" value="<liferay-ui:message key="add-vocabulary" />">
			</c:if>

			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_CATEGORY) %>">
				<input class="add-category-button" id="add-category-button" name="add-category-button" type="button" value="<liferay-ui:message key="add-category" />">
			</c:if>

			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getParentGroupId()) %>"
					var="permissionsURL"
				/>

				<input type="button" value="<liferay-ui:message key="permissions" />" onClick="location.href = '<%= permissionsURL %>';" />
			</c:if>
		</span>
	</div>

	<div class="vocabulary-content-wrapper">
		<div class="vocabulary-content">
			<div class="vocabulary-list-container column">
				<div class="results-header"><liferay-ui:message key="vocabularies" /></div>

				<div class="vocabulary-list lfr-component"></div>
			</div>
			<div class="vocabulary-categories-container column">
				<div class="results-header">
					<liferay-ui:message key="categories" />
				</div>

				<div class="vocabulary-categories"></div>
			</div>
			<div class="aui-helper-hidden vocabulary-edit-category column">
				<div class="results-header"><liferay-ui:message key="category-details" />
					<div class="category-view-close">
					   <span>
						   <liferay-ui:icon
								image="close" id="category-view-close"
						   />
						</span>
					</div>
				</div>

				<div class="category-view">
				</div>
			</div>
		</div>
	</div>
</div>

</form>

<aui:script use="liferay-category-admin">
	new Liferay.Portlet.AssetCategoryAdmin(
		{
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>