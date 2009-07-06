(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;
	var Widget = YAHOO.widget;

	Alloy.AutoComplete = new Alloy.Class(Widget.AutoComplete);

	Alloy.AutoComplete = Alloy.AutoComplete.extend(
		{
			highlightClassName: 'aui-autocomplete-highlight',

			initialize: function(options) {
				var instance = this;

				var input = jQuery('#' + options.input);

				var resultContainer = options.resultContainer;

				if (!resultContainer) {
					resultContainer = Dom.generateId();

					Alloy.getBody().append('<div class="aui-autocomplete-container" id="' + resultContainer + '"></div>');
				}

				if (options.useShadow !== false) {
					options.useShadow = true;
				}

				if (options.useIFrame !== false) {
					options.useIFrame = options.useIFrame || (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() < 7);
				}

				instance.options = options;

				var dataSource = instance.createDataSource();

				instance._super(options.input, resultContainer, dataSource, options);

				instance.dataRequestEvent.subscribe(instance._onDataRequest);
				instance.dataReturnEvent.subscribe(instance._onDataReturn);

				var container = instance.getContainerEl();
				var contentEl = Dom.getFirstChild(container);

				Dom.addClass(contentEl, 'aui-autocomplete-content');
				input.addClass('aui-autocomplete-input');
			},

			createDataSource: function() {
				var instance = this;

				var options = instance.options;
				var dataSource = options.dataSource;

				var source = dataSource.source;

				delete options.dataSource.source;

				instance._dataSource = new Alloy.DataSource(source, options.dataSource);

				delete options.dataSource;

				return instance._dataSource;
			},

			doBeforeExpandContainer: function(inputEl, containerEl, query, results) {
				var instance = this;

				var options = instance.options;

				var container = jQuery(containerEl);
				var input = jQuery(inputEl);

				var offset = input.offset();

				var height = input.outerHeight();
				var width = input.outerWidth();

				width += options.width || 0;

				container.css(
					{
						left: offset.left,
						top: offset.top + height,
						width: width
					}
				);

				return true;
			},

			formatResult: function(resultData, query, resultMatch) {
				var instance = this;

				var re = new RegExp(query, 'gim');

				resultMatch = resultMatch.replace(re, '<strong>' + query + '</strong>')

				return resultMatch;
			},

			getDataSource: function() {
				var instance = this;

				return instance._dataSource;
			},

			_initContainerHelperEls: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				if (instance.useIFrame) {
					Dom.addClass(instance._elIFrame, 'aui-autocomplete-iframe');
				}

				if (instance.useShadow) {
					Dom.addClass(instance._elShadow, 'aui-autocomplete-shadow');
				}
			},

			_onDataRequest: function() {
				var instance = this;

				var el = instance.getInputEl();

				Dom.addClass(el, 'aui-autocomplete-loading');
			},

			_onDataReturn: function() {
				var instance = this;

				var el = instance.getInputEl();

				Dom.removeClass(el, 'aui-autocomplete-loading');
			}
		}
	);
})();