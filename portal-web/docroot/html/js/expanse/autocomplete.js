(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;
	var Widget = YAHOO.widget;

	Expanse.AutoComplete = new Expanse.Class(Widget.AutoComplete);

	Expanse.AutoComplete = Expanse.AutoComplete.extend(
		{
			highlightClassName: 'exp-autocomplete-highlight',

			initialize: function(options) {
				var instance = this;

				var input = jQuery('#' + options.input);

				var resultContainer = options.resultContainer;

				if (!resultContainer) {
					resultContainer = Dom.generateId();

					Expanse.getBody().append('<div class="exp-autocomplete-container" id="' + resultContainer + '"></div>');
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

				Dom.addClass(contentEl, 'exp-autocomplete-content');
				input.addClass('exp-autocomplete-input');
			},

			createDataSource: function() {
				var instance = this;

				var options = instance.options;
				var dataSource = options.dataSource;

				var source = dataSource.source;

				delete options.dataSource.source;

				instance._dataSource = new Expanse.DataSource(source, options.dataSource);

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
					Dom.addClass(instance._elIFrame, 'exp-autocomplete-iframe');
				}

				if (instance.useShadow) {
					Dom.addClass(instance._elShadow, 'exp-autocomplete-shadow');
				}
			},

			_onDataRequest: function() {
				var instance = this;

				var el = instance.getInputEl();

				Dom.addClass(el, 'exp-autocomplete-loading');
			},

			_onDataReturn: function() {
				var instance = this;

				var el = instance.getInputEl();

				Dom.removeClass(el, 'exp-autocomplete-loading');
			}
		}
	);
})();