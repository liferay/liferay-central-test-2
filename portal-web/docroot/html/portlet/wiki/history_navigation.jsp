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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
long nodeId = (Long)request.getAttribute(WebKeys.WIKI_NODE_ID);
String title = (String)request.getAttribute(WebKeys.TITLE);
double sourceVersion = (Double)request.getAttribute(WebKeys.SOURCE_VERSION);
double targetVersion = (Double)request.getAttribute(WebKeys.TARGET_VERSION);

double previousVersion = 0;
double nextVersion = 0;

List<WikiPage> allPages = WikiPageLocalServiceUtil.getPages(nodeId, title, QueryUtil.ALL_POS, QueryUtil.ALL_POS, new PageVersionComparator());
List<WikiPage> intermediatePages = new ArrayList<WikiPage>();

for (WikiPage wikiPage : allPages) {
	if ((wikiPage.getVersion() < sourceVersion) &&
		(wikiPage.getVersion() > previousVersion)) {

		previousVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > targetVersion) &&
		((wikiPage.getVersion() < nextVersion) ||
		 (nextVersion == 0))) {

		nextVersion = wikiPage.getVersion();
	}

	if ((wikiPage.getVersion() > sourceVersion) &&
		(wikiPage.getVersion() <= targetVersion)) {

		intermediatePages.add(wikiPage);
	}
}

String sourceVersionString = (previousVersion != 0) ? String.valueOf(sourceVersion) : String.valueOf(sourceVersion) + " (" + LanguageUtil.get(pageContext, "first-version") + ")";
String targetVersionString = (nextVersion != 0) ? String.valueOf(targetVersion) : String.valueOf(targetVersion) + " (" + LanguageUtil.get(pageContext, "last-version") + ")";

String type = ParamUtil.getString(request, "type", "text");

boolean htmlMode = false;

if (type.equals("html")) {
	htmlMode = true;
}
%>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="changeMode">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(sourceVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(targetVersion) %>" />
	<portlet:param name="type" value='<%= htmlMode ? "text" : "html" %>' />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="previousChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(previousVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(sourceVersion) %>" />
	<portlet:param name="type" value="<%= type %>" />
</portlet:renderURL>

<portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="nextChange">
	<portlet:param name="struts_action" value="/wiki/compare_versions" />
	<portlet:param name="nodeId" value="<%= String.valueOf(nodeId) %>" />
	<portlet:param name="title" value="<%= title %>" />
	<portlet:param name="sourceVersion" value="<%= String.valueOf(targetVersion) %>" />
	<portlet:param name="targetVersion" value="<%= String.valueOf(nextVersion) %>" />
	<portlet:param name="type" value="<%= type %>" />
</portlet:renderURL>

<div class="history-navigation">
	<c:choose>
		<c:when test="<%= previousVersion != 0 %>">
			<aui:a cssClass="previous" href="<%= previousChange %>" label="previous-change" />
		</c:when>
		<c:otherwise>
			<span class="previous"><liferay-ui:message key="previous-change" /></span>
		</c:otherwise>
	</c:choose>

	<div class="central-info">
		<liferay-ui:icon
			image="pages"
			message='<%= LanguageUtil.format(pageContext, "comparing-versions-x-and-x", new Object[] {sourceVersionString, targetVersionString}) %>'
			label="<%= true %>"
			cssClass="central-title"
		/>

		<c:choose>
			<c:when test="<%= !htmlMode %>">
				<aui:a cssClass="change-mode" href="<%= changeMode %>" label="html-mode" />
			</c:when>
			<c:otherwise>
				<span class="change-mode"><liferay-ui:message key="html-mode" /></span>
			</c:otherwise>
		</c:choose>

		<%= StringPool.PIPE %>

		<c:choose>
			<c:when test="<%= htmlMode %>">
				<aui:a cssClass="change-mode" href="<%= changeMode %>" label="text-mode" />
			</c:when>
			<c:otherwise>
				<span class="change-mode"><liferay-ui:message key="text-mode" /></span>
			</c:otherwise>
		</c:choose>

		<div class="central-author">
			<c:choose>
				<c:when test="<%= intermediatePages.size() > 1%>">

					<%
					StringBuilder sb = new StringBuilder();

					for (WikiPage wikiPage: intermediatePages) {
						sb.append(wikiPage.getUserName());
						sb.append(StringPool.SPACE);
						sb.append(StringPool.OPEN_PARENTHESIS);
						sb.append(wikiPage.getVersion());
						sb.append(StringPool.CLOSE_PARENTHESIS);
						sb.append(StringPool.COMMA);
						sb.append(StringPool.SPACE);
					}

					sb.deleteCharAt(sb.lastIndexOf(StringPool.COMMA));
					%>

					<liferay-ui:icon
						image="user_icon"
						message="<%= sb.toString() %>"
						label="<%= true %>"
						toolTip="authors"
					/>
				</c:when>
				<c:otherwise>

					<%
					WikiPage wikiPage = intermediatePages.get(0);
					%>

					<liferay-ui:icon
						message="<%= wikiPage.getUserName() %>"
						image="user_icon"
						label="<%= true %>"
						toolTip="author"
						cssClass="central-username"
					/>

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
			<aui:a cssClass="next" href="<%= nextChange %>" label="next-change" />
		</c:when>
		<c:otherwise>
			<span class="next"><liferay-ui:message key="next-change" /></span>
		</c:otherwise>
	</c:choose>
</div>