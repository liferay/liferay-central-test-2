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
DDLEntry entry = (DDLEntry)request.getAttribute("view_entry_items.jsp-entry");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/dynamic_data_lists/view_entry");
portletURL.setParameter("entryId", String.valueOf(entry.getEntryId()));

DDMStructure structure = entry.getDDMStructure();

Map<String, Map<String, String>> fieldsMap = structure.getFieldsMap();

List<Map<String, String>> fieldsList = new ArrayList(fieldsMap.values());

List<String> headerNames = new ArrayList();

for (Map<String, String> field : fieldsList) {
	headerNames.add(field.get(DDMFieldConstants.LABEL));
}

headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, portletURL, headerNames, "no-list-items-were-found");

List<DDLEntryItem> entryItems = DDLEntryItemLocalServiceUtil.getEntryItems(entry.getEntryId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());

int total = DDLEntryItemLocalServiceUtil.getEntryItemsCount(entry.getEntryId());

searchContainer.setTotal(total);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < entryItems.size(); i++) {
	DDLEntryItem entryItem = entryItems.get(i);

	Fields fields = StorageEngineUtil.getFields(entryItem.getClassPK());

	ResultRow row = new ResultRow(entryItem, entryItem.getEntryItemId(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setParameter("struts_action", "/dynamic_data_lists/edit_entry_item");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("entryItemId", String.valueOf(entryItem.getEntryItemId()));

	// Columns

	String name = null;
	String value = null;

	for (Map<String, String> field : fieldsList) {
		name = field.get(DDMFieldConstants.NAME);

		if (fields.get(name) != null) {
			value = String.valueOf(fields.get(name).getValue());
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