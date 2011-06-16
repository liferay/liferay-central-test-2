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

long start = ParamUtil.getLong(request, "start");
long end = ParamUtil.getLong(request, "end", SearchContainer.DEFAULT_DELTA);

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

<div class="portlet-msg-error aui-helper-hidden" id="<portlet:namespace />errorContainer">
	<liferay-ui:message key="your-request-failed-to-complete" />
</div>

<div id="<portlet:namespace />documentLibraryContainer">
	<aui:layout cssClass="view">
		<aui:column columnWidth="<%= 20 %>" cssClass="navigation-pane" first="<%= true %>">
			<div class="header-row">
				<div class="header-row-content" id="<portlet:namespace />parentFolderTitleContainer">
					<div class="parent-folder-title" id="<portlet:namespace />parentFolderTitle"></div>
				</div>
			</div>

			<div class="body-row">
				<div id="<portlet:namespace />folderContainer"></div>
			</div>
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
				<aui:input name="repositoryId" type="hidden" value="<%= repositoryId %>" />
				<aui:input name="folderIds" type="hidden" />
				<aui:input name="fileEntryIds" type="hidden" />
				<aui:input name="fileShortcutIds" type="hidden" />

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

				<div class="document-container" id="<portlet:namespace />documentContainer"></div>

				<div class="document-entries-paginator"></div>
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
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
		else if (action == '<%= Constants.MOVE %>') {
			<portlet:namespace />doFileEntryAction(action, '<portlet:renderURL><portlet:param name="struts_action" value="/document_library/move_entry" /></portlet:renderURL>');
		}
		else {
			<portlet:namespace />doFileEntryAction(action, '<portlet:actionURL><portlet:param name="struts_action" value="/document_library/edit_entry" /></portlet:actionURL>');
		}
	}

	Liferay.provide(
		window,
		'<portlet:namespace />doFileEntryAction',
		function(action, url) {
			document.<portlet:namespace />fm2.method = "post";
			document.<portlet:namespace />fm2.<portlet:namespace /><%= Constants.CMD %>.value = action;
			document.<portlet:namespace />fm2.<portlet:namespace />folderIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS  + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileEntryIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox');
			document.<portlet:namespace />fm2.<portlet:namespace />fileShortcutIds.value = Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox');

			submitForm(document.<portlet:namespace />fm2, url);
		},
		['liferay-util-list-fields']
	);

	Liferay.provide(
		window,
		'<portlet:namespace />toggleActionsButton',
		function() {
			var A = AUI();

			var actionsButton = A.one('#<portlet:namespace />actionsButtonContainer ul');

			var disabled = (Liferay.Util.listCheckedExcept(document.<portlet:namespace />fm2, '<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox').length == 0);

			actionsButton.toggleClass('disabled', disabled);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<aui:script use="aui-dialog,aui-dialog-iframe">
	var markSelected = function(node) {
		var documentThumbnail = node.ancestor('.document-display-style.selectable');

		documentThumbnail.toggleClass('selected');
	};

	var documentContainer = A.one('#<portlet:namespace />documentContainer');

	documentContainer.delegate(
		'change',
		function(event) {
			markSelected(event.currentTarget);

			<portlet:namespace />toggleActionsButton();

			Liferay.Util.checkAllBox(documentContainer, ['<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + FileEntry.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + DLFileShortcut.class.getName() %>Checkbox', '<portlet:namespace /><%= RowChecker.ROW_IDS + StringPool.UNDERLINE + Folder.class.getName() %>Checkbox'], '#<portlet:namespace /><%= RowChecker.ALL_ROW_IDS %>Checkbox');
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

<aui:script use="aui-paginator,liferay-list-view,history-hash">
	<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" varImpl="mainURL">
	</liferay-portlet:resourceURL>

	var AObject = A.Object;

	var CP_HANDLER = '<portlet:namespace />handler';

	var EVENT_DATA_REQUEST = '<portlet:namespace />dataRequest';

	var EVENT_DATA_RETRIEVE_SUCCESS = '<portlet:namespace />dataRetrieveSuccess';

	var INVALID_VALUE = A.Attribute.INVALID_VALUE;

	var owns = AObject.owns;

	var Lang = A.Lang;

	var DEFAULT_CALLBACK_PARAMS = {
		'<portlet:namespace />handler': 'mainEntry'
	};

	var DEFAULT_REQUEST_PARAMS = {
		'<portlet:namespace />struts_action': '/document_library/view',
		'<portlet:namespace />folderId': '<%= folderId %>',
		'<portlet:namespace />displayStyle': '<%= displayStyle %>',
		'<portlet:namespace />showSiblings': <%= Boolean.TRUE.toString() %>,
		'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
		'<portlet:namespace />viewFolders': <%= Boolean.TRUE.toString() %>
	};

	var SRC_HISTORY = 0;

	var REGEX_REQUEST_PARAM = /^<portlet:namespace />.+_r$/i;

	var REGEX_REQUEST_PARAM_SUFFIX = /_r$/i;

	var REGEX_CALLBACK_PARAM = /^<portlet:namespace />.+_c$/i;

	var REGEX_CALLBACK_PARAM_SUFFIX = /_c$/i;

	var entriesContainer = A.one('#<portlet:namespace />documentContainer');

	var documentLibraryContainer = A.one('#<portlet:namespace />documentLibraryContainer');

	var history = new A.HistoryHash();

	var ioRequest;

	function addHistoryEntry(data, callbackParams) {
		var historyState = {};

		AObject.each(
			data,
			function(index, item, collection) {
				historyState[item + '_r'] = collection[item];
			}
		);

		AObject.each(
			callbackParams,
			function(index, item, collection) {
				historyState[item + '_c'] = collection[item];
			}
		);

		var currentHistoryState = history.get();

		AObject.each(
			currentHistoryState,
			function(index, item, collection) {
				if (!owns(historyState, item)) {
					historyState[item] = null;
				}
			}
		);

		if (!AObject.isEmpty(historyState)) {
			history.add(historyState);
		}
	}

	function afterHistoryChange(event) {
		if (event.src === A.HistoryHash.SRC_HASH) {
			var state = history.get();

			var requestParams = {};

			var callbackParams = {};

			AObject.each(
				state,
				function(value, key, collection) {
					if (REGEX_REQUEST_PARAM.test(key)) {
						requestParams[key.replace(REGEX_REQUEST_PARAM_SUFFIX, '')] = value;
					}
					else if (REGEX_CALLBACK_PARAM.test(key)) {
						callbackParams[key.replace(REGEX_CALLBACK_PARAM_SUFFIX, '')] = value;
					}
				}
			);

			if (!AObject.isEmpty(requestParams)) {
				Liferay.fire(
					EVENT_DATA_REQUEST,
					{
						requestParams: requestParams,
						callbackParams: callbackParams,
						src: SRC_HISTORY
					}
				);
			}
		}
	}

	function afterListViewItemChange(event) {
		var selFolder = A.one('.folder.selected');

		if (selFolder) {
			selFolder.removeClass('selected');
		}

		var item = event.newVal;

		item.ancestor('.folder').addClass('selected');

		var dataDirectionRight = item.attr('data-direction-right');
		var dataDocumentTypeId = item.attr('data-document-type-id');
		var dataFolderId = item.attr('data-folder-id');
		var dataNavigation = item.attr('data-navigation');
		var dataRefreshEntries = item.attr('data-refresh-entries');
		var dataRefreshFolders = item.attr('data-refresh-folders');
		var dataShowSiblings = item.attr('data-show-siblings');
		var dataViewFolders = item.attr('data-view-folders');

		var direction = 'left';

		if (dataDirectionRight) {
			direction = 'right';
		}

		listView.set('direction', direction);

		var config = {
			'<portlet:namespace />struts_action': '/document_library/view',
			'<portlet:namespace />viewAddButton': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewBreadcrumb': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewDisplayStyleButtons': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewEntries': <%= Boolean.TRUE.toString() %>,
			'<portlet:namespace />viewFileEntrySearch': <%= Boolean.TRUE.toString() %>
		};

		if (dataFolderId) {
			config['<portlet:namespace />folderId'] = dataFolderId;
		}

		if (dataNavigation) {
			config['<portlet:namespace />navigation'] = dataNavigation;
		}

		if (dataShowSiblings) {
			config['<portlet:namespace />showSiblings'] = dataShowSiblings;
		}

		if (dataViewFolders) {
			config['<portlet:namespace />viewFolders'] = dataViewFolders;
		}

		if (dataDocumentTypeId) {
			config['<portlet:namespace />documentTypeId'] = dataDocumentTypeId;
		}

		Liferay.fire(
			EVENT_DATA_REQUEST,
			{
				requestParams: config,
				callbackParams: {
					'<portlet:namespace />handler': 'itemChosen',
					dataRefreshEntries: dataRefreshEntries,
					dataRefreshFolders: dataRefreshFolders
				}
			}
		);
	}

	function getIORequest() {
		if (!ioRequest) {
			ioRequest = A.io.request(
				'<%= mainURL %>',
				{
					autoLoad: false,
					after: {
						success: function(event, id, obj){
							var instance = this;

							sendIOResponse.call(instance, true);
						},
						failure: function(event, id, obj){
							var instance = this;

							sendIOResponse.call(instance, false);
						}
					}
				}
			);
		}

		return ioRequest;
	}

	function getResultsStartEnd(page, rowsPerPage) {
		if (!Lang.isValue(page)) {
			page = entryPaginator.get('page') || 0;
		}

		if (!Lang.isValue(rowsPerPage)) {
			rowsPerPage = entryPaginator.get('rowsPerPage');
		}

		var start = page * rowsPerPage;
		var end = start + rowsPerPage;

		return [start, end];
	}

	function handleBrowseFolder(event) {
		documentLibraryContainer.loadingmask.hide();

		var responseData = event.responseData;

		var content = A.Node.create(responseData);

		var folders = content.one('#<portlet:namespace />folderContainer');

		listView.set('data', folders);

		var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');
		var addButton = content.one('#<portlet:namespace />addButton');

		addButtonContainer.plug(A.Plugin.ParseContent);
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

	function handleEntryPaginator(event) {
		var rowsPerPage = event.callbackParams['<portlet:namespace />rowsPerPage'];

		if (rowsPerPage) {
			var currentRowsPerPage = entryPaginator.get('rowsPerPage');

			if (rowsPerPage !== currentRowsPerPage) {
				entryPaginator.set('rowsPerPage', Number(rowsPerPage));
			}
		}
		else {
			entryPaginator.set('rowsPerPage', <%= SearchContainer.DEFAULT_DELTA %>);
		}
	}

	function handleItemChosen(event) {
		documentLibraryContainer.loadingmask.hide();

		var responseData = event.responseData;

		var content = A.Node.create(responseData);

		var callbackParams = event.callbackParams;

		if (callbackParams.dataRefreshEntries) {
			var addButtonContainer = A.one('#<portlet:namespace />addButtonContainer');
			var addButton = content.one('#<portlet:namespace />addButton');

			addButtonContainer.plug(A.Plugin.ParseContent);
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

		if (callbackParams.dataRefreshFolders) {
			var foldersContent = content.one('#<portlet:namespace />folderContainer');

			if (foldersContent) {
				listView.set('data', foldersContent);
			}
		}
	}

	function handleMainEntry(event) {
		var data = event.data;
		var responseData = event.responseData;

		documentLibraryContainer.loadingmask.hide();

		var content = A.Node.create(responseData);

		var folders = content.one('#<portlet:namespace />folderContainer');

		listView.set('data', folders);

		var entries = content.one('#<portlet:namespace />entries')

		entriesContainer.plug(A.Plugin.ParseContent);
		entriesContainer.setContent(entries);
	}

	function mainEntry() {
		var initialState = history.get();

		if (!AObject.isEmpty(initialState)) {
			DEFAULT_REQUEST_PARAMS = {};

			DEFAULT_CALLBACK_PARAMS = {};

			AObject.each(
				initialState,
				function(value, key, collection) {
					if (REGEX_REQUEST_PARAM.test(key)) {
						DEFAULT_REQUEST_PARAMS[key.replace(REGEX_REQUEST_PARAM_SUFFIX, '')] = value;
					}
					else if (REGEX_CALLBACK_PARAM.test(key)) {
						DEFAULT_CALLBACK_PARAMS[key.replace(REGEX_CALLBACK_PARAM_SUFFIX, '')] = value;
					}
				}
			);
		}

		Liferay.fire(
			EVENT_DATA_REQUEST,
			{
				requestParams: DEFAULT_REQUEST_PARAMS,
				callbackParams: DEFAULT_CALLBACK_PARAMS
			}
		);
	}

	function onDataRequest(event) {
		var requestParams = event.requestParams;
		var callbackParams = event.callbackParams;

		var itemChosen = listView.get('item');

		var data = {
			'<portlet:namespace />start': 0,
			'<portlet:namespace />end': 20,
			'<portlet:namespace />folderId': '<%= DLFolderConstants.DEFAULT_PARENT_FOLDER_ID %>',
			'<portlet:namespace />displayStyle': '<portlet:namespace />view'
		};

		if (requestParams) {
	        A.mix(data, requestParams, true);
		}

		documentLibraryContainer.loadingmask.show();

		if (event.src !== SRC_HISTORY) {
			addHistoryEntry(data, callbackParams);
		}

		ioRequest = getIORequest();

		ioRequest.set('arguments', callbackParams);
		ioRequest.set('data', data);

		ioRequest.start();
	}

	function sendIOResponse(result) {
		var instance = this;

		var arguments = instance.get('arguments');
		var data = instance.get('data');
		var reponseData = instance.get('responseData');

		var event = result ? EVENT_DATA_RETRIEVE_SUCCESS : '<portlet:namespace />dataRetrieveFailure';

		Liferay.fire(
			event,
			{
				callbackParams: arguments,
				data: data,
				responseData: reponseData
			}
		);
	}

	var entryPaginator = new A.Paginator(
		{
			circular: false,
			containers: '.document-entries-paginator',
			firstPageLinkLabel: '<<',
			lastPageLinkLabel: '>>',
			nextPageLinkLabel: '>',
			prevPageLinkLabel: '<',
			rowsPerPage: <%= SearchContainer.DEFAULT_DELTA %>,
			rowsPerPageOptions: [<%= StringUtil.merge(PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) %>]
		}
	).render();

	entryPaginator.on(
		'changeRequest',
		function(event) {
			var state = event.state;

			var ioRequest = getIORequest();

			var callbackParams = ioRequest.get('arguments');
			var requestParams = ioRequest.get('data');

			var resultsStartEnd = getResultsStartEnd(state.page - 1, state.rowsPerPage);

			A.mix(
				requestParams,
				{
					'<portlet:namespace />start': resultsStartEnd[0],
					'<portlet:namespace />end': resultsStartEnd[1],
				},
				true
			);
			
			var previousState = state.before;

			if (previousState.rowsPerPage !== state.rowsPerPage) {
				A.mix(
					callbackParams,
					{
						'<portlet:namespace />rowsPerPage': state.rowsPerPage
					},
					true
				);
			}

			Liferay.fire(
				EVENT_DATA_REQUEST,
				{
					requestParams: requestParams,
					callbackParams: callbackParams
				}
			);
		}
	);

	Liferay.on(
		EVENT_DATA_RETRIEVE_SUCCESS,
		function(event) {
			var data = event.data;

			if (event.callbackParams) {
				var handlers = event.callbackParams[CP_HANDLER]

				if (handlers) {
					handlers = handlers.split('|');

					var callback;

					A.Array.each(
						handlers,
						function(value, index, collection) {
							if (value === 'mainEntry') {
								callback = handleMainEntry;
							}
							else if (value === 'itemChosen') {
								callback = handleItemChosen;
							}
							else if (value === 'browseFolder') {
								callback = handleBrowseFolder;
							}
							else if (value === 'ePaginator') {
								callback = handleEntryPaginator;
							}

							if (callback) {
								callback(event);
							}
						}
					);
				}
			}
		}
	);

	Liferay.on(
		'viewEntriesLoaded',
		function(event) {
			entryPaginator.setState(
				{
					page: event.page,
					rowsPerPage: event.rowsPerPage,
					total: event.total
				}
			);

			paginationURL = event.paginationURL;
		}
	);

	Liferay.on(EVENT_DATA_REQUEST, onDataRequest);

	var listView = new Liferay.ListView(
		{
			itemSelector: '.folder a',
			srcNode: '#<portlet:namespace />folderContainer'
		}
	).render();

	listView.after('itemChange', afterListViewItemChange);

	A.one('#<portlet:namespace />documentLibraryContainer').delegate(
		'click',
		function(event) {
			event.preventDefault();

			Liferay.fire(
				EVENT_DATA_REQUEST,
	            {
					requestParams: {
						<portlet:namespace />action: 'browseFolder',
						<portlet:namespace />folderId: event.currentTarget.attr('data-folderId'),
						<portlet:namespace />showSiblings: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />struts_action: '/document_library/view',
						<portlet:namespace />viewAddButton: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />viewBreadcrumb: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />viewDisplayStyleButtons: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />viewEntries: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />viewFileEntrySearch: <%= Boolean.TRUE.toString() %>,
						<portlet:namespace />viewFolders: <%= Boolean.TRUE.toString() %>
					},
					callbackParams: {
						'<portlet:namespace />handler': 'browseFolder',
					}
				}
			);
		},
		'a[data-folder=true], #<portlet:namespace />breadcrumbContainer a'
	);

	history.after('change', afterHistoryChange);

	documentLibraryContainer.plug(A.LoadingMask);

	mainEntry();

</aui:script>