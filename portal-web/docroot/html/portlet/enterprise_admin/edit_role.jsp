<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

int type = ParamUtil.getInteger(request, "type");
String subtype = BeanParamUtil.getString(role, request, "subtype");
%>

<liferay-ui:tabs
	names="roles"
	backURL="<%= redirect %>"
/>

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
		<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
		<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
		<input name="<portlet:namespace />roleId" type="hidden" value="<%= roleId %>" />

		<liferay-ui:error exception="<%= DuplicateRoleException.class %>" message="please-enter-a-unique-name" />
		<liferay-ui:error exception="<%= RequiredRoleException.class %>" message="old-role-name-is-a-required-system-role" />
		<liferay-ui:error exception="<%= RoleNameException.class %>" message="please-enter-a-valid-name" />

		<table class="lfr-table">

		<c:if test="<%= role != null %>">
			<tr>
				<td>
					<liferay-ui:message key="old-name" />
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
				<liferay-ui:message key="description" />
			</td>
			<td>
				<liferay-ui:input-field model="<%= Role.class %>" bean="<%= role %>" field="description" />
			</td>
		</tr>
		<tr>
			<td>
				<liferay-ui:message key="type" />
			</td>
			<td>
				<c:choose>
					<c:when test="<%= ((role == null) && (type == 0)) %>">
						<select name="<portlet:namespace/>type">
							<option value="<%= RoleImpl.TYPE_REGULAR %>"><liferay-ui:message key="regular" /></option>
							<option value="<%= RoleImpl.TYPE_COMMUNITY %>"><liferay-ui:message key="community" /></option>
							<option value="<%= RoleImpl.TYPE_ORGANIZATION %>"><liferay-ui:message key="organization" /></option>
						</select>
					</c:when>
					<c:when test="<%= (role == null) %>">
						<input type="hidden" name="<portlet:namespace/>type" value="<%= String.valueOf(type) %>" />

						<%= LanguageUtil.get(pageContext, RoleImpl.getTypeLabel(type)) %>
					</c:when>
					<c:otherwise>
						<%= LanguageUtil.get(pageContext, role.getTypeLabel()) %>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>

		<c:if test="<%= role != null %>">

			<%
			String[] subtypes = null;

			if (role.getType() == RoleImpl.TYPE_COMMUNITY) {
				subtypes = PropsValues.ROLES_COMMUNITY_SUBTYPES;
			}
			else if (role.getType() == RoleImpl.TYPE_ORGANIZATION) {
				subtypes = PropsValues.ROLES_ORGANIZATION_SUBTYPES;
			}
			else if (role.getType() == RoleImpl.TYPE_REGULAR) {
				subtypes = PropsValues.ROLES_REGULAR_SUBTYPES;
			}
			else {
				subtypes = new String[0];
			}
			%>

			<c:if test="<%= subtypes.length > 0 %>">
				<tr>
					<td>
						<liferay-ui:message key="subtype" />
					</td>
					<td>
						<select name="<portlet:namespace/>subtype">
							<option value=""></option>

							<%
							for (String curSubtype : subtypes) {
							%>

								<option <%= subtype.equals(curSubtype) ? "selected" : "" %> value="<%= curSubtype %>"><liferay-ui:message key="<%= curSubtype %>" /></option>

							<%
							}
							%>

						</select>
					</td>
				</tr>
			</c:if>
		</c:if>

		</table>

		<br />

		<input type="submit" value="<liferay-ui:message key="save" />" />

		<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

		</form>

		<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
			<script type="text/javascript">
				Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />name);
			</script>
		</c:if>
	</c:otherwise>
</c:choose>