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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<div class="search-form">
	<span class="aui-search-bar">
		<aui:input inlineField="<%= true %>" label="" name="keywords" size="30" title="search-entries" type="text" />

		<aui:button type="submit" value="search" />
	</span>
</div>

<aui:button-row>
	<aui:button onClick='<%= renderResponse.getNamespace() + "addFileEntryType();" %>' value="add-document-type" />
</aui:button-row>

<aui:script>
	function <portlet:namespace />addFileEntryType() {
		var url = '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/edit_file_entry_type" /><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:renderURL>';

		submitForm(document.hrefFm, url);
	}
</aui:script>