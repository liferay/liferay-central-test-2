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

<%
Folder folder = (com.liferay.portal.kernel.repository.model.Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

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

String displayStyle = ParamUtil.getString(request, "displayStyle");

if (Validator.isNull(displayStyle)) {
	displayStyle = portalPreferences.getValue(PortletKeys.DOCUMENT_LIBRARY, "display-style", "icon");
}

String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");

if (Validator.isNotNull(orderByCol) && Validator.isNotNull(orderByType)) {
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-col", orderByCol);
	portalPreferences.setValue(PortletKeys.DOCUMENT_LIBRARY, "order-by-type", orderByType);
}

request.setAttribute("view.jsp-folder", folder);

request.setAttribute("view.jsp-folderId", String.valueOf(folderId));

request.setAttribute("view.jsp-repositoryId", String.valueOf(repositoryId));
%>

<div class="portlet-msg-error yui3-aui-helper-hidden" id="<portlet:namespace />errorContainer">
	<liferay-ui:message key="your-request-failed-to-complete" />
</div>

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:layout cssClass="view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<liferay-util:include page="/html/portlet/document_library/view_folders.jsp" />
		</aui:column>

		<aui:column columnWidth="<%= showFolderMenu ? 80 : 100 %>" cssClass="context-pane" last="<%= true %>">
			<span class="search-button-container" id="<portlet:namespace />fileEntrySearchContainer">
				<liferay-util:include page="/html/portlet/document_library/file_entry_search.jsp" />
			</span>

			<liferay-portlet:renderURL varImpl="editFileEntryURL">
				<portlet:param name="struts_action" value="/document_library/edit_file_entry" />
			</liferay-portlet:renderURL>

			<aui:form action="<%= editFileEntryURL.toString() %>" method="get" name="fm2">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="deleteEntryIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />

				<div class="header-row">
					<div class="header-row-content">
						<div class="toolbar">
							<liferay-util:include page="/html/portlet/document_library/toolbar.jsp" />
						</div>

						<div class="display-style">
							<span class="toolbar" id="<portlet:namespace />displayStyleToolbar"></span>
						</div>
					</div>
				</div>

				<div class="document-container" id="<portlet:namespace />documentContainer">
					<c:if test='<%= true %>'>
						<liferay-util:include page="/html/portlet/document_library/view_entries.jsp" />
					</c:if>
				</div>
			</aui:form>
		</aui:column>
	</aui:layout>

	<div class="document-library-breadcrumb" id="<portlet:namespace />breadcrumbContainer">
		<liferay-util:include page="/html/portlet/document_library/breadcrumb.jsp" />
	</div>
</div>

<%
if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);

	if (portletName.equals(PortletKeys.DOCUMENT_LIBRARY)) {
		PortalUtil.setPageSubtitle(folder.getName(), request);
		PortalUtil.setPageDescription(folder.getDescription(), request);
	}
}
%>

