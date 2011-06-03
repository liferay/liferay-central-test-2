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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");

long repositoryId = ParamUtil.getLong(request, "repositoryId");
long folderId = ParamUtil.getLong(request, "folderId");

List<DLDocumentType> documentTypes = new ArrayList<DLDocumentType>();

DLDocumentType basicDocumentType = DLDocumentTypeLocalServiceUtil.createDLDocumentType(0);

basicDocumentType.setName(LanguageUtil.get(pageContext, "basic-document"));

documentTypes.add(basicDocumentType);

documentTypes.addAll(DLDocumentTypeServiceUtil.getDocumentTypes(scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS));
%>

<liferay-ui:search-container
	id='<%= renderResponse.getNamespace() + "documentTypesSearchContainer" %>'
>
	<liferay-ui:search-container-results
		results="<%= documentTypes %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.documentlibrary.model.DLDocumentType"
		escapedModel="<%= true %>"
		keyProperty="documentTypeId"
		modelVar="documentType"
	>
		<liferay-ui:search-container-column-text name="name">
			<a class="select-document-type" data-rowId="<%= documentType.getDocumentTypeId() %>" href="javascript:;"><%= documentType.getName() %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-portlet-url,liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />documentTypesSearchContainer');

	searchContainer.get('contentBox').delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var portletURL = Liferay.PortletURL.createRenderURL();

			portletURL.setPortletId('<%= portletId %>');

			portletURL.setParameter('backURL', '<%= HtmlUtil.escape(backURL) %>');
			portletURL.setParameter('<%= Constants.CMD %>', '<%= Constants.ADD %>');
			portletURL.setParameter('documentTypeId', link.getAttribute('data-rowId'));
			portletURL.setParameter('folderId', '<%= folderId %>');
			portletURL.setParameter('redirect', '<%= HtmlUtil.escape(redirect) %>');
			portletURL.setParameter('repositoryId', '<%= repositoryId %>');
			portletURL.setParameter('struts_action', '/document_library/edit_file_entry');

			Liferay.Util.getOpener().location.href = portletURL.toString();

			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />selectDocumentType'
				}
			);
		},
		'.select-document-type'
	);
</aui:script>