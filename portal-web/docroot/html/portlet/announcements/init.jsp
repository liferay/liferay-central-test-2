<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.service.permission.OrganizationPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.RolePermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.UserGroupPermissionUtil" %>
<%@ page import="com.liferay.portlet.announcements.EntryContentException" %>
<%@ page import="com.liferay.portlet.announcements.EntryDisplayDateException" %>
<%@ page import="com.liferay.portlet.announcements.EntryExpirationDateException" %>
<%@ page import="com.liferay.portlet.announcements.EntryTitleException" %>
<%@ page import="com.liferay.portlet.announcements.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.announcements.NoSuchFlagException" %>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementsEntry" %>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementsEntryConstants" %>
<%@ page import="com.liferay.portlet.announcements.model.AnnouncementsFlagConstants" %>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.announcements.service.AnnouncementsFlagLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission" %>
<%@ page import="com.liferay.portlet.announcements.util.AnnouncementsUtil" %>

<%
int delta = 3;

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/announcements/init-ext.jsp" %>