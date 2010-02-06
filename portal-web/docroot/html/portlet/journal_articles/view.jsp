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

		String status = "approved";

		PortletURL portletURL = renderResponse.createRenderURL();

		if (pageURL.equals("normal")) {
			portletURL.setWindowState(WindowState.NORMAL);
		}
		else {
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		portletURL.setParameter("struts_action", "/journal_articles/view");

		PortletURL articleURL = PortletURLUtil.clone(portletURL, renderResponse);

		ArticleSearch searchContainer = new ArticleSearch(renderRequest, portletURL);

		searchContainer.setDelta(pageDelta);
		searchContainer.setOrderByCol(orderByCol);
		searchContainer.setOrderByType(orderByType);
		searchContainer.setOrderByComparator(orderByComparator);

		List headerNames = searchContainer.getHeaderNames();

		headerNames.clear();

		headerNames.add("name");
		headerNames.add("display-date");
		headerNames.add("author");

		searchContainer.setOrderableHeaders(null);

		ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

		searchTerms.setGroupId(groupId);
		searchTerms.setType(type);

		if (Validator.isNotNull(structureId)) {
			searchTerms.setStructureId(structureId);
		}

		searchTerms.setDisplayDateLT(new Date());
		searchTerms.setStatus(status);
		%>

		<%@ include file="/html/portlet/journal/article_search_results.jspf" %>

		<%
		List resultRows = searchContainer.getResultRows();

		for (int i = 0; i < results.size(); i++) {
			JournalArticle article = (JournalArticle)results.get(i);

			article = article.toEscapedModel();

			ResultRow row = new ResultRow(article, article.getArticleId() + EditArticleAction.VERSION_SEPARATOR + article.getVersion(), i);

			String rowHREF = null;

			if (pageURL.equals("popUp")) {
				StringBuilder sb = new StringBuilder();

				sb.append(themeDisplay.getPathMain());
				sb.append("/journal_articles/view_article_content?groupId=");
				sb.append(article.getGroupId());
				sb.append("&articleId=");
				sb.append(article.getArticleId());
				sb.append("&version=");
				sb.append(article.getVersion());

				rowHREF = sb.toString();
			}
			else {
				articleURL.setParameter("groupId", String.valueOf(article.getGroupId()));
				articleURL.setParameter("articleId", article.getArticleId());
				articleURL.setParameter("version", String.valueOf(article.getVersion()));

				rowHREF = articleURL.toString();
			}

			String target = null;

			if (pageURL.equals("popUp")) {
				target = "_blank";
			}

			TextSearchEntry rowTextEntry = new TextSearchEntry(SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN, article.getArticleId(), rowHREF, target, null);

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
	</c:when>
	<c:otherwise>

		<%
		String languageId = LanguageUtil.getLanguageId(request);
		int articlePage = ParamUtil.getInteger(renderRequest, "page", 1);
		String xmlRequest = PortletRequestUtil.toXML(renderRequest, renderResponse);

		JournalArticleDisplay articleDisplay = JournalContentUtil.getDisplay(groupId, articleId, null, null, languageId, themeDisplay, articlePage, xmlRequest);

		if (articleDisplay != null) {
			AssetEntryLocalServiceUtil.incrementViewCounter(JournalArticle.class.getName(), articleDisplay.getResourcePrimKey());
		}
		%>

		<c:choose>
			<c:when test="<%= articleDisplay != null %>">
				<div class="journal-content-article">
					<%= articleDisplay.getContent() %>
				</div>

				<c:if test="<%= articleDisplay.isPaginate() %>">

					<%
					PortletURL portletURL = renderResponse.createRenderURL();

					portletURL.setParameter("articleId", articleId);
					portletURL.setParameter("version", String.valueOf(version));
					%>

					<br />

					<liferay-ui:page-iterator
						cur="<%= articleDisplay.getCurrentPage() %>"
						curParam='<%= "page" %>'
						delta="<%= 1 %>"
						maxPages="<%= 25 %>"
						total="<%= articleDisplay.getNumberOfPages() %>"
						type="article"
						url="<%= portletURL.toString() %>"
					/>

					<br />
				</c:if>
			</c:when>
			<c:otherwise>
				<div class="portlet-msg-error">
					<liferay-ui:message key="you-do-not-have-the-required-permissions" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>