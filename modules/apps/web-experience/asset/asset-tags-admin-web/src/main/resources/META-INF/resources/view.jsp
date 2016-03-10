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

<%@ include file="/init.jsp" %>

<liferay-portlet:renderURL varImpl="portletURL" />

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<portlet:renderURL var="mainURL" />

	<aui:nav cssClass="navbar-nav">
		<aui:nav-item href="<%= mainURL.toString() %>" label="tags" selected="<%= true %>" />
	</aui:nav>

	<c:if test="<%= assetTagsDisplayContext.isShowTagsSearch() %>">
		<aui:nav-bar-search>
			<aui:form action="<%= portletURL %>" name="searchFm">
				<liferay-ui:input-search markupView="lexicon" />
			</aui:form>
		</aui:nav-bar-search>
	</c:if>
</aui:nav-bar>

<liferay-frontend:management-bar
	disabled="<%= assetTagsDisplayContext.isDisabledTagsManagementBar() %>"
	includeCheckBox="<%= true %>"
	searchContainerId="assetTags"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= assetTagsDisplayContext.getOrderByCol() %>"
			orderByType="<%= assetTagsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "usages"} %>'
			portletURL="<%= renderResponse.createRenderURL() %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= assetTagsDisplayContext.getDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button href="javascript:;" icon="change" id="mergeSelectedTags" label="merge" />

		<liferay-frontend:management-bar-button href="javascript:;" icon="trash" id="deleteSelectedTags" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="deleteTag" var="deleteTagURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= deleteTagURL %>" cssClass="container-fluid-1280" name="fm">
	<liferay-ui:search-container
		id="assetTags"
		searchContainer="<%= assetTagsDisplayContext.getTagsSearchContainer() %>"
	>

		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetTag"
			keyProperty="tagId"
			modelVar="tag"
		>
			<liferay-ui:search-container-column-text
				cssClass="content-column title-column name-column"
				name="name"
				truncate="<%= true %>"
				value="<%= tag.getName() %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="usages-column"
				name="usages"
				value="<%= String.valueOf(tag.getAssetCount()) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/tag_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<c:if test="<%= AssetPermission.contains(permissionChecker, themeDisplay.getSiteGroupId(), ActionKeys.ADD_TAG) %>">
	<portlet:renderURL var="editTagURL">
		<portlet:param name="mvcPath" value="/edit_tag.jsp" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-tag") %>' url="<%= editTagURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>

<aui:script sandbox="<%= true %>">
	var Util = Liferay.Util;

	var form = $(document.<portlet:namespace />fm);

	$('#<portlet:namespace />deleteSelectedTags').on(
		'click',
		function() {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				submitForm(form);
			}
		}
	);

	$('#<portlet:namespace />mergeSelectedTags').on(
		'click',
		function() {
			<portlet:renderURL var="mergeURL">
				<portlet:param name="mvcPath" value="/merge_tag.jsp" />
				<portlet:param name="mergeTagIds" value="[$MERGE_TAGS_IDS$]" />
			</portlet:renderURL>

			var mergeURL = '<%= mergeURL %>';

			location.href = mergeURL.replace(escape('[$MERGE_TAGS_IDS$]'), Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));
		}
	);
</aui:script>