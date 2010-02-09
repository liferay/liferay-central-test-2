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

<%@ include file="/html/portlet/journal_content_search/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

String[] queryTerms = (String[])objArray[0];
Document doc = (Document)objArray[1];
String snippet = (String)objArray[2];

String content = snippet;

if (Validator.isNull(snippet)) {
	content = StringUtil.shorten(doc.get(Field.CONTENT), 200);
}

content = StringUtil.highlight(content, queryTerms);

String articleId = doc.get(Field.ENTRY_CLASS_PK);
long articleGroupId = GetterUtil.getLong(doc.get(Field.GROUP_ID));

List hitLayoutIds = JournalContentSearchLocalServiceUtil.getLayoutIds(layout.getGroupId(), layout.isPrivateLayout(), articleId);
%>

<%= content %><br />

<c:choose>
	<c:when test="<%= !hitLayoutIds.isEmpty() %>">
		<span style="font-size: xx-small;">

		<%
		for (int i = 0; i < hitLayoutIds.size(); i++) {
			Long hitLayoutId = (Long)hitLayoutIds.get(i);

			Layout hitLayout = null;

			try {
				hitLayout = LayoutLocalServiceUtil.getLayout(layout.getGroupId(), layout.isPrivateLayout(), hitLayoutId.longValue());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Journal content search is stale and contains layout {" + layout.getGroupId() + ", " + layout.isPrivateLayout() + ", " + hitLayoutId.longValue() + "}");
				}

				continue;
			}

			String hitLayoutURL = PortalUtil.getLayoutFullURL(hitLayout, themeDisplay);
		%>

			<br /><a href="<%= hitLayoutURL %>"><%= StringUtil.shorten(hitLayoutURL, 100) %></a>

		<%
		}
		%>

		</span>
	</c:when>
	<c:otherwise>
		<span style="font-size: xx-small;">

		<%
		StringBuilder sb = new StringBuilder();

		sb.append(PortalUtil.getLayoutFriendlyURL(layout, themeDisplay));
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("journal_content/");

		if (Validator.isNotNull(targetPortletId)) {
			sb.append(targetPortletId);
		}
		else {
			sb.append(PortletKeys.JOURNAL_CONTENT);
		}

		sb.append("/");
		sb.append(String.valueOf(articleGroupId));
		sb.append("/");
		sb.append(articleId);
		%>

		<br /><a href="<%= sb.toString() %>"><%= themeDisplay.getPortalURL() %><%= StringUtil.shorten(sb.toString(), 100) %></a>

		</span>
	</c:otherwise>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal_content_search.article_content.jsp");
%>