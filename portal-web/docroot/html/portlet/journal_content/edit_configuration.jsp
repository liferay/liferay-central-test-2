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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
String cur = ParamUtil.getString(request, "cur");

String groupId = null;

List communities = GroupLocalServiceUtil.search(company.getCompanyId(), null, null, null, 0, 1);

if (communities.size() > 0) {
	Group group = (Group)communities.get(0);

	groupId = group.getGroupId();
}

String type = JournalArticle.TYPES[0];

JournalArticle article = null;

try {
	article = JournalArticleLocalServiceUtil.getLatestArticle(company.getCompanyId(), articleId);

	groupId = article.getGroupId();
	type = article.getType();
}
catch (NoSuchArticleException nsae) {
}

groupId = ParamUtil.getString(request, "groupId", groupId);
type = ParamUtil.getString(request, "type", type);
%>

<liferay-portlet:renderURL portletConfiguration="true" varImpl="portletURL" />

<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="">
<input name="<portlet:namespace />redirect" type="hidden" value="<%= portletURL.toString() %>&<portlet:namespace />cur=<%= cur %>">
<input name="<portlet:namespace />articleId" type="hidden" value="">

<liferay-ui:error exception="<%= NoSuchArticleException.class %>" message="the-article-could-not-be-found" />

<%
DynamicRenderRequest dynamicRenderReq = new DynamicRenderRequest(renderRequest);

dynamicRenderReq.setParameter("groupId", groupId);

ArticleSearch searchContainer = new ArticleSearch(dynamicRenderReq, portletURL);
%>

<liferay-ui:search-form
	page="/html/portlet/journal/article_search.jsp"
	searchContainer="<%= searchContainer %>"
>
	<liferay-ui:param name="groupId" value="<%= groupId %>" />
	<liferay-ui:param name="type" value="<%= type %>" />
</liferay-ui:search-form>

<c:if test="<%= article != null %>">
	<br>

	<%= LanguageUtil.get(pageContext, "displaying-article") %>: <%= article.getArticleId() %><br>
</c:if>

<%
OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(searchContainer.getOrderByCol(), searchContainer.getOrderByType());

ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

int total = JournalArticleLocalServiceUtil.searchCount(company.getCompanyId(), searchTerms.getArticleId(), searchTerms.getVersionObj(), searchTerms.getGroupId(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.getContent(), searchTerms.getType(), searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getApprovedObj(), searchTerms.getExpiredObj(), searchTerms.getReviewDate(), searchTerms.isAndOperator());

searchContainer.setTotal(total);

List results = JournalArticleLocalServiceUtil.search(company.getCompanyId(), searchTerms.getArticleId(), searchTerms.getVersionObj(), searchTerms.getGroupId(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.getContent(), searchTerms.getType(), searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getApprovedObj(), searchTerms.getExpiredObj(), searchTerms.getReviewDate(), searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);

searchContainer.setResults(results);
%>

<br><div class="beta-separator"></div><br>

<%
List resultRows = searchContainer.getResultRows();

for (int i = 0; i < results.size(); i++) {
	JournalArticle curArticle = (JournalArticle)results.get(i);

	ResultRow row = new ResultRow(article, curArticle.getArticleId() + EditArticleAction.VERSION_SEPARATOR + curArticle.getVersion(), i);

	StringBuffer sb = new StringBuffer();

	sb.append("javascript: document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm.");
	sb.append(renderResponse.getNamespace());
	sb.append(Constants.CMD);
	sb.append(".value = '");
	sb.append(Constants.UPDATE);
	sb.append("'; document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm.");
	sb.append(renderResponse.getNamespace());
	sb.append("articleId.value = '");
	sb.append(curArticle.getArticleId());
	sb.append("'; submitForm(document.");
	sb.append(renderResponse.getNamespace());
	sb.append("fm);");

	String rowHREF = sb.toString();

	// Article id

	row.addText(curArticle.getArticleId(), rowHREF);

	// Version

	row.addText(String.valueOf(curArticle.getVersion()), rowHREF);

	// Title

	row.addText(curArticle.getTitle(), rowHREF);

	// Display date

	row.addText(dateFormatDateTime.format(curArticle.getDisplayDate()), rowHREF);

	// Author

	row.addText(PortalUtil.getUserName(curArticle.getUserId(), curArticle.getUserName()), rowHREF);

	// Add result row

	resultRows.add(row);
}
%>

<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />

</form>

<script type="text/javascript">
	document.<portlet:namespace />fm.<portlet:namespace />searchArticleId.focus();
</script>