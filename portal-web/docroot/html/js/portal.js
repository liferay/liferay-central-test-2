function changeBackground(path, extension) { 
	var bodyWidth; 
	if (is_safari) { 
		bodyWidth = self.innerWidth; 
	} 
	else { 
		bodyWidth = document.body.clientWidth; 
	} 
	       
	if (extension != null) { 
		if (bodyWidth <= 1024) { 
			document.body.style.backgroundImage = "url(" + path + "." + extension + ")";
		} 
		else if (bodyWidth > 1024 && bodyWidth <= 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1280." + extension + ")";
		} 
		else if (bodyWidth > 1280) { 
			document.body.style.backgroundImage = "url(" + path + "-1600." + extension + ")";
		} 
	} 
}

var DynamicSelect = {
	create : function(url, source, target, callback, query) {
		var returnObj = new Object();
		returnObj["callback"] = callback;
		returnObj["target"] = target;
		
		source.onchange = function() {
			loadPage(url, (query ? (query + "&") : "") + "sourceValue=" + this.value, DynamicSelect.returnFunction, returnObj);
		}
	},
	
	returnFunction : function(xmlHttpReq, returnObj) {
		var select;
		var target = returnObj["target"];
		var callback = returnObj["callback"];
		
		try {
			select = eval("(" + xmlHttpReq.responseText + ")");
		}
		catch (err) {
		}
		
		target.length = 0;
		if (select.options.length > 0) {
			target.disabled = false;
			var options = select.options;
			for (var i = 0; i < options.length; i++) {
				target.options[i] = new Option(options[i].name, options[i].value);
			}
		}
		else {
			target.disabled = true;
		}
		
		if (callback != null) {
			callback();
		}
	}
}

var PortletHeaderBar = {

	fadeIn : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
			
		if (bar.startOut) {
			// stop fadeOut prematurely
			clearTimeout(bar.timerOut);
			bar.timerOut = 0;
		}
		bar.startOut = false;		
		bar.startIn = true;		

		bar.opac += 20;
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		
		if (bar.opac < 100) {
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 50);
		}
		else {
			bar.timerIn = 0;
			bar.startIn = false;
		}
	},
	
	fadeOut : function (id) {
		var bar = document.getElementById(id);
		
		// portlet has been removed.  exit.
		if (bar == null)
			return;
		
		if (bar.startIn) {
			// stop fadeIn prematurely
			clearTimeout(bar.timerIn);
			bar.timerIn = 0;
		}
		bar.startIn = false;
		bar.startOut = true;		
		
		bar.opac -= 20;
		for (var i = 0; i < bar.iconList.length; i++) {
			changeOpacity(bar.iconList[i], bar.opac);
		}
		bar.iconBar.style.display = "block";
		if (bar.opac > 0) {
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 50);
		}
		else {
			bar.iconBar.style.display = "none";
			bar.timerOut = 0;
			bar.startOut = false;
		}
	},
	
	init : function (bar) {
		if (!bar.iconBar) {
			bar.iconBar = getElementByClassName(bar, "portlet-small-icon-bar");
		}
			
		if (!bar.iconList) {
			bar.iconList = bar.iconBar.getElementsByTagName("img");
		}
	},
	
	hide : function (id) {
		var bar = document.getElementById(id);
		
		// If fadeIn timer has been set, but hasn't started, cancel it
		if (bar.timerIn && !bar.startIn) {
			// cancel unstarted fadeIn
			clearTimeout(bar.timerIn);
			bar.timerIn = 0;
		}	
		
		if (!bar.startOut && bar.opac > 0) {
			if (bar.timerOut) {
				// reset unstarted fadeOut timer
				clearTimeout(bar.timerOut);
				bar.timerOut = 0;
			}

			this.init(bar);
			bar.timerOut = setTimeout("PortletHeaderBar.fadeOut(\"" + id + "\")", 150);
		}
	},
	
	show : function (id) {
		var bar = document.getElementById(id);
		
		// If fadeOut timer has been set, but hasn't started, cancel it
		if (bar.timerOut && !bar.startOut) {
			// cancel unstarted fadeOut
			clearTimeout(bar.timerOut);
			bar.timerOut = 0;
		}
		
		if (!bar.startIn && (!bar.opac || bar.opac < 100)){
			if (!bar.opac) {
				bar.opac = 0;
			}

			if (bar.timerIn) {
				// reset unstarted fadeIn timer
				clearTimeout(bar.timerIn);
				bar.timerIn = 0;
			}

			this.init(bar);
			bar.timerIn = setTimeout("PortletHeaderBar.fadeIn(\"" + id + "\")", 150);
		}
	}
}