(function(){
/*
 * jQuery 1.1.4 - New Wave Javascript
 *
 * Copyright (c) 2007 John Resig (jquery.com)
 * Dual licensed under the MIT (MIT-LICENSE.txt)
 * and GPL (GPL-LICENSE.txt) licenses.
 *
 * $Date: 2007-08-23 21:49:27 -0400 (Thu, 23 Aug 2007) $
 * $Rev: 2862 $
 */
// Map over jQuery in case of overwrite
if ( typeof jQuery != "undefined" )
	var _jQuery = jQuery;

var jQuery = window.jQuery = function(a,c) {
	// If the context is global, return a new object
	if ( window == this || !this.init )
		return new jQuery(a,c);
	
	return this.init(a,c);
};

// Map over the $ in case of overwrite
if ( typeof $ != "undefined" )
	var _$ = $;
	
// Map the jQuery namespace to the '$' one
window.$ = jQuery;

var quickExpr = /^[^<]*(<(.|\s)+>)[^>]*$|^#(\w+)$/;

jQuery.fn = jQuery.prototype = {
	init: function(a,c) {
		// Make sure that a selection was provided
		a = a || document;

		// Handle HTML strings
		if ( typeof a  == "string" ) {
			var m = quickExpr.exec(a);
			if ( m && (m[1] || !c) ) {
				// HANDLE: $(html) -> $(array)
				if ( m[1] )
					a = jQuery.clean( [ m[1] ] );

				// HANDLE: $("#id")
				else {
					var tmp = document.getElementById( m[3] );
					if ( tmp )
						// Handle the case where IE and Opera return items
						// by name instead of ID
						if ( tmp.id != m[3] ) {
							return jQuery().find( a );
						}else {
							this[0] = tmp;
							this.length = 1;
							return this;
						}
					else
						a = [];
				}

			// HANDLE: $(expr)
			} else
				return new jQuery( c ).find( a );

		// HANDLE: $(function)
		// Shortcut for document ready
		} else if ( jQuery.isFunction(a) )
			return new jQuery(document)[ jQuery.fn.ready ? "ready" : "load" ]( a );

		return this.setArray(
			// HANDLE: $(array)
			a.constructor == Array && a ||

			// HANDLE: $(arraylike)
			// Watch for when an array-like object is passed as the selector
			(a.jquery || a.length && a != window && !a.nodeType && a[0] != undefined && a[0].nodeType) && jQuery.makeArray( a ) ||

			// HANDLE: $(*)
			[ a ] );
	},
	jquery: "1.1.4",

	size: function() {
		return this.length;
	},
	
	length: 0,

	get: function( num ) {
		return num == undefined ?

			// Return a 'clean' array
			jQuery.makeArray( this ) :

			// Return just the object
			this[num];
	},
	pushStack: function( a ) {
		var ret = jQuery(a);
		ret.prevObject = this;
		return ret;
	},
	setArray: function( a ) {
		this.length = 0;
		Array.prototype.push.apply( this, a );
		return this;
	},
	each: function( fn, args ) {
		return jQuery.each( this, fn, args );
	},
	index: function( obj ) {
		var pos = -1;
		this.each(function(i){
			if ( this == obj ) pos = i;
		});
		return pos;
	},

	attr: function( key, value, type ) {
		var obj = key;
		
		// Look for the case where we're accessing a style value
		if ( key.constructor == String )
			if ( value == undefined )
				return this.length && jQuery[ type || "attr" ]( this[0], key ) || undefined;
			else {
				obj = {};
				obj[ key ] = value;
			}
		
		// Check to see if we're setting style values
		return this.each(function(index){
			// Set all the styles
			for ( var prop in obj )
				jQuery.attr(
					type ? this.style : this,
					prop, jQuery.prop(this, obj[prop], type, index, prop)
				);
		});
	},

	css: function( key, value ) {
		return this.attr( key, value, "curCSS" );
	},

	text: function(e) {
		if ( typeof e != "object" && e != null )
			return this.empty().append( document.createTextNode( e ) );

		var t = "";
		jQuery.each( e || this, function(){
			jQuery.each( this.childNodes, function(){
				if ( this.nodeType != 8 )
					t += this.nodeType != 1 ?
						this.nodeValue : jQuery.fn.text([ this ]);
			});
		});
		return t;
	},

	wrap: function() {
		// The elements to wrap the target around
		var a, args = arguments;

		// Wrap each of the matched elements individually
		return this.each(function(){
			if ( !a )
				a = jQuery.clean(args, this.ownerDocument);

			// Clone the structure that we're using to wrap
			var b = a[0].cloneNode(true);

			// Insert it before the element to be wrapped
			this.parentNode.insertBefore( b, this );

			// Find the deepest point in the wrap structure
			while ( b.firstChild )
				b = b.firstChild;

			// Move the matched element to within the wrap structure
			b.appendChild( this );
		});
	},
	append: function() {
		return this.domManip(arguments, true, 1, function(a){
			this.appendChild( a );
		});
	},
	prepend: function() {
		return this.domManip(arguments, true, -1, function(a){
			this.insertBefore( a, this.firstChild );
		});
	},
	before: function() {
		return this.domManip(arguments, false, 1, function(a){
			this.parentNode.insertBefore( a, this );
		});
	},
	after: function() {
		return this.domManip(arguments, false, -1, function(a){
			this.parentNode.insertBefore( a, this.nextSibling );
		});
	},
	end: function() {
		return this.prevObject || jQuery([]);
	},
	find: function(t) {
		var data = jQuery.map(this, function(a){ return jQuery.find(t,a); });
		return this.pushStack( /[^+>] [^+>]/.test( t ) || t.indexOf("..") > -1 ?
			jQuery.unique( data ) : data );
	},
	clone: function(deep) {
		deep = deep != undefined ? deep : true;
		var $this = this.add(this.find("*"));
		if (jQuery.browser.msie) {
			// Need to remove events on the element and its descendants
			$this.each(function() {
				this._$events = {};
				for (var type in this.$events)
					this._$events[type] = jQuery.extend({},this.$events[type]);
			}).unbind();
		}

		// Do the clone
		var r = this.pushStack( jQuery.map( this, function(a){
			return a.cloneNode( deep );
		}) );

		if (jQuery.browser.msie) {
			$this.each(function() {
				// Add the events back to the original and its descendants
				var events = this._$events;
				for (var type in events)
					for (var handler in events[type])
						jQuery.event.add(this, type, events[type][handler], events[type][handler].data);
				this._$events = null;
			});
		}

		// copy form values over
		if (deep) {
			var inputs = r.add(r.find('*')).filter('select,input[@type=checkbox]');
			$this.filter('select,input[@type=checkbox]').each(function(i) {
				if (this.selectedIndex)
					inputs[i].selectedIndex = this.selectedIndex;
				if (this.checked)
					inputs[i].checked = true;
			});
		}

		// Return the cloned set
		return r;
	},

	filter: function(t) {
		return this.pushStack(
			jQuery.isFunction( t ) &&
			jQuery.grep(this, function(el, index){
				return t.apply(el, [index]);
			}) ||

			jQuery.multiFilter(t,this) );
	},

	not: function(t) {
		return this.pushStack(
			t.constructor == String &&
			jQuery.multiFilter(t, this, true) ||

			jQuery.grep(this, function(a) {
				return ( t.constructor == Array || t.jquery )
					? jQuery.inArray( a, t ) < 0
					: a != t;
			})
		);
	},

	add: function(t) {
		return this.pushStack( jQuery.merge(
			this.get(),
			t.constructor == String ?
				jQuery(t).get() :
				t.length != undefined && (!t.nodeName || t.nodeName == "FORM") ?
					t : [t] )
		);
	},
	is: function(expr) {
		return expr ? jQuery.multiFilter(expr,this).length > 0 : false;
	},

	val: function( val ) {
		return val == undefined ?
			( this.length ? this[0].value : null ) :
			this.attr( "value", val );
	},

	html: function( val ) {
		return val == undefined ?
			( this.length ? this[0].innerHTML : null ) :
			this.empty().append( val );
	},

	slice: function() {
		return this.pushStack( Array.prototype.slice.apply( this, arguments ) );
	},
	domManip: function(args, table, dir, fn){
		var clone = this.length > 1, a; 

		return this.each(function(){
			if ( !a ) {
				a = jQuery.clean(args, this.ownerDocument);
				if ( dir < 0 )
					a.reverse();
			}

			var obj = this;

			if ( table && jQuery.nodeName(this, "table") && jQuery.nodeName(a[0], "tr") )
				obj = this.getElementsByTagName("tbody")[0] || this.appendChild(document.createElement("tbody"));

			jQuery.each( a, function(){
				if ( jQuery.nodeName(this, "script") ) {
					if ( this.src )
						jQuery.ajax({ url: this.src, async: false, dataType: "script" });
					else
						jQuery.globalEval( this.text || this.textContent || this.innerHTML || "" );
				} else
					fn.apply( obj, [ clone ? this.cloneNode(true) : this ] );
			});
		});
	}
};

jQuery.extend = jQuery.fn.extend = function() {
	// copy reference to target object
	var target = arguments[0] || {}, a = 1, al = arguments.length, deep = false;

	// Handle a deep copy situation
	if ( target.constructor == Boolean ) {
		deep = target;
		target = arguments[1] || {};
	}

	// extend jQuery itself if only one argument is passed
	if ( al == 1 ) {
		target = this;
		a = 0;
	}

	var prop;

	for ( ; a < al; a++ )
		// Only deal with non-null/undefined values
		if ( (prop = arguments[a]) != null )
			// Extend the base object
			for ( var i in prop ) {
				// Prevent never-ending loop
				if ( target == prop[i] )
					continue;

				// Recurse if we're merging object values
				if ( deep && typeof prop[i] == 'object' && target[i] )
					jQuery.extend( target[i], prop[i] );

				// Don't bring in undefined values
				else if ( prop[i] != undefined )
					target[i] = prop[i];
			}

	// Return the modified object
	return target;
};

jQuery.extend({
	noConflict: function(deep) {
		window.$ = _$;
		if ( deep )
			window.jQuery = _jQuery;
		return jQuery;
	},

	// This may seem like some crazy code, but trust me when I say that this
	// is the only cross-browser way to do this. --John
	isFunction: function( fn ) {
		return !!fn && typeof fn != "string" && !fn.nodeName && 
			fn.constructor != Array && /function/i.test( fn + "" );
	},
	
	// check if an element is in a XML document
	isXMLDoc: function(elem) {
		return elem.documentElement && !elem.body ||
			elem.tagName && elem.ownerDocument && !elem.ownerDocument.body;
	},

	// Evalulates a script in a global context
	// Evaluates Async. in Safari 2 :-(
	globalEval: function( data ) {
		data = jQuery.trim( data );
		if ( data ) {
			if ( window.execScript )
				window.execScript( data );
			else if ( jQuery.browser.safari )
				// safari doesn't provide a synchronous global eval
				window.setTimeout( data, 0 );
			else
				eval.call( window, data );
		}
	},

	nodeName: function( elem, name ) {
		return elem.nodeName && elem.nodeName.toUpperCase() == name.toUpperCase();
	},
	// args is for internal usage only
	each: function( obj, fn, args ) {
		if ( args ) {
			if ( obj.length == undefined )
				for ( var i in obj )
					fn.apply( obj[i], args );
			else
				for ( var i = 0, ol = obj.length; i < ol; i++ )
					if ( fn.apply( obj[i], args ) === false ) break;

		// A special, fast, case for the most common use of each
		} else {
			if ( obj.length == undefined )
				for ( var i in obj )
					fn.call( obj[i], i, obj[i] );
			else
				for ( var i = 0, ol = obj.length, val = obj[0]; 
					i < ol && fn.call(val,i,val) !== false; val = obj[++i] ){}
		}

		return obj;
	},
	
	prop: function(elem, value, type, index, prop){
			// Handle executable functions
			if ( jQuery.isFunction( value ) )
				value = value.call( elem, [index] );
				
			// exclude the following css properties to add px
			var exclude = /z-?index|font-?weight|opacity|zoom|line-?height/i;

			// Handle passing in a number to a CSS property
			return value && value.constructor == Number && type == "curCSS" && !exclude.test(prop) ?
				value + "px" :
				value;
	},

	className: {
		// internal only, use addClass("class")
		add: function( elem, c ){
			jQuery.each( (c || "").split(/\s+/), function(i, cur){
				if ( !jQuery.className.has( elem.className, cur ) )
					elem.className += ( elem.className ? " " : "" ) + cur;
			});
		},

		// internal only, use removeClass("class")
		remove: function( elem, c ){
			elem.className = c != undefined ?
				jQuery.grep( elem.className.split(/\s+/), function(cur){
					return !jQuery.className.has( c, cur );	
				}).join(" ") : "";
		},

		// internal only, use is(".class")
		has: function( t, c ) {
			return jQuery.inArray( c, (t.className || t).toString().split(/\s+/) ) > -1;
		}
	},
	swap: function(e,o,f) {
		for ( var i in o ) {
			e.style["old"+i] = e.style[i];
			e.style[i] = o[i];
		}
		f.apply( e, [] );
		for ( var i in o )
			e.style[i] = e.style["old"+i];
	},

	css: function(e,p) {
		if ( p == "height" || p == "width" ) {
			var old = {}, oHeight, oWidth, d = ["Top","Bottom","Right","Left"];

			jQuery.each( d, function(){
				old["padding" + this] = 0;
				old["border" + this + "Width"] = 0;
			});

			jQuery.swap( e, old, function() {
				if ( jQuery(e).is(':visible') ) {
					oHeight = e.offsetHeight;
					oWidth = e.offsetWidth;
				} else {
					e = jQuery(e.cloneNode(true))
						.find(":radio").removeAttr("checked").end()
						.css({
							visibility: "hidden", position: "absolute", display: "block", right: "0", left: "0"
						}).appendTo(e.parentNode)[0];

					var parPos = jQuery.css(e.parentNode,"position") || "static";
					if ( parPos == "static" )
						e.parentNode.style.position = "relative";

					oHeight = e.clientHeight;
					oWidth = e.clientWidth;

					if ( parPos == "static" )
						e.parentNode.style.position = "static";

					e.parentNode.removeChild(e);
				}
			});

			return p == "height" ? oHeight : oWidth;
		}

		return jQuery.curCSS( e, p );
	},

	curCSS: function(elem, prop, force) {
		var ret, stack = [], swap = [];

		// A helper method for determining if an element's values are broken
		function color(a){
			if ( !jQuery.browser.safari )
				return false;

			var ret = document.defaultView.getComputedStyle(a,null);
			return !ret || ret.getPropertyValue("color") == "";
		}

		if (prop == "opacity" && jQuery.browser.msie) {
			ret = jQuery.attr(elem.style, "opacity");
			return ret == "" ? "1" : ret;
		}
		
		if (prop.match(/float/i))
			prop = styleFloat;

		if (!force && elem.style[prop])
			ret = elem.style[prop];

		else if (document.defaultView && document.defaultView.getComputedStyle) {

			if (prop.match(/float/i))
				prop = "float";

			prop = prop.replace(/([A-Z])/g,"-$1").toLowerCase();
			var cur = document.defaultView.getComputedStyle(elem, null);

			if ( cur && !color(elem) )
				ret = cur.getPropertyValue(prop);

			// If the element isn't reporting its values properly in Safari
			// then some display: none elements are involved
			else {
				// Locate all of the parent display: none elements
				for ( var a = elem; a && color(a); a = a.parentNode )
					stack.unshift(a);

				// Go through and make them visible, but in reverse
				// (It would be better if we knew the exact display type that they had)
				for ( a = 0; a < stack.length; a++ )
					if ( color(stack[a]) ) {
						swap[a] = stack[a].style.display;
						stack[a].style.display = "block";
					}

				// Since we flip the display style, we have to handle that
				// one special, otherwise get the value
				ret = prop == "display" && swap[stack.length-1] != null ?
					"none" :
					document.defaultView.getComputedStyle(elem,null).getPropertyValue(prop) || "";

				// Finally, revert the display styles back
				for ( a = 0; a < swap.length; a++ )
					if ( swap[a] != null )
						stack[a].style.display = swap[a];
			}

			if ( prop == "opacity" && ret == "" )
				ret = "1";

		} else if (elem.currentStyle) {
			var newProp = prop.replace(/\-(\w)/g,function(m,c){return c.toUpperCase();});
			ret = elem.currentStyle[prop] || elem.currentStyle[newProp];
		}

		return ret;
	},
	
	clean: function(a, doc) {
		var r = [];
		doc = doc || document;

		jQuery.each( a, function(i,arg){
			if ( !arg ) return;

			if ( arg.constructor == Number )
				arg = arg.toString();
			
			// Convert html string into DOM nodes
			if ( typeof arg == "string" ) {
				// Trim whitespace, otherwise indexOf won't work as expected
				var s = jQuery.trim(arg).toLowerCase(), div = doc.createElement("div"), tb = [];

				var wrap =
					// option or optgroup
					!s.indexOf("<opt") &&
					[1, "<select>", "</select>"] ||
					
					!s.indexOf("<leg") &&
					[1, "<fieldset>", "</fieldset>"] ||
					
					s.match(/^<(thead|tbody|tfoot|colg|cap)/) &&
					[1, "<table>", "</table>"] ||
					
					!s.indexOf("<tr") &&
					[2, "<table><tbody>", "</tbody></table>"] ||
					
				 	// <thead> matched above
					(!s.indexOf("<td") || !s.indexOf("<th")) &&
					[3, "<table><tbody><tr>", "</tr></tbody></table>"] ||
					
					!s.indexOf("<col") &&
					[2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"] ||

					// IE can't serialize <link> and <script> tags normally
					jQuery.browser.msie &&
					[1, "div<div>", "</div>"] ||
					
					[0,"",""];

				// Go to html and back, then peel off extra wrappers
				div.innerHTML = wrap[1] + arg + wrap[2];
				
				// Move to the right depth
				while ( wrap[0]-- )
					div = div.lastChild;
				
				// Remove IE's autoinserted <tbody> from table fragments
				if ( jQuery.browser.msie ) {
					
					// String was a <table>, *may* have spurious <tbody>
					if ( !s.indexOf("<table") && s.indexOf("<tbody") < 0 ) 
						tb = div.firstChild && div.firstChild.childNodes;
						
					// String was a bare <thead> or <tfoot>
					else if ( wrap[1] == "<table>" && s.indexOf("<tbody") < 0 )
						tb = div.childNodes;

					for ( var n = tb.length-1; n >= 0 ; --n )
						if ( jQuery.nodeName(tb[n], "tbody") && !tb[n].childNodes.length )
							tb[n].parentNode.removeChild(tb[n]);
	
					// IE completely kills leading whitespace when innerHTML is used	
					if ( /^\s/.test(arg) )	
						div.insertBefore( doc.createTextNode( arg.match(/^\s*/)[0] ), div.firstChild );

				}
				
				arg = jQuery.makeArray( div.childNodes );
			}

			if ( 0 === arg.length && (!jQuery.nodeName(arg, "form") && !jQuery.nodeName(arg, "select")) )
				return;

			if ( arg[0] == undefined || jQuery.nodeName(arg, "form") || arg.options )
				r.push( arg );
			else
				r = jQuery.merge( r, arg );

		});

		return r;
	},
	
	attr: function(elem, name, value){
		var fix = jQuery.isXMLDoc(elem) ? {} : jQuery.props;

		// Safari mis-reports the default selected property of a hidden option
		// Accessing the parent's selectedIndex property fixes it
		if ( name == "selected" && jQuery.browser.safari )
			elem.parentNode.selectedIndex;
		
		// Certain attributes only work when accessed via the old DOM 0 way
		if ( fix[name] ) {
			if ( value != undefined ) elem[fix[name]] = value;
			return elem[fix[name]];
		} else if ( jQuery.browser.msie && name == "style" )
			return jQuery.attr( elem.style, "cssText", value );

		else if ( value == undefined && jQuery.browser.msie && jQuery.nodeName(elem, "form") && (name == "action" || name == "method") )
			return elem.getAttributeNode(name).nodeValue;

		// IE elem.getAttribute passes even for style
		else if ( elem.tagName ) {

			if ( value != undefined ) elem.setAttribute( name, value );
			if ( jQuery.browser.msie && /href|src/.test(name) && !jQuery.isXMLDoc(elem) ) 
				return elem.getAttribute( name, 2 );
			return elem.getAttribute( name );

		// elem is actually elem.style ... set the style
		} else {
			// IE actually uses filters for opacity
			if ( name == "opacity" && jQuery.browser.msie ) {
				if ( value != undefined ) {
					// IE has trouble with opacity if it does not have layout
					// Force it by setting the zoom level
					elem.zoom = 1; 
	
					// Set the alpha filter to set the opacity
					elem.filter = (elem.filter || "").replace(/alpha\([^)]*\)/,"") +
						(parseFloat(value).toString() == "NaN" ? "" : "alpha(opacity=" + value * 100 + ")");
				}
	
				return elem.filter ? 
					(parseFloat( elem.filter.match(/opacity=([^)]*)/)[1] ) / 100).toString() : "";
			}
			name = name.replace(/-([a-z])/ig,function(z,b){return b.toUpperCase();});
			if ( value != undefined ) elem[name] = value;
			return elem[name];
		}
	},
	trim: function(t){
		return (t||"").replace(/^\s+|\s+$/g, "");
	},

	makeArray: function( a ) {
		var r = [];

		// Need to use typeof to fight Safari childNodes crashes
		if ( typeof a != "array" )
			for ( var i = 0, al = a.length; i < al; i++ )
				r.push( a[i] );
		else
			r = a.slice( 0 );

		return r;
	},

	inArray: function( b, a ) {
		for ( var i = 0, al = a.length; i < al; i++ )
			if ( a[i] == b )
				return i;
		return -1;
	},
	merge: function(first, second) {
		// We have to loop this way because IE & Opera overwrite the length
		// expando of getElementsByTagName

		// Also, we need to make sure that the correct elements are being returned
		// (IE returns comment nodes in a '*' query)
		if ( jQuery.browser.msie ) {
			for ( var i = 0; second[i]; i++ )
				if ( second[i].nodeType != 8 )
					first.push(second[i]);
		} else
			for ( var i = 0; second[i]; i++ )
				first.push(second[i]);

		return first;
	},
	unique: function(first) {
		var r = [], num = jQuery.mergeNum++;

		try {
			for ( var i = 0, fl = first.length; i < fl; i++ )
				if ( num != first[i].mergeNum ) {
					first[i].mergeNum = num;
					r.push(first[i]);
				}
		} catch(e) {
			r = first;
		}

		return r;
	},

	mergeNum: 0,
	grep: function(elems, fn, inv) {
		// If a string is passed in for the function, make a function
		// for it (a handy shortcut)
		if ( typeof fn == "string" )
			fn = eval("false||function(a,i){return " + fn + "}");

		var result = [];

		// Go through the array, only saving the items
		// that pass the validator function
		for ( var i = 0, el = elems.length; i < el; i++ )
			if ( !inv && fn(elems[i],i) || inv && !fn(elems[i],i) )
				result.push( elems[i] );

		return result;
	},
	map: function(elems, fn) {
		// If a string is passed in for the function, make a function
		// for it (a handy shortcut)
		if ( typeof fn == "string" )
			fn = eval("false||function(a){return " + fn + "}");

		var result = [];

		// Go through the array, translating each of the items to their
		// new value (or values).
		for ( var i = 0, el = elems.length; i < el; i++ ) {
			var val = fn(elems[i],i);

			if ( val !== null && val != undefined ) {
				if ( val.constructor != Array ) val = [val];
				result = result.concat( val );
			}
		}

		return result;
	}
});
 
/*
 * Whether the W3C compliant box model is being used.
 *
 * @property
 * @name $.boxModel
 * @type Boolean
 * @cat JavaScript
 */
var userAgent = navigator.userAgent.toLowerCase();

// Figure out what browser is being used
jQuery.browser = {
	version: (userAgent.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/) || [])[1],
	safari: /webkit/.test(userAgent),
	opera: /opera/.test(userAgent),
	msie: /msie/.test(userAgent) && !/opera/.test(userAgent),
	mozilla: /mozilla/.test(userAgent) && !/(compatible|webkit)/.test(userAgent)
};

