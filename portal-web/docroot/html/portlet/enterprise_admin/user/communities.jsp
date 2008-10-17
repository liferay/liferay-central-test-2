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
List<Group> communities = (List<Group>)request.getAttribute("user.communities");
%>

<liferay-util:buffer var="removeCommunityIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<h3><liferay-ui:message key="communities" /></h3>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "communitiesSearchContainer" %>'
	headerNames="name"
>
	<liferay-ui:search-container-results
		results="<%= communities %>"
		total="<%= communities.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Group"
		keyProperty="groupId"
		modelVar="curCommunity"
	>
		<liferay-ui:search-container-column-text
			name="name"
			property="name"
		/>

		<liferay-ui:search-container-column-text>
			<a href="javascript: ;" onclick="Liferay.SearchContainer.get('<portlet:namespace />communitiesSearchContainer').deleteRow(this, <%= String.valueOf(curCommunity.getGroupId()) %>);"><%= removeCommunityIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<liferay-ui:icon image="add" label="true" message="add" url='<%= "javascript: " + renderResponse.getNamespace() + "openCommunitySelector();" %>'/>

<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectCommunityURL">
	<portlet:param name="struts_action" value="/enterprise_admin/select_community" />
	<portlet:param name="tabs1" value="communities" />
</portlet:renderURL>

<liferay-util:buffer var="removeCommunityIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<script type="text/javascript">
	function <portlet:namespace />openCommunitySelector() {
		var communityWindow = window.open('<%= selectCommunityURL %>', 'community', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		void('');
		communityWindow.focus();
	}

	function <portlet:namespace />selectGroup(groupId, name, target) {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />communitiesSearchContainer');
		var rowColumns = [];

		rowColumns.push(name);
		rowColumns.push(<portlet:namespace />createURL('javascript: ;', '<%= UnicodeFormatter.toString(removeCommunityIcon) %>', 'Liferay.SearchContainer.get(\'<portlet:namespace />communitiesSearchContainer\').deleteRow(this, ' + groupId + ')'));

		searchContainer.addRow(rowColumns, groupId);

		searchContainer.updateDataStore();
	}
</script>