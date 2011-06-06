AUI().add(
	'liferay-util-list-fields',
	function(A) {
		var Util = Liferay.Util;

		Util.listChecked = function(form) {
			return Util.listChecked(form).join(',');
		};

		Util.listCheckedExcept = function(form, except) {
			return Util.listCheckedExceptItem(form, except).join(',');
		};

		Util.listCheckedExceptItem = function(form, except) {
			var s = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && item.get('checked')) {
							s.push(val);
						}
					}
				);
			}

			return s;
		};

		Util.listCheckedItem = function(form) {
			var s = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('checked')) {
							s.push(val);
						}
					}
				);
			}

			return s;
		};

		Util.listSelect = function(box, delimeter) {
			var s = [];

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
							s.push(val);
						}
					}
				);
			}

			if (s[0] == '.none') {
				return '';
			}
			else {
				return s.join(delimeter);
			}
		};

		Util.listUncheckedExcept = function(form, except) {
			var s = [];

			form = AUI().one(form);

			if (form) {
				form.all('input[type=checkbox]').each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && !item.get('checked')) {
							s.push(val);
						}
					}
				);
			}

			return s.join(',');
		};

	},
	'',
	{
		requires: ['aui-base']
	}
);