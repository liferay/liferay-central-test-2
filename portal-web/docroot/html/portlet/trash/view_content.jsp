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

<%@ include file="/html/portlet/trash/init.jsp" %>

<liferay-util:include page="/html/portlet/trash/restore_path.jsp" />

<div class="asset-content">

	<%
	String redirect = ParamUtil.getString(request, "redirect");
	String backURL = ParamUtil.getString(request, "backURL", redirect);

	long trashEntryId = ParamUtil.getLong(request, "trashEntryId");

	String className = ParamUtil.getString(request, "className");
	long classPK = ParamUtil.getLong(request, "classPK");

	TrashEntry entry = null;

	if (trashEntryId > 0) {
		entry = TrashEntryLocalServiceUtil.getEntry(trashEntryId);
	}
	else if (Validator.isNotNull(className) && (classPK > 0)) {
		entry = TrashEntryLocalServiceUtil.fetchEntry(className, classPK);
	}

	if (entry != null) {
		className = entry.getClassName();
		classPK = entry.getClassPK();
	}

	TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

	TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

	PortletURL portletURL = renderResponse.createRenderURL();

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(locale, "recycle-bin"), portletURL.toString());

	PortletURL containerModelURL = renderResponse.createRenderURL();

	containerModelURL.setParameter("mvcPath", "/html/portlet/trash/view_content.jsp");
	containerModelURL.setParameter("redirect", redirect);
	containerModelURL.setParameter("className", trashHandler.getContainerModelClassName(classPK));

	TrashUtil.addBaseModelBreadcrumbEntries(request, liferayPortletResponse, className, classPK, containerModelURL);
	%>

	<liferay-ui:breadcrumb
		showCurrentGroup="<%= false %>"
		showGuestGroup="<%= false %>"
		showLayout="<%= false %>"
		showParentGroups="<%= false %>"
	/>

	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= false %>"
		title="<%= trashRenderer.getTitle(locale) %>"
	/>

	<c:choose>
		<c:when test="<%= Validator.isNotNull(trashRenderer.renderActions(renderRequest, renderResponse)) %>">
			<liferay-util:include page="<%= trashRenderer.renderActions(renderRequest, renderResponse) %>" />
		</c:when>
		<c:otherwise>
			<div class="edit-toolbar" id="<portlet:namespace />entryToolbar">
				<div class="btn-group">
					<c:choose>
						<c:when test="<%= entry != null %>">
							<c:choose>
								<c:when test="<%= trashHandler.isRestorable(entry.getClassPK()) && !trashHandler.isInTrashContainer(entry.getClassPK()) %>">
									<aui:button icon="icon-undo" name="restoreEntryButton" value="restore" />

									<aui:script>
										<portlet:actionURL name="restoreEntries" var="restoreEntryURL">
											<portlet:param name="redirect" value="<%= backURL %>" />
											<portlet:param name="trashEntryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
										</portlet:actionURL>

										AUI.$('#<portlet:namespace />restoreEntryButton').on(
											'click',
											function(event) {
												Liferay.fire(
													'<portlet:namespace />checkEntry',
													{
														trashEntryId: <%= entry.getEntryId() %>,
														uri: '<%= restoreEntryURL.toString() %>'
													}
												);
											}
										);
									</aui:script>
								</c:when>
								<c:when test="<%= !trashHandler.isRestorable(entry.getClassPK()) && trashHandler.isMovable() %>">
									<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
										<portlet:param name="mvcPath" value="/html/portlet/trash/view_container_model.jsp" />
										<portlet:param name="redirect" value="<%= backURL %>" />
										<portlet:param name="className" value="<%= entry.getClassName() %>" />
										<portlet:param name="classPK" value="<%= String.valueOf(entry.getClassPK()) %>" />
										<portlet:param name="containerModelClassName" value="<%= trashHandler.getContainerModelClassName(entry.getClassPK()) %>" />
										<portlet:param name="containerModelId" value="<%= String.valueOf(trashHandler.getRootContainerModelId(entry.getClassPK())) %>" />
										<portlet:param name="rootContainerModelMovable" value="<%= String.valueOf(trashHandler.isRootContainerModelMovable()) %>" />
									</portlet:renderURL>

									<%
									String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
									%>

									<aui:button icon="icon-undo" name="restoreEntryButton" onClick="<%= taglibOnClick %>" value="restore" />
								</c:when>
							</c:choose>

							<c:if test="<%= trashHandler.isDeletable() %>">
								<aui:button icon="icon-remove" name="removeEntryButton" value="delete" />

								<aui:script>
									<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
										<portlet:param name="redirect" value="<%= backURL %>" />
										<portlet:param name="trashEntryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
									</portlet:actionURL>

									AUI.$('#<portlet:namespace />removeEntryButton').on(
										'click',
										function(event) {
											if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
												submitForm(document.hrefFm, '<%= deleteEntryURL.toString() %>');
											}
										}
									);
								</aui:script>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="<%= trashHandler.isMovable() %>">
								<portlet:renderURL var="moveURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
									<portlet:param name="mvcPath" value="/html/portlet/trash/view_container_model.jsp" />
									<portlet:param name="redirect" value="<%= backURL %>" />
									<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
									<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
									<portlet:param name="containerModelClassName" value="<%= trashHandler.getContainerModelClassName(classPK) %>" />
								</portlet:renderURL>

								<%
								String taglibOnClick = renderResponse.getNamespace() + "restoreDialog('" + moveURL + "')";
								%>

								<aui:button icon="icon-undo" name="moveEntryButton" onClick="<%= taglibOnClick %>" value="restore" />
							</c:if>

							<c:if test="<%= trashHandler.isDeletable() %>">
								<aui:button icon="icon-remove" name="removeEntryButton" value="delete" />

								<aui:script>
									<portlet:actionURL name="deleteEntries" var="deleteEntryURL">
										<portlet:param name="redirect" value="<%= backURL %>" />
										<portlet:param name="className" value="<%= trashRenderer.getClassName() %>" />
										<portlet:param name="classPK" value="<%= String.valueOf(trashRenderer.getClassPK()) %>" />
									</portlet:actionURL>

									AUI.$('#<portlet:namespace />removeEntryButton').on(
										'click',
										function(event) {
											if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-this") %>')) {
												submitForm(document.hrefFm, '<%= deleteEntryURL.toString() %>');
											}
										}
									);
								</aui:script>
							</c:if>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="<%= trashHandler.isContainerModel() %>">

			<%
			PortletURL iteratorURL = renderResponse.createRenderURL();

			iteratorURL.setParameter("mvcPath", "/html/portlet/trash/view_content.jsp");
			iteratorURL.setParameter("redirect", redirect);
			iteratorURL.setParameter("className", className);
			iteratorURL.setParameter("classPK", String.valueOf(classPK));

			int containerModelsCount = trashHandler.getTrashContainerModelsCount(classPK);
			int baseModelsCount = trashHandler.getTrashContainedModelsCount(classPK);
			%>

			<liferay-ui:panel-container extended="<%= false %>" id="containerDisplayInfoPanelContainer" persistState="<%= true %>">
				<c:if test="<%= containerModelsCount > 0 %>">
					<liferay-ui:panel collapsible="<%= true %>" cssClass="view-folders" extended="<%= false %>" id="containerModelsListingPanel" persistState="<%= true %>" title="<%= trashHandler.getTrashContainerModelName() %>">
						<liferay-ui:search-container
							curParam="cur1"
							deltaConfigurable="<%= false %>"
							iteratorURL="<%= iteratorURL %>"
							total="<%= containerModelsCount %>"
						>
							<liferay-ui:search-container-results
								results="<%= trashHandler.getTrashContainerModelTrashRenderers(classPK, searchContainer.getStart(), searchContainer.getEnd()) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portal.kernel.trash.TrashRenderer"
								modelVar="curTrashRenderer"
							>

								<%
								TrashHandler curTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(curTrashRenderer.getClassName());

								int curContainerModelsCount = curTrashHandler.getTrashContainerModelsCount(curTrashRenderer.getClassPK());
								int curBaseModelsCount = curTrashHandler.getTrashContainedModelsCount(curTrashRenderer.getClassPK());

								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("mvcPath", "/html/portlet/trash/view_content.jsp");
								rowURL.setParameter("redirect", redirect);
								rowURL.setParameter("backURL", currentURL);
								rowURL.setParameter("className", (curTrashRenderer.getClassName()));
								rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
								%>

								<liferay-ui:search-container-column-text
									name="name"
								>
									<liferay-ui:icon
										iconCssClass="<%= curTrashRenderer.getIconCssClass() %>"
										label="<%= true %>"
										message="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>"
										method="get"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-text
									name='<%= LanguageUtil.format(request, "num-of-x", curTrashHandler.getTrashContainedModelName()) %>'
									value="<%= String.valueOf(curBaseModelsCount) %>"
								/>

								<liferay-ui:search-container-column-text
									name='<%= LanguageUtil.format(request, "num-of-x", curTrashHandler.getTrashContainerModelName()) %>'
									value="<%= String.valueOf(curContainerModelsCount) %>"
								/>

								<liferay-ui:search-container-column-jsp
									align="right"
									cssClass="entry-action"
									path="/html/portlet/trash/view_content_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= baseModelsCount > 0 %>">
					<liferay-ui:panel collapsible="<%= true %>" extended="<%= false %>" id="baseModelsListingPanel" persistState="<%= true %>" title="<%= trashHandler.getTrashContainedModelName() %>">
						<liferay-ui:search-container
							curParam="cur2"
							deltaConfigurable="<%= false %>"
							iteratorURL="<%= iteratorURL %>"
							total="<%= baseModelsCount %>"
						>
							<liferay-ui:search-container-results
								results="<%= trashHandler.getTrashContainedModelTrashRenderers(classPK, searchContainer.getStart(), searchContainer.getEnd()) %>"
							/>

							<liferay-ui:search-container-row
								className="com.liferay.portal.kernel.trash.TrashRenderer"
								modelVar="curTrashRenderer"
							>

								<%
								PortletURL rowURL = renderResponse.createRenderURL();

								rowURL.setParameter("mvcPath", "/html/portlet/trash/view_content.jsp");
								rowURL.setParameter("redirect", redirect);
								rowURL.setParameter("backURL", currentURL);
								rowURL.setParameter("className", curTrashRenderer.getClassName());
								rowURL.setParameter("classPK", String.valueOf(curTrashRenderer.getClassPK()));
								%>

								<liferay-ui:search-container-column-text
									name="name"
								>
									<liferay-ui:icon
										iconCssClass="<%= curTrashRenderer.getIconCssClass() %>"
										label="<%= true %>"
										message="<%= HtmlUtil.escape(curTrashRenderer.getTitle(locale)) %>"
										method="get"
										url="<%= rowURL.toString() %>"
									/>
								</liferay-ui:search-container-column-text>

								<liferay-ui:search-container-column-jsp
									align="right"
									cssClass="entry-action"
									path="/html/portlet/trash/view_content_action.jsp"
								/>
							</liferay-ui:search-container-row>

							<liferay-ui:search-iterator />
						</liferay-ui:search-container>
					</liferay-ui:panel>
				</c:if>

				<c:if test="<%= (containerModelsCount + baseModelsCount) == 0 %>">
					<div class="alert alert-info">
						<liferay-ui:message arguments="<%= new String[] {ResourceActionsUtil.getModelResource(locale, className)} %>" key="this-x-does-not-contain-an-entry" translateArguments="<%= false %>" />
					</div>
				</c:if>
			</liferay-ui:panel-container>
		</c:when>
		<c:otherwise>
			<liferay-ui:asset-display
				renderer="<%= trashRenderer %>"
			/>
		</c:otherwise>
	</c:choose>

	<c:if test="<%= trashRenderer instanceof AssetRenderer %>">

		<%
		AssetRenderer assetRenderer = (AssetRenderer)trashRenderer;
		%>

		<c:if test="<%= !assetRenderer.getClassName().equals(DLFileEntry.class.getName()) %>">
			<div class="asset-ratings">
				<liferay-ui:ratings
					className="<%= className %>"
					classPK="<%= classPK %>"
				/>
			</div>

			<%
			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(className, classPK);
			%>

			<div class="asset-related-assets">
				<liferay-ui:asset-links
					assetEntryId="<%= assetEntry.getEntryId() %>"
				/>
			</div>

			<c:if test="<%= Validator.isNotNull(assetRenderer.getDiscussionPath()) %>">
				<div class="alert alert-warning">
					<liferay-ui:message key="commenting-is-disabled-because-this-entry-is-in-the-recycle-bin" />
				</div>

				<portlet:actionURL name="invokeTaglibDiscussion" var="discussionURL" />

				<portlet:resourceURL var="discussionPaginationURL">
					<portlet:param name="invokeTaglibDiscussion" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:resourceURL>

				<div class="asset-discussion">
					<liferay-ui:discussion
						className="<%= className %>"
						classPK="<%= classPK %>"
						formAction="<%= discussionURL %>"
						formName='<%= "fm" + classPK %>'
						hideControls="<%= true %>"
						paginationURL="<%= discussionPaginationURL %>"
						redirect="<%= currentURL %>"
						userId="<%= assetEntry.getUserId() %>"
					/>
				</div>
			</c:if>
		</c:if>
	</c:if>
</div>