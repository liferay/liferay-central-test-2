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
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(groupId, privateLayout);

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

LayoutSetBranch layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

if (layoutSetBranches.contains(layoutSetBranch)) {
	layoutSetBranches = ListUtil.copy(layoutSetBranches);

	layoutSetBranches.remove(layoutSetBranch);
}
%>

<div data-namespace="<portlet:namespace />">
	<portlet:actionURL var="mergeLayoutSetBranchURL">
		<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set_branch" />
	</portlet:actionURL>

	<aui:form action="<%= mergeLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm4">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="merge_layout_set_branch" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
		<aui:input name="mergeLayoutSetBranchId" type="hidden" />

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
					name="branch"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					buffer="buffer"
				>

					<%
					buffer.append("<a class='layout-set-branch' data-layoutSetBranchId='");
					buffer.append(curLayoutSetBranch.getLayoutSetBranchId());
					buffer.append("' data-layoutSetBranchName='");
					buffer.append(curLayoutSetBranch.getName());
					buffer.append("' href='#'>");
					buffer.append(LanguageUtil.get(pageContext, "select"));
					buffer.append("</a>");
					%>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" paginate="<%= false %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>