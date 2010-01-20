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

<%@ include file="/html/portlet/asset_publisher/init.jsp" %>

<%
ArticleSearch searchContainer = (ArticleSearch)request.getAttribute("liferay-ui:search:searchContainer");

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();

String redirect = ParamUtil.getString(request, "backURL");

redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/portlet_configuration/edit_configuration");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("backURL", redirect);
portletURL.setParameter("portletResource", portletResource);
portletURL.setParameter("typeSelection", JournalArticle.class.getName());
%>

<liferay-ui:search-toggle
	id="toggle_id_journal_article_search"
	displayTerms="<%= displayTerms %>"
	buttonLabel="search-web-content"
>
	<table class="lfr-table">
	<tr>
		<td>
			<liferay-ui:message key="id" />
		</td>
		<td>
			<liferay-ui:message key="version" />
		</td>
		<td>
			<liferay-ui:message key="name" />
		</td>
		<td>
			<liferay-ui:message key="description" />
		</td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.ARTICLE_ID %>" size="20" type="text" value="<%= displayTerms.getArticleId() %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.VERSION %>" size="20" type="text" value="<%= displayTerms.getVersionString() %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.TITLE %>" size="20" type="text" value="<%= displayTerms.getTitle() %>" />
		</td>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.DESCRIPTION %>" size="20" type="text" value="<%= displayTerms.getDescription() %>" />
		</td>
	</tr>
	<tr>
		<td>
			<liferay-ui:message key="content" />
		</td>
		<td>
			<liferay-ui:message key="type" />
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="<%= portletName.equals(PortletKeys.JOURNAL) %>">
					<liferay-ui:message key="status" />
				</c:when>
				<c:otherwise>
					<liferay-ui:message key="community" />
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td>
			<input name="<portlet:namespace /><%= displayTerms.CONTENT %>" size="20" type="text" value="<%= displayTerms.getContent() %>" />
		</td>
		<td>
			<select name="<portlet:namespace /><%= displayTerms.TYPE %>">
				<option value=""></option>

				<%
				for (int i = 0; i < JournalArticleConstants.TYPES.length; i++) {
				%>

					<option <%= displayTerms.getType().equals(JournalArticleConstants.TYPES[i]) ? "selected" : "" %> value="<%= JournalArticleConstants.TYPES[i] %>"><%= LanguageUtil.get(pageContext, JournalArticleConstants.TYPES[i]) %></option>

				<%
				}
				%>

			</select>
		</td>
		<td colspan="2">
			<c:choose>
				<c:when test="<%= portletName.equals(PortletKeys.JOURNAL) %>">
					<select name="<portlet:namespace /><%= displayTerms.STATUS %>">
						<option value=""></option>
						<option <%= displayTerms.getStatus().equals("approved") ? "selected" : "" %> value="approved"><liferay-ui:message key="approved" /></option>
						<option <%= displayTerms.getStatus().equals("not-approved") ? "selected" : "" %> value="not-approved"><liferay-ui:message key="not-approved" /></option>
						<option <%= displayTerms.getStatus().equals("expired") ? "selected" : "" %> value="expired"><liferay-ui:message key="expired" /></option>
						<option <%= displayTerms.getStatus().equals("review") ? "selected" : "" %> value="review"><liferay-ui:message key="review" /></option>
					</select>
				</c:when>
				<c:otherwise>
					<select name="<portlet:namespace /><%= displayTerms.GROUP_ID %>">

						<%
						List<Group> myPlaces = user.getMyPlaces();

						for (Group myPlace : myPlaces) {
							if (myPlace.hasStagingGroup()) {
								myPlace = myPlace.getStagingGroup();
							}
						%>

							<option <%= displayTerms.getGroupId() == myPlace.getGroupId() ? "selected" : "" %> value="<%= myPlace.getGroupId() %>">
								<c:choose>
									<c:when test="<%= myPlace.isUser() %>">
										<liferay-ui:message key="my-community" />
									</c:when>
									<c:otherwise>
										<%= HtmlUtil.escape(myPlace.getDescriptiveName()) %>
									</c:otherwise>
								</c:choose>
							</option>

						<%
						}
						%>

					</select>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	</table>
</liferay-ui:search-toggle>

<aui:script>
	var <portlet:namespace />configurationActionURL = "";

	Liferay.Util.actsAsAspect(window);

	window.before(
		'<portlet:namespace />selectAsset',
		function() {
			var fm = AUI().one(document.<portlet:namespace />fm);

			if (fm) {
				fm.attr('action', <portlet:namespace />configurationActionURL);
			}
		}
	);

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.ARTICLE_ID %>);
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace /><%= displayTerms.KEYWORDS %>);
	</c:if>
</aui:script>

<aui:script use="node">
	var fm = A.one(document.<portlet:namespace />fm);

	if (fm) {
		<portlet:namespace />configurationActionURL = fm.attr('action');

		fm.attr('action', '<%= portletURL.toString() %>');
	}
</aui:script>