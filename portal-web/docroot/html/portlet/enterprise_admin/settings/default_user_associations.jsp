<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
String adminDefaultGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_GROUP_NAMES));
String adminDefaultRoleNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_ROLE_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES));
String adminDefaultUserGroupNames = ParamUtil.getString(request, "settings(" + PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES + ")", PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES));
%>

<h3><liferay-ui:message key="default-user-associations" /></h3>

<fieldset class="exp-block-labels">
	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_GROUP_NAMES %>)"><liferay-ui:message key="communities" /> <liferay-ui:icon-help message="enter-the-default-community-names-per-line-that-are-associated-with-newly-created-users" /></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_GROUP_NAMES %>)"><%= HtmlUtil.escape(adminDefaultGroupNames) %></textarea>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_ROLE_NAMES %>)"><liferay-ui:message key="roles" /> <liferay-ui:icon-help message="enter-the-default-role-names-per-line-that-are-associated-with-newly-created-users" /></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_ROLE_NAMES %>)"><%= HtmlUtil.escape(adminDefaultRoleNames) %></textarea>
	</div>

	<div class="exp-ctrl-holder">
		<label for="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES %>)"><liferay-ui:message key="user-groups" /> <liferay-ui:icon-help message="enter-the-default-user-group-names-per-line-that-are-associated-with-newly-created-users" /></label>

		<textarea class="lfr-textarea" name="<portlet:namespace />settings(<%= PropsKeys.ADMIN_DEFAULT_USER_GROUP_NAMES %>)"><%= HtmlUtil.escape(adminDefaultUserGroupNames) %></textarea>
	</div>
</fieldset>