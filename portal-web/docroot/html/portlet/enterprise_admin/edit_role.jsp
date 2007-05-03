<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
String redirect = ParamUtil.getString(request, "redirect");

Role role = (Role)request.getAttribute(WebKeys.ROLE);

long roleId = BeanParamUtil.getLong(role, request, "roleId");
%>

<liferay-util:include page="/html/portlet/enterprise_admin/tabs1.jsp">
	<liferay-util:param name="tabs1" value="roles" />
	<liferay-util:param name="backURL" value="<%= redirect %>" />
</liferay-util:include>

<c:choose>
	<c:when test="<%= (role != null) && PortalUtil.isSystemRole(role.getName()) %>">
		<%= LanguageUtil.format(pageContext, "x-is-a-required-system-role", role.getName()) %>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">
			function <portlet:namespace />saveRole() {
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= role == null ? Constants.ADD : Constants.UPDATE %>";
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_role" /></portlet:actionURL>");
			}
		</script>

		<form method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveRole(); return false;">
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
		<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>">

		<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />
		<liferay-ui:error exception="<%= RoleNameException.class %>" message="please-enter-a-valid-name" />

		<table class="liferay-table">

		<c:if test="<%= role != null %>">
			<tr>
				<td>
					<bean:message key="old-name" />
				</td>
				<td>
					<%= role.getName() %>
				</td>
			</tr>
		</c:if>

		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, ((role != null) ? "new-name" : "name")) %>
			</td>
			<td>
				<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="name" />
			</td>
		</tr>
		<tr>
			<td>
				<bean:message key="type" />
			</td>
			<td>
				<c:choose>
					<c:when test="<%= role == null %>">
						<select name="<portlet:namespace/>type">
							<option value="<%= RoleImpl.TYPE_REGULAR %>"><%=LanguageUtil.get(pageContext, "regular")%></option>
							<option value="<%= RoleImpl.TYPE_COMMUNITY %>"><%=LanguageUtil.get(pageContext, "community")%></option>
					</c:when>
					<c:otherwise>
						<%= LanguageUtil.get(pageContext, (role.getType() == RoleImpl.TYPE_REGULAR) ? "regular" : "community") %>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</table>

		<br />

		<input type="submit" value="<bean:message key="save" />">

		<input type="button" value="<bean:message key="cancel" />" onClick="self.location = '<%= redirect %>';">

		</form>

		<script type="text/javascript">
			document.<portlet:namespace />fm.<portlet:namespace />name.focus();
		</script>
	</c:otherwise>
</c:choose>