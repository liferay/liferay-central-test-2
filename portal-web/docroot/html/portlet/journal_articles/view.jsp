<%
/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
if (Validator.isNull(type)) {
	type = null;
}

Boolean approved = Boolean.TRUE;
Boolean expired = Boolean.FALSE;
Date reviewDate = null;

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(WindowState.MAXIMIZED);

portletURL.setParameter("struts_action", "/journal_articles/view");

ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

searchContainer.setDelta(pageDelta);

List headerNames = searchContainer.getHeaderNames();

headerNames.clear();

headerNames.add("title");
headerNames.add("display-date");
headerNames.add("author");

ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

int total = JournalArticleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getArticleId(), searchTerms.getVersionObj(), groupId, searchTerms.getTitle(), searchTerms.getContent(), type, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), approved, expired, reviewDate, searchTerms.isAndOperator());

searchContainer.setTotal(total);

List results = JournalArticleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getArticleId(), searchTerms.getVersionObj(), groupId, searchTerms.getTitle(), searchTerms.getContent(), type, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), approved, expired, reviewDate, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);

searchContainer.setResults(results);

List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalArticle article = (JournalArticle)results.get(i);

	ResultRow row = new ResultRow(article, article.getArticleId() + EditArticleAction.VERSION_SEPARATOR + article.getVersion(), i);

	StringBuffer sb = new StringBuffer();

	sb.append(themeDisplay.getPathMain());
	sb.append("/journal_articles/view_article_content?articleId=");
	sb.append(article.getArticleId());
	sb.append("&version=");
	sb.append(article.getVersion());

	String rowHREF = sb.toString();

	TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, article.getArticleId(), rowHREF, "_blank", null);

	/*// Article id

	row.addText(rowTextEntry);

	// Version

	rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

	rowTextEntry.setName(String.valueOf(article.getVersion()));

	row.addText(rowTextEntry);*/

	// Title

	rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

	rowTextEntry.setName(article.getTitle());

	row.addText(rowTextEntry);

	// Display date

	rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

	rowTextEntry.setName(dateFormatDateTime.format(article.getDisplayDate()));

	row.addText(rowTextEntry);

	// Author

	rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

	rowTextEntry.setName(PortalUtil.getUserName(article.getUserId(), article.getUserName()));

	row.addText(rowTextEntry);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />