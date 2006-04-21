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

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"

%><%@ taglib uri="http://liferay.com/tld/theme" prefix="theme"

%><%@ taglib uri="http://liferay.com/tld/util" prefix="liferay"

%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"
%><%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"

%><%@ page import="com.liferay.portal.language.LanguageUtil"
%><%@ page import="com.liferay.portal.model.Group"
%><%@ page import="com.liferay.portal.model.Layout"
%><%@ page import="com.liferay.portal.service.spring.GroupLocalServiceUtil"
%><%@ page import="com.liferay.portal.service.spring.RoleLocalServiceUtil"
%><%@ page import="com.liferay.portal.service.spring.UserLocalServiceUtil"
%><%@ page import="com.liferay.portal.theme.PortletDisplay"
%><%@ page import="com.liferay.portal.util.Constants"
%><%@ page import="com.liferay.portal.util.PortalUtil"
%><%@ page import="com.liferay.portal.util.PropsUtil"
%><%@ page import="com.liferay.portal.util.SessionClicks"
%><%@ page import="com.liferay.portlet.LiferayWindowState"
%><%@ page import="com.liferay.portlet.PortletURLImpl"
%><%@ page import="com.liferay.util.BrowserSniffer"
%><%@ page import="com.liferay.util.GetterUtil"
%><%@ page import="com.liferay.util.ParamUtil"
%><%@ page import="com.liferay.util.StringPool"
%><%@ page import="com.liferay.util.StringUtil"
%><%@ page import="com.liferay.util.Validator"

%><%@ page import="java.util.Arrays"
%><%@ page import="java.util.Collections"
%><%@ page import="java.util.List"

%><%@ page import="javax.portlet.PortletMode"
%><%@ page import="javax.portlet.WindowState" %>

<theme:defineObjects />

<%
int TAB_IMAGE_HEIGHT = 30;
int TAB_IMAGE_SIDE_WIDTH = 23;
%>
