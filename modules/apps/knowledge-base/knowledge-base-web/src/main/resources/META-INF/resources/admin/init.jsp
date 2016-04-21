<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.document.library.kernel.util.DLUtil" %><%@
page import="com.liferay.knowledge.base.constants.KBCommentConstants" %><%@
page import="com.liferay.knowledge.base.service.util.AdminUtil" %><%@
page import="com.liferay.knowledge.base.web.display.context.KBSuggestionListDisplayContext" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleDisplayTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleSearch" %><%@
page import="com.liferay.knowledge.base.web.search.KBArticleSearchTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateDisplayTerms" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateSearch" %><%@
page import="com.liferay.knowledge.base.web.search.KBTemplateSearchTerms" %><%@
page import="com.liferay.knowledge.base.web.util.KBArticleAssetEntriesUtil" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %>

<%
String kbArticlesOrderByCol = portletPreferences.getValue("kbArticlesOrderByCol", StringPool.BLANK);
String kbArticlesOrderByType = portletPreferences.getValue("kbArticlesOrderByType", StringPool.BLANK);

boolean enableKBArticleDescription = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleDescription", null));
boolean enableKBArticleRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleRatings", null));
String kbArticleRatingsType = GetterUtil.getString(portletPreferences.getValue("kbArticleRatingsType", null), "thumbs");
boolean showKBArticleAssetEntries = GetterUtil.getBoolean(portletPreferences.getValue("showKBArticleAssetEntries", null));
boolean showKBArticleAttachments = GetterUtil.getBoolean(portletPreferences.getValue("showKBArticleAttachments", null), true);
boolean enableKBArticleAssetLinks = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleAssetLinks", null), true);
boolean enableKBArticleViewCountIncrement = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleViewCountIncrement", null));
boolean enableKBArticleSubscriptions = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleSubscriptions", null), true);
boolean enableKBArticleHistory = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticleHistory", null), true);
boolean enableKBArticlePrint = GetterUtil.getBoolean(portletPreferences.getValue("enableKBArticlePrint", null), true);
boolean enableSocialBookmarks = GetterUtil.getBoolean(portletPreferences.getValue("enableSocialBookmarks", null));
String socialBookmarksDisplayStyle = portletPreferences.getValue("socialBookmarksDisplayStyle", PortletPropsValues.KNOWLEDGE_BASE_SOCIAL_BOOKMARKS_DISPLAY_STYLE);
String socialBookmarksDisplayPosition = portletPreferences.getValue("socialBookmarksDisplayPosition", "bottom");
String socialBookmarksTypes = portletPreferences.getValue("socialBookmarksTypes", PropsUtil.get(PropsKeys.SOCIAL_BOOKMARK_TYPES));

boolean enableKBTemplateKBComments = GetterUtil.getBoolean(portletPreferences.getValue("enableKBTemplateKBComments", null));
boolean showKBTemplateKBComments = GetterUtil.getBoolean(portletPreferences.getValue("showKBTemplateKBComments", null));

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean(portletPreferences.getValue("enableRss", null), true);
int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_FULL_CONTENT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
%>