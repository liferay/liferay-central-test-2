Liferay.SearchContainer = new Class({
	initialize: function(options) {
		var instance = this;

		instance._id = options.id || '';
		instance._container = jQuery('#' + instance._id + 'searchContainer');
		instance._table = instance._container.find('table');

		Liferay.SearchContainer.register(instance._id, instance);
	},

	addRow: function(arr) {
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
	},

	deleteRow: function(obj) {
		var instance = this;

		if (typeof obj == 'number' || typeof obj == 'string') {
			obj = instance.table.find('tr').eq(obj - 1);
		}

		return Liferay.SearchContainer.deleteRow(obj);
	}
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