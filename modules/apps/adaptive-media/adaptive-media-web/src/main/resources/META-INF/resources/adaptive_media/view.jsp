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
			disabled="<%= true %>"
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
		<liferay-frontend:management-bar-button disabled="<%= configurationEntries.size() <= 0 %>" href='<%= "javascript:" + renderResponse.getNamespace() + "deleteImageConfigurationEntries();" %>' icon="times" label="delete" />
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<%
AdaptiveMediaImageConfigurationHelper adaptiveMediaImageConfigurationHelper = (AdaptiveMediaImageConfigurationHelper)request.getAttribute(AdaptiveMediaWebKeys.IMAGE_ADAPTIVE_MEDIA_CONFIGURATION_HELPER);

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="container-fluid-1280">
	<c:if test="<%= adaptiveMediaImageConfigurationHelper.isDefaultConfiguration(themeDisplay.getCompanyId()) %>">
		<div class="alert alert-info">
			<liferay-ui:message key="this-configuration-was-not-saved-yet" />
		</div>
	</c:if>

	<portlet:actionURL name="/adaptive_media/delete_image_configuration_entry" var="deleteImageConfigurationEntryURL" />

	<%
	int optimizeImagesAllConfigurationsBackgroundTasksCount = BackgroundTaskManagerUtil.getBackgroundTasksCount(CompanyConstants.SYSTEM, OptimizeImagesAllConfigurationsBackgroundTaskExecutor.class.getName(), false);
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

				<%
				int percentage = AdaptiveMediaImageEntryLocalServiceUtil.getPercentage(themeDisplay.getCompanyId(), configurationEntry.getUUID());
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="optimized-images"
				>
					<div class="progress">
						<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="<%= percentage %>" class="<%= (percentage == 100) ? "progress-bar progress-bar-success" : "progress-bar" %>" role="progressbar" style="<%= "width: " + percentage + "%;" %>"><%= percentage + "%" %></div>
					</div>
				</liferay-ui:search-container-column-text>

				<%
				Map<String, String> properties = configurationEntry.getProperties();
				%>

				<liferay-ui:search-container-column-text
					name="max-width"
					orderable="<%= false %>"
					value='<%= properties.get("max-width") %>'
				/>

				<liferay-ui:search-container-column-text
					name="max-height"
					orderable="<%= false %>"
					value='<%= properties.get("max-height") %>'
				/>

				<c:if test="<%= optimizeImagesAllConfigurationsBackgroundTasksCount == 0 %>">
					<liferay-ui:search-container-column-jsp
						path="/adaptive_media/image_configuration_entry_action.jsp"
					/>
				</c:if>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteImageConfigurationEntries() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			submitForm(form);
		}
	}
</aui:script>

<portlet:renderURL var="addImageConfigurationEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-image-resolution") %>' url="<%= addImageConfigurationEntryURL %>" />
</liferay-frontend:add-menu>