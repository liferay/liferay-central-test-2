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
	<c:otherwise>
		<%= LanguageUtil.get(pageContext, "please-contact-the-administrator-to-setup-this-portlet") %>

		<br><br>

		<%= LanguageUtil.get(pageContext, "select-an-existing-article-or-add-an-article-to-be-displayed-in-this-portlet") %>

		<br>

		<c:if test="<%= Validator.isNotNull(articleId) %>">
			<br>

			<span class="portlet-msg-error">
			<%= LanguageUtil.format(pageContext, "x-is-not-approved,-does-not-have-any-content,-or-no-longer-exists", articleId) %>
			</span>

			<br>
		</c:if>
	</c:otherwise>
</c:choose>

<c:if test="<%= themeDisplay.isSignedIn() %>">
	<br>

	<c:if test="<%= PortletPermission.contains(permissionChecker, plid, PortletKeys.JOURNAL, ActionKeys.CONFIGURATION) %>">
		<liferay-ui:icon image="configuration" message="select-article" url="<%= portletDisplay.getURLConfiguration() %>" />
	</c:if>

	<%
	try {
		JournalArticle article = JournalArticleLocalServiceUtil.getLatestArticle(company.getCompanyId(), articleId);
	%>

		<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.UPDATE) %>">
			<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletName="<%= PortletKeys.JOURNAL %>">
				<liferay-portlet:param name="struts_action" value="/journal/edit_article" />
				<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
				<liferay-portlet:param name="articleId" value="<%= article.getArticleId() %>" />
				<liferay-portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon image="edit" message="edit-article" url="<%= portletURL %>" />
		</c:if>

	<%
	}
	catch (NoSuchArticleException nsae) {
	}
	%>

	<c:if test="<%= PortletPermission.contains(permissionChecker, plid, PortletKeys.JOURNAL, ActionKeys.ADD_ARTICLE) %>">
		<liferay-portlet:renderURL windowState="<%= WindowState.MAXIMIZED.toString() %>" var="portletURL" portletName="<%= PortletKeys.JOURNAL %>">
			<liferay-portlet:param name="struts_action" value="/journal/edit_article" />
			<liferay-portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
			<liferay-portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon image="add_article" message="add-article" url="<%= portletURL %>" />
	</c:if>
</c:if>