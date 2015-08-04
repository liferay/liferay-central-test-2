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

<%@ include file="/document_library/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/document_library/view_file_entry_type.jsp");
%>

<liferay-util:include page="/document_library/file_entry_type_toolbar.jsp" servletContext="<%= application %>">
	<liferay-util:param name="includeBasicFileEntryType" value="<%= Boolean.FALSE.toString() %>" />
</liferay-util:include>

<liferay-ui:error exception="<%= RequiredFileEntryTypeException.class %>" message="cannot-delete-a-document-type-that-is-presently-used-by-one-or-more-documents" />

<div class="separator"></div>

<liferay-ui:search-container
	searchContainer='<%= new SearchContainer(renderRequest, new DisplayTerms(request), new DisplayTerms(request), SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, null, LanguageUtil.get(request, "there-are-no-results")) %>'
>
	<liferay-ui:search-container-results>
		<%@ include file="/document_library/file_entry_type_search_results.jspf" %>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
		escapedModel="<%= true %>"
		keyProperty="fileEntryTypeId"
		modelVar="fileEntryType"
	>
		<liferay-ui:search-container-column-text
			name="name"
			value="<%= fileEntryType.getName(locale) %>"
		/>

		<%
		Group group = GroupLocalServiceUtil.getGroup(fileEntryType.getGroupId());
		%>

		<liferay-ui:search-container-column-text
			name="scope"
			value="<%= LanguageUtil.get(request, group.getScopeLabel(themeDisplay)) %>"
		/>

		<liferay-ui:search-container-column-date
			name="modified-date"
			value="<%= fileEntryType.getModifiedDate() %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/document_library/file_entry_type_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>