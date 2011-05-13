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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

<div class="yui3-aui-helper-hidden" data-namespace="<portlet:namespace />" id="<portlet:namespace />addBranch">
	<aui:model-context model="<%= LayoutSetBranch.class %>" />

	<portlet:actionURL var="editLayoutSetBranchURL">
		<portlet:param name="struts_action" value="/staging_bar/edit_layout_set_branch" />
	</portlet:actionURL>

	<aui:form action="<%= editLayoutSetBranchURL %>" enctype="multipart/form-data" method="post" name="fm3">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="groupId" type="hidden" value="<%= group.getGroupId() %>" />
		<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />

		<aui:fieldset>
			<aui:input name="name" />

			<aui:input name="description" />
		</aui:fieldset>

		<aui:button-row>
			<aui:button type="submit" value="add-backstage" />
		</aui:button-row>
	</aui:form>
</div>