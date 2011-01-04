AUI.add(
	'liferay-shutdown',
	function(A) {
		var Shutdown = {
			pollingTime: 0,

			init: function(pollingTime) {
				var instance = this;
				
				instance.pollingTime = pollingTime;

				var urlBase = themeDisplay.getPathMain() + '/portal/';

				instance._shutdownURL = urlBase + 'check_shutdown';
				instance._shutdownText = Liferay.Language.get('the-portal-will-shutdown-for-maintenance-in-x-minutes');
				instance._isShutdown = false;

				if (instance.pollingTime > 0) {
					instance._shutdownTimer = setInterval(function() {
						A.io.request(
							instance._shutdownURL,
							{
								type: 'POST',
								dataType: 'json',
								on: {
									success: A.bind( function(event) {
										var xhr = event.currentTarget;
										var json = xhr.get('responseData');

										if (!instance._isShutdown && json.process > 0) {
											instance._currentTime = json.process;

											var shutdownText = A.substitute(instance._shutdownText, ['<span class="countdown-timer"></span>', json.process]);

											if (json.message != '') {
												shutdownText = shutdownText + '<span class="custom-shutdown-message">' + json.message + '</span>';
											}

											instance._warningShutdown(shutdownText);
											instance._isShutdown = true;
										}
									})
								}
							}
						);
					}, instance.pollingTime);
				}
			},

			_warningShutdown: function(shutdownText) {
				var instance = this;

				instance.banner = new Liferay.Notice(
					{
						content: shutdownText,
						toggleText: false,
						onClose: function() {
							if (instance._countdownTimer) {
								clearInterval(instance._countdownTimer);
							}
						}
					}
				);

				instance._shutdownCounter();
			},

			_shutdownCounter: function(shutdownTime) {
				var instance = this;

				var banner = instance.banner;

				if (banner) {
					instance._counterText = banner.one('.countdown-timer');
					instance._originalTitle = document.title;

					var interval = 1000;

					instance._counterText.text(instance._setTime());
					document.title = instance.banner.text();

					instance._countdownTimer = setInterval(
						function() {
							var time = instance._setTime();

							instance._currentTime = instance._currentTime - interval;

							if (instance._currentTime > 0) {
								instance._counterText.text(time);
								document.title = instance.banner.text();
							}
							else {
								if (instance._countdownTimer) {
									clearInterval(instance._countdownTimer);
								}
							}
						},
						interval
					);
				}
			},

			_formatNumber: function(num) {
				var instance = this;

				if (!Liferay.Util.isArray(num)) {
					if (num <= 9) {
						num = '0' + num;
					}
				}
				else {
					num = A.Array.map(num, instance._formatNumber);
				}
				return num;
			},

			_setTime: function() {
				var instance = this;

				var amount = instance._currentTime;

				if (amount <= 0) {

				}
				else {
					var days=0, hours=0, minutes=0, seconds=0, output='';

					// Remove the milliseconds
					amount = Math.floor(amount/1000);

					hours = Math.floor(amount/3600);
					amount = amount%3600;

					minutes = Math.floor(amount/60);
					amount = amount%60;

					seconds = Math.floor(amount);

					return instance._formatNumber([hours, minutes, seconds]).join(':');
				}
			},

			_banner: [],
			_currentTime: 0,
			_originalTitle: '',
			_shutdownURL: '',
			_timeout: 0,
			_timeoutDiff: 0,
			_warning: 0
		};

		Liferay.Shutdown = Shutdown;
	},
	'',
	{
		requires: ['aui-io', 'collection', 'liferay-notice', 'substitute']
	}
);