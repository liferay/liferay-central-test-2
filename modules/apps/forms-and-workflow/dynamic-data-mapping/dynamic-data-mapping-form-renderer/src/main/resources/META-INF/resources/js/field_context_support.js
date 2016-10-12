AUI.add(
	'liferay-ddm-form-renderer-field-context-support',
	function(A) {
		var AObject = A.Object;

		var Renderer = Liferay.DDM.Renderer;

		var FieldTypes = Renderer.FieldTypes;
		var Util = Renderer.Util;

		var FieldContextSupport = function(config) {};

		FieldContextSupport.ATTRS = {
			context: {
				setter: '_setContext',
				value: {}
			}
		};

		FieldContextSupport.prototype = {
			initializer: function() {
				var instance = this;

				var type = instance.get('type');

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				var fieldClass = A.Object.getValue(window, fieldClassName.split('.'));

				instance._eventHandlers = [];

				instance._unrepaintableAttributes = {};

				instance.bindFieldClassAttributesStatus(fieldClass);
			},

			bindFieldClassAttributesStatus: function(fieldClass) {				
				var instance = this;

				var EXTENDS = fieldClass;

				var context = instance.get('context');

				var setAttributeChangeEvent = function(attributeName) {
					if (EXTENDS.ATTRS[attributeName].state) {
						if (context[attributeName]) {
							instance.set(attributeName, context[attributeName]);
						}
						else {
							context[attributeName] = instance.get(attributeName);
						}

						instance.after(attributeName + 'Change', A.bind(instance._afterAttributeChange, instance, attributeName));
					}
					else {
						instance._unrepaintableAttributes[attributeName] = true;
					}
				};

				for (var attr in context) {
					if (!instance.getAttrs().hasOwnProperty(attr)) {

						var config = {
							state: true,
							value: context[attr]
						};

						instance.addAttr(attr, config);
						instance.after(attr + 'Change', A.bind(instance._afterAttributeChange, instance, attr));
					}
				}

				while (EXTENDS) {
					AObject.keys(EXTENDS.ATTRS).forEach(setAttributeChangeEvent);

					EXTENDS = EXTENDS.EXTENDS;
				}

				instance._eventHandlers.push(instance.after('contextChange', instance._afterContextChange));
			},

			isRepaintable: function(attributeName) {
				var instance = this;

				var context = instance.get('context');

				return context && context.hasOwnProperty(attributeName) && !instance._unrepaintableAttributes[attributeName];
			},

			_afterAttributeChange: function(name) {
				var instance = this;

				var value = instance.get(name);

				var context = instance.get('context');

				if (!Util.compare(value, context[name])) {
					instance.set('context.' + name, value);
				}
			},

			_afterContextChange: function(event) {
				var instance = this;

				var repaint = false;

				var newContext = event.newVal;

				var oldContext = event.prevVal;

				for (var name in newContext) {
					if (!Util.compare(newContext[name], oldContext[name])) {
						if (instance.isRepaintable(name)) {
							repaint = true;
						}
					}

					if (!Util.compare(newContext[name], instance.get(name))) {
						instance.set(name, newContext[name]);
					}
				}

				if (repaint && instance.get('rendered')) {
					instance.render();
				}
			},

			_setContext: function(val) {
				var instance = this;

				return A.merge(instance.get('context'), val);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldContextSupport = FieldContextSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);