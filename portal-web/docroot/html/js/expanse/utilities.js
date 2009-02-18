Expanse.extend(Expanse,
	{
		destroy: function() {
			var args = arguments;
			var length = args.length;

			for(var i = 0; i < len; i++) {
				var arg = args[i];

				if(arg) {
					if (arg.jquery) {
						arg.unbind();
						arg.remove();
					}
					else if (arg.destroy && Expanse.lang.isFunction(arg)) {
						arg.destroy();
					}
				}
			}
		},

		urlEncode: function(obj) {
			var buffer = [];

			var add = function(key, value) {
				buffer.push(encodeURIComponent(key) + '=' + encodeURIComponent(value));
			};

			if (Expanse.lang.isArray(obj) || obj.expanse) {
				var length = obj.length;

				for (var i = 0; i < length; i++) {
					var item = obj[i];

					add(item.name, item.value);
				}
			}
			else {
				for (var i in obj) {
					var item = obj[i];

					if (Expanse.lang.isArray(item)) {
						var length = item.length;

						for (var j = 0; j < length; j++) {
							var subItem = item[j];

							add(j, subItem);
						}
					}
					else {
						var val = obj[j];

						if (Expanse.lang.isFunction(val)) {
							val = val();
						}

						add(j, val);
					}
				}
			}

			buffer = buffer.join('&');
			buffer = buffer.replace(/%20/g, '+');

			return buffer;
		},

		urlDecode: function(str, overwrite) {
			var instance = this;

			if (str) {
				var obj = {};
				var pieces = str.split('&');

				var piece = '';
				var key = '';
				var value = '';
				var length = pieces.length;

				for (var i=0; i < length; i++) {
					piece = pieces[i].split('=');
					key = decodeURIComponent(piece[0]);
					value = decodeURIComponent(piece[1]);

					var prop = obj[key];

					if (overwrite !== true) {
						if (typeof prop == 'undefined') {
							prop = value;
						}
						else if (typeof prop == 'string') {
							prop = [prop];
							prop.push(value);
						}
						else {
							prop.push(value);
						}
					}
					else {
						prop = value;
					}

					obj[key] = prop;
				}

				return obj;
			}
		}
	}
);