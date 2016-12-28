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

<portlet:renderURL var="addImageConfigurationEntryURL">
	<portlet:param name="mvcRenderCommandName" value="/adaptive_media/edit_image_configuration_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-image-resolution") %>' url="<%= addImageConfigurationEntryURL %>" />
</liferay-frontend:add-menu>