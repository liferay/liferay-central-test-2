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

<%--<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil"%>
<%@ page import="com.liferay.portal.service.permission.RolePermissionUtil"%>
<%@ page import="com.liferay.portal.service.permission.UserGroupPermissionUtil"%>
<%@ page import="com.liferay.portal.service.persistence.ClassNameUtil"%>
<%@ page import="com.liferay.portlet.announcements.AnnouncementEntryContentException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementEntryDisplayDateException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementEntryExpirationDateException" %>
<%@ page import="com.liferay.portlet.announcements.AnnouncementEntryTitleException" %>
<%@ page import="com.liferay.portlet.announcements.NoSuchAnnouncementFlagException"%>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementEntry"%>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementFlag"%>
<%@ page import="com.liferay.portlet.announcements.model.impl.AnnouncementEntryImpl"%>
<%@ page import="com.liferay.portlet.announcements.model.impl.AnnouncementFlagImpl"%>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementEntryLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementFlagLocalServiceUtil"%>
<%@ page import="com.liferay.portlet.announcements.service.permission.AnnouncementEntryPermission"%>
<%@ page import="com.liferay.portlet.announcements.util.AnnouncementsUtil"%>--%>

<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);
%>