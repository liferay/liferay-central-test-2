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
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portlet.blogs.EntryContentException" %>
<%@ page import="com.liferay.portlet.blogs.EntryDisplayDateException" %>
<%@ page import="com.liferay.portlet.blogs.EntryTitleException" %>
<%@ page import="com.liferay.portlet.blogs.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.blogs.model.BlogsEntry" %>
<%@ page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.blogs.service.permission.BlogsEntryPermission" %>
<%@ page import="com.liferay.portlet.blogs.util.BlogsUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>

<%@ page import="com.liferay.util.Html" %>
<%@ page import="com.liferay.util.RSSUtil" %>

<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource, true, true);
}

int abstractLength = GetterUtil.getInteger(PropsUtil.get(PropsUtil.BLOGS_ABSTRACT_LENGTH), 400);

int delta = GetterUtil.getInteger(prefs.getValue("delta", StringPool.BLANK), 5);

String displayStyle = prefs.getValue("display-style", "full-content");

int feedDelta = GetterUtil.getInteger(prefs.getValue("feed-delta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);

String feedDisplayStyle = prefs.getValue("feed-display-style", "full-content");
String feedFormat = prefs.getValue("feed-format", "atom10");

String feedFormatType = RSSUtil.DEFAULT_TYPE;
String feedFormatVersion = String.valueOf(RSSUtil.DEFAULT_VERSION);

if (feedFormat.equals("rss10")) {
	feedFormatType = RSSUtil.RSS;
	feedFormatVersion = "1.0";
}
else if (feedFormat.equals("atom10")) {
	feedFormatType = RSSUtil.ATOM;
	feedFormatVersion = "1.0";
}

DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);
%>