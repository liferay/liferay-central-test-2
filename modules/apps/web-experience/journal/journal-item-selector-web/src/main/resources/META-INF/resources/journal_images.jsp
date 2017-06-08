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
JournalItemSelectorViewDisplayContext journalItemSelectorViewDisplayContext = (JournalItemSelectorViewDisplayContext)request.getAttribute(JournalItemSelectorWebKeys.JOURNAL_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT);

int cur = ParamUtil.getInteger(request, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_CUR);
int delta = ParamUtil.getInteger(request, SearchContainer.DEFAULT_DELTA_PARAM, SearchContainer.DEFAULT_DELTA);

int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(cur, delta);

int start = startAndEnd[0];
int end = startAndEnd[1];

JournalArticle journalArticle = journalItemSelectorViewDisplayContext.getJournalArticle();

List portletFileEntries = null;
int portletFileEntriesCount = 0;

if (journalArticle != null) {
	String orderByCol = ParamUtil.getString(request, "orderByCol", "title");
	String orderByType = ParamUtil.getString(request, "orderByType", "asc");

	OrderByComparator<FileEntry> orderByComparator = DLUtil.getRepositoryModelOrderByComparator(orderByCol, orderByType);

	portletFileEntries = journalArticle.getImagesFileEntries(start, end, orderByComparator);
	portletFileEntriesCount = journalArticle.getImagesFileEntriesCount();
}
%>

<liferay-item-selector:repository-entry-browser
	emptyResultsMessage='<%= LanguageUtil.get(resourceBundle, "there-are-no-journal-images") %>'
	itemSelectedEventName="<%= journalItemSelectorViewDisplayContext.getItemSelectedEventName() %>"
	itemSelectorReturnTypeResolver="<%= journalItemSelectorViewDisplayContext.getItemSelectorReturnTypeResolver() %>"
	portletURL="<%= journalItemSelectorViewDisplayContext.getPortletURL(request, liferayPortletResponse) %>"
	repositoryEntries="<%= portletFileEntries %>"
	repositoryEntriesCount="<%= portletFileEntriesCount %>"
	tabName="<%= journalItemSelectorViewDisplayContext.getTitle(locale) %>"
	uploadURL="<%= journalItemSelectorViewDisplayContext.getUploadURL(liferayPortletResponse) %>"
/>