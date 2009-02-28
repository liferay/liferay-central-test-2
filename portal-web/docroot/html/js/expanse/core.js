window.Expanse = {
	version: '0.1',

	emptyFunction: function() {},

	extend: jQuery.extend,

	get: function(el) {
		var obj = el;

		if (!el.jquery || !el.length) {
			obj = jQuery.apply(jQuery, arguments);
		}

		return obj;
	},

	getEl: function(el) {
		var element = null;

		if(el) {
			if (typeof el == 'string') {
				el = Expanse.prefix(el, "#");
				el = Expanse.get(el)[0];
			}

			if (el.jquery && el.length) {
				el = el[0];
			}

			if (el.tagName) {
				element = el;
			}
		}

		return element;
	},

	getDocument: function() {
		var instance = this;

		if (!instance._document) {
			instance._document = Expanse.get(document);
		}

		return instance._document;
	},

	getBody: function() {
		var instance = this;

		if (!instance._body) {
			instance._body = Expanse.get(document.body || document.documentElement);
		}

		return instance._body;
	},

	getWindow: function() {
		var instance = this;

		if (!instance._window) {
			instance._window = Expanse.get(window);
		}

		return instance._window;
	},

	namespace: function() {
		var args = arguments;
		var baseObject = Expanse;

		var i, j, objPieces;

		for (i=0; i < args.length; i++) {
			objPieces = args[i].split('.');

			j = 1;

			var baseProp = objPieces[0];

			if (!window[baseProp]) {
				window[baseProp] = {};
			}

			baseObject = window[baseProp];

			for (j; j < objPieces.length; j++) {
				baseObject[objPieces[j]] = baseObject[objPieces[j]] || {};

				baseObject = baseObject[objPieces[j]]
			}
		}

		return baseObject;
	},

	prefix: function() {
		var instance = this;

		if (typeof str != "string") {
			str = String(str);
		}

		prefix = prefix || "";

		if (str.indexOf(prefix) !== 0) {
			str = prefix + str;
		}

		return str;
	}
};

Expanse.__generateId = YAHOO.util.Dom.generateId;

YAHOO.util.Dom.generateId = function(el, prefix) {
	return Expanse.__generateId(el, prefix || 'exp-gen-');
};

Expanse.generateId = YAHOO.util.Dom.generateId;

Expanse.Dom = YAHOO.util.Dom;
Expanse.Event = YAHOO.util.Event;

Expanse.Event.un = Expanse.Event.removeListener;

Expanse.onReady = function(fn, obj, scope) {
	if (obj || scope) {
		Expanse.Event.onDOMReady(fn, obj, scope);
	}
	else {
		jQuery(fn);
	}
};