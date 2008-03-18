<%/**
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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY ND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */%>

<%@ include file="/html/portlet/announcements/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "entries");

String distributionScope = ParamUtil.getString(request, "distributionScope");

boolean readHidden = tabs1.equals("old-entries") ? true : false;

long userId = user.getUserId();

boolean hasAddEntry = PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY);

boolean alerts = false;

String strutsAction = "/announcements/edit_entry";

String tab1Names = "entries";

if (!user.isDefaultUser()) {
	tab1Names += ",old-entries";
}

if (hasAddEntry) {
	tab1Names += ",manage-entries";
}

if (portletName.equals(PortletKeys.ALERTS)) {
	hasAddEntry = PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ALERTS, ActionKeys.ADD_ENTRY);
	alerts = true;
	strutsAction = "/alerts/edit_entry";
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
portletURL.setParameter("struts_action", alerts ? "/alerts/view" : "/announcements/view");
portletURL.setParameter("tabs1", tabs1);
%>

<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/service_unpacked.js" type="text/javascript"></script>

<script type="text/javascript">
	function <portlet:namespace />hideEntry(entryId) {
		Liferay.Service.Announcements.AnnouncementFlag.addAnnouncementFlag({userId: <%= userId %>, entryId : entryId, flag: 2});

		jQuery('#<portlet:namespace/>' + entryId).hide("slow");
	}

	function <portlet:namespace />markEntryAsRead(entryId) {
		Liferay.Service.Announcements.AnnouncementFlag.addAnnouncementFlag({userId: <%= userId %>, entryId : entryId, flag: 1});
	}

	function <portlet:namespace />selectDistributionScope(scope) {
		var url = "<%= portletURL.toString() %>&<portlet:namespace />distributionScope=" + scope;
		submitForm(document.<portlet:namespace />fm, url);
	}
</script>

