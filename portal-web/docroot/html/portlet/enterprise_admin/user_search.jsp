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
UserSearch searchContainer = (UserSearch)request.getAttribute("liferay-ui:search:searchContainer");
boolean showAddButton = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search:showAddButton"));

UserDisplayTerms displayTerms = (UserDisplayTerms)searchContainer.getDisplayTerms();
%>

<liferay-ui:search-toggle
	id="toggle_id_enterprise_admin_user_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search-users"
>
	<%@ include file="/html/portlet/enterprise_admin/user/user_search_advanced.jspf" %>
</liferay-ui:search-toggle>

<c:if test="<%= showAddButton %>">
	<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) || OrganizationPermissionUtil.contains(permissionChecker, displayTerms.getOrganizationId(), ActionKeys.ADD_USER) %>">
		<br />

		<div>
			<input type="button" value="<liferay-ui:message key="add-user" />" onClick="<portlet:namespace />addUser();" />
		</div>
	</c:if>
</c:if>

<script type="text/javascript">
	function <portlet:namespace />addUser() {
		var url = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/enterprise_admin/edit_user" /></portlet:renderURL>';

		if (toggle_id_enterprise_admin_user_searchcurClickValue == 'basic') {
			url += '&<portlet:namespace />redirect=' + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />usersRedirect.value);
			url += '&<portlet:namespace /><%= displayTerms.SCREEN_NAME %>=' + document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>.value;

			submitForm(document.hrefFm, url);
		}
		else {
			document.<portlet:namespace />fm.method = 'post';
			document.<portlet:namespace />fm.<portlet:namespace />redirect.value = document.<portlet:namespace />fm.<portlet:namespace />usersRedirect.value;
			submitForm(document.<portlet:namespace />fm, url);
		}
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.FIRST_NAME %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</script>