var styleFloat = jQuery.browser.msie ? "styleFloat" : "cssFloat";
	
jQuery.extend({
	// Check to see if the W3C box model is being used
	boxModel: !jQuery.browser.msie || document.compatMode == "CSS1Compat",
	
	styleFloat: jQuery.browser.msie ? "styleFloat" : "cssFloat",
	
	props: {
		"for": "htmlFor",
		"class": "className",
		"float": styleFloat,
		cssFloat: styleFloat,
		styleFloat: styleFloat,
		innerHTML: "innerHTML",
		className: "className",
		value: "value",
		disabled: "disabled",
		checked: "checked",
		readonly: "readOnly",
		selected: "selected",
		maxlength: "maxLength"
	}
});

jQuery.each({
	parent: "a.parentNode",
	parents: "jQuery.parents(a)",
	next: "jQuery.nth(a,2,'nextSibling')",
	prev: "jQuery.nth(a,2,'previousSibling')",
	siblings: "jQuery.sibling(a.parentNode.firstChild,a)",
	children: "jQuery.sibling(a.firstChild)"
}, function(i,n){
	jQuery.fn[ i ] = function(a) {
		var ret = jQuery.map(this,n);
		if ( a && typeof a == "string" )
			ret = jQuery.multiFilter(a,ret);
		return this.pushStack( jQuery.unique(ret) );
	};
});

jQuery.each({
	appendTo: "append",
	prependTo: "prepend",
	insertBefore: "before",
	insertAfter: "after"
}, function(i,n){
	jQuery.fn[ i ] = function(){
		var a = arguments;
		return this.each(function(){
			for ( var j = 0, al = a.length; j < al; j++ )
				jQuery(a[j])[n]( this );
		});
	};
});

jQuery.each( {
	removeAttr: function( key ) {
		jQuery.attr( this, key, "" );
		this.removeAttribute( key );
	},
	addClass: function(c){
		jQuery.className.add(this,c);
	},
	removeClass: function(c){
		jQuery.className.remove(this,c);
	},
	toggleClass: function( c ){
		jQuery.className[ jQuery.className.has(this,c) ? "remove" : "add" ](this, c);
	},
	remove: function(a){
		if ( !a || jQuery.filter( a, [this] ).r.length )
			this.parentNode.removeChild( this );
	},
	empty: function() {
		while ( this.firstChild )
			this.removeChild( this.firstChild );
	}
}, function(i,n){
	jQuery.fn[ i ] = function() {
		return this.each( n, arguments );
	};
});

// DEPRECATED
jQuery.each( [ "eq", "lt", "gt", "contains" ], function(i,n){
	jQuery.fn[ n ] = function(num,fn) {
		return this.filter( ":" + n + "(" + num + ")", fn );
	};
});

jQuery.each( [ "height", "width" ], function(i,n){
	jQuery.fn[ n ] = function(h) {
		return h == undefined ?
			( this.length ? jQuery.css( this[0], n ) : null ) :
			this.css( n, h.constructor == String ? h : h + "px" );
	};
});

var chars = jQuery.browser.safari && parseInt(jQuery.browser.version) < 417 ?
		"(?:[\\w*_-]|\\\\.)" :
		"(?:[\\w\u0128-\uFFFF*_-]|\\\\.)",
	quickChild = new RegExp("^[/>]\\s*(" + chars + "+)"),
	quickID = new RegExp("^(" + chars + "+)(#)(" + chars + "+)"),
	quickClass = new RegExp("^([#.]?)(" + chars + "*)");

