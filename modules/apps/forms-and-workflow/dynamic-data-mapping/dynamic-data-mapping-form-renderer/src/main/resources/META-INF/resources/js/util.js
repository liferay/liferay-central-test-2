AUI.add(
	'liferay-ddm-form-renderer-util',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var VALIDATIONS = {
			number: [
				{
					label: Liferay.Language.get('is-greater-than-or-equal-to'),
					name: 'gteq',
					parameterMessage: Liferay.Language.get('this-number'),
					regex: /^(\w+)\>\=(\d+)$/,
					template: '{name}>={parameter}'
				},
				{
					label: Liferay.Language.get('is-greater-than'),
					name: 'gt',
					parameterMessage: Liferay.Language.get('this-number'),
					regex: /^(\w+)\>(\d+)$/,
					template: '{name}>{parameter}'
				},
				{
					label: Liferay.Language.get('is-equal-to'),
					name: 'eq',
					parameterMessage: Liferay.Language.get('this-number'),
					regex: /^(\w+)\=\=(\d+)$/,
					template: '{name}=={parameter}'
				},
				{
					label: Liferay.Language.get('is-less-than-or-equal-to'),
					name: 'lteq',
					parameterMessage: Liferay.Language.get('this-number'),
					regex: /^(\w+)\<\=(\d+)$/,
					template: '{name}<={parameter}'
				},
				{
					label: Liferay.Language.get('is-less-than'),
					name: 'lt',
					parameterMessage: Liferay.Language.get('this-number'),
					regex: /^(\w+)\<(\d+)$/,
					template: '{name}<{parameter}'
				}
			],
			string: [
				{
					label: Liferay.Language.get('contains'),
					name: 'contains',
					parameterMessage: Liferay.Language.get('this-text'),
					regex: /^contains\((\w+), "(\w+)"\)$/,
					template: 'contains({name}, "{parameter}")'
				},
				{
					label: Liferay.Language.get('not-contains'),
					name: 'notContains',
					parameterMessage: Liferay.Language.get('this-text'),
					regex: /^NOT\(contains\((\w+), "(\w+)"\)\)$/,
					template: 'NOT(contains({name}, "{parameter}"))'
				},
				{
					label: Liferay.Language.get('url'),
					name: 'url',
					parameterMessage: '',
					regex: /^isURL\((\w+)\)$/,
					template: 'isURL({name})'
				},
				{
					label: Liferay.Language.get('email'),
					name: 'email',
					parameterMessage: '',
					regex: /^isEmailAddress\((\w+)\)$/,
					template: 'isEmailAddress({name})'
				},
				{
					label: Liferay.Language.get('regular-expression'),
					name: 'regularExpression',
					parameterMessage: Liferay.Language.get('this-text'),
					regex: /^match\((\w+), "(.*)"\)$/,
					template: 'match({name}, "{parameter}")'
				}
			]
		};

		var Util = {
			compare: function(valueA, valueB) {
				var instance = this;

				var i;

				if (typeof valueA !== typeof valueB) {
					return false;
				}
				else if (Lang.isArray(valueA) && !Lang.isArray(valueB)) {
					return false;
				}
				else if (!Lang.isArray(valueA) && Lang.isArray(valueB)) {
					return false;
				}
				else if (Lang.isObject(valueA) && !Lang.isObject(valueB)) {
					return false;
				}
				else if (!Lang.isObject(valueA) && Lang.isObject(valueB)) {
					return false;
				}
				else if (Lang.isArray(valueA) && Lang.isArray(valueB)) {
					if (valueA.length !== valueB.length) {
						return false;
					}

					for (i = 0; i < valueA.length; i++) {
						if (!instance.compare(valueA[i], valueB[i])) {
							return false;
						}
					}

					return true;
				}
				else if (Lang.isObject(valueA) && Lang.isObject(valueB)) {
					var keysA = AObject.keys(valueA);
					var keysB = AObject.keys(valueB);

					var sameKeys = keysA.filter(
						function(keyA) {
							return keysB.indexOf(keyA) > -1;
						}
					).length === keysB.length;

					if (!sameKeys) {
						return false;
					}

					for (i = 0; i < keysA.length; i++) {
						if (!instance.compare(valueA[keysA[i]], valueB[keysA[i]])) {
							return false;
						}
					}

					return true;
				}

				return valueA === valueB;
			},

			generateInstanceId: function(length) {
				var instance = this;

				var text = '';

				var possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

				for (var i = 0; i < length; i++) {
					text += possible.charAt(Math.floor(Math.random() * possible.length));
				}

				return text;
			},

			getFieldByKey: function(haystack, needle, searchKey) {
				var instance = this;

				return instance.searchFieldsByKey(haystack, needle, searchKey)[0];
			},

			getFieldNameFromQualifiedName: function(qualifiedName) {
				var instance = this;

				var nestedFieldName = qualifiedName.split('#');

				if (nestedFieldName.length > 1) {
					return nestedFieldName[1].split('$')[0];
				}

				var name = qualifiedName.split('$$')[1];

				return name.split('$')[0];
			},

			getInstanceIdFromQualifiedName: function(qualifiedName) {
				var instance = this;

				var nestedFieldName = qualifiedName.split('#');

				if (nestedFieldName.length > 1) {
					return nestedFieldName[1].split('$')[1];
				}

				var name = qualifiedName.split('$$')[1];

				return name.split('$')[1];
			},

			getValidations: function(selectedType) {
				return VALIDATIONS[selectedType];
			},

			searchFieldsByKey: function(haystack, needle, searchKey) {
				var queue = new A.Queue(haystack);

				var results = [];

				var addToQueue = function(item) {
					queue.add(item);
				};

				searchKey = searchKey || 'name';

				while (queue.size() > 0) {
					var next = queue.next();

					if (next[searchKey] === needle) {
						results.push(next);
					}
					else {
						var children = next.fields || next.nestedFields || next.fieldValues || next.nestedFieldValues;

						if (children) {
							children.forEach(addToQueue);
						}
					}
				}

				return results;
			}
		};

		Liferay.namespace('DDM.Renderer').Util = Util;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types']
	}
);