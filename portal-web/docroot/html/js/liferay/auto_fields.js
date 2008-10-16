Liferay.autoFields = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * container {string|object}: A jQuery selector that contains the rows you wish to duplicate.
	 * baseRows {string|object}: A jQuery selector that defines which fields are duplicated.
	 *
	 * Optional
	 * fieldIndexes {string}: The name of the POST parameter that will contain a list of the order for the fields.
	 */

	initialize: function(options) {
		var instance = this;

		var container = jQuery(options.container);
		var baseRows = jQuery(options.baseRows);

		var fullContainer = jQuery('<div class="row-container"></div>');
		var baseContainer = jQuery('<div class="lfr-form-row"></div>');
		var undoText = Liferay.Language.get('undo-x', ['[$SPAN$]']);
		undoText = undoText.replace(/\[\$SPAN\$\]/, '<span class="items-left">(0)</span>');

		var rowControls = jQuery('<span class="row-controls"><a href="javascript: ;" class="add-row">' + Liferay.Language.get('add-row') + '</a><a href="javascript: ;" class="delete-row">' + Liferay.Language.get('delete-row') + '</a></span>');
		var undoManager = jQuery('<div class="portlet-msg-info undo-queue queue-empty"><a class="undo-action" href="javascript: ;">' + undoText + '</a><a class="clear-undos" href="javascript: ;">' + Liferay.Language.get('clear-history') + '</a></div>');

		instance._baseContainer = fullContainer;
		instance._idSeed = baseRows.length;

		if (options.fieldIndexes) {
			instance._fieldIndexes = jQuery('[@name=' + options.fieldIndexes + ']');

			if (!instance._fieldIndexes.length) {
				instance._fieldIndexes = jQuery('<input name="' + options.fieldIndexes + '" type="hidden" />')
				instance._baseContainer.append(instance._fieldIndexes);
			}
		}
		else {
			instance._fieldIndexes = jQuery([]);
		}

		fullContainer.click(
			function(event) {
				if (event.target.parentNode.className.indexOf('row-controls') > -1) {
					var target = jQuery(event.target);
					var currentRow = target.parents('.lfr-form-row:first')[0];

					if (target.is('.add-row')) {
						instance.addRow(currentRow);
					}

					if (target.is('.delete-row')) {
						instance.deleteRow(currentRow);
					}
				}
			}
		);

		instance._container = container;
		instance._rowContainer = fullContainer;

		instance._undoManager = undoManager;

		instance._undoItemsLeft = undoManager.find('.items-left');
		instance._undoButton = undoManager.find('.undo-action');
		instance._clearUndos = undoManager.find('.clear-undos');

		instance._clearUndos.click(
			function(event) {
				instance._undoCache = [];
				instance._rowContainer.find('.lfr-form-row:hidden').remove();

				Liferay.trigger('updateUndoList');
			}
		);

		instance._undoButton.click(
			function(event) {
				instance.undoLast();
			}
		);

		fullContainer.prepend(undoManager);

		baseRows.each(
			function(i) {
				var formRow;
				var controls = rowControls.clone();
				var currentRow = jQuery(this);

				if (currentRow.is('.lfr-form-row')) {
					formRow = currentRow;
				}
				else {
					formRow = baseContainer.clone();
					formRow.append(this);
				}

				formRow.append(controls);
				fullContainer.append(formRow);

				if (i == 0) {
					instance._rowTemplate = formRow.clone();
					instance._rowTemplate.clearForm();
				}
			}
		);

		var rows = fullContainer.find('.lfr-form-row');

		container.append(fullContainer);

		Liferay.bind(
			'updateUndoList',
			function(event) {
				instance._updateUndoList();
			}
		);

		Liferay.bind(
			'submitForm',
			function(event, data) {
				var form = jQuery(data.form);

				form.find('.lfr-form-row:hidden').remove();

				var fieldOrder = instance.serialize();

				instance._fieldIndexes.val(fieldOrder);
			}
		);
	},

	addRow: function(el) {
		var instance = this;

		var currentRow = jQuery(el);
		var clone = currentRow.clone(true);

		var newSeed = (++instance._idSeed);

		clone.find('input, select').each(
			function() {
				var el = jQuery(this);
				var oldName = el.attr('name');
				var originalName = oldName.replace(/([0-9]+)$/, '');
				var newName = originalName + newSeed;

				if (!el.is(':radio')) {
					el.attr('name', newName);
				}
				else {
					oldName = el.attr('id');
				}

				el.attr('id', newName);
				clone.find('label[for=' + oldName + ']').attr('for', newName);
			}
		);

		clone.clearForm();

		clone.find("input[type=hidden]").each(
			function() {
				this.value = '';
			}
		);

		currentRow.after(clone);

		clone.find('input:text:first').trigger('focus');
	},

	deleteRow: function(el) {
		var instance = this;

		var visibleRows = instance._rowContainer.find('.lfr-form-row:visible');

		if (visibleRows.length == 1) {
			instance.addRow(el);
		}

		var deletedElement = jQuery(el);

		deletedElement.hide();

		instance._queueUndo(deletedElement);
	},

	undoLast: function() {
		var instance = this;

		var itemsLeft = instance._undoCache.length;

		if (itemsLeft > 0) {
			var deletedElement = instance._undoCache.pop();

			deletedElement.show();

			Liferay.trigger('updateUndoList');
		}
	},

	serialize: function(filter) {
		var instance = this;

		var rows = instance._baseContainer.find('.lfr-form-row:visible');
		var serializedData = [];

		if (filter) {
			serializedData = filter.apply(instance, [rows]) || [];
		}
		else {
			rows.each(
				function(i) {
					var formField = jQuery(this).find(':input:first');
					var fieldId = formField.attr('id');

					if (!fieldId) {
						fieldId = formField.attr('name');
					}
					fieldId = (fieldId || '').match(/([0-9]+)$/);

					if (fieldId && fieldId[0]) {
						serializedData.push(fieldId[0]);
					}
				}
			)
		}

		return serializedData.join(',');
	},

	_queueUndo: function(deletedElement) {
		var instance = this;

		instance._undoCache.push(deletedElement);

		Liferay.trigger('updateUndoList');
	},

	_updateUndoList: function() {
		var instance = this;

		var itemsLeft = instance._undoCache.length;
		var undoManager = instance._undoManager;

		if (itemsLeft == 1) {
			undoManager.addClass('queue-single');
		}
		else {
			undoManager.removeClass('queue-single');
		}

		if (itemsLeft > 0) {
			undoManager.removeClass('queue-empty');
		}
		else {
			undoManager.addClass('queue-empty');
		}

		instance._undoItemsLeft.text('(' + itemsLeft + ')');
	},

	_undoCache: [],
	_idSeed: 0
});
