/*! DD_belatedPNG.license.txt *//*

DD_belatedPNG v0.0.5a <http://www.dillerdesign.com/experiment/DD_belatedPNG/>
Copyright (c) 2008 Drew Diller
This software is released under the MIT License <http://www.opensource.org/licenses/mit-license.php>

*//*jslint
	passfail: false,
	white: true,
	browser: true,
	widget: false,
	sidebar: false,
	rhino: false,
	safe: false,
	adsafe: false,
	debug: false,
	evil: false,
	cap: false,
	on: false,
	fragment: false,
	laxbreak: false,
	forin: true,
	sub: false,
	css: false,
	undef: true,
	nomen: false,
	eqeqeq: true,
	plusplus: false,
	bitwise: true,
	regexp: true,
	onevar: true,
	strict: false
*//*global
	DD_belatedPNG
*/

jQuery.DD_belatedPNG = (function ($) {
	var doc = document, ie = (!window.opera && (ie = navigator.userAgent.match(/MSIE (\d)/))) ? parseInt(ie[1].replace('5', '6'), 10) : false, nameSpace = 'DD_belatedPNG';

	if (!ie) {
		return {
			fixPng: function () {
				return true;
			}
		};
	}

	if (doc.namespaces && !doc.namespaces[nameSpace]) {
		doc.namespaces.add(nameSpace, 'urn:schemas-microsoft-com:vml');
	}

	return {
		resize: function (elem) {
			this.updateVmlDimensions(elem);
		},

		interceptPropertyChanges: function (elem) {
			if (/background/.test(event.propertyName)) {
				this.updateVmlFill(elem);
			}
		},

		handlePseudoHover: function (elem) {
			var self = this;

			setTimeout(function () {
				elem.runtimeStyle.backgroundColor = '';
				elem.runtimeStyle.backgroundImage = '';
				self.updateVmlFill(elem);
			}, 1);
		},

		fixPng: function (elem) {
			var bgSizeFinderStyle, each, elemName = elem.nodeName, handlers = {
				propertychange: 'interceptPropertyChanges',
				resize: 'resize',
				move: 'resize'
			}, moreForAs = {
				mouseleave: 'handlePseudoHover',
				mouseenter: 'handlePseudoHover',
				focus: 'handlePseudoHover',
				blur: 'handlePseudoHover'
			}, onHandler = function () {
				self[handlers[each]](elem);
			}, self = this;

			elem.style.behavior = 'none';
			if (/^(BODY|TR|TD)$/.test(elemName)) {
				return;
			}
			elem.bgSizeFinder = doc.createElement('IMG');
			bgSizeFinderStyle = elem.bgSizeFinder.style;
			bgSizeFinderStyle.position = 'absolute';
			bgSizeFinderStyle.top = '-10000px';
			bgSizeFinderStyle.visibility = 'hidden';
			bgSizeFinderStyle.zIndex = -1;
			elem.bgSizeFinder.attachEvent('onload', function () {
				self.updateVmlDimensions(elem);
			});
			doc.body.insertBefore(elem.bgSizeFinder, doc.body.firstChild);
			elem.imgRect = doc.createElement(nameSpace + ':rect');
			elem.imgRect.style.position = 'absolute';
			elem.imgFill = doc.createElement(nameSpace + ':fill');
			elem.colorRect = doc.createElement(nameSpace + ':rect');
			elem.colorRect.style.position = 'absolute';
			elem.rects = [elem.imgRect, elem.colorRect];
			for (each = 0; each < 2; each++) {
				elem.rects[each].stroked = false;
			}
			elem.parentNode.insertBefore(elem.colorRect, elem);
			elem.imgRect.appendChild(elem.imgFill);
			elem.parentNode.insertBefore(elem.imgRect, elem);
			elem.imgRect.addBehavior('#default#VML');
			elem.imgFill.addBehavior('#default#VML');
			elem.colorRect.addBehavior('#default#VML');
			if (elemName === 'A') {
				for (each in moreForAs) {
					handlers[each] = moreForAs[each];
				}
			}
			for (each in handlers) {
				elem.attachEvent('on' + each, onHandler);
			}
			self.updateVmlFill(elem);
		},

		updateVmlFill: function (elem) {
			var bAtts = {
				Style: true,
				Width: true,
				Color: true
			}, bg, each, elemName = elem.nodeName, elemStyle = elem.currentStyle, giveLayout = function (elem) {
				elem.style.zoom = 1;
				if (elem.currentStyle.position === 'static') {
					elem.style.position = 'relative';
				}
			}, knownZ;

			if (elemStyle.backgroundImage) {
				knownZ = (elemStyle.zIndex !== '0') ? elemStyle.zIndex : -1;
				elem.colorRect.style.zIndex = knownZ;
				elem.imgRect.style.zIndex = knownZ;
				giveLayout(elem);
				giveLayout(elem.parentNode);
				bg = elemStyle.backgroundImage.split('"')[1];
			}
			else if (elem.src) {
				bg = elem.src;
			}
			if (elemStyle.backgroundImage || elem.src) {
				elem.bgSizeFinder.src = bg;
				elem.imgFill.src = bg;
				elem.imgFill.type = 'tile';
			}
			elem.imgRect.filled = true;
			elem.colorRect.filled = false;
			elem.colorRect.style.backgroundColor = elemStyle.backgroundColor;
			elem.runtimeStyle.backgroundImage = 'none';
			elem.runtimeStyle.backgroundColor = 'transparent';
			if (elemName === 'IMG') {
				if (elemStyle.position === 'static') {
					elem.style.position = 'relative';
				}
				for (each in bAtts) {
					elem.imgRect.style['border' + each] = elemStyle['border' + each];
					elem.colorRect.style['border' + each] = elemStyle['border' + each];
				}
				elem.width = elem.clientWidth;
				elem.height = elem.clientHeight;
				elem.style.visibility = 'hidden';
			}
		},

		updateVmlDimensions: function (elem) {
			var altC = {
				X: {
					b1: 'L',
					b2: 'R',
					d: 'W'
				},
				Y: {
					b1: 'T',
					b2: 'B',
					d: 'H'
				}
			}, bg = {
				X: 0,
				Y: 0
			}, bgR, corner, dC, each, elemName = elem.nodeName, elemStyle = elem.currentStyle, fraction = true, horz, position, size, v;

			size = {
				W: elem.clientWidth,
				H: elem.clientHeight,
				w: elem.bgSizeFinder.width,
				h: elem.bgSizeFinder.height,
				L: elem.offsetLeft,
				T: elem.offsetTop,
				bLW: parseInt(elemStyle.borderLeftWidth, 10),
				bTW: parseInt(elemStyle.borderTopWidth, 10)
			};
			if (isNaN(size.bLW) || elemName === 'IMG') {
				size.bLW = 0;
			}
			if (isNaN(size.bTW) || elemName === 'IMG') {
				size.bTW = 0;
			}
			if (size.W >= doc.body.clientWidth) {
				--size.W;
			}
			for (each = 0; each < 2; each++) {
				elem.rects[each].style.width = size.W + 'px';
				elem.rects[each].style.height = size.H + 'px';
				elem.rects[each].style.left = (size.L + size.bLW) + 'px';
				elem.rects[each].style.top = (size.T + size.bTW) + 'px';
			}
			for (each in bg) {
				position = elemStyle['backgroundPosition' + each];
				switch (position) {
				case 'left':
				case 'top':
					bg[each] = 0;
					break;
				case 'center':
					bg[each] = 0.5;
					break;
				case 'right':
				case 'bottom':
					bg[each] = 1;
					break;
				default:
					if (/%/.test(position)) {
						bg[each] = parseInt(position, 10) * 0.01;
					}
					else {
						fraction = false;
					}
				}
				horz = (each === 'X');
				bg[each] = Math.ceil(fraction ? ((size[horz ? 'W' : 'H'] * bg[each]) - (size[horz ? 'w': 'h'] * bg[each])) : parseInt(position, 10));
			}
			elem.imgFill.position = (bg.X / size.W) + ',' + (bg.Y / size.H);
			bgR = elemStyle.backgroundRepeat;
			dC = {
				T: 0,
				R: size.W,
				B: size.H,
				L: 0
			};
			if (bgR !== 'repeat') {
				corner = {
					T: bg.Y,
					R: bg.X + size.w + ((size.bLW === 0) ? 1 : 0),
					B: bg.Y + size.h,
					L: bg.X + ((size.bLW === 0) ? 1 : 0)
				};
				if (/repeat-/.test(bgR)) {
					v = bgR.split('repeat-')[1].toUpperCase();
					corner[altC[v].b1] = 0;
					corner[altC[v].b2] = size[altC[v].d];
				}
				elem.imgRect.style.clip = 'rect(' + [corner.T, corner.R, corner.B, corner.L, ''].join('px ') + ')';
			}
			else {
				elem.imgRect.style.clip = 'rect(auto)';
			}
		}
	};
}(jQuery));

jQuery.fn.DD_belatedPNG = (function ($) {
	return function () {
		var $each, each = 0;
		while (($each = this.eq(each++))[0]) {
			if ($.browser.msie) {
				$each[0].setExpression('behavior', $.DD_belatedPNG.fixPng($each[0]));
			}
		}	
	};
}(jQuery));