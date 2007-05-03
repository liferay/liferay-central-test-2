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

<%@ include file="/html/portlet/communities/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "communities-owned");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/view");
portletURL.setParameter("tabs1", tabs1);
%>

<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm">

<liferay-ui:tabs
	names="communities-owned,communities-joined,communities-open"
	url="<%= portletURL.toString() %>"
/>

<%
GroupSearch searchContainer = new GroupSearch(renderRequest, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/enterprise_admin/group_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<c:if test="<%= renderRequest.getWindowState().equals(WindowState.MAXIMIZED) %>">

	<%
	GroupSearchTerms searchTerms = (GroupSearchTerms)searchContainer.getSearchTerms();

	LinkedHashMap groupParams = new LinkedHashMap();

	if (tabs1.equals("communities-owned")) {
		groupParams.put("creatorUserId", new Long(user.getUserId()));
	}
	else if (tabs1.equals("communities-joined")) {
		groupParams.put("usersGroups", new Long(user.getUserId()));
		groupParams.put("active", Boolean.TRUE);
	}
	else if (tabs1.equals("communities-open")) {
		groupParams.put("type", GroupImpl.TYPE_COMMUNITY_OPEN);
		groupParams.put("active", Boolean.TRUE);
	}

	int total = GroupLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams);

	searchContainer.setTotal(total);

	List results = GroupLocalServiceUtil.search(company.getCompanyId(), searchTerms.getName(), searchTerms.getDescription(), groupParams, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);
	%>

	<div class="separator"></div>

	<c:if test="<%= PortalPermission.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) %>">
		<input type="button" value="<bean:message key="create-community" />" onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_community" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" />

		<br /><br />
	</c:if>

	<liferay-ui:error exception="<%= NoSuchLayoutSetException.class %>">

		<%
		NoSuchLayoutSetException nslse = (NoSuchLayoutSetException)errorException;

		String ownerId = nslse.getMessage();

		long groupId = LayoutImpl.getGroupId(ownerId);

		Group group = GroupLocalServiceUtil.getGroup(groupId);
		%>

		<%= LanguageUtil.format(pageContext, "community-x-does-not-have-any-private-pages", group.getName()) %>
	</liferay-ui:error>

	<liferay-ui:error exception="<%= RequiredGroupException.class %>" message="the-group-cannot-be-deleted-because-it-is-a-required-system-group" />

	<%
	List headerNames = new ArrayList();

	headerNames.add("name");
	headerNames.add("members");
	headerNames.add("online-now");

	if (tabs1.equals("communities-owned")) {
		headerNames.add("active");
	}

	headerNames.add(StringPool.BLANK);

	searchContainer.setHeaderNames(headerNames);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Group group = (Group)results.get(i);

		ResultRow row = new ResultRow(new Object[] {group, tabs1}, group.getGroupId(), i);

		PortletURL rowURL = renderResponse.createActionURL();

		rowURL.setWindowState(WindowState.NORMAL);

		rowURL.setParameter("struts_action", "/communities/page");
		rowURL.setParameter("redirect", currentURL);

		// Name

		StringMaker sm = new StringMaker();

		sm.append("<b>");
		sm.append(group.getName());
		sm.append("</b>");

		int publicLayoutsPageCount = group.getPublicLayoutsPageCount();
		int privateLayoutsPageCount = group.getPrivateLayoutsPageCount();

		Group stagingGroup = null;

		if (group.hasStagingGroup()) {
			stagingGroup = group.getStagingGroup();
		}

		if ((tabs1.equals("communities-owned") || tabs1.equals("communities-joined")) &&
			((publicLayoutsPageCount > 0) || (privateLayoutsPageCount > 0))) {

			sm.append("<br />");
			sm.append("<span style=\"font-size: xx-small;\">");

			if (publicLayoutsPageCount > 0) {
				rowURL.setParameter("ownerId", LayoutImpl.PUBLIC + group.getGroupId());

				sm.append("<a href=\"");
				sm.append(rowURL.toString());
				sm.append("\">");
				sm.append(LanguageUtil.get(pageContext, "public-pages"));
				sm.append(" - ");
				sm.append(LanguageUtil.get(pageContext, "live"));
				sm.append(" (");
				sm.append(group.getPublicLayoutsPageCount());
				sm.append(")");
				sm.append("</a>");
			}
			else {
				sm.append(LanguageUtil.get(pageContext, "public-pages"));
				sm.append(" (0)");
			}

			if ((stagingGroup != null) && GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
				rowURL.setParameter("ownerId", LayoutImpl.PUBLIC + stagingGroup.getGroupId());

				if (stagingGroup.getPublicLayoutsPageCount() > 0) {
					sm.append(" / ");
					sm.append("<a href=\"");
					sm.append(rowURL.toString());
					sm.append("\">");
					sm.append(LanguageUtil.get(pageContext, "staging"));
					sm.append("</a>");
				}
			}

			sm.append("<br />");

			if (privateLayoutsPageCount > 0) {
				rowURL.setParameter("ownerId", LayoutImpl.PRIVATE + group.getGroupId());

				sm.append("<a href=\"");
				sm.append(rowURL.toString());
				sm.append("\">");
				sm.append(LanguageUtil.get(pageContext, "private-pages"));
				sm.append(" - ");
				sm.append(LanguageUtil.get(pageContext, "live"));
				sm.append(" (");
				sm.append(group.getPrivateLayoutsPageCount());
				sm.append(")");
				sm.append("</a>");
			}
			else {
				sm.append(LanguageUtil.get(pageContext, "private-pages"));
				sm.append(" (0)");
			}

			if ((stagingGroup != null) && GroupPermission.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_LAYOUTS)) {
				rowURL.setParameter("ownerId", LayoutImpl.PRIVATE + stagingGroup.getGroupId());

				if (stagingGroup.getPrivateLayoutsPageCount() > 0) {
					sm.append(" / ");
					sm.append("<a href=\"");
					sm.append(rowURL.toString());
					sm.append("\">");
					sm.append(LanguageUtil.get(pageContext, "staging"));
					sm.append("</a>");
				}
			}

			sm.append("</span>");
		}

		row.addText(sm.toString());

		// Members

		LinkedHashMap userParams = new LinkedHashMap();

		userParams.put("usersGroups", new Long(group.getGroupId()));

		int membersCount = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, null, null, null, null, true, userParams, true);

		row.addText(String.valueOf(membersCount));

		// Online Now

		int onlineCount = LiveUsers.getGroupUsers(group.getGroupId()).size();

		row.addText(String.valueOf(onlineCount));

		// Active

		if (tabs1.equals("communities-owned")) {
			row.addText(LanguageUtil.get(pageContext, (group.isActive() ? "yes" : "no")));
		}

		// Action

		row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/communities/community_action.jsp");

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
</c:if>

</form>