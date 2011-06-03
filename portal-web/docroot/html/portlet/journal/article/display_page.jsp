<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
JournalArticle article = (JournalArticle)request.getAttribute(WebKeys.JOURNAL_ARTICLE);

String defaultLanguageId = (String)request.getAttribute("edit_article.jsp-defaultLanguageId");

String layoutUuid = BeanParamUtil.getString(article, request, "layoutUuid");

boolean preselectCurrentLayout = false;

if ((article == null) || article.isNew()) {
	UnicodeProperties typeSettingsProperties = layout.getTypeSettingsProperties();

	long refererPlid = ParamUtil.getLong(request, "refererPlid", LayoutConstants.DEFAULT_PLID);

	if (refererPlid > 0) {
		Layout refererLayout = LayoutLocalServiceUtil.getLayout(refererPlid);

		typeSettingsProperties = refererLayout.getTypeSettingsProperties();

		String defaultAssetPublisherPortletId = typeSettingsProperties.getProperty(LayoutTypePortletConstants.DEFAULT_ASSET_PUBLISHER_PORTLET_ID);

		if (Validator.isNotNull(defaultAssetPublisherPortletId)) {
			preselectCurrentLayout = true;
		}
	}
}
%>

<liferay-ui:error-marker key="errorSection" value="display-page" />

<aui:model-context bean="<%= article %>" model="<%= JournalArticle.class %>" />

<h3><liferay-ui:message key="display-page" /></h3>

<%
List<Layout> privateGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, true);
List<Layout> publicGroupLayouts = JournalUtil.getDefaultAssetPublisherLayouts(scopeGroupId, false);

if (privateGroupLayouts.isEmpty() && publicGroupLayouts.isEmpty()) {
%>

	<liferay-ui:message key="there-are-no-pages-configured-to-be-the-display-page" />

<%
}
else {
%>

	<aui:select helpMessage="default-display-page-help" label="default-display-page" name="layoutUuid" showEmptyOption="<%= true %>">

	<%
	if (!publicGroupLayouts.isEmpty()) {
	%>

		<optgroup label="<liferay-ui:message key="public-pages" />">

	<%
		for (Layout groupLayout : publicGroupLayouts) {
	%>

			<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= layoutUuid.equals(groupLayout.getUuid()) || preselectCurrentLayout %>" value="<%= groupLayout.getUuid() %>" />

	<%
		}
	%>

		</optgroup>

	<%
	}

	if (!privateGroupLayouts.isEmpty()) {
	%>

		<optgroup label="<liferay-ui:message key="private-pages" />">

	<%
		for (Layout groupLayout : privateGroupLayouts) {
	%>

			<aui:option label="<%= groupLayout.getName(defaultLanguageId) %>" selected="<%= layoutUuid.equals(groupLayout.getUuid()) || preselectCurrentLayout %>" value="<%= groupLayout.getUuid() %>" />

	<%
		}
	%>

		</optgroup>
	<%
	}
	%>

	</aui:select>

<%
}
%>

<c:if test="<%= Validator.isNotNull(layoutUuid)%>">
	<%
	Layout defaultDisplayPage = LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(layoutUuid, scopeGroupId);

	AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());
	AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());

	PortletRequest portletRequest = (PortletRequest)request.getAttribute(JavaConstants.JAVAX_PORTLET_REQUEST);
	PortletResponse portletResponse = (PortletResponse)request.getAttribute(JavaConstants.JAVAX_PORTLET_RESPONSE);

	String urlViewInContext = assetRenderer.getURLViewInContext((LiferayPortletRequest) portletRequest, (LiferayPortletResponse) portletResponse, currentURL);
	%>

	<c:if test="<%= Validator.isNotNull(urlViewInContext) %>">
		<a href="<%= urlViewInContext %>" target="blank"><%= LanguageUtil.format(pageContext, "view-content-in-x", defaultDisplayPage.getName(locale)) %></a>
	</c:if>
</c:if>