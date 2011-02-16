<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Layout selLayout = (Layout)request.getAttribute("edit_pages.jsp-selLayout");

UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}
%>

<liferay-ui:error-marker key="errorSection" value="javascript" />

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<h3><liferay-ui:message key="javascript" /></h3>

<aui:fieldset>
	<aui:input cssClass="lfr-textarea-container" label="javascript-1" name="TypeSettingsProperties--javascript-1--" style="width: 300px" type="textarea" value='<%= layoutTypeSettings.getProperty("javascript-1") %>' wrap="soft" />

	<aui:input cssClass="lfr-textarea-container" label="javascript-2" name="TypeSettingsProperties--javascript-2--" style="width: 300px" type="textarea" value='<%= layoutTypeSettings.getProperty("javascript-2") %>' wrap="soft" />

	<aui:input cssClass="lfr-textarea-container" label="javascript-3" name="TypeSettingsProperties--javascript-3--" style="width: 300px" type="textarea" value='<%= layoutTypeSettings.getProperty("javascript-3") %>' wrap="soft" />
</aui:fieldset>