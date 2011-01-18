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

<aui:form name="fm">

<div class="vocabulary-container">
	<div class="vocabulary-toolbar">
		<span class="vocabulary-search-bar">
			<aui:input cssClass="vocabulary-search" label="" name="vocabularySearchInput" />

			<aui:select cssClass="vocabulary-select-search" label="" name="vocabularySelectSearch">
				<aui:option label="categories" selected="<%= true %>" />
				<aui:option label="vocabularies" />
			</aui:select>
		</span>

		<aui:button-row cssClass="vocabulary-actions">
			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_VOCABULARY) %>">
				<aui:button cssClass="add-vocabulary-button" name="addVocabularyButton" value="add-vocabulary" />
			</c:if>

			<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.ADD_CATEGORY) %>">
				<aui:button cssClass="add-category-button" name="addCategoryButton" value="add-category" />
			</c:if>

			<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, themeDisplay.getParentGroupId(), ActionKeys.PERMISSIONS) %>">
				<liferay-security:permissionsURL
					modelResource="com.liferay.portlet.asset"
					modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
					resourcePrimKey="<%= String.valueOf(themeDisplay.getParentGroupId()) %>"
					var="permissionsURL"
				/>

				<aui:button name="categoryPermissionsButton" onClick="<%= permissionsURL %>" value="permissions" />
			</c:if>
		</aui:button-row>
	</div>

	<div class="vocabulary-content-wrapper">
		<aui:layout cssClass="vocabulary-content">
			<aui:column columnWidth="25" cssClass="vocabulary-list-container">
				<div class="results-header"><liferay-ui:message key="vocabularies" /></div>

				<div class="vocabulary-list lfr-component"></div>
			</aui:column>

			<aui:column columnWidth="75" cssClass="vocabulary-categories-container">
				<div class="results-header"><liferay-ui:message key="categories" /></div>

				<div class="vocabulary-categories"></div>
			</aui:column>

			<aui:column columnWidth="35" cssClass="aui-helper-hidden vocabulary-edit-category">
				<div class="results-header">
					<liferay-ui:message key="category-details" />

					<div class="category-view-close">
					   <span>
						   <liferay-ui:icon
								id="category-view-close"
								image="close"
						   />
						</span>
					</div>
				</div>

				<div class="category-view">
				</div>
			</aui:column>
		</aui:layout>
	</div>
</div>

</aui:form>

<aui:script use="liferay-category-admin">
	new Liferay.Portlet.AssetCategoryAdmin(
		{
			portletId: '<%= portletDisplay.getId() %>'
		}
	);
</aui:script>