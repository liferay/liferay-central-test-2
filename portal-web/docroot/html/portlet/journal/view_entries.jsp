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

<%@ include file="/html/portlet/journal/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.JOURNAL, "display-style", PropsValues.JOURNAL_DEFAULT_DISPLAY_VIEW);
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(request, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(displayViews, displayStyle)) {
		portalPreferences.setValue(PortletKeys.JOURNAL, "display-style", displayStyle);
	}
}

if (!ArrayUtil.contains(displayViews, displayStyle)) {
	displayStyle = displayViews[0];
}

long ddmStructureId = 0;

String ddmStructureName = LanguageUtil.get(pageContext, "basic-web-content");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/view");

int entryStart = ParamUtil.getInteger(request, "entryStart");
int entryEnd = ParamUtil.getInteger(request, "entryEnd", SearchContainer.DEFAULT_DELTA);

ArticleSearch searchContainer = new ArticleSearch(liferayPortletRequest, entryEnd / (entryEnd - entryStart), entryEnd - entryStart, portletURL);

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNull(orderByCol)) {
	orderByCol = portalPreferences.getValue(PortletKeys.JOURNAL, "order-by-col", "modified-date");
	orderByType = portalPreferences.getValue(PortletKeys.JOURNAL, "order-by-type", "asc");
}
else {
	boolean saveOrderBy = ParamUtil.getBoolean(request, "saveOrderBy");

	if (saveOrderBy) {
		portalPreferences.setValue(PortletKeys.JOURNAL, "order-by-col", orderByCol);
		portalPreferences.setValue(PortletKeys.JOURNAL, "order-by-type", orderByType);
	}
}

OrderByComparator orderByComparator = JournalUtil.getArticleOrderByComparator(orderByCol, orderByType);

searchContainer.setOrderByCol(orderByCol);
searchContainer.setOrderByComparator(orderByComparator);
searchContainer.setOrderByJS("javascript:" + liferayPortletResponse.getNamespace() + "sortEntries('" + folderId + "', 'orderKey', 'orderByType');");
searchContainer.setOrderByType(orderByType);

EntriesChecker entriesChecker = new EntriesChecker(liferayPortletRequest, liferayPortletResponse);

entriesChecker.setCssClass("entry-selector");

searchContainer.setRowChecker(entriesChecker);

ArticleDisplayTerms displayTerms = (ArticleDisplayTerms)searchContainer.getDisplayTerms();
%>

<c:if test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
	<aui:input name="<%= displayTerms.STRUCTURE_ID %>" type="hidden" value="<%= displayTerms.getStructureId() %>" />

	<%
	try {
		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(themeDisplay.getSiteGroupId(), PortalUtil.getClassNameId(JournalArticle.class), displayTerms.getStructureId(), true);

		ddmStructureId = ddmStructure.getStructureId();

		ddmStructureName = ddmStructure.getName(locale);
	}
	catch (NoSuchStructureException nsse) {
	}
	%>

</c:if>

<c:if test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">
	<aui:input name="<%= displayTerms.TEMPLATE_ID %>" type="hidden" value="<%= displayTerms.getTemplateId() %>" />
</c:if>

<c:if test="<%= portletName.equals(PortletKeys.JOURNAL) && !((themeDisplay.getScopeGroupId() == themeDisplay.getCompanyGroupId()) && (Validator.isNotNull(displayTerms.getStructureId()) || Validator.isNotNull(displayTerms.getTemplateId()))) %>">
	<aui:input name="groupId" type="hidden" />
</c:if>

<%
ArticleSearchTerms searchTerms = (ArticleSearchTerms)searchContainer.getSearchTerms();

if (folderId != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	List<Long> folderIds = new ArrayList<Long>(1);

	folderIds.add(folderId);

	searchTerms.setFolderIds(folderIds);
}
else {
	searchTerms.setFolderIds(new ArrayList<Long>());
}

if (Validator.isNotNull(displayTerms.getStructureId())) {
	searchTerms.setStructureId(displayTerms.getStructureId());
}

