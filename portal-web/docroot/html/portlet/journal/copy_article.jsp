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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long groupId = ParamUtil.getLong(request, "groupId");
String oldArticleId = ParamUtil.getString(request, "oldArticleId");
String newArticleId = ParamUtil.getString(request, "newArticleId");
double version = ParamUtil.getDouble(request, "version");
%>

<form action="<portlet:actionURL windowState="<%= WindowState.MAXIMIZED.toString() %>"><portlet:param name="struts_action" value="/journal/copy_article" /></portlet:actionURL>" method="post" name="<portlet:namespace />fm">
<input name="<portlet:namespace /><%= Constants.CMD %>" type="hidden" value="<%= Constants.COPY %>" />
<input name="<portlet:namespace />redirect" type="hidden" value="<%= HtmlUtil.escapeAttribute(redirect) %>" />
<input name="<portlet:namespace />groupId" type="hidden" value="<%= groupId %>" />
<input name="<portlet:namespace />oldArticleId" type="hidden" value="<%= HtmlUtil.escapeAttribute(oldArticleId) %>" />
<input name="<portlet:namespace />version" type="hidden" value="<%= version %>" />

<liferay-ui:tabs
	names="web-content"
	backURL="<%= PortalUtil.escapeRedirect(redirect) %>"
/>

<liferay-ui:error exception="<%= DuplicateArticleIdException.class %>" message="please-enter-a-unique-id" />
<liferay-ui:error exception="<%= ArticleIdException.class %>" message="please-enter-a-valid-id" />

<table class="lfr-table">
<tr>
	<td>
		<liferay-ui:message key="id" />
	</td>
	<td>
		<%= oldArticleId %>
	</td>
</tr>
<tr>
	<td>
		<liferay-ui:message key="new-id" />
	</td>
	<td>
		<c:choose>
			<c:when test="<%= PropsValues.JOURNAL_ARTICLE_FORCE_AUTOGENERATE_ID %>">
				<liferay-ui:message key="autogenerate-id" />

				<input name="<portlet:namespace />newArticleId" type="hidden" value="" />
				<input name="<portlet:namespace />autoArticleId" type="hidden" value="true" />
			</c:when>
			<c:otherwise>
				<liferay-ui:input-field model="<%= JournalArticle.class %>" bean="<%= null %>" field="articleId" fieldParam="newArticleId" defaultValue="<%= newArticleId %>" />
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>

<br />

<input type="submit" value="<liferay-ui:message key="copy" />" />

<input type="button" value="<liferay-ui:message key="cancel" />" onClick="location.href = '<%= HtmlUtil.escape(PortalUtil.escapeRedirect(redirect)) %>';" />

</form>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />newArticleId);
	</aui:script>
</c:if>