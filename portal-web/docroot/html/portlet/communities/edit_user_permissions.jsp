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
String tabs2 = ParamUtil.getString(request, "tabs2", "current");

String assignmentType = ParamUtil.getString(request, "assignmentType");
String resourceId = ParamUtil.getString(request, "resourceId");

String cur = ParamUtil.getString(request, "cur");

Group group = (Group)request.getAttribute(WebKeys.GROUP);

String portletResource = ParamUtil.getString(request, "portletResource");
String modelResource = ParamUtil.getString(request, "modelResource");

String portletResourceName = null;
if (Validator.isNotNull(portletResource)) {
	Portlet portlet = PortletServiceUtil.getPortletById(company.getCompanyId(), portletResource);

	portletResourceName = PortalUtil.getPortletTitle(portlet, application, locale);
}

String modelResourceName = ResourceActionsUtil.getModelResource(pageContext, modelResource);

String selResource = modelResource;
String selResourceName = modelResourceName;

if (Validator.isNull(modelResource)) {
	selResource = portletResource;
	selResourceName = portletResourceName;
}

int actionPos = ParamUtil.getInteger(request, "actionPos");
String actionIds = ParamUtil.getString(request, "actionIds");
String[] actionIdsArray = StringUtil.split(actionIds);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/communities/edit_user_permissions");
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("tabs2", tabs2);
portletURL.setParameter("groupId", group.getGroupId());
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("modelResource", modelResource);

// Breadcrumbs

PortletURL breadcrumbsURL = renderResponse.createRenderURL();

breadcrumbsURL.setWindowState(WindowState.MAXIMIZED);

breadcrumbsURL.setParameter("struts_action", "/communities/view");
breadcrumbsURL.setParameter("tabs1", tabs1);
breadcrumbsURL.setParameter("groupId", group.getGroupId());

String breadcrumbs = "<a href=\"" + breadcrumbsURL.toString() + "\">" + LanguageUtil.get(pageContext, "communities") + "</a> &raquo; ";

breadcrumbsURL.setParameter("struts_action", "/communities/edit_user_permissions");

breadcrumbs += "<a href=\"" + breadcrumbsURL.toString() + "\">" + group.getName() + "</a>";

if (Validator.isNotNull(portletResource)) {
	breadcrumbsURL.setParameter("portletResource", portletResource);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + portletResourceName + "</a>";
}

if (Validator.isNotNull(modelResource)) {
	breadcrumbsURL.setParameter("modelResource", modelResource);

	breadcrumbs += " &raquo; <a href=\"" + breadcrumbsURL.toString() + "\">" + modelResourceName + "</a>";
}
%>

<script type="text/javascript">
	function <portlet:namespace />updateActions() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "actions";
		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = "<%= portletURL.toString() %>";
		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />updateUserPermissions(actionPos) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "user_permissions";

		var redirect = "";

		if (actionPos == -1) {
			redirect = "<%= portletURL.toString() %>";
		}
		else if (actionPos == <%= actionIdsArray.length %>) {
			redirect = "<%= portletURL.toString() %>&<portlet:namespace />actionPos=-1";
		}
		else {
			redirect = "<%= portletURL.toString() %>&<portlet:namespace />actionPos=" +
				actionPos + "&<portlet:namespace />actionIds=<%= actionIds %>";

			if (actionPos == <%= actionPos %>) {
				redirect += "&<portlet:namespace />cur=<%= cur %>";
			}
		}

		document.<portlet:namespace />fm.<portlet:namespace />redirect.value = redirect;
		document.<portlet:namespace />fm.<portlet:namespace />addUserIds.value = listCheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		document.<portlet:namespace />fm.<portlet:namespace />removeUserIds.value = listUncheckedExcept(document.<portlet:namespace />fm, "<portlet:namespace />allRowIds");
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/edit_user_permissions" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="">
<input name="<portlet:namespace />groupId" type="hidden" value="<%= group.getGroupId() %>">
<input name="<portlet:namespace />portletResource" type="hidden" value="<%= portletResource %>">
<input name="<portlet:namespace />modelResource" type="hidden" value="<%= modelResource %>">
<input name="<portlet:namespace />actionPos" type="hidden" value="<%= actionPos %>">
<input name="<portlet:namespace />actionIds" type="hidden" value="<%= actionIds %>">
<input name="<portlet:namespace />assignmentType" type="hidden" value="<%= assignmentType %>">
<input name="<portlet:namespace />resourceId" type="hidden" value="<%= resourceId %>">
<input name="<portlet:namespace />tabs2" type="hidden" value="<%= tabs2 %>">

<%
PortletURL communitiesURL = renderResponse.createRenderURL();

communitiesURL.setWindowState(WindowState.MAXIMIZED);

communitiesURL.setParameter("struts_action", "/communities/view");
%>

<liferay-ui:tabs
	names="current,available"
	url="<%= communitiesURL.toString() %>"
/>