jQuery.extend({
	expr: {
		"": "m[2]=='*'||jQuery.nodeName(a,m[2])",
		"#": "a.getAttribute('id')==m[2]",
		":": {
			// Position Checks
			lt: "i<m[3]-0",
			gt: "i>m[3]-0",
			nth: "m[3]-0==i",
			eq: "m[3]-0==i",
			first: "i==0",
			last: "i==r.length-1",
			even: "i%2==0",
			odd: "i%2",

			// Child Checks
			"first-child": "a.parentNode.getElementsByTagName('*')[0]==a",
			"last-child": "jQuery.nth(a.parentNode.lastChild,1,'previousSibling')==a",
			"only-child": "!jQuery.nth(a.parentNode.lastChild,2,'previousSibling')",

			// Parent Checks
			parent: "a.firstChild",
			empty: "!a.firstChild",

			// Text Check
			contains: "(a.textContent||a.innerText||'').indexOf(m[3])>=0",

			// Visibility
			visible: '"hidden"!=a.type&&jQuery.css(a,"display")!="none"&&jQuery.css(a,"visibility")!="hidden"',
			hidden: '"hidden"==a.type||jQuery.css(a,"display")=="none"||jQuery.css(a,"visibility")=="hidden"',

			// Form attributes
			enabled: "!a.disabled",
			disabled: "a.disabled",
			checked: "a.checked",
			selected: "a.selected||jQuery.attr(a,'selected')",

			// Form elements
			text: "'text'==a.type",
			radio: "'radio'==a.type",
			checkbox: "'checkbox'==a.type",
			file: "'file'==a.type",
			password: "'password'==a.type",
			submit: "'submit'==a.type",
			image: "'image'==a.type",
			reset: "'reset'==a.type",
			button: '"button"==a.type||jQuery.nodeName(a,"button")',
			input: "/input|select|textarea|button/i.test(a.nodeName)",

			// :has()
			has: "jQuery.find(m[3],a).length"
		},
		// DEPRECATED
		"[": "jQuery.find(m[2],a).length"
	},
	
	// The regular expressions that power the parsing engine
	parse: [
		// Match: [@value='test'], [@foo]
		/^\[ *(@)([\w-]+) *([!*$^~=]*) *('?"?)(.*?)\4 *\]/,

		// DEPRECATED
		// Match: [div], [div p]
		/^(\[)\s*(.*?(\[.*?\])?[^[]*?)\s*\]/,

		// Match: :contains('foo')
		/^(:)([\w-]+)\("?'?(.*?(\(.*?\))?[^(]*?)"?'?\)/,

		// Match: :even, :last-chlid, #id, .class
		new RegExp("^([:.#]*)(" + chars + "+)")
	],

	multiFilter: function( expr, elems, not ) {
		var old, cur = [];

		while ( expr && expr != old ) {
			old = expr;
			var f = jQuery.filter( expr, elems, not );
			expr = f.t.replace(/^\s*,\s*/, "" );
			cur = not ? elems = f.r : jQuery.merge( cur, f.r );
		}

		return cur;
	},
	find: function( t, context ) {
		// Quickly handle non-string expressions
		if ( typeof t != "string" )
			return [ t ];

		// Make sure that the context is a DOM Element
		if ( context && !context.nodeType )
			context = null;

		// Set the correct context (if none is provided)
		context = context || document;

		// DEPRECATED
		// Handle the common XPath // expression
		if ( !t.indexOf("//") ) {
			//context = context.documentElement;
			t = t.substr(2,t.length);

		// DEPRECATED
		// And the / root expression
		} else if ( !t.indexOf("/") && !context.ownerDocument ) {
			context = context.documentElement;
			t = t.substr(1,t.length);
			if ( t.indexOf("/") >= 1 )
				t = t.substr(t.indexOf("/"),t.length);
		}

		// Initialize the search
		var ret = [context], done = [], last;

		// Continue while a selector expression exists, and while
		// we're no longer looping upon ourselves
		while ( t && last != t ) {
			var r = [];
			last = t;

			// DEPRECATED
			t = jQuery.trim(t).replace( /^\/\//, "" );

			var foundToken = false;

			// An attempt at speeding up child selectors that
			// point to a specific element tag
			var re = quickChild;
			var m = re.exec(t);

			if ( m ) {
				var nodeName = m[1].toUpperCase();

				// Perform our own iteration and filter
				for ( var i = 0; ret[i]; i++ )
					for ( var c = ret[i].firstChild; c; c = c.nextSibling )
						if ( c.nodeType == 1 && (nodeName == "*" || c.nodeName.toUpperCase() == nodeName.toUpperCase()) )
							r.push( c );

				ret = r;
				t = t.replace( re, "" );
				if ( t.indexOf(" ") == 0 ) continue;
				foundToken = true;
			} else {
				// (.. and /) DEPRECATED
				re = /^((\/?\.\.)|([>\/+~]))\s*(\w*)/i;

				if ( (m = re.exec(t)) != null ) {
					r = [];

					var nodeName = m[4], mergeNum = jQuery.mergeNum++;
					m = m[1];

					for ( var j = 0, rl = ret.length; j < rl; j++ )
						if ( m.indexOf("..") < 0 ) {
							var n = m == "~" || m == "+" ? ret[j].nextSibling : ret[j].firstChild;
							for ( ; n; n = n.nextSibling )
								if ( n.nodeType == 1 ) {
									if ( m == "~" && n.mergeNum == mergeNum ) break;
									
									if (!nodeName || n.nodeName.toUpperCase() == nodeName.toUpperCase() ) {
										if ( m == "~" ) n.mergeNum = mergeNum;
										r.push( n );
									}
									
									if ( m == "+" ) break;
								}
						// DEPRECATED
						} else
							r.push( ret[j].parentNode );

					ret = r;

					// And remove the token
					t = jQuery.trim( t.replace( re, "" ) );
					foundToken = true;
				}
			}

			// See if there's still an expression, and that we haven't already
			// matched a token
			if ( t && !foundToken ) {
				// Handle multiple expressions
				if ( !t.indexOf(",") ) {
					// Clean the result set
					if ( context == ret[0] ) ret.shift();

					// Merge the result sets
					done = jQuery.merge( done, ret );

					// Reset the context
					r = ret = [context];

					// Touch up the selector string
					t = " " + t.substr(1,t.length);

				} else {
					// Optimize for the case nodeName#idName
					var re2 = quickID;
					var m = re2.exec(t);
					
					// Re-organize the results, so that they're consistent
					if ( m ) {
					   m = [ 0, m[2], m[3], m[1] ];

					} else {
						// Otherwise, do a traditional filter check for
						// ID, class, and element selectors
						re2 = quickClass;
						m = re2.exec(t);
					}

					m[2] = m[2].replace(/\\/g, "");

					var elem = ret[ret.length-1];

					// Try to do a global search by ID, where we can
					if ( m[1] == "#" && elem && elem.getElementById && !jQuery.isXMLDoc(elem) ) {
						// Optimization for HTML document case
						var oid = elem.getElementById(m[2]);
						
						// Do a quick check for the existence of the actual ID attribute
						// to avoid selecting by the name attribute in IE
						// also check to insure id is a string to avoid selecting an element with the name of 'id' inside a form
						if ( (jQuery.browser.msie||jQuery.browser.opera) && oid && typeof oid.id == "string" && oid.id != m[2] )
							oid = jQuery('[@id="'+m[2]+'"]', elem)[0];

						// Do a quick check for node name (where applicable) so
						// that div#foo searches will be really fast
						ret = r = oid && (!m[3] || jQuery.nodeName(oid, m[3])) ? [oid] : [];
					} else {
						// We need to find all descendant elements
						for ( var i = 0; ret[i]; i++ ) {
							// Grab the tag name being searched for
							var tag = m[1] != "" || m[0] == "" ? "*" : m[2];

							// Handle IE7 being really dumb about <object>s
							if ( tag == "*" && ret[i].nodeName.toLowerCase() == "object" )
								tag = "param";

							r = jQuery.merge( r, ret[i].getElementsByTagName( tag ));
						}

						// It's faster to filter by class and be done with it
						if ( m[1] == "." )
							r = jQuery.classFilter( r, m[2] );

						// Same with ID filtering
						if ( m[1] == "#" ) {
							var tmp = [];

							// Try to find the element with the ID
							for ( var i = 0; r[i]; i++ )
								if ( r[i].getAttribute("id") == m[2] ) {
									tmp = [ r[i] ];
									break;
								}

							r = tmp;
						}

						ret = r;
					}

					t = t.replace( re2, "" );
				}

			}

			// If a selector string still exists
			if ( t ) {
				// Attempt to filter it
				var val = jQuery.filter(t,r);
				ret = r = val.r;
				t = jQuery.trim(val.t);
			}
		}

		// An error occurred with the selector;
		// just return an empty set instead
		if ( t )
			ret = [];

		// Remove the root context
		if ( ret && context == ret[0] )
			ret.shift();

		// And combine the results
		done = jQuery.merge( done, ret );

		return done;
	},

	classFilter: function(r,m,not){
		m = " " + m + " ";
		var tmp = [];
		for ( var i = 0; r[i]; i++ ) {
			var pass = (" " + r[i].className + " ").indexOf( m ) >= 0;
			if ( !not && pass || not && !pass )
				tmp.push( r[i] );
		}
		return tmp;
	},

	filter: function(t,r,not) {
		var last;

		// Look for common filter expressions
		while ( t  && t != last ) {
			last = t;

			var p = jQuery.parse, m;

			for ( var i = 0; p[i]; i++ ) {
				m = p[i].exec( t );

				if ( m ) {
					// Remove what we just matched
					t = t.substring( m[0].length );

					m[2] = m[2].replace(/\\/g, "");
					break;
				}
			}

			if ( !m )
				break;

			// :not() is a special case that can be optimized by
			// keeping it out of the expression list
			if ( m[1] == ":" && m[2] == "not" )
				r = jQuery.filter(m[3], r, true).r;

			// We can get a big speed boost by filtering by class here
			else if ( m[1] == "." )
				r = jQuery.classFilter(r, m[2], not);

			else if ( m[1] == "@" ) {
				var tmp = [], type = m[3];
				
				for ( var i = 0, rl = r.length; i < rl; i++ ) {
					var a = r[i], z = a[ jQuery.props[m[2]] || m[2] ];
					
					if ( z == null || /href|src|selected/.test(m[2]) )
						z = jQuery.attr(a,m[2]) || '';

					if ( (type == "" && !!z ||
						 type == "=" && z == m[5] ||
						 type == "!=" && z != m[5] ||
						 type == "^=" && z && !z.indexOf(m[5]) ||
						 type == "$=" && z.substr(z.length - m[5].length) == m[5] ||
						 (type == "*=" || type == "~=") && z.indexOf(m[5]) >= 0) ^ not )
							tmp.push( a );
				}
				
				r = tmp;

			// We can get a speed boost by handling nth-child here
			} else if ( m[1] == ":" && m[2] == "nth-child" ) {
				var num = jQuery.mergeNum++, tmp = [],
					test = /(\d*)n\+?(\d*)/.exec(
						m[3] == "even" && "2n" || m[3] == "odd" && "2n+1" ||
						!/\D/.test(m[3]) && "n+" + m[3] || m[3]),
					first = (test[1] || 1) - 0, last = test[2] - 0;

				for ( var i = 0, rl = r.length; i < rl; i++ ) {
					var node = r[i], parentNode = node.parentNode;

					if ( num != parentNode.mergeNum ) {
						var c = 1;

						for ( var n = parentNode.firstChild; n; n = n.nextSibling )
							if ( n.nodeType == 1 )
								n.nodeIndex = c++;

						parentNode.mergeNum = num;
					}

					var add = false;

					if ( first == 1 ) {
						if ( last == 0 || node.nodeIndex == last )
							add = true;
					} else if ( (node.nodeIndex + last) % first == 0 )
						add = true;

					if ( add ^ not )
						tmp.push( node );
				}

				r = tmp;

			// Otherwise, find the expression to execute
			} else {
				var f = jQuery.expr[m[1]];
				if ( typeof f != "string" )
					f = jQuery.expr[m[1]][m[2]];

				// Build a custom macro to enclose it
				f = eval("false||function(a,i){return " + f + "}");

				// Execute it against the current filter
				r = jQuery.grep( r, f, not );
			}
		}

		// Return an array of filtered elements (r)
		// and the modified expression string (t)
		return { r: r, t: t };
	},
	parents: function( elem ){
		var matched = [];
		var cur = elem.parentNode;
		while ( cur && cur != document ) {
			matched.push( cur );
			cur = cur.parentNode;
		}
		return matched;
	},
	nth: function(cur,result,dir,elem){
		result = result || 1;
		var num = 0;

		for ( ; cur; cur = cur[dir] )
			if ( cur.nodeType == 1 && ++num == result )
				break;

		return cur;
	},
	sibling: function( n, elem ) {
		var r = [];

		for ( ; n; n = n.nextSibling ) {
			if ( n.nodeType == 1 && (!elem || n != elem) )
				r.push( n );
		}

		return r;
	}
});
/*
 * A number of helper functions used for managing events.
 * Many of the ideas behind this code orignated from 
 * Dean Edwards' addEvent library.
 */
jQuery.event = {

	// Bind an event to an element
	// Original by Dean Edwards
	add: function(element, type, handler, data) {
		// For whatever reason, IE has trouble passing the window object
		// around, causing it to be cloned in the process
		if ( jQuery.browser.msie && element.setInterval != undefined )
			element = window;
		
		// Make sure that the function being executed has a unique ID
		if ( !handler.guid )
			handler.guid = this.guid++;
			
		// if data is passed, bind to handler 
		if( data != undefined ) { 
        	// Create temporary function pointer to original handler 
			var fn = handler; 

			// Create unique handler function, wrapped around original handler 
			handler = function() { 
				// Pass arguments and context to original handler 
				return fn.apply(this, arguments); 
			};

			// Store data in unique handler 
			handler.data = data;

			// Set the guid of unique handler to the same of original handler, so it can be removed 
			handler.guid = fn.guid;
		}

		// Init the element's event structure
		if (!element.$events)
			element.$events = {};
		
		if (!element.$handle)
			element.$handle = function() {
				// returned undefined or false
				var val;

				// Handle the second event of a trigger and when
				// an event is called after a page has unloaded
				if ( typeof jQuery == "undefined" || jQuery.event.triggered )
				  return val;
				
				val = jQuery.event.handle.apply(element, arguments);
				
				return val;
			};

		// Get the current list of functions bound to this event
		var handlers = element.$events[type];

		// Init the event handler queue
		if (!handlers) {
			handlers = element.$events[type] = {};	
			
			// And bind the global event handler to the element
			if (element.addEventListener)
				element.addEventListener(type, element.$handle, false);
			else
				element.attachEvent("on" + type, element.$handle);
		}

		// Add the function to the element's handler list
		handlers[handler.guid] = handler;

		// Keep track of which events have been used, for global triggering
		this.global[type] = true;
	},

	guid: 1,
	global: {},

	// Detach an event or set of events from an element
	remove: function(element, type, handler) {
		var events = element.$events, ret, index;

		if ( events ) {
			// type is actually an event object here
			if ( type && type.type ) {
				handler = type.handler;
				type = type.type;
			}
			
			if ( !type ) {
				for ( type in events )
					this.remove( element, type );

			} else if ( events[type] ) {
				// remove the given handler for the given type
				if ( handler )
					delete events[type][handler.guid];
				
				// remove all handlers for the given type
				else
					for ( handler in element.$events[type] )
						delete events[type][handler];

				// remove generic event handler if no more handlers exist
				for ( ret in events[type] ) break;
				if ( !ret ) {
					if (element.removeEventListener)
						element.removeEventListener(type, element.$handle, false);
					else
						element.detachEvent("on" + type, element.$handle);
					ret = null;
					delete events[type];
				}
			}

			// Remove the expando if it's no longer used
			for ( ret in events ) break;
			if ( !ret )
				element.$handle = element.$events = null;
		}
	},

	trigger: function(type, data, element) {
		// Clone the incoming data, if any
		data = jQuery.makeArray(data || []);

		// Handle a global trigger
		if ( !element ) {
			// Only trigger if we've ever bound an event for it
			if ( this.global[type] )
				jQuery("*").add([window, document]).trigger(type, data);

		// Handle triggering a single element
		} else {
			var val, ret, fn = jQuery.isFunction( element[ type ] || null );
			
			// Pass along a fake event
			data.unshift( this.fix({ type: type, target: element }) );

			// Trigger the event
			if ( jQuery.isFunction( element.$handle ) )
				val = element.$handle.apply( element, data );
			if ( !fn && element["on"+type] && element["on"+type].apply( element, data ) === false )
				val = false;

			// Liferay
			//if ( fn && val !== false && !(jQuery.nodeName(element, 'a') && type == "click") ) {
			if ( fn && val !== false && !(jQuery.nodeName(element, 'a') && type == "click") && !(type == 'focus' && element.offsetHeight == 0) ) {
				this.triggered = true;
				element[ type ]();
			}

			this.triggered = false;
		}
	},

	handle: function(event) {
		// returned undefined or false
		var val;

		// Empty object is for triggered events with no data
		event = jQuery.event.fix( event || window.event || {} ); 

		var c = this.$events && this.$events[event.type], args = Array.prototype.slice.call( arguments, 1 );
		args.unshift( event );

		for ( var j in c ) {
			// Pass in a reference to the handler function itself
			// So that we can later remove it
			args[0].handler = c[j];
			args[0].data = c[j].data;

			if ( c[j].apply( this, args ) === false ) {
				event.preventDefault();
				event.stopPropagation();
				val = false;
			}
		}

		// Clean up added properties in IE to prevent memory leak
		if (jQuery.browser.msie)
			event.target = event.preventDefault = event.stopPropagation =
				event.handler = event.data = null;

		return val;
	},

	fix: function(event) {
		// store a copy of the original event object 
		// and clone to set read-only properties
		var originalEvent = event;
		event = jQuery.extend({}, originalEvent);
		
		// add preventDefault and stopPropagation since 
		// they will not work on the clone
		event.preventDefault = function() {
			// if preventDefault exists run it on the original event
			if (originalEvent.preventDefault)
				originalEvent.preventDefault();
			// otherwise set the returnValue property of the original event to false (IE)
			originalEvent.returnValue = false;
		};
		event.stopPropagation = function() {
			// if stopPropagation exists run it on the original event
			if (originalEvent.stopPropagation)
				originalEvent.stopPropagation();
			// otherwise set the cancelBubble property of the original event to true (IE)
			originalEvent.cancelBubble = true;
		};
		
		// Fix target property, if necessary
		if ( !event.target && event.srcElement )
			event.target = event.srcElement;
				
		// check if target is a textnode (safari)
		if (jQuery.browser.safari && event.target.nodeType == 3)
			event.target = originalEvent.target.parentNode;

		// Add relatedTarget, if necessary
		if ( !event.relatedTarget && event.fromElement )
			event.relatedTarget = event.fromElement == event.target ? event.toElement : event.fromElement;

		// Calculate pageX/Y if missing and clientX/Y available
		if ( event.pageX == null && event.clientX != null ) {
			var e = document.documentElement, b = document.body;
			event.pageX = event.clientX + (e && e.scrollLeft || b.scrollLeft || 0);
			event.pageY = event.clientY + (e && e.scrollTop || b.scrollTop || 0);
		}
			
		// Add which for key events
		if ( !event.which && (event.charCode || event.keyCode) )
			event.which = event.charCode || event.keyCode;
		
		// Add metaKey to non-Mac browsers (use ctrl for PC's and Meta for Macs)
		if ( !event.metaKey && event.ctrlKey )
			event.metaKey = event.ctrlKey;

		// Add which for click: 1 == left; 2 == middle; 3 == right
		// Note: button is not normalized, so don't use it
		if ( !event.which && event.button )
			event.which = (event.button & 1 ? 1 : ( event.button & 2 ? 3 : ( event.button & 4 ? 2 : 0 ) ));
			
		return event;
	}
};

jQuery.fn.extend({
	bind: function( type, data, fn ) {
		return type == "unload" ? this.one(type, data, fn) : this.each(function(){
			jQuery.event.add( this, type, fn || data, fn && data );
		});
	},
	one: function( type, data, fn ) {
		return this.each(function(){
			jQuery.event.add( this, type, function(event) {
				jQuery(this).unbind(event);
				return (fn || data).apply( this, arguments);
			}, fn && data);
		});
	},
	unbind: function( type, fn ) {
		return this.each(function(){
			jQuery.event.remove( this, type, fn );
		});
	},
	trigger: function( type, data ) {
		return this.each(function(){
			jQuery.event.trigger( type, data, this );
		});
	},
	toggle: function() {
		// Save reference to arguments for access in closure
		var a = arguments;

		return this.click(function(e) {
			// Figure out which function to execute
			this.lastToggle = 0 == this.lastToggle ? 1 : 0;
			
			// Make sure that clicks stop
			e.preventDefault();
			
			// and execute the function
			return a[this.lastToggle].apply( this, [e] ) || false;
		});
	},
	hover: function(f,g) {
		
		// A private function for handling mouse 'hovering'
		function handleHover(e) {
			// Check if mouse(over|out) are still within the same parent element
			var p = e.relatedTarget;
	
			// Traverse up the tree
			while ( p && p != this ) try { p = p.parentNode; } catch(e) { p = this; };
			
			// If we actually just moused on to a sub-element, ignore it
			if ( p == this ) return false;
			
			// Execute the right function
			return (e.type == "mouseover" ? f : g).apply(this, [e]);
		}
		
		// Bind the function to the two event listeners
		return this.mouseover(handleHover).mouseout(handleHover);
	},
	ready: function(f) {
		// Attach the listeners
		bindReady();

		// If the DOM is already ready
		if ( jQuery.isReady )
			// Execute the function immediately
			f.apply( document, [jQuery] );
			
		// Otherwise, remember the function for later
		else
			// Add the function to the wait list
			jQuery.readyList.push( function() { return f.apply(this, [jQuery]); } );
	
		return this;
	}
});

jQuery.extend({
	/*
	 * All the code that makes DOM Ready work nicely.
	 */
	isReady: false,
	readyList: [],
	
	// Handle when the DOM is ready
	ready: function() {
		// Make sure that the DOM is not already loaded
		if ( !jQuery.isReady ) {
			// Remember that the DOM is ready
			jQuery.isReady = true;
			
			// If there are functions bound, to execute
			if ( jQuery.readyList ) {
				// Execute all of them
				jQuery.each( jQuery.readyList, function(){
					this.apply( document );
				});
				
				// Reset the list of functions
				jQuery.readyList = null;
			}
			// Remove event listener to avoid memory leak
			if ( jQuery.browser.mozilla || jQuery.browser.opera )
				document.removeEventListener( "DOMContentLoaded", jQuery.ready, false );
			
			// Remove script element used by IE hack
			if( !window.frames.length ) // don't remove if frames are present (#1187)
				jQuery(window).load(function(){ jQuery("#__ie_init").remove(); });
		}
	}
});

	jQuery.each( ("blur,focus,load,resize,scroll,unload,click,dblclick," +
		"mousedown,mouseup,mousemove,mouseover,mouseout,change,select," + 
		"submit,keydown,keypress,keyup,error").split(","), function(i,o){
		
		// Handle event binding
		jQuery.fn[o] = function(f){
			return f ? this.bind(o, f) : this.trigger(o);
		};
			
	});

var readyBound = false;

function bindReady(){
	if ( readyBound ) return;
	readyBound = true;

	// If Mozilla is used
	if ( jQuery.browser.mozilla || jQuery.browser.opera )
		// Use the handy event callback
		document.addEventListener( "DOMContentLoaded", jQuery.ready, false );
	
	// If IE is used, use the excellent hack by Matthias Miller
	// http://www.outofhanwell.com/blog/index.php?title=the_window_onload_problem_revisited
	else if ( jQuery.browser.msie ) {
	
		// Only works if you document.write() it
		document.write("<scr" + "ipt id=__ie_init defer=true " + 
			"src=//:><\/script>");
	
		// Use the defer script hack
		var script = document.getElementById("__ie_init");
		
		// script does not exist if jQuery is loaded dynamically
		if ( script ) 
			script.onreadystatechange = function() {
				if ( document.readyState != "complete" ) return;
				jQuery.ready();
			};
	
		// Clear from memory
		script = null;
	
	// If Safari  is used
	} else if ( jQuery.browser.safari )
		// Continually check to see if the document.readyState is valid
		jQuery.safariTimer = setInterval(function(){
			// loaded and complete are both valid states
			if ( document.readyState == "loaded" || 
				document.readyState == "complete" ) {
	
				// If either one are found, remove the timer
				clearInterval( jQuery.safariTimer );
				jQuery.safariTimer = null;
	
				// and execute any waiting functions
				jQuery.ready();
			}
		}, 10); 

	// A fallback to window.onload, that will always work
	jQuery.event.add( window, "load", jQuery.ready );
}
jQuery.fn.extend({
	// DEPRECATED
	loadIfModified: function( url, params, callback ) {
		this.load( url, params, callback, 1 );
	},
	load: function( url, params, callback, ifModified ) {
		if ( jQuery.isFunction( url ) )
			return this.bind("load", url);

		callback = callback || function(){};

		// Default to a GET request
		var type = "GET";

		// If the second parameter was provided
		if ( params )
			// If it's a function
			if ( jQuery.isFunction( params ) ) {
				// We assume that it's the callback
				callback = params;
				params = null;

			// Otherwise, build a param string
			} else {
				params = jQuery.param( params );
				type = "POST";
			}

		var self = this;

		// Request the remote document
		jQuery.ajax({
			url: url,
			type: type,
			data: params,
			ifModified: ifModified,
			complete: function(res, status){
				// If successful, inject the HTML into all the matched elements
				if ( status == "success" || !ifModified && status == "notmodified" )
					self.html(res.responseText);

				// Add delay to account for Safari's delay in globalEval
				setTimeout(function(){
					self.each( callback, [res.responseText, status, res] );
				}, 13);
			}
		});
		return this;
	},
	serialize: function() {
		return jQuery.param( this );
	},

	// DEPRECATED
	// This method no longer does anything - all script evaluation is
	// taken care of within the HTML injection methods.
	evalScripts: function(){}

});

// Attach a bunch of functions for handling common AJAX events

jQuery.each( "ajaxStart,ajaxStop,ajaxComplete,ajaxError,ajaxSuccess,ajaxSend".split(","), function(i,o){
	jQuery.fn[o] = function(f){
		return this.bind(o, f);
	};
});

jQuery.extend({
	get: function( url, data, callback, type, ifModified ) {
		// shift arguments if data argument was ommited
		if ( jQuery.isFunction( data ) ) {
			callback = data;
			data = null;
		}
		
		return jQuery.ajax({
			type: "GET",
			url: url,
			data: data,
			success: callback,
			dataType: type,
			ifModified: ifModified
		});
	},
	// DEPRECATED
	getIfModified: function( url, data, callback, type ) {
		return jQuery.get(url, data, callback, type, 1);
	},
	getScript: function( url, callback ) {
		return jQuery.get(url, null, callback, "script");
	},
	getJSON: function( url, data, callback ) {
		return jQuery.get(url, data, callback, "json");
	},
	post: function( url, data, callback, type ) {
		if ( jQuery.isFunction( data ) ) {
			callback = data;
			data = {};
		}

		return jQuery.ajax({
			type: "POST",
			url: url,
			data: data,
			success: callback,
			dataType: type
		});
	},
	// DEPRECATED
	ajaxTimeout: function( timeout ) {
		jQuery.ajaxSettings.timeout = timeout;
	},
	ajaxSetup: function( settings ) {
		jQuery.extend( jQuery.ajaxSettings, settings );
	},

	ajaxSettings: {
		global: true,
		type: "GET",
		timeout: 0,
		contentType: "application/x-www-form-urlencoded",
		processData: true,
		async: true,
		data: null
	},
	
	// Last-Modified header cache for next request
	lastModified: {},
	ajax: function( s ) {
		// Extend the settings, but re-extend 's' so that it can be
		// checked again later (in the test suite, specifically)
		s = jQuery.extend(true, s, jQuery.extend(true, {}, jQuery.ajaxSettings, s));

		// if data available
		if ( s.data ) {
			// convert data if not already a string
			if ( s.processData && typeof s.data != "string" )
				s.data = jQuery.param(s.data);

			// append data to url for get requests
			if ( s.type.toLowerCase() == "get" ) {
				// "?" + data or "&" + data (in case there are already params)
				s.url += (s.url.indexOf("?") > -1 ? "&" : "?") + s.data;

				// IE likes to send both get and post data, prevent this
				s.data = null;
			}
		}

		// Watch for a new set of requests
		if ( s.global && ! jQuery.active++ )
			jQuery.event.trigger( "ajaxStart" );

		var requestDone = false;

		// Create the request object; Microsoft failed to properly
		// implement the XMLHttpRequest in IE7, so we use the ActiveXObject when it is available
		var xml = window.ActiveXObject ? new ActiveXObject("Microsoft.XMLHTTP") : new XMLHttpRequest();

		// Open the socket
		xml.open(s.type, s.url, s.async);

		// Set the correct header, if data is being sent
		if ( s.data )
			xml.setRequestHeader("Content-Type", s.contentType);

		// Set the If-Modified-Since header, if ifModified mode.
		if ( s.ifModified )
			xml.setRequestHeader("If-Modified-Since",
				jQuery.lastModified[s.url] || "Thu, 01 Jan 1970 00:00:00 GMT" );

		// Set header so the called script knows that it's an XMLHttpRequest
		xml.setRequestHeader("X-Requested-With", "XMLHttpRequest");

		// Allow custom headers/mimetypes
		if( s.beforeSend )
			s.beforeSend(xml);
			
		if ( s.global )
		    jQuery.event.trigger("ajaxSend", [xml, s]);

		// Wait for a response to come back
		var onreadystatechange = function(isTimeout){
			// The transfer is complete and the data is available, or the request timed out
			if ( !requestDone && xml && (xml.readyState == 4 || isTimeout == "timeout") ) {
				requestDone = true;
				
				// clear poll interval
				if (ival) {
					clearInterval(ival);
					ival = null;
				}
				
				var status = isTimeout == "timeout" && "timeout" ||
					!jQuery.httpSuccess( xml ) && "error" ||
					s.ifModified && jQuery.httpNotModified( xml, s.url ) && "notmodified" ||
					"success";

				if ( status == "success" ) {
					// Watch for, and catch, XML document parse errors
					try {
						// process the data (runs the xml through httpData regardless of callback)
						var data = jQuery.httpData( xml, s.dataType );
					} catch(e) {
						status = "parsererror";
					}
				}

				// Make sure that the request was successful or notmodified
				if ( status == "success" ) {
					// Cache Last-Modified header, if ifModified mode.
					var modRes;
					try {
						modRes = xml.getResponseHeader("Last-Modified");
					} catch(e) {} // swallow exception thrown by FF if header is not available
	
					if ( s.ifModified && modRes )
						jQuery.lastModified[s.url] = modRes;
	
					// If a local callback was specified, fire it and pass it the data
					if ( s.success )
						s.success( data, status );
	
					// Fire the global callback
					if ( s.global )
						jQuery.event.trigger( "ajaxSuccess", [xml, s] );
				} else
					jQuery.handleError(s, xml, status);

				// The request was completed
				if( s.global )
					jQuery.event.trigger( "ajaxComplete", [xml, s] );

				// Handle the global AJAX counter
				if ( s.global && ! --jQuery.active )
					jQuery.event.trigger( "ajaxStop" );

				// Process result
				if ( s.complete )
					s.complete(xml, status);

				// Stop memory leaks
				if(s.async)
					xml = null;
			}
		};
		
		if ( s.async ) {
			// don't attach the handler to the request, just poll it instead
			var ival = setInterval(onreadystatechange, 13); 

			// Timeout checker
			if ( s.timeout > 0 )
				setTimeout(function(){
					// Check to see if the request is still happening
					if ( xml ) {
						// Cancel the request
						xml.abort();
	
						if( !requestDone )
							onreadystatechange( "timeout" );
					}
				}, s.timeout);
		}
			
		// Send the data
		try {
			xml.send(s.data);
		} catch(e) {
			jQuery.handleError(s, xml, null, e);
		}
		
		// firefox 1.5 doesn't fire statechange for sync requests
		if ( !s.async )
			onreadystatechange();
		
		// return XMLHttpRequest to allow aborting the request etc.
		return xml;
	},

	handleError: function( s, xml, status, e ) {
		// If a local callback was specified, fire it
		if ( s.error ) s.error( xml, status, e );

		// Fire the global callback
		if ( s.global )
			jQuery.event.trigger( "ajaxError", [xml, s, e] );
	},

	// Counter for holding the number of active queries
	active: 0,

	// Determines if an XMLHttpRequest was successful or not
	httpSuccess: function( r ) {
		try {
			return !r.status && location.protocol == "file:" ||
				( r.status >= 200 && r.status < 300 ) || r.status == 304 ||
				jQuery.browser.safari && r.status == undefined;
		} catch(e){}
		return false;
	},

	// Determines if an XMLHttpRequest returns NotModified
	httpNotModified: function( xml, url ) {
		try {
			var xmlRes = xml.getResponseHeader("Last-Modified");

			// Firefox always returns 200. check Last-Modified date
			return xml.status == 304 || xmlRes == jQuery.lastModified[url] ||
				jQuery.browser.safari && xml.status == undefined;
		} catch(e){}
		return false;
	},

	/* Get the data out of an XMLHttpRequest.
	 * Return parsed XML if content-type header is "xml" and type is "xml" or omitted,
	 * otherwise return plain text.
	 * (String) data - The type of data that you're expecting back,
	 * (e.g. "xml", "html", "script")
	 */
	httpData: function( r, type ) {
		var ct = r.getResponseHeader("content-type");
		var xml = type == "xml" || !type && ct && ct.indexOf("xml") >= 0;
		data = xml ? r.responseXML : r.responseText;

		if ( xml && data.documentElement.tagName == "parsererror" )
			throw "parsererror";

		// If the type is "script", eval it in global context
		if ( type == "script" )
			jQuery.globalEval( data );

		// Get the JavaScript object, if JSON is used.
		if ( type == "json" )
			data = eval("(" + data + ")");

		return data;
	},

	// Serialize an array of form elements or a set of
	// key/values into a query string
	param: function( a ) {
		var s = [];

		// If an array was passed in, assume that it is an array
		// of form elements
		if ( a.constructor == Array || a.jquery )
			// Serialize the form elements
			jQuery.each( a, function(){
				s.push( encodeURIComponent(this.name) + "=" + encodeURIComponent( this.value ) );
			});

		// Otherwise, assume that it's an object of key/value pairs
		else
			// Serialize the key/values
			for ( var j in a )
				// If the value is an array then the key names need to be repeated
				if ( a[j] && a[j].constructor == Array )
					jQuery.each( a[j], function(){
						s.push( encodeURIComponent(j) + "=" + encodeURIComponent( this ) );
					});
				else
					s.push( encodeURIComponent(j) + "=" + encodeURIComponent( a[j] ) );

		// Return the resulting serialization
		return s.join("&");
	}

});
jQuery.fn.extend({

	show: function(speed,callback){

		return speed ?
			this.animate({
				height: "show", width: "show", opacity: "show"
			}, speed, callback) :
			
			this.filter(":hidden").each(function(){
				this.style.display = this.oldblock ? this.oldblock : "";
				if ( jQuery.css(this,"display") == "none" )
					this.style.display = "block";
			}).end();
	},

	hide: function(speed,callback){
		return speed ?
			this.animate({
				height: "hide", width: "hide", opacity: "hide"
			}, speed, callback) :
			
			this.filter(":visible").each(function(){
				this.oldblock = this.oldblock || jQuery.css(this,"display");
				if ( this.oldblock == "none" )
					this.oldblock = "block";
				this.style.display = "none";
			}).end();
	},

	// Save the old toggle function
	_toggle: jQuery.fn.toggle,
	toggle: function( fn, fn2 ){
		return jQuery.isFunction(fn) && jQuery.isFunction(fn2) ?
			this._toggle( fn, fn2 ) :
			fn ?
				this.animate({
					height: "toggle", width: "toggle", opacity: "toggle"
				}, fn, fn2) :
				this.each(function(){
					jQuery(this)[ jQuery(this).is(":hidden") ? "show" : "hide" ]();
				});
	},
	slideDown: function(speed,callback){
		return this.animate({height: "show"}, speed, callback);
	},
	slideUp: function(speed,callback){
		return this.animate({height: "hide"}, speed, callback);
	},
	slideToggle: function(speed, callback){
		return this.animate({height: "toggle"}, speed, callback);
	},
	fadeIn: function(speed, callback){
		return this.animate({opacity: "show"}, speed, callback);
	},
	fadeOut: function(speed, callback){
		return this.animate({opacity: "hide"}, speed, callback);
	},
	fadeTo: function(speed,to,callback){
		return this.animate({opacity: to}, speed, callback);
	},
	animate: function( prop, speed, easing, callback ) {
		return this.queue(function(){
			var hidden = jQuery(this).is(":hidden"),
				opt = jQuery.speed(speed, easing, callback),
				self = this;
			
			for ( var p in prop ) {
				if ( prop[p] == "hide" && hidden || prop[p] == "show" && !hidden )
					return jQuery.isFunction(opt.complete) && opt.complete.apply(this);

				if ( p == "height" || p == "width" ) {
					// Store display property
					opt.display = jQuery.css(this, "display");

					// Make sure that nothing sneaks out
					opt.overflow = this.style.overflow;
				}
			}

			if ( opt.overflow != null )
				this.style.overflow = "hidden";

			this.curAnim = jQuery.extend({}, prop);
			
			jQuery.each( prop, function(name, val){
				var e = new jQuery.fx( self, opt, name );
				if ( val.constructor == Number )
					e.custom( e.cur() || 0, val );
				else
					e[ val == "toggle" ? hidden ? "show" : "hide" : val ]( prop );
			});

			// For JS strict compliance
			return true;
		});
	},
	queue: function(type,fn){
		if ( !fn ) {
			fn = type;
			type = "fx";
		}
	
		return this.each(function(){
			if ( !this.queue )
				this.queue = {};
	
			if ( !this.queue[type] )
				this.queue[type] = [];
	
			this.queue[type].push( fn );
		
			if ( this.queue[type].length == 1 )
				fn.apply(this);
		});
	}

});

jQuery.extend({
	
	speed: function(speed, easing, fn) {
		var opt = speed && speed.constructor == Object ? speed : {
			complete: fn || !fn && easing || 
				jQuery.isFunction( speed ) && speed,
			duration: speed,
			easing: fn && easing || easing && easing.constructor != Function && easing
		};

		opt.duration = (opt.duration && opt.duration.constructor == Number ? 
			opt.duration : 
			{ slow: 600, fast: 200 }[opt.duration]) || 400;
	
		// Queueing
		opt.old = opt.complete;
		opt.complete = function(){
			jQuery.dequeue(this, "fx");
			if ( jQuery.isFunction( opt.old ) )
				opt.old.apply( this );
		};
	
		return opt;
	},
	
	easing: {
		linear: function( p, n, firstNum, diff ) {
			return firstNum + diff * p;
		},
		swing: function( p, n, firstNum, diff ) {
			return ((-Math.cos(p*Math.PI)/2) + 0.5) * diff + firstNum;
		}
	},
	
	queue: {},
	
	dequeue: function(elem,type){
		type = type || "fx";
	
		if ( elem.queue && elem.queue[type] ) {
			// Remove self
			elem.queue[type].shift();
	
			// Get next function
			var f = elem.queue[type][0];
		
			if ( f ) f.apply( elem );
		}
	},

	timers: [],

	/*
	 * I originally wrote fx() as a clone of moo.fx and in the process
	 * of making it small in size the code became illegible to sane
	 * people. You've been warned.
	 */
	
	fx: function( elem, options, prop ){

		var z = this;

		// The styles
		var y = elem.style;
		
		// Simple function for setting a style value
		z.a = function(){
			if ( options.step )
				options.step.apply( elem, [ z.now ] );

			if ( prop == "opacity" )
				jQuery.attr(y, "opacity", z.now); // Let attr handle opacity
			else {
				y[prop] = parseInt(z.now) + "px";

				// Set display property to block for height/width animations
				if ( prop == "height" || prop == "width" )
					y.display = "block";
			}
		};

		// Figure out the maximum number to run to
		z.max = function(){
			return parseFloat( jQuery.css(elem,prop) );
		};

		// Get the current size
		z.cur = function(){
			var r = parseFloat( jQuery.curCSS(elem, prop) );
			return r && r > -10000 ? r : z.max();
		};

		// Start an animation from one number to another
		z.custom = function(from,to){
			z.startTime = (new Date()).getTime();
			z.now = from;
			z.a();

			jQuery.timers.push(function(){
				return z.step(from, to);
			});

			if ( jQuery.timers.length == 1 ) {
				var timer = setInterval(function(){
					var timers = jQuery.timers;
					
					for ( var i = 0; i < timers.length; i++ )
						if ( !timers[i]() )
							timers.splice(i--, 1);

					if ( !timers.length )
						clearInterval( timer );
				}, 13);
			}
		};

		// Simple 'show' function
		z.show = function(){
			if ( !elem.orig ) elem.orig = {};

			// Remember where we started, so that we can go back to it later
			elem.orig[prop] = jQuery.attr( elem.style, prop );

			options.show = true;

			// Begin the animation
			z.custom(0, this.cur());

			// Make sure that we start at a small width/height to avoid any
			// flash of content
			if ( prop != "opacity" )
				y[prop] = "1px";
			
			// Start by showing the element
			jQuery(elem).show();
		};

		// Simple 'hide' function
		z.hide = function(){
			if ( !elem.orig ) elem.orig = {};

			// Remember where we started, so that we can go back to it later
			elem.orig[prop] = jQuery.attr( elem.style, prop );

			options.hide = true;

			// Begin the animation
			z.custom(this.cur(), 0);
		};

		// Each step of an animation
		z.step = function(firstNum, lastNum){
			var t = (new Date()).getTime();

			if (t > options.duration + z.startTime) {
				z.now = lastNum;
				z.a();

				if (elem.curAnim) elem.curAnim[ prop ] = true;

				var done = true;
				for ( var i in elem.curAnim )
					if ( elem.curAnim[i] !== true )
						done = false;

				if ( done ) {
					if ( options.display != null ) {
						// Reset the overflow
						y.overflow = options.overflow;
					
						// Reset the display
						y.display = options.display;
						if ( jQuery.css(elem, "display") == "none" )
							y.display = "block";
					}

					// Hide the element if the "hide" operation was done
					if ( options.hide )
						y.display = "none";

					// Reset the properties, if the item has been hidden or shown
					if ( options.hide || options.show )
						for ( var p in elem.curAnim )
							jQuery.attr(y, p, elem.orig[p]);
				}

				// If a callback was provided, execute it
				if ( done && jQuery.isFunction( options.complete ) )
					// Execute the complete function
					options.complete.apply( elem );

				return false;
			} else {
				var n = t - this.startTime;
				// Figure out where in the animation we are and set the number
				var p = n / options.duration;
				
				// Perform the easing function, defaults to swing
				z.now = jQuery.easing[options.easing || (jQuery.easing.swing ? "swing" : "linear")](p, n, firstNum, (lastNum-firstNum), options.duration);

				// Perform the next step of the animation
				z.a();
			}

			return true;
		};
	
	}
});
})();
/**
 * Cookie plugin
 *
 * Copyright (c) 2006 Klaus Hartl (stilbuero.de)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 */

/**
 * Create a cookie with the given name and value and other optional parameters.
 *
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Set the value of a cookie.
 * @example $.cookie('the_cookie', 'the_value', {expires: 7, path: '/', domain: 'jquery.com', secure: true});
 * @desc Create a cookie with all available options.
 * @example $.cookie('the_cookie', 'the_value');
 * @desc Create a session cookie.
 * @example $.cookie('the_cookie', '', {expires: -1});
 * @desc Delete a cookie by setting a date in the past.
 *
 * @param String name The name of the cookie.
 * @param String value The value of the cookie.
 * @param Object options An object literal containing key/value pairs to provide optional cookie attributes.
 * @option Number|Date expires Either an integer specifying the expiration date from now on in days or a Date object.
 *                             If a negative value is specified (e.g. a date in the past), the cookie will be deleted.
 *                             If set to null or omitted, the cookie will be a session cookie and will not be retained
 *                             when the the browser exits.
 * @option String path The value of the path atribute of the cookie (default: path of page that created the cookie).
 * @option String domain The value of the domain attribute of the cookie (default: domain of page that created the cookie).
 * @option Boolean secure If true, the secure attribute of the cookie will be set and the cookie transmission will
 *                        require a secure protocol (like HTTPS).
 * @type undefined
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */

/**
 * Get the value of a cookie with the given name.
 *
 * @example $.cookie('the_cookie');
 * @desc Get the value of a cookie.
 *
 * @param String name The name of the cookie.
 * @return The value of the cookie.
 * @type String
 *
 * @name $.cookie
 * @cat Plugins/Cookie
 * @author Klaus Hartl/klaus.hartl@stilbuero.de
 */
jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toGMTString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toGMTString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};
/**
* hoverIntent is similar to jQuery's built-in "hover" function except that
* instead of firing the onMouseOver event immediately, hoverIntent checks
* to see if the user's mouse has slowed down (beneath the sensitivity
* threshold) before firing the onMouseOver event.
* 
* hoverIntent r5 // 2007.03.27 // jQuery 1.1.2
* <http://cherne.net/brian/resources/jquery.hoverIntent.html>
* 
* hoverIntent is currently available for use in all personal or commercial 
* projects under both MIT and GPL licenses. This means that you can choose 
* the license that best suits your project, and use it accordingly.
* 
* // basic usage (just like .hover) receives onMouseOver and onMouseOut functions
* $("ul li").hoverIntent( showNav , hideNav );
* 
* // advanced usage receives configuration object only
* $("ul li").hoverIntent({
*	sensitivity: 2, // number = sensitivity threshold (must be 1 or higher)
*	interval: 50,   // number = milliseconds of polling interval
*	over: showNav,  // function = onMouseOver callback (required)
*	timeout: 100,   // number = milliseconds delay before onMouseOut function call
*	out: hideNav    // function = onMouseOut callback (required)
* });
* 
* @param  f  onMouseOver function || An object with configuration options
* @param  g  onMouseOut function  || Nothing (use configuration options object)
* @return    The object (aka "this") that called hoverIntent, and the event object
* @author    Brian Cherne <brian@cherne.net>
*/
(function($) {
	$.fn.hoverIntent = function(f,g) {
		// default configuration options
		var cfg = {
			sensitivity: 7,
			interval: 100,
			timeout: 0
		};
		// override configuration options with user supplied object
		cfg = $.extend(cfg, g ? { over: f, out: g } : f );

		// instantiate variables
		// cX, cY = current X and Y position of mouse, updated by mousemove event
		// pX, pY = previous X and Y position of mouse, set by mouseover and polling interval
		var cX, cY, pX, pY;

		// A private function for getting mouse position
		var track = function(ev) {
			cX = ev.pageX;
			cY = ev.pageY;
		};

		// A private function for comparing current and previous mouse position
		var compare = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			// compare mouse positions to see if they've crossed the threshold
			if ( ( Math.abs(pX-cX) + Math.abs(pY-cY) ) < cfg.sensitivity ) {
				$(ob).unbind("mousemove",track);
				// set hoverIntent state to true (so mouseOut can be called)
				ob.hoverIntent_s = 1;
				return cfg.over.apply(ob,[ev]);
			} else {
				// set previous coordinates for next time
				pX = cX; pY = cY;
				// use self-calling timeout, guarantees intervals are spaced out properly (avoids JavaScript timer bugs)
				ob.hoverIntent_t = setTimeout( function(){compare(ev, ob);} , cfg.interval );
			}
		};

		// A private function for delaying the mouseOut function
		var delay = function(ev,ob) {
			ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t);
			ob.hoverIntent_s = 0;
			return cfg.out.apply(ob,[ev]);
		};

		// A private function for handling mouse 'hovering'
		var handleHover = function(e) {
			// next three lines copied from jQuery.hover, ignore children onMouseOver/onMouseOut
			var p = (e.type == "mouseover" ? e.fromElement : e.toElement) || e.relatedTarget;
			while ( p && p != this ) { try { p = p.parentNode; } catch(e) { p = this; } }
			if ( p == this ) { return false; }

			// copy objects to be passed into t (required for event object to be passed in IE)
			var ev = jQuery.extend({},e);
			var ob = this;

			// cancel hoverIntent timer if it exists
			if (ob.hoverIntent_t) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); }

			// else e.type == "onmouseover"
			if (e.type == "mouseover") {
				// set "previous" X and Y position based on initial entry point
				pX = ev.pageX; pY = ev.pageY;
				// update "current" X and Y position based on mousemove
				$(ob).bind("mousemove",track);
				// start polling interval (self-calling timeout) to compare mouse coordinates over time
				if (ob.hoverIntent_s != 1) { ob.hoverIntent_t = setTimeout( function(){compare(ev,ob);} , cfg.interval );}

			// else e.type == "onmouseout"
			} else {
				// unbind expensive mousemove event
				$(ob).unbind("mousemove",track);
				// if hoverIntent state is true, then call the mouseOut function after the specified delay
				if (ob.hoverIntent_s == 1) { ob.hoverIntent_t = setTimeout( function(){delay(ev,ob);} , cfg.timeout );}
			}
		};

		// bind the function to the two event listeners
		return this.mouseover(handleHover).mouseout(handleHover);
	};
})(jQuery);
/**
 * jQBrowser? v1.0.1 - Extend jQuery's browser detection capabilities and implement CSS browser selectors
 *   * http://www.alterform.com/resources/jqbrowser-2
 *
 * Built on the shoulders of (and stolen from :) ) giants:
 *   * John Resig <http://jquery.com/>
 *   * Peter-Paul Koch <http://www.quirksmode.org/?/js/detect.html>
 *	 * Dave Cardwell <http://davecardwell.co.uk/>
 *	 * Rafael Lima <http://rafael.adm.br/css_browser_selector/>
 *
 * Copyright (c) 2006 Nate Cavanaugh, dual licensed under the MIT and GPL
 * licenses:
 *   * http://www.opensource.org/licenses/mit-license.php
 *   * http://www.gnu.org/licenses/gpl.txt
 */

