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

<%@ include file="/html/portlet/journal_articles/init.jsp" %>

<%
String articleId = ParamUtil.getString(request, "articleId");
double version = ParamUtil.getDouble(request, "version");
%>

<c:choose>
	<c:when test="<%= Validator.isNull(articleId) %>">

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

		if (showName) {
			headerNames.add("name");
		}
		if (showDisplayDate) {
			headerNames.add("display-date");
		}
		if (showAuthor) {
			headerNames.add("author");
		}

		searchContainer.setOrderableHeaders(null);

		ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

		int total = JournalArticleLocalServiceUtil.searchCount(company.getCompanyId(), groupId, searchTerms.getArticleId(), searchTerms.getVersionObj(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.getContent(), type, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), approved, expired, reviewDate, searchTerms.isAndOperator());

		searchContainer.setTotal(total);

		List results = JournalArticleLocalServiceUtil.search(company.getCompanyId(), groupId, searchTerms.getArticleId(), searchTerms.getVersionObj(), searchTerms.getTitle(), searchTerms.getDescription(), searchTerms.getContent(), type, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), approved, expired, reviewDate, searchTerms.isAndOperator(), searchContainer.getStart(), searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			JournalArticle article = (JournalArticle)results.get(i);

			ResultRow row = new ResultRow(article, article.getArticleId() + EditArticleAction.VERSION_SEPARATOR + article.getVersion(), i);

			String rowHREF = null;

			if (pageURL.equals("popUp")) {
				StringMaker sm = new StringMaker();

				sm.append(themeDisplay.getPathMain());
				sm.append("/journal_articles/view_article_content?groupId=");
				sm.append(article.getGroupId());
				sm.append("&articleId=");
				sm.append(article.getArticleId());
				sm.append("&version=");
				sm.append(article.getVersion());

				rowHREF = sm.toString();
			}
			else {
				portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
				portletURL.setParameter("articleId", article.getArticleId());
				portletURL.setParameter("version", String.valueOf(article.getVersion()));

				rowHREF = portletURL.toString();
			}

			String target = null;

			if (pageURL.equals("popUp")) {
				target = "_blank";;
			}

			if (displayStyle.equals("list")) {
				if (showName) {
				%>
					<h2 class="journal-article-title"><a href="<%= rowHREF%>"><%=article.getTitle()%></a></h2>
				<%
				}
				if (showAuthor) {
				%>
					<p class="journal-article-author"><%= LanguageUtil.get(pageContext, "by") %> <%= PortalUtil.getUserName(article.getUserId(), article.getUserName()) %></p>
				<%
				}
				if (showDescription) {
				%>
					<p class="journal-article-description"><%=article.getDescription()%>
				<%
				}
				if (showDisplayDate) {
				%>
					(<span  class="journal-article-date"><%= dateFormatDate.format(article.getDisplayDate()) %></span>)</p>
				<%
				}
			}
			else {
				TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, article.getArticleId(), rowHREF, target, null);

				/*// Article id

				row.addText(rowTextEntry);

				// Version

				rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

				rowTextEntry.setName(String.valueOf(article.getVersion()));

				row.addText(rowTextEntry);*/

				// Title and description

				StringMaker nameAndDescription = new StringMaker();
				if (showName) {
					nameAndDescription.append(article.getTitle());
				}

				if (showDescription) {
					nameAndDescription.append("<br>");
					nameAndDescription.append("<span style=\"font-size: xx-small;\">");
					nameAndDescription.append(article.getDescription());
					nameAndDescription.append("</span>");
				}

				if (nameAndDescription.length() > 0) {
					rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

					rowTextEntry.setName(nameAndDescription.toString());

					row.addText(rowTextEntry);
				}

				// Display date

				if (showDisplayDate) {
					rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

					rowTextEntry.setName(dateFormatDateTime.format(article.getDisplayDate()));

					row.addText(rowTextEntry);
				}

				// Author

				if (showAuthor) {
					rowTextEntry = (TextSearchEntry)rowTextEntry.clone();

					rowTextEntry.setName(PortalUtil.getUserName(article.getUserId(), article.getUserName()));

					row.addText(rowTextEntry);
				}

				// Add result row

				resultRows.add(row);
			}
		}
		%>
		<c:if test='<%= displayStyle.equals("table") %>' >
			<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
		</c:if>
		<c:if test="<%= showPagination %>" >
			<% portletURL.setParameter("articleId", ""); %>
			<liferay-ui:search-paginator searchContainer="<%= searchContainer %>" />
		</c:if>

	</c:when>
	<c:otherwise>

		<%
		String languageId = LanguageUtil.getLanguageId(request);

		String content = JournalArticleLocalServiceUtil.getArticleContent(company.getCompanyId(), groupId, articleId, version, languageId, themeDisplay);

		RuntimeLogic portletLogic = new PortletLogic(application, request, response, renderRequest, renderResponse);
		RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
		RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

		content = RuntimePortletUtil.processXML(request, content, portletLogic);
		content = RuntimePortletUtil.processXML(request, content, actionURLLogic);
		content = RuntimePortletUtil.processXML(request, content, renderURLLogic);
		%>

		<%= content %>
	</c:otherwise>
</c:choose>