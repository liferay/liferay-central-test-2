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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<form method="post" name="<portlet:namespace />fm">

<liferay-ui:tabs names="communities" />

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/document_library/select_group");

GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/enterprise_admin/group_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<div class="separator"><!-- --></div>

<%
GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

List myPlaces = user.getMyPlaces();

int total = myPlaces.size();

searchContainer.setTotal(total);

List results = myPlaces;

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	Group group = (Group)results.get(i);

	group = group.toEscapedModel();

	ResultRow row = new ResultRow(group, group.getGroupId(), i);

	String groupName = group.getName();

	if (group.isOrganization()) {
		Organization organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());

		groupName = organization.getName();
	}
	else if (group.isUser()) {
		groupName = LanguageUtil.get(pageContext, "my-community");
	}

	StringMaker sm = new StringMaker();

	sm.append("javascript: opener.");
	sm.append(renderResponse.getNamespace());
	sm.append("selectGroup('");
	sm.append(group.getGroupId());
	sm.append("', '");
	sm.append(UnicodeFormatter.toString(groupName));
	sm.append("'); window.close();");

	String rowHREF = sm.toString();

	// Name

	row.addText(groupName, rowHREF);

	// Type

	row.addText(LanguageUtil.get(pageContext, group.getTypeLabel()), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</form>