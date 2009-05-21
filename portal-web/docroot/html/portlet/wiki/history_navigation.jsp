<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
double sourceVersion = (Double)renderRequest.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)renderRequest.getAttribute(WebKeys.TARGET_VERSION);
long nodeId = (Long)renderRequest.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)renderRequest.getAttribute(WebKeys.TITLE);

List<WikiPage> wikiPages = WikiPageLocalServiceUtil.getPages(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
List<WikiPage> intermediates = new ArrayList<WikiPage>();
double previousVersion = 0;
double nextVersion = 0;

for (WikiPage wikiPage: wikiPages) {
	if ((wikiPage.getVersion() < sourceVersion) && (wikiPage.getVersion() > previousVersion)) {
		previousVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > targetVersion) && ((wikiPage.getVersion() < nextVersion) || nextVersion == 0)) {
		nextVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > sourceVersion) && (wikiPage.getVersion() <= targetVersion)){
		intermediates.add(wikiPage);
	}
}

String sourceVersionString = (previousVersion != 0) ? String.valueOf(sourceVersion) : String.valueOf(sourceVersion) + " (" + LanguageUtil.get(pageContext, "first-version") +")";
String targetVersionString = (nextVersion != 0) ? String.valueOf(targetVersion) : String.valueOf(targetVersion) + " (" + LanguageUtil.get(pageContext, "last-version") +")";

boolean htmlMode = Validator.equals ("html", ParamUtil.getString(request, "type"));
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="changeMode">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="type" value='<%= htmlMode ? "text" : "html" %>' />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(sourceVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(targetVersion) %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="previousChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="type" value='<%= htmlMode ? "html" : "text" %>' />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(previousVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(sourceVersion) %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="nextChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="type" value='<%= htmlMode ? "html" : "text" %>' />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(targetVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(nextVersion) %>" />
</portlet:renderURL>

<div class="history-navigation">
	<c:choose>
		<c:when test="<%= previousVersion != 0 %>">
			<a class="previous" href="<%= previousChange %>">
		</c:when>
		<c:otherwise>
			<span class="previous">
		</c:otherwise>
	</c:choose>

	<liferay-ui:message key="previous-change" />

	<c:choose>
		<c:when test="<%= previousVersion != 0 %>">
			</a>
		</c:when>
		<c:otherwise>
			</span>
		</c:otherwise>
	</c:choose>

	<div class="central-info">
		<liferay-ui:icon cssClass="central-title" image="pages" label="true" message='<%= LanguageUtil.format(pageContext, "comparing-versions-x-and-x", new Object[]{sourceVersionString, targetVersionString}) %>' />

			<c:choose>
				<c:when test="<%= !htmlMode %>">
					<a class="change-mode" href="<%= changeMode %>">
				</c:when>
				<c:otherwise>
					<span class="change-mode">
				</c:otherwise>
			</c:choose>

			<liferay-ui:message key="html-mode" />

			<c:choose>
				<c:when test="<%= !htmlMode %>">
					</a>
				</c:when>
				<c:otherwise>
					</span>
				</c:otherwise>
			</c:choose>

			<%= StringPool.PIPE %>

			<c:choose>
				<c:when test="<%= htmlMode %>">
					<a class="change-mode" href="<%= changeMode %>">
				</c:when>
				<c:otherwise>
					<span class="change-mode">
				</c:otherwise>
			</c:choose>

			<liferay-ui:message key="text-mode" />

			<c:choose>
				<c:when test="<%= htmlMode %>">
					</a>
				</c:when>
				<c:otherwise>
					</span>
				</c:otherwise>
			</c:choose>

		<div class="central-author">
			<c:choose>
				<c:when test="<%= intermediates.size() > 1%>">

					<%
					StringBuilder sb = new StringBuilder();

					for (WikiPage wikipage: intermediates) {
						sb.append(wikipage.getUserName());
						sb.append(StringPool.SPACE);
						sb.append(StringPool.OPEN_PARENTHESIS);
						sb.append(wikipage.getVersion());
						sb.append(StringPool.CLOSE_PARENTHESIS);
						sb.append(StringPool.COMMA);
						sb.append(StringPool.SPACE);
					}

					sb.deleteCharAt(sb.lastIndexOf(StringPool.COMMA));
					%>

					<liferay-ui:icon image="user_icon" toolTip="authors" label="true" message="<%= sb.toString() %>"></liferay-ui:icon>
				</c:when>
				<c:otherwise>

					<%
					WikiPage wikiPage = intermediates.get(0);
					%>

					<liferay-ui:icon cssClass="central-username" image="user_icon" toolTip="author" label="true" message="<%= wikiPage.getUserName() %>"></liferay-ui:icon>

					<c:if test="<%= Validator.isNotNull(wikiPage.getSummary()) %>">
						<%= StringPool.COLON + StringPool.SPACE + wikiPage.getSummary() %>
					</c:if>

					<c:if test="<%= wikiPage.isMinorEdit() %>">
						<%= StringPool.OPEN_PARENTHESIS + LanguageUtil.get(pageContext, "minor-edit") + StringPool.CLOSE_PARENTHESIS %>
					</c:if>
				</c:otherwise>
			</c:choose>
		</div>

	</div>

	<c:choose>
		<c:when test="<%= nextVersion != 0 %>">
			<a class="next" href="<%= nextChange %>">
		</c:when>
		<c:otherwise>
			<span class="next">
		</c:otherwise>
	</c:choose>

	<liferay-ui:message key="next-change" />

	<c:choose>
		<c:when test="<%= nextVersion != 0 %>">
			</a>
		</c:when>
		<c:otherwise>
			</span>
		</c:otherwise>
	</c:choose>
</div>