<c:choose>
	<c:when test="<%= actionPos == -1 %>">
		<%= breadcrumbs %>

		<br><br>

		<%= LanguageUtil.get(pageContext, "you-have-successfully-updated-the-following-users-with-the-following-permissions") %>

		<br><br>

		<%
		SearchContainer searchContainer = new SearchContainer();

		Map userParams = new TreeMap();
		userParams.put("usersGroups", group.getGroupId());
		userParams.put("permission", resourceId);

		// CAN'T GET THIS TO WORK!!!
		// userParams.put("permissionActions", actionIdsArray);

		int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), null, null, null, null, true, userParams, true);

		searchContainer.setTotal(total);

		List results = UserLocalServiceUtil.search(company.getCompanyId(), null, null, null, null, true, userParams, true, searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

		searchContainer.setResults(results);

		List headerNames = new ArrayList();

		headerNames.add("users");
		headerNames.add("email-address");
		headerNames.add("actions");

		searchContainer.setHeaderNames(headerNames);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			User user2 = (User)results.get(i);

			ResultRow row = new ResultRow(user2, user2.getPrimaryKey().toString(), i);

			// Name
			row.addText(user2.getFullName());

			// Email Address
			row.addText(user2.getEmailAddress());

			// Permissions
			List permissions = PermissionLocalServiceUtil.getUserPermissions(user2.getUserId(), resourceId);

			List actions = ResourceActionsUtil.getActions(permissions);
			List actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

			row.addText(StringUtil.merge(actionsNames, ", "));


			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "finished") %>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/communities/view" /><portlet:param name="tabs1" value="<%= tabs1 %>" /></portlet:renderURL>';">
	</c:when>
	<c:when test="<%= actionIdsArray.length > 0 %>">
		<input name="<portlet:namespace />addUserIds" type="hidden" value="">
		<input name="<portlet:namespace />removeUserIds" type="hidden" value="">

		<%= breadcrumbs %>

		<br><br>

		<%
		String actionId = "";

		portletURL.setParameter("actionIds", actionIds);
		portletURL.setParameter("resourceId", resourceId);
		portletURL.setParameter("assignmentType", assignmentType);

		if (assignmentType.equals("all")) {
			String actionNames = "";
			for (int i=0; i < actionIdsArray.length; i++) {
				actionNames += ResourceActionsUtil.getAction(pageContext, actionIdsArray[i]);
				if (i < actionIdsArray.length - 1) {
					actionNames += ", ";
				}
			}
			%>
			<%= LanguageUtil.format(pageContext, "select-users-to-associate-with-the-following-actions-x", "<b>" + actionNames + "</b>") %>
			<%
		}
		else {
			actionId = actionIdsArray[actionPos];

			portletURL.setParameter("actionPos", String.valueOf(actionPos));
			%>
			<%= LanguageUtil.format(pageContext, "select-users-to-associate-with-the-action-x", "<b>" + ResourceActionsUtil.getAction(pageContext, actionId) + "</b>") %>
			<%
		}
		%>

		<br><br>

		<liferay-ui:tabs
			names="current,available"
			param="tabs2"
			url="<%= portletURL.toString() %>"
		/>

		<%
		UserSearch searchContainer = new UserSearch(renderRequest, portletURL);

		if (assignmentType.equals("all")) {
			searchContainer.setRowChecker(new UserPermissionChecker(renderResponse, actionIdsArray, resourceId));
		}
		else {
			searchContainer.setRowChecker(new UserPermissionChecker(renderResponse, actionId, resourceId));
		}
		%>

		<liferay-ui:search-form
			page="/html/portlet/enterprise_admin/user_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		UserSearchTerms searchTerms = (UserSearchTerms)searchContainer.getSearchTerms();

		Map userParams = new TreeMap();
		userParams.put("usersGroups", group.getGroupId());

		if (tabs2.equals("current")) {
			userParams.put("permission", resourceId);

			// CAN'T GET THIS TO WORK!!!
			// userParams.put("permissionActions", actionIdsArray);
		}

		int total = UserLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = UserLocalServiceUtil.search(company.getCompanyId(), searchTerms.getFirstName(), searchTerms.getMiddleName(), searchTerms.getLastName(), searchTerms.getEmailAddress(), searchTerms.isActive(), userParams, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), new ContactLastNameComparator(true));

		searchContainer.setResults(results);
		%>

		<br><div class="beta-separator"></div><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "update-associations") %>' onClick="<portlet:namespace />updateUserPermissions(<%= actionPos %>);">

		<br><br>

		<%
		List headerNames = new ArrayList();

		headerNames.add("name");
		headerNames.add("email-address");
		headerNames.add("permissions");

		searchContainer.setHeaderNames(headerNames);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			User user2 = (User)results.get(i);

			ResultRow row = new ResultRow(user2, user2.getPrimaryKey().toString(), i);

			// Name
			row.addText(user2.getFullName());

			// Email Address
			row.addText(user2.getEmailAddress());

			// Permissions
			List permissions = PermissionLocalServiceUtil.getUserPermissions(user2.getUserId(), resourceId);

			List actions = ResourceActionsUtil.getActions(permissions);
			List actionsNames = ResourceActionsUtil.getActionsNames(pageContext, actions);

			row.addText(StringUtil.merge(actionsNames, ", "));


			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

		<br>

		<table border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "previous") %>' onClick="<portlet:namespace />updateUserPermissions(<%= actionPos - 1 %>);">

			<%
			if (assignmentType.equals("all")) {
			%>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "next") %>' onClick="<portlet:namespace />updateUserPermissions(<%= actionIdsArray.length %>);">
			<%
			} else {
			%>
				<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "next") %>' onClick="<portlet:namespace />updateUserPermissions(<%= actionPos + 1 %>);">
			<%
			}
			%>
			</td>
		</tr>
		</table>
	</c:when>
	<c:when test="<%= Validator.isNotNull(portletResource) || Validator.isNotNull(modelResource) %>">
		<%= breadcrumbs %>

		<br><br>

		<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<%= LanguageUtil.get(pageContext, "action") %>
			</td>
			<td style="padding-left: 10px;"></td>
			<td>
				<%= LanguageUtil.get(pageContext, "selected") %>
			</td>
		</tr>

		<%
		List actions = ResourceActionsUtil.getResourceActions(company.getCompanyId(), portletResource, modelResource);

		Collections.sort(actions, new ActionComparator(company.getCompanyId(), locale));

		for (int i = 0; i < actions.size(); i++) {
			String actionId = (String)actions.get(i);
		%>

			<tr>
				<td>
					<%= ResourceActionsUtil.getAction(pageContext, actionId) %>
				</td>
				<td style="padding-left: 10px;"></td>
				<td>
					<input type="checkbox" name="<portlet:namespace />action<%= actionId %>">
				</td>
			</tr>

		<%
		}
		%>

		</table>

		<br>

		<input type="radio" name="assignmentType" value="all" checked><%= LanguageUtil.get(pageContext, "assign-all-checked-actions-to-users") %>

		<br>

		<input type="radio" name="assignmentType" value="individual"><%= LanguageUtil.get(pageContext, "assign-checked-actions-individually-to-users") %>

		<br><br>

		<input class="portlet-form-button" type="button" value='<%= LanguageUtil.get(pageContext, "next") %>' onClick="<portlet:namespace />updateActions();">

		<c:if test="<%= Validator.isNull(modelResource) %>">

			<%
			List modelResources = ResourceActionsUtil.getPortletModelResources(portletResource);
			%>

			<c:if test="<%= modelResources.size() > 0 %>">
				<br><br>

				<liferay-ui:tabs names="resources" />

				<%
				SearchContainer searchContainer = new SearchContainer();

				List headerNames = new ArrayList();

				headerNames.add("name");

				searchContainer.setHeaderNames(headerNames);

				Collections.sort(modelResources, new ModelResourceComparator(company.getCompanyId(), locale));

				List resultRows = searchContainer.getResultRows();

				for (int i = 0; i < modelResources.size(); i++) {
					String curModelResource = (String)modelResources.get(i);

					ResultRow row = new ResultRow(curModelResource, curModelResource, i);

					PortletURL rowURL = renderResponse.createRenderURL();

					rowURL.setWindowState(WindowState.MAXIMIZED);

					rowURL.setParameter("struts_action", "/communities/edit_user_permissions");
					rowURL.setParameter("groupId", group.getGroupId());
					rowURL.setParameter("portletResource", portletResource);
					rowURL.setParameter("modelResource", curModelResource);
					rowURL.setParameter("tabs1", tabs1);

					// Name

					row.addText(ResourceActionsUtil.getModelResource(pageContext, curModelResource), rowURL);

					// Add result row

					resultRows.add(row);
				}
				%>

				<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>

		<%
		List headerNames = new ArrayList();

		headerNames.add("portlet");

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null);

		List portlets = PortletServiceUtil.getPortlets(company.getCompanyId());

		Collections.sort(portlets, new PortletTitleComparator(application, locale));

		int total = portlets.size();

		searchContainer.setTotal(total);

		List results = ListUtil.subList(portlets, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			Portlet portlet = (Portlet)results.get(i);

			ResultRow row = new ResultRow(portlet, portlet.getPrimaryKey().toString(), i);

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setWindowState(WindowState.MAXIMIZED);

			rowURL.setParameter("struts_action", "/communities/edit_user_permissions");
			rowURL.setParameter("groupId", group.getGroupId());
			rowURL.setParameter("tabs1", tabs1);
			rowURL.setParameter("portletResource", portlet.getPortletId());

			// Name

			row.addText(PortalUtil.getPortletTitle(portlet, application, locale), rowURL);

			// Add result row

			resultRows.add(row);
		}
		%>

		<%= breadcrumbs %>

		<br><br>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:otherwise>
</c:choose>

</form>