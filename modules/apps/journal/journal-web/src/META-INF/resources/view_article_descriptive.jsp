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

<%
String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

JournalArticle article = null;

if (row != null) {
	article = (JournalArticle)row.getObject();
}
else {
	article = (JournalArticle)request.getAttribute("view_entries.jsp-article");
}

JournalArticle latestApprovedArticleVersion = null;

Date createDate = article.getCreateDate();

if (article.getVersion() > JournalArticleConstants.VERSION_DEFAULT) {
	JournalArticle firstArticleVersion = JournalArticleLocalServiceUtil.getOldestArticle(article.getGroupId(), article.getArticleId());

	createDate = firstArticleVersion.getCreateDate();

	if (article.getStatus() > WorkflowConstants.STATUS_APPROVED) {
		latestApprovedArticleVersion = JournalArticleLocalServiceUtil.fetchLatestArticle(article.getGroupId(), article.getArticleId(), WorkflowConstants.STATUS_APPROVED);
	}
}

DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), article.getDDMStructureKey(), true);

String articleImageURL = article.getArticleImageURL(themeDisplay);
%>

<liferay-portlet:renderURL varImpl="rowURL">
	<portlet:param name="mvcPath" value="/edit_article.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="backURL" value="<%= currentURL %>" />
	<portlet:param name="referringPortletResource" value="<%= referringPortletResource %>" />
	<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
	<portlet:param name="folderId" value="<%= String.valueOf(article.getFolderId()) %>" />
	<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
	<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
</liferay-portlet:renderURL>

<liferay-ui:app-view-entry
	actionJsp="/article_action.jsp"
	actionJspServletContext="<%= application %>"
	assetCategoryClassName="<%= JournalArticle.class.getName() %>"
	assetCategoryClassPK="<%= article.getResourcePrimKey() %>"
	assetTagClassName="<%= JournalArticle.class.getName() %>"
	assetTagClassPK="<%= article.getResourcePrimKey() %>"
	author="<%= article.getUserName() %>"
	classTypeName="<%= HtmlUtil.escape(ddmStructure.getName(locale)) %>"
	createDate="<%= createDate %>"
	description="<%= HtmlUtil.escape(article.getDescription(locale)) %>"
	displayDate="<%= article.getDisplayDate() %>"
	displayStyle="descriptive"
	expirationDate="<%= article.getExpirationDate() %>"
	groupId="<%= article.getGroupId() %>"
	latestApprovedVersion="<%= (latestApprovedArticleVersion != null) ? String.valueOf(latestApprovedArticleVersion.getVersion()) : null %>"
	latestApprovedVersionAuthor="<%= (latestApprovedArticleVersion != null) ? String.valueOf(latestApprovedArticleVersion.getUserName()) : null %>"
	modifiedDate="<%= article.getModifiedDate() %>"
	reviewDate="<%= article.getReviewDate() %>"
	rowCheckerId="<%= HtmlUtil.escape(article.getArticleId()) %>"
	rowCheckerName="<%= JournalArticle.class.getSimpleName() %>"
	showCheckbox="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) || JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>"
	status="<%= article.getStatus() %>"
	thumbnailDivStyle="height: 146px; width: 146px;"
	thumbnailSrc='<%= Validator.isNotNull(articleImageURL) ? articleImageURL : themeDisplay.getPathThemeImages() + "/file_system/large/article.png" %>'
	thumbnailStyle="max-height: 128px; max-width: 128px;"
	title="<%= HtmlUtil.escape(article.getTitle(locale)) %>"
	url="<%= rowURL != null ? rowURL.toString() : null %>"
	version="<%= String.valueOf(article.getVersion()) %>"
	view="lexicon"
/>