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

<%@ include file="/html/taglib/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

boolean paginate = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:search-iterator:paginate"));
String type = (String)request.getAttribute("liferay-ui:search:type");

String id = searchContainer.getId(request, namespace);

List resultRows = searchContainer.getResultRows();
List<String> headerNames = searchContainer.getHeaderNames();
List<String> normalizedHeaderNames = searchContainer.getNormalizedHeaderNames();
String emptyResultsMessage = searchContainer.getEmptyResultsMessage();
RowChecker rowChecker = searchContainer.getRowChecker();

if (rowChecker != null) {
	if (headerNames != null) {
		headerNames.add(0, rowChecker.getAllRowsCheckBox(request));

		normalizedHeaderNames.add(0, "rowChecker");
	}
}

JSONArray primaryKeysJSONArray = JSONFactoryUtil.createJSONArray();
%>

<c:if test="<%= resultRows.isEmpty() && (emptyResultsMessage != null) %>">
	<div class="alert alert-info">
		<%= LanguageUtil.get(request, emptyResultsMessage) %>
	</div>
</c:if>

<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP && (resultRows.size() > PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_TOP_DELTA) && paginate %>">
	<div class="taglib-search-iterator-page-iterator-top">
		<liferay-ui:search-paginator id='<%= id + "PageIteratorTop" %>' searchContainer="<%= searchContainer %>" type="<%= type %>" />
	</div>
</c:if>

<ul class="<%= searchContainer.getCssClass() %> tabular-list-group <%= resultRows.isEmpty() ? "hide" : StringPool.BLANK %>" id="<%= namespace + id %>SearchContainer">
	<c:if test="<%= (headerNames != null) && Validator.isNotNull(headerNames.get(0)) %>">
		<li class="list-group-heading"><liferay-ui:message key="<%= headerNames.get(0) %>" /></li>
	</c:if>

	<%
	boolean allRowsIsChecked = true;

	for (int i = 0; i < resultRows.size(); i++) {
		com.liferay.portal.kernel.dao.search.ResultRow row = (com.liferay.portal.kernel.dao.search.ResultRow)resultRows.get(i);

		primaryKeysJSONArray.put(row.getPrimaryKey());

		request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW, row);

		List entries = row.getEntries();

		boolean rowIsChecked = false;

		if (rowChecker != null) {
			rowIsChecked = rowChecker.isChecked(row.getObject());

			if (!rowIsChecked) {
				allRowsIsChecked = false;
			}
		}

		request.setAttribute("liferay-ui:search-container-row:rowId", id.concat(StringPool.UNDERLINE.concat(row.getRowId())));

		Map<String, Object> data = row.getData();
	%>

		<li class="list-group-item <%= GetterUtil.getString(row.getClassName()) %> <%= row.getCssClass() %> <%= rowIsChecked ? "active" : StringPool.BLANK %> <%= Validator.isNotNull(row.getState()) ? "list-group-item-" + row.getState() : StringPool.BLANK %>"  <%= AUIUtil.buildData(data) %>>
			<c:if test="<%= rowChecker != null %>">
				<div class="hidden-sm hidden-x list-group-item-field">
					<aui:input checked="<%= rowIsChecked %>" cssClass="<%= rowChecker.getCssClass() %>" disabled="<%= rowChecker.isDisabled(row.getObject()) %>" id="<%= rowChecker.getRowIds() + row.getPrimaryKey() %>" label="" name="<%= rowChecker.getRowIds() %>" title="select" type="checkbox" useNamespace="<%= false %>" value="<%= row.getPrimaryKey() %>" wrapperCssClass="checkbox-default" />
				</div>
			</c:if>

			<%
			for (int j = 0; j < entries.size(); j++) {
				com.liferay.portal.kernel.dao.search.SearchEntry entry = (com.liferay.portal.kernel.dao.search.SearchEntry)entries.get(j);

				entry.setIndex(j);

				request.setAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY, entry);
			%>

				<div class="<%= entry.getCssClass() %> <%= entry.getColspan() > 1 ? "list-group-item-content" : "list-group-item-field" %>">

					<%
					entry.print(pageContext.getOut(), request, response);
					%>

				</div>

			<%
			}
			%>

		</li>

	<%
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
		request.removeAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW_ENTRY);

		request.removeAttribute("liferay-ui:search-container-row:rowId");
	}
	%>

	<li class="lfr-template list-group-item"></li>
</ul>

<c:if test="<%= PropsValues.SEARCH_CONTAINER_SHOW_PAGINATION_BOTTOM && paginate %>">
	<div class="taglib-search-iterator-page-iterator-bottom">
		<liferay-ui:search-paginator id='<%= id + "PageIteratorBottom" %>' searchContainer="<%= searchContainer %>" type="<%= type %>" />
	</div>
</c:if>

<c:if test="<%= (rowChecker != null) && !resultRows.isEmpty() && Validator.isNotNull(rowChecker.getAllRowsId()) && allRowsIsChecked %>">
	<aui:script>
		document.<%= rowChecker.getFormName() %>.<%= rowChecker.getAllRowsId() %>.checked = true;
	</aui:script>
</c:if>

<c:if test="<%= Validator.isNotNull(id) %>">
	<input id="<%= namespace + id %>PrimaryKeys" name="<%= namespace + id %>PrimaryKeys" type="hidden" value="" />

	<aui:script use="liferay-search-container">
		var searchContainer = new Liferay.SearchContainer(
			{
				classNameHover: '<%= _CLASS_NAME_HOVER %>',
				hover: <%= searchContainer.isHover() %>,
				id: '<%= namespace + id %>',
				rowClassNameAlternate: '<%= _ROW_CLASS_NAME_ALTERNATE %>',
				rowClassNameAlternateHover: '<%= _ROW_CLASS_NAME_ALTERNATE_HOVER %>',
				rowClassNameBody: '<%= _ROW_CLASS_NAME_BODY %>',
				rowClassNameBodyHover: '<%= _ROW_CLASS_NAME_BODY %>'
			}
		).render();

		searchContainer.updateDataStore(<%= primaryKeysJSONArray.toString() %>);
	</aui:script>
</c:if>

<%!
private static final String _CLASS_NAME_HOVER = "hover";

private static final String _ROW_CLASS_NAME_ALTERNATE = "";

private static final String _ROW_CLASS_NAME_ALTERNATE_HOVER = "-hover";

private static final String _ROW_CLASS_NAME_BODY = "";
%>