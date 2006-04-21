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

<%@ include file="/html/portlet/enterprise_admin/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Organization organization = (Organization)row.getObject();
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editOrganizationURL">
	<portlet:param name="struts_action" value='<%= "/enterprise_admin/edit_" + (organization.isRoot() ? "organization" : "location") %>' />
	<portlet:param name="organizationId" value="<%= organization.getOrganizationId() %>" />
</portlet:renderURL>

<%
String addUserURLString = null;
%>

<c:choose>
	<c:when test="<%= organization.isRoot() %>">
		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="organizationId" value="<%= organization.getOrganizationId() %>" />
			<portlet:param name="organizationName" value="<%= organization.getName() %>" />
		</portlet:renderURL>

		<%
		addUserURLString = addUserURL;
		%>

	</c:when>
	<c:otherwise>

		<%
		Organization parentOrganizaton = OrganizationLocalServiceUtil.getOrganization(organization.getParentOrganizationId());
		%>

		<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addUserURL">
			<portlet:param name="struts_action" value="/enterprise_admin/edit_user" />
			<portlet:param name="organizationId" value="<%= parentOrganizaton.getOrganizationId() %>" />
			<portlet:param name="organizationName" value="<%= parentOrganizaton.getName() %>" />
			<portlet:param name="locationId" value="<%= organization.getOrganizationId() %>" />
			<portlet:param name="locationName" value="<%= organization.getName() %>" />
		</portlet:renderURL>

		<%
		addUserURLString = addUserURL;
		%>

	</c:otherwise>
</c:choose>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewUsersURL">
	<portlet:param name="struts_action" value="/enterprise_admin/view" />
	<portlet:param name="tabs1" value="users" />
	<portlet:param name="organizationId" value="<%= organization.getOrganizationId() %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addLocationURL">
	<portlet:param name="struts_action" value="/enterprise_admin/edit_location" />
	<portlet:param name="parentOrganizationId" value="<%= organization.getOrganizationId() %>" />
	<portlet:param name="parentOrganizationName" value="<%= organization.getName() %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="viewLocationsURL">
	<portlet:param name="struts_action" value="/enterprise_admin/view" />
	<portlet:param name="tabs1" value="locations" />
	<portlet:param name="parentOrganizationId" value="<%= organization.getOrganizationId() %>" />
</portlet:renderURL>

<c:choose>
	<c:when test="<%= portletName.equals(PortletKeys.LOCATION_ADMIN) %>">
		<c:if test="<%= !organization.isRoot() %>">
			<liferay-ui:icon image="edit" url="<%= editOrganizationURL %>" />
		</c:if>

		<liferay-ui:icon image="add_user" message="add-user" url="<%= addUserURLString %>" />

		<liferay-ui:icon image="view_users" message="view-users" url="<%= viewUsersURL %>" />
	</c:when>
	<c:otherwise>
		<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
			<liferay-ui:icon image="edit" url="<%= editOrganizationURL %>" />

			<liferay-ui:icon image="add_user" message="add-user" url="<%= addUserURLString %>" />
		</c:if>

		<liferay-ui:icon image="view_users" message="view-users" url="<%= viewUsersURL %>" />

		<c:if test="<%= portletName.equals(PortletKeys.ENTERPRISE_ADMIN) || portletName.equals(PortletKeys.ORGANIZATION_ADMIN) %>">
			<c:if test="<%= organization.isRoot() %>">
				<liferay-ui:icon image="add_location" message="add-location" url="<%= addLocationURL %>" />
			</c:if>
		</c:if>

		<c:if test="<%= organization.isRoot() %>">
			<liferay-ui:icon image="view_locations" message="view-locations" url="<%= viewLocationsURL %>" />
		</c:if>
	</c:otherwise>
</c:choose>