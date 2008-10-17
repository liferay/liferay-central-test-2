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
List<Organization> organizations = (List<Organization>)request.getAttribute("user.organizations");
%>

<liferay-util:buffer var="removeOrganizationIcon">
	<liferay-ui:icon image="unlink" message="remove" label="<%= true %>" />
</liferay-util:buffer>

<h3><liferay-ui:message key="organizations" /></h3>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "organizationsSearchContainer" %>'
	headerNames="organization,type"
>
	<liferay-ui:search-container-results
		results="<%= organizations %>"
		total="<%= organizations.size() %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Organization"
		keyProperty="organizationId"
		modelVar="curOrganization"
	>
		<liferay-ui:search-container-column-text
			name="organization"
			property="organizationBreadcrumb"
		/>

		<liferay-ui:search-container-column-text
			name="type"
			value="<%= LanguageUtil.get(pageContext, curOrganization.getType()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a href="javascript: ;" onclick="Liferay.SearchContainer.get('<portlet:namespace />organizationsSearchContainer').deleteRow(this, <%= String.valueOf(curOrganization.getOrganizationId()) %>);"><%= removeOrganizationAction %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<liferay-ui:icon image="add" label="true" message="add" url='<%= "javascript: " + renderResponse.getNamespace() + "openOrganizationSelector();" %>'/>

<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="selectOrganizationURL">
	<portlet:param name="struts_action" value="/enterprise_admin/select_organization" />
	<portlet:param name="tabs1" value="organizations" />
</portlet:renderURL>

<script type="text/javascript">
	function <portlet:namespace />getSelectedOrganizationIds() {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationsSearchContainer');

		return searchContainer.getIds().join(',');
	}

	function <portlet:namespace />openOrganizationSelector() {
		var organizationWindow = window.open('<%= selectOrganizationURL %>', 'organization', 'directories=no,height=640,location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no,width=680');

		void('');
		organizationWindow.focus();
	}

	function <portlet:namespace />selectOrganization(organizationId, name, type) {
		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />organizationsSearchContainer');

		var rowColumns = [];

		rowColumns.push(name);
		rowColumns.push(Liferay.Language.get(type));
		rowColumns.push(<portlet:namespace />createURL('javascript: ;', '<%= UnicodeFormatter.toString(removeOrganizationIcon) %>', 'Liferay.SearchContainer.get(\'<portlet:namespace />organizationsSearchContainer\').deleteRow(this, ' + organizationId + ')'));

		searchContainer.addRow(rowColumns, organizationId);
		searchContainer.updateDataStore();
	}
</script>