AUI.add(
	'liferay-calendar-recurrence-converter',
	function(A) {
		var Lang = A.Lang;

		var EXDATE = 'EXDATE';

		var RRULE = 'RRULE';

		var STR_COLON = ':';

		var STR_COMMA = ',';

		var STR_EMPTY = '';

		var STR_EQUALS = '=';

		var STR_SEMICOLON = ';';

		var STR_ZERO = '0';

		var WEEKLY = 'WEEKLY';

		var RecurrenceConverter = function() {};

		A.mix(
			RecurrenceConverter.prototype,
			{
				encodeRecurrence: function(recurrence) {
					var instance = this;

					var string = null;

					if (recurrence) {
						var components = [];

						components.push(instance._encodeRRule(recurrence.rrule));

						if (recurrence.exdate) {
							components.push(instance._encodeExDate(recurrence.exdate));
						}

						string = components.join('\n');
					}

					return string;
				},

				parseRecurrence: function(string) {
					var instance = this;

					var recurrence = null;

					if (string) {
						var parts = string.split('\n');

						var rrule = instance._parseRRule(parts[0]);

						if (rrule) {
							recurrence = {
								rrule: rrule
							};

							var exdate = instance._parseExDate(parts[1]);

							if (exdate) {
								recurrence.exdate = exdate;
							}
						}
					}

					return recurrence;
				},

				_encode: function(value) {
					var instance = this;

					var result = value;

					if (value instanceof Date) {
						result = instance._encodeDate(value);
					}
					else if (value instanceof Array) {
						result = instance._encodeDaysOfWeek(value);
					}
					else if (value.position && value.dayOfWeek) {
						result = value.position + value.dayOfWeek;
					}

					return result;
				},

				_encodeDate: function(date) {
					var instance = this;

					var month = instance._twoDigits(date.getMonth() + 1);

					var day = instance._twoDigits(date.getDate());

					return [date.getFullYear(), month, day].join(STR_EMPTY);
				},

				_encodeDaysOfWeek: function(daysOfWeek) {
					daysOfWeek = A.Array.dedupe(daysOfWeek);

					return daysOfWeek.join(STR_COMMA);
				},

				_encodeExDate: function(exdate) {
					return EXDATE + STR_SEMICOLON + exdate;
				},

				_encodeRRule: function(rrule) {
					var instance = this;

					var string = '';

					var components = [];

					for (var key in rrule) {
						var value = rrule[key];

						components.push(key.toUpperCase() + STR_EQUALS + instance._encode(value));
					}

					if (components.length > 0) {
						string = RRULE + STR_COLON + components.join(STR_SEMICOLON);
					}

					return string;
				},

				_parseDate: function(string) {
					var year = Lang.toInt(string.slice(0, 4));

					var month = Lang.toInt(string.slice(4, 6)) - 1;

					var day = Lang.toInt(string.slice(6, 8));

					return new Date(year, month, day);
				},

				_parseExDate: function(string) {
					var instance = this;

					var exdate = null;

					if (string && string.startsWith(EXDATE + STR_SEMICOLON)) {
						exdate = string.slice(7);
					}

					return exdate;
				},

				_parsePositionalByDay: function(string) {
					var position = string.split(0, -2);

					var dayOfWeek = string.split(-2);

					return {
						dayOfWeek: dayOfWeek,
						position: Lang.toInt(position)
					};
				},

				_parseRRule: function(string) {
					var instance = this;

					var rrule = null;

					if (string && string.startsWith(RRULE + STR_COLON)) {
						string = string.slice(6);

						var params = string.split(STR_SEMICOLON);

						rrule = {};

						for (var i in params) {
							var pair = params[i].split(STR_EQUALS);

							rrule[pair[0].toLowerCase()] = pair[1];
						}

						if (rrule.interval) {
							rrule.interval = Lang.toInt(rrule.interval);
						}

						if (rrule.count) {
							rrule.count = Lang.toInt(rrule.count);
						}

						if (rrule.until) {
							rrule.until = instance._parseDate(rrule.until);
						}

						if (rrule.freq === WEEKLY) {
							rrule.byday = rrule.byday.split(STR_COMMA);
						}
						else if (rrule.byday) {
							rrule.byday = instance._parsePositionalByDay(rrule.byday);

							if (rrule.month) {
								rrule.bymonth = Lang.toInt(rrule.bymonth);
							}
						}
					}

					return rrule;
				},


				_twoDigits: function(number) {
					var paddedNumber = STR_ZERO + number;

					return paddedNumber.slice(-2);
				}
			}
		);

		Liferay.RecurrenceConverter = RecurrenceConverter;
	},
	''
);