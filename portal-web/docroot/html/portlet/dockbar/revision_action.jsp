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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("revision_graph.jsp-layoutRevision");

List<LayoutRevision> pendingRevisions = LayoutRevisionLocalServiceUtil.getLayoutRevisions(layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(), WorkflowConstants.STATUS_PENDING);
%>

<liferay-ui:icon-menu showWhenSingleIcon="<%= true %>">
	<c:if test="<%= !layoutRevision.isPending() && LayoutPermissionUtil.contains(permissionChecker, layoutRevision.getPlid(), ActionKeys.UPDATE) %>">
		<c:if test="<%= pendingRevisions.isEmpty() && layoutRevision.isMajor() && !layoutRevision.isHead() %>">
			<portlet:actionURL var="publishURL">
				<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
				<portlet:param name="pagesRedirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
				<portlet:param name="major" value="true" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="submit"
				message="publish"
				url='<%= "javascript:submitForm(document.hrefFm, \'".concat(HttpUtil.encodeURL(publishURL)).concat("\');") %>'
			/>
		</c:if>

		<c:if test="<%= !layoutRevision.isMajor() && !layoutRevision.isHead() %>">
			<portlet:actionURL var="saveURL">
				<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="update_layout_revision" />
				<portlet:param name="pagesRedirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
				<portlet:param name="workflowAction" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />
				<portlet:param name="major" value="true" />
			</portlet:actionURL>

			<liferay-ui:icon
				image="checked"
				message="save"
				url='<%= "javascript:submitForm(document.hrefFm, \'".concat(HttpUtil.encodeURL(saveURL)).concat("\');") %>'
			/>
		</c:if>

		<c:if test="<%= !layoutRevision.isHead() %>">
			<portlet:actionURL var="deleteURL">
				<portlet:param name="struts_action" value="/dockbar/edit_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="delete_layout_revision" />
				<portlet:param name="pagesRedirect" value="<%= PortalUtil.getLayoutFullURL(themeDisplay) %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(layoutRevision.getGroupId()) %>" />
				<portlet:param name="layoutRevisionId" value="<%= String.valueOf(layoutRevision.getLayoutRevisionId()) %>" />
			</portlet:actionURL>

			<liferay-ui:icon-delete url="<%= deleteURL %>" />
		</c:if>
	</c:if>
</liferay-ui:icon-menu>