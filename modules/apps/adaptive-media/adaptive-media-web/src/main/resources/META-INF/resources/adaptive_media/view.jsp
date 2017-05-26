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

<%@ include file="/adaptive_media/init.jsp" %>

<aui:nav-bar cssClass="collapse-basic-search" markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<portlet:renderURL var="viewImageConfigurationEntriesURL" />

		<aui:nav-item
			href="<%= viewImageConfigurationEntriesURL %>"
			label="image-resolutions"
			selected="<%= true %>"
		/>
	</aui:nav>
</aui:nav-bar>

<%
List<AdaptiveMediaImageConfigurationEntry> configurationEntries = (List)request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRIES_LIST);
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="imageConfigurationEntries"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			disabled="<%= (configurationEntries.size() <= 0) %>"
			icon="info-circle"
			label="info"
		/>

		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= PortletURLUtil.clone(currentURLObj, liferayPortletResponse) %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<%
	String entriesNavigation = ParamUtil.getString(request, "entriesNavigation", "all");
	%>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			disabled='<%= (configurationEntries.size() <= 0) && entriesNavigation.equals("all") %>'
			navigationKeys='<%= new String[] {"all", "enabled", "disabled"} %>'
			navigationParam="entriesNavigation"
			portletURL="<%= PortletURLUtil.clone(currentURLObj, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<%
PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="closed container-fluid-1280 sidenav-container sidenav-right" id="<portlet:namespace />infoPanelId">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" id="/adaptive_media/info_panel" var="sidebarPanelURL" />

	<liferay-frontend:sidebar-panel
		resourceURL="<%= sidebarPanelURL %>"
		searchContainerId="imageConfigurationEntries"
	>
		<liferay-util:include page="/adaptive_media/info_panel.jsp" servletContext="<%= application %>" />
	</liferay-frontend:sidebar-panel>

	<div class="sidenav-content">
		<liferay-util:include page="/adaptive_media/success_messages.jsp" servletContext="<%= application %>" />

		<c:choose>
			<c:when test='<%= SessionMessages.contains(request, "configurationEntryUpdated") %>'>

				<%
				AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(request, "configurationEntryUpdated");
				%>

				<div class="alert alert-success">
					<liferay-ui:message arguments="<%= configurationEntry.getName() %>" key="x-saved-successfully" translateArguments="<%= false %>" />
				</div>
			</c:when>
		</c:choose>

		<portlet:actionURL name="/adaptive_media/delete_image_configuration_entry" var="deleteImageConfigurationEntryURL" />

		<%
		int optimizeImagesAllConfigurationsBackgroundTasksCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(CompanyConstants.SYSTEM, OptimizeImagesAllConfigurationsBackgroundTaskExecutor.class.getName(), false);

		List<BackgroundTask> optimizeImageSingleBackgroundTasks = BackgroundTaskManagerUtil.getBackgroundTasks(CompanyConstants.SYSTEM, OptimizeImagesSingleConfigurationBackgroundTaskExecutor.class.getName(), BackgroundTaskConstants.STATUS_IN_PROGRESS);

		request.setAttribute("view.jsp-optimizeImageSingleBackgroundTasks", optimizeImageSingleBackgroundTasks);

		List<String> currentBackgroundTaskConfigurationEntryUuids = new ArrayList<>();

		for (BackgroundTask optimizeImageSingleBackgroundTask : optimizeImageSingleBackgroundTasks) {
			Map<String, Serializable> taskContextMap = optimizeImageSingleBackgroundTask.getTaskContextMap();

			String configurationEntryUuid = (String)taskContextMap.get("configurationEntryUuid");

			currentBackgroundTaskConfigurationEntryUuids.add(configurationEntryUuid);
		}
		%>

		<aui:form action="<%= deleteImageConfigurationEntryURL.toString() %>" method="post" name="fm">
			<liferay-ui:search-container
				emptyResultsMessage="there-are-no-image-resolutions"
				id="imageConfigurationEntries"
				iteratorURL="<%= portletURL %>"
				rowChecker="<%= new ImageConfigurationEntriesChecker(liferayPortletResponse) %>"
				total="<%= configurationEntries.size() %>"
			>
				<liferay-ui:search-container-results
					results="<%= ListUtil.subList(configurationEntries, searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry"
					modelVar="configurationEntry"
				>

					<%
					row.setPrimaryKey(String.valueOf(configurationEntry.getUUID()));
					%>

					<liferay-portlet:renderURL varImpl="rowURL">
						<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="entryUuid" value="<%= String.valueOf(configurationEntry.getUUID()) %>" />
					</liferay-portlet:renderURL>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						href="<%= rowURL %>"
						name="name"
						orderable="<%= false %>"
						value="<%= configurationEntry.getName() %>"
					/>

					<liferay-ui:search-container-column-text
						name="state"
						orderable="<%= false %>"
						value='<%= LanguageUtil.get(request, configurationEntry.isEnabled() ? "enabled" : "disabled") %>'
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="adapted-images"
					>

						<%
						String rowId = row.getRowId();
						String uuid = String.valueOf(configurationEntry.getUUID());

						int adaptedImages = AdaptiveMediaImageEntryLocalServiceUtil.getAdaptiveMediaImageEntriesCount(themeDisplay.getCompanyId(), configurationEntry.getUUID());

						int totalImages = AdaptiveMediaImageEntryLocalServiceUtil.getExpectedAdaptiveMediaImageEntriesCount(themeDisplay.getCompanyId());
						%>

						<div id="<portlet:namespace />AdaptRemaining_<%= rowId %>"></div>

						<portlet:resourceURL id="/adaptive_media/adapted_images_percentage" var="adaptedImagesPercentageURL">
							<portlet:param name="entryUuid" value="<%= uuid %>" />
						</portlet:resourceURL>

						<aui:script require="adaptive-media-web/adaptive_media/js/AdaptiveMediaProgress.es">
							var component = Liferay.component(
								'<portlet:namespace />AdaptRemaining<%= uuid %>',
								new adaptiveMediaWebAdaptive_mediaJsAdaptiveMediaProgressEs.default(
									{
										adaptedImages: <%= adaptedImages %>,
										disabled: <%= !configurationEntry.isEnabled() %>,
										namespace: '<portlet:namespace />',
										percentageUrl: '<%= adaptedImagesPercentageURL.toString() %>',
										totalImages: <%= totalImages %>,
										uuid: '<%= uuid %>'
									},
									<portlet:namespace />AdaptRemaining_<%= rowId %>
								)
							);

							<c:if test="<%= ((optimizeImagesAllConfigurationsBackgroundTasksCount > 0) && configurationEntry.isEnabled()) || currentBackgroundTaskConfigurationEntryUuids.contains(uuid) %>">
								setTimeout(() => component.startProgress(), 0);
							</c:if>
						</aui:script>
					</liferay-ui:search-container-column-text>

					<%
					Map<String, String> properties = configurationEntry.getProperties();
					%>

					<%
					String maxWidth = properties.get("max-width");
					%>

					<liferay-ui:search-container-column-text
						name="max-width"
						orderable="<%= false %>"
						value='<%= maxWidth.equals("0") ? LanguageUtil.get(request, "auto") : maxWidth + "px" %>'
					/>

					<%
					String maxHeight = properties.get("max-height");
					%>

					<liferay-ui:search-container-column-text
						name="max-height"
						orderable="<%= false %>"
						value='<%= maxHeight.equals("0") ? LanguageUtil.get(request, "auto") : maxHeight + "px" %>'
					/>

					<liferay-ui:search-container-column-jsp
						path="/adaptive_media/image_configuration_entry_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
			</liferay-ui:search-container>
		</aui:form>
	</div>
</div>

<aui:script>
	function <portlet:namespace />deleteImageConfigurationEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			submitForm(form);
		}
	}

	function <portlet:namespace />adaptRemaining(uuid, backgroundTaskUrl) {
		var component = Liferay.component('<portlet:namespace />AdaptRemaining' + uuid);

		component.startProgress(backgroundTaskUrl);
	}
</aui:script>

<portlet:renderURL var="addImageConfigurationEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-image-resolution") %>' url="<%= addImageConfigurationEntryURL %>" />
</liferay-frontend:add-menu>