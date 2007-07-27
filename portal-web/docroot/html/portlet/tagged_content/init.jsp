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

<%@ page import="com.liferay.portlet.blogs.model.BlogsEntry" %>
<%@ page import="com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.bookmarks.model.BookmarksEntry" %>
<%@ page import="com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.model.DLFileEntry" %>
<%@ page import="com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>
<%@ page import="com.liferay.portlet.imagegallery.model.IGImage" %>
<%@ page import="com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticle" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticleDisplay" %>
<%@ page import="com.liferay.portlet.journal.model.JournalArticleResource" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.journal.service.permission.JournalArticlePermission" %>
<%@ page import="com.liferay.portlet.tags.model.TagsAsset" %>
<%@ page import="com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiNode" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPage" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageResource" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiUtil" %>

<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactory.getPortletSetup(request, portletResource, true, true);
}

String[] entries = prefs.getValues("entries", new String[0]);
String[] notEntries = prefs.getValues("not-entries", new String[0]);
boolean andOperator = GetterUtil.getBoolean(prefs.getValue("and-operator", null), false);
String display = GetterUtil.getString(prefs.getValue("display", "full-content"));
boolean showAvailableLocales = GetterUtil.getBoolean(prefs.getValue("show-available-locales", StringPool.BLANK));

Arrays.sort(entries);
%>