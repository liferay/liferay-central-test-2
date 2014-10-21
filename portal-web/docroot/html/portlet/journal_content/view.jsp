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

<%@ include file="/html/portlet/journal_content/init.jsp" %>

<%
JournalArticle article = journalContentDisplayContext.getArticle();
JournalArticleDisplay articleDisplay = journalContentDisplayContext.getArticleDisplay();

journalContentDisplayContext.incrementViewCounter();
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
			<c:otherwise>
				<c:choose>
					<c:when test="<%= (articleDisplay != null) && !journalContentDisplayContext.isExpired() %>">
						<c:if test="<%= journalContentDisplayContext.isEnableConversions() || journalContentDisplayContext.isEnablePrint() || (journalContentDisplayContext.isShowAvailableLocales() && (articleDisplay.getAvailableLocales().length > 1)) %>">
							<div class="user-actions">
								<c:if test="<%= journalContentDisplayContext.isEnablePrint() %>">
									<c:choose>
										<c:when test="<%= journalContentDisplayContext.isPrint() %>">
											<aui:script>
												print();
											</aui:script>
										</c:when>
										<c:otherwise>
											<portlet:renderURL var="printPageURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
												<portlet:param name="struts_action" value="/journal_content/view" />
												<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
												<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
												<portlet:param name="page" value="<%= String.valueOf(articleDisplay.getCurrentPage()) %>" />
												<portlet:param name="viewMode" value="<%= Constants.PRINT %>" />
											</portlet:renderURL>

											<div class="print-action">
												<liferay-ui:icon
													iconCssClass="icon-print"
													label="<%= true %>"
													message='<%= LanguageUtil.format(request, "print-x-x", new Object[] {"hide-accessible", HtmlUtil.escape(articleDisplay.getTitle())}, false) %>'
													url='<%= "javascript:" + renderResponse.getNamespace() + "printPage();" %>'
												/>
											</div>

											<aui:script>
												function <portlet:namespace />printPage() {
													window.open('<%= printPageURL %>', '', 'directories=0,height=480,left=80,location=1,menubar=1,resizable=1,scrollbars=yes,status=0,toolbar=0,top=180,width=640');
												}
											</aui:script>
										</c:otherwise>
									</c:choose>
								</c:if>

								<c:if test="<%= journalContentDisplayContext.isEnableConversions() && !journalContentDisplayContext.isPrint() %>">
									<div class="export-actions">
										<liferay-ui:icon-list>

											<%
											for (String extension : journalContentDisplayContext.getExtensions()) {
											%>

												<portlet:actionURL var="exportArticleURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
													<portlet:param name="struts_action" value="/journal_content/export_article" />
													<portlet:param name="groupId" value="<%= String.valueOf(articleDisplay.getGroupId()) %>" />
													<portlet:param name="articleId" value="<%= articleDisplay.getArticleId() %>" />
													<portlet:param name="targetExtension" value="<%= extension %>" />
												</portlet:actionURL>

												<liferay-ui:icon
													iconCssClass="<%= DLUtil.getFileIconCssClass(extension) %>"
													label="<%= true %>"
													message='<%= LanguageUtil.format(request, "x-convert-x-to-x", new Object[] {"hide-accessible", HtmlUtil.escape(articleDisplay.getTitle()), StringUtil.toUpperCase(HtmlUtil.escape(extension))}) %>'
													method="get"
													url="<%= exportArticleURL %>"
												/>

											<%
											}
											%>

										</liferay-ui:icon-list>
									</div>
								</c:if>

								<c:if test="<%= journalContentDisplayContext.isShowAvailableLocales() && !journalContentDisplayContext.isPrint() %>">

									<%
									String[] availableLocales = articleDisplay.getAvailableLocales();
									%>

									<c:if test="<%= availableLocales.length > 1 %>">
										<c:if test="<%= journalContentDisplayContext.isEnableConversions() || journalContentDisplayContext.isEnablePrint() %>">
											<div class="locale-separator"> </div>
										</c:if>

										<div class="locale-actions">
											<liferay-ui:language displayStyle="<%= 0 %>" formAction="<%= currentURL %>" languageId="<%= LanguageUtil.getLanguageId(request) %>" languageIds="<%= availableLocales %>" />
										</div>
									</c:if>
								</c:if>
							</div>
						</c:if>

						<div class="journal-content-article">
							<%= RuntimePageUtil.processXML(request, response, articleDisplay.getContent()) %>
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
					<c:when test="<%= Validator.isNotNull(journalContentDisplayContext.getArticleId()) %>">
						<c:choose>
							<c:when test="<%= journalContentDisplayContext.isExpired() %>">
								<div class="alert alert-warning">
									<liferay-ui:message arguments="<%= HtmlUtil.escape(article.getTitle(locale)) %>" key="x-is-expired" />
								</div>
							</c:when>
							<c:when test="<%= !article.isApproved() %>">
								<c:choose>
									<c:when test="<%= JournalArticlePermission.contains(permissionChecker, article.getGroupId(), article.getArticleId(), ActionKeys.UPDATE) %>">
										<liferay-portlet:renderURL portletName="<%= PortletKeys.JOURNAL %>" var="editURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
											<portlet:param name="struts_action" value="/journal/edit_article" />
											<portlet:param name="redirect" value="<%= currentURL %>" />
											<portlet:param name="groupId" value="<%= String.valueOf(article.getGroupId()) %>" />
											<portlet:param name="folderId" value="<%= String.valueOf(article.getFolderId()) %>" />
											<portlet:param name="articleId" value="<%= article.getArticleId() %>" />
											<portlet:param name="version" value="<%= String.valueOf(article.getVersion()) %>" />
										</liferay-portlet:renderURL>

										<div class="alert alert-warning">
											<a href="<%= editURL %>">
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
						</c:choose>
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>

