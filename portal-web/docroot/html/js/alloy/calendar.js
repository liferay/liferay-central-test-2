(function() {
	var Dom = Alloy.Dom;
	var Event = Alloy.Event;

	var CalendarGroup = YAHOO.widget.CalendarGroup;
	var CalendarNavigator = YAHOO.widget.CalendarNavigator;

	CalendarGroup.CSS_CONTAINER = 'aui-calendar-container';
	CalendarGroup.CSS_MULTI_UP = 'aui-calendar-multiple';
	CalendarGroup.CSS_2UPTITLE = 'aui-calendar-title';
	CalendarGroup.CSS_2UPCLOSE = 'aui-calendar-close';

	Alloy.extend(
		CalendarNavigator.CLASSES,
		{
			BUTTON: 'aui-calendar-navigator-button',
			BUTTONS: 'aui-calendar-navigator-buttons',
			DEFAULT: 'aui-calendar-default',
			ERROR: 'aui-calendar-navigator-error',
			INVALID: 'aui-calendar-invalid',
			MASK: 'aui-calendar-navigator-mask',
			MONTH: 'aui-calendar-navigator-month',
			MONTH_CTRL: 'aui-calendar-navigator-month-control',
			NAV: 'aui-calendar-navigator',
			NAV_VISIBLE: 'aui-calendar-navigator-visible',
			YEAR: 'aui-calendar-navigator-year',
			YEAR_CTRL: 'aui-calendar-navigator-year-control'
		}
	);

	Alloy.Calendar = new Alloy.Widget(YAHOO.widget.Calendar);

	Alloy.Calendar = Alloy.Calendar.extend(
		{
			initStyles: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Alloy.extend(
					instance.Style,
					{
						CSS_BODY : 'aui-calendar-body',
						CSS_CALENDAR : 'aui-calendar',
						CSS_CELL : 'aui-calendar-cell',
						CSS_CELL_BOTTOM : 'aui-calendar-cell-bottom',
						CSS_CELL_HIGHLIGHT1 : 'aui-calendar-highlight1',
						CSS_CELL_HIGHLIGHT2 : 'aui-calendar-highlight2',
						CSS_CELL_HIGHLIGHT3 : 'aui-calendar-highlight3',
						CSS_CELL_HIGHLIGHT4 : 'aui-calendar-highlight4',
						CSS_CELL_HOVER : 'aui-calendar-cell-hover',
						CSS_CELL_LEFT : 'aui-calendar-cell-left',
						CSS_CELL_OOB : 'aui-calendar-previous',
						CSS_CELL_OOM : 'aui-calendar-oom',
						CSS_CELL_RESTRICTED : 'aui-calendar-restricted',
						CSS_CELL_RIGHT : 'aui-calendar-cell-right',
						CSS_CELL_SELECTABLE : 'aui-calendar-selectable',
						CSS_CELL_SELECTED : 'aui-calendar-selected',
						CSS_CELL_SELECTOR : 'aui-calendar-selector',
						CSS_CELL_TODAY : 'aui-calendar-today',
						CSS_CELL_TOP : 'aui-calendar-cell-top',
						CSS_CLOSE : 'aui-calendar-close',
						CSS_CONTAINER : 'aui-calendar-container',
						CSS_FOOTER : 'aui-calendar-foot',
						CSS_HEADER : 'aui-calendar-header',
						CSS_HEADER_TEXT : 'aui-calendar-head',
						CSS_NAV : 'aui-calendar-nav',
						CSS_NAV_LEFT : 'aui-calendar-nav-left',
						CSS_NAV_RIGHT : 'aui-calendar-nav-right',
						CSS_ROW_FOOTER: 'aui-calendar-rowfoot',
						CSS_ROW_HEADER: 'aui-calendar-rowhead',
						CSS_SINGLE : 'aui-calendar-single',
						CSS_WEEKDAY_CELL : 'aui-calendar-weekday-cell',
						CSS_WEEKDAY_ROW : 'aui-calendar-weekday-row'
					}
				);
			}
		}
	);

	Alloy.DatePicker = Alloy.Calendar.extend(
		{
			initialize: function(options) {
				var instance = this;

				var button = Dom.generateId(options.button);

				var panel = new Alloy.DatePicker.Panel(button, options.panelOptions);

				var calendarBodyId = Dom.generateId();

				panel.setBody('<div id="' + calendarBodyId + '"></div>');
				panel.render(document.body);

				options.iframe = false;

				instance._super(calendarBodyId, options);

				instance.render();

				instance.panel = panel;
				instance.button = Dom.get(button);

				instance.renderEvent.subscribe(instance._contentChanged);

				Event.on(button, 'click', instance._showDatePicker, instance, true);
				Event.on(panel.element, 'click', instance._swallowInternalClicks, instance, true);

				instance.selectEvent.subscribe(instance.panel.hide, instance.panel, true);
			},

			_contentChanged: function() {
				var instance = this;

				instance.panel.fireEvent('changeContent');
			},

			_hideDatePicker: function() {
				var instance = this;

				instance.panel.hide();

				Event.un(document, 'click', arguments.callee);
			},

			_showDatePicker: function(event) {
				var instance = this;

				instance.panel.show();

				Event.on(document, 'click', instance._hideDatePicker, instance, true);

				Event.stopEvent(event);
			},

			_swallowInternalClicks: function(event) {
				var instance = this;

				Event.stopPropagation(event);
			}
		}
	);

	Alloy.DatePicker.Panel = Alloy.Overlay.extend(
		{
			initialize: function(button, options) {
				var instance = this;

				var defaults = {
					close: true,
					constraintoviewport: true,
					context: [button, 'tl', 'bl', ['beforeShow']],
					draggable: false,
					header: 'choose a date',
					preventcontextoverlap: true,
					visible: false,
					zIndex: Alloy.zIndex.DATE_PICKER
				};

				options = Alloy.extend({}, defaults, options);

				instance._super(options.el || Alloy.generateId(), options);
			}
		}
	);
})();