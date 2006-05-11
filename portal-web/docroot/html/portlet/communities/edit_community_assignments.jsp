<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "current");

String cur = ParamUtil.getString(request, "cur");

String redirect = ParamUtil.getString(request, "redirect");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_community_assignments");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("groupId", group.getGroupId());
%>

<script type="text/javascript">
	function <portlet:namespace />updateGroupUsers(redirect) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "group_users";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm, "<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community_assignments" /></portlet:actionURL>");
	}
</script>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />tabs1" type="hidden" value="<%= tabs1 %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= group.getGroupId() %>">

<%= LanguageUtil.get(pageContext, "edit-assignments-for-community") %>: <%= group.getName() %>

<br><br>

<input name="<portlet:namespace />addUserIds" type="hidden" value="">
<input name="<portlet:namespace />removeUserIds" type="hidden" value="">

<liferay-ui:tabs
	names="current,available"
	url="<%= portletURL.toString() %>"
	backURL="<%= redirect %>"
/>

<%
UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

searchContainer.setRowChecker(new UserGroupChecker(renderResponse, group));
%>

<liferay-ui:search-form
	page="/html/portlet/enterprise_admin/user_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

Map userParams = new HashMap();

if (tabs1.equals("current")) {
	userParams.put("usersGroups", group.getGroupId());
}

int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator());

searchContainer.setTotal(total);

List results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

searchContainer.setResults(results);
%>

<br><div class="beta-separator"></div><br>

<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update-associations") %>' onClick="<portlet:namespace />updateGroupUsers('<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>');">

<br><br>

<%
List headerNames = new ArrayList();

headerNames.add("name");
headerNames.add("email-address");

searchContainer.setHeaderNames(headerNames);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	User user2 = (User)results.get(i);

	ResultRow row = new ResultRow(user2, user2.getPrimaryKey().toString(), i);

	// Name and email address

	row.addText(user2.getFullName());
	row.addText(user2.getEmailAddress());

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>