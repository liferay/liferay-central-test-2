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

<%@ include file="/html/common/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId");

PortletURLImpl searchURL = new PortletURLImpl(request, PortletKeys.SEARCH, plid, PortletRequest.RENDER_PHASE);

searchURL.setEscapeXml(true);

searchURL.setParameter("struts_action", "/search/search");
searchURL.setParameter("groupId", String.valueOf(groupId));

response.setContentType(ContentTypes.TEXT_XML_UTF8);

String siteName = company.getName();

if (plid > 0) {
	LayoutSet layoutSet = layout.getLayoutSet();

	if (Validator.isNotNull(layoutSet.getVirtualHost())) {
		siteName = layoutSet.getGroup().getName();
	}
}
%>

<?xml version="1.0" encoding="UTF-8"?>

<OpenSearchDescription xmlns="http://a9.com/-/spec/opensearch/1.1/">
	<ShortName><%= LanguageUtil.format(pageContext, "x-search", siteName, false) %></ShortName>
	<Description><%= LanguageUtil.format(pageContext, "x-search-provider", siteName, false) %></Description>
	<InputEncoding>UTF-8</InputEncoding>
	<Image width="16" height="16"><%= themeDisplay.getPortalURL() %><%= themeDisplay.getPathThemeImages() %>/<%= PropsValues.THEME_SHORTCUT_ICON %></Image>
	<Url type="text/html" template="<%= searchURL.toString() %>&amp;keywords={searchTerms}" />
	<Url type="application/atom+xml" template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=atom" />
	<Url type="application/rss+xml" template="<%= themeDisplay.getPortalURL() %><%= PortalUtil.getPathMain() %>/search/open_search?keywords={searchTerms}&amp;p={startPage?}&amp;c={count?}&amp;format=rss" />
</OpenSearchDescription>