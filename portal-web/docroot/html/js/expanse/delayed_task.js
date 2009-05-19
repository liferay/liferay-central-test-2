/**
* DelayedTask is a class that will execute a function after a specified amount of time, or cancel itself.
* It's useful for buffering tasks that need to only be executed after some condition has been met.
* 
* Example:
* <br /><a href="delayed_task.html">Using the Expanse.DelayedTask class</a>.
* 
* @namespace Expanse
* @class DelayedTask
* @constructor
* @param {Function} fn The function to run
* @param {Object} scope The scope to execute the function in
* @param {Array} args An array of arguments that will be passed to the function
*/

Expanse.DelayedTask = new Expanse.Class(
		{
			initialize: function(fn, scope, args) {
				var instance = this;

				instance._id = null;
				instance._delay = 0;
				instance._time = 0;
				instance._fn;
				instance._scope = scope;
				instance._args = args;

				instance._base = function() {
					var now = instance._getTime();

			        if (now - instance._time >= instance._delay) {
			            clearInterval(instance._id);

			            instance._id = null;

			            fn.apply(scope, args || []);
			        }
				};
			},

			/**
			* Cancels any pending executions and queues up a new one to run after the specified delay.
			* 
			* @method
			* @param {Number} delay The amount of time in milliseconds to delay the execution of the function
			* @param {Function} newFn Overrides the function originally passed in during instantiation
			* @param {Object} newScope Overrides the scope originally passed in during instantiation
			* @param {Array} newArgs Overrides the array of arguments originally passed in during instantiation
			*/

			delay: function(delay, newFn, newScope, newArgs) {
				var instance = this;

				if (instance._id && instance._delay != delay) {
					instance.cancel();
				}

				instance._delay = delay;
				instance._time = instance._getTime();

				instance._fn = newFn || instance._fn;
				instance._scope = newScope || instance._scope;
				instance._args = newArgs || instance._args;

				if (!instance._id) {
					instance._id = setInterval(instance._base, instance._delay);
				}
			},

			/**
			* Cancels the last queued execution.
			* 
			* @method
			*/

			cancel: function() {
				var instance = this;

				if (instance._id) {
					clearInterval(instance._id);
					instance._id = null;
				}
			},

			_getTime: function() {
				var instance = this;

				return (+new Date());
			}
		}
	);