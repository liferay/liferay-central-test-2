AUI().add(
	'liferay-util-list-fields',
	function(A) {
		var Util = Liferay.Util;

		Util.listChecked = function(form) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('checked')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join(',');
		};

		Util.listCheckedExcept = function(form, except) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && item.get('checked')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join(',');
		};

		Util.listSelect = function(box, delimeter) {
			var buffer = [];

			delimeter = delimeter || ',';

			if (box == null) {
				return '';
			}

			var select = AUI().one(box);

			if (select) {
				var options = select.all('option');

				options.each(
					function(item, index, collection) {
						var val = item.val();

						if (val) {
							buffer.push(val);
						}
					}
				);
			}

			if (s[0] == '.none') {
				return '';
			}
			else {
				return buffer.join(delimeter);
			}
		};

		Util.listUncheckedExcept = function(form, except) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && !item.get('checked')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join();
		};
	},
	'',
	{
		requires: ['aui-base']
	}
);