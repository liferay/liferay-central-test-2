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

<%@ include file="/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

boolean viewTrashAttachments = ParamUtil.getBoolean(request, "viewTrashAttachments");

if (!TrashUtil.isTrashEnabled(scopeGroupId)) {
	viewTrashAttachments = false;
}

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

PortletURL portletURL = renderResponse.createActionURL();

portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
portletURL.setParameter("title", wikiPage.getTitle());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view");

PortalUtil.addPortletBreadcrumbEntry(request, wikiPage.getTitle(), portletURL.toString());

portletURL.setParameter(ActionRequest.ACTION_NAME, "/wiki/view_page_attachments");

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "attachments"), portletURL.toString());
%>

<c:if test="<%= !viewTrashAttachments %>">
	<portlet:actionURL name="/wiki/edit_page_attachment" var="undoTrashURL">
		<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
	</portlet:actionURL>

	<liferay-trash:trash-undo portletURL="<%= undoTrashURL %>" />
</c:if>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/wiki/page_tabs.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="attachments" />
</liferay-util:include>

<c:if test="<%= viewTrashAttachments %>">
	<liferay-ui:header
		backURL="<%= redirect %>"
		title="removed-attachments"
	/>
</c:if>

<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
	<c:choose>
		<c:when test="<%= viewTrashAttachments %>">
			<portlet:actionURL name="/wiki/edit_page_attachment" var="emptyTrashURL">
				<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
				<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
			</portlet:actionURL>

			<liferay-trash:trash-empty
				confirmMessage="are-you-sure-you-want-to-remove-the-attachments-for-this-page"
				emptyMessage="remove-the-attachments-for-this-page"
				infoMessage="attachments-that-have-been-removed-for-more-than-x-will-be-automatically-deleted"
				portletURL="<%= emptyTrashURL.toString() %>"
				totalEntries="<%= wikiPage.getDeletedAttachmentsFileEntriesCount() %>"
			/>
		</c:when>
		<c:otherwise>

			<%
			int deletedAttachmentsCount = wikiPage.getDeletedAttachmentsFileEntriesCount();
			%>

			<c:if test="<%= TrashUtil.isTrashEnabled(scopeGroupId) && (deletedAttachmentsCount > 0) %>">
				<portlet:renderURL var="viewTrashAttachmentsURL">
					<portlet:param name="mvcRenderCommandName" value="/wiki/view_page_attachments" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="viewTrashAttachments" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:renderURL>

				<liferay-ui:icon
					cssClass="trash-attachments"
					iconCssClass="icon-trash"
					label="<%= true %>"
					message='<%= LanguageUtil.format(request, (deletedAttachmentsCount == 1) ? "x-recently-removed-attachment" : "x-recently-removed-attachments", deletedAttachmentsCount, false) %>'
					url="<%= viewTrashAttachmentsURL %>"
				/>
			</c:if>

			<portlet:renderURL var="addAttachmentsURL">
				<portlet:param name="mvcRenderCommandName" value="/wiki/edit_page_attachment" />
				<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
				<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
			</portlet:renderURL>

			<div class="btn-toolbar">

				<%
				String taglibAddAttachments = "location.href = '" + addAttachmentsURL + "';";
				%>

				<aui:button onClick="<%= taglibAddAttachments %>" value="add-attachments" />
			</div>
		</c:otherwise>
	</c:choose>
</c:if>

<%
String emptyResultsMessage = null;

if (viewTrashAttachments) {
	emptyResultsMessage = "this-page-does-not-have-file-attachments-in-the-recycle-bin";
}
else {
	emptyResultsMessage = "this-page-does-not-have-file-attachments";
}

PortletURL iteratorURL = renderResponse.createRenderURL();

iteratorURL.setParameter("mvcRenderCommandName", "/wiki/view_page_attachments");
iteratorURL.setParameter("redirect", currentURL);
iteratorURL.setParameter("nodeId", String.valueOf(node.getNodeId()));
iteratorURL.setParameter("title", wikiPage.getTitle());
iteratorURL.setParameter("viewTrashAttachments", String.valueOf(viewTrashAttachments));
%>

<liferay-ui:search-container
	emptyResultsMessage="<%= emptyResultsMessage %>"
	iteratorURL="<%= iteratorURL %>"
	total="<%= viewTrashAttachments ? wikiPage.getDeletedAttachmentsFileEntriesCount() : wikiPage.getAttachmentsFileEntriesCount() %>"
>
	<c:choose>
		<c:when test="<%= viewTrashAttachments %>">
			<liferay-ui:search-container-results
				results="<%= wikiPage.getDeletedAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>
		</c:when>
		<c:otherwise>
			<liferay-ui:search-container-results
				results="<%= wikiPage.getAttachmentsFileEntries(searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>
		</c:otherwise>
	</c:choose>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.repository.model.FileEntry"
		escapedModel="<%= true %>"
		keyProperty="fileEntryId"
		modelVar="fileEntry"
		rowVar="row"
	>

		<%
		int status = WorkflowConstants.STATUS_APPROVED;

		if (viewTrashAttachments) {
			status = WorkflowConstants.STATUS_IN_TRASH;
		}

		String rowHREF = PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, "status=" + status);
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="file-name"
		>

			<%
			AssetRendererFactory<?> assetRendererFactory = (AssetRendererFactory<?>)AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

			AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
			%>

			<liferay-ui:icon
				iconCssClass="<%= assetRenderer.getIconCssClass() %>"
				label="<%= true %>"
				message="<%= TrashUtil.getOriginalTitle(fileEntry.getTitle()) %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowHREF %>"
			name="size"
			value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/wiki/page_attachment_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<portlet:actionURL name="/wiki/edit_page_attachment" var="checkEntryURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.CHECK %>" />
</portlet:actionURL>

<portlet:renderURL var="duplicateEntryURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/wiki/restore_entry" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<aui:script use="liferay-restore-entry">
	new Liferay.RestoreEntry(
		{
			checkEntryURL: '<%= checkEntryURL.toString() %>',
			duplicateEntryURL: '<%= duplicateEntryURL.toString() %>',
			namespace: '<portlet:namespace />'
		}
	);
</aui:script>