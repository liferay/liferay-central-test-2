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
long groupId = ParamUtil.getLong(request, "groupId");
long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String redirect = ParamUtil.getString(request, "redirect");

List<LayoutSetBranch> branches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId, privateLayout);

LayoutSetBranch currentBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

if (branches.contains(currentBranch)) {
	branches = ListUtil.copy(branches);

	branches.remove(currentBranch);
}
%>

<div data-namespace="<portlet:namespace />" id="mergeBranch">
	<portlet:actionURL var="mergeBranchURL">
		<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set_branch" />
		<portlet:param name="<%= Constants.CMD %>" value="merge_layout_set_branch" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		<portlet:param name="layoutSetBranchId" value="<%= String.valueOf(layoutSetBranchId) %>" />
	</portlet:actionURL>

	<aui:form action="<%= mergeBranchURL %>" enctype="multipart/form-data" method="post" name="fm4">
		<aui:input id="mergeBranchCmd" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input id="mergeBranchGroupId" name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input id="mergeBranchPrivateLayout" name="privateLayout" type="hidden" value="<%= privateLayout %>" />
		<aui:input id="mergeBranchMergeLayoutSetBranchId" name="mergeLayoutSetBranchId" type="hidden" />

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
					property="name"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
				>

					<%
					buffer.append("<a class='layout-set-branch' href='#' data-layoutSetBranchId='");
					buffer.append(branch.getLayoutSetBranchId());
					buffer.append("' data-layoutSetBranchName='");
					buffer.append(branch.getName());
					buffer.append("'>");
					buffer.append(LanguageUtil.get(pageContext, "select"));
					buffer.append("</a>");
					%>

				</liferay-ui:search-container-column-text>

			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>