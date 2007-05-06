$ = null;
var _$J = jQuery;

Function.prototype.extendNativeFunctionObject = jQuery.extend;

jQuery.getOne = function(s, context) {
	var rt;

	if (typeof s == 'object') {
		rt = s;
	}
	else if (typeof s == 'string') {
		if (s.search(/^[#.]/) == -1) {
			s = '#' + s;
		}

		if (context == null) {
			rt = jQuery(s);
		}
		else {
			rt = jQuery(s, context);
		}

		if (rt.length > 0) {
			rt = rt.get(0);
		}
		else {
			rt = null;
		}
	}

	return rt;
};

jQuery.fn.getOne = function(s) {
	return jQuery.getOne(s, this);
};

Liferay = {};

/* jQuery's implementation of height() & width() is poor on performace
 * in IE. Override with basic JS until issue is resolved
 */
if (jQuery.browser.msie) {
	jQuery.each( [ "height", "width" ], function(i,n){
		jQuery.fn[ n ] = function(h) {
			return h == undefined ?
				( this.length ? (n == "height" ? this[0].offsetHeight : this[0].offsetWidth) : null ) :
				this.css( n, h.constructor == String ? h : h + "px" );
		};
	});
}