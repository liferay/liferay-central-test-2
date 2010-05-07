<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
%>

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String originalRedirect = ParamUtil.getString(request, "originalRedirect", StringPool.BLANK);

if (originalRedirect.equals(StringPool.BLANK)) {
	originalRedirect = redirect;
}
else {
	redirect = originalRedirect;
}

JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

PortletURL editArticleURL = renderResponse.createRenderURL();

editArticleURL.setParameter("groupId", String.valueOf(article.getGroupId()));
editArticleURL.setParameter("redirect", redirect);
editArticleURL.setParameter("articleId", article.getArticleId());
editArticleURL.setParameter("struts_action", "/journal/edit_article");

PortletURL viewArticleHistoryURL = renderResponse.createRenderURL();

viewArticleHistoryURL.setParameter("groupId", String.valueOf(article.getGroupId()));
viewArticleHistoryURL.setParameter("redirect", redirect);
viewArticleHistoryURL.setParameter("articleId", article.getArticleId());
viewArticleHistoryURL.setParameter("struts_action", "/journal/view_article_history");
%>

<h1 class="article-title">
	<%= article.getTitle() %>
</h1>

<liferay-ui:tabs
	names="content,history"
	url0="<%= editArticleURL.toString() %>"
	url1="<%= viewArticleHistoryURL.toString() %>"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>