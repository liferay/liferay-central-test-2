(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var DDM = Expanse.DragDrop;

	var AssetTagsAdmin = new Expanse.Class(
		{
			initialize: function(portletId) {
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

				var addTagLayer = jQuery('.add-tag-layer');

				var addTagButton = jQuery('.add-tag-button');

				instance._addTagOverlay = new Expanse.Overlay(
					addTagLayer[0],
					{
						context: [addTagButton[0], 'tr', 'br'],
						preventcontextoverlap: true,
						visible: false
					}
				);

				toolbar.find('.add-tag-button').click(
					function (event) {
						instance._showAddTagOverlay();
					}
				);

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
					var tagsAdminActionsContainer = jQuery('.tags-admin-actions');
					var tagName = tagsAdminActionsContainer.find('.new-tag-name').val();

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
											instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
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

				jQuery('.exp-overlay input:text').keyup(
					function(event) {
						var ESC_KEY_CODE = 27;
						var keyCode = event.keyCode;

						if (keyCode == ESC_KEY_CODE) {
							instance._hideToolbarOverlays();
						}
					}
				);

				instance._loadData();
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
							instance._sendMessage('success', 'your-request-processed-successfully');

							instance._displayTags(
								function() {
									var tag = instance._selectTag(message.tagId);

									if (tag.length) {
										jQuery(instance._tagsContainerSelector).scrollTo(tag);
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
							var errorKey = '';

							if (exception.indexOf('DuplicateTagException') > -1) {
								errorKey = 'that-tag-already-exists';
							}
							else if ((exception.indexOf('TagNameException') > -1) ||
									 (exception.indexOf('AssetTagException') > -1)) {

								errorKey = 'one-of-your-fields-contains-invalid-characters';
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								errorKey = 'you-do-not-have-permission-to-access-the-requested-resource';
							}

							if (errorKey) {
								instance._sendMessage('error', errorKey);
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
				var tagsContainer = jQuery(instance._tagsContainerSelector);

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
					instance._sendMessage('info', 'no-tags-were-found', '#tag-messages', true);
				}

				tagsContainer.html(buffer.join(''));

				instance._reloadSearch();

				var	tagsItems = jQuery(instance._tagsItemsSelector);

				tagsItems.click(
					function() {
						var tagId = instance._getTagId(this);
						var tagEditContainer = jQuery('.tag-edit');

						instance._selectTag(tagId);
						instance._showSection(tagEditContainer);
					}
				);

				for (var i = tagsItems.length - 1; i >= 0; i--) {
					var tagsItem = tagsItems[i];

					new droppableTag(tagsItem, 'tags');

					var draggable = new draggableTag(tagsItem, 'tags');

					draggable.on('dragDropEvent', instance._onDragDrop, instance);
				}

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

				buffer = permissions.fieldValue().join(',');

				return buffer;
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

			_merge: function(event, ui) {
				var instance = this;

				var draggable = ui.draggable;
				var droppable = ui.droppable;
				var fromTagId = instance._getTagId(draggable);
				var fromTagName = instance._getTagName(draggable);
				var toTagId = instance._getTagId(droppable);
				var toTagName = instance._getTagName(droppable);

				var destination = toTagName;

				var tagText = {
					SOURCE: instance._getTagName(draggable),
					DESTINATION: destination
				};

				var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x', ['[$SOURCE$]', '[$DESTINATION$]']).replace(
					/\[\$(SOURCE|DESTINATION)\$\]/gm,
					function(completeMatch, match, index, str) {
						return tagText[match];
					}
				);

				if (confirm(mergeText)) {
					instance._mergeTags(
						fromTagId,
						toTagId,
						function() {
							jQuery(draggable).remove();
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

			_onDragDrop: function(event, instance) {
				var draggable = this;
				var target = Dom.get(event.info);
				var src = draggable.getEl();

				if (DDM.interactionInfo.validDrop && target != src) {
					instance._merge(event,
						{
							draggable: src,
							droppable: target
						}
					);
				}

				Dom.removeClass(target, 'active-area');
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

			_sendMessage: function(type, key, output, noAutoHide) {
				var instance = this;

				var output = jQuery(output || '#tag-messages');
				var message = Liferay.Language.get(key);
				var typeClass = 'portlet-msg-' + type;

				clearTimeout(instance._messageTimeout);

				output.removeClass('portlet-msg-error portlet-msg-success');
				output.addClass(typeClass).html(message).fadeIn('fast');

				if (!noAutoHide) {
					instance._messageTimeout = setTimeout(
						function() {
							output.fadeOut('slow',
								function(event) {
									instance._addTagOverlay.align();
								}
							);
						}, 7000);
				}
			},

			_showAddTagOverlay: function() {
				var instance = this;

				var tagPanel = instance._addTagOverlay;

				tagPanel.show();
				tagPanel.align();

				jQuery('.new-tag-name', tagPanel.body).focus();
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
						properties: properties
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
							if (exception.indexOf('auth.PrincipalException') > -1) {
								instance._sendMessage('error', 'you-do-not-have-permission-to-access-the-requested-resource');
							}
							else if (exception.indexOf('Exception') > -1) {
								instance._sendMessage('error', 'one-of-your-fields-contains-invalid-characters');
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

	var droppableTag = Expanse.Droppable;

	var scrollParent = jQuery('.tags')[0];

	var draggableTag = Expanse.DragProxy.extend(
		{
			initialize: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				instance.removeInvalidHandleType('a');

				instance.goingUp = false;
				instance.lastY = 0;

				instance._scrollParent = scrollParent;

	            instance._scrollHeight = scrollParent.scrollHeight;
	            instance._clientHeight = scrollParent.clientHeight;
	            instance._xy = Dom.getXY(scrollParent);
			},

			endDrag: function(event) {
				var instance = this;

				var proxy = instance.getDragEl();

				Dom.setStyle(proxy, 'top', 0);
				Dom.setStyle(proxy, 'left', 0);

				instance._removeScrollInterval();
			},

			onDrag: function(event) {
				var instance = this;

				instance._super.apply(instance, arguments);

				var y = Event.getPageY(event);

				if (y < instance.lastY) {
					instance.goingUp = true;
				}
				else if (y > instance.lastY) {
					instance.goingUp = false;
				}

				instance.lastY = y;

				var pageY = Event.getPageY(event);
				var clientHeight = instance.getEl().clientHeight;
				var scrollTop = false;

				instance._scrollBy = (clientHeight * 2) + instance._overflow;

				if (instance.goingUp) {
					var deltaTop = instance._xy[1] + (clientHeight + instance._overflow);

					if (pageY < deltaTop) {
						scrollTop = instance._scrollParent.scrollTop - instance._scrollBy;
					}
				}
				else {
					var deltaBottom = instance._clientHeight + instance._xy[1] - (clientHeight + instance._overflow);

					if (pageY > deltaBottom) {
						scrollTop = instance._scrollParent.scrollTop + instance._scrollBy;
					}
				}

				instance._scrollTo(scrollTop);
			},

			onDragDrop: function() {
				var instance = this;

				instance._super.apply(this, arguments);

				instance._removeScrollInterval();
			},

			onDragEnter: function(event, id) {
				var instance = this;

				var target = Dom.get(id);
				var src = instance.getEl();

				if (target != src) {
					Dom.addClass(target, 'active-area');
				}
			},

			onDragOut: function(event, id) {
				var instance = this;

				var target = Dom.get(id);
				var src = instance.getEl();

				if (target != src) {
					Dom.removeClass(target, 'active-area');
				}
			},

			startDrag: function(x, y) {
				var instance = this;

				var proxy = instance.getDragEl();
				var src = instance.getEl();

				proxy.innerHTML = '';

				var clone = src.cloneNode(true);
				clone.id = '';

				proxy.appendChild(clone);

				Dom.setStyle(proxy, 'border-width', 0);
				Dom.addClass(clone, 'portlet-tags-admin-helper');
			},

			_removeScrollInterval: function() {
				var instance = this;

				if (instance._scrollInterval) {
					clearInterval(instance._scrollInterval);
				}
			},

			_scrollTo: function(scrollTop) {
				var instance = this;

				instance._currentScrollTop = scrollTop;

				instance._removeScrollInterval();

				if (scrollTop) {
					instance._scrollInterval = setInterval(
						function() {
							if ((instance._currentScrollTop < 0) || (instance._currentScrollTop > instance._scrollHeight)) {
								instance._removeScrollInterval();
							}

							instance._scrollParent.scrollTop = instance._currentScrollTop;

							DDM.refreshCache();

							if (instance.goingUp) {
								instance._currentScrollTop -= instance._scrollBy;
							}
							else {
								instance._currentScrollTop += instance._scrollBy;
							}
						},
						10
					);
				}
			},

			_overflow: 5,
			_scrollBy: 0,
			_scrollInterval: null
		}
	);

	Liferay.Portlet.AssetTagsAdmin = AssetTagsAdmin;
})();