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

<%@ include file="/html/portlet/software_repository/init.jsp" %>

<%
String type = ParamUtil.getString(request, "type");
String keywords = ParamUtil.getString(request, "keywords");
%>

<form action="<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/software_repository/search" /></portlet:renderURL>" method="post" name="<portlet:namespace />fm" onSubmit="submitForm(this); return false;">

<liferay-util:include page="/html/portlet/software_repository/tabs1.jsp" />

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/software_repository/search");
portletURL.setParameter("type", type);
portletURL.setParameter("keywords", keywords);

List headerNames = new ArrayList();

headerNames.add("#");
headerNames.add("name");
headerNames.add("type");
headerNames.add("score");

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, LanguageUtil.format(pageContext, "no-products-were-found-that-matched-the-keywords-x", "<b>" + keywords + "</b>"));

Hits hits = SRProductEntryLocalServiceUtil.search(company.getCompanyId(), portletGroupId.longValue(), type, keywords);

Hits results = hits.subset(searchContainer.getStart(), searchContainer.getEnd());

int total = hits.getLength();

searchContainer.setTotal(total);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.getLength(); i++) {
	Document doc = results.doc(i);

	ResultRow row = new ResultRow(doc, String.valueOf(i), i);

	// Position

	row.addText(searchContainer.getStart() + i + 1 + StringPool.PERIOD);

	// Name and type

	String title = doc.get("title");
	String docType = doc.get("type");
	String productEntryId = doc.get("productEntryId");

	PortletURL rowURL = renderResponse.createRenderURL();

	rowURL.setWindowState(WindowState.MAXIMIZED);

	rowURL.setParameter("struts_action", "/software_repository/view_product_entry");
	rowURL.setParameter("redirect", currentURL);
	rowURL.setParameter("productEntryId", productEntryId);

	row.addText(title, rowURL);
	row.addText(LanguageUtil.get(pageContext, docType), rowURL);

	// Score

	row.addText(String.valueOf(hits.score(i)), rowURL);

	// Add result row

	resultRows.add(row);
}
%>

<input class="form-text" name="<portlet:namespace />keywords" size="30" type="text" value="<%= keywords %>">

<select name="<portlet:namespace/>type">
	<option value=""></option>
	<option <%= type.equals("portlet")? "selected" : "" %> value="portlet"><%= LanguageUtil.get(pageContext, "portlet") %></option>
	<option <%= type.equals("theme")? "selected" : "" %> value="theme"><%= LanguageUtil.get(pageContext, "theme") %></option>
	<option <%= type.equals("layout")? "selected" : "" %> value="layout"><%= LanguageUtil.get(pageContext, "layout") %></option>
	<option <%= type.equals("extension")? "selected" : "" %> value="extension"><%= LanguageUtil.get(pageContext, "extension") %></option>
</select>

<input class="portlet-form-button" type="submit" value="<%= LanguageUtil.get(pageContext, "search-products") %>">

<br><br>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />keywords.focus();
</script>