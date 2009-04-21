(function() {
	var Dom = Expanse.Dom;
	var Event = Expanse.Event;

	var CalendarGroup = YAHOO.widget.CalendarGroup;
	var CalendarNavigator = YAHOO.widget.CalendarNavigator;

	CalendarGroup.CSS_CONTAINER = 'exp-calendar-container';
	CalendarGroup.CSS_MULTI_UP = 'exp-calendar-multiple';
	CalendarGroup.CSS_2UPTITLE = 'exp-calendar-title';
	CalendarGroup.CSS_2UPCLOSE = 'exp-calendar-close';

	Expanse.extend(
		CalendarNavigator.CLASSES,
		{
			BUTTON: 'exp-calendar-navigator-button',
			BUTTONS: 'exp-calendar-navigator-buttons',
			DEFAULT: 'exp-calendar-default',
			ERROR: 'exp-calendar-navigator-error',
			INVALID: 'exp-calendar-invalid',
			MASK: 'exp-calendar-navigator-mask',
			MONTH: 'exp-calendar-navigator-month',
			MONTH_CTRL: 'exp-calendar-navigator-month-control',
			NAV: 'exp-calendar-navigator',
			NAV_VISIBLE: 'exp-calendar-navigator-visible',
			YEAR: 'exp-calendar-navigator-year',
			YEAR_CTRL: 'exp-calendar-navigator-year-control'
		}
	);

	Expanse.Calendar = new Expanse.Widget(YAHOO.widget.Calendar);

	Expanse.Calendar = Expanse.Calendar.extend(
		{
			initStyles: function() {
				var instance = this;

				instance._super.apply(instance, arguments);

				Expanse.extend(
					instance.Style,
					{
						CSS_BODY : 'exp-calendar-body',
						CSS_CALENDAR : 'exp-calendar',
						CSS_CELL : 'exp-calendar-cell',
						CSS_CELL_BOTTOM : 'exp-calendar-cell-bottom',
						CSS_CELL_HIGHLIGHT1 : 'exp-calendar-highlight1',
						CSS_CELL_HIGHLIGHT2 : 'exp-calendar-highlight2',
						CSS_CELL_HIGHLIGHT3 : 'exp-calendar-highlight3',
						CSS_CELL_HIGHLIGHT4 : 'exp-calendar-highlight4',
						CSS_CELL_HOVER : 'exp-calendar-cell-hover',
						CSS_CELL_LEFT : 'exp-calendar-cell-left',
						CSS_CELL_OOB : 'exp-calendar-previous',
						CSS_CELL_OOM : 'exp-calendar-oom',
						CSS_CELL_RESTRICTED : 'exp-calendar-restricted',
						CSS_CELL_RIGHT : 'exp-calendar-cell-right',
						CSS_CELL_SELECTABLE : 'exp-calendar-selectable',
						CSS_CELL_SELECTED : 'exp-calendar-selected',
						CSS_CELL_SELECTOR : 'exp-calendar-selector',
						CSS_CELL_TODAY : 'exp-calendar-today',
						CSS_CELL_TOP : 'exp-calendar-cell-top',
						CSS_CLOSE : 'exp-calendar-close',
						CSS_CONTAINER : 'exp-calendar-container',
						CSS_FOOTER : 'exp-calendar-foot',
						CSS_HEADER : 'exp-calendar-header',
						CSS_HEADER_TEXT : 'exp-calendar-head',
						CSS_NAV : 'exp-calendar-nav',
						CSS_NAV_LEFT : 'exp-calendar-nav-left',
						CSS_NAV_RIGHT : 'exp-calendar-nav-right',
						CSS_ROW_FOOTER: 'exp-calendar-rowfoot',
						CSS_ROW_HEADER: 'exp-calendar-rowhead',
						CSS_SINGLE : 'exp-calendar-single',
						CSS_WEEKDAY_CELL : 'exp-calendar-weekday-cell',
						CSS_WEEKDAY_ROW : 'exp-calendar-weekday-row'
					}
				);
			}
		}
	);

	Expanse.DatePicker = Expanse.Calendar.extend(
		{
			initialize: function(options) {
				var instance = this;

				var button = Dom.generateId(options.button);

				var panel = new Expanse.DatePicker.Panel(button, options.panelOptions);

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

	Expanse.DatePicker.Panel = Expanse.Overlay.extend(
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
					visible: false
				};

				options = Expanse.extend({}, defaults, options);

				instance._super(options.el || Expanse.generateId(), options);
			}
		}
	);
})();