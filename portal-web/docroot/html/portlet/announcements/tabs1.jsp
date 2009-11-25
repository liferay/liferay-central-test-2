<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
String tabs1 = ParamUtil.getString(request, "tabs1", "entries");

PortletURL tabs1URL = renderResponse.createRenderURL();

tabs1URL.setWindowState(WindowState.MAXIMIZED);

tabs1URL.setParameter("struts_action", "/announcements/view");
tabs1URL.setParameter("tabs1", tabs1);

String tabs1Names = "entries";

Group group = themeDisplay.getScopeGroup();

Organization organization = null;

if (group.isOrganization()) {
	organization = OrganizationLocalServiceUtil.getOrganization(group.getClassPK());
}

if (PortletPermissionUtil.contains(permissionChecker, plid, PortletKeys.ANNOUNCEMENTS, ActionKeys.ADD_ENTRY)) {
	tabs1Names += ",manage-entries";
}
else if ((group.isCommunity()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.MANAGE_ANNOUNCEMENTS))) {
	tabs1Names += ",manage-entries";
}
else if ((group.isOrganization()) && (organization != null)) {
	if (OrganizationPermissionUtil.contains(permissionChecker, organization.getOrganizationId(), ActionKeys.MANAGE_ANNOUNCEMENTS)) {
		tabs1Names += ",manage-entries";
	}
}
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	url="<%= tabs1URL.toString() %>"
/>