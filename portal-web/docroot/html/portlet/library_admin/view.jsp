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

<%@ include file="/html/portlet/library_admin/init.jsp" %>

<%
Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

boolean showRepositories = false;

if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	showRepositories = true;
}

if ((folder == null) && (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {
	try {
		folder = DLAppLocalServiceUtil.getFolder(folderId);
	}
	catch (NoSuchFolderException nsfe) {
		folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	}
}

long repositoryId = scopeGroupId;

if (folder != null) {
	repositoryId = folder.getRepositoryId();
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));

request.setAttribute("view.jsp-showRepositories", String.valueOf(showRepositories));

PortalPreferences portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(renderRequest);

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.LIBRARY_ADMIN, "display-style", "icon");
}
else {
	boolean saveDisplayStyle = ParamUtil.getBoolean(request, "saveDisplayStyle");

	if (saveDisplayStyle && ArrayUtil.contains(PropsValues.DL_DISPLAY_VIEWS, displayStyle)) {
		portalPreferences.setValue(PortletKeys.LIBRARY_ADMIN, "display-style", displayStyle);
	}
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.LIBRARY_ADMIN, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.LIBRARY_ADMIN, "order-by-type", orderByType);
}
%>

<div class="portlet-msg-error yui3-aui-helper-hidden" id="<portlet:namespace />errorContainer">
	<liferay-ui:message key="your-request-failed-to-complete" />
</div>

<liferay-portlet:renderURL varImpl="deleteURL">
	<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
</liferay-portlet:renderURL>

<aui:form action="<%= deleteURL.toString() %>" method="get" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteEntryIds" type="hidden" />
	<aui:input name="fileEntryIds" type="hidden" />

	<aui:layout cssClass="view">
		<aui:column columnWidth="<%= 20 %>" first="<%= true %>">
			<div class="header-row">
				<div class="header-row-content"> </div>
			</div>

			<div class="body-row">
				<div id="<portlet:namespace />folderContainer">
					<liferay-util:include page="/html/portlet/library_admin/view_folders.jsp" />
				</div>
			</div>
		</aui:column>

		<aui:column columnWidth="<%= showFolderMenu ? 80 : 100 %>" cssClass="context-pane" last="<%= true %>">
			<div class="header-row">
				<div class="header-row-content">
					<div class="toolbar">
						<liferay-util:include page="/html/portlet/library_admin/toolbar.jsp" />
					</div>

					<div class="display-style">
						<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>

						<aui:input cssClass="keywords" label="" name="keywords" type="text" />
					</div>
				</div>
			</div>

			<div class="document-container" id="<portlet:namespace />documentContainer">
				<c:if test='<%= true %>'>
					<liferay-util:include page="/html/portlet/library_admin/view_entries.jsp" />
				</c:if>
			</div>
		</aui:column>
	</aui:layout>
</aui:form>

<%
if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
		PortalUtil.setPageSubtitle(folder.getName(), request);
		PortalUtil.setPageDescription(folder.getDescription(), request);
	}
}
%>

