AUI.add(
	'liferay-ddl-form-builder-modal-support',
	function(A) {
		var Lang = A.Lang;

		var FormBuilderModalSupport = function() {
		};

		FormBuilderModalSupport.ATTRS = {
			centered: {
				valueFn: '_valueCentered'
			},

			dynamicContentHeight: {
				value: false,
				writeOnce: true
			},

			portletNamespace: {
			},

			topFixed: {
				validator: '_validateTopFixed',
				value: false
			},

			zIndex: {
				value: Liferay.zIndex.OVERLAY
			}
		};

		FormBuilderModalSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandles.push(
					instance.after(instance._afterModalRender, instance, '_afterRender'),
					instance.after(instance._bindModalUI, instance, 'bindUI')
				);
			},

			syncHeight: function() {
				var instance = this;

				var bodyNode = instance.getStdModNode(A.WidgetStdMod.BODY);

				bodyNode.setStyle('max-height', '100%');
				bodyNode.setStyle('max-height', A.DOM.winHeight(A.config.doc) - instance._getModalOffset());
			},

			_afterModalRender: function() {
				var instance = this;

				if (instance.get('dynamicContentHeight')) {
					instance._configModalDynamicHeight();

					instance.syncHeight();
				}
			},

			_afterTopFixedChange: function() {
				var instance = this;

				instance.align();
			},

			_afterWindowResize: function() {
				var instance = this;

				if (instance.get('dynamicContentHeight')) {
					instance.syncHeight();
				}

				if (instance.get('centered')) {
					instance.align();
				}
			},

			_bindModalUI: function() {
				var instance = this;

				instance._eventHandles.push(
					instance.after('topFixedChange', instance._afterTopFixedChange),
					instance.on('xyChange', instance._onModalXYChange)
				);
			},

			_configModalDynamicHeight: function() {
				var instance = this;

				instance.get('boundingBox').addClass('dynamic-content-height');
			},

			_fixAtTheTop: function(xy) {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				xy[1] = A.config.win.scrollY + Lang.toInt(boundingBox.getComputedStyle('margin-top'));

				return xy;
			},

			_getModalOffset: function() {
				var instance = this;

				var bodyNode = instance.getStdModNode(A.WidgetStdMod.BODY);

				var bodyHeight = bodyNode.get('offsetHeight');

				var boundingBox = instance.get('boundingBox');

				var outterHeight = boundingBox.get('offsetHeight') +
					Lang.toInt(boundingBox.getComputedStyle('marginTop')) +
					Lang.toInt(boundingBox.getComputedStyle('marginBottom'));

				return Math.max(bodyHeight, outterHeight) - Math.min(bodyHeight, outterHeight);
			},

			_onModalXYChange: function(event) {
				var instance = this;

				if (instance.get('centered') && instance.get('topFixed')) {
					event.newVal = instance._fixAtTheTop(event.newVal);
				}
			},

			_validateTopFixed: function() {
				var instance = this;

				return instance.get('centered');
			},

			_valueCentered: function() {
				var instance = this;

				var portletNode = A.one('#p_p_id' + instance.get('portletNamespace'));

				instance.set('centered', portletNode);
			}
		};

		Liferay.namespace('DDL').FormBuilderModalSupport = FormBuilderModalSupport;
	},
	'',
	{
		requires: ['aui-modal']
	}
);