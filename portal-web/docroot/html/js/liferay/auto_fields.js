Liferay.autoFields = new Class({

	/**
	 * OPTIONS
	 *
	 * Required
	 * container {string|object}: A jQuery selector that contains the rows you wish to duplicate.
	 * baseRows {string|object}: A jQuery selector that defines which fields are duplicated.
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

		instance._idSeed = baseRows.length;

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

		Liferay.bind('updateUndoList',
			function(event) {
				instance._updateUndoList();
			}
		);

		Liferay.bind('submitForm',
			function(event, data) {
				var form = jQuery(data.form);

				form.find('.lfr-form-row:hidden').remove();
			}
		);
	},

	addRow: function(el) {
		var instance = this;

		var currentRow = jQuery(el);
		var clone = currentRow.clone(true);

		var newSeed = (++instance._idSeed);

		if (newSeed < 10) {
			newSeed = '0' + newSeed;
		}

		clone.find('input, select').each(
			function() {
				var el = jQuery(this);
				var oldName = el.attr('name');
				var originalName = oldName.substring(0, oldName.length-2);
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

		var fields = instance.baseContainer('.lfr-form-row:visible :input');
		var serializedData = '';

		if (filter) {
			filter.apply(instance, [fields]);
		}

		return '';
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

	_queueUndo: function(deletedElement) {
		var instance = this;

		instance._undoCache.push(deletedElement);

		Liferay.trigger('updateUndoList');
	},

	_undoCache: [],
	_idSeed: 0
});
