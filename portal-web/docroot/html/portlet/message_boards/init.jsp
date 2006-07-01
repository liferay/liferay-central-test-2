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
%><%--

--%><%@ include file="/html/portlet/init.jsp" %><%--

--%><%@ page import="com.liferay.documentlibrary.FileNameException" %><%--
--%><%@ page import="com.liferay.documentlibrary.FileSizeException" %><%--
--%><%@ page import="com.liferay.documentlibrary.NoSuchDirectoryException" %><%--
--%><%@ page import="com.liferay.documentlibrary.service.spring.DLServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.CategoryNameException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.MessageBodyException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.MessageSubjectException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.NoSuchCategoryException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.NoSuchMessageException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.NoSuchStatsUserException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.NoSuchThreadException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.NoSuchTopicException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.RequiredMessageException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.TopicNameException" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBCategory" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBMessage" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBMessageDisplay" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBStatsUser" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBThread" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.model.MBTopic" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.permission.MBCategoryPermission" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.permission.MBTopicPermission" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBMessageFlagLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBStatsUserLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBThreadLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.service.spring.MBTopicLocalServiceUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.util.BBCodeUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.util.MBUtil" %><%--
--%><%@ page import="com.liferay.portlet.messageboards.util.TreeWalker" %><%--

--%><%@ page import="org.apache.lucene.document.Document" %><%--

--%><%
PortletPreferences portletSetup = PortletPreferencesFactory.getPortletSetup(request, portletDisplay.getId(), false, true);

DateFormat dateFormatDate = DateFormats.getDate(locale, timeZone);
DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<style type="text/css">
.message-board-code {
	background: white;
	border: 1px solid <%= colorScheme.getPortletFontDim() %>;
	font-family: monospace;
}

.message-board-lines {
	border-right: 1px solid <%= colorScheme.getPortletFontDim() %>;
	color: <%= colorScheme.getPortletFontDim() %>;
	margin-right: 5px;
	padding: 0px 5px 0px 5px;
}

.message-board-quote {
	background: white url(<%= themeDisplay.getPathThemeImage() %>/message_boards/quoteleft.gif) left top no-repeat;
	border: 1px solid <%= colorScheme.getPortletFontDim() %>;
	padding: 5px 0px 0px 5px;
}

.message-board-quote-content {
	background: transparent url(<%= themeDisplay.getPathThemeImage() %>/message_boards/quoteright.gif) right bottom no-repeat;
	padding: 5px 30px 10px 30px;
}

.message-board-quote-title {
	font-weight: bold;
	padding: 5px 0px 5px 0px;
}
</style>