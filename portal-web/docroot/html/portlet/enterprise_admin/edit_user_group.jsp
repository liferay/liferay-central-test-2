<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

UserGroup userGroup = (UserGroup)request.getAttribute(WebKeys.USER_GROUP);

long userGroupId = BeanParamUtil.getLong(userGroup, request, "userGroupId");
%>

<aui:form method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveUserGroup();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="userGroupId" type="hidden" value="<%= userGroupId %>" />

	<liferay-util:include page="/html/portlet/enterprise_admin/user_group/toolbar.jsp">
		<liferay-util:param name="toolbarItem" value='<%= (userGroup == null) ? "add" : "view-all" %>' />
		<liferay-util:param name="backURL" value="<%= PortalUtil.escapeRedirect(backURL) %>" />
	</liferay-util:include>

	<liferay-ui:error exception="<%= DuplicateUserGroupException.class %>" message="please-enter-a-unique-name" />
	<liferay-ui:error exception="<%= RequiredUserGroupException.class %>" message="this-is-a-required-user-group" />
	<liferay-ui:error exception="<%= UserGroupNameException.class %>" message="please-enter-a-valid-name" />

	<aui:model-context bean="<%= userGroup %>" model="<%= UserGroup.class %>" />

	<aui:fieldset>
		<c:if test="<%= userGroup != null %>">
			<aui:field-wrapper label="old-name">
				<%= userGroup.getName() %>
			</aui:field-wrapper>
		</c:if>

		<aui:input label='<%= (userGroup != null) ? "new-name" : "name" %>' name="name" />

		<aui:input name="description" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button onClick="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveUserGroup() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= userGroup == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/enterprise_admin/edit_user_group" /></portlet:actionURL>");
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
	</c:if>
</aui:script>

<%
if (userGroup != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, userGroup.getName(), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-user-group"), currentURL);
}
%>