<c:if test="<%= !user.isDefaultUser() && (!alerts || (alerts && hasAddEntry)) %>">
	<liferay-ui:tabs
		names="<%= tab1Names %>"
		param="tabs1"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("entries") || tabs1.equals("old-entries") %>'>
		<div class="<%= (alerts ? "lfr-alerts" : "lfr-announcements") %>">
			<%
				LinkedHashMap<Long,Long[]> scopes = AnnouncementsUtil.getAnnouncementScopes(userId);

				int flag = AnnouncementFlagImpl.NOT_HIDDEN;

				if (readHidden) {
					flag = AnnouncementFlagImpl.HIDDEN;
				}

				SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "no-entries-were-found");

				int total = AnnouncementEntryLocalServiceUtil.getEntriesCount(userId, scopes, flag, alerts);

				List<AnnouncementEntry> results = AnnouncementEntryLocalServiceUtil.getEntries(userId, scopes, flag, alerts, searchContainer.getStart(), searchContainer.getEnd());

				searchContainer.setTotal(total);

				searchContainer.setResults(results);

				for(AnnouncementEntry entry : results) {
					AnnouncementFlag readFlag = null;
					String isRead = Boolean.FALSE.toString();

					try {
						readFlag = AnnouncementFlagLocalServiceUtil.getAnnouncementFlag(userId, entry.getEntryId(), AnnouncementFlagImpl.READ);
					}
					catch (NoSuchAnnouncementFlagException nsafe) {
					}

					if (readFlag != null) {
						isRead = Boolean.TRUE.toString();
					}
			%>
				<div class="entry read-<%= isRead %>" id="<portlet:namespace/><%= entry.getEntryId() %>">

					<div class="edit-actions">
						<table class="lfr-table">
						<tr>
							<c:if test="<%= AnnouncementEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
								<td>
									<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editEntryURL">
										<portlet:param name="struts_action" value="<%= strutsAction %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
									</portlet:renderURL>

									<liferay-ui:icon image="edit" url="<%= editEntryURL %>" label="<%= true %>" />
								</td>
							</c:if>
							<c:if test="<%= AnnouncementEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
								<td>
									<portlet:actionURL var="deleteEntryURL">
										<portlet:param name="struts_action" value="<%= strutsAction %>" />
										<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon-delete url="<%= deleteEntryURL %>" label="<%= true %>" />
								</td>
							</c:if>
							<td>
								<c:if test="<%= !readHidden && !user.isDefaultUser() %>">
									<liferay-ui:icon image="close" message="hide" url='<%= "javascript: " + renderResponse.getNamespace() + "hideEntry(" + entry.getEntryId() + ");" %>' />
								</c:if>
							</td>
						</tr>
						</table>
					</div>

					<span class="entry-title">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(entry.getUrl()) %>">
								<a class="entry-url" href="<%= entry.getUrl() %>"><%= entry.getTitle() %></a>
							</c:when>
							<c:when test="<%= Validator.isNull(entry.getUrl()) %>">
								<%= entry.getTitle() %>
							</c:when>
						</c:choose>
					</span>

					<span class="entry-content entry-type-<%= entry.getType() %>"><%= entry.getContent() %></span>

					<c:choose>
						<c:when test="<%= entry.getClassNameId() == 0 %>">
							<span class="entry-scope general">
								<liferay-ui:message key="general"/>
							</span>
						</c:when>
						<c:when test="<%= entry.getClassNameId() == userClassNameId %>">
							<span class="entry-scope personal">
								<liferay-ui:message key="personal"/>
							</span>
						</c:when>
						<c:when test="<%= entry.getClassNameId() == roleClassNameId %>">
							<span class="entry-scope role">
								<%
								Role role = RoleLocalServiceUtil.getRole(entry.getClassPK());
								%>
								<liferay-ui:message key="role"/>: <%= role.getName() %>
							</span>
						</c:when>
						<c:when test="<%= entry.getClassNameId() == userGroupClassNameId %>">
							<span class="entry-scope user-group">
								<%
								UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(entry.getClassPK());
								%>
								<liferay-ui:message key="user-group"/>: <%= userGroup.getName() %>
							</span>
						</c:when>
						<c:when test="<%= entry.getClassNameId() == communityClassNameId %>">
							<span class="entry-scope community">
								<%
								Group group2 = GroupLocalServiceUtil.getGroup(entry.getClassPK());
								%>
								<liferay-ui:message key="community"/>: <%= group2.getName() %>
							</span>
						</c:when>
						<c:when test="<%= entry.getClassNameId() == organizationClassNameId %>">
							<span class="entry-scope organization">
								<%
								Organization org = OrganizationLocalServiceUtil.getOrganization(entry.getClassPK());
								%>
								<liferay-ui:message key="organization"/>: <%= org.getName() %>
							</span>
						</c:when>
					</c:choose>

					<c:if test="<%= !user.isDefaultUser() %>">
						<script type="text/javascript">
							<portlet:namespace />markEntryAsRead(<%= entry.getEntryId() %>);
						</script>
					</c:if>
				</div>
			<%
			}
			%>

			<c:if test="<%= (results.size() <= 0) %>">
				<liferay-ui:message key="no-entries-were-found" />
			</c:if>

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="article" />

			<c:if test="<%= alerts && (results.size() <= 0) && !PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY) %>">
				<style type="text/css">
					.portlet-alerts {
						display: none;
					}
				</style>
			</c:if>
		</div>
	</c:when>
	<c:when test='<%= tabs1.equals("manage-entries") %>'>
		<%
		String[] distributionScopeParts = StringUtil.split(distributionScope);

		long classNameId = -1;
		long classPK = -1;

		if (distributionScopeParts.length == 2) {
			classNameId = GetterUtil.getLong(distributionScopeParts[0]);

			if (classNameId >= 0) {
				classPK = GetterUtil.getLong(distributionScopeParts[1]);
			}
		}

		if (classNameId == 0 && classPK == 0 && !permissionChecker.isOmniadmin()) {
			throw new PrincipalException();
		}
		%>

		<form action="<%= portletURL.toString() %>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;" />

		<liferay-ui:message key="select-distribution-scope" />

		<select name="<portlet:namespace />distributionScopes" onChange="<portlet:namespace />selectDistributionScope(this.value);">
			<option value=""></option>

			<c:if test="<%= permissionChecker.isOmniadmin() %>">
				<option value="0,0" <%= (classPK == 0 ? "selected" : "") %>><liferay-ui:message key="general"/></option>
			</c:if>

			<optgroup label='<liferay-ui:message key="roles"/>'>
				<%
				List<Role> roles = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId());

				for (Role role : roles) {
					if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
					%>
						<option value="<%= roleClassNameId + StringPool.COMMA + role.getRoleId() %>" <%= (classPK == role.getRoleId() ? "selected" : "") %>><%= role.getName() %></option>
					<%
					}
				}
				%>
			</optgroup>

			<optgroup label='<liferay-ui:message key="user-groups"/>'>
				<%
				List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

				for (UserGroup userGroup : userGroups) {
					if (UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
					%>
						<option value="<%= userGroupClassNameId + StringPool.COMMA + userGroup.getUserGroupId() %>" <%= (classPK == userGroup.getUserGroupId() ? "selected" : "") %>><%= userGroup.getName() %></option>
					<%
					}
				}
				%>
			</optgroup>

			<optgroup label='<liferay-ui:message key="communities"/>'>
				<%
				List<Group> groups = GroupLocalServiceUtil.getUserGroups(userId);

				for (Group group2 : groups) {
					if (group2.isCommunity() && GroupPermissionUtil.contains(permissionChecker, group2.getGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
					%>
						<option value="<%= communityClassNameId + StringPool.COMMA + group2.getGroupId() %>" <%= (classPK == group2.getGroupId() ? "selected" : "") %>><%= group2.getName() %></option>
					<%
					}
				}
				%>
			</optgroup>

			<optgroup label='<liferay-ui:message key="organizations"/>'>
				<%
				List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(userId);

				for (Organization organization : organizations) {
					if (OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.ASSIGN_MEMBERS)) {
					%>
						<option value="<%= organizationClassNameId + StringPool.COMMA + organization.getOrganizationId() %>" <%= (classPK == organization.getOrganizationId() ? "selected" : "") %>><%= organization.getName() %></option>
					<%
					}
				}
				%>
			</optgroup>
		</select>

		<br /><br />

		<input type="button" value='<liferay-ui:message key="add-entry"/>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="<%= strutsAction %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'" />

		<c:if test="<%= Validator.isNotNull(distributionScope) %>">

			<div class="separator"><!-- --></div>

			<liferay-ui:message key="distribution-scope" />:

			<c:choose>
				<c:when test="<%= classNameId == 0 %>">
					<liferay-ui:message key="general"/>
				</c:when>
				<c:when test="<%= classNameId == userClassNameId %>">
					<%
					User user2 = UserLocalServiceUtil.getUserById(classPK);
					%>
					<liferay-ui:message key="personal"/> &raquo; <%= user2.getFullName() %>
				</c:when>
				<c:when test="<%= classNameId == roleClassNameId %>">
					<%
					Role role = RoleLocalServiceUtil.getRole(classPK);
					%>
					<liferay-ui:message key="role"/> &raquo; <%= role.getName() %>
				</c:when>
				<c:when test="<%= classNameId == userGroupClassNameId %>">
					<%
					UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(classPK);
					%>
					<liferay-ui:message key="user-group"/> &raquo;	<%= userGroup.getName() %>
				</c:when>
				<c:when test="<%= classNameId == communityClassNameId %>">
					<%
					Group group2 = GroupLocalServiceUtil.getGroup(classPK);
					%>
					<liferay-ui:message key="community"/> &raquo; <%= group2.getName() %>
				</c:when>
				<c:when test="<%= classNameId == organizationClassNameId %>">
					<%
					Organization org = OrganizationLocalServiceUtil.getOrganization(classPK);
					%>
					<liferay-ui:message key="organization"/> &raquo; <%= org.getName() %>
				</c:when>
			</c:choose>

			<br /><br />

			<%
			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "no-entries-were-found");

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("title");
			headerNames.add("type");
			headerNames.add("modified-date");
			headerNames.add("display-date");
			headerNames.add("expiration-date");
			headerNames.add(StringPool.BLANK);

			searchContainer.setHeaderNames(headerNames);

			List resultRows = searchContainer.getResultRows();

			int total = AnnouncementEntryLocalServiceUtil.getEntriesCount(classNameId, classPK, alerts);

			List<AnnouncementEntry> results = AnnouncementEntryLocalServiceUtil.getEntries(classNameId, classPK, alerts, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setTotal(total);

			searchContainer.setResults(results);

			int i = 0;

			for(AnnouncementEntry entry : results) {
				entry = entry.toEscapedModel();

				ResultRow row = new ResultRow(entry, entry.getEntryId(), ++i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", strutsAction);
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
