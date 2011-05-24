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

<%@ include file="/html/portlet/asset_browser/init.jsp" %>

<%
String typeSelection = request.getParameter("typeSelection");
String callback = request.getParameter("callback");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/search");
portletURL.setParameter("typeSelection", typeSelection);
%>

<liferay-ui:header
	title='<%= LanguageUtil.get(pageContext, "select") + StringPool.COLON + StringPool.SPACE + ResourceActionsUtil.getModelResource(locale, typeSelection) %>'
/>

<div class="asset-search">
	<liferay-portlet:renderURL varImpl="searchURL">
		<portlet:param name="struts_action" value="/asset_browser/view" />
		<portlet:param name="callback" value="<%= callback%>" />
	</liferay-portlet:renderURL>

	<aui:form action="<%= searchURL %>" method="post" name="searchFm">
		<aui:input name="typeSelection" type="hidden" value="<%= typeSelection %>" />

		<%
		AssetSearch searchContainer = new AssetSearch(renderRequest, portletURL);
		%>

		<liferay-ui:search-form
			page="/html/portlet/asset_browser/asset_search.jsp"
			searchContainer="<%= searchContainer %>"
		/>

		<%
		AssetSearchTerms searchTerms = (AssetSearchTerms)searchContainer.getSearchTerms();

		long[] groupIds = new long[] {scopeGroupId};
		%>

		<%@ include file="/html/portlet/asset_publisher/asset_search_results.jspf" %>

		<div class="separator"><!-- --></div>

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.getDocs().length; i++) {
			Document doc = results.doc(i);

			ResultRow row = new ResultRow(doc, i, i);

			long entryId = 0;

			if (typeSelection.equals(JournalArticle.class.getName())) {
				entryId = GetterUtil.getLong(doc.get(Field.ROOT_ENTRY_CLASS_PK));
			}
			else {
				entryId = GetterUtil.getLong(doc.get(Field.ENTRY_CLASS_PK));
			}

			AssetEntry entry = AssetEntryLocalServiceUtil.getEntry(typeSelection, entryId);

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(entry.getClassPK());

			entry = entry.toEscapedModel();

			StringBundler sb = new StringBundler(11);

			sb.append("javascript:Liferay.Util.getOpener().");
			sb.append(callback);
			sb.append("('");
			sb.append(entry.getEntryId());
			sb.append("', '");
			sb.append(LanguageUtil.get(locale, "model.resource." + entry.getClassName()));
			sb.append("', '");
			sb.append(assetRenderer.getTitle(locale));
			sb.append("');Liferay.Util.getWindow().close();");

			String rowHREF = sb.toString();

			// Title

			row.addText(assetRenderer.getTitle(locale), rowHREF);

			// Description

			row.addText(assetRenderer.getSummary(locale), rowHREF);

			// User natypeSelectionme

			row.addText(HtmlUtil.escape(PortalUtil.getUserName(entry.getUserId(), entry.getUserName())), rowHREF);

			// Modified date

			row.addText(dateFormatDate.format(entry.getModifiedDate()), rowHREF);

			// Add result row

			resultRows.add(row);
		}
		%>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</aui:form>
</div>