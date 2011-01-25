AUI().add(
	'liferay-tags-admin',
	function(A) {
		var Lang = A.Lang;
		var Node = A.Node;

		var ACTION_ADD = 0;

		var ACTION_EDIT = 1;

		var ACTION_VIEW = 2;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_TAG_DIALOG = 'portlet-asset-tag-admin-dialog';

		var DRAG_NODE = 'dragNode';

		var EVENT_CLICK  = 'click';

		var EVENT_SUBMIT = 'submit';

		var LIFECYCLE_RENDER = 0;

		var LIFECYCLE_PROCESS = 1;

		var MESSAGE_TYPE_ERROR = 'error';

		var MESSAGE_TYPE_SUCCESS = 'success';

		var NODE = 'node';

		var TPL_PORTLET_MESSAGES = '<div class="aui-helper-hidden lfr-message-response" id="portletMessages" />';

		var TPL_TAG_LIST = '<li class="tag-item-container results-row {cssClassSelected}" data-tag="{name}" data-tagId="{tagId}" tabIndex="0">' +
			'<div class="tags-admin-content-wrapper">' +
					'<span class="tag-item">' +
						'<a href="javascript:;" data-tagId="{tagId}" tabIndex="-1">{name}</a>' +
					'</span>' +
					'<a href="javascript:;" class="tag-item-actions-trigger" data-tagId="{tagId}"></a>' +
			'</div>' +
		'</li>';

		var TPL_TAGS_MESSAGES = '<div class="aui-helper-hidden lfr-message-response portlet-msg-info" id="tagsMessages" />';

		var AssetTagsAdmin = A.Component.create(
			{
				NAME: 'assettagsadmin',

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.portletId = config.portletId;
						instance._prefixedPortletId = '_' + config.portletId + '_';

						instance._container = A.one('.tags-admin-container');
						instance._tagViewContainer = A.one('.tag-view-container');
						instance._tagsList = A.one('.tags-admin-list');

						instance._tagsMessageContainer = Node.create(TPL_TAGS_MESSAGES);
						instance._portletMessageContainer = Node.create(TPL_PORTLET_MESSAGES);

						instance._container.placeBefore(instance._portletMessageContainer);

						var tagDataContainer = A.one('.tags-admin-edit-tag');

						instance._dialogAlignConfig = {
							node: tagDataContainer,
							points: ['tc', 'tc']
						};

						instance._hideMessageTask = new A.DelayedTask(
							function() {
								instance._portletMessageContainer.hide();
							}
						);

						instance._tagsList.on(EVENT_CLICK, instance._onTagsListClick, instance);
						instance._tagsList.on('key', instance._onTagsListSelect, 'up:13', instance);

						instance._tagViewContainer.on(EVENT_CLICK, instance._onTagViewContainerClick, instance);

						var namespace = instance._prefixedPortletId;

						A.one('#' + namespace + 'addTagButton').on(EVENT_CLICK, instance._onShowTagPanel, instance, ACTION_ADD);
						A.one('#' + namespace + 'tagsPermissionsButton').on(EVENT_CLICK, instance._onTagChangePermissions, instance);

						instance._loadData();

						instance.after('drag:drag', instance._afterDrag);
						instance.after('drag:drophit', instance._afterDragDrop);
						instance.after('drag:enter', instance._afterDragEnter);
						instance.after('drag:exit', instance._afterDragExit);
						instance.after('drag:start', instance._afterDragStart);
					},

					_afterDrag: function(event) {
						var instance = this;

						A.DD.DDM.syncActiveShims(true);
					},

					_afterDragDrop: function(event) {
						var instance = this;

						var dropNode = event.drop.get(NODE);
						var node = event.target.get(NODE);

						dropNode.removeClass(CSS_ACTIVE_AREA);

						instance._merge(node, dropNode);
					},

					_afterDragEnter: function(event) {
						var instance = this;

						var target = event.target;
						var proxyNode = target.get(DRAG_NODE);
						var node = target.get(NODE);
						var dropNode = event.drop.get(NODE);

						var textDestNode = dropNode.one('a').html();
						var textSrcNode = node.one('a').html();

						proxyNode.one('a').html(textDestNode + ' &larr; ' + textSrcNode);

						dropNode.addClass(CSS_ACTIVE_AREA);
					},

					_afterDragExit: function(event) {
						var instance = this;

						var dropNode = event.drop.get(NODE);

						dropNode.removeClass(CSS_ACTIVE_AREA);
					},

					_afterDragStart: function(event) {
						var instance = this;

						var drag = event.target;

						var proxyNode = drag.get(DRAG_NODE);
						var node = drag.get(NODE);

						var clone = proxyNode.get('firstChild');

						if (!clone) {
							clone = node.clone().empty();

							clone.addClass('tag-item-merge');

							proxyNode.attr('data-tagId', clone.attr('data-tagId'));
							proxyNode.appendChild(clone);
						}

						clone.html(node.html());
					},

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get('boundingBox').on('key', contextPanel.hide, 'up:27', contextPanel);
					},

					_createTagPanelAdd: function() {
						var instance = this;

						var tagPanelAdd = new A.Dialog(
							{
								align: instance._dialogAlignConfig,
								cssClass: CSS_TAG_DIALOG,
								resizable: false,
								title: Liferay.Language.get('add-tag'),
								width: 550,
								zIndex: 1000
							}
						).render();

						tagPanelAdd.hide();

						instance._bindCloseEvent(tagPanelAdd);

						instance._tagPanelAdd = tagPanelAdd;

						return tagPanelAdd;
					},

					_createTagPanelEdit: function() {
						var instance = this;

						instance._tagPanelEdit = new A.Dialog(
							{
								align: instance._dialogAlignConfig,
								cssClass: CSS_TAG_DIALOG,
								resizable: false,
								title: Liferay.Language.get('edit-tag'),
								width: 550,
								zIndex: 1000
							}
						).render();

						instance._tagPanelEdit.hide();

						instance._bindCloseEvent(instance._tagPanelEdit);

						instance._tagPanelEdit.after(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									var body = instance._tagPanelEdit.getStdModNode(A.WidgetStdMod.BODY);

									body.empty();
								}
							}
						);

						return instance._tagPanelEdit;
					},

					_createTagPanelPermissions: function() {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!panelPermissionsChange) {
							panelPermissionsChange = new A.Dialog(
								{
									align: instance._dialogAlignConfig,
									cssClass: CSS_TAG_DIALOG + ' permissions-change',
									title: Liferay.Language.get('edit-permissions'),
									width: 600
								}
							).plug(
								A.Plugin.DialogIframe,
								{
									after: {
										load: Liferay.Util.afterIframeLoaded
									}
								}
							).render();

							instance._panelPermissionsChange = panelPermissionsChange;
						}

						return panelPermissionsChange;
					},

					_createURL: function(action, lifecycle, params) {
						var instance = this;

						var path = '/asset_tag_admin/';

						var url;

						if (lifecycle == LIFECYCLE_RENDER) {
							url = Liferay.PortletURL.createRenderURL();
						}
						else if (lifecycle == LIFECYCLE_PROCESS) {
							url = Liferay.PortletURL.createActionURL();
						}
						else {
							throw 'Internal error. Unimplemented lifecycle.';
						}

						url.setPortletId(instance.portletId);
						url.setWindowState('exclusive');

						if (action == ACTION_ADD) {
							path += 'edit_tag';
						}
						else if (action == ACTION_EDIT) {
							path += 'edit_tag';
							url.setParameter('tagId', instance._selectedTagId);
						}
						else if (action == ACTION_VIEW) {
							path += 'view_tag';
							url.setParameter('tagId', instance._selectedTagId);
						}

						url.setParameter('struts_action', path);

						if (params) {
							var hasOwnProperty = Object.prototype.hasOwnProperty;

							for (var key in params) {
								if (hasOwnProperty.call(params, key)) {
									url.setParameter(key, params[key]);
								}
							}
						}

						return url;
					},

					_deleteTag: function(tagId, callback) {
						var instance = this;

						Liferay.Service.Asset.AssetTag.deleteTag(
							{
								tagId: tagId
							},
							callback
						);
					},

					_displayTagData: function(tagId) {
						var instance = this;

						tagId = tagId || instance._selectedTagId;

						if (!tagId) {
							instance._tagViewContainer.empty();
							return;
						}

						var tagURL = instance._createURL(ACTION_VIEW, LIFECYCLE_RENDER);

						var ioDetails = instance._getIOTagDetails();

						ioDetails.set('uri', tagURL.toString()).start();
					},

					_displayTags: function(callback) {
						var instance = this;

						instance._getTags(
							function(tags) {
								instance._prepareTags(tags, callback);
							}
						);
					},

					_focusTagPanelAdd: function() {
						var instance = this;

						var inputTagAddNameNode = instance._tagFormAdd.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagAddNameNode);
					},

					_focusTagPanelEdit: function() {
						var instance = this;

						var inputTagEditNameNode = instance._tagFormEdit.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagEditNameNode);
					},

					_getIOTagUpdate: function() {
						var instance = this;

						var ioTag = instance._ioTag;

						if (!ioTag) {
							ioTag = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj) {
											var response = this.get('responseData');

											instance._onTagUpdateSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onTagUpdateFailure(obj);
										}
									}
								}
							);

							instance._ioTag = ioTag;
						}

						return ioTag;
					},

					_getIOTagDetails: function() {
						var instance = this;

						var ioTagDetails = instance._ioTagDetails;

						if (!ioTagDetails) {
							ioTagDetails = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'html',
									on: {
										success: function(event, id, obj) {
											var response = this.get('responseData');

											instance._onTagViewSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onTagViewFailure(obj);
										}
									}
								}
							);

							instance._ioTagDetails = ioTagDetails;
						}

						return ioTagDetails;
					},

					_getTag: function(tagId) {
						var instance = this;

						return instance._tagsList.one('li[data-tagId="' + tagId + '"]');
					},

					_getTagId: function(expr) {
						var instance = this;

						var elem = expr;
						var attr;

						if (!expr instanceof Node) {
							elem = instance._tagsList.one(expr);
						}

						if (elem) {
							attr = elem.attr('data-tagId');
						}

						return attr;
					},

					_getTagName: function(expr) {
						var instance = this;

						var elem = expr;
						var attr;

						if (!expr instanceof Node) {
							elem = instance._tagsList.one(expr);
						}

						if (elem) {
							attr = elem.attr('data-tag');
						}

						return attr;
					},

					_getTags: function(callback) {
						var instance = this;

						Liferay.Service.Asset.AssetTag.getGroupTags(
							{
								groupId: themeDisplay.getParentGroupId()
							},
							callback
						);
					},

					_initializeTagPanelAdd: function(callback) {
						var instance = this;

						var tagPanelAdd = instance._tagPanelAdd;

						var tagFormAdd = tagPanelAdd.get('contentBox').one('form.update-tag-form');

						tagFormAdd.detach(EVENT_SUBMIT);

						tagFormAdd.on(EVENT_SUBMIT, instance._onTagFormSubmit, instance, tagFormAdd);

						instance._tagFormAdd = tagFormAdd;

						var closeButton = tagFormAdd.one('.aui-button-input-cancel');

						closeButton.on(
							EVENT_CLICK,
							function(event, panel) {
								panel.hide();
							},
							instance,
							tagPanelAdd
						);

						tagPanelAdd.on(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									if (instance._tagFormAdd) {
										instance._tagFormAdd.reset();
									}

									instance._resetTagsProperties(event);
								}
							}
						);

						if (callback) {
							callback.call(instance);
						}

						return tagPanelAdd;
					},

					_initializeTagPanelEdit: function(callback) {
						var instance = this;

						var tagFormEdit = instance._tagPanelEdit.get('contentBox').one('form.update-tag-form');

						tagFormEdit.detach(EVENT_SUBMIT);

						tagFormEdit.on(EVENT_SUBMIT, instance._onTagFormSubmit, instance, tagFormEdit);

						var closeButton = tagFormEdit.one('.aui-button-input-cancel');

						closeButton.on(
							EVENT_CLICK,
							function(event, panel) {
								panel.hide();
							},
							instance,
							instance._tagPanelEdit
						);

						var buttonDeleteTag = tagFormEdit.one('#deleteTagButton');

						if (buttonDeleteTag) {
							buttonDeleteTag.on(EVENT_CLICK, instance._onDeleteTag, instance);
						}

						var buttonChangeTagPermissions = tagFormEdit.one('#updateTagPermissions');

						if (buttonChangeTagPermissions) {
							buttonChangeTagPermissions.on(EVENT_CLICK, instance._onTagChangePermissions, instance);
						}

						var inputTagNameNode = tagFormEdit.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagNameNode);
					},

					_hideAllMessages: function() {
						var instance = this;

						instance._container.all('.lfr-message-response').hide();
					},

					_hidePanels: function() {
						var instance = this;

						if (instance._tagPanelAdd) {
							instance._tagPanelAdd.hide();
						}

						if (instance._tagPanelEdit) {
							instance._tagPanelEdit.hide();
						}
					},

					_loadData: function() {
						var instance = this;

						instance._showLoading('.tags-admin-list, .tag-view-container');

						instance._displayTags(
							function() {
								instance._displayTagData();
							}
						);
					},

					_loadPermissions: function(url) {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!instance._panelPermissionsChange) {
							panelPermissionsChange = instance._createTagPanelPermissions();
						}

						panelPermissionsChange.show();

						panelPermissionsChange.iframe.set('uri', url);

						panelPermissionsChange._syncUIPosAlign();

						if (instance._tagPanelEdit) {
							var zIndex = parseInt(instance._tagPanelEdit.get('zIndex'), 10) + 2;

							panelPermissionsChange.set('zIndex', zIndex);
						}
					},

					_merge: function(node, dropNode) {
						var instance = this;

						var fromTagId = instance._getTagId(node);
						var fromTagName = instance._getTagName(node);
						var toTagId = instance._getTagId(dropNode);
						var toTagName = instance._getTagName(dropNode);

						var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x');

						mergeText = A.substitute(mergeText, [fromTagName, toTagName]);

						if (confirm(mergeText)) {
							instance._mergeTags(
								fromTagId,
								toTagId,
								function() {
									node.remove();

									instance._selectTag(toTagId);
									instance._alternateRows();
								}
							);
						}
					},

					_mergeTags: function(fromId, toId, callback) {
						Liferay.Service.Asset.AssetTag.mergeTags(
							{
								fromTagId: fromId,
								toTagId: toId
							},
							callback
						);
					},

					_onDeleteTag: function(event) {
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-tag'))) {
							instance._deleteTag(
								instance._selectedTagId,
								function(message) {
									var exception = message.exception;

									if (!exception) {
										instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

										instance._hidePanels();
										instance._loadData();
									}
									else {
										var errorText;

										if (exception.indexOf('auth.PrincipalException') > -1) {
											errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
										}
										else {
											errorText = Liferay.Language.get('your-request-failed-to-complete');
										}

										instance._sendMessage(MESSAGE_TYPE_ERROR, errorText);
									}
								}
							);
						}
					},

					_onShowTagPanel: function(event, action) {
						var instance = this;

						instance._hidePanels();

						instance._showTagPanel(action);
					},

					_onTagChangePermissions: function(event) {
						var instance = this;

						var url = event.target.attr('data-url');

						instance._loadPermissions(url);
					},

					_onTagFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						Liferay.fire(
							'saveAutoFields',
							{
								form: form
							}
						);

						instance._updateTag(form);
					},

					_onTagsListClick: function(event) {
						var instance = this;

						instance._onTagsListSelect(event);

						if (event.target.hasClass('tag-item-actions-trigger')) {
							instance._onShowTagPanel(event, ACTION_EDIT);
						}
					},

					_onTagsListSelect: function(event) {
						var instance = this;

						var tagId = instance._getTagId(event.target);

						instance._selectTag(tagId);
					},

					_onTagUpdateFailure: function(response) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onTagUpdateSuccess: function(response) {
						var instance = this;

						instance._hideAllMessages();

						var exception = response.exception;

						if (!response.exception) {
							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._displayTags(
								function() {
									instance._unselectAllTags();
									instance._selectTag(response.tagId);
								}
							);

							instance._hidePanels();
						}
						else {
							var errorText;

							if (exception.indexOf('DuplicateTagException') > -1) {
								errorText = Liferay.Language.get('that-tag-already-exists');
							}
							else if ((exception.indexOf('TagNameException') > -1) ||
									 (exception.indexOf('AssetTagException') > -1)) {
								errorText = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorText = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorText);
						}
					},

					_onTagViewContainerClick: function(event) {
						var instance = this;

						var targetId = event.target.get('id');

						if (targetId == 'editTagButton') {
							instance._onShowTagPanel(event, ACTION_EDIT);
						}
						else if (targetId == 'deleteTagButton') {
							instance._onDeleteTag(event);
						}
						else if (targetId == 'updateTagPermissions') {
							instance._onTagChangePermissions(event);
						}
					},

					_onTagViewFailure: function() {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onTagViewSuccess: function(response) {
						var instance = this;

						instance._tagViewContainer.html(response);
					},

					_resetTagsProperties: function(event) {
						var instance = this;

						var contextPanel = event.currentTarget;
						var boundingBox = contextPanel.get('boundingBox');
						var propertiesTrigger = boundingBox.one('fieldset#tagProperties');

						var autoFieldsInstance = propertiesTrigger.getData('autoFieldsInstance');

						autoFieldsInstance.reset();
					},

					_prepareTags: function(tags, callback) {
						var instance = this;

						if (tags.length > 0) {
							var buffer = ['<ul>'];

							instance._tags = tags;

							A.each(
								tags,
								function(item, index, collection) {
									if (index == 0) {
										item.cssClassSelected = 'selected';
									}
									else {
										item.cssClassSelected = '';
									}

									buffer.push(Lang.sub(TPL_TAG_LIST, item));
								}
							);

							buffer.push('</ul>');

							instance._tagsList.html(buffer.join(''));

							var firstTag = A.one(instance._tagsItemsSelector);
							var tagName = instance._getTagName(firstTag);
							var tagId = instance._getTagId(firstTag);

							instance._selectedTagName = tagName;
							instance._selectedTagId = tagId;
						}
						else {
							var tagsMessageContainer = instance._tagsMessageContainer;

							tagsMessageContainer.html(Liferay.Language.get('there-are-no-tags'));
							instance._tagsList.setContent(tagsMessageContainer);
							tagsMessageContainer.show();
						}

						instance._reloadSearch();
						instance._prepareTagsMerge();

						if (callback) {
							callback();
						}
					},

					_prepareTagsMerge: function() {
						var instance = this;

						var	tagsItems = A.all(instance._tagsItemsSelector);
						var tagsContainer = instance._tagsList;

						tagsItems.on(
							EVENT_CLICK,
							function(event) {
								var tagId = instance._getTagId(event.currentTarget);

								instance._selectTag(tagId);
							}
						);

						tagsItems.each(
							function(item, index, collection) {
								var dd = new A.DD.Drag(
									{
										bubbleTargets: instance,
										node: item,
										target: true
									}
								);

								dd.plug(
									A.Plugin.DDProxy,
									{
										borderStyle: '0',
										moveOnEnd: false
									}
								);

								dd.plug(
									A.Plugin.DDConstrained,
									{
										constrain2node: tagsContainer
									}
								);

								dd.plug(
									A.Plugin.DDNodeScroll,
									{
										node: tagsContainer,
										scrollDelay: 100
									}
								);

								dd.removeInvalid('a');
							}
						);
					},

					_reloadSearch: function() {
						var	instance = this;

						var namespace = instance._prefixedPortletId;

						var options = {
							data: function(node) {
								return node.one('span a').html();
							},
							input: '#' + namespace + 'tagsAdminSearchInput',
							nodes: instance._tagsItemsSelector
						};

						if (instance.liveSearch) {
							instance.liveSearch.destroy();
						}

						instance.liveSearch = new A.LiveSearch(options);
					},

					_selectTag: function(tagId) {
						var instance = this;

						var tag = instance._getTag(tagId);

						if (tag) {
							var tagName = instance._getTagName(tag);

							if (tag.hasClass('selected')) {
								return tag;
							}

							instance._hideAllMessages();
							instance._selectedTagName = tagName;
							instance._selectedTagId = tagId;

							instance._unselectAllTags();

							tag.addClass('selected');

							instance._displayTagData();
						}

						return tag;
					},

					_sendMessage: function(type, message) {
						var instance = this;

						var output = instance._portletMessageContainer;
						var typeClass = 'portlet-msg-' + type;

						output.removeClass('portlet-msg-error').removeClass('portlet-msg-success');
						output.addClass(typeClass);
						output.html(message);

						output.show();

						instance._hideMessageTask.delay(7000);
					},

					_showLoading: function(container) {
						var instance = this;

						A.all(container).html('<div class="loading-animation" />');
					},

					_showTagPanel: function(action) {
						var instance = this;

						if (action == ACTION_ADD) {
							instance._showTagPanelAdd();
						}
						else if (action == ACTION_EDIT) {
							instance._showTagPanelEdit();
						}
						else {
							throw 'Internal error. No action specified.';
						}
					},

					_showTagPanelAdd: function() {
						var instance = this;

						var tagPanelAdd = instance._tagPanelAdd;

						if (!tagPanelAdd) {
							tagPanelAdd = instance._createTagPanelAdd();

							var tagURL = instance._createURL(ACTION_ADD, LIFECYCLE_RENDER);

							tagPanelAdd.show();

							tagPanelAdd._syncUIPosAlign();

							var afterSuccess = A.bind(
								instance._initializeTagPanelAdd,
								instance,
								function() {
									instance._focusTagPanelAdd();
								}
							);

							tagPanelAdd.plug(
								A.Plugin.IO,
								{
									uri: tagURL.toString(),
									after: {
										success: afterSuccess
									}
								}
							);
						}
						else {
							tagPanelAdd.show();

							tagPanelAdd._syncUIPosAlign();

							instance._focusTagPanelAdd();
						}
					},

					_showTagPanelEdit: function() {
						var instance = this;

						var forceStart = false;
						var tagPanelEdit = instance._tagPanelEdit;

						if (!tagPanelEdit) {
							tagPanelEdit = instance._createTagPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditIOHandle.detach();
						}

						var tagEditURL = instance._createURL(ACTION_EDIT, LIFECYCLE_RENDER);

						tagPanelEdit.show();

						tagPanelEdit._syncUIPosAlign();

						tagPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: tagEditURL.toString()
							}
						);

						instance._currentPanelEditIOHandle = tagPanelEdit.io.after('success', instance._initializeTagPanelEdit, instance);

						if (forceStart) {
							tagPanelEdit.io.start();
						}
					},

					_updateTag: function(form) {
						var instance = this;

						var ioTag = instance._getIOTagUpdate();

						ioTag.set('form', form.getDOM());
						ioTag.set('uri', form.attr('action'));

						ioTag.start();
					},

					_unselectAllTags: function() {
						var instance = this;

						A.all(instance._tagsItemsSelector).removeClass('selected');
					},

					_tagsItemsSelector: '.tags-admin-list li'
				}
			}
		);

		Liferay.Portlet.AssetTagsAdmin = AssetTagsAdmin;
	},
	'',
	{
		requires: ['aui-live-search', 'aui-dialog', 'aui-dialog-iframe', 'aui-tree-view', 'dd', 'json', 'liferay-portlet-url']
	}
);