Liferay.SearchContainer = new Class({
	initialize: function(options) {
		var instance = this;

		instance._id = options.id || '';
		instance._container = jQuery('#' + instance._id + 'searchContainer');
		instance._table = instance._container.find('table');

		instance._table.attr('data-searchContainerId', instance._id);

		Liferay.SearchContainer.register(instance._id, instance);
	},

	addRow: function(arr, id) {
		var instance = this;

		var row = instance._table.find('tr:last').clone();
		var cells = row.find('> td');

		jQuery.each(
			arr,
			function(i, n) {
				if (cells[i]) {
					cells.eq(i).html(n);
				}
			}
		);

		instance._table.append(row);

		if (id) {
			instance._ids.push(id);
		}

		instance.trigger('addRow', {ids: instance._ids, rowData: arr});
	},

	bind: function(event, func) {
		var instance = this;

		instance._container.bind(event, func);
	},

	deleteRow: function(obj, id) {
		var instance = this;

		if (typeof obj == 'number' || typeof obj == 'string') {
			obj = instance._table.find('tr').eq(obj);
		}

		if (id) {
			var pos = instance._ids.indexOf(id);

			if (pos > -1) {
				instance._ids.splice(pos, 1);
			}
		}

		instance.trigger('deleteRow', {ids: instance._ids, row: obj});
		return Liferay.SearchContainer.deleteRow(obj);
	},

	trigger: function(event) {
		var instance = this;

		instance._container.trigger(event);
	},

	_ids: []
});

jQuery.extend(
	Liferay.SearchContainer,
	{
		deleteRow: function(obj) {
			var instance = this;

			var el;

			if (obj.nodeName) {
				el = jQuery(obj);
			}
			else if (obj.jquery) {
				el = obj;
			}

			if (!el.is('tr')) {
				el = el.parents('tr:first');
			}

			el.remove();
		},

		get: function(id) {
			var instance = this;

			var searchContainer;

			if (instance._cache[id]) {
				searchContainer = instance._cache[id];
			}
			else {
				searchContainer = new Liferay.SearchContainer(
					{
						id: id
					}
				);
			}

			return searchContainer;
		},

		register: function(id, obj) {
			var instance = this;

			instance._cache[id] = obj;
		},

		_cache: {}
	}
);