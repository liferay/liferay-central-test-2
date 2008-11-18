Liferay.Panel = Liferay.Observable.extend({

	/**
	 * OPTIONS
	 *
	 * Optional
	 * container {string|object}: A jQuery selector of the panel container if there are multiple panels handled by this one.
	 * panel {string|object}: A jQuery selector of the panel.
	 * panelContent {string|object}: A jQuery selector of the content section of the panel.
	 * header {string|object}: A jQuery selector of the panel's header area.
	 * titles {string|object}: A jQuery selector of the titles in the panel.
	 * footer {string|object}: A jQuery selector of the panel's footer area.
	 * isAccordian {boolean}: Whether or not the panels have accordion behavior (meaning only one panel can be open at a time).
	 * isCollapsible {boolean}: Whether or not the panel can be collapsed by clicking the title.
	 *
	 */

	initialize: function(options) {
		var instance = this;

		var defaults = {
			container: null,
			panel: '.lfr-panel',
			panelContent: '.lfr-panel-content',
			header: '.lfr-panel-header',
			titles: '.lfr-panel-titlebar',
			footer: '.lfr-panel-footer',
			isAccordian: true,
			isCollapsible: true
		};

		options = jQuery.extend(defaults, options);

		instance._inContainer = false;
		instance._container = jQuery(document.body);

		if (options.container) {
			instance._container = jQuery(options.container);
			instance._inContainer = true;
		}

		instance._panel = instance._container.find(options.panel);

		instance._panelContent = instance._panel.find(options.panelContent);
		instance._header = instance._panel.find(options.header);
		instance._footer = instance._panel.find(options.footer);
		instance._panelTitles = instance._panel.find(options.titles);
		instance._isAccordion = options.isAccordian;

		instance._isCollapsible = options.isCollapsible;

		if (instance._isCollapsible) {
			instance.makeCollapsible();

			instance._panelTitles.disableSelection();
			instance._panelTitles.css(
				{
					cursor: 'pointer'
				}
			);

			var collapsedPanels = instance._panel.filter('.lfr-collapsed');

			if (instance._isAccordion && !collapsedPanels.length) {
				instance._panel.slice(1).addClass('lfr-collapsed');
			}
		}

		instance.set('container', instance._container);
		instance.set('panel', instance._panel);
		instance.set('panelContent', instance._panelContent);
		instance.set('panelTitles', instance._panelTitles);
	},

	makeCollapsible: function() {
		var instance = this;

		instance._panelTitles.each(
			function(i, n) {
				if (this.className && this.className.indexOf('lfr-has-button') > -1) {
					var title = jQuery(this);
					var toggler = title.find('.lfr-panel-button');

					if (!toggler.length) {
						title.append('<a class="lfr-panel-button" href="javascript: ;"></a>');
					}
				}
			}
		);

		instance._panelTitles.mousedown(
			function(event) {
				instance.onTitleClick(this);
			}
		);
	},

	onTitleClick: function(el) {
		var instance = this;

		var currentContainer = jQuery(el).parents('.lfr-panel');

		currentContainer.toggleClass('lfr-collapsed');

		if (instance._isAccordion) {
			currentContainer.siblings('.lfr-panel').addClass('lfr-collapsed');
		}

		instance.trigger('titleClick');
	}
});