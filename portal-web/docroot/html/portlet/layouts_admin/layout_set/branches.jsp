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

List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId, privateLayout);

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(themeDisplay.getUserId(), groupId, privateLayout, 0);
%>

<liferay-util:html-top>
	<liferay-util:include page="/html/portlet/layouts_admin/add_layout_set_branch.jsp" />
</liferay-util:html-top>

<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, groupId, ActionKeys.ADD_LAYOUT_SET_BRANCH) %>">

	<%
	String taglibOnClick = "javascript:Liferay.Staging.Branching.addBranch('" + renderResponse.getNamespace() + "');";
	%>

	<aui:button onClick="<%= taglibOnClick %>" value="add-branch" />

	<br /><br />
</c:if>

<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= layoutSetBranches %>"
		total="<%= layoutSetBranches.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.LayoutSetBranch"
		escapedModel="<%= true %>"
		keyProperty="layoutSetBranchId"
		modelVar="curLayoutSetBranch"
	>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="name"
		>

			<%
			if (layoutSetBranch.equals(curLayoutSetBranch)) {
				buffer.append("<strong>");
			}

			buffer.append(curLayoutSetBranch.getName());

			if (layoutSetBranch.equals(curLayoutSetBranch)) {
				buffer.append(" (*)</strong>");
			}
			%>

		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			property="description"
		/>

		<liferay-ui:search-container-column-jsp
			path="/html/portlet/layouts_admin/layout_set_branch_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
</liferay-ui:search-container>

<aui:script position="inline" use="liferay-staging">
	Liferay.Staging.Branching.init(
		{
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>