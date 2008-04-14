/*

jQuery Browser Plugin
	* Version 1.1.0
	* URL: http://jquery.thewikies.com/browser
	* Description: jQuery Browser Plugin extends browser detection capabilities and implements CSS browser selectors.
	* Author: Nate Cavanaugh, Minhchau Dang, & Jonathan Neal
	* Copyright: Copyright (c) 2008 Jonathan Neal under dual MIT/GPL license.

*/

(function($) {
	
	// Define whether Browser Selectors will be added automatically; set as false to disable.
	var addSelectors = true;

	// Define Navigator Properties.
	var pl = navigator.platform;
	var ua = navigator.userAgent;

	// Define Browser Properties.
	var ob = {

		// Define the rendering client
		gecko: /Gecko/.test(ua) && !/like Gecko/.test(ua),
		webkit: /WebKit/.test(ua),

		// Define the browser
		aol: /America Online Browser/.test(ua),
		camino: /Camino/.test(ua),
		firefox: /Firefox/.test(ua),
		flock: /Flock/.test(ua),
		icab: /iCab/.test(ua),
		konqueror: /KDE/.test(ua),
		mozilla: /mozilla/.test(ua),
		msie: /MSIE/.test(ua),
		netscape: /Netscape/.test(ua),
		opera: /Opera/.test(ua),
		safari: /Safari/.test(ua),
		browser: /(MSIE|Firefox|Opera|Safari|KDE|iCab|Flock)/.exec(ua)[0],

		// Define the opperating system
		win: /Win/.test(pl),
		mac: /Mac/.test(pl),
		linux: /Linux/.test(pl),
		iphone: /iPhone/.test(pl),
		OS: /(Win|Mac|Linux|iPhone)/.exec(pl)[0],

		// Define the classic navigator properties
		platform: pl,
		agent: ua,

		// Define the 'addSelectors' function which adds Browser Selectors to a tag; by default <HTML>.
		addSelectors: function() {
			jQuery(arguments[0] || 'html').addClass([this.renderer,this.browser,this.browser+this.version.major,this.OS,'js'].join(' ').toLowerCase());
		},

		// Define the 'removeSelectors' function which removes Browser Selectors to a tag; by default <HTML>.
		removeSelectors: function() {
			jQuery(arguments[0] || 'html').removeClass([this.renderer,this.browser,this.browser+this.version.major,this.OS,'js'].join(' ').toLowerCase());
		}

	};

	// Redefine the Rendering Client.
	ob.renderer = (ob.gecko) ? 'gecko' : (ob.webkit) ? 'webkit' : '';

	// Redefine the Browser Client Version.
	ob.version = {};
	ob.version.string  = (ob.msie)
		? /MSIE ([^;]+)/.exec(ua)[1]
		: (ob.firefox)
			? /Firefox\/(.+)/.exec(ua)[1]
			: (ob.safari)
				? /Version\/([^\s]+)/.exec(ua)[1]
				: (ob.opera)
					? /Opera\/([^\s]+)/.exec(ua)[1]
					: '';
	ob.version.number = parseFloat(ob.version.string);
	ob.version.major = /([^\.]+)/.exec(ob.version.string)[1];

	// Run the 'addSelectors' Function if the 'addSelectors' Variable is set as true.
	if (addSelectors) {
		ob.addSelectors();
	}

	// Define the jQuery 'browser' Object.
	$.browser = ob;

}(jQuery));