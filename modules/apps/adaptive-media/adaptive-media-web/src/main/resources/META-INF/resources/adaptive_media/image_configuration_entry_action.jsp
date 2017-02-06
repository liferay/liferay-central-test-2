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

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

ImageAdaptiveMediaConfigurationEntry configurationEntry = (ImageAdaptiveMediaConfigurationEntry)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<liferay-portlet:renderURL var="editImageConfigurationEntryURL">
		<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryUuid" value="<%= String.valueOf(configurationEntry.getUUID()) %>" />
	</liferay-portlet:renderURL>

	<liferay-ui:icon
		message="edit"
		url="<%= editImageConfigurationEntryURL %>"
	/>

	<portlet:actionURL name="/adaptive_media/delete_image_configuration_entry" var="deleteImageConfigurationEntryURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="rowIdsImageAdaptiveMediaConfigurationEntry" value="<%= String.valueOf(configurationEntry.getUUID()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete
		trash="<%= false %>"
		url="<%= deleteImageConfigurationEntryURL %>"
	/>

	<portlet:actionURL name="/adaptive_media/optimize_images" var="optimizeImagesURL">
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="entryUuid" value="<%= String.valueOf(configurationEntry.getUUID()) %>" />
	</portlet:actionURL>

	<liferay-ui:icon
		message="optimize-remaining"
		url="<%= optimizeImagesURL %>"
	/>
</liferay-ui:icon-menu>