var jQBrowser2 = function() {
	var add_selectors = true;
    /**
     * The following functions and attributes form the internal methods and
     * state of the jQBrowser? plugin.  See the relevant function definition
     * later in the source for further information.
     *
     * Private.browser
     * Private.version
     * Private.OS
     *
     * Private.aol
     * Private.camino
     * Private.firefox
     * Private.flock
     * Private.icab
     * Private.konqueror
     * Private.mozilla
     * Private.msie
     * Private.netscape
     * Private.opera
     * Private.safari
     *
     * Private.linux
     * Private.mac
     * Private.win
     */
    var Private = {
        // Initially set to 'Unknown', if detected each of these properties will
        // be updated.
          'browser': 'Unknown',
          'version': {
              'number': undefined,
              'string': 'Unknown'
          },
               'OS': 'Unknown',

        // Initially set to false, if detected one of the following browsers
        // will be updated.
              'aol': false,
           'camino': false,
          'firefox': false,
            'flock': false,
             'icab': false,
        'konqueror': false,
          'mozilla': false,
             'msie': false,
         'netscape': false,
            'opera': false,
           'safari': false,

        // Initially set to false, if detected one of the following operating
        // systems will be updated.
           'iphone': false, 
			'linux': false,
              'mac': false,
              'win': false
    };
	

    /**
     * Loop over the items in 'data' trying to find a browser match with the
     * test in data[i].browser().  Once found, attempt to determine the
     * browser version.
     *
     *       'name': A string containing the full name of the browser.
     * 'identifier': By default this is a lowercase version of 'name', but
     *               this can be overwritten by explicitly defining an
     *               'identifier'.
     *    'browser': A function that returns a boolean value indicating
     *               whether or not the given browser is detected.
     *    'version': An optional function that overwrites the default version
     *               testing.  Must return the result of a .match().
     *
     * Please note that the order of the data array is important, as some
     * browsers contain details of others in their navigator.userAgent string.
     * For example, Flock's contains 'Firefox' so much come before Firefox's
     * test to avoid false positives.
     */
    for( var  i = 0,                    // counter
             ua = navigator.userAgent,  // the navigator's user agent string
             ve = navigator.vendor,     // the navigator's vendor string
           data = [                     // browser tests and data
                { // Safari <http://www.apple.com/safari/>
                          'name': 'Safari',
                       'browser':  /Apple/.test(ve)
                },
                { // Opera <http://www.opera.com/>
                          'name': 'Opera',
                       'browser': window.opera != undefined
                },
                { // iCab <http://www.icab.de/>
                          'name': 'iCab',
                       'browser': /iCab/.test(ve)
                },
                { // Konqueror <http://www.konqueror.org/>
                          'name': 'Konqueror',
                       'browser': /KDE/.test(ve)
                },
                { // AOL Explorer <http://downloads.channel.aol.com/browser>
                    'identifier': 'aol',
                          'name': 'AOL Explorer',
                       'browser': /America Online Browser/.test(ua),
                       'version': ua.match(/rev(\d+(?:\.\d+)+)/)
                },
                { // Flock <http://www.flock.com/>
                          'name': 'Flock',
                       'browser': /Flock/.test(ua)
                },
                { // Camino <http://www.caminobrowser.org/>
                          'name': 'Camino',
                       'browser': /Camino/.test(ve)
                },
                { // Firefox <http://www.mozilla.com/firefox/>
                          'name': 'Firefox',
                       'browser': /Firefox/.test(ua)
                },
                { // Netscape <http://browser.netscape.com/>
                          'name': 'Netscape',
                       'browser': /Netscape/.test(ua)
                },
                { // Internet Explorer <http://www.microsoft.com/windows/ie/>
                  //                   <http://www.microsoft.com/mac/ie/>
                    'identifier': 'msie',
                          'name': 'Internet Explorer',
                       'browser': /MSIE/.test(ua),
                       'version': ua.match(
                                          /MSIE (\d+(?:\.\d+)+(?:b\d*)?)/
                                      )
                },
                { // Mozilla <http://www.mozilla.org/products/mozilla1.x/>
                          'name': 'Mozilla',
                       'browser': /Gecko|Mozilla/.test(ua),
                       'version': ua.match(/rv:(\d+(?:\.\d+)+)/)
                 }
             ];
         i < data.length;
         i++
    ) {
        if( data[i].browser ) { // we have a match
            // If the identifier is not explicitly set, use a lowercase
            // version of the given name.
            var identifier = data[i].identifier ? data[i].identifier
                                                : data[i].name.toLowerCase();

            // Make a note that this browser was detected.
            Private[ identifier ] = true;

            // $.browser.browser() will now return the correct browser.
            Private.browser = data[i].name;

            var result;
            if( data[i].version != undefined && (result = data[i].version) ) {
                // Use the explicitly set test for browser version.
                Private.version.string = result[1];
                Private.version.number = parseFloat( result[1] );
            } else {
                // Otherwise use the default test which searches for the
                // version number after the browser name in the user agent
                // string.
                var re = new RegExp(
                    data[i].name + '(?:\\s|\\/)(\\d+(?:\\.\\d+)+(?:(?:a|b)\\d*)?)'
                );

                result = ua.match(re);
                if( result != undefined ) {
                    Private.version.string = result[1];
                    Private.version.number = parseFloat( result[1] );
                }
            }
			
			//If we're on a Gecko based browser, mark Private.mozilla as true
			if (/firefox|camino|flock|netscape/i.test(Private.browser)) {
				Private.mozilla = true;
			}

            // Once we've detected the browser there is no need to check the
            // others.
            break;
        }
		
    };



    /**
     * Loop over the items in 'data' trying to find a operating system match
     * with the test in data[i].os().
     *
     *       'name': A string containing the full name of the operating
     *               system.
     * 'identifier': By default this is a lowercase version of 'name', but
     *               this can be overwritten by explicitly defining an
     *               'identifier'.
     *         'OS': A function that returns a boolean value indicating
     *               whether or not the given operating system is detected.
     */
    for( var  i = 0,                  // counter
             pl = navigator.platform, // the navigator's platform string
           data = [                   // OS data and tests
                { // Microsoft Windows <http://www.microsoft.com/windows/>
                    'identifier': 'win',
                          'name': 'Windows',
                            'OS': /Win/.test(pl)
                },
                { // Apple Mac OS <http://www.apple.com/macos/>
                          'name': 'Mac',
                            'OS': /Mac/.test(pl)
                },
                { // Linux <http://www.linux.org/>
                          'name': 'Linux',
                            'OS': /Linux/.test(pl)
                },
				{ // iPhone <http://www.apple.com/iphone>
						  'name': 'iPhone',
						    'OS': /iPhone/.test(pl) 
				}
           ];
       i < data.length;
       i++
    ) {
        if( data[i].OS ) { // we have a match
            // If the identifier is not explicitly set, use a lowercase
            // version of the given name.
            var identifier = data[i].identifier ? data[i].identifier
                                                : data[i].name.toLowerCase();

            // Make a note that the OS was detected.
            Private[ identifier ] = true;

            // $.browser.OS() will now return the correct OS.
            Private.OS = data[i].name;

            // Once we've detected the browser there is no need to check the
            // others.
            break;
        }
    };
	/**
     * The following functions and attributes form the Public interface of the
     * jQBrowser? plugin, accessed externally through the $.browser object.
     * See the relevant function definition later in the source for further
     * information.
     *
     * $.browser.browser
     * $.browser.version.number()
     * $.browser.version.string()
	 * * * version.string() and version.number both take arguments ( best to use 'round'), to round out the version number
     * $.browser.OS
     *
     * $.browser.aol
     * $.browser.camino
     * $.browser.firefox
     * $.browser.flock
     * $.browser.icab
     * $.browser.konqueror
     * $.browser.mozilla
     * $.browser.msie
     * $.browser.netscape
     * $.browser.opera
     * $.browser.safari
     *
     * $.browser.linux
     * $.browser.mac
     * $.browser.win
     */
	var Public = {
        // The current browser, its version as a number or a string, and the
        // operating system its running on.
          'browser': Private.browser,
          'version': {
              'number': function() { return !arguments.length ? Private.version.number : Math.floor(Private.version.number); },
              'string': function() { return !arguments.length ? Private.version.string : this.number('round').toString(); }
          },
               'OS': Private.OS,

        // A boolean value indicating whether or not the given browser was
        // detected.
              'aol': Private.aol,
           'camino': Private.camino,
          'firefox': Private.firefox,
            'flock': Private.flock,
             'icab': Private.icab,
        'konqueror': Private.konqueror,
          'mozilla': Private.mozilla,
             'msie': Private.msie,
         'netscape': Private.netscape,
            'opera': Private.opera,
           'safari': Private.safari,

        // A boolean value indicating whether or not the given OS was
        // detected.
		   'iphone': Private.iphone, 
            'linux': Private.linux,
              'mac': Private.mac,
              'win': Private.win
    };
	
	jQuery.browser = Public;
	// Browser selectors
	if(!add_selectors){return;}
	var bn = jQuery.browser.browser.toLowerCase();
	var bv = jQuery.browser.version.string('round');
	var b = jQuery.browser.msie // IE
						? 'ie ie'+jQuery.browser.version.string('round')
						: (jQuery.browser.mozilla) // Gecko
							? 'gecko '+ bn + bv + ' ' + bn
								: (jQuery.browser.opera) // Opera
									? 'opera ' + bn + bv
										: (jQuery.browser.safari) // Safari
											? 'safari ' + bn + bv
												: jQuery.browser.konqueror // Konqueror
													? 'konqueror ' + bn + bv
														: jQuery.browser.icab // iCab
															? 'icab ' + bn + bv
																: jQuery.browser.aol // AOL
																 	? 'aol ' + bn + bv
																	: '',
		os=jQuery.browser.linux?'linux':jQuery.browser.iphone?'iphone':jQuery.browser.mac?'mac':jQuery.browser.win?'win':'';
		jQuery('html').addClass(b).addClass(os).addClass('js');
}();



