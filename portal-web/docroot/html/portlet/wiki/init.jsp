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

<%@ page import="com.liferay.documentlibrary.service.DLServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portlet.documentlibrary.util.DLUtil" %>
<%@ page import="com.liferay.portlet.tags.NoSuchEntryException" %>
<%@ page import="com.liferay.portlet.tags.NoSuchPropertyException" %>
<%@ page import="com.liferay.portlet.tags.model.TagsAsset" %>
<%@ page import="com.liferay.portlet.tags.model.TagsEntry" %>
<%@ page import="com.liferay.portlet.tags.model.TagsProperty" %>
<%@ page import="com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.tags.service.TagsPropertyLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.DuplicateNodeNameException" %>
<%@ page import="com.liferay.portlet.wiki.DuplicatePageException" %>
<%@ page import="com.liferay.portlet.wiki.NodeNameException" %>
<%@ page import="com.liferay.portlet.wiki.NoSuchNodeException" %>
<%@ page import="com.liferay.portlet.wiki.NoSuchPageException" %>
<%@ page import="com.liferay.portlet.wiki.PageContentException" %>
<%@ page import="com.liferay.portlet.wiki.PageTitleException" %>
<%@ page import="com.liferay.portlet.wiki.PageVersionException" %>
<%@ page import="com.liferay.portlet.wiki.WikiFormatException" %>
<%@ page import="com.liferay.portlet.wiki.importers.WikiImporterKeys" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiNode" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPage" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageDisplay" %>
<%@ page import="com.liferay.portlet.wiki.model.WikiPageResource" %>
<%@ page import="com.liferay.portlet.wiki.model.impl.WikiPageImpl" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.WikiPageResourceLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiNodePermission" %>
<%@ page import="com.liferay.portlet.wiki.service.permission.WikiPagePermission" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiCacheUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.WikiUtil" %>
<%@ page import="com.liferay.portlet.wiki.util.comparator.PageVersionComparator" %>
<%@ page import="com.liferay.util.RSSUtil" %>

<%
PortletPreferences prefs = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	prefs = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

boolean enableComments = GetterUtil.getBoolean(prefs.getValue("enable-comments", null), true);
boolean enableCommentRatings = GetterUtil.getBoolean(prefs.getValue("enable-comment-ratings", null), true);

int rssDelta = GetterUtil.getInteger(prefs.getValue("rss-delta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = prefs.getValue("rss-display-style", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);

StringMaker rssURLParams = new StringMaker();

if ((rssDelta != SearchContainer.DEFAULT_DELTA) || !rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
	if (rssDelta != SearchContainer.DEFAULT_DELTA) {
		rssURLParams.append("&max=");
		rssURLParams.append(rssDelta);
	}

	if (!rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		rssURLParams.append("&displayStyle=");
		rssURLParams.append(rssDisplayStyle);
	}
}

StringMaker rssURLAtomParams = new StringMaker(rssURLParams.toString());

rssURLAtomParams.append("&type=");
rssURLAtomParams.append(RSSUtil.ATOM);
rssURLAtomParams.append("&version=1.0");

StringMaker rssURLRSS10Params = new StringMaker(rssURLParams.toString());

rssURLRSS10Params.append("&type=");
rssURLRSS10Params.append(RSSUtil.RSS);
rssURLRSS10Params.append("&version=1.0");

StringMaker rssURLRSS20Params = new StringMaker(rssURLParams.toString());

rssURLRSS20Params.append("&type=");
rssURLRSS20Params.append(RSSUtil.RSS);
rssURLRSS20Params.append("&version=2.0");

DateFormat dateFormatDateTime = DateFormats.getDateTime(locale, timeZone);

String allNodes = ListUtil.toString(WikiNodeLocalServiceUtil.getNodes(portletGroupId.longValue()), "name");

String[] visibleNodes = StringUtil.split(PrefsParamUtil.getString(prefs, request, "visible-nodes", allNodes));
String[] hiddenNodes = StringUtil.split(PrefsParamUtil.getString(prefs, request, "hidden-nodes", StringPool.BLANK));
%>