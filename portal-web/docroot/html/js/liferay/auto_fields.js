AUI().add(
	'liferay-auto-fields',
	function(A) {
		var Lang = A.Lang;

		var CSS_AUTOROW_CONTROLS = 'lfr-autorow-controls';

		var CSS_ICON_LOADING = 'aui-icon-loading';

		var TPL_INPUT_HIDDEN = '<input name="{name}" type="hidden" />';

		var TPL_LOADING = '<div class="' + CSS_ICON_LOADING + '"></div>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * container {string|object}: A selector that contains the rows you wish to duplicate.
		 * baseRows {string|object}: A selector that defines which fields are duplicated.
		 *
		 * Optional
		 * fieldIndexes {string}: The name of the POST parameter that will contain a list of the order for the fields.
		 * sortable{boolean}: Whether or not the rows should be sortable
		 * sortableHandle{string}: A selector that defines a handle for the sortables
		 *
		 */

		var AutoFields = function(config) {
			AutoFields.superclass.constructor.apply(this, arguments);
		};

		AutoFields.NAME = 'autofields';

		AutoFields.ATTRS = {
			baseRows: {
				setter: function(value) {
					var instance = this;

					if (Lang.isString(value)) {
						value = instance.get('contentBox').all(value);
					}

					if (!value) {
						value = A.Attribute.INVALID_VALUE;
					}

					return value;
				},
				value: '.lfr-form-row'
			},
			fieldIndexes: {
				value: ''
			},
			sortable: {
				value: false
			},
			sortableHandle: {
				value: null
			},
			url: {
				value: null
			}
		};

		A.extend(
			AutoFields,
			A.Widget,
			{
				renderUI: function() {
					var instance = this;

					var contentBox = instance.get('contentBox');
					var baseRows = instance.get('baseRows');

					instance._guid = baseRows.size();

					instance._undoManager = new Liferay.UndoManager().render(contentBox);

					instance._renderFieldIndexes();

					instance.rows = new A.DataSet();

					if (instance.get('sortable')){
						instance._initSortable();
					}

					baseRows.each(instance._initRows, instance);
				},

				bindUI: function() {
					var instance = this;

					instance.on('autorow:clone', instance._onCloneRow);
					instance.on('autorow:delete', instance._onDeleteRow);

					Liferay.on('submitForm', A.bind(instance._syncFields, instance));

					instance._undoManager.on('clearList', instance._clearInactiveRows, instance);
				},

				serialize: function(filter) {
					var instance = this;

					var rows = instance.rows;
					var serializedData = [];

					filter = filter || function(item, index, collection) {
						var contentBox = item.get('contentBox');

						var field = contentBox.one('input, select, textarea');

						if (field) {
							fieldId = field.get('id') || field.get('name') || '';

							fieldId = fieldId.match(/([0-9]+)$/);

							var fieldData = fieldId && fieldId[0];

							if (fieldData) {
								serializedData.push(fieldData);
							}
						}
					};

					rows.each(filter);

					return serializedData.join(',');
				},

				_clearInactiveRows: function() {
					var instance = this;

					var rows = instance.rows.filter(
						function(item, index, collection) {
							var active = item.get('active');

							if (!active) {
								item.destroy();
							}

							return active;
						}
					);

					instance.rows = rows;

					return instance.rows;
				},

				_initRows: function(item, index, collection) {
					var instance = this;

					var row = new AutoRow(
						{
							bubbleTargets: instance,
							contentBox: item,
							guid: (++instance._guid),
							reset: false,
							sortable: instance._sortable,
							url: instance.get('url')
						}
					).render();

					row._originalConfig.reset = true;

					instance.rows.add(row);
				},

				_initSortable: function() {
					var instance = this;

					var sortableHandle = instance.get('sortableHandle');
					var contentBox = instance.get('contentBox');

					if (sortableHandle) {
						contentBox.all(sortableHandle).addClass('handle-sort-vertical');
					}

					instance._sortable = new A.Sortable(
						{
							constrain: {
								stickY: true
							},
							dd: {
								handles: [sortableHandle]
							},
							nodes: []
						}
					);
				},

				_onCloneRow: function(event) {
					var instance = this;

					instance.rows.add(event.row);

					instance._guid++;
				},

				_onDeleteRow: function(event) {
					var instance = this;

					var deletedRow = event.deletedRow;

					var activeRows = instance.rows.filter(
						function(item, index, collection) {
							return item.get('active');
						}
					);

					if (activeRows.size() == 0) {
						deletedRow.clone();
					}

					var historyState = A.bind(deletedRow.set, deletedRow, 'active', true);

					instance._undoManager.add(historyState);
				},

				_syncFields: function() {
					var instance = this;

					instance._clearInactiveRows();
					instance._updateFieldIndexes();
				},

				_renderFieldIndexes: function() {
					var instance = this;

					var contentBox = instance.get('contentBox');
					var fieldIndexes = instance.get('fieldIndexes');

					if (fieldIndexes) {
						instance._fieldIndexes = A.one('[name=' + fieldIndexes + ']');

						if (!instance._fieldIndexes) {
							var fieldIndexHTML = A.substitute(
								TPL_INPUT_HIDDEN,
								{
									name: fieldIndexes
								}
							);

							instance._fieldIndexes = A.Node.create(fieldIndexHTML);

							contentBox.appendChild(instance._fieldIndexes);
						}
					}
				},

				_updateFieldIndexes: function() {
					var instance = this;

					if (instance._fieldIndexes) {
						var fieldOrder = instance.serialize();

						instance._fieldIndexes.val(fieldOrder);
					}
				},

				_guid: 0
			}
		);

		var AutoRow = function() {
			AutoRow.superclass.constructor.apply(this, arguments);
		};

		AutoRow.NAME = 'autorow';

		AutoRow.ATTRS = {
			active: {
				value: true
			},
			bubbleTargets: {
				value: null
			},
			guid: {
				getter: function(value) {
					var instance = this;

					return instance.get('bubbleTargets')._guid;
				},
				lazyAdd: false,
				value: 0
			},
			reset: {
				value: true
			},
			url: {
				value: null
			}
		};

		A.extend(
			AutoRow,
			A.Widget,
			{
				initializer: function(config) {
					var instance = this;

					instance._originalConfig = config;
					instance._sortable = config.sortable;
				},

				renderUI: function() {
					var instance = this;

					var boundingBox = instance.get('boundingBox');

					var renderControls = new A.Toolbar(
						{
							children: [
								{
									handler: {
										context: instance,
										fn: instance.clone
									},
									icon: 'plus',
									id: 'add'
								},
								{
									handler: {
										context: instance,
										fn: instance.remove
									},
									icon: 'minus',
									id: 'delete'
								}
							]
						}
					)
					.render(boundingBox);

					renderControls.get('boundingBox').addClass(CSS_AUTOROW_CONTROLS);

					if (instance.get('reset')) {
						instance._clearForm();
					}

					if (instance._sortable) {
						instance._sortable.add(boundingBox);
					}
				},

				bindUI: function() {
					var instance = this;

					instance._createEvents();

					instance.after('activeChange', instance._afterActiveChange);
					instance.after('visibleChange', instance._afterRowVisibleChange);
				},

				clone: function() {
					var instance = this;

					var boundingBox = instance.get('boundingBox');
					var parentNode = boundingBox.get('parentNode');

					var clonedNode = instance._createClone();

					var guid = instance.get('guid') + 1;

					var config = {
						contentBox: clonedNode,
						guid: guid
					};

					A.mix(config, instance._originalConfig);

					var clone = new instance.constructor(config);

					var cloneBoundingBox = clone.get('boundingBox');

					clone.render(parentNode);

					boundingBox.placeAfter(cloneBoundingBox);

					var url = instance.get('url');

					if (url) {
						var contentBox = clone.get('contentBox');

						contentBox.html(TPL_LOADING);

						contentBox.plug(A.Plugin.ParseContent);

						A.io.request(
							url,
							{
								data: {
									index: guid
								},
								method: 'POST',
								on: {
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										contentBox.setContent(responseData);
									}
								}
							}
						);
					}

					instance.fire(
						'clone',
						{
							guid: guid,
							originalRow: instance,
							row: clone
						}
					);

					return clone;
				},

				remove: function() {
					var instance = this;

					instance.set('active', false);

					instance.fire(
						'delete',
						{
							deletedRow: instance,
							guid: instance.get('guid')
						}
					);
				},

				_afterActiveChange: function(event) {
					var instance = this;

					var action = 'hide';

					if (event.newVal) {
						action = 'show';
					}

					instance[action]();
				},

				_afterRowVisibleChange: function(event) {
					var instance = this;

					var action = 'addClass';

					if (event.newVal) {
						action = 'removeClass';
					}

					instance.get('boundingBox')[action]('aui-helper-hidden');
				},

				_clearForm: function() {
					var instance = this;

					var contentBox = instance.get('contentBox');

					contentBox.all('input, select, textarea').each(
						function(item, index, collection) {
							var type = item.getAttribute('type');
							var tag = item.get('nodeName').toLowerCase();

							if (type == 'text' || type == 'password' || tag == 'textarea') {
								item.val('');
							}
							else if (type == 'checkbox' || type == 'radio') {
								item.set('checked', false);
							}
							else if (tag == 'select') {
								item.set('selectedIndex', -1);
							}
						}
					);
				},

				_createClone: function() {
					var instance = this;

					var clone = instance.get('contentBox').clone();

					if (!instance.get('url')) {
						instance._resetMarkup(clone);
					}
					else {
						clone.html(TPL_LOADING);
					}

					return clone;
				},

				_createEvents: function() {
					var instance = this;

					var bubbleTargets = instance.get('bubbleTargets');

					if (bubbleTargets) {
						instance.addTarget(bubbleTargets);
					}

					var eventConfig = {
						bubbles: true,
						emitsFacade: true
					};

					instance.publish('clone', eventConfig);
					instance.publish('delete', eventConfig);
				},

				_resetMarkup: function(clone) {
					var instance = this;

					var guid = instance.get('guid') + 1;

					clone.all('input, select, textarea, span').each(
						function(item, index, collection) {
							var oldName = item.getAttribute('name') || item.getAttribute('id');

							var originalName = oldName.replace(/([0-9]+)$/, '');
							var newName = originalName + guid;

							var type = item.getAttribute('type');

							if (type == 'radio') {
								oldName = item.getAttribute('id');

								item.set('checked', false);
								item.setAttribute('value', guid);
								item.setAttribute('id', newName);
							}
							else if ((type == 'button' || item.get('nodeName').toLowerCase() == 'button') ||
									item.get('nodeName').toLowerCase() == 'span') {

								if (oldName) {
									item.setAttribute('id', newName);
								}
							}
							else {
								item.setAttribute('name', newName);
								item.setAttribute('id', newName);
							}

							var labelNode = clone.one('label[for=' + oldName + ']');

							if (labelNode) {
								labelNode.setAttribute('for', newName);
							}
						}
					);

					clone.all('input[type=hidden]').set('value', '');

					clone.resetId();

					if (instance._sortable) {
						clone.one('.handle-sort-vertical').attr('id', '');
					}

					var firstTextField = clone.one('input[type=text]');

					if (firstTextField) {
						Liferay.Util.focusFormField(firstTextField.getDOM());
					}
				}
			}
		);

		AutoFields.AutoRow = AutoRow;

		Liferay.AutoFields = AutoFields;
	},
	'',
	{
		requires: ['aui-base', 'aui-data-set', 'aui-io-request', 'aui-parse-content', 'aui-sortable', 'base', 'liferay-undo-manager']
	}
);