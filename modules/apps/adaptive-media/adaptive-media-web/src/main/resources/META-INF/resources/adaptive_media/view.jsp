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
List<ImageAdaptiveMediaConfigurationEntry> configurationEntries = (List)request.getAttribute(AdaptiveMediaWebKeys.CONFIGURATION_ENTRIES_LIST);

PortletURL portletURL = renderResponse.createRenderURL();
%>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		emptyResultsMessage="there-are-no-image-resolutions"
		id="imageConfigurationEntries"
		iteratorURL="<%= portletURL %>"
		total="<%= configurationEntries.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= configurationEntries %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.adaptive.media.image.configuration.ImageAdaptiveMediaConfigurationEntry"
			modelVar="configurationEntry"
		>
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
				name="uuid"
				orderable="<%= false %>"
				value="<%= configurationEntry.getUUID() %>"
			/>

			<%
			Map<String, String> properties = configurationEntry.getProperties();
			%>

			<liferay-ui:search-container-column-text
				name="max-width"
				orderable="<%= false %>"
				value='<%= properties.get("width") %>'
			/>

			<liferay-ui:search-container-column-text
				name="max-height"
				orderable="<%= false %>"
				value='<%= properties.get("height") %>'
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<portlet:renderURL var="addImageConfigurationEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-image-resolution") %>' url="<%= addImageConfigurationEntryURL %>" />
</liferay-frontend:add-menu>