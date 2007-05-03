<%
/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
String breadcrumbsCategoryId = ParamUtil.getString(request, "breadcrumbsCategoryId");

String categoryIds = ParamUtil.getString(request, "categoryIds");
String[] categoryIdsArray = StringUtil.split(categoryIds);

String keywords = ParamUtil.getString(request, "keywords");
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/shopping/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">
<input name="<portlet:namespace />breadcrumbsCategoryId" type="hidden" value="<%= breadcrumbsCategoryId %>">
<input name="<portlet:namespace />categoryIds" type="hidden" value="<%= categoryIds %>">

<div class="breadcrumbs">
	<%= ShoppingUtil.getBreadcrumbs(breadcrumbsCategoryId, pageContext, renderRequest, renderResponse) %> &raquo; <bean:message key="search" />
</div>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/shopping/search");
portletURL.setParameter("breadcrumbsCategoryId", breadcrumbsCategoryId);
portletURL.setParameter("categoryIds", categoryIds);
portletURL.setParameter("keywords", keywords);

List headerNames = new ArrayList();

headerNames.add("sku");
headerNames.add("description");
headerNames.add("min-qty");
headerNames.add("price");
headerNames.add(StringPool.BLANK);

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-entries-were-found-that-matched-the-keywords-x", "<b>" + keywords + "</b>"));

int total = ShoppingItemLocalServiceUtil.searchCount(portletGroupId.longValue(), categoryIdsArray, keywords);

searchContainer.setTotal(total);

List results = ShoppingItemLocalServiceUtil.search(portletGroupId.longValue(), categoryIdsArray, keywords, searchContainer.getStart(), searchContainer.getEnd());

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	ShoppingItem item = (ShoppingItem)results.get(i);

	ResultRow row = new ResultRow(item, item.getPrimaryKey().toString(), i);

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/shopping/view_item");
	rowURL.setParameter("itemId", item.getItemId());

	// SKU and small image

	StringMaker sm = new StringMaker();

	if (item.isSmallImage()) {
		sm.append("<br />");
		sm.append("<img alt=\"");
		sm.append(item.getSku());
		sm.append("\" border=\"0\" src=\"");

		if (Validator.isNotNull(item.getSmallImageURL())) {
			sm.append(item.getSmallImageURL());
		}
		else {
			sm.append(themeDisplay.getPathImage());
			sm.append("/shopping/item?img_id=");
			sm.append(item.getItemId());
			sm.append("&small=1");
		}

		sm.append("\">");
	}
	else {
		sm.append("<span style=\"font-size: xx-small;\">");
		sm.append(item.getSku());
		sm.append("</span>");
	}

	row.addText(sm.toString(), rowURL);

	// Description

	sm = new StringMaker();

	sm.append(item.getName());

	sm.append("<span style=\"font-size: xx-small;\">");

	if (Validator.isNotNull(item.getDescription())) {
		sm.append("<br />");
		sm.append(item.getDescription());
	}

	Properties props = new OrderedProperties();

	PropertiesUtil.load(props, item.getProperties());

	Enumeration enu = props.propertyNames();

	while (enu.hasMoreElements()) {
		String propsKey = (String)enu.nextElement();
		String propsValue = props.getProperty(propsKey, StringPool.BLANK);

		sm.append("<br />");
		sm.append(propsKey);
		sm.append(": ");
		sm.append(propsValue);
	}

	sm.append("</span>");

	row.addText(sm.toString(), rowURL);

	// Minimum quantity

	row.addText(String.valueOf(item.getMinQuantity()), rowURL);

	// Price

	if (item.getDiscount() <= 0) {
		row.addText(currency.getSymbol() + doubleFormat.format(item.getPrice()), rowURL);
	}
	else {
		row.addText("<span class=\"portlet-msg-success\">" + currency.getSymbol() + doubleFormat.format(ShoppingUtil.calculateActualPrice(item)) + "</span>", rowURL);
	}

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/shopping/item_action.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<input name="<portlet:namespace />keywords" size="30" type="text" value="<%= keywords %>">

<input type="submit" value="<bean:message key="search" />">

<br /><br />

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />keywords.focus();
</script>