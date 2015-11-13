(function() {
	var STR_BLANK = '';

	var STR_DASH = '-';

	var STR_DOT = '.';

	var STR_PLUS = '+';

	var STR_SPACE = ' ';

	AUI.add(
		'liferay-calendar-simple-menu',
		function(A) {
			var Lang = A.Lang;

			var getClassName = A.getClassName;

			var isArray = Lang.isArray;

			var CSS_SIMPLE_MENU_ITEM = getClassName('simple-menu', 'item');

			var CSS_SIMPLE_MENU_ITEM_HIDDEN = getClassName('simple-menu', 'item', 'hidden');

			var CSS_SIMPLE_MENU_SEPARATOR = getClassName('simple-menu', 'separator');

			var DEFAULT_ALIGN_POINTS = [A.WidgetPositionAlign.TL, A.WidgetPositionAlign.BL];

			var TPL_ICON = '<i class="{iconClass}"></i>';

			var TPL_SIMPLE_MENU_ITEM = '<li class="{cssClass}" data-id="{id}">{icon} {caption}</li>';

			var getItemHandler = A.cached(
				function(id, items) {
					var found = null;

					items.some(
						function(item, index) {
							if (item.id === id) {
								found = item;

								return true;
							}
						}
					);

					return found && found.fn;
				}
			);

			var SimpleMenu = A.Component.create(
				{

					ATTRS: {
						alignNode: {
							value: null
						},

						hiddenItems: {
							validator: isArray,
							value: []
						},

						host: {
							value: null
						},

						items: {
							validator: isArray,
							value: []
						}
					},

					AUGMENTS: [A.WidgetModality, A.WidgetPosition, A.WidgetPositionAlign, A.WidgetPositionConstrain, A.WidgetStack, A.WidgetStdMod],

					NAME: 'simple-menu',

					UI_ATTRS: ['hiddenItems', 'items'],

					prototype: {
						CONTENT_TEMPLATE: '<ul></ul>',

						renderUI: function() {
							var instance = this;

							instance.get('boundingBox').unselectable();

							instance._renderItems(instance.get('items'));
						},

						bindUI: function() {
							var instance = this;

							A.Event.defineOutside('touchend');

							var contentBox = instance.get('contentBox');

							contentBox.delegate('click', instance._onClickItems, STR_DOT + CSS_SIMPLE_MENU_ITEM, instance);

							contentBox.on('touchendoutside', instance._closeMenu, instance);

							A.getDoc().on('click', instance._closeMenu, instance);

							A.getWin().on(
								'resize',
								A.debounce(instance._positionMenu, 200, instance)
							);

							instance.after('visibleChange', instance._positionMenu, instance);
						},

						_closeMenu: function() {
							var instance = this;

							instance.hide();
						},

						_onClickItems: function(event) {
							var instance = this;

							var items = instance.get('items');

							var id = event.currentTarget.attr('data-id');

							var handler = getItemHandler(id, items);

							if (handler) {
								handler.apply(instance, arguments);
							}
						},

						_positionMenu: function() {
							var instance = this;

							var Util = Liferay.Util;

							var align = {
								node: instance.get('alignNode'),
								points: DEFAULT_ALIGN_POINTS
							};

							var centered = false;
							var modal = false;
							var width = 222;

							if (Util.isPhone() || Util.isTablet()) {
								align = null;
								centered = true;
								modal = true;
								width = '90%';
							}

							instance.setAttrs(
								{
									align: align,
									centered: centered,
									modal: modal,
									width: width
								}
							);
						},

						_renderItems: function(items) {
							var instance = this;

							var contentBox = instance.get('contentBox');
							var hiddenItems = instance.get('hiddenItems');

							instance.items = A.NodeList.create();

							items.forEach(
								function(item, index) {
									var caption = item.caption;

									if (!item.hasOwnProperty('id')) {
										item.id = A.guid();
									}

									var id = item.id;

									var cssClass = CSS_SIMPLE_MENU_ITEM;

									if (caption == STR_DASH) {
										cssClass = CSS_SIMPLE_MENU_SEPARATOR;
									}

									if (hiddenItems.indexOf(id) > -1) {
										cssClass += STR_SPACE + CSS_SIMPLE_MENU_ITEM_HIDDEN;
									}

									if (item.cssClass) {
										cssClass += STR_SPACE + item.cssClass;
									}

									var icon = STR_BLANK;

									if (item.icon) {
										icon = Lang.sub(
											TPL_ICON,
											{
												iconClass: item.icon
											}
										);

										caption = [icon, caption].join(STR_SPACE);
									}

									var li = A.Node.create(
										Lang.sub(
											TPL_SIMPLE_MENU_ITEM,
											{
												cssClass: cssClass,
												icon: icon,
												id: id
											}
										)
									);

									li.setContent(caption);

									instance.items.push(li);
								}
							);

							contentBox.setContent(instance.items);
						},

						_uiSetHiddenItems: function(val) {
							var instance = this;

							if (instance.get('rendered')) {
								instance.items.each(
									function(item, index) {
										var id = item.attr('data-id');

										item.toggleClass(CSS_SIMPLE_MENU_ITEM_HIDDEN, val.indexOf(id) > -1);
									}
								);
							}
						},

						_uiSetItems: function(val) {
							var instance = this;

							if (instance.get('rendered')) {
								instance._renderItems(val);
							}
						}
					}
				}
			);

			Liferay.SimpleMenu = SimpleMenu;
		},
		'',
		{
			requires: ['aui-base', 'aui-template-deprecated', 'event-outside', 'event-touch', 'widget-modality', 'widget-position', 'widget-position-align', 'widget-position-constrain', 'widget-stack', 'widget-stdmod']
		}
	);

	AUI.add(
		'liferay-calendar-list',
		function(A) {
			var AArray = A.Array;
			var Lang = A.Lang;

			var isArray = Lang.isArray;
			var isObject = Lang.isObject;

			var	getClassName = A.getClassName;

			var STR_CALENDAR_LIST = 'calendar-list';

			var STR_ITEM = 'item';

			var CSS_CALENDAR_LIST_EMPTY_MESSAGE = getClassName(STR_CALENDAR_LIST, 'empty', 'message');

			var CSS_CALENDAR_LIST_ITEM = getClassName(STR_CALENDAR_LIST, STR_ITEM);

			var CSS_CALENDAR_LIST_ITEM_ACTIVE = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'active');

			var CSS_CALENDAR_LIST_ITEM_ARROW = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'arrow');

			var CSS_CALENDAR_LIST_ITEM_COLOR = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'color');

			var CSS_CALENDAR_LIST_ITEM_HOVER = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'hover');

			var CSS_CALENDAR_LIST_ITEM_LABEL = getClassName(STR_CALENDAR_LIST, STR_ITEM, 'label');

			var CSS_ICON_CARET_DOWN = 'icon-caret-down';

			var TPL_CALENDAR_LIST_EMPTY_MESSAGE = '<div class="' + CSS_CALENDAR_LIST_EMPTY_MESSAGE + '">{message}</div>';

			var TPL_CALENDAR_LIST_ITEM = new A.Template(
				'<tpl for="calendars">',
					'<div class="', CSS_CALENDAR_LIST_ITEM, '">',
						'<div class="', CSS_CALENDAR_LIST_ITEM_COLOR, '" {[ parent.calendars[$i].get("visible") ? ', '\'style="background-color:\'', STR_PLUS, 'parent.calendars[$i].get("color")', STR_PLUS, '";border-color:"', STR_PLUS, 'parent.calendars[$i].get("color")', STR_PLUS, '";\\""', ' : \'', STR_BLANK, '\' ]}></div>',
						'<span class="', CSS_CALENDAR_LIST_ITEM_LABEL, '">{[LString.escapeHTML(parent.calendars[$i].getDisplayName())]}</span>',
						'<div class="', CSS_CALENDAR_LIST_ITEM_ARROW, '">',
							'<i class="', CSS_ICON_CARET_DOWN, '"></i>',
						'</div>',
					'</div>',
				'</tpl>'
			);

			var CalendarList = A.Component.create(
				{
					ATTRS: {
						calendars: {
							setter: '_setCalendars',
							validator: isArray,
							value: []
						},

						scheduler: {
						},

						simpleMenu: {
							setter: '_setSimpleMenu',
							validator: isObject,
							value: null,
							zIndex: Liferay.zIndex.MENU
						},

						strings: {
							value: {
								emptyMessage: Liferay.Language.get('no-calendars-selected')
							}
						}
					},

					NAME: 'calendar-list',

					UI_ATTRS: ['calendars'],

					prototype: {
						initializer: function() {
							var instance = this;

							var emptyMessage = instance.get('strings.emptyMessage');

							instance.emptyMessageNode = A.Node.create(
								Lang.sub(
									TPL_CALENDAR_LIST_EMPTY_MESSAGE,
									{
										message: emptyMessage
									}
								)
							);

							instance.simpleMenu = new Liferay.SimpleMenu(instance.get('simpleMenu'));
						},

						renderUI: function() {
							var instance = this;

							instance._renderCalendars();

							instance.simpleMenu.render();
						},

						bindUI: function() {
							var instance = this;

							var contentBox = instance.get('contentBox');

							instance.on('scheduler-calendar:colorChange', instance._onCalendarColorChange, instance);
							instance.on('scheduler-calendar:visibleChange', instance._onCalendarVisibleChange, instance);
							instance.on('simple-menu:visibleChange', instance._onSimpleMenuVisibleChange, instance);

							contentBox.delegate('click', instance._onClick, STR_DOT + CSS_CALENDAR_LIST_ITEM, instance);

							contentBox.delegate(
								'hover',
								A.bind('_onHoverOver', instance),
								A.bind('_onHoverOut', instance),
								STR_DOT + CSS_CALENDAR_LIST_ITEM
							);
						},

						add: function(calendar) {
							var instance = this;

							var calendars = instance.get('calendars');

							calendars.push(calendar);

							instance.set('calendars', calendars);
						},

						clear: function() {
							var instance = this;

							instance.set('calendars', []);
						},

						getCalendar: function(calendarId) {
							var instance = this;

							var calendars = instance.get('calendars');

							var calendar = null;

							for (var i = 0; i < calendars.length; i++) {
								var cal = calendars[i];

								if (cal.get('calendarId') === calendarId) {
									calendar = cal;

									break;
								}
							}

							return calendar;
						},

						getCalendarByNode: function(node) {
							var instance = this;

							var calendars = instance.get('calendars');

							return calendars[instance.items.indexOf(node)];
						},

						getCalendarNode: function(calendar) {
							var instance = this;

							var calendars = instance.get('calendars');

							return instance.items.item(calendars.indexOf(calendar));
						},

						remove: function(calendar) {
							var instance = this;

							var calendars = instance.get('calendars');

							if (calendars.length > 0) {
								var index = calendars.indexOf(calendar);

								if (index > -1) {
									AArray.remove(calendars, index);
								}
							}

							instance.set('calendars', calendars);
						},

						_clearCalendarColor: function(calendar) {
							var instance = this;

							var node = instance.getCalendarNode(calendar);

							var colorNode = node.one(STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

							colorNode.setAttribute('style', STR_BLANK);
						},

						_onCalendarColorChange: function(event) {
							var instance = this;

							var target = event.target;

							if (target.get('visible')) {
								instance._setCalendarColor(target, event.newVal);
							}
						},

						_onCalendarVisibleChange: function(event) {
							var instance = this;

							var target = event.target;

							if (event.newVal) {
								instance._setCalendarColor(target, target.get('color'));
							}
							else {
								instance._clearCalendarColor(target);
							}
						},

						_onClick: function(event) {
							var instance = this;

							var target = event.target.ancestor(STR_DOT + CSS_CALENDAR_LIST_ITEM_ARROW, true, STR_DOT + CSS_CALENDAR_LIST_ITEM);

							if (target) {
								event.stopPropagation();

								var activeNode = instance.activeNode;

								if (activeNode) {
									activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
								}

								activeNode = event.currentTarget;

								instance.activeItem = instance.getCalendarByNode(activeNode);

								activeNode.addClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);

								instance.activeNode = activeNode;

								var simpleMenu = instance.simpleMenu;

								simpleMenu.setAttrs(
									{
										alignNode: target,
										visible: simpleMenu.get('align.node') !== target || !simpleMenu.get('visible')
									}
								);
							}
							else {
								var calendar = instance.getCalendarByNode(event.currentTarget);

								calendar.set('visible', !calendar.get('visible'));
							}
						},

						_onHoverOut: function(event) {
							var instance = this;

							var currentTarget = event.currentTarget;

							var calendar = instance.getCalendarByNode(currentTarget);

							if (!calendar.get('visible')) {
								instance._clearCalendarColor(calendar);
							}

							currentTarget.removeClass(CSS_CALENDAR_LIST_ITEM_HOVER);
						},

						_onHoverOver: function(event) {
							var instance = this;

							var currentTarget = event.currentTarget;

							var calendar = instance.getCalendarByNode(currentTarget);

							currentTarget.addClass(CSS_CALENDAR_LIST_ITEM_HOVER);

							if (!calendar.get('visible')) {
								instance._setCalendarColor(calendar, calendar.get('color'));
							}
						},

						_onSimpleMenuVisibleChange: function(event) {
							var instance = this;

							if (instance.activeNode && !event.newVal) {
								instance.activeNode.removeClass(CSS_CALENDAR_LIST_ITEM_ACTIVE);
							}
						},

						_renderCalendars: function() {
							var instance = this;

							var calendars = instance.get('calendars');
							var contentBox = instance.get('contentBox');

							if (calendars.length === 0) {
								contentBox.setContent(instance.emptyMessageNode);
							}
							else {
								instance.items = A.NodeList.create(
									TPL_CALENDAR_LIST_ITEM.parse(
										{
											calendars: calendars
										}
									)
								);

								contentBox.setContent(instance.items);
							}
						},

						_setCalendarColor: function(calendar, val) {
							var instance = this;

							var node = instance.getCalendarNode(calendar);

							var colorNode = node.one(STR_DOT + CSS_CALENDAR_LIST_ITEM_COLOR);

							colorNode.setStyles(
								{
									backgroundColor: val,
									borderColor: val
								}
							);
						},

						_setCalendars: function(val) {
							var instance = this;

							var scheduler = instance.get('scheduler');

							val.forEach(
								function(item, index) {
									var calendar = item;

									if (!A.instanceOf(item, Liferay.SchedulerCalendar)) {
										calendar = new Liferay.SchedulerCalendar(item);

										val[index] = calendar;
									}

									calendar.addTarget(instance);

									calendar.set('scheduler', scheduler);
								}
							);

							return val;
						},

						_setSimpleMenu: function(val) {
							var instance = this;

							return A.merge(
								{
									align: {
										points: [A.WidgetPositionAlign.TL, A.WidgetPositionAlign.BL]
									},
									bubbleTargets: [instance],
									constrain: true,
									host: instance,
									items: [],
									plugins: [A.Plugin.OverlayAutohide],
									visible: false,
									width: 290,
									zIndex: Liferay.zIndex.MENU
								},
								val || {}
							);
						},

						_uiSetCalendars: function(val) {
							var instance = this;

							if (instance.get('rendered')) {
								instance._renderCalendars();
							}
						}
					}
				}
			);

			Liferay.CalendarList = CalendarList;
		},
		'',
		{
			requires: ['aui-template-deprecated', 'liferay-scheduler']
		}
	);

	AUI.add(
		'liferay-calendar-simple-color-picker',
		function(A) {
			var	AArray = A.Array;
			var Lang = A.Lang;

			var	getClassName = A.getClassName;

			var	CSS_SIMPLE_COLOR_PICKER_ITEM = getClassName('simple-color-picker', 'item');

			var	CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED = getClassName('simple-color-picker', 'item', 'selected');

			var TPL_SIMPLE_COLOR_PICKER_ITEM = new A.Template(
				'<tpl for="pallete">',
					'<div class="', CSS_SIMPLE_COLOR_PICKER_ITEM, '" style="background-color: {.}', '; border-color:', '{.};', '"></div>',
				'</tpl>'
			);

			var SimpleColorPicker = A.Component.create(
				{
					ATTRS: {
						color: {
							setter: function(val) {
								return val.toUpperCase();
							},
							validator: Lang.isString,
							value: STR_BLANK
						},

						host: {
							value: null
						},

						pallete: {
							setter: function(val) {
								return AArray.invoke(val, 'toUpperCase');
							},
							validator: Lang.isArray,
							value: ['#d96666', '#e67399', '#b373b3', '#8c66d9', '#668cb3', '#668cd9', '#59bfb3', '#65ad89', '#4cb052', '#8cbf40', '#bfbf4d', '#e0c240', '#f2a640', '#e6804d', '#be9494', '#a992a9', '#8997a5', '#94a2be', '#85aaa5', '#a7a77d', '#c4a883', '#c7561e', '#b5515d', '#c244ab']
						}
					},

					NAME: 'simple-color-picker',

					UI_ATTRS: ['color', 'pallete'],

					prototype: {
						renderUI: function() {
							var instance = this;

							instance._renderPallete();
						},

						bindUI: function() {
							var instance = this;

							var contentBox = instance.get('contentBox');

							contentBox.delegate('click', instance._onClickColor, STR_DOT + CSS_SIMPLE_COLOR_PICKER_ITEM, instance);
						},

						_onClickColor: function(event) {
							var instance = this;

							var pallete = instance.get('pallete');

							instance.set('color', pallete[instance.items.indexOf(event.currentTarget)]);
						},

						_renderPallete: function() {
							var instance = this;

							instance.items = A.NodeList.create(
								TPL_SIMPLE_COLOR_PICKER_ITEM.parse(
									{
										pallete: instance.get('pallete')
									}
								)
							);

							instance.get('contentBox').setContent(instance.items);
						},

						_uiSetColor: function(val) {
							var instance = this;

							var pallete = instance.get('pallete');

							instance.items.removeClass(CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED);

							var newNode = instance.items.item(pallete.indexOf(val));

							if (newNode) {
								newNode.addClass(CSS_SIMPLE_COLOR_PICKER_ITEM_SELECTED);
							}
						},

						_uiSetPallete: function(val) {
							var instance = this;

							if (instance.get('rendered')) {
								instance._renderPallete();
							}
						}
					}
				}
			);

			Liferay.SimpleColorPicker = SimpleColorPicker;
		},
		'',
		{
			requires: ['aui-base', 'aui-template-deprecated']
		}
	);

	AUI.add(
		'liferay-calendar-reminders',
		function(A) {
			var Lang = A.Lang;

			var TPL_REMINDER_SECTION = '<div class="calendar-portlet-reminder-section form-inline">' +
					'<label class="checkbox">' +
						'<input <tpl if="!disabled">checked="checked"</tpl> class="calendar-portlet-reminder-check" id="{portletNamespace}reminder{i}" name="{portletNamespace}reminder{i}" type="checkbox" />' +
					'</label>' +
					'<label class="reminder-type" for="{portletNamespace}reminder{i}">' +
						'<input id="{portletNamespace}reminderType{i}" name="{portletNamespace}reminderType{i}" type="hidden" value="email" />' +
						'{email}' +
					'</label>' +
					'<input class="input-mini reminder-value" <tpl if="disabled">disabled="disabled"</tpl> name="{portletNamespace}reminderValue{i}" size="5" type="text" value="{time.value}" /> ' +
					'<select class="reminder-duration span2" <tpl if="disabled">disabled="disabled"</tpl> name="{portletNamespace}reminderDuration{i}">' +
						'<option <tpl if="time.desc == \'minutes\'">selected="selected"</tpl> value="60">{minutes}</option>' +
						'<option <tpl if="time.desc == \'hours\'">selected="selected"</tpl> value="3600">{hours}</option>' +
						'<option <tpl if="time.desc == \'days\'">selected="selected"</tpl> value="86400">{days}</option>' +
						'<option <tpl if="time.desc == \'weeks\'">selected="selected"</tpl> value="604800">{weeks}</option>' +
					'</select>' +
				'</div>';

			var Reminders = A.Component.create(
				{
					ATTRS: {
						portletNamespace: {
							value: ''
						},

						strings: {
							value: {
								days: Liferay.Language.get('days'),
								email: Liferay.Language.get('email'),
								hours: Liferay.Language.get('hours'),
								minutes: Liferay.Language.get('minutes'),
								weeks: Liferay.Language.get('weeks')
							}
						},

						values: {
							validator: Lang.isArray,
							value: [
								{
									interval: 10,
									type: Liferay.CalendarUtil.NOTIFICATION_DEFAULT_TYPE
								},
								{
									interval: 60,
									type: Liferay.CalendarUtil.NOTIFICATION_DEFAULT_TYPE
								}
							]
						}
					},

					NAME: 'reminders',

					UI_ATTRS: ['values'],

					prototype: {
						initializer: function() {
							var instance = this;

							instance.tplReminder = new A.Template(TPL_REMINDER_SECTION);
						},

						bindUI: function() {
							var instance = this;

							var boundingBox = instance.get('boundingBox');

							boundingBox.delegate('change', instance._onChangeCheckbox, '.calendar-portlet-reminder-check', instance);
						},

						_onChangeCheckbox: function(event) {
							var instance = this;

							var target = event.target;

							var checked = target.get('checked');
							var elements = target.ancestor().siblings('input[type=text],select');

							elements.set('disabled', !checked);

							if (checked) {
								elements.first().select();
							}
						},

						_uiSetValues: function(val) {
							var instance = this;

							var boundingBox = instance.get('boundingBox');
							var portletNamespace = instance.get('portletNamespace');
							var strings = instance.get('strings');

							var buffer = [];

							var tplReminder = instance.tplReminder;

							for (var i = 0; i < val.length; i++) {
								var value = val[i];

								buffer.push(
									tplReminder.parse(
										A.merge(
											strings,
											{
												disabled: !value.interval,
												i: i,
												portletNamespace: portletNamespace,
												time: Liferay.Time.getDescription(value.interval)
											}
										)
									)
								);
							}

							boundingBox.setContent(buffer.join(STR_BLANK));
						}
					}
				}
			);

			Liferay.Reminders = Reminders;
		},
		'',
		{
			requires: ['aui-base']
		}
	);

	AUI.add(
		'liferay-calendar-date-picker-util',
		function(A) {
			var Lang = A.Lang;

			var toInt = Lang.toInt;

			Liferay.DatePickerUtil = {
				syncUI: function(form, fieldName, date) {
					var instance = this;

					var amPmNode = form.one('select[name$=' + fieldName + 'AmPm]');
					var hourNode = form.one('select[name$=' + fieldName + 'Hour]');
					var minuteNode = form.one('select[name$=' + fieldName + 'Minute]');

					var datePicker = Liferay.component(Liferay.CalendarUtil.PORTLET_NAMESPACE + fieldName + 'datePicker');

					if (datePicker) {
						datePicker.calendar.deselectDates();
						datePicker.calendar.selectDates(date);

						datePicker.syncUI();
					}

					var hours = date.getHours();
					var minutes = date.getMinutes();

					var amPm = hours < 12 ? 0 : 1;

					if (amPm === 1) {
						hours -= 12;

						if (hours === 12) {
							hours = 0;
						}
					}

					amPmNode.val(amPm);
					hourNode.val(hours);
					minuteNode.val(minutes);
				},

				linkToSchedulerEvent: function(datePickerContainer, schedulerEvent, dateAttr) {
					var instance = this;

					var selects = A.one(datePickerContainer).all('select');

					selects.on(
						'change',
						function(event) {
							var currentTarget = event.currentTarget;

							var date = schedulerEvent.get(dateAttr);

							var selectedSetter = selects.indexOf(currentTarget);

							var setters = [date.setMonth, date.setDate, date.setFullYear, date.setHours, date.setMinutes, date.setHours];

							var value = toInt(currentTarget.val());

							if (selectedSetter === 3 && date.getHours() > 12) {
								value += 12;
							}

							if (selectedSetter === 5) {
								value = date.getHours() + (value === 1 ? 12 : -12);
							}

							setters[selectedSetter].call(date, value);

							schedulerEvent.get('scheduler').syncEventsUI();
						}
					);
				}
			};
		},
		'',
		{
			requires: ['aui-base']
		}
	);

	AUI.add(
		'liferay-calendar-recurrence-util',
		function(A) {
			Liferay.RecurrenceUtil = {
				FREQUENCY: {
					DAILY: 'DAILY',
					MONTHLY: 'MONTHLY',
					WEEKLY: 'WEEKLY',
					YEARLY: 'YEARLY'
				},

				INTERVAL_UNITS: {},

				MONTH_LABELS: [],

				POSITION_LABELS: {},

				RECURRENCE_SUMMARIES: {},

				WEEKDAY_LABELS: {},

				getSummary: function(recurrence) {
					var instance = this;

					var key;
					var params = [];
					var parts = [];

					if (recurrence.interval == 1) {
						parts.push(A.Lang.String.toLowerCase(recurrence.frequency));
					}
					else {
						parts.push('every-x-' + instance.INTERVAL_UNITS[recurrence.frequency]);

						params.push(recurrence.interval);
					}

					if (recurrence.positionalWeekday) {
						if (recurrence.frequency == instance.FREQUENCY.MONTHLY) {
							parts.push('on-x-x');

							params.push(instance.POSITION_LABELS[recurrence.positionalWeekday.position]);
							params.push(instance.WEEKDAY_LABELS[recurrence.positionalWeekday.weekday]);
						}
						else {
							parts.push('on-x-x-of-x');

							params.push(instance.POSITION_LABELS[recurrence.positionalWeekday.position]);
							params.push(instance.WEEKDAY_LABELS[recurrence.positionalWeekday.weekday]);
							params.push(instance.MONTH_LABELS[recurrence.positionalWeekday.month]);
						}
					}
					else if (recurrence.frequency == instance.FREQUENCY.WEEKLY && recurrence.weekdays.length > 0) {
						parts.push('on-x');

						params.push(recurrence.weekdays.join(', '));
					}

					if (recurrence.count && recurrence.endValue === 'after') {
						parts.push('x-times');

						params.push(recurrence.count);
					}
					else if (recurrence.untilDate && recurrence.endValue === 'on') {
						parts.push('until-x-x-x');

						var untilDate = recurrence.untilDate;

						params.push(instance.MONTH_LABELS[untilDate.getMonth()]);
						params.push(untilDate.getDate());
						params.push(untilDate.getFullYear());
					}

					key = parts.join(STR_DASH);

					return A.Lang.sub(instance.RECURRENCE_SUMMARIES[key], params);
				},

				openConfirmationPanel: function(actionName, onlyThisInstanceFn, allFollowingFn, allEventsInFn, cancelFn) {
					var instance = this;

					var changeDeleteText;
					var confirmationPanel;
					var titleText;

					if (actionName === 'delete') {
						titleText = Liferay.Language.get('delete-recurring-event');
						changeDeleteText = Liferay.Language.get('would-you-like-to-delete-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series');
					}
					else {
						titleText = Liferay.Language.get('change-recurring-event');
						changeDeleteText = Liferay.Language.get('would-you-like-to-change-only-this-event-all-events-in-the-series-or-this-and-all-future-events-in-the-series');
					}

					var getButtonConfig = function(label, callback) {
						return {
							label: label,
							on: {
								click: function() {
									if (callback) {
										callback.apply(this, arguments);
									}

									confirmationPanel.hide();
								}
							}
						};
					};

					confirmationPanel = Liferay.Util.Window.getWindow(
						{
							dialog:	{
								bodyContent: changeDeleteText,
								destroyOnHide: true,
								height: 400,
								hideOn: [],
								resizable: false,
								toolbars: {
									footer: [
										getButtonConfig(Liferay.Language.get('only-this-instance'), onlyThisInstanceFn),
										getButtonConfig(Liferay.Language.get('all-following'), allFollowingFn),
										getButtonConfig(Liferay.Language.get('all-events-in-the-series'), allEventsInFn),
										getButtonConfig(Liferay.Language.get('cancel-this-change'), cancelFn)
									]
								},
								width: 700
							},
							title: titleText
						}
					);

					return confirmationPanel.render().show();
				}
			};
		},
		'',
		{
			requires: ['aui-base', 'liferay-util-window']
		}
	);

	AUI.add(
		'liferay-calendar-message-util',
		function(A) {
			var Lang = A.Lang;
			var LString = Lang.String;

			var TPL_MESSAGE_UPDATE_ALL_INVITED = '<p class="calendar-portlet-confirmation-text">' +
					Liferay.Language.get('invited-users-will-be-notified') +
				'</p>';

			Liferay.CalendarMessageUtil = {

				confirm: function(message, yesButtonLabel, noButtonLabel, yesFn, noFn) {
					var instance = this;

					var confirmationPanel;

					var getButtonConfig = function(label, callback) {
						return {
							label: label,
							on: {
								click: function() {
									if (callback) {
										callback.apply(this, arguments);
									}

									confirmationPanel.hide();
								}
							}
						};
					};

					confirmationPanel = Liferay.Util.Window.getWindow(
						{
							dialog: {
								bodyContent: message,
								height: 250,
								hideOn: [],
								resizable: false,
								toolbars: {
									footer: [
										getButtonConfig(yesButtonLabel, yesFn),
										getButtonConfig(noButtonLabel, noFn)
									]
								},
								width: 700
							},
							title: Liferay.Language.get('are-you-sure')
						}
					);

					return confirmationPanel.render().show();
				},

				promptSchedulerEventUpdate: function(data) {
					var instance = this;

					data.answers = {};

					var queue = new A.AsyncQueue();

					if (data.recurring) {
						queue.add(
							{
								args: [data],
								autoContinue: false,
								context: instance,
								fn: instance._queueableQuestionUpdateRecurring,
								timeout: 0
							}
						);
					}

					if (data.masterBooking) {
						if (data.hasChild) {
							queue.add(
								{
									args: [data],
									autoContinue: false,
									context: instance,
									fn: instance._queueableQuestionUpdateAllInvited,
									timeout: 0
								}
							);
						}
					}
					else {
						queue.add(
							{
								args: [data],
								autoContinue: false,
								context: instance,
								fn: instance._queueableQuestionUserCalendarOnly,
								timeout: 0
							}
						);
					}

					queue.add(
						{
							args: [data],
							autoContinue: false,
							context: instance,
							fn: data.resolver,
							timeout: 0
						}
					);

					instance.queue = queue;

					queue.run();
				},

				showAlert: function(container, message) {
					new A.Alert(
						{
							animated: true,
							bodyContent: message,
							closeable: true,
							cssClass: 'alert-success',
							destroyOnHide: true,
							duration: 1
						}
					).render(container);
				},

				_queueableQuestionUpdateAllInvited: function(data) {
					var instance = this;

					var answers = data.answers;

					var showNextQuestion = A.bind('run', instance.queue);

					if (answers.cancel) {
						A.soon(showNextQuestion);
					}
					else {
						Liferay.CalendarMessageUtil.confirm(
							TPL_MESSAGE_UPDATE_ALL_INVITED,
							Liferay.Language.get('save-changes'),
							Liferay.Language.get('do-not-change-the-event'),
							showNextQuestion,
							function() {
								answers.cancel = true;

								showNextQuestion();
							}
						);
					}
				},

				_queueableQuestionUpdateRecurring: function(data) {
					var instance = this;

					var answers = data.answers;

					var showNextQuestion = A.bind('run', instance.queue);

					if (answers.cancel) {
						A.soon(showNextQuestion);
					}
					else {
						Liferay.RecurrenceUtil.openConfirmationPanel(
							'update',
							function() {
								answers.updateInstance = true;

								showNextQuestion();
							},
							function() {
								answers.allFollowing = true;
								answers.updateInstance = true;

								showNextQuestion();
							},
							showNextQuestion,
							function() {
								answers.cancel = true;

								showNextQuestion();
							}
						);
					}
				},

				_queueableQuestionUserCalendarOnly: function(data) {
					var instance = this;

					var answers = data.answers;

					var showNextQuestion = A.bind('run', instance.queue);

					if (answers.cancel) {
						A.soon(showNextQuestion);
					}
					else {
						var content = [
							'<p class="calendar-portlet-confirmation-text">',
							Lang.sub(
								Liferay.Language.get('you-are-about-to-make-changes-that-will-only-affect-your-calendar-x'),
								[LString.escapeHTML(data.calendarName)]
							),
							'</p>'
						].join(STR_BLANK);

						Liferay.CalendarMessageUtil.confirm(
							content,
							Liferay.Language.get('save-changes'),
							Liferay.Language.get('do-not-change-the-event'),
							showNextQuestion,
							function() {
								answers.cancel = true;

								showNextQuestion();
							}
						);
					}
				}
			};
		},
		'',
		{
			requires: ['aui-alert', 'liferay-util-window']
		}
	);

	AUI.add(
			'liferay-calendar-interval-selector',
			function(A) {
				var AArray = A.Array;

				var EVENT_SELECTION_CHANGE = 'selectionChange';

				var IntervalSelector = A.Component.create(
					{
						AUGMENTS: [Liferay.PortletBase],

						EXTENDS: A.Base,

						NAME: 'interval-selector',

						prototype: {
							initializer: function(config) {
								var instance = this;

								instance.eventHandlers = [];

								instance._containerNode = instance.byId(config.containerId);
								instance._submitButtonNode = instance.byId(config.submitButtonId);

								instance._duration = 0;
								instance._endDate = new Date();
								instance._startDate = new Date();
								instance._validDate = true;

								instance._endDatePicker = instance._getComponent(config.endDatePickerName + 'DatePicker');
								instance._endTimePicker = instance._getComponent(config.endTimePickerName + 'TimePicker');
								instance._startDatePicker = instance._getComponent(config.startDatePickerName + 'DatePicker');
								instance._startTimePicker = instance._getComponent(config.startTimePickerName + 'TimePicker');

								instance._initPicker(instance._endDatePicker);
								instance._initPicker(instance._endTimePicker);
								instance._initPicker(instance._startDatePicker);
								instance._initPicker(instance._startTimePicker);

								instance._setEndDate();
								instance._setEndTime();
								instance._setStartDate();
								instance._setStartTime();
								instance._setDuration();

								instance.bindUI();
							},

							bindUI: function() {
								var instance = this;

								instance.eventHandlers.push(
									instance._endDatePicker.on(EVENT_SELECTION_CHANGE, instance._onEndDatePickerSelectionChange, instance),
									instance._endTimePicker.on(EVENT_SELECTION_CHANGE, instance._onEndTimePickerSelectionChange, instance),
									instance._startDatePicker.on(EVENT_SELECTION_CHANGE, instance._onStartDatePickerSelectionChange, instance),
									instance._startTimePicker.on(EVENT_SELECTION_CHANGE, instance._onStartTimePickerSelectionChange, instance)
								);
							},

							destructor: function() {
								var instance = this;

								AArray.invoke(instance.eventHandlers, 'detach');

								instance.eventHandlers = null;
							},

							_getComponent: function(name) {
								var instance = this;

								return Liferay.component(instance.NS + name);
							},

							_initPicker: function(picker) {
								var instance = this;

								var attrs = picker.getAttrs();

								var inputNode = A.one(attrs.container._node.children[0]);

								picker.useInputNodeOnce(inputNode);
							},

							_onEndDatePickerSelectionChange: function() {
								var instance = this;

								instance._setEndDate();

								var endDateValue = instance._endDate.valueOf();

								if (instance._validDate && (instance._startDate.valueOf() >= endDateValue)) {
									instance._startDate = new Date(endDateValue - instance._duration);

									instance._setStartDatePickerDate();
								}

								instance._setDuration();
								instance._validate();
							},

							_onEndTimePickerSelectionChange: function() {
								var instance = this;

								instance._setEndTime();

								var endDateValue = instance._endDate.valueOf();

								if (instance._validDate && (instance._startDate.valueOf() >= endDateValue)) {
									instance._startDate = new Date(endDateValue - instance._duration);

									instance._setStartDatePickerDate();
									instance._setStartTimePickerTime();
								}

								instance._setDuration();
								instance._validate();
							},

							_onStartDatePickerSelectionChange: function() {
								var instance = this;

								instance._setStartDate();

								if (instance._validDate) {
									instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

									instance._setEndDatePickerDate();
								}

								instance._setDuration();
								instance._validate();
							},

							_onStartTimePickerSelectionChange: function() {
								var instance = this;

								instance._setStartTime();

								if (instance._validDate) {
									instance._endDate = new Date(instance._startDate.valueOf() + instance._duration);

									instance._setEndDatePickerDate();
									instance._setEndTimePickerTime();
								}

								instance._setDuration();
								instance._validate();
							},

							_setDuration: function() {
								var instance = this;

								instance._duration = (instance._endDate.valueOf() - instance._startDate.valueOf());
							},

							_setEndDate: function() {
								var instance = this;

								var endDateObj = instance._endDatePicker.getDate();

								var endDate = instance._endDate;

								endDate.setDate(endDateObj.getDate());
								endDate.setMonth(endDateObj.getMonth());
								endDate.setYear(endDateObj.getFullYear());
							},

							_setEndDatePickerDate: function() {
								var instance = this;

								instance._endDatePicker.clearSelection(true);

								instance._endDatePicker.selectDates([instance._endDate]);
							},

							_setEndTime: function() {
								var instance = this;

								var endTime = instance._endTimePicker.getTime();

								instance._endDate.setHours(endTime.getHours());
								instance._endDate.setMinutes(endTime.getMinutes());
							},

							_setEndTimePickerTime: function() {
								var instance = this;

								instance._endTimePicker.selectDates([instance._endDate]);
							},

							_setStartDate: function() {
								var instance = this;

								var startDateObj = instance._startDatePicker.getDate();

								var startDate = instance._startDate;

								startDate.setDate(startDateObj.getDate());
								startDate.setMonth(startDateObj.getMonth());
								startDate.setYear(startDateObj.getFullYear());
							},

							_setStartDatePickerDate: function() {
								var instance = this;

								var startDatePicker = instance._startDatePicker;

								startDatePicker.clearSelection(true);

								startDatePicker.selectDates([instance._startDate]);
							},

							_setStartTime: function() {
								var instance = this;

								var startTime = instance._startTimePicker.getTime();

								var startDate = instance._startDate;

								startDate.setHours(startTime.getHours());
								startDate.setMinutes(startTime.getMinutes());
							},

							_setStartTimePickerTime: function() {
								var instance = this;

								instance._startTimePicker.selectDates([instance._startDate]);
							},

							_validate: function() {
								var instance = this;

								var validDate = (instance._duration > 0);

								instance._validDate = validDate;

								var meetingEventDate = instance._containerNode;

								if (meetingEventDate) {
									meetingEventDate.toggleClass('error', !validDate);

									var helpInline = meetingEventDate.one('.help-inline');

									if (validDate && helpInline) {
										helpInline.remove();
									}

									if (!validDate && !helpInline) {
										var inlineHelp = A.Node.create('<div class="help-inline">' + Liferay.Language.get('the-end-time-must-be-after-the-start-time') + '</div>');

										meetingEventDate.insert(inlineHelp);
									}

									var submitButton = instance._submitButtonNode;

									if (submitButton) {
										submitButton.attr('disabled', !validDate);
									}
								}
							}
						}
					}
				);

				Liferay.IntervalSelector = IntervalSelector;
			},
			'',
			{
				requires: ['aui-base', 'liferay-portlet-base']
			}
		);
}());