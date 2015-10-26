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
JournalArticle article = journalContentDisplayContext.getArticle();
JournalArticleDisplay articleDisplay = journalContentDisplayContext.getArticleDisplay();

journalContentDisplayContext.incrementViewCounter();

AssetRendererFactory<JournalArticle> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(JournalArticle.class);
%>

<c:choose>
	<c:when test="<%= article == null %>">
		<c:choose>
			<c:when test="<%= Validator.isNull(journalContentDisplayContext.getArticleId()) %>">
				<div class="alert alert-info">
					<liferay-ui:message key="select-existing-web-content-or-add-some-web-content-to-be-displayed-in-this-portlet" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-danger">
					<liferay-ui:message key="the-selected-web-content-no-longer-exists" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test="<%= !journalContentDisplayContext.hasViewPermission() %>">
				<div class="alert alert-danger">
					<liferay-ui:message key="you-do-not-have-the-roles-required-to-access-this-web-content-entry" />
				</div>
			</c:when>
			<c:when test="<%= Validator.isNotNull(journalContentDisplayContext.getArticleId()) %>">
				<c:choose>
					<c:when test="<%= journalContentDisplayContext.isExpired() %>">
						<div class="alert alert-warning">
							<liferay-ui:message arguments="<%= HtmlUtil.escape(article.getTitle(locale)) %>" key="x-is-expired" />
						</div>
					</c:when>
					<c:when test="<%= article.isScheduled() %>">
						<div class="alert alert-warning">
							<liferay-ui:message arguments="<%= new Object[] {HtmlUtil.escape(article.getTitle(locale)), dateFormatDateTime.format(article.getDisplayDate())} %>" key="x-is-scheduled-and-will-be-displayed-on-x" />
						</div>
					</c:when>
					<c:when test="<%= !article.isApproved() %>">

						<%
							AssetRenderer<JournalArticle> assetRenderer = assetRendererFactory.getAssetRenderer(article.getResourcePrimKey());
						%>

						<c:choose>
							<c:when test="<%= assetRenderer.hasEditPermission(permissionChecker) %>">
								<div class="alert alert-warning">
									<a href="<%= assetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse, WindowState.MAXIMIZED, currentURLObj) %>">
										<liferay-ui:message arguments="<%= HtmlUtil.escape(article.getTitle(locale)) %>" key="x-is-not-approved" />
									</a>
								</div>
							</c:when>
							<c:otherwise>
								<div class="alert alert-warning">
									<liferay-ui:message arguments="<%= HtmlUtil.escape(article.getTitle(locale)) %>" key="x-is-not-approved" />
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= (articleDisplay != null) %>">
						<div class="text-right user-tool-asset-addon-entries">
							<liferay-ui:asset-addon-entry-display assetAddonEntries="<%= journalContentDisplayContext.getSelectedUserToolAssetAddonEntries() %>" />
						</div>

						<div class="journal-content-article">
							<%= articleDisplay.getContent() %>
						</div>

						<c:if test="<%= articleDisplay.isPaginate() %>">

							<%
							PortletURL portletURL = renderResponse.createRenderURL();
							%>

							<liferay-ui:page-iterator
								cur="<%= articleDisplay.getCurrentPage() %>"
								curParam='<%= "page" %>'
								delta="<%= 1 %>"
								id="articleDisplayPages"
								maxPages="<%= 25 %>"
								total="<%= articleDisplay.getNumberOfPages() %>"
								type="article"
								url="<%= portletURL.toString() %>"
							/>

							<br />
						</c:if>
					</c:when>
				</c:choose>
			</c:when>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= journalContentDisplayContext.isShowIconsActions() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<liferay-portlet:renderURL varImpl="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcPath" value="/update_journal_article_redirect.jsp" />
				<portlet:param name="referringPortletResource" value="<%= portletDisplay.getId() %>" />
			</liferay-portlet:renderURL>

			<c:if test="<%= journalContentDisplayContext.isShowEditArticleIcon() %>">

				<%
				JournalArticle latestArticle = journalContentDisplayContext.getLatestArticle();

				AssetRenderer<JournalArticle> latestArticleAssetRenderer = assetRendererFactory.getAssetRenderer(latestArticle.getResourcePrimKey());

				PortletURL latestArticleEditURL = latestArticleAssetRenderer.getURLEdit(liferayPortletRequest, liferayPortletResponse, LiferayWindowState.POP_UP, redirectURL);

				latestArticleEditURL.setParameter("showHeader", Boolean.FALSE.toString());

				String taglibEditArticleURL = "javascript:Liferay.Util.openWindow({dialog: {destroyOnHide: true}, id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + HtmlUtil.escapeJS(HtmlUtil.escape(latestArticle.getTitle(locale))) + "', uri:'" + HtmlUtil.escapeJS(latestArticleEditURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-pencil"
					label="<%= true %>"
					message="edit"
					url="<%= taglibEditArticleURL %>"
				/>
			</c:if>

			<c:if test="<%= journalContentDisplayContext.isShowEditTemplateIcon() %>">

				<%
				DDMTemplate ddmTemplate = journalContentDisplayContext.getDDMTemplate();
				%>

				<liferay-portlet:renderURL portletName="<%= PortletProviderUtil.getPortletId(DDMTemplate.class.getName(), PortletProvider.Action.EDIT) %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcPath" value="/edit_template.jsp" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletProviderUtil.getPortletId(JournalArticle.class.getName(), PortletProvider.Action.EDIT) %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(ddmTemplate.getGroupId()) %>" />
					<portlet:param name="templateId" value="<%= String.valueOf(ddmTemplate.getTemplateId()) %>" />
					<portlet:param name="showCacheableInput" value="<%= Boolean.TRUE.toString() %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibEditTemplateURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + HtmlUtil.escapeJS(HtmlUtil.escape(ddmTemplate.getName(locale))) + "', uri:'" + HtmlUtil.escapeJS(editTemplateURL.toString()) + "'});";
				%>

				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-edit"
					label="<%= true %>"
					message="edit-template"
					url="<%= taglibEditTemplateURL %>"
				/>
			</c:if>

			<c:if test="<%= journalContentDisplayContext.isShowSelectArticleIcon() %>">
				<liferay-ui:icon
					cssClass="lfr-icon-action"
					iconCssClass="icon-cog"
					label="<%= true %>"
					message="select-web-content"
					method="get"
					onClick="<%= portletDisplay.getURLConfigurationJS() %>"
					url="<%= portletDisplay.getURLConfiguration() %>"
				/>
			</c:if>

			<c:if test="<%= journalContentDisplayContext.isShowAddArticleIcon() %>">
				<liferay-ui:icon-menu
					cssClass="lfr-icon-action lfr-icon-action-add"
					direction="down"
					extended="<%= false %>"
					icon="../aui/plus"
					message="add"
					showArrow="<%= false %>"
					showWhenSingleIcon="<%= true %>"
				>

					<%
					PortletURL addArticleURL = assetRendererFactory.getURLAdd(liferayPortletRequest, liferayPortletResponse, 0);

					addArticleURL.setParameter("redirect", redirectURL.toString());
					addArticleURL.setParameter("showHeader", Boolean.FALSE.toString());
					addArticleURL.setParameter("portletResource", portletDisplay.getId());
					addArticleURL.setParameter("groupId", String.valueOf(scopeGroupId));

					addArticleURL.setWindowState(LiferayWindowState.POP_UP);

					List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(company.getCompanyId(), PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), PortalUtil.getClassNameId(JournalArticle.class), WorkflowConstants.STATUS_APPROVED);

					for (DDMStructure ddmStructure : ddmStructures) {
						addArticleURL.setParameter("ddmStructureId", String.valueOf(ddmStructure.getStructureId()));
					%>

						<%
						String taglibAddArticleURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format(request, "new-x", ddmStructure.getName(locale))) + "', uri:'" + HtmlUtil.escapeJS(addArticleURL.toString()) + "'});";
						%>

						<liferay-ui:icon
							cssClass="lfr-icon-action lfr-icon-action-add"
							iconCssClass="<%= assetRendererFactory.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= ddmStructure.getName(locale) %>"
							url="<%= taglibAddArticleURL %>"
						/>

					<%
					}
					%>

				</liferay-ui:icon-menu>
			</c:if>
		</div>
	</div>
</c:if>

<c:if test="<%= (articleDisplay != null) && journalContentDisplayContext.hasViewPermission() %>">
	<div class="content-metadata-asset-addon-entries">
		<liferay-ui:asset-addon-entry-display assetAddonEntries="<%= journalContentDisplayContext.getSelectedContentMetadataAssetAddonEntries() %>" />
	</div>
</c:if>