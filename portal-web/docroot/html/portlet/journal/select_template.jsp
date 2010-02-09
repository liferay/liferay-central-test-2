<%
/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/journal/init.jsp" %>

<form method="post" name="<portlet:namespace />fm">

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "templates");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_template");
portletURL.setParameter("tabs1", tabs1);

TemplateSearch searchContainer = new TemplateSearch(renderRequest, portletURL);

searchContainer.setDelta(10);

String tabs1Names = "shared-templates";

if (scopeGroupId != themeDisplay.getCompanyGroupId()) {
	tabs1Names = "templates," + tabs1Names;
}
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	url="<%= portletURL.toString() %>"
/>

<liferay-ui:search-form
	page="/html/portlet/journal/template_search.jsp"
	searchContainer="<%= searchContainer %>"
/>

<%
TemplateSearchTerms searchTerms = (TemplateSearchTerms)searchContainer.getSearchTerms();

if (tabs1.equals("shared-templates")) {
	long companyGroupId = themeDisplay.getCompanyGroupId();

	searchTerms.setGroupId(companyGroupId);
}

searchTerms.setStructureId(StringPool.BLANK);
searchTerms.setStructureIdComparator(StringPool.NOT_EQUAL);
%>

<%@ include file="/html/portlet/journal/template_search_results.jspf" %>

<div class="separator"><!-- --></div>

<%

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalTemplate template = (JournalTemplate)results.get(i);

	ResultRow row = new ResultRow(template, template.getId(), i);

	StringBundler sb = new StringBundler(7);

	sb.append("javascript:opener.");
	sb.append(renderResponse.getNamespace());
	sb.append("selectTemplate('");
	sb.append(template.getStructureId());
	sb.append("', '");
	sb.append(template.getTemplateId());
	sb.append("'); window.close();");

	String rowHREF = sb.toString();

	row.setParameter("rowHREF", rowHREF);

	// Template id

	row.addText(template.getTemplateId(), rowHREF);

	// Name, description, and image

	row.addJSP("/html/portlet/journal/template_description.jsp");

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

</form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />searchTemplateId);
</aui:script>