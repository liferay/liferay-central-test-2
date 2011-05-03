
AUI().add(
	'liferay-list-view',
	function(A) {
		var BOTTOM = 'bottom';

		var CONTAINER = 'container';

		var CONTENT_BOX = 'contentBox';

		var DATA = 'data';

		var DEFAULT_TRANSITION_CONFIG = {
			duration: 1,
			easing: 'ease-out',
			left: 0,
			top: 0
		};

		var getClassName = A.ClassNameManager.getClassName;

		var ITEM_CHOSEN = 'itemChosen';

		var Lang = A.Lang;

		var LEFT = 'left';

		var NAME = 'liferaylistview';

		var CLASS_DATA_CONTAINER = getClassName(NAME, DATA, CONTAINER);

		var CLASS_DATA_CONTAINER_HIDDEN = getClassName(NAME, DATA, CONTAINER, 'hidden');

		var PX = 'px';

		var REGION = 'region';

		var RIGHT = 'right';

		var TOP = 'top';

		var TPL_DATA_CONTAINER = '<div class="' + CLASS_DATA_CONTAINER + ' ' + CLASS_DATA_CONTAINER_HIDDEN + '"></div>';

		var ZERO_PX = '0px';

		var ListView = A.Component.create(
			{
				EXTENDS: A.Widget,

				ATTRS: {
					data: {
						setter: '_setData',
						value: null,
						validator: '_validateData'
					},

					direction: {
						value: LEFT,
						validator: '_validateDirection'
					},

					itemChosen: {
						value: 'click',
						validtor: Lang.isString
					},

					itemSelector: {
						value: null,
						validtor: Lang.isString
					},

					itemAttributes: {
						value: 'href',
						validator: '_validateItemAttributes'
					},

					transitionConfig: {
						value: DEFAULT_TRANSITION_CONFIG,
						validator: Lang.isObject
					},

					useTransition: {
						value: true,
						validator: Lang.isBoolean
					}
				},

				NAME: NAME,

				prototype: {

					bindUI: function() {
						var instance = this;

						instance._itemChosenHandle = A.delegate(
							instance.get(ITEM_CHOSEN),
							A.bind(instance._onItemChosen, instance),
							instance.get(CONTENT_BOX),
							instance.get('itemSelector')
						);

						instance.after(
							DATA + 'Change',
							A.bind(instance._afterDataChange, instance)
						);
					},

					destructor: function() {
						var instance = this;

						if (instance._itemChosenHandle) {
							instance._itemChosenHandle.detach();
						}

						if (instance._dataContainer) {
							instance._dataContainer.destroy(true);
						}
					},

					renderUI: function() {
						var instance = this;

						var dataContainer = A.Node.create(TPL_DATA_CONTAINER);

						var boundingBox = instance.get('boundingBox');

						boundingBox.appendChild(dataContainer);

						instance._dataContainer = dataContainer;
					},

					_afterDataChange: function(value) {
						var instance = this;

						var useTransition = instance.get('useTransition');

						if (useTransition) {
							var dataContainer = instance._dataContainer;

							dataContainer.empty();

							instance._dataContainer.appendChild(value.newVal);

							instance._moveContainer();
						}
						else {
							instance.get(CONTENT_BOX).html(value.newVal);
						}
					},

					_moveContainer: function() {
						var instance = this;

						var contentBox = instance.get(CONTENT_BOX);

						var targetRegion = contentBox.get(REGION);

						instance._setDataContainerPosition(targetRegion);

						var dataContainer = instance._dataContainer;

						dataContainer.removeClass(CLASS_DATA_CONTAINER_HIDDEN);

						var transitionConfig = instance.get('transitionConfig');

						dataContainer.transition(transitionConfig, A.bind(instance._onTransitionCompleted, instance));
					},

					_onItemChosen: function(event) {
						var instance = this;

						var itemAttributes = instance.get('itemAttributes');

						if (itemAttributes) {
							event.preventDefault();

							itemAttributes = A.Array(itemAttributes);

							var attributesData = {};

							var target = event.currentTarget;

							A.Array.each(itemAttributes, function(item, index, array) {
								var attributeData = target.getAttribute(item);

								attributesData[item] = attributeData;
							});

							var params = {
								target: target,
								attributes: attributesData
							};

							instance.fire(ITEM_CHOSEN, params);
						}
					},

					_onTransitionCompleted: function() {
						var instance = this;

						var dataContainer = instance._dataContainer;

						instance.get(CONTENT_BOX).html(dataContainer.html());

						dataContainer.addClass(CLASS_DATA_CONTAINER_HIDDEN);

						dataContainer.empty();
					},

					_setData: function(value) {
						if (Lang.isString(value)) {
							value = A.Node.create(value);
						}

						return value;
					},

					_setDataContainerPosition: function(targetRegion) {
						var instance = this;

						targetRegion = targetRegion || instance.get(CONTENT_BOX).get(REGION);

						var direction = instance.get('direction');

						var dataContainer = instance._dataContainer;

						if (direction == LEFT) {
							dataContainer.setStyle(LEFT, targetRegion.width + PX );
							dataContainer.setStyle(TOP, ZERO_PX );
						}
						else if (direction == RIGHT) {
							dataContainer.setStyle(LEFT, -targetRegion.width + PX );
							dataContainer.setStyle(TOP, ZERO_PX );
						}
						else if (direction == TOP) {
							dataContainer.setStyle(LEFT, ZERO_PX );
							dataContainer.setStyle(TOP, -targetRegion.height + PX );
						}
						else if (direction == BOTTOM) {
							dataContainer.setStyle(LEFT, ZERO_PX );
							dataContainer.setStyle(TOP, targetRegion.height + PX );
						}
						else {
							throw 'Internal error. Unsupported direction value';
						}
					},

					_validateData: function(value) {
						return Lang.isString(value) || (value instanceof A.Node);
					},

					_validateDirection: function(value) {
						return value === BOTTOM || value === LEFT ||
							value === RIGHT || value === TOP;
					},

					_validateItemAttributes: function(value) {
						return Lang.isString(value) || Lang.isArray(value);
					}
				}
			}
		);

		Liferay.ListView = ListView;
	},
	'',
	{
		requires: ['aui-widget', 'aui-paginator']
	}
);