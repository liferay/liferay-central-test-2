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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");
String articleId = ParamUtil.getString(request, "articleId");
boolean hideControls = ParamUtil.getBoolean(request, "hideControls");

Set<Locale> availableLocales = (Set<Locale>)request.getAttribute(WebKeys.AVAILABLE_LOCALES);
String diffHtmlResults = (String)request.getAttribute(WebKeys.DIFF_HTML_RESULTS);
String languageId = (String)request.getAttribute(WebKeys.LANGUAGE_ID);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/journal/compare_versions" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
</liferay-portlet:renderURL>

<liferay-portlet:resourceURL varImpl="resourceURL">
	<portlet:param name="struts_action" value="/journal/compare_versions" />
	<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<portlet:param name="articleId" value="<%= articleId %>" />
</liferay-portlet:resourceURL>

<liferay-ui:diff-version-comparator
	availableLocales="<%= availableLocales %>"
	diffHtmlResults="<%= diffHtmlResults %>"
	diffVersionsInfo="<%= JournalUtil.getDiffVersionsInfo(groupId, articleId, sourceVersion, targetVersion) %>"
	hideControls="<%= hideControls %>"
	languageId="<%= languageId %>"
	portletURL="<%= portletURL %>"
	resourceURL="<%= resourceURL %>"
	sourceVersion="<%= sourceVersion %>"
	targetVersion="<%= targetVersion %>"
/>