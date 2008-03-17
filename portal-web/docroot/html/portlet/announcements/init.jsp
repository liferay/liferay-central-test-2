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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil"%>
<%@ page import="com.liferay.portal.service.permission.RolePermissionUtil"%>
<%@ page import="com.liferay.portal.service.permission.UserGroupPermissionUtil"%>
<%@ page import="com.liferay.portal.service.persistence.ClassNameUtil"%>
<%@ page import="com.liferay.portlet.announcements.AnnouncementContentException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementDisplayDateException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementExpirationDateException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementTitleException" %>
<%@ page import="com.liferay.portlet.announcements.NoSuchAnnouncementFlagException"%>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementFlag"%>
<%@ page import="com.liferay.portlet.announcements.model.Announcement"%>
<%@ page import="com.liferay.portlet.announcements.model.impl.AnnouncementFlagImpl"%>
<%@ page import="com.liferay.portlet.announcements.model.impl.AnnouncementImpl"%>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementFlagLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.announcements.service.permission.AnnouncementPermission"%>
<%@ page import="com.liferay.portlet.announcements.util.AnnouncementsUtil"%>


<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);

long userClassNameId = PortalUtil.getClassNameId(User.class.getName());
long roleClassNameId = PortalUtil.getClassNameId(Role.class.getName());
long userGroupClassNameId = PortalUtil.getClassNameId(UserGroup.class.getName());
long organizationClassNameId = PortalUtil.getClassNameId(Organization.class.getName());
long communityClassNameId = PortalUtil.getClassNameId(Group.class.getName());
%>
