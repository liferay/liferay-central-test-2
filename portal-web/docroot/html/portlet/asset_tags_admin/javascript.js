AUI().add(
	'liferay-tags-admin',
	function(A) {
		var AssetTagsAdmin = function() {
			AssetTagsAdmin.superclass.constructor.call(this);
		};

		AssetTagsAdmin.NAME = 'assettagsadmin';

		A.extend(
			AssetTagsAdmin,
			A.Base,
			{
				initializer: function(portletId) {
					var instance = this;

					var tagsContainer = jQuery(instance._tagsContainerSelector);

					instance.portletId = portletId;
					instance._tagsAdminContainer = jQuery('.tags-admin-container');

					jQuery('.tag-close').click(
						function() {
							instance._unselectAllTags();
							instance._closeEditSection();
						}
					);

					jQuery('.tag-save-properties').click(
						function() {
							instance._saveProperties();
						}
					);

					instance._portletMessageContainer = jQuery('<div class="lfr-message-response" id="tag-portlet-messages" />');
					instance._tagMessageContainer = jQuery('<div class="lfr-message-response" id="tag-messages" />');

					instance._portletMessageContainer.hide();
					instance._tagMessageContainer.hide();

					instance._tagsAdminContainer.before(instance._portletMessageContainer);
					tagsContainer.before(instance._tagMessageContainer);

					var toolbar = jQuery('.tags-admin-toolbar');

					var addTagButton = jQuery('.add-tag-button');

					instance._addTagOverlay = new A.ContextPanel(
						{
							bodyContent: A.get('.add-tag-layer'),
							trigger: '.add-tag-button',
							align: {
								points: ['tr', 'br']
							}
						}
					)
					.render();

					jQuery('.tag-permissions-button').click(
						function() {
							var tagName = instance._selectedTagName;
							var tagId = instance._selectedTagId;

							if (tagName && tagId) {
								var portletURL = instance._createPermissionURL(
									'com.liferay.portlet.asset.model.AssetTag',
									tagName, tagId);

								submitForm(document.hrefFm, portletURL.toString());
							}
							else {
								alert(Liferay.Language.get('please-first-select-a-tag'));
							}
						}
					);

					jQuery('#tag-search-bar').change(
						function(event) {
							jQuery('#tags-admin-search-input').focus();
							instance._reloadSearch();
						}
					);

					var addTag = function() {
						var addTagLayer = jQuery('.add-tag-layer');
						var tagName = addTagLayer.find('.new-tag-name').val();

						instance._hideAllMessages();
						instance._addTag(tagName);
					};

					jQuery('input.tag-save-button').click(addTag);

					jQuery('.tags-admin-actions input').keyup(
						function(event) {
							if (event.keyCode == 13) {
								var input = jQuery(this);

								addTag();

								return false;
							}
						}
					);

					jQuery('input.tag-delete-button').click(
						function() {
							if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-tag'))) {
								instance._deleteTag(
									instance._selectedTagId,
									function(message) {
										var exception = message.exception;

										if (!exception) {
											instance._closeEditSection();
											instance._hideToolbarOverlays();
											instance._displayTags();
										}
										else {
											if (exception.indexOf('auth.PrincipalException') > -1) {
												var errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');

												instance._sendMessage('error', errorText);
											}
										}
									}
								);
							}
						}
					);

					jQuery('.close-panel').click(
						function() {
							instance._hideToolbarOverlays();
						}
					);

					jQuery('.aui-overlay input:text').keyup(
						function(event) {
							var ESC_KEY_CODE = 27;
							var keyCode = event.keyCode;

							if (keyCode == ESC_KEY_CODE) {
								instance._hideToolbarOverlays();
							}
						}
					);

					instance._loadData();

					instance.after('drag:drag', instance._afterDrag);
					instance.after('drag:drophit', instance._afterDragDrop);
					instance.after('drag:enter', instance._afterDragEnter);
					instance.after('drag:exit', instance._afterDragExit);
					instance.after('drag:start', instance._afterDragStart);
				},

				_addTag: function(tagName, callback) {
					var instance = this;
					var communityPermission = instance._getPermissionsEnabled('community');
					var guestPermission = instance._getPermissionsEnabled('guest');

					var serviceParameterTypes = [
						'java.lang.String',
						'[Ljava.lang.String;',
						'com.liferay.portal.service.ServiceContext'
					];

					Liferay.Service.Asset.AssetTag.addTag(
						{
							name: tagName,
							properties: [],
							serviceContext: jQuery.toJSON(
								{
									communityPermissions: communityPermission,
									guestPermissions: guestPermission,
									scopeGroupId: themeDisplay.getScopeGroupId()
								}
							),
							serviceParameterTypes: jQuery.toJSON(serviceParameterTypes)
						},
						function(message) {
							var exception = message.exception;

							if (!exception && message.tagId) {
								instance._sendMessage('success', Liferay.Language.get('your-request-processed-successfully'));

								instance._displayTags(
									function() {
										var tag = instance._selectTag(message.tagId);

										if (tag.length) {
											jQuery(instance._tagsContainerSelector).scrollTop(tag.offset().top);
										}

										instance._showSection('.tag-edit');
									}
								);

								instance._resetActionValues();
								instance._hideToolbarOverlays();

								if (callback) {
									callback(tagName);
								}
							}
							else {
								var errorText = '';

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

								if (errorText) {
									instance._sendMessage('error', errorText);
								}
							}
						}
					);
				},

				_addProperty: function(baseNode, key, value) {
					var instance = this;

					var baseProperty = jQuery('div.tag-property-row:last');
					var newProperty = baseProperty.clone();

					newProperty.find('.property-key').val(key);
					newProperty.find('.property-value').val(value);
					newProperty.insertAfter(baseNode);
					newProperty.show();

					if (!key && !value) {
						newProperty.find('input:first').addClass('lfr-auto-focus');
					}

					instance._attachPropertyIconEvents(newProperty);
				},

				_afterDrag: function(event) {
					var instance = this;

					A.DD.DDM.syncActiveShims(true);
				},

				_afterDragDrop: function(event) {
					var instance = this;

					var dropNode = event.drop.get('node');
					var node = event.target.get('node');

					dropNode.removeClass('active-area');

					instance._merge(node, dropNode);
				},

				_afterDragEnter: function(event) {
					var instance = this;

					var dropNode = event.drop.get('node');

					dropNode.addClass('active-area');
				},

				_afterDragExit: function(event) {
					var instance = this;

					var dropNode = event.drop.get('node');

					dropNode.removeClass('active-area');
				},

				_afterDragStart: function(event) {
					var instance = this;

					var drag = event.target;

					var proxyNode = drag.get('dragNode');
					var node = drag.get('node');

					var clone = proxyNode.get('firstChild');

					if (!clone) {
						clone = node.cloneNode();

						clone.addClass('portlet-tags-admin-helper');

						proxyNode.appendChild(clone);
					}

					clone.html(node.html());
				},

				_alternateRows: function() {
					var instance = this;

					var tagsScope = jQuery(instance._tagsContainerSelector);

					jQuery('li', tagsScope).removeClass('alt');
					jQuery('li:odd', tagsScope).addClass('alt');
				},

				_attachPropertyIconEvents: function(property) {
					var instance = this;

					var row = jQuery(property);

					row.find('.add-property').click(
						function() {
							instance._addProperty(property, '', '');
						}
					);

					row.find('.delete-property').click(
						function() {
							instance._removeProperty(property);
						}
					);
				},

				_buildProperties: function() {
					var instance = this;

					var buffer = [];

					jQuery('.tag-property-row:visible').each(
						function(i, o) {
							var propertyRow = jQuery(this);
							var key = propertyRow.find('input.property-key').val();
							var value = propertyRow.find('input.property-value').val();
							var rowValue = [key, ':', value, ','].join('');

							buffer.push(rowValue);
						}
					);

					return buffer.join('');
				},

				_closeEditSection: function() {
					var instance = this;

					instance._hideSection('.tag-edit');
					jQuery(instance._tagsContainerCellsSelector).width('auto');
				},

				_createPermissionURL: function(modelResource, modelResourceDescription, resourcePrimKey) {
					var instance = this;

					var portletURL = Liferay.PortletURL.createPermissionURL(
						instance.portletId, modelResource, modelResourceDescription, resourcePrimKey);

					return portletURL;
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

				_displayProperties: function(tagId) {
					var instance = this;

					instance._getProperties(
						tagId,
						function(properties) {
							if (!properties.length) {
								properties = [{ key: '', value: '' }];
							}

							var total = properties.length;
							var totalRendered = jQuery('div.tag-property-row').length;

							if (totalRendered > total) {
								return;
							}

							jQuery.each(
								properties,
								function() {
									var baseProperty = jQuery('div.tag-property-row:last');

									instance._addProperty(baseProperty, this.key, this.value);
								}
							);
						}
					);
				},

				_displayTags: function(callback) {
					var instance = this;

					jQuery('#tag-messages').hide();

					instance._getTags(
						function(tags) {
							instance._displayTagsImpl(tags, callback);
						}
					);
				},

				_displayTagsImpl: function(tags, callback) {
					var instance = this;

					var buffer = [];
					var tagsContainer = A.get(instance._tagsContainerSelector);

					buffer.push('<ul>');

					jQuery.each(
						tags,
						function(i) {
							buffer.push('<li class="tag-item results-row" ');
							buffer.push('data-tag="');
							buffer.push(this.name);
							buffer.push('" data-tagId="');
							buffer.push(this.tagId);
							buffer.push('"><span><a href="javascript:;">');
							buffer.push(this.name);
							buffer.push('</a></span>');
							buffer.push('</li>');
						}
					);

					buffer.push('</ul>');

					if (!tags.length) {
						buffer = [];
						instance._sendMessage('info', Liferay.Language.get('no-tags-were-found'), '#tag-messages', true);
					}

					tagsContainer.html(buffer.join(''));

					instance._reloadSearch();

					var	tagsItems = A.all(instance._tagsItemsSelector);

					tagsItems.on(
						'click',
						function(event) {
							var tagId = instance._getTagId(event.currentTarget.getDOM());

							instance._selectTag(tagId);
							instance._showSection('.tag-edit');
						}
					);

					tagsItems.each(
						function(item, index, collection) {
							var dd = new A.DD.Drag(
								{
									bubbles: instance,
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

					instance._alternateRows();

					if (callback) {
						callback();
					}
				},

				_getTag: function(tagId) {
					var instance = this;

					return jQuery('li[data-tagId=' + tagId + ']')
				},

				_getTagId: function(exp) {
					var instance = this;

					return jQuery(exp).attr('data-tagId');
				},

				_getTagName: function(exp) {
					var instance = this;

					return jQuery(exp).attr('data-tag');
				},

				_getPermissionsEnabled: function(type) {
					var buffer = [];
					var permissionsActions = jQuery('.tag-permissions-actions');
					var permissions = permissionsActions.find('[name$=tagPermissions]:checked');

					permissions.each(
						function(i, n) {
							buffer.push(this.value);
						}
					);

					return buffer.join(',');
				},

				_getProperties: function(tagId, callback) {
					var instance = this;

					Liferay.Service.Asset.AssetTagProperty.getTagProperties(
						{
							tagId: tagId
						},
						callback
					);
				},

				_getTags: function(callback) {
					var instance = this;

					instance._showLoading(instance._tagsContainerSelector);

					Liferay.Service.Asset.AssetTag.getGroupTags(
						{
							groupId: themeDisplay.getScopeGroupId()
						},
						callback
					);
				},

				_hideAllMessages: function() {
					var instance = this;

					instance._tagsAdminContainer.find('.lfr-message-response').hide();
				},

				_hideLoading: function(exp) {
					var instance = this;

					instance._tagsAdminContainer.find('div.loading-animation').remove();
				},

				_hideSection: function(exp) {
					var instance = this;

					jQuery(exp).parent().removeClass('tag-editing');
				},

				_hideToolbarOverlays: function() {
					var instance = this;

					instance._addTagOverlay.hide();
				},

				_loadData: function() {
					var instance = this;

					instance._closeEditSection();

					instance._displayTags(
						function() {
							var tagId = instance._getTagId(instance._tagsItemsSelector + ':first');
						}
					);
				},

				_merge: function(node, dropNode) {
					var instance = this;

					var nodeEl = node.getDOM();
					var dropNodeEl = dropNode.getDOM();

					var fromTagId = instance._getTagId(nodeEl);
					var fromTagName = instance._getTagName(nodeEl);
					var toTagId = instance._getTagId(dropNodeEl);
					var toTagName = instance._getTagName(dropNodeEl);

					var destination = toTagName;

					var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x');

					mergeText = A.substitute(mergeText, [instance._getTagName(nodeEl), destination]);

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

				_reloadSearch: function() {
					var	instance = this;
					var options = {};
					var input = jQuery('#tags-admin-search-input');
					var tagList = jQuery(instance._tagsItemsSelector);

					input.unbind('keyup');

					var filter = 'span a';

					options = {
						list: tagList,
						filter: jQuery(filter, tagList)
					};

					input.liveSearch(options);
				},

				_removeProperty: function(property) {
					var instance = this;

					if (jQuery('div.tag-property-row').length > 2) {
						property.remove();
					}
				},

				_resetActionValues: function() {
					var instance = this;

					jQuery('.tags-admin-actions input:text').val('');
				},

				_saveProperties: function() {
					var instance = this;

					var tagId = instance._selectedTagId;
					var tagName = jQuery('.tag-edit input.tag-name').val() || instance._selectedTagName;
					var properties = instance._buildProperties();

					instance._updateTag(tagId, tagName, properties);
					instance._displayTags();
				},

				_selectTag: function(tagId) {
					var instance = this;

					var tag = instance._getTag(tagId);
					var tagId = instance._getTagId(tag);
					var tagName = instance._getTagName(tag);

					instance._selectedTagId = tagId;
					instance._selectedTagName = tagName;

					if (tag.is('.selected') || !tagId) {
						return tag;
					}

					instance._unselectAllTags();
					tag.addClass('selected');

					var editContainer = jQuery('.tag-edit');
					var tagNameField = editContainer.find('input.tag-name');

					tagNameField.val(tagName);
					instance._displayProperties(tagId);

					instance._selectedTag = tag;

					return tag;
				},

				_sendMessage: function(type, message, output, noAutoHide) {
					var instance = this;

					var output = jQuery(output || '#tag-messages');
					var typeClass = 'portlet-msg-' + type;

					clearTimeout(instance._messageTimeout);

					output.removeClass('portlet-msg-error portlet-msg-success');
					output.addClass(typeClass).html(message).fadeIn('fast');

					if (!noAutoHide) {
						instance._messageTimeout = setTimeout(
							function() {
								output.fadeOut('slow',
									function(event) {
										instance._addTagOverlay.refreshAlign();
									}
								);
							}, 7000);
					}
				},

				_showLoading: function(container) {
					var instance = this;

					jQuery(container).html('<div class="loading-animation" />');
				},

				_showSection: function(exp) {
					var instance = this;

					var element = jQuery(exp);

					if (!element.is(':visible')) {
						element.parent().addClass('tag-editing');
						element.find('input:first').focus();
						jQuery(instance._tagsContainerCellsSelector).width('50%');
					}
				},

				_unselectAllTags: function() {
					var instance = this;

					jQuery(instance._tagsItemsSelector).removeClass('selected');
					jQuery('div.tag-property-row:gt(0)').remove();
				},

				_updateTag: function(tagId, name, properties, callback) {
					var instance = this;

					Liferay.Service.Asset.AssetTag.updateTag(
						{
							tagId: tagId,
							name: name,
							properties: properties,
							serviceContext: null
						},
						function(message) {
							var exception = message.exception;

							if (!exception) {
								var selectedText = instance._selectedTag.find('> span > a');

								if (!selectedText.length) {
									selectedText.find('> span');
								}

								instance._selectedTag.attr('data-tag', name);
								selectedText.text(name);

								instance._closeEditSection();
							}
							else {
								var errorText = '';

								if (exception.indexOf('auth.PrincipalException') > -1) {
									errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
								}
								else if (exception.indexOf('Exception') > -1) {
									errorText = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
								}

								if (errorText) {
									instance._sendMessage('error', errorText);
								}
							}

							if (callback) {
								callback(message);
							}
						}
					);
				},

				_selectedTag: null,
				_selectedTagName: null,
				_tagsContainerCellsSelector: '.portlet-tags-admin .tags-admin-content td',
				_tagsContainerSelector: '.tags',
				_tagsItemsSelector: '.tags li'
			}
		);

		Liferay.Portlet.AssetTagsAdmin = AssetTagsAdmin;
	},
	'',
	{
		requires: ['base', 'context-panel', 'dd', 'substitute']
	}
);