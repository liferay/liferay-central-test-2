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
AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(WebKeys.ANNOUNCEMENTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

long classNameId = BeanParamUtil.getLong(entry, request, "classNameId");

long classPK = BeanParamUtil.getLong(entry, request, "classPK");

String type = BeanParamUtil.getString(entry, request, "type", AnnouncementsEntryImpl.TYPES[0]);

int priority = BeanParamUtil.getInteger(entry, request, "priority", 1);

String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL", redirect);

boolean alerts = false;
String strutsPath = "/announcements/edit_entry";

if (portletName.equals(PortletKeys.ALERTS)) {
	alerts =  true;
	strutsPath = "/alerts/edit_entry";
}

Calendar displayDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

if (entry != null) {
	if (entry.getDisplayDate() != null) {
		displayDate.setTime(entry.getDisplayDate());
	}
}

Calendar expirationDate = CalendarFactoryUtil.getCalendar(timeZone, locale);

expirationDate.add(Calendar.MONTH, 1);

if (entry != null) {
	if (entry.getExpirationDate() != null) {
		expirationDate.setTime(entry.getExpirationDate());
	}
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", strutsPath);
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("entryId", String.valueOf(entryId));
%>

<script type="text/javascript">
	function <portlet:namespace />saveEntry() {
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="<%= strutsPath %>" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= (entry == null ? Constants.ADD : Constants.UPDATE) %>">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= redirect %>" />
<input name="<portlet:namespace />entryId" type="hidden" value="<%= entryId %>" />
<input name="<portlet:namespace />alert" type="hidden" value="<%= alerts %>" />

<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
<liferay-ui:error exception="<%= EntryDisplayDateException.class %>" message="please-enter-valid-display-date" />
<liferay-ui:error exception="<%= EntryExpirationDateException.class %>" message="please-enter-valid-expiration-date" />
<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-valid-title" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="title" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= AnnouncementsEntry.class %>" bean="<%= entry %>" field="title" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="url" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= AnnouncementsEntry.class %>" bean="<%= entry %>" field="url" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="content" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= AnnouncementsEntry.class %>" bean="<%= entry %>" field="content" />
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="distribution-scope" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= entry != null %>">
				<c:choose>
					<c:when test="<%= entry.getClassNameId() == 0 %>">
						<liferay-ui:message key="general" />
					</c:when>
<%--
					<c:when test="<%= entry.getClassNameId() == userClassNameId %>">
						<%
						User user2 = UserLocalServiceUtil.getUserById(entry.getClassPK());
						%>
						<liferay-ui:message key="personal" /> &raquo; <%= user2.getFullName() %>
					</c:when>
					<c:when test="<%= entry.getClassNameId() == roleClassNameId %>">
						<%
						Role role = RoleLocalServiceUtil.getRole(entry.getClassPK());
						%>
						<liferay-ui:message key="role" /> &raquo; <%= role.getName() %>
					</c:when>
					<c:when test="<%= entry.getClassNameId() == userGroupClassNameId %>">
						<%
						UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(entry.getClassPK());
						%>
						<liferay-ui:message key="user-group" /> &raquo;	<%= userGroup.getName() %>
					</c:when>
					<c:when test="<%= entry.getClassNameId() == communityClassNameId %>">
						<%
						Group group2 = GroupLocalServiceUtil.getGroup(entry.getClassPK());
						%>
						<liferay-ui:message key="community" /> &raquo; <%= group2.getName() %>
					</c:when>
					<c:when test="<%= entry.getClassNameId() == organizationClassNameId %>">
						<%
						Organization org = OrganizationLocalServiceUtil.getOrganization(entry.getClassPK());
						%>
						<liferay-ui:message key="organization" /> &raquo; <%= org.getName() %>
					</c:when>
--%>
				</c:choose>
			</c:when>
			<c:otherwise>
				<select name="<portlet:namespace />distributionScope">
					<c:if test="<%= permissionChecker.isOmniadmin() %>">
						<option value="0,0" ><liferay-ui:message key="general" /></option>
					</c:if>

<%--
					<optgroup label='<liferay-ui:message key="roles" />'>
						<%
						List<Role> roles = RoleLocalServiceUtil.getRoles(themeDisplay.getCompanyId());

						for (Role role : roles) {
							if (RolePermissionUtil.contains(permissionChecker, role.getRoleId(), ActionKeys.ASSIGN_MEMBERS)) {
							%>
								<option value="<%= roleClassNameId + StringPool.COMMA + role.getRoleId() %>" ><%= role.getName() %></option>
							<%
							}
						}
						%>
					</optgroup>

					<optgroup label='<liferay-ui:message key="user-groups" />'>
						<%
						List<UserGroup> userGroups = UserGroupLocalServiceUtil.getUserGroups(themeDisplay.getCompanyId());

						for (UserGroup userGroup : userGroups) {
							if (UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
							%>
								<option value="<%= userGroupClassNameId + StringPool.COMMA + userGroup.getUserGroupId() %>" ><%= userGroup.getName() %></option>
							<%
							}
						}
						%>
					</optgroup>

					<optgroup label='<liferay-ui:message key="communities" />'>
						<%
						List<Group> groups = GroupLocalServiceUtil.getUserGroups(user.getUserId());

						for (Group group2 : groups) {
							if (group2.isCommunity() && GroupPermissionUtil.contains(permissionChecker, group2.getGroupId(), ActionKeys.ASSIGN_MEMBERS)) {
							%>
								<option value="<%= communityClassNameId + StringPool.COMMA + group2.getGroupId() %>" ><%= group2.getName() %></option>
							<%
							}
						}
						%>
					</optgroup>

					<optgroup label='<liferay-ui:message key="organizations" />'>
						<%
						List<Organization> organizations = OrganizationLocalServiceUtil.getUserOrganizations(user.getUserId());

						for (Organization organization : organizations) {
							if (OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.ASSIGN_MEMBERS)) {
							%>
								<option value="<%= organizationClassNameId + StringPool.COMMA + organization.getOrganizationId() %>" ><%= organization.getName() %></option>
							<%
							}
						}
						%>
					</optgroup>
--%>
				</select>
			</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="priority" />
	</td>
	<td>
		<select name="<portlet:namespace />priority">
			<option value="0" <%= (priority == 0 ? "selected=\"selected\"" : "") %>><liferay-ui:message key="low" /></option>
			<option value="1" <%= (priority == 1 ? "selected=\"selected\"" : "") %>><liferay-ui:message key="medium" /></option>
			<option value="2" <%= (priority == 2 ? "selected=\"selected\"" : "") %>><liferay-ui:message key="high" /></option>
			<option value="3" <%= (priority == 3 ? "selected=\"selected\"" : "") %>><liferay-ui:message key="highest" /></option>
		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">
			<%
				for (int i = 0; i < AnnouncementsEntryImpl.TYPES.length; i++) {
			%>

			<option <%= type.equals(AnnouncementsEntryImpl.TYPES[i]) ? "selected" : "" %> value="<%= AnnouncementsEntryImpl.TYPES[i] %>"><%= LanguageUtil.get(pageContext, AnnouncementsEntryImpl.TYPES[i]) %></option>

			<%
				}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-date" />
	</td>
	<td>
		<liferay-ui:input-date
			formName="fm"
			monthParam="displayMonth"
			monthValue="<%= displayDate.get(Calendar.MONTH) %>"
			dayParam="displayDay"
			dayValue="<%= displayDate.get(Calendar.DATE) %>"
			yearParam="displayYear"
			yearValue="<%= displayDate.get(Calendar.YEAR) %>"
			yearRangeStart="<%= displayDate.get(Calendar.YEAR) %>"
			yearRangeEnd="<%= displayDate.get(Calendar.YEAR) + 100 %>"
		/>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="expiration-date" />
	</td>
	<td>
		<liferay-ui:input-date
			formName="fm"
			monthParam="expirationMonth"
			monthValue="<%= expirationDate.get(Calendar.MONTH) %>"
			dayParam="expirationDay"
			dayValue="<%= expirationDate.get(Calendar.DATE) %>"
			yearParam="expirationYear"
			yearValue="<%= expirationDate.get(Calendar.YEAR) %>"
			yearRangeStart="<%= expirationDate.get(Calendar.YEAR) %>"
			yearRangeEnd="<%= expirationDate.get(Calendar.YEAR) + 100 %>"
		/>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="self.location = '<%= redirect %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />content);
	</script>
</c:if>