<aui:script use="aui-dialog,aui-dialog-iframe">
	Liferay.provide(
		window,
		'<portlet:namespace />doFileEntryAction',
		function(action) {
			if (action == '<%= Constants.DELETE %>') {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
					document.<portlet:namespace />fm.method = "post";
					document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
					document.<portlet:namespace />fm.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>');
					submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>");
				}
			}
			else if (action == '<%= Constants.MOVE %>') {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
				document.<portlet:namespace />fm.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>');
				submitForm(document.<portlet:namespace />fm, "<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_file_entry" /></portlet:renderURL>");
			}
			else {
				document.<portlet:namespace />fm.method = "post";
				document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = action;
				document.<portlet:namespace />fm.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>');
				submitForm(document.<portlet:namespace />fm, "<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>");
			}
		},
		['liferay-util-list-fields']
	);

	A.one('.document-container').delegate(
		'change',
		function(event) {
			markSelected(event.currentTarget);

			Liferay.Util.checkAllBox(A.one('.document-container'), '<portlet:namespace /><%= RowChecker.ROW_IDS %>', '#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>');
		},
		'.document-selector'
	);


	<c:if test='<%= (!displayStyle.equals("list")) %>'>
		A.one('.document-container').delegate(
			'focus',
			function(event) {
				var documentView = event.currentTarget.ancestor('.document-display-style');

				if (documentView) {
					documentView.addClass('hover');
				}
			}
		);

		A.one('.document-container').delegate(
			'blur',
			function(event) {
				var documentView = event.currentTarget.ancestor('.document-display-style');
				documentView.removeClass('hover');
			}
		);
	</c:if>

	function markSelected(e) {
		var documentThumbnail = e.ancestor('.document-display-style');

		documentThumbnail.toggleClass("selected");
	}

	var buttonRow = A.one('#<portlet:namespace />displayStyleToolbar');

	var permissionPopUp = null;
	var popUp = null;

	var displayStyleToolbar = new A.Toolbar(
		{
			activeState: false,
			boundingBox: buttonRow,
			children: [
				{

					<portlet:renderURL var="iconDisplayStyle">
						<portlet:param name="struts_action" value="/library_admin/view" />
						<portlet:param name="displayStyle" value="icon" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					classNames: {'default': 'aui-icon-display-icon'},
					handler: function(event) {
						location.href = '<%= iconDisplayStyle.toString() %>';
					},
					icon: 'pin-b'
				},
				{

					<portlet:renderURL var="descriptiveDisplayStyle">
						<portlet:param name="struts_action" value="/library_admin/view" />
						<portlet:param name="displayStyle" value="descriptive" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					classNames: {'default': 'aui-icon-display-descriptive'},
					handler: function(event) {
						location.href = '<%= descriptiveDisplayStyle.toString() %>';
					},
					icon: 'pin-b'
				},
				{

					<portlet:renderURL var="listDisplayStyle">
						<portlet:param name="struts_action" value="/library_admin/view" />
						<portlet:param name="displayStyle" value="list" />
						<portlet:param name="saveDisplayStyle" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					activeState: true,
					classNames: {'default': 'aui-icon-display-list'},
					handler: function(event) {
						location.href = '<%= listDisplayStyle.toString() %>';
					},
					icon: 'pin-b'
				},
			]
		}
	).render();

	buttonRow.setData('displayStyleToolbar', displayStyleToolbar);

</aui:script>

<aui:script use="liferay-list-view">
	var listView = new Liferay.ListView(
		{
			itemAttributes: ['data-resource-url', 'data-expand'],
			itemSelector: 'ul > li > a',
			srcNode: '#<portlet:namespace />folderContainer'
		}
	);

	listView.render();

	listView.on('itemChosen', function(event) {
		var details = event.details[0];

		var target = details.target;
		var attributes = details.attributes;

		var dataResourceUrl = attributes['data-resource-url'];
		var dataExpand = attributes['data-expand'];

		A.io.request(
			dataResourceUrl,
			{
				on: {
					success: function(event, id, obj) {
						var selFolder = A.one('.folder.selected');

						if (selFolder) {
							selFolder.removeClass('selected');
						}

						var responseData = this.get('responseData');

						var content = A.Node.create(responseData);

						target.ancestor('.folder').addClass('selected');

						if (dataExpand) {
							listView.set('data', content);
						}
						else {
							var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');
							var addButton = content.one('#addButton')

							addButtonContainer.empty();
							addButtonContainer.appendChild(addButton);

							var entriesContainer = A.one('#<portlet:namespace />documentContainer');

							var entries = content.one('#entries');

							entriesContainer.empty();
							entriesContainer.appendChild(entries);
						}
					}
				}
			}
		);
		}
	);

	A.one('.document-container').delegate(
		'click',
		function(event) {

			event.preventDefault();

			var requestUrl = event.currentTarget.getAttribute('data-resource-url');

			A.io.request(
				requestUrl,
				{
					on: {
						success: function(event, id, obj) {
							var selFolder = A.one('.folder.selected');

							if (selFolder) {
								selFolder.removeClass('selected');
							}

							var responseData = this.get('responseData');

							var content = A.Node.create(responseData);

							var folders = content.one('#folders');
							listView.set('data', folders);

							var addButton = content.one('#addButton');

							var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');

							addButtonContainer.empty();
							addButtonContainer.appendChild(addButton);

							var entries = content.one('#entries');

							var entriesContainer = A.one('#<portlet:namespace />documentContainer');

							entriesContainer.empty();
							entriesContainer.appendChild(entries);
						}
					}
				}
			);
		},
		'a[data-isFolder=true]'
	);
</aui:script>