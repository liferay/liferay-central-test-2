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

var LayoutColumns = {
	columns: new Array(),
	layoutMaximized: "",
	plid: "",
	
	init: function(colArray) {
		for (var i = 0; i < colArray.length; i++) {
			var column =  $("layout-column_" + colArray[i]);
			
			if (column) {
				column.columnId = colArray[i];
				
				DropZone.add(column, {
					onDrop: LayoutColumns.onDrop,
					inheritParent: true
					});
					
				LayoutColumns.columns.push(column, {onDrop:LayoutColumns.onDrop});
				
				var boxes = document.getElementsByClassName("portlet-boundary", column);
				
				boxes.foreach(function(item, index) {
					if (!item.isStatic) {
						LayoutColumns.initPortlet(item);
					}
				});
			}
		}
	},
	
	initPortlet: function(portlet) {
		portlet = $(portlet);
		var handle = document.getElementsByClassName("portlet-header-bar", portlet)[0] || document.getElementsByClassName("portlet-title-default", portlet)[0];
		
		DragDrop.create(portlet, {
			revert: true,
			handle: handle,
			highlightDropzones: true});
	},
	
	onDrop: function(item) {
		var dropOptions = this;
		var container = dropOptions.dropItem;
		var childList = container.childNodes;
		var insertBox = null;

		item.dragOptions.clone.isStatic = "yes";
		
		for (var i = 0; i < childList.length; i++){
			var box = childList[i];
			
			if (Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					var nwOffset = Coordinates.northwestOffset(box, true);
					var midY = nwOffset.y + (box.offsetHeight / 2);
					
					if (mousePos.y < midY) {
						insertBox = box;
						break;
					}
				}
				else if (box.isStatic.match("end")) {
					insertBox = box;
					break;
				}
			}
		}
		
		item.parentNode.removeChild(item);
		container.insertBefore(item, insertBox);
		
		item.dragOptions.revert = false;
		item.style.position = "";
		item.style.left = "";
		item.style.top = "";
		item.style.height = "";
		item.style.width = "100%";
		
		// Find new position
		var newPosition = 0;
		
		for (var i = 0; i < childList.length; i++){
			var box = childList[i];
			if (Element.hasClassName(box, "portlet-boundary")) {
				if (!box.isStatic) {
					if (box == item) {
						break;
					}
					newPosition++;
				}
			}
		}
		movePortlet(LayoutColumns.plid, item.portletId, container.columnId, newPosition);
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

}

var QuickEdit = {
	curentInput: null,
	
	create: function(id, options) {
		/* OPTIONS
		 * dragId: specify drag ID to disable drag during editing
		 */
		 
		var item = $(id);
		item.editOptions = options;
		item.onclick = function() { QuickEdit.edit(this); };
	},
	
	edit: function(textObj) {
		var opts = textObj.editOptions;
		var wasClicked = true;
		
		if (opts.dragId) {
			wasClicked = $(opts.dragId).wasClicked;
		}

		if (!textObj.editing && wasClicked) {
			var input = document.createElement("input");
			var textDiv = textObj.parentNode;
			var inputWidth = textObj.offsetWidth;
			
			input.className = "portlet-form-input-field";
			input.style.width = (inputWidth + 10) + "px";
			input.style.marginLeft = "5px";
			input.value = textObj.innerHTML;
			input.textObj = textObj;
			input.onmouseover = function() {
				document.onclick = function() {};
			}
			input.onmouseout = function() {
				document.onclick = QuickEdit.done;
			}
			input.onkeydown = function(event) {
				if (enterPressed(event)) {
					QuickEdit.done();
				}
			}
			
			textObj.style.display = "none";
			textDiv.appendChild(input);
			input.focus();
			
			QuickEdit.curentInput = input;
			
			if (opts.dragId) {
				$(opts.dragId).disableDrag = true;
			}
			
			textObj.editing = true;
		}
	},
	
	done: function() {
		var input = QuickEdit.curentInput;
		if (input) {
			document.onclick = function() {};
			
			var textObj = input.textObj;
			var newText = input.value;
			var opts = textObj.editOptions;
			
			if (newText != textObj.innerHTML) {
				textObj.innerHTML = newText;
				if (opts.onComplete) {
					opts.onComplete(newText);
				}
			}
			
			input.parentNode.removeChild(input);
			textObj.style.display = "";
			textObj.editing = false;
			QuickEdit.curentInput = null;
			
			if (opts.dragId) {
				$(opts.dragId).disableDrag = false;
			}
		}
	}
}