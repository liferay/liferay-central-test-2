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

<%@ include file="/html/portlet/announcements/init.jsp" %>

<%
themeDisplay.setIncludeServiceJs(true);

String tabs1 = ParamUtil.getString(request, "tabs1", "entries");

String tabs1Names = "entries,old-entries";

if (PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY)) {
	tabs1Names += ",manage-entries";
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/announcements/view");
portletURL.setParameter("tabs1", tabs1);
%>

<c:if test="<%= !portletName.equals(PortletKeys.ALERTS) || (portletName.equals(PortletKeys.ALERTS) && PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY)) %>">
	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("entries") || tabs1.equals("old-entries") %>'>
		<script type="text/javascript">
			function <portlet:namespace />hideEntry(entryId) {
				Liferay.Service.Announcements.AnnouncementsFlag.addFlag({entryId : entryId, flag: <%= AnnouncementsFlagImpl.HIDDEN %>});

				jQuery('#<portlet:namespace/>' + entryId).hide('slow');
			}
		</script>

		<%
		LinkedHashMap<Long, long[]> scopes = AnnouncementsUtil.getAnnouncementScopes(user.getUserId());

		int flagValue = AnnouncementsFlagImpl.NOT_HIDDEN;

		if (tabs1.equals("old-entries")) {
			flagValue = AnnouncementsFlagImpl.HIDDEN;
		}

		SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "no-entries-were-found");

		int total = AnnouncementsEntryLocalServiceUtil.getEntriesCount(user.getUserId(), scopes, portletName.equals(PortletKeys.ALERTS), flagValue);

		searchContainer.setTotal(total);

		List<AnnouncementsEntry> results = AnnouncementsEntryLocalServiceUtil.getEntries(user.getUserId(), scopes, portletName.equals(PortletKeys.ALERTS), flagValue, searchContainer.getStart(), searchContainer.getEnd());

		searchContainer.setResults(results);

		for (int i = 0; i < results.size(); i++) {
			AnnouncementsEntry entry = results.get(i);

			boolean readEntry = false;

			try {
				AnnouncementsFlagLocalServiceUtil.getFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagImpl.READ);

				readEntry = true;
			}
			catch (NoSuchFlagException nsfe) {
				AnnouncementsFlagLocalServiceUtil.addFlag(user.getUserId(), entry.getEntryId(), AnnouncementsFlagImpl.READ);
			}

			String className = StringPool.BLANK;

			if (i == 1) {
				className = "first";
			}
			else if ((i + 1) == results.size()) {
				className = "last";
			}
		%>

			<div class="entry read-<%= readEntry %> <%= className %>" id="<portlet:namespace/><%= entry.getEntryId() %>">
				<div class="edit-actions">
					<table class="lfr-table">
					<tr>
						<c:if test="<%= AnnouncementsEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
							<td class="edit-entry">
								<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL">
									<portlet:param name="struts_action" value="/announcements/edit_entry" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
								</portlet:renderURL>

								<liferay-ui:icon image="edit" url="<%= editURL %>" label="<%= true %>" />
							</td>
						</c:if>

						<c:if test="<%= AnnouncementsEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
							<td class="delete-entry">
								<portlet:actionURL var="deleteURL">
									<portlet:param name="struts_action" value="/announcements/edit_entry" />
									<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
									<portlet:param name="redirect" value="<%= currentURL %>" />
									<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
								</portlet:actionURL>

								<liferay-ui:icon-delete url="<%= deleteURL %>" label="<%= true %>" />
							</td>
						</c:if>

						<td class="hide-entry">
							<c:if test='<%= tabs1.equals("entries") && themeDisplay.isSignedIn() %>'>
								<liferay-ui:icon image="close" message="hide" url='<%= "javascript: " + renderResponse.getNamespace() + "hideEntry(" + entry.getEntryId() + ");" %>' />
							</c:if>
						</td>
					</tr>
					</table>
				</div>

				<h3 class="entry-title">
					<c:choose>
						<c:when test="<%= Validator.isNotNull(entry.getUrl()) %>">
							<a class="entry-url" href="<%= entry.getUrl() %>"><%= entry.getTitle() %></a>
						</c:when>
						<c:when test="<%= Validator.isNull(entry.getUrl()) %>">
							<%= entry.getTitle() %>
						</c:when>
					</c:choose>
				</h3>

				<p class="entry-content entry-type-<%= entry.getType() %>">
					<%= entry.getContent() %>
				</p>
			</div>

		<%
		}
		%>

		<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
	</c:when>
	<c:when test='<%= tabs1.equals("manage-entries") %>'>

		<%
		String distributionScope = ParamUtil.getString(request, "distributionScope");

		long classNameId = -1;
		long classPK = -1;

		String[] distributionScopeArray = StringUtil.split(distributionScope);

		if (distributionScopeArray.length == 2) {
			classNameId = GetterUtil.getLong(distributionScopeArray[0]);
			classPK = GetterUtil.getLong(distributionScopeArray[1]);
		}

		if ((classNameId == 0) && (classPK == 0) && !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
		%>

		<script type="text/javascript">
			function <portlet:namespace />selectDistributionScope(distributionScope) {
				var url = "<%= portletURL.toString() %>&<portlet:namespace />distributionScope=" + distributionScope;
				submitForm(document.<portlet:namespace />fm, url);
			}
		</script>

		<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;" />

		<table class="lfr-table">
		<tr>
			<td>
				<liferay-ui:message key="distribution-scope" />
			</td>
			<td>
				<select name="<portlet:namespace />distributionScope" onChange="<portlet:namespace />selectDistributionScope(this.value);">
					<option value=""></option>

					<c:if test="<%= permissionChecker.isOmniadmin() %>">
						<option <%= ((classNameId == 0) && (classPK == 0)) ? "selected" : "" %> value="0,0"><liferay-ui:message key="general" /></option>
					</c:if>

					<optgroup label="<liferay-ui:message key="communities" />">

						<%
						List<Group> groups = GroupLocalServiceUtil.getUserGroups(user.getUserId());

						for (Group group : groups) {
							if (group.isCommunity() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
						%>

								<option <%= (classPK == group.getGroupId()) ? "selected" : "" %> value="<%= PortalUtil.getClassNameId(Group.class) %><%= StringPool.COMMA %><%= group.getGroupId() %>"><%= group.getName() %></option>

						<%
							}
						}
						%>

					</optgroup>
					<optgroup label="<liferay-ui:message key="organizations" />">

						<%
						List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(user.getUserId());

						for (Organization organization : organizations) {
							if (OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.ASSIGN_MEMBERS)) {
						%>

								<option <%= (classPK == organization.getOrganizationId()) ? "selected" : "" %> value="<%= PortalUtil.getClassNameId(Organization.class) %><%= StringPool.COMMA %><%= organization.getOrganizationId() %>"><%= organization.getName() %></option>

						<%
							}
						}
						%>

					</optgroup>
					<optgroup label="<liferay-ui:message key="roles" />">

						<%
						List<Role> roles = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId());

						for (Role role : roles) {
							if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
						%>

								<option <%= (classPK == role.getRoleId()) ? "selected" : "" %> value="<%= PortalUtil.getClassNameId(Role.class) %><%= StringPool.COMMA %><%= role.getRoleId() %>"><%= role.getName() %></option>

						<%
							}
						}
						%>

					</optgroup>
					<optgroup label="<liferay-ui:message key="user-groups" />">

						<%
						List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

						for (UserGroup userGroup : userGroups) {
							if (UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
						%>

								<option <%= (classPK == userGroup.getUserGroupId()) ? "selected" : "" %> value="<%= PortalUtil.getClassNameId(UserGroup.class) %><%= StringPool.COMMA %><%= userGroup.getUserGroupId() %>"><%= userGroup.getName() %></option>

						<%
							}
						}
						%>

					</optgroup>
				</select>
			</td>
		</tr>
		</table>

		<br />

		<input type="button" value='<liferay-ui:message key="add-entry" />' onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/announcements/edit_entry" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'" />

		<c:if test="<%= Validator.isNotNull(distributionScope) %>">
			<br /><br />

			<div class="separator"><!-- --></div>

			<%
			List<String> headerNames = new ArrayList<String>();

			headerNames.add("title");
			headerNames.add("type");
			headerNames.add("modified-date");
			headerNames.add("display-date");
			headerNames.add("expiration-date");
			headerNames.add(StringPool.BLANK);

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "no-entries-were-found");

			int total = AnnouncementsEntryLocalServiceUtil.getEntriesCount(classNameId, classPK, portletName.equals(PortletKeys.ALERTS));

			searchContainer.setTotal(total);

			List<AnnouncementsEntry> results = AnnouncementsEntryLocalServiceUtil.getEntries(classNameId, classPK, portletName.equals(PortletKeys.ALERTS), searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(results);

			List resultRows = searchContainer.getResultRows();

			for (int i = 0; i < results.size(); i++) {
				AnnouncementsEntry entry = results.get(i);

				entry = entry.toEscapedModel();

				ResultRow row = new ResultRow(entry, entry.getEntryId(), i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", "/announcements/edit_entry");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

				// Title

				row.addText(entry.getTitle(), rowURL);

				// Type

				row.addText(LanguageUtil.get(pageContext, entry.getType()), rowURL);

				// Modified date

				row.addText(dateFormatDate.format(entry.getModifiedDate()), rowURL);

				// Display date

				row.addText(dateFormatDate.format(entry.getDisplayDate()), rowURL);

				// Expiration date

				row.addText(dateFormatDate.format(entry.getExpirationDate()), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/announcements/entry_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>

		</form>
	</c:when>
</c:choose>