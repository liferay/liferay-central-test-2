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

<%@ include file="/html/portlet/dynamic_data_lists/init.jsp" %>

<%
DDLEntry entry = (DDLEntry)request.getAttribute(WebKeys.DYNAMIC_DATA_LISTS_ENTRY);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_entry");
portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

DDMStructure ddmStructure = entry.getDDMStructure();

Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap();

List<String> headerNames = new ArrayList();

for (Map<String, String> fields : fieldsMap.values()) {
	String label = fields.get(DDMFieldConstants.LABEL);

	headerNames.add(label);
}

headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, portletURL, headerNames, "no-list-items-were-found");

int total = DDLEntryItemLocalServiceUtil.getEntryItemsCount(entry.getEntryId());

searchContainer.setTotal(total);

List<DDLEntryItem> results = DDLEntryItemLocalServiceUtil.getEntryItems(entry.getEntryId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	DDLEntryItem entryItem = results.get(i);

	Fields fieldsModel = StorageEngineUtil.getFields(entryItem.getClassPK());

	ResultRow row = new ResultRow(entryItem, entryItem.getEntryItemId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/dynamic_data_lists/edit_entry_item");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("entryItemId", String.valueOf(entryItem.getEntryItemId()));

	// Columns

	for (Map<String, String> fields : fieldsMap.values()) {
		String name = fields.get(DDMFieldConstants.NAME);

		String value = null;

		if (fieldsModel.contains(name)) {
			com.liferay.portlet.dynamicdatamapping.storage.Field field = fieldsModel.get(name);

			value = String.valueOf(field.getValue());
		}
		else {
			value = StringPool.BLANK;
		}

		row.addText(value, rowURL);
	}

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/dynamic_data_lists/entry_item_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />