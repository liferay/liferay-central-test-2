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
List<UserGroup> userGroups = (List<UserGroup>)request.getAttribute("user.userGroups");
%>

<liferay-util:buffer var="removeUserGroupIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<script type="text/javascript">
	function <portlet:namespace />openUserGroupSelector() {
		var userGroupWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_user_group" /></portlet:renderURL>', 'usergroup', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		userGroupWindow.focus();
	}

	function <portlet:namespace />selectUserGroup(userGroupId, name) {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />userGroupsSearchContainer');

		var rowColumns = [];

		rowColumns.push(name);
		rowColumns.push(<portlet:namespace />createURL('javascript:;', '<%= UnicodeFormatter.toString(removeUserGroupIcon) %>', 'Liferay.SearchContainer.get(\'<portlet:namespace />userGroupsSearchContainer\').deleteRow(this, ' + userGroupId + ')'));

		searchContainer.addRow(rowColumns, userGroupId);
		searchContainer.updateDataStore();

		<portlet:namespace />trackChanges();
	}

	function <portlet:namespace />trackChanges() {
		AUI().use(
			'event',
			function(A) {
				A.fire(
					'enterpriseAdmin:trackChanges',
					A.one('.selected .modify-link')
				);
			}
		);
	}
</script>

<h3><liferay-ui:message key="user-groups" /></h3>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "userGroupsSearchContainer" %>'
	headerNames="name"
>
	<liferay-ui:search-container-results
		results="<%= userGroups %>"
		total="<%= userGroups.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.UserGroup"
		escapedModel="<%= true %>"
		keyProperty="userGroupId"
		modelVar="userGroup"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" href="javascript:;" onclick="<portlet:namespace />trackChanges();Liferay.SearchContainer.get('<portlet:namespace />userGroupsSearchContainer').deleteRow(this, <%= userGroup.getUserGroupId() %>);"><%= removeUserGroupIcon %></a>
			</liferay-ui:search-container-column-text>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
	<br />

	<liferay-ui:icon
		image="add"
		message="select"
		url='<%= "javascript:" + renderResponse.getNamespace() + "openUserGroupSelector();" %>'
		label="<%= true %>"
		cssClass="modify-link"
	/>
</c:if>