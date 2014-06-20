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

<%@ include file="/html/portlet/asset_tag_admin/init.jsp" %>

<aui:form name="fm">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-tags"
		rowChecker="<%= new RowChecker(renderResponse) %>"
	>
		<aui:nav-bar>
			<aui:nav cssClass="navbar-nav">
				<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
					<aui:nav-item id="addTagButton" label="add-tag" />
				</c:if>

				<c:if test="<%= PropsValues.ASSET_TAG_PERMISSIONS_ENABLED && AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) && GroupPermissionUtil.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.PERMISSIONS) %>">
					<liferay-security:permissionsURL
						modelResource="com.liferay.portlet.asset"
						modelResourceDescription="<%= themeDisplay.getScopeGroupName() %>"
						resourcePrimKey="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>"
						var="permissionsURL"
						windowState="<%= LiferayWindowState.POP_UP.toString() %>"
					/>

					<aui:nav-item data-url="<%= permissionsURL %>" id="tagsPermissionsButton" label="permissions" />
				</c:if>

				<aui:nav-item cssClass="hide" dropdown="<%= true %>" id="tagsActionsButton" label="actions">
					<aui:nav-item iconCssClass="icon-random" id="mergeSelectedTags" label="merge" />

					<aui:nav-item cssClass="item-remove" iconCssClass="icon-remove" id="deleteSelectedTags" label="delete" />
				</aui:nav-item>
			</aui:nav>

			<aui:nav-bar-search cssClass="pull-right">
				<div class="col-xs-12 form-search">
					<liferay-ui:input-search id="tagsAdminSearchInput" name="tagsAdminSearchInput" showButton="<%= false %>" />
				</div>
			</aui:nav-bar-search>
		</aui:nav-bar>

		<liferay-ui:search-container-results>

			<%
			String keywords = ParamUtil.getString(request, "keywords");

			if (Validator.isNotNull(keywords)) {
				total = AssetTagServiceUtil.getTagsCount(scopeGroupId, keywords, new String[0]);

				searchContainer.setTotal(total);

				results = AssetTagServiceUtil.getTags(scopeGroupId, keywords, new String[0], searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);
			}
			else {
				total = AssetTagServiceUtil.getTagsCount(scopeGroupId, null, new String[0]);

				searchContainer.setTotal(total);

				results = AssetTagServiceUtil.getTags(scopeGroupId, null, new String[0], searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setResults(results);
			}
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.asset.model.AssetTag"
			keyProperty="tagId"
			modelVar="tag"
		>
			<liferay-ui:search-container-column-text
				name="name"
				property="name"
			/>

			<liferay-ui:search-container-column-text
				name="usages"
			>
				<c:choose>
					<c:when test="<%= tag.getAssetCount() > 0 %>">
						<liferay-ui:message arguments="<%= tag.getAssetCount() %>" key="used-in-x-assets" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-tag-is-not-used" />
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<c:if test="<%= PropsValues.ASSET_TAG_PROPERTIES_ENABLED %>">
				<liferay-ui:search-container-column-text
					name="properties"
				>

					<%
					List<AssetTagProperty> tagProperties = AssetTagPropertyServiceUtil.getTagProperties(tag.getTagId());

					for (AssetTagProperty tagProperty : tagProperties) {
					%>

						<span class="property-key"><%= tagProperty.getKey() %></span>: <span class="property-value"><%= tagProperty.getValue() %></span><br />

					<%
					}
					%>

				</liferay-ui:search-container-column-text>
			</c:if>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/html/portlet/asset_tag_admin/tag_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base,liferay-util-list-fields">
	A.one('#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer').delegate(
		'click',
		function() {
			var hide = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			A.one('#<portlet:namespace />tagsActionsButton').toggle(!hide);
		},
		'input[type=checkbox]'
	);
</aui:script>
