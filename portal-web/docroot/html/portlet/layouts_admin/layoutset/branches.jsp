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

<%
long groupId = ((Long)request.getAttribute("edit_pages.jsp-groupId")).longValue();
boolean privateLayout = ((Boolean)request.getAttribute("edit_pages.jsp-privateLayout")).booleanValue();

List<LayoutSetBranch> branches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId, privateLayout);

LayoutSetBranch currentBranch = LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(themeDisplay.getUserId(), groupId, privateLayout, 0);
%>

<liferay-util:html-top>
	<liferay-util:include page="/html/portlet/layouts_admin/add_branch.jsp" />
</liferay-util:html-top>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_LAYOUT_SET_BRANCH) %>">
	<aui:button name="addBranchButton" value="add-branch" onClick='<%= "javascript:Liferay.Staging.Branching.addBranch(\'" + renderResponse.getNamespace() + "\');" %>' />

	<br /><br />
</c:if>

<liferay-ui:search-container id="branchesSearchContainer">
	<liferay-ui:search-container-results
		results="<%= branches %>"
		total="<%= branches.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.LayoutSetBranch"
		escapedModel="<%= true %>"
		keyProperty="layoutSetBranchId"
		modelVar="branch"
	>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="name"
		>

			<%
			if (currentBranch.equals(branch)) {
				buffer.append("<strong>");
			}

			buffer.append(branch.getName());

			if (currentBranch.equals(branch)) {
				buffer.append(" (*)</strong>");
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			property="description"
		/>

		<liferay-ui:search-container-column-jsp
			path="/html/portlet/layouts_admin/branch_action.jsp"
		/>

	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:script position="inline" use="aui-dialog,aui-io-request,staging">
	Liferay.Staging.Branching.init({namespace:'<portlet:namespace />'});
</aui:script>