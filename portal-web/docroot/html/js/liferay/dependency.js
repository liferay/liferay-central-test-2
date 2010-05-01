(function(){
	var A = AUI();

	var usedModules = {};
	var emptyFn = function(){};

	var Dependency = {
		provide: function(obj, methodName, methodFn, modules) {
			if (!A.Lang.isArray(modules)) {
				modules = [modules];
			}

			var guid = A.guid();
			var before;
			var once;

			if (A.Lang.isObject(methodFn, true)) {
				var config = methodFn;

				methodFn = config.fn;
				before = config.before;
				once = config.once;

				if (!A.Lang.isFunction(before)) {
					before = null;
				}
			}

			var AOP = Dependency._getAOP(obj, methodName);

			if (AOP) {
				delete A.Do.objs[obj._yuid][methodName];
			}

			var proxy = function() {
				var args = arguments;

				if (modules.length == 1) {
					if (modules[0] in usedModules) {
						Dependency._replaceMethod(obj, methodName, methodFn, once);

						methodFn.apply(obj, args);

						return;
					}
				}

				var queue = Dependency._proxyLoaders[guid];
				var firstLoad = false;

				if (!queue) {
					firstLoad = true;
					Dependency._proxyLoaders[guid] = new A.Queue();

					queue = Dependency._proxyLoaders[guid];
				}

				queue.add(args);

				if (firstLoad) {
					modules.push(A.bind(Dependency._proxy, Liferay, obj, methodName, methodFn, once, guid));

					A.use.apply(A, modules);

					for (var i = modules.length - 1; i >= 0; i--) {
						usedModules[modules[i]] = true;
					}
				}
			};

			proxy.toString = function() {
				return methodFn.toString();
			};

			obj[methodName] = proxy;
		},

		_getAOP: function(obj, methodName) {
			var instance = this;

			return obj._yuid && A.Do.objs[obj._yuid] && A.Do.objs[obj._yuid][methodName];
		},

		_proxy: function(obj, methodName, methodFn, once, guid, A) {
			var queue = Dependency._proxyLoaders[guid];
			var args;

			Dependency._replaceMethod(obj, methodName, methodFn, once);

			while ((args = queue.next())) {
		        methodFn.apply(obj, args);
		    }
		},

		_replaceMethod: function(obj, methodName, methodFn, once) {
			var instance = this;

			if (!once) {
				var AOP = Dependency._getAOP(obj, methodName);

				if (AOP) {
					AOP.method = methodFn;
				}
				else {
					obj[methodName] = methodFn;
				}
			}
			else {
				obj[methodName] = emptyFn;
			}
		},

		_proxyLoaders: {}
	};

	Liferay.Dependency = Dependency;

	Liferay.provide = Dependency.provide;
})();