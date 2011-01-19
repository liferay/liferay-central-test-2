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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
UnicodeProperties layoutTypeSettings = null;

if (selLayout != null) {
	layoutTypeSettings = selLayout.getTypeSettingsProperties();
}

String url = layoutTypeSettings.getProperty("url", StringPool.BLANK);
String description = layoutTypeSettings.getProperty("description", StringPool.BLANK);
%>

<aui:input cssClass="lfr-input-text-container" label="url" name="TypeSettingsProperties--url--" type="text" value="<%= url %>" />

<aui:input cssClass="lfr-textarea-container" label="description" name="TypeSettingsProperties--description--" type="textarea" value="<%= description %>" wrap="soft" />