<aui:script>
	function <portlet:namespace />editFileEntry(action) {
		if (action == '<%= Constants.DELETE %>') {
			if (confirm('<%= UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
				<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>');
			}
		}
		else if (action == '<%= Constants.MOVE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_file_entry" /></portlet:renderURL>');
		}
		else {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_file_entry" /></portlet:actionURL>');
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />doFileEntryAction',
		function(action, url) {
			document.<portlet:namespace />fm2.method = "post";
			document.<portlet:namespace />fm2.<portlet:namespace /><%= Constants.CMD %>.value = action;
			document.<portlet:namespace />fm2.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox');

			submitForm(document.<portlet:namespace />fm2, url);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var markSelected = function(node) {
		var documentThumbnail = node.ancestor('.document-display-style');

		documentThumbnail.toggleClass('selected');
	};

	var documentContainer = A.one('#<portlet:namespace />documentContainer');

	documentContainer.delegate(
		'change',
		function(event) {
			markSelected(event.currentTarget);

			Liferay.Util.checkAllBox(documentContainer, '<portlet:namespace /><%= RowChecker.ROW_IDS %>Checkbox', '#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox');
		},
		'.document-selector'
	);

	<c:if test='<%= (!displayStyle.equals("list")) %>'>
		var toggleHoverClass = function(event) {
			var documentDisplayStyle = event.currentTarget.ancestor('.document-display-style');

			if (documentDisplayStyle) {
				documentDisplayStyle.toggleClass('hover', (event.type == 'focus'));
			}
		};

		documentContainer.delegate('focus', toggleHoverClass, '*');

		documentContainer.delegate('blur', toggleHoverClass, '*');
	</c:if>
</aui:script>

<span id="<portlet:namespace />displayStyleButtonsContainer">
	<liferay-util:include page="/html/portlet/document_library/display_style_buttons.jsp" />
</span>

<aui:script use="liferay-list-view">
	var listView = new Liferay.ListView(
		{
			itemAttributes: ['data-direction-right', 'data-refresh-entries', 'data-refresh-folders', 'data-resource-url'],
			itemSelector: '.folder a',
			srcNode: '#<portlet:namespace />folderContainer'
		}
	).render();

	listView.on(
		'itemChosen',
		function(event) {
			var item = event.item;
			var attributes = event.attributes;

			var dataDirectionRight = attributes['data-direction-right'];
			var dataRefreshEntries = attributes['data-refresh-entries'];
			var dataRefreshFolders = attributes['data-refresh-folders'];
			var dataResourceUrl = attributes['data-resource-url'];

			var entriesContainer = A.one('#<portlet:namespace />documentContainer');

			entriesContainer.plug(A.LoadingMask);

			entriesContainer.loadingmask.toggle();

			A.io.request(
				dataResourceUrl,
				{
					after: {
						success: function(event, id, obj) {
							entriesContainer.unplug(A.LoadingMask);

							var selFolder = A.one('.folder.selected');

							if (selFolder) {
								selFolder.removeClass('selected');
							}

							var responseData = this.get('responseData');

							var content = A.Node.create(responseData);

							item.ancestor('.folder').addClass('selected');

							var direction = 'left';

							if (dataDirectionRight) {
								direction = 'right';
							}

							listView.set('direction', direction);

							if (dataRefreshEntries) {
								var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');
								var addButton = content.one('#<portlet:namespace />addButton')

								addButtonContainer.setContent(addButton);

								A.one('#<portlet:namespace />displayStyleToolbar').empty();

								var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');
								var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

								displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
								displayStyleButtonsContainer.setContent(displayStyleButtons);

								var fileEntrySearchContainer = A.one('#<portlet:namespace />fileEntrySearchContainer');
								var fileEntrySearch = content.one('#<portlet:namespace />fileEntrySearch');

								fileEntrySearchContainer.plug(A.Plugin.ParseContent);
								fileEntrySearchContainer.setContent(fileEntrySearch);

								var parentFolderTitleContainer = A.one('#<portlet:namespace />parentFolderTitleContainer');
								var parentFolderTitle = content.one('#<portlet:namespace />parentFolderTitle');

								if (parentFolderTitle) {
									parentFolderTitleContainer.setContent(parentFolderTitle);
								}

								var breadcrumbContainer = A.one('#<portlet:namespace />breadcrumbContainer');

								var breadcrumb = content.one('#<portlet:namespace />breadcrumb');

								breadcrumbContainer.setContent(breadcrumb);

								var entries = content.one('#<portlet:namespace />entries');

								entriesContainer.setContent(entries);
							}

							if (dataRefreshFolders) {
								var foldersContent = content.one('#<portlet:namespace />folderContainer');

								if (foldersContent) {
									listView.set('data', foldersContent);
								}
							}
						}
					}
				}
			);
		}
	);

	A.one('#<portlet:namespace />documentLibraryContainer').delegate(
		'click',
		function(event) {
			event.preventDefault();

			var requestUrl = event.currentTarget.attr('data-resource-url');

			if (!requestUrl) {
				requestUrl = event.currentTarget.attr('href');
			}

			var entriesContainer = A.one('#<portlet:namespace />documentContainer');

			entriesContainer.plug(A.LoadingMask);

			entriesContainer.loadingmask.toggle();

			A.io.request(
				requestUrl,
				{
					after: {
						success: function(event, id, obj) {
							entriesContainer.unplug(A.LoadingMask);

							var selFolder = A.one('.folder.selected');

							if (selFolder) {
								selFolder.removeClass('selected');
							}

							var responseData = this.get('responseData');

							var content = A.Node.create(responseData);

							var folders = content.one('#<portlet:namespace />folderContainer');

							listView.set('data', folders);

							var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');
							var addButton = content.one('#<portlet:namespace />addButton');

							addButtonContainer.setContent(addButton);

							A.one('#<portlet:namespace />displayStyleToolbar').empty();

							var displayStyleButtonsContainer = A.one('#<portlet:namespace />displayStyleButtonsContainer');
							var displayStyleButtons = content.one('#<portlet:namespace />displayStyleButtons');

							displayStyleButtonsContainer.plug(A.Plugin.ParseContent);
							displayStyleButtonsContainer.setContent(displayStyleButtons);

							var fileEntrySearchContainer = A.one('#<portlet:namespace />fileEntrySearchContainer');
							var fileEntrySearch = content.one('#<portlet:namespace />fileEntrySearch');

							fileEntrySearchContainer.plug(A.Plugin.ParseContent);
							fileEntrySearchContainer.setContent(fileEntrySearch);

							var parentFolderTitleContainer = A.one('#<portlet:namespace />parentFolderTitleContainer');
							var parentFolderTitle = content.one('#<portlet:namespace />parentFolderTitle');

							parentFolderTitleContainer.setContent(parentFolderTitle);

							var breadcrumbContainer = A.one('#<portlet:namespace />breadcrumbContainer');
							var breadcrumb = content.one('#<portlet:namespace />breadcrumb');

							breadcrumbContainer.setContent(breadcrumb);

							var entries = content.one('#<portlet:namespace />entries');

							entriesContainer.setContent(entries);
						}
					}
				}
			);
		},
		'a[data-folder=true], #<portlet:namespace />breadcrumbContainer a'
	);
</aui:script>