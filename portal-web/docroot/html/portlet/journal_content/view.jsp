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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
String content = (String)request.getAttribute(WebKeys.JOURNAL_ARTICLE_CONTENT);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(content) %>">

		<%
		RuntimeLogic portletLogic = new PortletLogic(application, request, response, renderRequest, renderResponse);
		RuntimeLogic actionURLLogic = new ActionURLLogic(renderResponse);
		RuntimeLogic renderURLLogic = new RenderURLLogic(renderResponse);

		content = RuntimePortletUtil.processXML(request, content, portletLogic);
		content = RuntimePortletUtil.processXML(request, content, actionURLLogic);
		content = RuntimePortletUtil.processXML(request, content, renderURLLogic);
		%>

		<%= content %>

	</c:when>
	<c:when test="<%= portletDisplay.isShowConfigurationIcon() %>">
		<br />

		<liferay-ui:message key="select-an-existing-article-or-add-an-article-to-be-displayed-in-this-portlet" />

		<br />

		<c:if test="<%= Validator.isNotNull(articleId) %>">
			<br />

			<span class="portlet-msg-error">
			<%= LanguageUtil.format(pageContext, "x-is-expired,-is-not-approved,-does-not-have-any-content,-or-no-longer-exists", articleId) %>
			</span>

			<br />
		</c:if>
	</c:when>
</c:choose>

<%
JournalArticle article = null;

try {
	article = JournalArticleLocalServiceUtil.getLatestArticle(groupId, articleId);
}
catch (NoSuchArticleException nsae) {
}
%>

<c:if test="<%= themeDisplay.isSignedIn() %>">
	<br />

	<c:if test="<%= article != null %>">
		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
			<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="editURL" portletName="<%= PortletKeys.JOURNAL %>">
				<liferay-portlet:param name="struts_action" value="/journal/edit_article" />
				<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
				<liferay-portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
				<liferay-portlet:param name="articleId" value="<%= article.getArticleId() %>" />
				<liferay-portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon image="edit" message="edit-article" url="<%= editURL %>" />
		</c:if>
	</c:if>

	<c:if test="<%= PortletPermission.contains(permissionChecker, plid.longValue(), PortletKeys.JOURNAL, ActionKeys.CONFIGURATION) %>">
		<liferay-ui:icon image="configuration" message="select-article" url="<%= portletDisplay.getURLConfiguration() %>" />
	</c:if>

	<c:if test="<%= PortletPermission.contains(permissionChecker, plid.longValue(), PortletKeys.JOURNAL, ActionKeys.ADD_ARTICLE) %>">
		<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="addArticleURL" portletName="<%= PortletKeys.JOURNAL %>">
			<liferay-portlet:param name="struts_action" value="/journal/edit_article" />
			<liferay-portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
			<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon image="add_article" message="add-article" url="<%= addArticleURL %>" />
	</c:if>
</c:if>

<c:if test="<%= article != null %>">
	<br />

	<c:if test="<%= enableRatings %>">
		<br />

		<liferay-ui:ratings
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= article.getResourcePrimKey() %>"
			url='<%= themeDisplay.getPathMain() + "/journal_content/rate_article" %>'
		/>
	</c:if>

	<c:if test="<%= enableComments %>">
		<br />

		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/journal_content/edit_article_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			formAction="<%= discussionURL %>"
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= article.getResourcePrimKey() %>"
			userId="<%= article.getUserId() %>"
			subject="<%= article.getTitle() %>"
			redirect="<%= currentURL %>"
		/>
	</c:if>
</c:if>