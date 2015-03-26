<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "web-content");

String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

JournalArticle article = ActionUtil.getArticle(request);
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<div class="alert alert-danger">
			<%= LanguageUtil.get(request, "the-selected-web-content-no-longer-exists") %>
		</div>
	</c:when>
	<c:otherwise>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "html/portlet/journal/view_article_history.jsp");
		portletURL.setParameter("tabs1", tabs1);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("referringPortletResource", referringPortletResource);
		portletURL.setParameter("groupId", String.valueOf(article.getGroupId()));
		portletURL.setParameter("articleId", article.getArticleId());
		%>

		<liferay-util:include page="/article_header.jsp" servletContext="<%= application %>" />

		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
			<aui:input name="groupId" type="hidden" />
			<aui:input name="articleId" type="hidden" value="<%= article.getArticleId() %>" />
			<aui:input name="articleIds" type="hidden" />
			<aui:input name="expireArticleIds" type="hidden" />

			<liferay-ui:search-container
				rowChecker="<%= new RowChecker(renderResponse) %>"
				searchContainer="<%= new ArticleSearch(renderRequest, portletURL) %>"
				total="<%= JournalArticleServiceUtil.getArticlesCountByArticleId(article.getGroupId(), article.getArticleId()) %>"
			>
				<liferay-ui:search-container-results
					results="<%= JournalArticleServiceUtil.getArticlesByArticleId(article.getGroupId(), article.getArticleId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.journal.model.JournalArticle"
					modelVar="articleVersion"
				>

					<%
					row.setPrimaryKey(articleVersion.getArticleId() + JournalPortlet.VERSION_SEPARATOR + articleVersion.getVersion());
					%>

					<liferay-ui:search-container-column-text
						name="id"
						value="<%= HtmlUtil.escape(articleVersion.getArticleId()) %>"
					/>

					<liferay-ui:search-container-column-text
						name="title"
						value="<%= HtmlUtil.escape(articleVersion.getTitle(locale)) %>"
					/>

					<liferay-ui:search-container-column-text
						name="version"
						orderable="<%= true %>"

					/>

					<liferay-ui:search-container-column-status
						name="status"
					/>

					<liferay-ui:search-container-column-date
						name="modified-date"
						orderable="<%= true %>"
						property="modifiedDate"
					/>

					<c:if test="<%= article.getDisplayDate() != null %>">
						<liferay-ui:search-container-column-date
							name="display-date"
							orderable="<%= true %>"
							property="displayDate"
						/>
					</c:if>

					<liferay-ui:search-container-column-text
						name="author"
						value="<%= PortalUtil.getUserName(articleVersion) %>"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action"
						path="/article_version_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<c:if test="<%= !results.isEmpty() %>">
					<aui:button-row>
						<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
							<aui:button disabled="<%= true %>" name="expire" onClick='<%= renderResponse.getNamespace() + "expireArticles();" %>' value="expire" />
						</c:if>

						<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
							<aui:button disabled="<%= true %>" name="delete" onClick='<%= renderResponse.getNamespace() + "deleteArticles();" %>' value="delete" />
						</c:if>
					</aui:button-row>
				</c:if>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</aui:form>

		<aui:script>
			Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />delete', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');
			Liferay.Util.toggleSearchContainerButton('#<portlet:namespace />expire', '#<portlet:namespace /><%= searchContainerReference.getId() %>SearchContainer', document.<portlet:namespace />fm, '<portlet:namespace />allRowIds');

			AUI.$('body').on(
				'click',
				'.compare-to-link a',
				function(event) {
					var currentTarget = AUI.$(event.currentTarget);

					Liferay.Util.selectEntity(
						{
							dialog: {
								constrain: true,
								destroyOnHide: true,
								modal: true
							},
							eventName: '<portlet:namespace />selectVersionFm',
							id: '<portlet:namespace />compareVersions' + currentTarget.attr('id'),
							title: '<liferay-ui:message key="compare-versions" />',
							uri: currentTarget.data('uri')
						},
						function(event) {
							<portlet:renderURL var="compareVersionURL">
								<portlet:param name="mvcPath" value="/compare_versions.jsp" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
								<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
							</portlet:renderURL>

							var uri = '<%= compareVersionURL %>';

							uri = Liferay.Util.addParams('<portlet:namespace />sourceVersion=' + event.sourceversion, uri);
							uri = Liferay.Util.addParams('<portlet:namespace />targetVersion=' + event.targetversion, uri);

							location.href = uri;
						}
					);
				}
			);

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.DELETE) %>">
				function <portlet:namespace />deleteArticles() {
					if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-version") %>')) {
						var form = AUI.$(document.<portlet:namespace />fm);

						form.fm('groupId').val('<%= String.valueOf(article.getGroupId()) %>');
						form.fm('articleId').val('');
						form.fm('articleIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

						submitForm(form, '<portlet:actionURL name="deleteArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
					}
				}
			</c:if>

			<c:if test="<%= JournalArticlePermission.contains(permissionChecker, article, ActionKeys.EXPIRE) %>">
				function <portlet:namespace />expireArticles() {
					if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-expire-the-selected-version") %>')) {
						var form = AUI.$(document.<portlet:namespace />fm);

						form.fm('groupId').val('<%= String.valueOf(article.getGroupId()) %>');
						form.fm('articleId').val('');
						form.fm('expireArticleIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

						submitForm(form, '<portlet:actionURL name="expireArticles"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>');
					}
				}
			</c:if>
		</aui:script>
	</c:otherwise>
</c:choose>