<c:if test="<%= journalContentDisplayContext.isShowIconsActions() %>">
	<div class="icons-container lfr-meta-actions">
		<div class="lfr-icon-actions">
			<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/journal_content/add_asset_redirect" />
				<portlet:param name="referringPortletResource" value="<%= portletDisplay.getId() %>" />
			</portlet:renderURL>

			<c:if test="<%= journalContentDisplayContext.isShowEditArticleIcon() %>">

				<%
				JournalArticle latestArticle = journalContentDisplayContext.getLatestArticle();
				%>

				<liferay-portlet:renderURL portletName="<%= PortletKeys.JOURNAL %>" var="editArticleURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/journal/edit_article" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(latestArticle.getGroupId()) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(latestArticle.getFolderId()) %>" />
					<portlet:param name="articleId" value="<%= latestArticle.getArticleId() %>" />
					<portlet:param name="version" value="<%= String.valueOf(latestArticle.getVersion()) %>" />
					<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
				</liferay-portlet:renderURL>

				<%
				String taglibEditArticleURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + HtmlUtil.escapeJS(HtmlUtil.escape(latestArticle.getTitle(locale))) + "', uri:'" + HtmlUtil.escapeJS(editArticleURL.toString()) + "'});";
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

				<liferay-portlet:renderURL portletName="<%= PortletKeys.DYNAMIC_DATA_MAPPING %>" var="editTemplateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="struts_action" value="/dynamic_data_mapping/edit_template" />
					<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
					<portlet:param name="showBackURL" value="<%= Boolean.FALSE.toString() %>" />
					<portlet:param name="refererPortletName" value="<%= PortletKeys.JOURNAL_CONTENT %>" />
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
					<liferay-portlet:renderURL portletName="<%= PortletKeys.JOURNAL %>" varImpl="addArticleURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
						<portlet:param name="struts_action" value="/journal/edit_article" />
						<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
						<portlet:param name="portletResource" value="<%= portletDisplay.getId() %>" />
						<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
						<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
					</liferay-portlet:renderURL>

					<%
					List<DDMStructure> ddmStructures = DDMStructureServiceUtil.getStructures(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId), PortalUtil.getClassNameId(JournalArticle.class));

					for (DDMStructure ddmStructure : ddmStructures) {
						addArticleURL.setParameter("ddmStructureId", String.valueOf(ddmStructure.getStructureId()));
					%>

						<%
						AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(JournalArticle.class.getName());

						String taglibAddArticleURL = "javascript:Liferay.Util.openWindow({id: '_" + HtmlUtil.escapeJS(portletDisplay.getId()) + "_editAsset', title: '" + HtmlUtil.escapeJS(LanguageUtil.format(locale, "new-x", ddmStructure.getName(locale))) + "', uri:'" + HtmlUtil.escapeJS(addArticleURL.toString()) + "'});";
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
	<c:if test="<%= journalContentDisplayContext.isEnableRelatedAssets() %>">
		<div class="entry-links">
			<liferay-ui:asset-links
				className="<%= JournalArticle.class.getName() %>"
				classPK="<%= articleDisplay.getResourcePrimKey() %>"
			/>
		</div>
	</c:if>

	<c:if test="<%= journalContentDisplayContext.isEnableRatings() && !journalContentDisplayContext.isPrint() %>">
		<div class="taglib-ratings-wrapper">
			<liferay-ui:ratings
				className="<%= JournalArticle.class.getName() %>"
				classPK="<%= articleDisplay.getResourcePrimKey() %>"
			/>
		</div>
	</c:if>

	<c:if test="<%= journalContentDisplayContext.isEnableComments() %>">
		<c:if test="<%= journalContentDisplayContext.getDiscussionMessagesCount() > 0 %>">
			<liferay-ui:header
				title="comments"
			/>
		</c:if>

		<portlet:actionURL var="discussionURL">
			<portlet:param name="struts_action" value="/journal_content/edit_article_discussion" />
		</portlet:actionURL>

		<liferay-ui:discussion
			className="<%= JournalArticle.class.getName() %>"
			classPK="<%= articleDisplay.getResourcePrimKey() %>"
			formAction="<%= discussionURL %>"
			hideControls="<%= journalContentDisplayContext.isPrint() %>"
			ratingsEnabled="<%= journalContentDisplayContext.isEnableCommentRatings() && !journalContentDisplayContext.isPrint() %>"
			redirect="<%= currentURL %>"
			userId="<%= articleDisplay.getUserId() %>"
		/>
	</c:if>
</c:if>