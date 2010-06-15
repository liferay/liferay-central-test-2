AUI().add(
	'liferay-async-queue',
	function(A) {
		var Lang = A.Lang;

		var AsyncQueue = function() {
			var instance = this;

			instance._delayedRunTask = A.bind(instance._delayedRun, instance);

			AsyncQueue.superclass.constructor.apply(this, arguments);
		};

		A.extend(
			AsyncQueue,
			A.AsyncQueue,
			{
				addWithCallback: function(obj) {
					var instance = this;

					if (Lang.isFunction(obj)) {
						instance.add(obj);
					}
					else {
						var fn = obj.fn;
						var args = obj.args;
						var callback = args[args.length - 1];

						if (instance._isProvided(fn) && Lang.isFunction(callback)) {
							obj._callback = callback;

							A.Do.before(instance.pause, obj, 'fn', instance);

							A.Do.after(instance._delayedRunTask, obj, '_callback', instance);

							args.pop();
							args.push(obj._callback);
						}

						instance.add(obj);
					}
				},

				_delayedRun: function() {
					var instance = this;

					setTimeout(
						function() {
							instance.run();
						},
						0
					);
				},

				_isProvided: function(fn) {
					var instance = this;

					return fn && fn._guid && fn._guid.indexOf('lfr-provide') === 0;
				}
			}
		);

		Liferay.AsyncQueue = AsyncQueue;
	},
	'',
	{
		requires: ['async-queue']
	}
);