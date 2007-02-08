<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException" %>
<%@ page import="com.liferay.portlet.softwarecatalog.NoSuchLicenseException" %>
<%@ page import="com.liferay.portlet.softwarecatalog.NoSuchProductEntryException" %>
<%@ page import="com.liferay.portlet.softwarecatalog.NoSuchProductVersionException" %>
<%@ page import="com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion" %>
<%@ page import="com.liferay.portlet.softwarecatalog.model.SCLicense" %>
<%@ page import="com.liferay.portlet.softwarecatalog.model.SCProductEntry" %>
<%@ page import="com.liferay.portlet.softwarecatalog.model.SCProductVersion" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.SCFrameworkVersionLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.SCLicenseLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.SCProductEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.SCProductVersionLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.permission.SCFrameworkVersionPermission" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission" %>
<%@ page import="com.liferay.portlet.softwarecatalog.service.permission.SCProductVersionPermission" %>

<%
DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);
%>