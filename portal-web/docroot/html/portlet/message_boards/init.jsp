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

<%@ page import="com.liferay.documentlibrary.FileNameException" %>
<%@ page import="com.liferay.documentlibrary.FileSizeException" %>
<%@ page import="com.liferay.documentlibrary.service.DLServiceUtil" %>
<%@ page import="com.liferay.portal.kernel.search.Document" %>
<%@ page import="com.liferay.portal.kernel.search.Field" %>
<%@ page import="com.liferay.portal.kernel.search.Hits" %>
<%@ page import="com.liferay.portal.kernel.search.Indexer" %>
<%@ page import="com.liferay.portal.kernel.search.IndexerRegistryUtil" %>
<%@ page import="com.liferay.portal.kernel.search.SearchContext" %>
<%@ page import="com.liferay.portal.search.SearchContextFactory" %>
<%@ page import="com.liferay.portlet.asset.model.AssetTag" %>
<%@ page import="com.liferay.portlet.asset.service.AssetTagLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.asset.util.AssetUtil" %>
<%@ page import="com.liferay.portlet.messageboards.BannedUserException" %>
<%@ page import="com.liferay.portlet.messageboards.CategoryNameException" %>
<%@ page import="com.liferay.portlet.messageboards.LockedThreadException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListEmailAddressException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListInServerNameException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListInUserNameException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListOutEmailAddressException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListOutServerNameException" %>
<%@ page import="com.liferay.portlet.messageboards.MailingListOutUserNameException" %>
<%@ page import="com.liferay.portlet.messageboards.MessageBodyException" %>
<%@ page import="com.liferay.portlet.messageboards.MessageSubjectException" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchCategoryException" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchMailingListException" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchMessageException" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchStatsUserException" %>
<%@ page import="com.liferay.portlet.messageboards.NoSuchThreadException" %>
<%@ page import="com.liferay.portlet.messageboards.RequiredMessageException" %>
<%@ page import="com.liferay.portlet.messageboards.SplitThreadException" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBBan" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBCategory" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBCategoryDisplay" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBCategoryConstants" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMailingList" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessage" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessageConstants" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessageDisplay" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBMessageFlag" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBStatsUser" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThread" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBThreadConstants" %>
<%@ page import="com.liferay.portlet.messageboards.model.MBTreeWalker" %>
<%@ page import="com.liferay.portlet.messageboards.model.impl.MBCategoryDisplayImpl" %>
<%@ page import="com.liferay.portlet.messageboards.model.impl.MBMessageImpl" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMailingListLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageFlagLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil" %>
<%@ page import="com.liferay.portlet.messageboards.service.permission.MBCategoryPermission" %>
<%@ page import="com.liferay.portlet.messageboards.service.permission.MBMessagePermission" %>
<%@ page import="com.liferay.portlet.messageboards.service.permission.MBPermission" %>
<%@ page import="com.liferay.portlet.messageboards.util.BBCodeUtil" %>
<%@ page import="com.liferay.portlet.messageboards.util.MBUtil" %>
<%@ page import="com.liferay.portlet.messageboards.util.ThreadHits" %>
<%@ page import="com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator" %>
<%@ page import="com.liferay.util.RSSUtil" %>

<%
PortletPreferences preferences = renderRequest.getPreferences();

String portletResource = ParamUtil.getString(request, "portletResource");

if (Validator.isNotNull(portletResource)) {
	preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
}

String currentLanguageId = LanguageUtil.getLanguageId(request);
Locale currentLocale = LocaleUtil.fromLanguageId(currentLanguageId);
Locale defaultLocale = LocaleUtil.getDefault();
String defaultLanguageId = LocaleUtil.toLanguageId(defaultLocale);

Locale[] locales = LanguageUtil.getAvailableLocales();

String[] priorities = LocalizationUtil.getPreferencesValues(preferences, "priorities", currentLanguageId);

int rssDelta = GetterUtil.getInteger(preferences.getValue("rss-delta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = preferences.getValue("rss-display-style", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
String rssFormat = preferences.getValue("rss-format", "atom10");
boolean allowAnonymousPosting = MBUtil.isAllowAnonymousPosting(preferences);
boolean enableFlags = GetterUtil.getBoolean(preferences.getValue("enable-flags", null), true);
boolean enableRatings = GetterUtil.getBoolean(preferences.getValue("enable-message-ratings", null), true);

String rssFormatType = RSSUtil.DEFAULT_TYPE;
double rssFormatVersion = RSSUtil.DEFAULT_VERSION;

if (rssFormat.equals("rss10")) {
	rssFormatType = RSSUtil.RSS;
	rssFormatVersion = 1.0;
}
else if (rssFormat.equals("rss20")) {
	rssFormatType = RSSUtil.RSS;
	rssFormatVersion = 2.0;
}
else if (rssFormat.equals("atom10")) {
	rssFormatType = RSSUtil.ATOM;
	rssFormatVersion = 1.0;
}

StringBuilder rssURLParams = new StringBuilder();

if ((rssDelta != SearchContainer.DEFAULT_DELTA) || !rssFormatType.equals(RSSUtil.DEFAULT_TYPE) || (rssFormatVersion != RSSUtil.DEFAULT_VERSION) || !rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
	if (rssDelta != SearchContainer.DEFAULT_DELTA) {
		rssURLParams.append("&max=");
		rssURLParams.append(rssDelta);
	}

	if (!rssFormatType.equals(RSSUtil.DEFAULT_TYPE)) {
		rssURLParams.append("&type=");
		rssURLParams.append(rssFormatType);
	}

	if (rssFormatVersion != RSSUtil.DEFAULT_VERSION) {
		rssURLParams.append("&version=");
		rssURLParams.append(rssFormatVersion);
	}

	if (!rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT)) {
		rssURLParams.append("&displayStyle=");
		rssURLParams.append(rssDisplayStyle);
	}
}

boolean childrenMessagesTaggable = true;
boolean includeFormTag = true;
boolean showSearch = true;

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);

NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
%>

<%@ include file="/html/portlet/message_boards/init-ext.jsp" %>