/*
Class: Class
	The base class object of the <http://mootools.net> framework.
	
Arguments:
	properties - the collection of properties that apply to the class. Creates a new class, its initialize method will fire upon class instantiation.
	
Example:
	>var Cat = new Class({
	>	initialize: function(name){
	>		this.name = name;
	>	}
	>});
	>var myCat = new Cat('Micia');
	>alert myCat.name; //alerts 'Micia'
*/

var Class = function(properties){
	var klass = function(){
		for (var p in this){
			if (this[p]) this[p]._proto_ = this;
		}
		if (arguments[0] != 'noinit' && this.initialize) return this.initialize.apply(this, arguments);
	};
	klass.extend = this.extend;
	klass.implement = this.implement;
	klass.prototype = properties;
	return klass;
};

/*
Property: empty
	Returns an empty function
*/

Class.empty = function(){};

/*
Property: create
	same as new Class. see <Class>
*/

Class.create = function(properties){
	return new Class(properties);
};

Class.prototype = {

	/*
	Property: extend
		Returns the copy of the Class extended with the passed in properties.
		
	Arguments:
		properties - the properties to add to the base class in this new Class.
		
	Example:
		>var Animal = new Class({
		>	initialize: function(age){
		>		this.age = age;
		>	}
		>});
		>var Cat = Animal.extend({
		>	initialize: function(name, age){
		>		this.parent(age); //will call the previous initialize;
		>		this.name = name;
		>	}
		>});
		>var myCat = new Cat('Micia', 20);
		>alert myCat.name; //alerts 'Micia'
		>alert myCat.age; //alerts 20
	*/

	extend: function(properties){
		var pr0t0typ3 = new this('noinit');
		for (var property in properties){
			var previous = pr0t0typ3[property];
			var current = properties[property];
			if (previous && previous != current) current = previous.parentize(current) || current;
			pr0t0typ3[property] = current;
		}
		return new Class(pr0t0typ3);
	},
	
	/*	
	Property: implement
		Implements the passed in properties to the base Class prototypes, altering the base class, unlike <Class.extend>.

	Arguments:
		properties - the properties to add to the base class.
		
	Example:
		>var Animal = new Class({
		>	initialize: function(age){
		>		this.age = age;
		>	}
		>});
		>Animal.implement({
		>	setName: function(name){
		>		this.name = name
		>	}
		>});
		>var myAnimal = new Animal(20);
		>myAnimal.setName('Micia');
		>alert(myAnimal.name); //alerts 'Micia'
	*/
	
	implement: function(properties){
		for (var property in properties) this.prototype[property] = properties[property];
	}

};

/*
Function: Object.Native
	Will add a .extend method to the objects passed as a parameter, equivalent to <Class.implement>

Arguments:
	a number of classes/native javascript objects

*/

Object.Native = function(){
	for (var i = 0; i < arguments.length; i++) arguments[i].extend = Class.prototype.implement;
};

new Object.Native(Function, Array, String, Number);

Function.extend({

	parentize: function(current){
		var previous = this;
		return function(){
			this.parent = previous;
			return current.apply(this, arguments);
		};
	}

});
/**
 * SWFObject v1.5: Flash Player detection and embed - http://blog.deconcept.com/swfobject/
 *
 * SWFObject is (c) 2007 Geoff Stearns and is released under the MIT License:
 * http://www.opensource.org/licenses/mit-license.php
 *
 */
