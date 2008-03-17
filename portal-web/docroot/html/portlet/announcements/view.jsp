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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY ND, EXPRESS OR
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
String tabs1 = ParamUtil.getString(request, "tabs1", "announcements");

String distributionScope = ParamUtil.getString(request, "distributionScope");

boolean readHidden = tabs1.equals("old-announcements") ? true : false;

String redirect = currentURL;

long userId = user.getUserId();

boolean hasAddAnnouncement = PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ANNOUNCEMENT);

boolean alerts = false;

String strutsAction = "/announcements/edit_announcement";

String tab1Names = "announcements";

if (!user.isDefaultUser()) {
	tab1Names += ",old-announcements";
}

if (hasAddAnnouncement) {
	tab1Names += ",manage-announcements";
}

if (portletName.equals(PortletKeys.ALERTS)) {
	hasAddAnnouncement = PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ALERTS, ActionKeys.ADD_ANNOUNCEMENT);
	alerts = true;
	strutsAction = "/alerts/edit_announcement";

	if (tabs1.equals("announcements")) {
		tabs1 = "alerts";
	}

	tab1Names = "alerts,manage-alerts";
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(LiferayWindowState.MAXIMIZED);
portletURL.setParameter("struts_action", alerts ? "/alerts/view" : "/announcements/view");
portletURL.setParameter("tabs1", tabs1);
%>

<script src="<%= themeDisplay.getPathJavaScript() %>/liferay/service_unpacked.js" type="text/javascript"></script>

<script type="text/javascript">
	function <portlet:namespace />hideAnnouncement(announcementId) {
		Liferay.Service.Announcements.AnnouncementFlag.addAnnouncementFlag({userId: <%= userId %>, announcementId : announcementId, flag: 2});

		jQuery('#<portlet:namespace/>' + announcementId).hide("slow");
	}

	function <portlet:namespace />markAnnouncementAsRead(announcementId) {
		Liferay.Service.Announcements.AnnouncementFlag.addAnnouncementFlag({userId: <%= userId %>, announcementId : announcementId, flag: 1});
	}

	function <portlet:namespace />selectDistributionScope(scope) {
		var url = "<%= portletURL.toString() %>&<portlet:namespace />distributionScope=" + scope;
		submitForm(document.<portlet:namespace />fm, url);
	}
</script>

<c:if test="<%= !user.isDefaultUser() %>">
	<liferay-ui:tabs
		names="<%= tab1Names %>"
		param="tabs1"
		url="<%= portletURL.toString() %>"
	/>
</c:if>

