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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Document document = (Document)row.getObject();

String className = document.get(Field.ENTRY_CLASS_NAME);
long classPK = GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK));

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(className);

AssetRenderer<?> assetRenderer = null;

if (assetRendererFactory != null) {
	long resourcePrimKey = GetterUtil.getLong(document.get(Field.ROOT_ENTRY_CLASS_PK));

	if (resourcePrimKey > 0) {
		classPK = resourcePrimKey;
	}

	assetRenderer = assetRendererFactory.getAssetRenderer(classPK);
}

String viewURL = com.liferay.search.web.util.SearchUtil.getSearchResultViewURL(renderRequest, renderResponse, className, classPK, searchDisplayContext.isViewInContext(), currentURL);

Indexer indexer = IndexerRegistryUtil.getIndexer(className);

Summary summary = null;

if (indexer != null) {
	String snippet = document.get(Field.SNIPPET);

	summary = indexer.getSummary(document, snippet, renderRequest, renderResponse);
}
else if (assetRenderer != null) {
	summary = new Summary(locale, assetRenderer.getTitle(locale), assetRenderer.getSearchSummary(locale));
}
%>

<c:if test="<%= summary != null %>">

	<%
	viewURL = searchDisplayContext.checkViewURL(viewURL, currentURL);

	summary.setHighlight(searchDisplayContext.isHighlightEnabled());
	summary.setQueryTerms(searchDisplayContext.getQueryTerms());
	%>

	<span class="asset-entry">
		<span class="asset-entry-type">
			<%= ResourceActionsUtil.getModelResource(themeDisplay.getLocale(), className) %>

			<c:if test="<%= locale != summary.getLocale() %>">

				<%
				Locale summaryLocale = summary.getLocale();
				%>

				<liferay-ui:icon image='<%= "../language/" + LocaleUtil.toLanguageId(summaryLocale) %>' message='<%= LanguageUtil.format(request, "this-result-comes-from-the-x-version-of-this-content", summaryLocale.getDisplayLanguage(locale), false) %>' />
			</c:if>
		</span>

		<span class="asset-entry-title">
			<a href="<%= viewURL %>">
				<%= summary.getHighlightedTitle() %>
			</a>

			<c:if test="<%= (assetRenderer != null) && Validator.isNotNull(assetRenderer.getURLDownload(themeDisplay)) %>">
				<aui:a href="<%= assetRenderer.getURLDownload(themeDisplay) %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(summary.getTitle()) %>" key="download-x" />
				</aui:a>
			</c:if>
		</span>

		<%
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(className, classPK);
		%>

		<c:if test="<%= Validator.isNotNull(summary.getContent()) || ((assetEntry != null) && (ArrayUtil.isNotEmpty(assetEntry.getCategoryIds()) || ArrayUtil.isNotEmpty(assetEntry.getTagNames()))) %>">
			<div class="asset-entry-content">
				<c:if test="<%= Validator.isNotNull(summary.getContent()) %>">
					<span class="asset-entry-summary">
						<%= summary.getHighlightedContent() %>
					</span>
				</c:if>

				<c:if test="<%= assetEntry != null %>">
					<liferay-ui:asset-tags-summary
						className="<%= className %>"
						classPK="<%= classPK %>"
						paramName="<%= Field.ASSET_TAG_NAMES %>"
						portletURL="<%= searchDisplayContext.getPortletURL() %>"
					/>

					<liferay-ui:asset-categories-summary
						className="<%= className %>"
						classPK="<%= classPK %>"
						paramName="<%= Field.ASSET_CATEGORY_IDS %>"
						portletURL="<%= searchDisplayContext.getPortletURL() %>"
					/>
				</c:if>
			</div>
		</c:if>
	</span>
</c:if>