searchTerms.setVersion(-1);

if (displayTerms.isNavigationRecent()) {
	searchContainer.setOrderByCol("create-date");
	searchContainer.setOrderByType(orderByType);
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

List results = null;
int total = 0;
%>

<c:choose>
	<c:when test='<%= displayTerms.getNavigation().equals("mine") || displayTerms.isNavigationRecent() %>'>

		<%
		long userId = 0;

		if (displayTerms.getNavigation().equals("mine")) {
			userId = themeDisplay.getUserId();

			status = WorkflowConstants.STATUS_ANY;
		}

		total = JournalArticleServiceUtil.getGroupArticlesCount(scopeGroupId, userId, folderId, status);

		searchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getGroupArticles(scopeGroupId, userId, folderId, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">

		<%
		total = JournalArticleServiceUtil.getArticlesCountByStructureId(displayTerms.getGroupId(), searchTerms.getStructureId());

		searchContainer.setTotal(total);

		results = JournalArticleServiceUtil.getArticlesByStructureId(displayTerms.getGroupId(), displayTerms.getStructureId(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:when test="<%= Validator.isNotNull(displayTerms.getTemplateId()) %>">

		<%
		total = JournalArticleServiceUtil.searchCount(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderIds(), JournalArticleConstants.CLASSNAME_ID_DEFAULT, searchTerms.getKeywords(), searchTerms.getVersionObj(), null, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatus(), searchTerms.getReviewDate());

		searchContainer.setTotal(total);

		results = JournalArticleServiceUtil.search(company.getCompanyId(), searchTerms.getGroupId(), searchTerms.getFolderIds(), JournalArticleConstants.CLASSNAME_ID_DEFAULT, searchTerms.getKeywords(), searchTerms.getVersionObj(), null, searchTerms.getStructureId(), searchTerms.getTemplateId(), searchTerms.getDisplayDateGT(), searchTerms.getDisplayDateLT(), searchTerms.getStatus(), searchTerms.getReviewDate(), searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		%>

	</c:when>
	<c:otherwise>

		<%
		total = JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, folderId, status);

		searchContainer.setTotal(total);

		results = JournalFolderServiceUtil.getFoldersAndArticles(scopeGroupId, folderId, status, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator());
		%>

	</c:otherwise>
</c:choose>

<%
searchContainer.setResults(results);

request.setAttribute("view.jsp-total", String.valueOf(total));

request.setAttribute("view_entries.jsp-entryStart", String.valueOf(searchContainer.getStart()));
request.setAttribute("view_entries.jsp-entryEnd", String.valueOf(searchContainer.getEnd()));
%>

<div class="subscribe-action">
	<c:if test="<%= JournalPermission.contains(permissionChecker, scopeGroupId, ActionKeys.SUBSCRIBE) && JournalUtil.getEmailArticleAnyEventEnabled(portletPreferences) %>">

		<%
		boolean subscribed = false;
		boolean unsubscribable = true;

		if (Validator.isNull(displayTerms.getStructureId())) {
			subscribed = JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId);

			if (subscribed) {
				if (!JournalUtil.isSubscribedToFolder(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), folderId, false)) {
					unsubscribable = false;
				}
			}
		}
		else {
			subscribed = JournalUtil.isSubscribedToStructure(themeDisplay.getCompanyId(), scopeGroupId, user.getUserId(), ddmStructureId);
		}
		%>

		<c:choose>
			<c:when test="<%= subscribed %>">
				<c:choose>
					<c:when test="<%= unsubscribable %>">
						<portlet:actionURL var="unsubscribeURL">
							<portlet:param name="struts_action" value='<%= Validator.isNull(displayTerms.getStructureId()) ? "/journal/edit_folder" : "/journal/edit_article" %>' />
							<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
							<portlet:param name="redirect" value="<%= currentURL %>" />

							<c:choose>
								<c:when test="<%= Validator.isNull(displayTerms.getStructureId()) %>">
									<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
								</c:when>
								<c:otherwise>
									<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
								</c:otherwise>
							</c:choose>
						</portlet:actionURL>

						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="unsubscribe"
							url="<%= unsubscribeURL %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:icon
							iconCssClass="icon-remove-sign"
							label="<%= true %>"
							message="subscribed-to-a-parent-folder"
						/>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value='<%= Validator.isNull(displayTerms.getStructureId()) ? "/journal/edit_folder" : "/journal/edit_article" %>' />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />

					<c:choose>
						<c:when test="<%= Validator.isNull(displayTerms.getStructureId()) %>">
							<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
						</c:when>
						<c:otherwise>
							<portlet:param name="ddmStructureId" value="<%= String.valueOf(ddmStructureId) %>" />
						</c:otherwise>
					</c:choose>
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					label="<%= true %>"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>

<c:if test="<%= results.isEmpty() %>">
	<div class="alert alert-info entries-empty">
		<c:choose>
			<c:when test="<%= Validator.isNotNull(displayTerms.getStructureId()) %>">
				<c:if test="<%= total == 0 %>">
					<liferay-ui:message arguments="<%= HtmlUtil.escape(ddmStructureName) %>" key="there-is-no-web-content-with-structure-x" translateArguments="<%= false %>" />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="<%= total == 0 %>">
					<liferay-ui:message key="no-web-content-was-found" />
				</c:if>
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:choose>
	<c:when test='<%= !displayStyle.equals("list") %>'>

		<%
		for (int i = 0; i < results.size(); i++) {
			Object result = results.get(i);
		%>

			<%@ include file="/html/portlet/journal/cast_result.jspf" %>

			<c:choose>
				<c:when test="<%= curArticle != null %>">

					<%
					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/journal/edit_article");
					tempRowURL.setParameter("redirect", currentURL);
					tempRowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
					tempRowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
					tempRowURL.setParameter("articleId", curArticle.getArticleId());

					tempRowURL.setParameter("status", String.valueOf(status));

					request.setAttribute("view_entries.jsp-article", curArticle);

					request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("icon") %>'>
							<liferay-util:include page="/html/portlet/journal/view_article_icon.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/journal/view_article_descriptive.jsp" />
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="<%= curFolder != null %>">

					<%
					String folderImage = "folder_empty_article";

					if (JournalFolderServiceUtil.getFoldersAndArticlesCount(scopeGroupId, curFolder.getFolderId()) > 0) {
						folderImage = "folder_full_article";
					}

					PortletURL tempRowURL = liferayPortletResponse.createRenderURL();

					tempRowURL.setParameter("struts_action", "/journal/view");
					tempRowURL.setParameter("redirect", currentURL);
					tempRowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
					tempRowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));

					request.setAttribute("view_entries.jsp-folder", curFolder);

					request.setAttribute("view_entries.jsp-folderImage", folderImage);

					request.setAttribute("view_entries.jsp-tempRowURL", tempRowURL);
					%>

					<c:choose>
						<c:when test='<%= displayStyle.equals("icon") %>'>
							<liferay-util:include page="/html/portlet/journal/view_folder_icon.jsp" />
						</c:when>
						<c:otherwise>
							<liferay-util:include page="/html/portlet/journal/view_folder_descriptive.jsp" />
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>

		<%
		}
		%>

	</c:when>
	<c:otherwise>

		<%
		for (int i = 0; i < results.size(); i++) {
			Object result = results.get(i);
		%>

			<%@ include file="/html/portlet/journal/cast_result.jspf" %>

			<c:choose>
				<c:when test="<%= curArticle != null %>">
					<liferay-util:buffer var="articleTitle">

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

						AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(JournalArticleAssetRenderer.getClassPK(curArticle));

						PortletURL rowURL = liferayPortletResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/journal/edit_article");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("groupId", String.valueOf(curArticle.getGroupId()));
						rowURL.setParameter("folderId", String.valueOf(curArticle.getFolderId()));
						rowURL.setParameter("articleId", curArticle.getArticleId());

						rowURL.setParameter("status", String.valueOf(status));
						%>

						<liferay-ui:icon
							cssClass="entry-display-style selectable"
							iconCssClass="<%= assetRenderer.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= curArticle.getTitle(locale) %>"
							method="get"
							url="<%= rowURL.toString() %>"
						/>

						<c:if test="<%= curArticle.getGroupId() != scopeGroupId %>">
							<small class="group-info">
								<dl>

									<%
									Group group = GroupLocalServiceUtil.getGroup(curArticle.getGroupId());
									%>

									<c:if test="<%= !group.isLayout() || (group.getParentGroupId() != scopeGroupId) %>">
										<dt>
											<liferay-ui:message key="site" />:
										</dt>

										<dd>

											<%
											String groupDescriptiveName = null;

											if (group.isLayout()) {
												Group parentGroup = group.getParentGroup();

												groupDescriptiveName = parentGroup.getDescriptiveName(locale);
											}
											else {
												groupDescriptiveName = group.getDescriptiveName(locale);
											}
											%>

											<%= HtmlUtil.escape(groupDescriptiveName) %>
										</dd>
									</c:if>

									<c:if test="<%= group.isLayout() %>">
										<dt>
											<liferay-ui:message key="scope" />:
										</dt>

										<dd>
											<%= HtmlUtil.escape(group.getDescriptiveName(locale)) %>
										</dd>
									</c:if>
								</dl>
							</small>
						</c:if>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(curArticle, curArticle.getArticleId(), i);

					row.setClassName("entry-display-style");

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("draggable", JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.DELETE) || JournalArticlePermission.contains(permissionChecker, curArticle, ActionKeys.UPDATE));
					data.put("title", curArticle.getTitle(locale));

					row.setData(data);
					%>

					<%@ include file="/html/portlet/journal/article_columns.jspf" %>

					<%

					// Add result row

					resultRows.add(row);
					%>

				</c:when>
				<c:when test="<%= curFolder != null %>">
					<liferay-util:buffer var="folderTitle">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("folder", true);
						data.put("folder-id", curFolder.getFolderId());

						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalFolder.class.getName());

						AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());

						PortletURL rowURL = liferayPortletResponse.createRenderURL();

						rowURL.setParameter("struts_action", "/journal/view");
						rowURL.setParameter("redirect", currentURL);
						rowURL.setParameter("groupId", String.valueOf(curFolder.getGroupId()));
						rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
						%>

						<liferay-ui:icon
							data="<%= data %>"
							iconCssClass="<%= assetRenderer.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= curFolder.getName() %>"
							method="get"
							url="<%= rowURL.toString() %>"
						/>
					</liferay-util:buffer>

					<%
					List resultRows = searchContainer.getResultRows();

					ResultRow row = new ResultRow(curFolder, curFolder.getPrimaryKey(), i);

					row.setClassName("entry-display-style");

					Map<String, Object> data = new HashMap<String, Object>();

					data.put("draggable", JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.DELETE) || JournalFolderPermission.contains(permissionChecker, curFolder, ActionKeys.UPDATE));
					data.put("folder", true);
					data.put("folder-id", curFolder.getFolderId());
					data.put("title", curFolder.getName());

					row.setData(data);
					%>

					<%@ include file="/html/portlet/journal/folder_columns.jspf" %>

					<%

					// Add result row

					resultRows.add(row);
					%>
				</c:when>
			</c:choose>

		<%
		}
		%>

		<liferay-ui:search-iterator paginate="<%= false %>" searchContainer="<%= searchContainer %>" />
	</c:otherwise>
</c:choose>

<aui:script>
	Liferay.fire(
		'<portlet:namespace />pageLoaded',
		{
			pagination: {
				name: 'entryPagination',
				state: {
					page: <%= (total == 0) ? 0 : searchContainer.getCur() %>,
					rowsPerPage: <%= searchContainer.getDelta() %>,
					total: <%= total %>
				}
			}
		}
	);
</aui:script>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.journal.view_entries_jsp");
%>