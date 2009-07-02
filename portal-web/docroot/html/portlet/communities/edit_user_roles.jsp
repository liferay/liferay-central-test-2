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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "users");
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

String groupName = group.getName();

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");

int roleType = RoleConstants.TYPE_COMMUNITY;

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

	groupName = organization.getName();

	roleType = RoleConstants.TYPE_ORGANIZATION;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_user_roles");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
portletURL.setParameter("roleId", String.valueOf(roleId));

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setParameter("struts_action", "/communities/edit_user_roles");
breadcrumbsURL.setParameter("tabs1", tabs1);
breadcrumbsURL.setParameter("tabs2", tabs2);
breadcrumbsURL.setParameter("redirect", redirect);
breadcrumbsURL.setParameter("groupId", String.valueOf(group.getGroupId()));

String breadcrumbs = "<a href=\"" + HtmlUtil.escape(redirect) + "\">" + LanguageUtil.get(pageContext, group.isOrganization() ? "organizations" : "communities") + "</a> &raquo; ";

breadcrumbs += "<a href=\"" + breadcrumbsURL.toString() + "\">" + HtmlUtil.escape(groupName) + "</a>";

if (role != null) {
	breadcrumbsURL.setParameter("roleId", String.valueOf(roleId));

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + HtmlUtil.escape(role.getTitle(locale)) + "</a>";
}

request.setAttribute("edit_user_roles.jsp-tabs1", tabs1);
request.setAttribute("edit_user_roles.jsp-tabs2", tabs2);

request.setAttribute("edit_user_roles.jsp-cur", cur);

request.setAttribute("edit_user_roles.jsp-redirect", redirect);

request.setAttribute("edit_user_roles.jsp-group", group);
request.setAttribute("edit_user_roles.jsp-groupName", groupName);
request.setAttribute("edit_user_roles.jsp-role", role);
request.setAttribute("edit_user_roles.jsp-roleId", roleId);
request.setAttribute("edit_user_roles.jsp-roleType", roleType);
request.setAttribute("edit_user_roles.jsp-organization", organization);

request.setAttribute("edit_user_roles.jsp-portletURL", portletURL);

request.setAttribute("edit_user_roles.jsp-breadcrumbs", breadcrumbs);
%>

<script type="text/javascript">
	function <portlet:namespace />updateRoleGroups(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_group_role_user_groups";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserGroupIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserGroupIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_user_roles" /></portlet:actionURL>");
	}
	function <portlet:namespace />updateUserGroupRoleUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_group_role_users";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = Liferay.Util.listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_user_roles" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs1) %>" />
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= String.valueOf(group.getGroupId()) %>" />
<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>" />

<%= LanguageUtil.get(pageContext, "assign-" + (group.isOrganization() ? "organization" : "community") + "-roles-to-users") %>

<br /><br />

<c:choose>
	<c:when test="<%= role == null %>">
		<liferay-util:include page="/html/portlet/communities/edit_user_roles_role.jsp" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portlet/communities/edit_user_roles_users.jsp" />
	</c:otherwise>
</c:choose>

</form>