if(typeof deconcept=="undefined"){var deconcept=new Object();}if(typeof deconcept.util=="undefined"){deconcept.util=new Object();}if(typeof deconcept.SWFObjectUtil=="undefined"){deconcept.SWFObjectUtil=new Object();}deconcept.SWFObject=function(_1,id,w,h,_5,c,_7,_8,_9,_a){if(!document.getElementById){return;}this.DETECT_KEY=_a?_a:"detectflash";this.skipDetect=deconcept.util.getRequestParameter(this.DETECT_KEY);this.params=new Object();this.variables=new Object();this.attributes=new Array();if(_1){this.setAttribute("swf",_1);}if(id){this.setAttribute("id",id);}if(w){this.setAttribute("width",w);}if(h){this.setAttribute("height",h);}if(_5){this.setAttribute("version",new deconcept.PlayerVersion(_5.toString().split(".")));}this.installedVer=deconcept.SWFObjectUtil.getPlayerVersion();if(!window.opera&&document.all&&this.installedVer.major>7){deconcept.SWFObject.doPrepUnload=true;}if(c){this.addParam("bgcolor",c);}var q=_7?_7:"high";this.addParam("quality",q);this.setAttribute("useExpressInstall",false);this.setAttribute("doExpressInstall",false);var _c=(_8)?_8:window.location;this.setAttribute("xiRedirectUrl",_c);this.setAttribute("redirectUrl","");if(_9){this.setAttribute("redirectUrl",_9);}};deconcept.SWFObject.prototype={useExpressInstall:function(_d){this.xiSWFPath=!_d?"expressinstall.swf":_d;this.setAttribute("useExpressInstall",true);},setAttribute:function(_e,_f){this.attributes[_e]=_f;},getAttribute:function(_10){return this.attributes[_10];},addParam:function(_11,_12){this.params[_11]=_12;},getParams:function(){return this.params;},addVariable:function(_13,_14){this.variables[_13]=_14;},getVariable:function(_15){return this.variables[_15];},getVariables:function(){return this.variables;},getVariablePairs:function(){var _16=new Array();var key;var _18=this.getVariables();for(key in _18){_16[_16.length]=key+"="+_18[key];}return _16;},getSWFHTML:function(){var _19="";if(navigator.plugins&&navigator.mimeTypes&&navigator.mimeTypes.length){if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","PlugIn");this.setAttribute("swf",this.xiSWFPath);}_19="<embed type=\"application/x-shockwave-flash\" src=\""+this.getAttribute("swf")+"\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\" style=\""+this.getAttribute("style")+"\"";_19+=" id=\""+this.getAttribute("id")+"\" name=\""+this.getAttribute("id")+"\" ";var _1a=this.getParams();for(var key in _1a){_19+=[key]+"=\""+_1a[key]+"\" ";}var _1c=this.getVariablePairs().join("&");if(_1c.length>0){_19+="flashvars=\""+_1c+"\"";}_19+="/>";}else{if(this.getAttribute("doExpressInstall")){this.addVariable("MMplayerType","ActiveX");this.setAttribute("swf",this.xiSWFPath);}_19="<object id=\""+this.getAttribute("id")+"\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\""+this.getAttribute("width")+"\" height=\""+this.getAttribute("height")+"\" style=\""+this.getAttribute("style")+"\">";_19+="<param name=\"movie\" value=\""+this.getAttribute("swf")+"\" />";var _1d=this.getParams();for(var key in _1d){_19+="<param name=\""+key+"\" value=\""+_1d[key]+"\" />";}var _1f=this.getVariablePairs().join("&");if(_1f.length>0){_19+="<param name=\"flashvars\" value=\""+_1f+"\" />";}_19+="</object>";}return _19;},write:function(_20){if(this.getAttribute("useExpressInstall")){var _21=new deconcept.PlayerVersion([6,0,65]);if(this.installedVer.versionIsValid(_21)&&!this.installedVer.versionIsValid(this.getAttribute("version"))){this.setAttribute("doExpressInstall",true);this.addVariable("MMredirectURL",escape(this.getAttribute("xiRedirectUrl")));document.title=document.title.slice(0,47)+" - Flash Player Installation";this.addVariable("MMdoctitle",document.title);}}if(this.skipDetect||this.getAttribute("doExpressInstall")||this.installedVer.versionIsValid(this.getAttribute("version"))){var n=(typeof _20=="string")?document.getElementById(_20):_20;n.innerHTML=this.getSWFHTML();return true;}else{if(this.getAttribute("redirectUrl")!=""){document.location.replace(this.getAttribute("redirectUrl"));}}return false;}};deconcept.SWFObjectUtil.getPlayerVersion=function(){var _23=new deconcept.PlayerVersion([0,0,0]);if(navigator.plugins&&navigator.mimeTypes.length){var x=navigator.plugins["Shockwave Flash"];if(x&&x.description){_23=new deconcept.PlayerVersion(x.description.replace(/([a-zA-Z]|\s)+/,"").replace(/(\s+r|\s+b[0-9]+)/,".").split("."));}}else{if(navigator.userAgent&&navigator.userAgent.indexOf("Windows CE")>=0){var axo=1;var _26=3;while(axo){try{_26++;axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash."+_26);_23=new deconcept.PlayerVersion([_26,0,0]);}catch(e){axo=null;}}}else{try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");}catch(e){try{var axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");_23=new deconcept.PlayerVersion([6,0,21]);axo.AllowScriptAccess="always";}catch(e){if(_23.major==6){return _23;}}try{axo=new ActiveXObject("ShockwaveFlash.ShockwaveFlash");}catch(e){}}if(axo!=null){_23=new deconcept.PlayerVersion(axo.GetVariable("$version").split(" ")[1].split(","));}}}return _23;};deconcept.PlayerVersion=function(_29){this.major=_29[0]!=null?parseInt(_29[0]):0;this.minor=_29[1]!=null?parseInt(_29[1]):0;this.rev=_29[2]!=null?parseInt(_29[2]):0;};deconcept.PlayerVersion.prototype.versionIsValid=function(fv){if(this.major<fv.major){return false;}if(this.major>fv.major){return true;}if(this.minor<fv.minor){return false;}if(this.minor>fv.minor){return true;}if(this.rev<fv.rev){return false;}return true;};deconcept.util={getRequestParameter:function(_2b){var q=document.location.search||document.location.hash;if(_2b==null){return q;}if(q){var _2d=q.substring(1).split("&");for(var i=0;i<_2d.length;i++){if(_2d[i].substring(0,_2d[i].indexOf("="))==_2b){return _2d[i].substring((_2d[i].indexOf("=")+1));}}}return "";}};deconcept.SWFObjectUtil.cleanupSWFs=function(){var _2f=document.getElementsByTagName("OBJECT");for(var i=_2f.length-1;i>=0;i--){_2f[i].style.display="none";for(var x in _2f[i]){if(typeof _2f[i][x]=="function"){_2f[i][x]=function(){};}}}};if(deconcept.SWFObject.doPrepUnload){if(!deconcept.unloadSet){deconcept.SWFObjectUtil.prepUnload=function(){__flash_unloadHandler=function(){};__flash_savedUnloadHandler=function(){};window.attachEvent("onunload",deconcept.SWFObjectUtil.cleanupSWFs);};window.attachEvent("onbeforeunload",deconcept.SWFObjectUtil.prepUnload);deconcept.unloadSet=true;}}if(!document.getElementById&&document.all){document.getElementById=function(id){return document.all[id];};}var getQueryParamValue=deconcept.util.getRequestParameter;var FlashObject=deconcept.SWFObject;var SWFObject=deconcept.SWFObject;
$ = null;

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

jQuery.ajaxOld = jQuery.ajax;

jQuery.ajax = function(options) {
	options.url = Liferay.Util.getURLWithSessionId(options.url);

	return jQuery.ajaxOld(options);
};

jQuery.ajaxSetup(
	{
		type: 'POST'
	}
);

Liferay = function() {
	var $ = jQuery;

	return {};
}();

Liferay.Service = {
	actionUrl: themeDisplay.getPathMain() + "/portal/json_service",

	tunnelUrl: themeDisplay.getPathContext() + "/tunnel-web/secure/json",

	classNameSuffix: "ServiceJSON",

	ajax: function(params, callback) {
		var instance = this;

		var serviceUrl = instance.actionUrl;

		if (Liferay.ServiceAuth.header) {
			serviceUrl = instance.tunnelUrl;
		}

		params.serviceParameters = Liferay.Service.getParameters(params);

		if (callback) {
			jQuery.ajax(
				{
					type: 'GET',
					url: serviceUrl,
					data: params,
					dataType: 'json',
					beforeSend: function(xHR) {
						if (Liferay.ServiceAuth.header) {
							xHR.setRequestHeader('Authorization', Liferay.ServiceAuth.header);
						}
					},
					success: callback
				}
			);
		}
		else {
			var xHR = jQuery.ajax(
				{
					url: serviceUrl,
					data: params,
					dataType: 'json',
					async: false
				}
			);

			return eval("(" + xHR.responseText + ")");
		}
	},

	getParameters: function(params) {
		var serviceParameters = "";

		for (var key in params) {
			if ((key != "serviceClassName") && (key != "serviceMethodName")) {
				serviceParameters += key + ",";
			}
		}

		if (Liferay.Util.endsWith(serviceParameters, ",")) {
			serviceParameters = serviceParameters.substring(0, serviceParameters.length - 1);
		}

		return serviceParameters;
	}
};

Liferay.ServiceAuth = {
	header: null,

	setHeader: function(userId, password) {
		var instance = this;

		instance.header = "Basic " + Liferay.Base64.encode(userId + ':' + password);
	}
};

/*
http://www.webtoolkit.info/
*/
Liferay.Base64 = {
	encode: function(input) {
		var instance = this;

		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		input = instance._utf8Encode(input);

		while (i < input.length) {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			}
			else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
		}

		return output;
	},

	decode: function(input) {
		var instance = this;

		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		while (i < input.length) {
			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + String.fromCharCode(chr1);

			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}

			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}
		}

		output = instance._utf8Decode(output);

		return output;
	},

	_utf8Encode: function(string) {
		string = string.replace(/\r\n/g,"\n");

		var utftext = "";

		for (var n = 0; n < string.length; n++) {
			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	_utf8Decode: function(utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;

		while (i < utftext.length) {
			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}
		}

		return string;
	},

	_keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
};

jQuery.fn.exactHeight = jQuery.fn.height;
jQuery.fn.exactWidth = jQuery.fn.width;

jQuery.each(
	['height', 'width'],
	function(i, n) {
	jQuery.fn[n] = function(h) {
		return h == undefined ?
			(this.length ? (n == 'height' ? this[0].offsetHeight : this[0].offsetWidth) : null) :
			this.css(n, h.constructor == String ? h : h + "px");
	};
});
Liferay.Browser = {

	init: function() {
		var instance = this;

		var version = instance.version();
		var exactVersion = instance.version(true);

		instance._browserVars = {
			agent: '',

			is_ie: false,
			is_ie_4: false,
			is_ie_5: false,
			is_ie_5_5: false,
			is_ie_5_up: false,
			is_ie_6: false,
			is_ie_7: false,

			is_mozilla: false,
			is_mozilla_1_3_up: false,

			is_ns_4: false,

			is_rtf: false,

			is_safari: false,
			is_opera: false
		};

		instance._browserVars.agent = instance.browser().toLowerCase();

		instance._browserVars.is_ie = jQuery.browser.msie;
		instance._browserVars.is_ie_4 = (instance.is_ie && version == 4);
		instance._browserVars.is_ie_5 = (instance.is_ie && version == 5);
		instance._browserVars.is_ie_5_5 = (instance.is_ie && exactVersion == 5.5);
		instance._browserVars.is_ie_5_up = (instance.is_ie && version >= 5);
		instance._browserVars.is_ie_6 = (instance.is_ie && version == 6);
		instance._browserVars.is_ie_7 = (instance.is_ie && version == 7);

		instance._browserVars.is_mozilla = (jQuery.browser.mozilla);
		instance._browserVars.is_mozilla_1_3_up = (instance.is_mozilla && exactVersion > 1.3);

		instance._browserVars.is_ns_4 = (jQuery.browser.netscape && version == 4);

		instance._browserVars.is_rtf = (instance.is_ie_5_5_up || instance.is_mozilla_1_3_up);

		instance._browserVars.is_safari = jQuery.browser.safari;
		instance._browserVars.is_opera = jQuery.browser.opera;

		jQuery.extend(instance, instance._browserVars);
	},

	browser: function() {
		var instance = this;

		return jQuery.browser.browser;
	},

	compat: function() {
		var instance = this;

		for (var i in instance._browserVars){
			if (!window[i]) {
				window[i] = instance._browserVars[i];
			}
		}
	},

	version: function(exact) {
		var instance = this;

		if (!exact) {
			return jQuery.browser.version.string(true);
		}
		else {
			return jQuery.browser.version.string();
		}
	},

	_browserVars: {}
};

jQuery(
	function() {
		Liferay.Browser.init();
		//Uncomment the following line if you wish to have the original global variables set (eg. is_ie6, is_mozilla, etc)
		// Liferay.Browser.compat();
	}
);
Liferay.Util = {
	submitCountdown: 0,

	actsAsAspect: function(object) {
		object.yield = null;
		object.rv = {};

		object.before = function(method, f) {
			var original = eval("this." + method);

			this[method] = function() {
				f.apply(this, arguments);

				return original.apply(this, arguments);
			};
		};

		object.after = function(method, f) {
			var original = eval("this." + method);

			this[method] = function() {
				this.rv[method] = original.apply(this, arguments);

				return f.apply(this, arguments);
			};
		};

		object.around = function(method, f) {
			var original = eval("this." + method);

			this[method] = function() {
				this.yield = original;

				return f.apply(this, arguments);
			};
		};
	},

	addInputFocus: function(el) {
		var item = null;

		if (el) {
			if (typeof el == 'object') {
				item = jQuery(el);
			}
			else {
				item = jQuery('#' + el);
			}
		}
		else {
			item = document.body;
		}

		var inputs = jQuery("input[@type=text], input[@type=password], textarea", item);

		inputs.focus(
			function() {
				jQuery(this).addClass('focus');

				if (this.createTextRange) {
					var value = this.value;
					var textRange = this.createTextRange();

					textRange.moveStart('character', value.length);
					textRange.select();
				}
			}
		);

		inputs.blur(
			function() {
				jQuery(this).removeClass('focus');
			}
		);
	},

	addInputType: function(el) {
		var item;

		if (jQuery.browser.msie && jQuery.browser.version.number() < 7) {
			if (el) {
				if (typeof el == 'object') {
					item = jQuery(el);
				}
				else {
					item = jQuery('#' + el);
				}
			}
			else {
				item = document.body;
			}

			jQuery("input", item).each(function() {
				var current = jQuery(this);
				var type = this.type || "text";

				current.addClass(type);
			});
		}
	},

	addParams: function(params, url) {
		var instance = this;

		if (typeof params == 'object') {
			params = jQuery.param(params);
		}
		else {
			params = jQuery.trim(params);
		}

		if (params != '') {
			var loc = url || location.href;
			var anchorHash, finalUrl;

			if (loc.indexOf('#') > -1) {
				var locationPieces = loc.split('#');
				loc = locationPieces[0];
				anchorHash = locationPieces[1];
			}

			if (loc.indexOf('?') == -1) {
				params = '?' + params;
			}
			else {
				params = '&' + params;
			}

			if (loc.indexOf(params) == -1) {
				finalUrl = loc + params;

				if (anchorHash) {
					finalUrl += '#' + anchorHash;
				}
				if (!url) {
					location.href = finalUrl;
				}
				return finalUrl;
			}
		}
	},

	check: function(form, name, checked) {
		jQuery('input[@name=' + name + ']:checkbox',form).attr('checked', checked);
	},

	checkAll: function(form, name, allBox) {
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[@name='+ name.join(']:checkbox,input[@name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[@name=' + name + ']:checkbox', form);
		}

		inputs.attr('checked', allBox.checked);
	},

	checkAllBox: function(form, name, allBox) {
		var totalBoxes = 0;
		var totalOn = 0;
		var inputs;

		if (Liferay.Util.isArray(name)) {
			var names = 'input[@name='+ name.join(']:checkbox,input[@name=') + ']:checkbox';

			inputs = jQuery(names, form);
		}
		else {
			inputs = jQuery('input[@name=' + name + ']:checkbox', form);
		}

		inputs = inputs.not(allBox);

		totalBoxes = inputs.length;
		totalOn = inputs.filter(':checked').length;

		allBox.checked = (totalBoxes == totalOn);
	},

	checkMaxLength: function(box, maxLength) {
		if ((box.value.length) >= maxLength) {
			box.value = box.value.substring(0, maxLength - 1);
		}
	},

	checkTab: function(box) {
		if ((document.all) && (event.keyCode == 9)) {
			box.selection = document.selection.createRange();
			setTimeout("Liferay.Util.processTab(\"" + box.id + "\")", 0);
		}
	},

	createFlyouts: function(options) {
		var instance = this;

		options = options || {};

		var flyout, containers;

		var containerFilter = function() {
			return (jQuery('ul', this).length != 0);
		};

		if (!options.container) {
			flyout = jQuery('.lfr-flyout');
			containers = flyout.find('li').filter(containerFilter);
		}
		else {
			flyout = jQuery('li', options.container);
			containers = flyout.filter(containerFilter);
		}

		containers.addClass('lfr-flyout');
		containers.addClass('has-children');

		if (!options.container) {
			containers = containers.add(flyout);
		}

		var over = function(event) {
			jQuery('> ul', this).show();

			if (options.mouseOver) {
				options.mouseOver.apply(this, [event]);
			}
		};

		var out = function(event) {
			jQuery('> ul', this).hide();

			if (options.mouseOut) {
				options.mouseOut.apply(this, [event]);
			}
		};

		containers.hover(over,out);
	},

	disableEsc: function() {
		if ((document.all) && (event.keyCode == 27)) {
			event.returnValue = false;
		}
	},

	endsWith: function(str, x) {
		return (str.lastIndexOf(x) === (str.length - x.length));
	},

	evalScripts: function(obj) {
		var instance = this;

		jQuery(obj).find('script').each(
			function(){
				if ( this.src ) {
					jQuery.getScript( this.src );
				}
				else {
					jQuery.globalEval( this.text || this.textContent || this.innerHTML || "" );
				}
			}
		);
	},

	focusFormField: function(el) {
		var interacting = false;

		jQuery(document).one(
			'click',
			function() {
				interacting = true;
			}
		);

		jQuery(
			function() {
				if (el && (el.offsetHeight != 0) && !interacting) {
					var elObj = jQuery(el);

					jQuery('input').trigger('blur');

					elObj.trigger('focus');
				}
			}
		);
	},

	getSelectedIndex: function(col) {
		for (var i = 0; i < col.length; i++) {
			if (col[i].checked == true) {
				return i;
			}
		}

		return -1;
	},

	getSelectedRadioValue: function(col) {
		var i = Liferay.Util.getSelectedIndex(col);

		if (i == -1) {
			var radioValue = col.value;

			if (radioValue == null) {
				radioValue = "";
			}

			return radioValue;
		}
		else {
			return col[i].value;
		}
	},

	getURLWithSessionId: function(url) {
		if (document.cookie && (document.cookie.length > 0)) {
			return url;
		}

		// LEP-4787

		var x = url.indexOf(";");

		if (x != -1) {
			return url;
		}

		x = url.indexOf("?");

		if (x != -1) {
			return url.substring(0, x) + ";jsessionid=" + themeDisplay.getSessionId() + url.substring(x);
		}

		// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
		// http://www.abc.com/;jsessionid=XYZ does work.

		x = url.indexOf("//");

		if (x != -1) {
			var y = url.lastIndexOf("/");

			if (x + 1 == y) {
				return url + "/;jsessionid=" + themeDisplay.getSessionId();
			}
		}

		return url + ";jsessionid=" + themeDisplay.getSessionId();
	},

	inlineEditor: function(options) {
		var instance = this;

		/*
		button (jQuery selector | DOM element): The button that opens the popup when clicked
		url (String): url to open that sets the editor
		width (Int): The width to set the popup to
		height (Int): The height to set the popup to
		textarea (String): the name of the textarea to auto-resize
		*/

		if (options.url && options.button) {
			var url = options.url;
			var button = options.button;
			var width = options.width || 680;
			var height = options.height || 640;
			var textarea = options.textarea;
			var clicked = false;

			var editorButton = jQuery(button);

			editorButton.click(
				function(event) {
					if (!clicked) {
						var popup = Liferay.Popup(
							{
								height: 640,
								width: 680,
								noCenter: true,
								title: '',
								onClose: function() {
									jQuery(document).unbind('popupResize');
									clicked = false;
								}
							}
						);

						var jPopup = jQuery(popup);
						var resizeDiv = '<div class="portlet-resize-handle"></div>';

						jQuery.ajax(
							{
								url: url + '&rt=' + Liferay.Util.randomInt(),
								success: function(message) {
									jPopup.find('.loading-animation').remove();
									jPopup.append(message);
									jPopup.after(resizeDiv);

									var form = jPopup.find('form');

									form.css(
										{
											height: 340,
											width: 680
										}
									);

									if (textarea) {
										var usingPlainEditor = jPopup.find('.lfr-textarea').length;

										Liferay.Util.resizeTextarea(textarea, !usingPlainEditor, true);
									}

									var handle = jQuery('.portlet-resize-handle')[0];

									var mainPopup = jPopup.parents('.popup:first');

									mainPopup.lResize(
										{
											direction: 'horizontal',
											handle: handle,
											mode: 'add',
											onMove: function(settings) {
												form.css(
													{
														height: settings.browserEvent.clientY - 130,
														width: settings.browserEvent.clientX - 30
													}
												);
												jQuery(document).trigger('popupResize');
											}
										}
									);

									mainPopup.lResize(
										{
											handle: handle,
											direction: "vertical",
											mode: "add"
										}
									);
								}
							}
						);

						clicked = true;
					}
				}
			);
		}
	},

	isArray: function(object) {
		if (!window.Array) {
			return false;
		}
		else {
			return object.constructor == window.Array;
		}
	},

	listChecked: function(form) {
		var s = [];
		var inputs = jQuery('input[@value!=]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listCheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[@value!=][@name!="' + except + '"]:checked:checkbox', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	listSelect: function(box, delimeter) {
		var s = [];

		delimeter = delimeter || ',';

		if (box == null) {
			return "";
		}

		var opts = jQuery(box).find('option[@value!=]');

		opts.each(
			function() {
				s.push(this.value);
			}
		);

		if (s[0] == '.none') {
			return '';
		}
		else {
			return s.join(',');
		}
	},

	listUncheckedExcept: function(form, except) {
		var s = [];
		var inputs = jQuery('input[@value!=][@name!="' + except + '"]:checkbox:not(:checked)', form);

		inputs.each(
			function() {
				s.push(this.value);
			}
		);

		return s.join(',');
	},

	moveItem: function(fromBox, toBox, sort) {
		if (fromBox.selectedIndex >= 0) {
			var toSelect = jQuery(toBox);
			var selectedOption = jQuery(fromBox).find('option:selected');

			toSelect.append(selectedOption);
		}

		if (selectedOption.text() != '' && sort == true) {
			Liferay.Util.sortBox(toBox);
		}
	},

	portletTitleEdit: function(options) {
		var instance = this;

		var obj = options.obj;
		var plid = options.plid;
		var doAsUserId = options.doAsUserId;
		var portletId = options.portletId;
		var url = options.url;

		var title = obj.find('.portlet-title');

		if (!title.is('.not-editable')) {
			title.editable(
				function(value, settings) {
					var cruft = settings._LFR_.cruft || [];

					cruft = cruft.join('');

					if (value != settings._LFR_.oldText) {
						Liferay.Util.savePortletTitle(
							{
								plid: plid,
								doAsUserId: doAsUserId,
								portletId: portletId,
								title: value
							}
						);
					}

					obj[0]._LFR_noDrag = null;

					return cruft + value;
				},
				{
					cssclass: 'text',
					data: function(value, settings) {
						var input = jQuery(this);
						var re = new RegExp('<\/?[^>]+>|\n|\r|\t', 'gim');

						var cruft = value.match(re);

						settings._LFR_ = {};
						settings._LFR_.oldText = value;
						settings._LFR_.cruft = cruft;

						value = value.replace(re, '');
						settings._LFR_.oldText = value;
						obj[0]._LFR_noDrag = true;

						return value;
					},
					height: '',
					width: '',
					onblur: 'submit',
					type: 'text',
					select: false,
					style: '',
					submit: ''
				}
			);
		}
	},

	processTab: function(id) {
		document.all[id].selection.text = String.fromCharCode(9);
		document.all[id].focus();
	},

	randomInt: function() {
		return (Math.ceil(Math.random() * (new Date).getTime()));
	},

	randomMinMax: function(min, max) {
		return (Math.round(Math.random() * (max - min))) + min;
	},

	removeItem: function(box, value) {
		var selectEl = jQuery(box);

		if (!value) {
			selectEl.find('option:selected').remove();
		}
		else {
			selectEl.find('option[@value=' + value + ']:selected').remove();
		}
	},

	reorder: function(box, down) {
		var si = box.selectedIndex;

		if (si == -1) {
			box.selectedIndex = 0;
		}
		else {
			sText = box.options[si].text;
			sValue = box.options[si].value;

			if ((box.options[si].value > "") && (si > 0) && (down == 0)) {
				box.options[si].text = box.options[si - 1].text;
				box.options[si].value = box.options[si - 1].value;
				box.options[si - 1].text = sText;
				box.options[si - 1].value = sValue;
				box.selectedIndex--;
			}
			else if ((si < box.length - 1) && (box.options[si + 1].value > "") && (down == 1)) {
				box.options[si].text = box.options[si + 1].text;
				box.options[si].value = box.options[si + 1].value;
				box.options[si + 1].text = sText;
				box.options[si + 1].value = sValue;
				box.selectedIndex++;
			}
			else if (si == 0) {
				for (var i = 0; i < (box.length - 1); i++) {
					box.options[i].text = box.options[i + 1].text;
					box.options[i].value = box.options[i + 1].value;
				}

				box.options[box.length - 1].text = sText;
				box.options[box.length - 1].value = sValue;

				box.selectedIndex = box.length - 1;
			}
			else if (si == (box.length - 1)) {
				for (var j = (box.length - 1); j > 0; j--) {
					box.options[j].text = box.options[j - 1].text;
					box.options[j].value = box.options[j - 1].value;
				}

				box.options[0].text = sText;
				box.options[0].value = sValue;

				box.selectedIndex = 0;
			}
		}
	},

	resizeTextarea: function(elString, usingRichEditor, resizeToInlinePopup) {
		var init = function() {
			var el = jQuery('#' + elString);

			if (!el.length) {
				el = jQuery('textarea[@name=' + elString + ']');
			}

			if (el.length) {
				var pageBody;

				if (resizeToInlinePopup) {
					pageBody = el.parents('.popup:first');
				}
				else {
					pageBody = jQuery('body');
				}

				var resize = function() {
					var pageBodyHeight = pageBody.height();

					if (usingRichEditor) {
						try {
							if (!el.is('iframe')) {
								el = eval(elString);

								if (!el.jquery) {
									el = jQuery(el);
								}
							}
						}
						catch (e) {
						}
					}

					var diff = 150;

					if (!resizeToInlinePopup) {
						diff = 100;
					}

					el.css(
						{
							height: (pageBodyHeight - diff) + "px",
							width: '98%'
						}
					);
				};

				resize();

				if (resizeToInlinePopup) {
					jQuery(document).bind('popupResize', resize);
				}
				else {
					jQuery(window).resize(resize);
				}
			}
		};

		jQuery(init);
	},

	resubmitCountdown: function(formName) {
		if (Liferay.Util.submitCountdown > 0) {
			Liferay.Util.submitCountdown--;

			setTimeout("Liferay.Util.resubmitCountdown('" + formName + "')", 1000);
		}
		else {
			Liferay.Util.submitCountdown = 0;

			if (!Liferay.Browser.is_ns_4) {
				document.body.style.cursor = "auto";
			}

			var form = document.forms[formName];

			for (var i = 0; i < form.length; i++){
				var e = form.elements[i];

				if (e.type && (e.type.toLowerCase() == "button" || e.type.toLowerCase() == "reset" || e.type.toLowerCase() == "submit")) {
					e.disabled = false;
				}
			}
		}
	},

	savePortletTitle: function(params) {
		var defaultParams = {
			plid: 0,
			doAsUserId: 0,
			portletId: 0,
			title: '',
			url: themeDisplay.getPathMain() + '/portlet_configuration/update_title'
		};

		var settings = jQuery.extend(defaultParams, params);

		jQuery.ajax(
			{
				url: settings.url,
				data: {
					p_l_id: settings.plid,
					doAsUserId: settings.doAsUserId,
					portletId: settings.portletId,
					title: settings.title
				}
			}
		);
	},

	selectAndCopy: function(el) {
		el.focus();
		el.select();

		if (document.all) {
			var textRange = el.createTextRange();

			textRange.execCommand("copy");
		}
	},

	setBox: function(oldBox, newBox) {
		for (var i = oldBox.length - 1; i > -1; i--) {
			oldBox.options[i] = null;
		}

		for (var i = 0; i < newBox.length; i++) {
			oldBox.options[i] = new Option(newBox[i].value, i);
		}

		oldBox.options[0].selected = true;
	},

	setSelectedValue: function(col, value) {
		jQuery('option[@value=' + value + ']', col).attr('selected', true);
	},

	showCapsLock: function(event, span) {
		var keyCode = event.keyCode ? event.keyCode : event.which;
		var shiftKey = event.shiftKey ? event.shiftKey : ((keyCode == 16) ? true : false);

		if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) ||
			((keyCode >= 97 && keyCode <= 122) && shiftKey)) {

			document.getElementById(span).style.display = '';
		}
		else {
			document.getElementById(span).style.display = 'none';
		}
	},

	sortBox: function(box) {
		var newBox = [];

		for (var i = 0; i < box.length; i++) {
			newBox[i] = [box[i].value, box[i].text];
		}

		newBox.sort(Liferay.Util.sortByAscending);

		var boxObj = jQuery(box);

		boxObj.find('option').remove();

		jQuery.each(
			newBox,
			function(key, value) {
				boxObj.append('<option value="' + value[0] + '">' + value[1] + '</option>');
			}
		);
	},

	sortByAscending: function(a, b) {
		if (a[1].toLowerCase() > b[1].toLowerCase()) {
			return 1;
		}
		else if (a[1].toLowerCase() < b[1].toLowerCase()) {
			return -1;
		}
		else {
			return 0;
		}
	},

	startsWith: function(str, x) {
		return (str.indexOf(x) === 0);
	},

	switchEditor: function(options) {
		var instance = this;

		/*
		url (String): url to open that sets the editor
		popup (String|DOM|jQuery element): the popup that contains the editor
		textarea (String): the name of the textarea to auto-resize
		*/

		if (options.url && options.popup) {
			var url = options.url;
			var popup = options.popup;
			var textarea = options.textarea;

			if (!popup.jquery) {
				popup = jQuery(popup);
			}

			var popupMessage = popup.find('.popup-message');

			jQuery.ajax(
				{
					url: url,
					beforeSend: function() {
						popupMessage.empty();
						popupMessage.append('<div class="loading-animation"><div>');
					},
				  	success: function(message) {
						popupMessage.empty();
						popupMessage.append(message);

						if (textarea) {
							var usingPlainEditor = popup.find('.lfr-textarea').length;

							Liferay.Util.resizeTextarea(textarea, !usingPlainEditor, true);
						}
				 	}
				}
			);
		}
	},

	toggleByIdSpan: function(obj, id) {
		jQuery('#' + id).toggle();

		var spans = jQuery(obj).find('span');

		spans.toggle();
	},

	toggle: function(obj, returnState, displayType) {
		if (typeof obj == 'string') {
			obj = '#' + obj;
		}

		var el = jQuery(obj);
		var hidden = el.toggle().is(':visible');

		if (displayType) {
			el.css('display', displayType);
			hidden = el.is(':visible');
		}

		if (returnState) {
			return hidden;
		}
	},

	toggleBoxes: function(checkBoxId, toggleBoxId) {
		var checkBox = jQuery('#' + checkBoxId);
		var toggleBox = jQuery('#' + toggleBoxId);

		if (!checkBox.is(':checked')){
			toggleBox.hide();
		}

		checkBox.click(
			function(){
				toggleBox.toggle();
			}
		);
	},

	toJSONObject: function(s) {
		return jQuery.parseJSON(s);
	},

	toJSONString: function (s) {
		var rt = s;
		var m = {
			'\b': '\\b',
			'\t': '\\t',
			'\n': '\\n',
			'\f': '\\f',
			'\r': '\\r',
			'"' : '\\"',
			'\\': '\\\\'
		};

		if (/["\\\x00-\x1f]/.test(s)) {
			rt = s.replace(/([\x00-\x1f\\"])/g, function(a, b) {
				var c = m[b];
				if (c) {
					return c;
				}
				c = b.charCodeAt();
				return '\\u00' +
					Math.floor(c / 16).toString(16) +
					(c % 16).toString(16);
			});
		}

		return rt;
	}
};

Element = {};

Element.disable = function(element) {
	element = jQuery.getOne(element);

	var items = element.getElementsByTagName("*");

	for (var i = 0; i < items.length; i++) {
		var item = items[i];
		var nodeName = item.nodeName.toLowerCase();

		item.onclick = function() {};
		item.onmouseover = function() {};
		item.onmouseout = function() {};

		if (Liferay.Browser.is_ie) {
			item.onmouseenter = function() {};
			item.onmouseleave = function() {};
		}

		if (nodeName == "a") {
			item.href = "javascript: void(0)";
		}
		else if (nodeName == "input" || nodeName == "select" || nodeName == "script") {
			item.disabled = "true";
		}
		else if (nodeName == "form") {
			item.action = "";
			item.onsubmit = function() { return false; };
		}

		item.style.cursor = "default";
	}
};

Element.remove = function(id) {
	var obj = jQuery.getOne(id);

	obj.parentNode.removeChild(obj);
};

function LinkedList() {
	this.head = null;
	this.tail = null;
}

LinkedList.prototype.add = function(obj) {
	obj.listInfo = {};
	var tail = this.tail;
	var head = this.head;

	if (this.head == null) {
		this.head = obj;
		this.tail = obj;
	}
	else {
		this.tail.listInfo.next = obj;
		obj.listInfo.prev = this.tail;
		this.tail = obj;
	}

	obj.listInfo.listObj = this;
};

LinkedList.prototype.remove = function(obj) {
	if (obj.listInfo.listObj == this && this.head) {
		var next = obj.listInfo.next;
		var prev = obj.listInfo.prev;

		if (next) {
			next.listInfo.prev = prev;
		}
		if (prev) {
			prev.listInfo.next = next;
		}
		if (this.head == obj) {
			this.head = next;
		}
		if (this.tail == obj) {
			this.tail = prev;
		}
	}
};

LinkedList.prototype.each = function(func) {
	var cur = this.head;
	var count = 0;

	while (cur){
		count++;
		var next = cur.listInfo.next;

		if (func) {
			func(cur);
		}

		cur = next;
	}

	return count;
};

LinkedList.prototype.size = function() {
	return this.each();
};

function submitForm(form, action, singleSubmit) {
	if (Liferay.Util.submitCountdown == 0) {
		Liferay.Util.submitCountdown = 10;

		setTimeout("Liferay.Util.resubmitCountdown('" + form.name + "')", 1000);

		if (singleSubmit == null || singleSubmit) {
			Liferay.Util.submitCountdown++;

			var inputs = jQuery('input[@type=button], input[@type=reset], input[@type=submit]', form);

			inputs.each(
				function(i, el) {
					var input = jQuery(this);

					input.attr('disabled', true);
					input.fadeTo(50, 0.5);
				}
			);
		}

		if (action != null) {
			form.action = action;
		}

		if (!Liferay.Browser.is_ns_4) {
			document.body.style.cursor = "wait";
		}

		form.submit();
	}
}

var Viewport = {
	frame: function() {
		var x,y;
		if (self.innerHeight) // all except Explorer
		{
			x = self.innerWidth;
			y = self.innerHeight;
		}
		else if (document.documentElement && document.documentElement.clientHeight)
			// Explorer 6 Strict Mode
		{
			x = document.documentElement.clientWidth;
			y = document.documentElement.clientHeight;
		}
		else if (document.body) // other Explorers
		{
			x = document.body.clientWidth;
			y = document.body.clientHeight;
		}

		return (new Coordinate(x,y));
	},

	scroll: function() {
		var x,y;
		if (self.pageYOffset) {
			// all except Explorer
			x = self.pageXOffset;
			y = self.pageYOffset;
		}
		else if (document.documentElement && document.documentElement.scrollTop) {
			// Explorer 6 Strict
			x = document.documentElement.scrollLeft;
			y = document.documentElement.scrollTop;
		}
		else if (document.body) {
			// all other Explorers
			x = document.body.scrollLeft;
			y = document.body.scrollTop;
		}

		return (new Coordinate(x,y));
	},

	page: function() {
		var x,y;
		var test1 = document.body.scrollHeight;
		var test2 = document.body.offsetHeight;
		if (test1 > test2) // all but Explorer Mac
		{
			x = document.body.scrollWidth;
			y = document.body.scrollHeight;
		}
		else // Explorer Mac;
			//would also work in Explorer 6 Strict, Mozilla and Safari
		{
			x = document.body.offsetWidth;
			y = document.body.offsetHeight;
		}

		return (new Coordinate(x,y));
	}
};

String.prototype.trim = jQuery.trim;

// 0-200: Theme Developer
// 200-400: Portlet Developer
// 400+: Liferay

Liferay.zIndex = {
	DOCK:			10,
	DOCK_PARENT:	20,
	ALERT:			430,
	DROP_AREA:		440,
	DROP_POSITION:	450,
	DRAG_ITEM:		460,
	TOOLTIP:		470
};
function AjaxRequest(url, options) {

	var xmlHttpReq;
	var opts = options;

	if (window.XMLHttpRequest) {
		xmlHttpReq = new XMLHttpRequest();

		if (xmlHttpReq.overrideMimeType) {
			xmlHttpReq.overrideMimeType("text/html");
		}
	}
	else if (window.ActiveXObject) {
		try {
			xmlHttpReq = new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e) {
			try {
				xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e) {
				try {
					xmlHttpReq = new XMLHttpRequest();
				}
				catch (e) {
				}
			}
		}
	}

	var returnFunction = function() {
		if (xmlHttpReq.readyState == 4) {
			try {
				if (xmlHttpReq.status == 200) {
					if (opts.update) {
						var el = jQuery.getOne(opts.update);
						el.innerHTML = xmlHttpReq.responseText;
						executeLoadedScript(el);
					}

					if (opts.onComplete) {
						opts.onComplete(xmlHttpReq, opts.returnArgs);
					}

					AjaxUtil.remove(opts.ajaxId);
				}
			}
			catch(e) {
			}
		}
	};

	var send = function(url) {
		url = Liferay.Util.getURLWithSessionId(url);

		var urlArray = url.split("?");
		var path = urlArray[0];
		var query = urlArray[1];

		try {
			if (opts.method == "get") {
				xmlHttpReq.open("GET", url, true);
				xmlHttpReq.onreadystatechange = returnFunction;
				xmlHttpReq.send("");
			}
			else {
				xmlHttpReq.open("POST", path, true);
				xmlHttpReq.setRequestHeader("Method", "POST " + path + " HTTP/1.1");
				xmlHttpReq.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlHttpReq.onreadystatechange = returnFunction;
				xmlHttpReq.send(query);
			}
		}
		catch (e) {
		}
	};

	send(url);

	this.resend = function(url, options) {
		opts = options;
		/*
		ajaxId = 0;
		onComplete = opts.onComplete;
		*/
		send(url);
	};

	this.getId = function() {
		return ajaxId;
	};

	this.cleanUp = function() {
		xmlHttpReq.onreadystatechange = function() {};
		returnFunction = null;
		returnArgs = null;
		xmlHttpReq = null;
	};
}

var AjaxUtil = {
	counter : 1,
	requests : [],

	request : function(url, options) {
		/*
		 * OPTIONS:
		 * onComplete (function) - function to call after response is received
		 * returnArgs (object) - object to pass to return function
		 * reverseAjax (boolean) - use reverse ajax. (only one at a time)
		 * method (string) - use "get" or "post". Default is post.
		 */
		var opts = options || {};
		var ajaxId = (opts.reverseAjax) ? 0 : AjaxUtil.getNextId();
		opts.ajaxId = ajaxId;

		var request;

		if (ajaxId == 0 && AjaxUtil.requests[0]) {
			request = AjaxUtil.requests[0];
			request.resend(url, opts);
		}
		else {
			request = new AjaxRequest(url, opts);
			AjaxUtil.requests[ajaxId] = request;
		}

		if (!opts.onComplete && !opts.update) {
			AjaxUtil.remove(ajaxId);
		}
	},

	submit: function(form, options) {
		var url = form.action;
		var inputs = jQuery("input, textarea, select", form);
		var opts = options || {};
		var params = inputs.serialize();

		if (url.indexOf("?") == -1) {
			url = url + "?" + params;
		}

		if (url.lastIndexOf("?") == url.length - 1) {
			url = url + params;
		}
		else {
			url = url + "&" + params;
		}

		if (opts.disable) {
			inputs.attr("disabled", true);
		}

		AjaxUtil.request(url, opts);
	},

	update : function(url, id, options) {
		var opts = options || {};

		opts.update = id;

		AjaxUtil.request(url, opts);
	},

	getNextId : function() {
		var id = AjaxUtil.counter++;

		if (AjaxUtil.counter > 20) {
			/* Reset array in a round-robin fashion */
			/* Reserve index 0 for reverse ajax requests */
			AjaxUtil.counter = 1;
		}

		return id;
	},

	remove : function(id) {
		if (id) {
			var request = AjaxUtil.requests[id];

			if (request) {
				request.cleanUp();
				request = null;
			}
		}
	}
};

var ReverseAjax = {
	initialize: function() {
		jQuery(window).bind('unload', ReverseAjax.release);
		ReverseAjax.request();
	},

	request: function() {
		AjaxUtil.request(
			themeDisplay.getPathMain() + "/portal/reverse_ajax",
			{
				onComplete: ReverseAjax.response,
				reverseAjax: true
			}
		);
	},

	response: function(xmlHttpRequest) {
		var res = Liferay.Util.toJSONObject(xmlHttpRequest.responseText);
		var status = res.status;

		if (status && status != "failure") {
			if (status == "success") {
				if (res.chatMessages) {
					Messaging.getChatsReturn(res.chatMessages);
				}
				if (res.chatRoster) {
					MessagingRoster.getEntriesReturn(res.chatRoster);
				}
			}

			ReverseAjax.request();
		}
	},

	release : function() {
		AjaxUtil.request(themeDisplay.getPathMain() + "/portal/reverse_ajax?release=1", {reverseAjax:true});
	}
};

function executeLoadedScript(el) {
	var scripts = el.getElementsByTagName("script");

	for (var i = 0; i < scripts.length; i++) {
		if (scripts[i].src) {
			var head = document.getElementsByTagName("head")[0];
			var scriptObj = document.createElement("script");

			scriptObj.setAttribute("type", "text/javascript");
			scriptObj.setAttribute("src", scripts[i].src);

			head.appendChild(scriptObj);
		}
		else {
			try {
				if (Liferay.Browser.is_safari) {
					eval(scripts[i].innerHTML);
				}
				else if (Liferay.Browser.is_mozilla) {
					eval(scripts[i].textContent);
				}
				else {
					eval(scripts[i].text);
				}
			}
			catch (e) {}
		}
	}
}

/*
 * NOTE: loadPage() has been depricated.  Use AjaxUtil.request() instead
 */
function loadPage(path, queryString, returnFunction, returnArgs) {
	AjaxUtil.request(
		path + "?" + queryString,
		{
			onComplete: returnFunction,
			returnArgs: returnArgs
		}
	);
}

function printJSON(data) {
	if (data && data.id) {
		var target = document.getElementById(data.id);

		if (target) {
			target.innerHTML = data.toString();
		}
	}
}
var DragLink = {
	create: function(item, dragId) {
		item.dragId = jQuery.getOne(dragId);
		item.clickLink = item.href;
		item.href = "javascript:void(0)";
		item.onclick = DragLink.onLinkClick;
	},

	onLinkClick: function() {
		if (this.dragId.wasClicked) {
			if (Liferay.Browser.is_ie) {
				setTimeout("window.location = \"" + this.clickLink + "\";", 0);
			}
			else {
				window.location = this.clickLink;
			}
		}
	}
};

var NavFlyout = {
	zIndex: 1,
	initialize: function (nav) {
		var nav = jQuery.getOne(nav);
		var navMapList = jQuery("portlet-nav-map-list", nav);

		navMapList.not(".portlet-nav-map-level_1, .portlet-nav-map-level_2")
			.css({position: "absolute", display: "none"});

		jQuery(".portlet-nav-map-list a", nav).each(function(){
			var item = jQuery(this.parentNode.parentNode);
				if (item.is(".portlet-nav-map-level_1")) {
					item.mouseover(function() {
						NavFlyout.hide(this.parentNode);
					});
				}
				else {
					item.mouseover(NavFlyout.onHoverOver);
				}
		});
	},

	initToggle: function(nav, imgSrc) {
		var nav = jQuery.getOne(nav);
		var navMapList = jQuery(".portlet-nav-map-level_1 > li", nav);
		navMapList.click(NavFlyout.onToggle);
		navMapList.css({ backgroundImage: "url(" + imgSrc + ")" });
	},

	hide: function(listItem) {
		NavFlyout.initialize(listItem.parentNode);
	},

	onHoverOver: function() {
		var listItem = this.parentNode;

		// Hide all other submenus
		if (jQuery(listItem.parentNode).is(".portlet-nav-map-level_2")) {
			NavFlyout.hide(listItem.parentNode.parentNode.parentNode);
		}
		else {
			NavFlyout.hide(listItem);
		}

		// Show current submenu
		jQuery(listItem.childNodes).filter("ul")
			.css({
				display: "block",
				top: "5px",
				left: "100px"
			});

		// Fix Z-Index
		zItem = listItem;
		while (zItem.nodeName.toLowerCase() != "div") {
			if (zItem.nodeName.toLowerCase() == "li") {
				zItem.style.zIndex = NavFlyout.zIndex;
			}
			zItem = zItem.parentNode;
		}

		NavFlyout.zIndex++;
	},

	onToggle: function() {
		var subMenu = jQuery("ul:first", this).get(0);

		if (this.isShowing) {
			subMenu.style.display = "none";
			this.style.backgroundImage = this.style.backgroundImage.replace(/02_minus/, "02_plus");
			this.isShowing = false;
		}
		else {
			subMenu.style.display = "block";
			subMenu.style.backgroundImage = "none";
			this.style.backgroundImage = this.style.backgroundImage.replace(/02_plus/, "02_minus");
			this.isShowing = true;
		}
	}
};

var PortletHeaderBar = {
	mode: {},

	fade: function(data) {
		var count = data.count;
		var id = data.id;

		var changed = false;
		var icons = jQuery("#portlet-header-bar_" + id + " .portlet-small-icon-bar");

		if (PortletHeaderBar.mode[id] == "in") {
			if (count <= 10) {
				if (count >= 0) {
					icons.css({"opacity": (count/10), "display": ""});
				}
				changed = true;
				data.count++;
			}
		}
		else if (PortletHeaderBar.mode[id] == "out") {
			if (count >= 0) {
				if (count <= 10) {
					icons.css("opacity", (count/10));
				}
				data.count--;
				changed = true;
			}
			else {
				icons.css("display", "none");
			}
		}

		return changed;
	},

	hide: function (id) {
		this.mode[id] = "out";
		Liferay.Animate("header-icon-fade_" + id,
			PortletHeaderBar.fade,
			{ count: 15, id: id }
		);
	},

	show: function (id) {
		this.mode[id] = "in";
		Liferay.Animate("header-icon-fade_" + id,
			PortletHeaderBar.fade,
			{ count: -5, id: id }
		);
	}
};

var PhotoSlider = Class.create();
PhotoSlider.prototype = {

	initialize: function (slidingWindow, windowWidth, photos, totalPages, varName) {
		this.TOTAL_FRAMES = 20;
		this.count = 0;
		this.page = 0;
		this.timer = 0;
		this.start = 0;

		this.photos = jQuery.getOne(photos);
		this.photos.style.position = "relative";
		this.photos.style.left = "0px";

		this.slidingWindow = _J$.getOne(slidingWindow);
		this.windowWidth = windowWidth;
		this.totalPages = totalPages;
		this.varName = varName;
	},

	animate: function() {
		if (this.count <= this.TOTAL_FRAMES) {
			var ratio = this.count / this.TOTAL_FRAMES;
			var ratio2 = Math.sin(ratio * (Math.PI/2));
			var delta = -(this.page * this.windowWidth) - this.start;

			this.photos.style.left = this.start + (delta * ratio2);
			this.count++;
			this.timer = setTimeout(this.varName + ".animate()", 30);
		}
		else {
			this.timer = 0;
		}
	},

	left: function() {
		this.start = parseInt(this.photos.style.left);

		if (this.page > 0) {
			this.page--;
			this.count = 0;

			if (!this.timer) {
				this.timer = setTimeout(this.varName + ".animate()", 30);
			}
		}
	},

	right: function() {
		this.start = parseInt(this.photos.style.left);

		if (this.page < (this.totalPages - 1)) {
			this.page++;
			this.count = 0;

			if (!this.timer) {
				this.timer = setTimeout(this.varName + ".animate()", 30);
			}
		}
	}
};

var Tabs = {
	show : function (namespace, names, id) {
		var el = document.getElementById(namespace + id + "TabsId");

		if (el) {
			el.className = "current";
		}

		el = document.getElementById(namespace + id + "TabsSection");

		if (el) {
			el.style.display = "block";
		}

		for (var i = 0; (names.length > 1) && (i < names.length); i++) {
			if (id != names[i]) {
				el = document.getElementById(namespace + names[i] + "TabsId");

				if (el) {
					el.className = "none";
				}

				el = document.getElementById(namespace + names[i] + "TabsSection");

				if (el) {
					el.style.display = "none";
				}
			}
		}
	}
};

var StarRating = new Class({
	initialize: function(id, options) {
		/* OPTIONS
		 * displayOnly: (boolean) non-modifiable display
		 * onComplete: (function) executes when rating is selected
		 * rating: rating to initialize to
		 */
			this.options = options || {};
			this.rating = this.options.rating || 0;
			var item = jQuery("#" + id);
			this.stars = item.find("img");
			var self = this;

			if (!this.options.displayOnly) {
				item.bind("mouseout",  {self: this}, this.onHoverOut);

				this.stars.each(function(index) {
					this.index = index + 1;
					jQuery(this).bind("click", {self: self}, self.onClick)
						   .bind("mouseover", {self: self}, self.onHoverOver);
				})
			}

			this.display(this.rating, "rating");
		}
	});

StarRating.implement({
	display: function(rating, mode) {
		var self = this;
		rating = rating == null ? this.rating : rating;

		var whole = Math.floor(rating);
		var fraction = rating - whole;

		this.stars.each(function(index) {
			image = this;
			if (index < whole) {
				if (mode == "hover") {
					image.src = image.src.replace(/\bstar_.*\./, "star_hover.");
				}
				else {
					image.src = image.src.replace(/\bstar_.*\./, "star_on.");
				}
			}
			else {
				if (fraction < 0.25) {
					image.src = image.src.replace(/\bstar_.*\./, "star_off.");
				}
				else if (fraction < 0.50) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_quarter.");
				}
				else if (fraction < 0.75) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_half.");
				}
				else if (fraction < 1.00) {
					image.src = image.src.replace(/\bstar_.*\./, "star_on_threequarters.");
				}
				fraction = 0;
			}
		});
	},

	onHoverOver: function(event) {
		event.data.self.display(this.index, "hover");
	},
	onHoverOut: function(event) {
		event.data.self.display();
	},
	onClick: function(event) {
		var target = this;
		var newRating = target.index;
		var self = event.data.self;
		self.rating = newRating;

		if (self.options.onComplete) {
			self.options.onComplete(newRating);
		}

		self.display(newRating);
	}
});

var ToolTip = {
	container: null,

	show: function(event, obj, text) {
		var instance = this;

		var container = instance.container;
		var currentItem = jQuery(obj);
		var position = currentItem.offset();

		var dimensions = instance._windowCalculation();

		if (!container) {
			container = jQuery('<div class="portal-tool-tip"></div>').appendTo('body');

			instance.container = container;
		}

		container.html(text);

		container.show();

		var boxWidth = container.width();
		var width = currentItem.width();
		var height = currentItem.height();
		var boxHeight = container.height();
		var left = position.left - (boxWidth / 2);
		var top = position.top + height + 5;

		if (left < 0) {
			left = 5;
		}
		else {
			left += 5;
		}

		if (left + boxWidth > dimensions.right) {
			left = (left - (boxWidth / 2 )) + width;
		}

		if (top + boxHeight > dimensions.bottom) {
			top = top - (height + boxHeight + 5);
		}

		container.css(
			{
				cursor: 'default',
				left: left + 'px',
				position: 'absolute',
				top: top + 'px',
				zIndex: Liferay.zIndex.TOOLTIP
			}
		);

		currentItem.one(
			'mouseout',
			function() {
				instance.hide();
			}
		);
	},

	hide: function(event) {
		var instance = this;

		instance.container.hide();
	},

	_windowCalculation: function() {
		var instance = this;

		if (instance._window.right == null) {
			var windowSize = {};
			var body = instance._body;
			if (!body) {
				body = jQuery('body');
				instance._body = body;
			}

			instance._window = {
				bottom: body.height(),
				left: 0,
				right: body.width(),
				top: 0
			};

			jQuery(window).resize(
				function() {
					instance._window.bottom = body.height();
					instance._window.right = body.width();
				}
			);
		}
		return instance._window;
	},
	_body: null,
	_window: {}
};
Liferay.Portlet = {
	fn: {},
	fnAll: [],
	fnLast: [],
	ajaxList: {},
	list: {},

	isAjax: function(id) {
		return (this.ajaxList[id] == 1);
	},

	flagAjax: function(id) {
		this.ajaxList[id] = 1;
	},

	process: function(id) {
		var status = this.list[id];
		var count = 0;

		this.list[id] = 0;

		if (status == 1) {
			this.processPortlet(id);
			this.processAll(id);
		}
		else if (status == 0) {

			// Already processed. Do nothing.

		}
		else {

			// New portlet. Process and mark.

			this.processPortlet(id);
		}

		for (var i in this.list) {
			count += this.list[i];
		}

		if (count == 0) {
			this.processLast(id);
		}
	},

	processAll: function(id) {
		for (var i = 0; i < this.fnAll.length; i++) {
			this.fnAll[i](id, jQuery("#p_p_id_" + id + "_"));
		}
	},

	processPortlet: function(id) {
		if (this.fn[id]) {
			for (var i = 0; i < this.fn[id].length; i++) {
				this.fn[id][i](id, jQuery("#p_p_id_" + id + "_"));
			}
			this.fn[id] = [];
		}
	},

	processLast: function(id) {
		for (var i = 0; i < this.fnLast.length; i++) {
			this.fnLast[i](id);
		}
		this.fnLast = [];
	},

	ready: function(arg1, arg2) {
		if (typeof arg1 == "function") {
			this.fnAll.push(arg1);
		}
		else if (typeof arg1 == "string" && typeof arg2 == "function") {
			if (!this.fn[arg1]) {
				this.fn[arg1] = [];
			}

			this.fn[arg1].push(arg2);
		}
	},

	remove: function(id) {
		this.ajaxList[id] = 0;
		this.list[id] = 1;
		this.fn[id] = [];
	},

	last: function(arg1) {
		this.fnLast.push(arg1);
	},

	/*
	 * Helper methods for portlets
	 */
	findIndex: function(portlet) {
		var index = -1;

		jQuery("> .portlet-boundary", portlet.parentNode).each(
			function(i) {
				if (this == portlet) {
					index = i;
				}
			}
		);

		return index;
	},

	move: function(data) {
		var portlet = data.portlet;
		var neighbor = data.neighbor;
		var quadrant = data.quadrant;
		var container = data.container;

		if (neighbor && quadrant) {
			if (quadrant == 1 || quadrant == 2) {
				jQuery(neighbor).before(portlet);
			}
			else {
				jQuery(neighbor).after(portlet);
			}

			if (portlet && portlet.originalColumn) {
				var oColumn = jQuery(portlet.originalColumn);
				var portlets = oColumn.find('.portlet-boundary:first');

				if (portlets.length == 0) {
					oColumn.addClass('empty');
				}

				oColumn = portlet.originalColumn = null;
			}
		}
		else if (container) {
			jQuery(container).append(portlet).removeClass('empty');
		}

		this.savePosition(portlet);
	},

	savePosition: function(portlet) {
		var currentColumnId = portlet.parentNode.id.replace(/^layout-column_/, '');
		var index = this.findIndex(portlet);
		var portletId = portlet.id.replace(/^(p_p_id_)/, '');
		portletId = portletId.substring(0, portletId.length - 1);

		AjaxUtil.request(themeDisplay.getPathMain() + "/portal/update_layout" +
			"?p_l_id=" + themeDisplay.getPlid() +
			"&p_p_id=" + portletId +
			"&p_p_col_id=" + currentColumnId +
			"&p_p_col_pos=" + index +
			"&doAsUserId=" + themeDisplay.getDoAsUserIdEncoded() +
			"&cmd=move");
	}
};

jQuery.fn.last = function(fn) {
	Liferay.Portlet.last(fn);
};
Liferay.Dock = {
	init: function() {
		var instance = this;

		var dock = jQuery('.lfr-dock');

		if (!dock.is('.interactive-mode')) {
			return;
		}

		dock.addClass('lfr-component');

		var dockList = dock.find('.lfr-dock-list');

		if (dockList.length > 0){
			var myPlaces = jQuery('.my-places', dock);

			Liferay.Util.createFlyouts(
				{
					container: dockList[0],
					mouseOver: function(event) {
						if (this.className.indexOf('my-places') > -1) {
							jQuery('.current-community > ul', this).show();
						}
						else if (this.parentNode.className.indexOf('taglib-my-places') > -1) {
							jQuery('ul', this.parentNode).hide();
							jQuery('> ul', this).show();
						}
					}
				}
			);

			dockList.find('li:first-child, a:first-child').addClass('first');
			dockList.find('li:last-child, a:last-child').addClass('last');

			instance._dock = dock;
			instance._dockList = dockList;
			instance._myPlaces = myPlaces;

			dockList.hide();
			dockList.wrap('<div class="lfr-dock-list-container"></div>');

			var dockData = {
				dock: dock,
				dockList: dockList
			};

			dock.css(
				{
					cursor: 'pointer',
					position: 'absolute',
					zIndex: Liferay.zIndex.DOCK
				}
			);

			var dockOver = function(event) {
				event.data = dockData;

				jQuery(document).one(
					'click',
					function(internalEvent) {
						var currentEl = jQuery(internalEvent.target);
						var dockParent = currentEl.parents('.lfr-dock');

						if ((dockParent.length == 0) && !currentEl.is('.lfr-dock')) {
							instance._toggle(event, 'hide');
						}
					}
				);

				instance._toggle(event, 'show');
			};

			var dockOut = function(event) {
				event.data = dockData;

				instance._toggle(event, 'hide');
			};

			dock.hoverIntent(
				{
					interval: 0,
					out: dockOut,
					over: dockOver,
					timeout: 500
				}
			);

			if (Liferay.Browser.is_ie && Liferay.Browser.version() <= 6) {
				myPlaces.find('> ul').css('zoom', 1);
			}

			var dockParent = dock.parent();

			dockParent.css(
				{
					position: 'relative',
					zIndex: Liferay.zIndex.DOCK_PARENT
				}
			);

			instance._handleDebug();
		}
	},

	_handleDebug: function() {
		var instance = this;

		var dock = instance._dock;
		var dockList = instance._dockList;
		var myPlacesList = instance._myPlaces.find('> ul');

		if (dock.is('.debug')) {
			dock.show();
			dockList.show();
			dockList.addClass('expanded');
		}
	},

	_toggle: function(event, state) {
		var params = event.data;

		var dock = params.dock;
		var dockList = params.dockList;

		if (state == 'hide') {
			dockList.hide();
			dock.removeClass('expanded');
		}
		else if (state == 'show') {
			dockList.show();
			dock.addClass('expanded');
		}
		else {
			dockList.toggle();
			dock.toggleClass('expanded');
		}
	}
};
