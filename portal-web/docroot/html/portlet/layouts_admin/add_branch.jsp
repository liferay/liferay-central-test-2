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

PortletURL portletURL = ((PortletURL)request.getAttribute("edit_pages.jsp-portletURL"));
%>

<div class="aui-helper-hidden" data-namespace="<portlet:namespace />" id="addBranch">
	<aui:model-context model="<%= LayoutSetBranch.class %>" />

	<portlet:actionURL var="editBranchURL">
			<portlet:param name="struts_action" value="/layouts_admin/edit_layout_set_branch" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	</portlet:actionURL>

	<aui:form action="<%= editBranchURL %>" enctype="multipart/form-data" method="post" name="fm3">
		<aui:input id="addBranchCmd" name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input id="addBranchPagesRedirect" name="pagesRedirect" type="hidden" value='<%= portletURL.toString() %>' />
		<aui:input id="addBranchGroupId" name="groupId" type="hidden" value="<%= groupId %>" />
		<aui:input id="addBranchPrivateLayout" name="privateLayout" type="hidden" value="<%= privateLayout %>" />

		<aui:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value="add-branch" />
		</aui:button-row>
	</aui:form>
</div>