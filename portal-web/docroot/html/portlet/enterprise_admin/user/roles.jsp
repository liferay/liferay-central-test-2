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
List<Role> regularRoles = (List<Role>)request.getAttribute("user.regularRoles");
%>

<liferay-util:buffer var="removeRoleIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<h3><liferay-ui:message key="roles" /></h3>

<h4><liferay-ui:message key="regular-roles" /></h4>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "regularRolesSearchContainer" %>'
	headerNames="name,subtype"
>
	<liferay-ui:search-container-results
		results="<%= regularRoles %>"
		total="<%= regularRoles.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Role"
		keyProperty="roleId"
		modelVar="curRole"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			name="subtype"
			value="<%= LanguageUtil.get(pageContext, curRole.getSubtype()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<c:if test="<%= !curRole.getName().equals(RoleConstants.USER) %>">
				<a href="javascript: ;" onclick="Liferay.SearchContainer.get('<portlet:namespace />regularRolesSearchContainer').deleteRow(this, <%= String.valueOf(curRole.getRoleId()) %>);"><%= removeRoleIcon %></a>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<liferay-ui:icon image="add" label="true" message="add" url='<%= "javascript: " + renderResponse.getNamespace() + "openRegularRolSelector();" %>'/>

<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectRegularRoleURL">
	<portlet:param name="struts_action" value="/enterprise_admin/select_role" />
	<portlet:param name="type" value="<%= String.valueOf(RoleConstants.TYPE_REGULAR) %>" />
	<portlet:param name="tabs1" value="roles" />
</portlet:renderURL>

<script type="text/javascript">
	function <portlet:namespace />openRegularRolSelector() {
		var regularroleWindow = window.open('<%= selectRegularRoleURL %>', 'regularRole', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		void('');
		regularroleWindow.focus();
	}

	function <portlet:namespace />selectRole(roleId, name, type) {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace/>regularRolesSearchContainer');

		var rowColumns = [];

		rowColumns.push(name);
		rowColumns.push(Liferay.Language.get(type));
		rowColumns.push(<portlet:namespace />createURL('javascript: ;', '<%= UnicodeFormatter.toString(removeRoleIcon) %>', 'Liferay.SearchContainer.get(\'<portlet:namespace />regularRolesSearchContainer\').deleteRow(this, ' + roleId + ')'));

		searchContainer.addRow(rowColumns, roleId);

		searchContainer.updateDataStore();
	}
</script>
