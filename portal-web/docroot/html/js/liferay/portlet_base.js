AUI.add(
	'liferay-portlet-base',
	function(A) {
		var Lang = A.Lang;
		var LString = Lang.String;

		var prefix = LString.prefix;
		var startsWith = LString.startsWith;

		var PortletBase = function(config) {
			var instance = this;

			var namespace;

			if ('namespace' in config) {
				namespace = config.namespace;
			}
			else {
				namespace = A.guid();
			}

			instance.NS = namespace;
			instance.ID = namespace.replace(/^_(.*)_$/, '$1');

			instance.rootNode = A.one('#p_p_id' + namespace);

			instance.ns = A.cached(
				function(str) {
					var value = instance.NS;

					if (!Lang.isUndefined(str)) {
						value = prefix(value, str);
					}

					return value;
				}
			);
		};

		PortletBase.ATTRS = {
			namespace: {
				getter: '_getNS',
				writeOnce: true
			}
		};

		PortletBase.prototype = {
			all: function(selector, root) {
				var instance = this;

				root = root || instance.rootNode || A;

				return root.allNS(instance.NS, selector);
			},

			byId: function(id) {
				var instance = this;

				return A.byIdNS(instance.NS, id);
			},

			one: function(selector, root) {
				var instance = this;

				root = root || instance.rootNode || A;

				return root.oneNS(instance.NS, selector);
			},

			nsKeys: function(obj) {
				var instance = this;

				var namespace = instance.NS;

				var newObj = {};

				A.Object.each(
					obj,
					function(item, index, collection) {
						if (!startsWith(index, namespace)) {
							index = prefix(namespace, index);
						}

						newObj[index] = item;
					}
				);

				return newObj;
			},

			_getNS: function(value) {
				var instance = this;

				return instance.NS;
			}
		};

		Liferay.PortletBase = PortletBase;
	},
	'',
	{
		requires: ['aui-base', 'liferay-node']
	}
);