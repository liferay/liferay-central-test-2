<%
/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WebKeys.WIKI_PAGE);

WikiPageResource wikiPageResource = wikiPage.getWikiPageResource();
String[] fileNames = wikiPageResource.getAttachmentFileNames();

if (fileNames == null) {
	fileNames = new String[0];
}

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("struts_action", "/wiki/get_page_attachment");
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-util:include page="/html/portlet/wiki/page_info_tabs.jsp">
	<liferay-util:param name="tab" value="attachments" />
</liferay-util:include>

<%
List headerNames = new ArrayList();

headerNames.add("file-name");
headerNames.add("size");
headerNames.add(StringPool.BLANK);

String emptyResultsMessage = "there-are-no-file-attachments-in-this-page";

SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, emptyResultsMessage);

int total = fileNames.length;
List results = ListUtil.fromArray(fileNames);

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	String fileName = (String)results.get(i);
	String shortFileName = FileUtil.getShortFileName(fileName);

	long fileSize = DLServiceUtil.getFileSize(company.getCompanyId(), CompanyImpl.SYSTEM, fileName);

	ResultRow row = new ResultRow(new Object[] {node, wikiPage, fileName}, fileName, i);

	PortletURL rowURL = renderResponse.createActionURL();

	rowURL.setWindowState(LiferayWindowState.EXCLUSIVE);

	rowURL.setParameter("struts_action", "/wiki/get_page_attachment");
	rowURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
	rowURL.setParameter("title", wikiPage.getTitle());
	rowURL.setParameter("fileName", shortFileName);

	// File name

	StringMaker sm = new StringMaker();

	sm.append("<img align=\"left\" border=\"0\" src=\"");
	sm.append(themeDisplay.getPathThemeImages());
	sm.append("/document_library/");
	sm.append(DLUtil.getFileExtension(shortFileName));
	sm.append(".png\">&nbsp;");
	sm.append(shortFileName);

	row.addText(sm.toString(), rowURL);

	// Size

	row.addText(TextFormatter.formatKB(fileSize, locale) + "k", rowURL);

	// Action

	row.addJSP("right", SearchEntry.DEFAULT_VALIGN, "/html/portlet/wiki/page_attachment_action.jsp");

	// Add result row

	resultRows.add(row);
}

%>
<c:if test="<%= WikiNodePermission.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
	<input type="button" value="<liferay-ui:message key="add-attachment" />" onClick="self.location = '<portlet:renderURL><portlet:param name="struts_action" value="/wiki/edit_page_attachment" /><portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>"/><portlet:param name="title" value="<%= wikiPage.getTitle() %>"/><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';" />

	<br/><br/>
</c:if>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />