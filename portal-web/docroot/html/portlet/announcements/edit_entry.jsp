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
String redirect = ParamUtil.getString(request, "redirect");

AnnouncementsEntry entry = (AnnouncementsEntry)request.getAttribute(WebKeys.ANNOUNCEMENTS_ENTRY);

long entryId = BeanParamUtil.getLong(entry, request, "entryId");

String type = BeanParamUtil.getString(entry, request, "type");

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

int priority = BeanParamUtil.getInteger(entry, request, "priority");
%>

<script type="text/javascript">
	function <portlet:namespace />saveEntry() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= entry == null ? Constants.ADD : Constants.UPDATE %>";
		submitForm(document.<portlet:namespace />fm);
	}
</script>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/announcements/edit_entry" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm" onSubmit="<portlet:namespace />saveEntry(); return false;">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escape(redirect) %>" />
<input name="<portlet:namespace />entryId" type="hidden" value="<%= entryId %>" />
<input name="<portlet:namespace />alert" type="hidden" value="<%= portletName.equals(PortletKeys.ALERTS) %>" />

<liferay-ui:tabs
	names="entry"
	backURL="<%= redirect %>"
/>

<liferay-ui:error exception="<%= EntryContentException.class %>" message="please-enter-valid-content" />
<liferay-ui:error exception="<%= EntryDisplayDateException.class %>" message="please-enter-a-valid-display-date" />
<liferay-ui:error exception="<%= EntryExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
<liferay-ui:error exception="<%= EntryTitleException.class %>" message="please-enter-a-valid-title" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="distribution-scope" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= entry != null %>">

				<%
				String className = BeanParamUtil.getString(entry, request, "className");
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(className) %>">
						<liferay-ui:message key="general" />
					</c:when>
					<c:when test="<%= className.equals(Group.class.getName()) %>">

						<%
						Group group = GroupLocalServiceUtil.getGroup(entry.getClassPK());
						%>

						<liferay-ui:message key="community" /> &raquo; <%= group.getName() %>
					</c:when>
					<c:when test="<%= className.equals(Organization.class.getName()) %>">

						<%
						Organization organization = OrganizationLocalServiceUtil.getOrganization(entry.getClassPK());
						%>

						<liferay-ui:message key="organization" /> &raquo; <%= organization.getName() %>
					</c:when>
					<c:when test="<%= className.equals(Role.class.getName()) %>">

						<%
						Role role = RoleLocalServiceUtil.getRole(entry.getClassPK());
						%>

						<liferay-ui:message key="role" /> &raquo; <%= role.getName() %>
					</c:when>
					<c:when test="<%= className.equals(User.class.getName()) %>">

						<%
						User user2 = UserLocalServiceUtil.getUserById(entry.getClassPK());
						%>

						<liferay-ui:message key="personal" /> &raquo; <%= user2.getFullName() %>
					</c:when>
					<c:when test="<%= className.equals(UserGroup.class.getName()) %>">

						<%
						UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(entry.getClassPK());
						%>

						<liferay-ui:message key="user-group" /> &raquo;	<%= userGroup.getName() %>
					</c:when>
				</c:choose>
			</c:when>
			<c:otherwise>

				<%
				String distributionScope = ParamUtil.getString(request, "distributionScope");

				long classNameId = -1;
				long classPK = -1;

				String[] distributionScopeArray = StringUtil.split(distributionScope);

				if (distributionScopeArray.length == 2) {
					classNameId = GetterUtil.getLong(distributionScopeArray[0]);
					classPK = GetterUtil.getLong(distributionScopeArray[1]);
				}
				%>

				<select name="<portlet:namespace />distributionScope">
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
		<liferay-ui:message key="type" />
	</td>
	<td>
		<select name="<portlet:namespace />type">

			<%
			for (String curType : AnnouncementsEntryImpl.TYPES) {
			%>

				<option <%= type.equals(curType) ? "selected" : "" %> value="<%= curType %>"><liferay-ui:message key="<%= curType %>" /></option>

			<%
			}
			%>

		</select>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="priority" />
	</td>
	<td>
		<select name="<portlet:namespace />priority">
			<option value="0" <%= (priority == 0) ? "selected" : "" %>><liferay-ui:message key="low" /></option>
			<option value="1" <%= (priority == 1) ? "selected" : "" %>><liferay-ui:message key="medium" /></option>
			<option value="2" <%= (priority == 2) ? "selected" : "" %>><liferay-ui:message key="high" /></option>
			<option value="3" <%= (priority == 3) ? "selected" : "" %>><liferay-ui:message key="highest" /></option>
		</select>
	</td>
</tr>
<tr>
	<td colspan="2">
		<br />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="display-date" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= AnnouncementsEntry.class %>" bean="<%= entry %>" field="displayDate" defaultValue="<%= displayDate %>" />
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="expiration-date" />
	</td>
	<td>
		<liferay-ui:input-field model="<%= AnnouncementsEntry.class %>" bean="<%= entry %>" field="expirationDate" defaultValue="<%= expirationDate %>" />
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="save" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(redirect) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<script type="text/javascript">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />title);
	</script>
</c:if>