<c:choose>
	<c:when test='<%= tabs1.equals("announcements") || tabs1.equals("old-announcements") || tabs1.equals("alerts") %>'>
		<div class="<%= (alerts ? "lfr-alerts" : "lfr-announcements") %>">
			<%
			LinkedHashMap<Long,Long[]> scopes = AnnouncementsUtil.getAnnouncementScopes(userId);

			int flag = AnnouncementFlagImpl.NOT_HIDDEN;

			if (readHidden) {
				flag = AnnouncementFlagImpl.HIDDEN;
			}

			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "no-announcements-were-found");

			int total = AnnouncementLocalServiceUtil.getAnnouncementsCount(userId, scopes, flag, alerts);

			List<Announcement> results = AnnouncementLocalServiceUtil.getAnnouncements(userId, scopes, flag, alerts, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setTotal(total);

			searchContainer.setResults(results);

			for(Announcement announcement : results) {
				AnnouncementFlag readFlag = null;
				String isRead = Boolean.FALSE.toString();

				try {
					readFlag = AnnouncementFlagLocalServiceUtil.getAnnouncementFlag(userId, announcement.getAnnouncementId(), AnnouncementFlagImpl.READ);
				}
				catch (NoSuchAnnouncementFlagException nsafe) {
				}

				if (readFlag != null) {
					isRead = Boolean.TRUE.toString();
				}
			%>
				<div class="announcement read-<%= isRead %>" id="<portlet:namespace/><%= announcement.getAnnouncementId() %>">

					<div class="edit-actions">
						<table class="lfr-table">
						<tr>
							<c:if test="<%= AnnouncementPermission.contains(permissionChecker, announcement, ActionKeys.UPDATE) %>">
								<td>
									<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editAnnouncementURL">
										<portlet:param name="struts_action" value="<%= strutsAction %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="announcementId" value="<%= String.valueOf(announcement.getAnnouncementId()) %>" />
									</portlet:renderURL>

									<liferay-ui:icon image="edit" url="<%= editAnnouncementURL %>" label="<%= true %>" />
								</td>
							</c:if>
							<c:if test="<%= AnnouncementPermission.contains(permissionChecker, announcement, ActionKeys.DELETE) %>">
								<td>
									<portlet:actionURL var="deleteAnnouncementURL">
										<portlet:param name="struts_action" value="<%= strutsAction %>" />
										<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="announcementId" value="<%= String.valueOf(announcement.getAnnouncementId()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon-delete url="<%= deleteAnnouncementURL %>" label="<%= true %>" />
								</td>
							</c:if>
							<td>
								<c:if test="<%= !readHidden && !user.isDefaultUser() %>">
									<liferay-ui:icon image="close" message="hide" url='<%= "javascript: " + renderResponse.getNamespace() + "hideAnnouncement(" + announcement.getAnnouncementId() + ");" %>' />
								</c:if>
							</td>
						</tr>
						</table>
					</div>

					<span class="announcement-title">
						<c:choose>
							<c:when test="<%= Validator.isNotNull(announcement.getUrl()) %>">
								<a class="announcement-url" href="<%= announcement.getUrl() %>"><%= announcement.getTitle() %></a>
							</c:when>
							<c:when test="<%= Validator.isNull(announcement.getUrl()) %>">
								<%= announcement.getTitle() %>
							</c:when>
						</c:choose>
					</span>

					<span class="announcement-content announcement-type-<%= announcement.getType() %>"><%= announcement.getContent() %></span>

					<c:choose>
						<c:when test="<%= announcement.getClassNameId() == 0 %>">
							<span class="announcement-scope general">
								<liferay-ui:message key="general"/>
							</span>
						</c:when>
						<c:when test="<%= announcement.getClassNameId() == userClassNameId %>">
							<span class="announcement-scope personal">
								<liferay-ui:message key="personal"/>
							</span>
						</c:when>
						<c:when test="<%= announcement.getClassNameId() == roleClassNameId %>">
							<span class="announcement-scope role">
								<%
								Role role = RoleLocalServiceUtil.getRole(announcement.getClassPK());
								%>
								<liferay-ui:message key="role"/>: <%= role.getName() %>
							</span>
						</c:when>
						<c:when test="<%= announcement.getClassNameId() == userGroupClassNameId %>">
							<span class="announcement-scope user-group">
								<%
								UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(announcement.getClassPK());
								%>
								<liferay-ui:message key="user-group"/>: <%= userGroup.getName() %>
							</span>
						</c:when>
						<c:when test="<%= announcement.getClassNameId() == communityClassNameId %>">
							<span class="announcement-scope community">
								<%
								Group group2 = GroupLocalServiceUtil.getGroup(announcement.getClassPK());
								%>
								<liferay-ui:message key="community"/>: <%= group2.getName() %>
							</span>
						</c:when>
						<c:when test="<%= announcement.getClassNameId() == organizationClassNameId %>">
							<span class="announcement-scope organization">
								<%
								Organization org = OrganizationLocalServiceUtil.getOrganization(announcement.getClassPK());
								%>
								<liferay-ui:message key="organization"/>: <%= org.getName() %>
							</span>
						</c:when>
					</c:choose>

					<c:if test="<%= !user.isDefaultUser() %>">
						<script type="text/javascript">
							<portlet:namespace />markAnnouncementAsRead(<%= announcement.getAnnouncementId() %>);
						</script>
					</c:if>
				</div>
			<%
			}
			%>

			<c:if test="<%= (results.size() <= 0) %>">
				<liferay-ui:message key="no-announcements-were-found" />
			</c:if>

			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" type="article" />

			<c:if test="<%= alerts && (results.size() <= 0) && !PortletPermissionUtil.contains(permissionChecker, plid.longValue(), PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ANNOUNCEMENT) %>">
				<style type="text/css">
					.portlet-alerts {
						display: none;
					}
				</style>
			</c:if>
		</div>
	</c:when>
	<c:when test='<%= tabs1.equals("manage-announcements") || tabs1.equals("manage-alerts") %>'>
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
				List<Group> groups = GroupLocalServiceUtil.getUserGroups(user.getUserId());

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
				List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(user.getUserId());

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

		<input type="button" value='<liferay-ui:message key="<%= (alerts ? "add-alert" : "add-announcement") %>"/>' onClick="self.location = '<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="<%= strutsAction %>" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>'" />

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
			SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, "no-announcements-were-found");

			List<String> headerNames = new ArrayList<String>();

			headerNames.add("title");
			headerNames.add("type");
			headerNames.add("modified-date");
			headerNames.add("display-date");
			headerNames.add("expiration-date");
			headerNames.add(StringPool.BLANK);

			searchContainer.setHeaderNames(headerNames);

			List resultRows = searchContainer.getResultRows();

			int total = AnnouncementLocalServiceUtil.getAnnouncementsCount(classNameId, classPK, alerts);

			List<Announcement> results = AnnouncementLocalServiceUtil.getAnnouncements(classNameId, classPK, alerts, searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setTotal(total);

			searchContainer.setResults(results);

			int i = 0;

			for(Announcement announcement : results) {
				announcement = announcement.toEscapedModel();

				ResultRow row = new ResultRow(announcement, announcement.getAnnouncementId(), ++i);

				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setWindowState(WindowState.MAXIMIZED);

				rowURL.setParameter("struts_action", strutsAction);
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("announcementId", String.valueOf(announcement.getAnnouncementId()));

				// Title

				row.addText(announcement.getTitle(), rowURL);

				// Type

				row.addText(LanguageUtil.get(pageContext, announcement.getType()), rowURL);

				// Modified date

				row.addText(dateFormatDate.format(announcement.getModifiedDate()), rowURL);

				// Display date

				row.addText(dateFormatDate.format(announcement.getDisplayDate()), rowURL);

				// Expiration date

				row.addText(dateFormatDate.format(announcement.getExpirationDate()), rowURL);

				// Action

				row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/announcements/announcement_action.jsp");

				// Add result row

				resultRows.add(row);
			}
			%>

			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
		</form>
	</c:when>
</c:choose>
