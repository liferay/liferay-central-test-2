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
User selUser = (User)request.getAttribute("user.selUser");

List<Group> groups = (List<Group>)request.getAttribute("user.groups");
%>

<liferay-util:buffer var="removeGroupIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<aui:script>
	function <portlet:namespace />openGroupSelector() {
		var groupWindow = window.open('<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/select_community" /></portlet:renderURL>', 'group', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		groupWindow.focus();
	}

	function <portlet:namespace />selectGroup(groupId, name) {
		AUI().use(
			'liferay-search-container',
			function(A) {
				var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

				var rowColumns = [];

				rowColumns.push(name);
				rowColumns.push('<%= RoleConstants.COMMUNITY_MEMBER %>');
				rowColumns.push('<a class="modify-link" data-rowId="' + groupId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeGroupIcon) %></a>');

				searchContainer.addRow(rowColumns, groupId);
				searchContainer.updateDataStore();

				<portlet:namespace />trackChanges();
			}
		);
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
</aui:script>

<h3><liferay-ui:message key="communities" /></h3>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "groupsSearchContainer" %>'
	headerNames="name,roles"
>
	<liferay-ui:search-container-results
		results="<%= groups %>"
		total="<%= groups.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		escapedModel="<%= true %>"
		keyProperty="groupId"
		modelVar="group"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text
			buffer="buffer"
			name="roles"
		>

			<%
			List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(selUser.getUserId(), group.getGroupId());

			Iterator itr = userGroupRoles.iterator();

			while (itr.hasNext()) {
				UserGroupRole userGroupRole = (UserGroupRole)itr.next();

				Role role = RoleLocalServiceUtil.getRole(userGroupRole.getRoleId());

				buffer.append(HtmlUtil.escape(role.getTitle(locale)));

				if (itr.hasNext()) {
					buffer.append(StringPool.COMMA_AND_SPACE);
				}
			}
			%>

		</liferay-ui:search-container-column-text>

		<c:if test="<%= !portletName.equals(PortletKeys.MY_ACCOUNT) %>">
			<liferay-ui:search-container-column-text>
				<a class="modify-link" data-rowId="<%= group.getGroupId() %>" href="javascript:;"><%= removeGroupIcon %></a>
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
		url='<%= "javascript:" + renderResponse.getNamespace() + "openGroupSelector();" %>'
		label="<%= true %>"
		cssClass="modify-link"
	/>
</c:if>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />groupsSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;
			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />trackChanges();
		},
		'.modify-link'
	);
</aui:script>